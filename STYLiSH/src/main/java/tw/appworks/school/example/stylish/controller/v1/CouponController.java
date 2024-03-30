package tw.appworks.school.example.stylish.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.model.coupon.Coupon;
import tw.appworks.school.example.stylish.service.CouponService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;

	@GetMapping("gamePage")
	public ResponseEntity<?> gamePage(
			@RequestHeader("Authorization") String authorization) {

		// new Map 設定 response
		Map<String, Object> response = new HashMap<>();

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			response.put("Error", "Invalid or missing Bearer token");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		String accessToken = extractAccessToken(authorization);

		// Get random coupon
		Coupon coupon = couponService.RandomCoupon();
		System.out.println("hiii");

		// Get game chance
		Integer chance = couponService.Chance(accessToken);
		System.out.println("apple");

		response.put("coupon", coupon);
		response.put("game_chance", chance);

		System.out.println(response);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
	}

	@GetMapping("profilePage")
	public ResponseEntity<?> profilePage(
			@RequestHeader("Authorization") String authorization) {

		// new Map 設定 response
		Map<String, Object> response = new HashMap<>();

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			response.put("Error", "Invalid or missing Bearer token");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		String accessToken = extractAccessToken(authorization);

		// Get user id
		Integer userId = couponService.userId(accessToken);

		// Get coupon list
		List<Map<String, Object>> couponList = couponService.couponList(userId);

		response.put("user_id", userId);
		response.put("coupons", couponList);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
	}

	private String extractAccessToken(String authorization) {
		String[] parts = authorization.split(" ");
		if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
			return parts[1];
		} else {
			return null;
		}
	}
}
