package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleNoticeDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Taipei")
    private Timestamp startTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Taipei")
    private Timestamp endTime;
}
