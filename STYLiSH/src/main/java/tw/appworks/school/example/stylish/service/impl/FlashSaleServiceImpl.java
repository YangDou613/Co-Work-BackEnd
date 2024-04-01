package tw.appworks.school.example.stylish.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
public class FlashSaleServiceImpl implements FlashSale {
    private final FlashSaleDao flashSaleDao;

    @Value("${image.prefix}")
    private String prefix;

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
        flashSaleDao.reduceStockByFlashSaleId(flashSaleId, 1);
    }

    private void appendPrefix(ProductDto dto) {
        dto.setMainImage(prefix + dto.getMainImage());
        dto.setImages(dto.getImages().stream().map(image -> prefix + image).collect(Collectors.toSet()));
    }
}
