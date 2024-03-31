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
public class FlashSaleEventDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("story")
    private String story;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Taipei")
    private Timestamp startTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Taipei")
    private Timestamp endTime;

    @JsonProperty("product")
    private ProductDto product;

    @JsonProperty("stock")
    private Integer stock;
}
