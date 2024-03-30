package tw.appworks.school.example.stylish.repository.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.coupon.Coupon;
import tw.appworks.school.example.stylish.model.zodiac.Zodiac;

import java.util.List;
import java.util.Map;

@Repository
public class CouponRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Coupon getRandomCoupon(int randomId) {

		System.out.println("kkkk");

		String getCouponSql = "SELECT * FROM coupon WHERE coupon_id = ?";
		return jdbcTemplate.queryForObject(getCouponSql, new BeanPropertyRowMapper<>(Coupon.class), randomId);

	}

	public Integer getChance(Integer userId) {

		String getChanceSql = "SELECT chance FROM user_game_chance WHERE user_id = ?";
		return jdbcTemplate.queryForObject(getChanceSql, Integer.class, userId);

	}

	public Integer getUserId(String accessToken) {

		System.out.println("hereeeeeeeeeee");

		String getUserIdSql = "SELECT id FROM user WHERE access_token = ?";
		return jdbcTemplate.queryForObject(getUserIdSql, Integer.class, accessToken);

	}

	public List<Map<String, Object>> getCouponList(Integer userID) {

		String getCouponListSql = "SELECT * FROM user_coupons LEFT JOIN coupon ON user_coupons.id = coupon.coupon_id WHERE user_id = ?";
		return jdbcTemplate.queryForList(getCouponListSql, userID);

	}
}
