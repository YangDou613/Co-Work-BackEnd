package tw.appworks.school.example.stylish.model.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCoupon {
	private BigInteger user_id;
	private BigInteger coupon_id;
	private LocalDateTime obtain_time;
	private LocalDateTime expire_time;
	private String coupon_status;
}
