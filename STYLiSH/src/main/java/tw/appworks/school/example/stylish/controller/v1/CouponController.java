package tw.appworks.school.example.stylish.controller.v1;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@RestController
@RequestMapping("api/1.0/coupon")
@Slf4j
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

		response = couponService.getRandomCouponAndChance(accessToken);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
	}

	@GetMapping("startGame")
	public ResponseEntity<?> startGame(
			@RequestHeader("Authorization") String authorization) {

		// new Map 設定 response
		Map<String, Object> response = new HashMap<>();

		String accessToken = extractAccessToken(authorization);
		try {
			Integer chances = couponService.deductOneChance(accessToken);
			log.info("Deduct one chance.");
			response.put("chances", chances);
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(response);
		} catch (Exception e) {
			log.info(e.getMessage());
			response.put("Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

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

		response = couponService.getCouponList(accessToken);

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
