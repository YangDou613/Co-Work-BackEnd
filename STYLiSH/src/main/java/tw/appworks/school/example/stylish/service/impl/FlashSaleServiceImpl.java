package tw.appworks.school.example.stylish.service.impl;

import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.dto.FlashSaleEventDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleNoticeDto;
import tw.appworks.school.example.stylish.repository.flashSale.FlashSaleDao;
import tw.appworks.school.example.stylish.service.FlashSale;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class FlashSaleServiceImpl implements FlashSale {
    private final FlashSaleDao flashSaleDao;

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
        return flashSaleDao.getFlashSaleAfterDate(currentTimeStamp);
    }
}
