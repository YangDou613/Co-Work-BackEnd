package tw.appworks.school.example.stylish.model.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coupon {
	private Integer coupon_id;
	private Integer campaign_id;
	private Integer discount_amt;
	private Integer expire_period;
	private String description;
	private String coupon_title;
	private Integer min_expense;
}
