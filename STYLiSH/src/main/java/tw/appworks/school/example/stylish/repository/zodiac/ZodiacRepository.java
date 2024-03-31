package tw.appworks.school.example.stylish.repository.zodiac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.model.zodiac.Zodiac;
import tw.appworks.school.example.stylish.model.zodiac.ZodiacEle;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ZodiacRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Get zodiac id
	public List<BigInteger> getIdFromZodiacCrawler() {

		String getIdSql = "SELECT zodiac_id FROM zodiac_ele";
		return jdbcTemplate.queryForList(getIdSql, BigInteger.class);

	}

	// Get zodiac
	public Zodiac getZodiacFromZodiacCrawler(LocalDate currentDate, BigInteger id) {

		Date date = Date.valueOf(currentDate);
		String getZodiacSql = "SELECT * FROM zodiac_crawler WHERE date = ? AND zodiac_id = ?";
		return jdbcTemplate.queryForObject(getZodiacSql, new BeanPropertyRowMapper<>(Zodiac.class), date, id);

	}

	// Get zodiac element
	public ZodiacEle getZodiacEle(BigInteger id) {

		String getZodiacEleSql = "SELECT * FROM zodiac_ele WHERE zodiac_id = ?";
		return jdbcTemplate.queryForObject(getZodiacEleSql, new BeanPropertyRowMapper<>(ZodiacEle.class), id);

	}

	// Get result
	public BigInteger getResult(BigInteger id) {

		String getResultSql = "SELECT product_id FROM sim_result WHERE zodiac_crawler_id = ?";
		return jdbcTemplate.queryForObject(getResultSql, BigInteger.class, id);

	}

	// Get product
	public Product getProduct(BigInteger productId) {

		String getProductSql = "SELECT * FROM product WHERE id = ?";
		return jdbcTemplate.queryForObject(getProductSql, new BeanPropertyRowMapper<>(Product.class), productId);

	}

}
