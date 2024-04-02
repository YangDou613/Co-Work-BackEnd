package tw.appworks.school.example.stylish.service.impl;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import tw.appworks.school.example.stylish.data.dto.FlashSaleEventDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleNoticeDto;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.error.StockEmptyException;
import tw.appworks.school.example.stylish.repository.flashSale.FlashSaleDao;
import tw.appworks.school.example.stylish.service.FlashSale;

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class FlashSaleServiceImpl implements FlashSale {
    private final FlashSaleDao flashSaleDao;

    @Value("${image.prefix}")
    private String prefix;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    public FlashSaleServiceImpl(FlashSaleDao flashSaleDao) {
        this.flashSaleDao = flashSaleDao;
    }

    @Override
    public FlashSaleNoticeDto getNotice() {
        Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
        return flashSaleDao.getFlashSaleNoticeAfterDate(currentTimeStamp);
    }

    @Override
    public FlashSaleEventDto getUpcomingEvent() {
        // get current time stamp
        Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
        FlashSaleEventDto flashSaleEventDto = flashSaleDao.getFlashSaleAfterDate(currentTimeStamp);
        appendPrefix(flashSaleEventDto.getProduct());
        return flashSaleEventDto;
    }

    public void handleCheckout(Long flashSaleId) throws StockEmptyException {
        // check redis before implement
        boolean isStockAvailable = checkStock(flashSaleId);
        if (isStockAvailable) {
            flashSaleDao.reduceStockByFlashSaleId(flashSaleId, 1);
        } else {
            throw new StockEmptyException("FlashSale product stock with ID " + flashSaleId + " has been empty, sorry");
        }
    }

    private void appendPrefix(ProductDto dto) {
        dto.setMainImage(prefix + dto.getMainImage());
        dto.setImages(dto.getImages().stream().map(image -> prefix + image).collect(Collectors.toSet()));
    }

    private boolean checkStock(Long flashSaleId) {
        Jedis jedis = new Jedis(host, port);

        // Check if key exists
        Integer initialStock = flashSaleDao.getStockByFlashSaleId(flashSaleId);

        Long result = jedis.setnx(String.valueOf(flashSaleId), String.valueOf(initialStock));

        // If the key already exists, result will be 0, meaning the key was not set
        if (result == 1) {
            // Key didn't exist before, set initial stock
            log.info("Initial stock set for flash sale: " + initialStock);
        }

        Long newCount = jedis.decr(String.valueOf(flashSaleId));
        // Close the connection
        jedis.close();
        // Check if the counter is already at zero
        if (newCount < 0) {
            log.info("Stock below 0, flash sale product sold out!");
            return false;
        } else {
            System.out.println("Product purchased successfully. Remaining stock: " + newCount);
            log.info("Stock decrease 1, remaining stock: " + newCount);
            return true;
        }
    }
}
