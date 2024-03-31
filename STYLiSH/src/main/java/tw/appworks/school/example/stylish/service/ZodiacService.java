package tw.appworks.school.example.stylish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.dto.ZodiacDto;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.model.zodiac.Zodiac;
import tw.appworks.school.example.stylish.model.zodiac.ZodiacEle;
import tw.appworks.school.example.stylish.repository.zodiac.ZodiacRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZodiacService {

	@Autowired
	ZodiacRepository zodiacRepository;

	public List<ZodiacDto> getZodiacDetails() {

		List<ZodiacDto> zodiacDtoList = new ArrayList<>();

		// Get zodiac id
		List<BigInteger> idList = getId();

		for (BigInteger id : idList) {

			ZodiacDto zodiacDto = new ZodiacDto();

			// Get zodiac
			LocalDate currentDate = LocalDate.now();
			Zodiac zodiac = zodiacRepository.getZodiacFromZodiacCrawler(currentDate, id);

			// Get zodiac element
			ZodiacEle zodiacEle = zodiacRepository.getZodiacEle(id);

			// Get productId from result
			BigInteger productId = zodiacRepository.getResult(id);

			// Get product
			Product product = zodiacRepository.getProduct(productId);

			// Summary
			zodiacDto.setZodiacCrawlerId(id);
			zodiacDto.setZodiacElement(zodiacEle.getZodiac_element());
			zodiacDto.setZodiacZh(zodiacEle.getZodiac_zh());
			zodiacDto.setColorName(zodiac.getColor_name());
			zodiacDto.setColorHex(zodiac.getZodiac_hex());
			zodiacDto.setDescription(zodiac.getDescription());
			zodiacDto.setProduct(product);

			zodiacDtoList.add(zodiacDto);
		}

		return zodiacDtoList;

	}

	private List<BigInteger> getId() {
		return zodiacRepository.getIdFromZodiacCrawler();
	}
}
