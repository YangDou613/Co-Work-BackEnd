package tw.appworks.school.example.stylish.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.data.dto.FlashSaleEventDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleNoticeDto;
import tw.appworks.school.example.stylish.service.impl.FlashSaleServiceImpl;

@RestController
@RequestMapping("api/1.0/flashSale/")
public class FlashSaleController {

    private final FlashSaleServiceImpl flashSaleService;

    public FlashSaleController(FlashSaleServiceImpl flashSaleService) {
        this.flashSaleService = flashSaleService;
    }

    @GetMapping("/notice")
    @ResponseBody
    public ResponseEntity<?> getNotice() {
        FlashSaleNoticeDto noticeDto = flashSaleService.getNotice();
        return ResponseEntity.status(HttpStatus.OK).body(noticeDto);
    }

    @GetMapping("/event")
    @ResponseBody
    public ResponseEntity<?> getEvent() {
        FlashSaleEventDto result = flashSaleService.getUpcomingEvent();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
