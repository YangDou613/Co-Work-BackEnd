package tw.appworks.school.example.stylish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.model.coupon.Coupon;
import tw.appworks.school.example.stylish.repository.coupon.CouponRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;

	public Coupon RandomCoupon() {

		// Random id
		int randomId = 0;
		randomId = (int)(Math.random() * 5) + 1;

		System.out.println(randomId);

		System.out.println("oooooooooo");

		Coupon response = couponRepository.getRandomCoupon(randomId);

		System.out.println(response);

		return response;

//		return couponRepository.getRandomCoupon(randomId);
	}

	public Integer Chance(String accessToken) {

		// Get user id
		Integer userId = userId(accessToken);
		System.out.println(userId);

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
