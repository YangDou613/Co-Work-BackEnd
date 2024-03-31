package tw.appworks.school.example.stylish.service;

import tw.appworks.school.example.stylish.data.dto.CampaignDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleEventDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleNoticeDto;
import tw.appworks.school.example.stylish.data.dto.MarketHotsDto;
import tw.appworks.school.example.stylish.data.form.CampaignForm;
import tw.appworks.school.example.stylish.model.campaign.Campaign;

import java.util.List;

public interface FlashSale {
    FlashSaleNoticeDto getNotice();
    FlashSaleEventDto getUpcomingEvent();
}
