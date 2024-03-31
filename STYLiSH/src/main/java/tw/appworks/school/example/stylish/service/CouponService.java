package tw.appworks.school.example.stylish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.model.coupon.Coupon;
import tw.appworks.school.example.stylish.repository.coupon.CouponRepository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;

	public Map<String, Object> getRandomCouponAndChance(String accessToken) {

		Integer userId = userId(accessToken); // Get user id

		// Get
		Map<String, Object> response = new HashMap<>();
		Coupon coupon = RandomCoupon(); // Random coupon
		Integer chance = Chance(accessToken); // Game chance
		response.put("coupon", coupon);
		response.put("game_chance", chance);

		// Insert to user_coupons table
		addToDatabase(userId, coupon);

		return response;
	}

	public void addToDatabase(Integer userId, Coupon coupon) {
		couponRepository.insertToUserCouponsTable(userId, coupon);
	}

	public Integer deductOneChance(String accessToken) {

		Integer userId = userId(accessToken);
		return couponRepository.deductChanceFromDatabase(userId);
	}

	public Map<String, Object> getCouponList(String accessToken) {

		Map<String, Object> response = new HashMap<>();
		Integer userId = userId(accessToken); // User id
		List<Map<String, Object>> couponList = couponList(userId); // Coupon list
		response.put("user_id", userId);
		response.put("coupons", couponList);
		return response;
	}

	public Coupon RandomCoupon() {

		int couponCount = couponRepository.getCouponCount();

		// Random id
		int randomId = 0;
		randomId = (int)(Math.random() * couponCount) + 1;

		return couponRepository.getRandomCoupon(randomId);
	}

	public Integer Chance(String accessToken) {

		// Get user id
		Integer userId = userId(accessToken);

		// Get chance
		return couponRepository.getChance(userId);

	}

	public Integer userId(String accessToken) {
		return couponRepository.getUserId(accessToken);
	}

	public List<Map<String, Object>> couponList(Integer userID) {
		return couponRepository.getCouponList(userID);
	}
}
