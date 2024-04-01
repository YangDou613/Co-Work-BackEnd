package tw.appworks.school.example.stylish.repository.zodiac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.product.Color;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.model.zodiac.*;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZodiacRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Get id from zodiac crawler
	public List<BigInteger> getIdFromZodiacCrawler(LocalDate currentDate) {

		Date date = Date.valueOf(currentDate);
		String getIdSql = "SELECT id FROM zodiac_crawler WHERE date = ?";
		return jdbcTemplate.queryForList(getIdSql, BigInteger.class, date);

	}

	// Get zodiac
	public Zodiac getZodiacFromZodiacCrawler(BigInteger id) {

		String getZodiacSql = "SELECT id, zodiac_id, color_name, SUBSTRING(zodiac_hex, 2) AS zodiac_hex, description FROM zodiac_crawler WHERE id = ?";
		return jdbcTemplate.queryForObject(getZodiacSql, new BeanPropertyRowMapper<>(Zodiac.class), id);

	}

	// Get zodiac element
	public ZodiacEle getZodiacEle(BigInteger zodiacId) {

		String getZodiacEleSql = "SELECT * FROM zodiac_ele WHERE zodiac_id = ?";
		return jdbcTemplate.queryForObject(getZodiacEleSql, new BeanPropertyRowMapper<>(ZodiacEle.class), zodiacId);

	}

	// Get productId from result
	public BigInteger getResult(BigInteger id) {

		String getResultSql = "SELECT product_id FROM sim_result WHERE zodiac_crawler_id = ?";
		return jdbcTemplate.queryForObject(getResultSql, BigInteger.class, id);

	}

	// Get product
	public ProductForZodiac getProduct(BigInteger productId) {

		ProductForZodiac product;

		// Get product
		String getProductSql = "SELECT * FROM product WHERE id = ?";
		product = jdbcTemplate.queryForObject(getProductSql, new BeanPropertyRowMapper<>(ProductForZodiac.class), productId);

		// Get colors
		String getColorsIdSql = "SELECT DISTINCT color_id FROM variant WHERE product_id = ?";
		List<Integer> colorsIdList = jdbcTemplate.queryForList(getColorsIdSql, Integer.class, productId);

		List<ColorsForZodiac> colors = new ArrayList<>();;
		for (Integer id : colorsIdList) {
			String getColorsSql = "SELECT code, name FROM color WHERE id = ?";
			ColorsForZodiac color = jdbcTemplate.queryForObject(getColorsSql, new BeanPropertyRowMapper<>(ColorsForZodiac.class), id);
			colors.add(color);
		}
		product.setColors(colors);

		// Get sizes
		String getSizesSql = "SELECT DISTINCT size FROM variant WHERE product_id = ?";
		List<String> sizes = jdbcTemplate.queryForList(getSizesSql, String.class, productId);
		product.setSizes(sizes);

		// Get stock
		String getVariantsSql = "SELECT SUBSTRING(color_hex, 2) AS color_hex, size, stock FROM variant WHERE product_id = ?";
		List<VariantsForZodiac> variants = jdbcTemplate.query(getVariantsSql, new BeanPropertyRowMapper<>(VariantsForZodiac.class), productId);
		product.setVariants(variants);

		// Get images
		String getImagesSql = "SELECT image FROM product_images WHERE product_id = ?";
		List<String> images = jdbcTemplate.queryForList(getImagesSql, String.class, productId);
		product.setImages(images);

		return product;

	}

}
