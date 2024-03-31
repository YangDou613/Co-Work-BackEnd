package tw.appworks.school.example.stylish.model.zodiac;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class Zodiac {
	private BigInteger id;
	private BigInteger zodiac_id;
	private String zodiac_hex;
	private String description;
	private String color_name;
}
