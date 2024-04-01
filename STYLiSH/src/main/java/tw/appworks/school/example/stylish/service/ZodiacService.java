package tw.appworks.school.example.stylish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.dto.ZodiacDto;
import tw.appworks.school.example.stylish.model.zodiac.ProductForZodiac;
import tw.appworks.school.example.stylish.model.zodiac.Zodiac;
import tw.appworks.school.example.stylish.model.zodiac.ZodiacEle;
import tw.appworks.school.example.stylish.repository.zodiac.ZodiacRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZodiacService {

    @Autowired
    ZodiacRepository zodiacRepository;
    @Value("${image.prefix}")
    private String prefix;

    public List<ZodiacDto> getZodiacDetails() {

        List<ZodiacDto> zodiacDtoList = new ArrayList<>();

        // Get id from zodiac crawler
        LocalDate currentDate = LocalDate.now();
        List<BigInteger> idListFromZodiacCrawler = getId(currentDate);

        for (BigInteger id : idListFromZodiacCrawler) {

            ZodiacDto zodiacDto = new ZodiacDto();

            // Get zodiac
            Zodiac zodiac = zodiacRepository.getZodiacFromZodiacCrawler(id);

            // Get zodiac element
            ZodiacEle zodiacEle = zodiacRepository.getZodiacEle(zodiac.getZodiac_id());

            // Get productId from result
            BigInteger productId = zodiacRepository.getResult(id);

            // Get product
            ProductForZodiac product = zodiacRepository.getProduct(productId);

            // Summary
            zodiacDto.setZodiacId(zodiac.getZodiac_id());
            zodiacDto.setZodiacElement(zodiacEle.getZodiac_element());
            zodiacDto.setZodiacZh(zodiacEle.getZodiac_zh());
            zodiacDto.setColorName(zodiac.getColor_name());
            zodiacDto.setColorHex(zodiac.getZodiac_hex());
            zodiacDto.setDescription(zodiac.getDescription());

            appendPrefix(product);
            zodiacDto.setProduct(product);

            zodiacDtoList.add(zodiacDto);
        }

        return zodiacDtoList;

    }

    private List<BigInteger> getId(LocalDate currentDate) {
        return zodiacRepository.getIdFromZodiacCrawler(currentDate);
    }

    private void appendPrefix(ProductForZodiac dto) {
        dto.setMain_image(prefix + dto.getMain_image());
        dto.setImages(dto.getImages().stream().map(image -> prefix + image).collect(Collectors.toList()));
    }
}
