package tw.appworks.school.example.stylish.repository.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.coupon.Coupon;
import tw.appworks.school.example.stylish.model.zodiac.Zodiac;

import java.time.LocalDateTime;
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

	public void insertToUserCouponsTable(Integer userId, Coupon coupon) {

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime expire_time = currentTime.plusDays(coupon.getExpire_period());

		String insertSql = "INSERT INTO user_coupons (user_id, coupon_id, obtain_time, expire_time, coupon_status) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, userId, coupon.getCoupon_id(), currentTime, expire_time, "0");
	}

	public Integer deductChanceFromDatabase(Integer userId) {

		String getChanceSql = "SELECT chance FROM user_game_chance WHERE user_id = ?";
		Integer chances = jdbcTemplate.queryForObject(getChanceSql, Integer.class, userId);

		if (chances != null && chances > 0) {
			chances--;

			String updateSql = "UPDATE user_game_chance SET chance = ? WHERE user_id = ?";
			jdbcTemplate.update(updateSql, chances, userId);
		}

		return chances;
	}

	public Integer getUserId(String accessToken) {

		String getUserIdSql = "SELECT id FROM user WHERE access_token = ?";
		return jdbcTemplate.queryForObject(getUserIdSql, Integer.class, accessToken);

	}

	public List<Map<String, Object>> getCouponList(Integer userID) {

		String getCouponListSql = "SELECT * FROM user_coupons LEFT JOIN coupon ON user_coupons.id = coupon.coupon_id WHERE user_id = ?";
		return jdbcTemplate.queryForList(getCouponListSql, userID);

	}
}