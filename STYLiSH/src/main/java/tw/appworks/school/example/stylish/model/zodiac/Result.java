package tw.appworks.school.example.stylish.model.zodiac;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Result {
	private BigInteger zodiac_crawler_id;
	private String zodiac_hex;
	private BigInteger product_id;
	private String product_hex;
	private BigInteger distance;
}
