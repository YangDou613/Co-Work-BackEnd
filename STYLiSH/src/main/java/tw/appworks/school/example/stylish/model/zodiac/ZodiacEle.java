package tw.appworks.school.example.stylish.model.zodiac;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ZodiacEle {
	private BigInteger zodiac_id;
	private String zodiac_en;
	private String zodiac_zh;
	private String zodiac_element;
}
