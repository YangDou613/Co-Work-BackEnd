package tw.appworks.school.example.stylish.model.zodiac;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tw.appworks.school.example.stylish.model.product.Color;

import java.util.ArrayList;
import java.util.List;

@Data
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
