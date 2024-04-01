package tw.appworks.school.example.stylish.model.zodiac;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VariantsForZodiac {
    @JsonProperty("color_code")
    private String color_hex;
    private String size;
    private Integer stock;
}
