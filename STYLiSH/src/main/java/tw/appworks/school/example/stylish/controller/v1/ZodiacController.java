package tw.appworks.school.example.stylish.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.data.dto.ZodiacDto;
import tw.appworks.school.example.stylish.service.ZodiacService;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ZodiacController {

	@Autowired
	private ZodiacService zodiacService;

	@GetMapping("/api/1.0/zodiac")
	public ResponseEntity<?> zodiacDetails() {

		try {
			List<ZodiacDto> response = zodiacService.getZodiacDetails();
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(response);
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
