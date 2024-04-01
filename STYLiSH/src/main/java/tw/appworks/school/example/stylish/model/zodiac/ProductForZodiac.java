package tw.appworks.school.example.stylish.model.zodiac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForZodiac {
    private long id;
    private String category;
    private String title;
    private String description;
    private Integer price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private List<ColorsForZodiac> colors;
    private List<String> sizes;
    private List<VariantsForZodiac> variants;
    private String main_image;
    private List<String> images;
}
