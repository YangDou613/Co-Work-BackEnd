package tw.appworks.school.example.stylish.data.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ZodiacDto {
	private BigInteger zodiacCrawlerId;
	private String zodiacElement;
	private String zodiacZh;
	private String colorName;
	private String colorHex;
	private String description;
	private Object product;
}
