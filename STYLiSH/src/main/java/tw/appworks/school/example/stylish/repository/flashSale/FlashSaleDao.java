package tw.appworks.school.example.stylish.repository.flashSale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.data.dto.ColorDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleEventDto;
import tw.appworks.school.example.stylish.data.dto.FlashSaleNoticeDto;
import tw.appworks.school.example.stylish.data.dto.ProductDto;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class FlashSaleDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlashSaleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FlashSaleEventDto getFlashSaleAfterDate(Timestamp timestamp) {
        String selectSql = "SELECT fs.*, p.*, pi.*, c.* FROM (" +
                "    SELECT * FROM flashSale WHERE end_time > ? ORDER BY end_time LIMIT 1" +
                ") fs " +
                "LEFT JOIN product p ON fs.product_id = p.id " +
                "LEFT JOIN product_images pi ON p.id = pi.product_id " +
                "LEFT JOIN color c ON fs.color_id = c.id";

        try {
            Map<Long, FlashSaleEventDto> productMap = new HashMap<>();

            jdbcTemplate.query(selectSql, new Object[]{timestamp}, rs -> {
                Long productId = rs.getLong("p.id");
                if (productMap.containsKey(productId)) {
                    // Product already exists in the map
                    FlashSaleEventDto flashSale = productMap.get(productId);
                    // images
                    Set<String> images = new HashSet<>();
                    images.add(rs.getString("pi.image"));
                    flashSale.getProduct().setImages(images);
                } else {
                    // Create a new FlashSaleEventDto object
                    FlashSaleEventDto flashSale = new FlashSaleEventDto();
                    // Set properties from flashSale table
                    flashSale.setId(rs.getLong("fs.id"));
                    flashSale.setTitle(rs.getString("fs.title"));
                    flashSale.setStory(rs.getString("fs.story"));
                    flashSale.setStartTime(rs.getTimestamp("fs.start_time"));
                    flashSale.setEndTime(rs.getTimestamp("fs.end_time"));
                    flashSale.setStock(rs.getInt("fs.stock"));
                    // Create a new Product object
                    ProductDto product = new ProductDto();
                    product.setId(rs.getLong("p.id"));
                    product.setCategory(rs.getString("p.category"));
                    product.setTitle(rs.getString("p.title"));
                    product.setDescription(rs.getString("p.description"));
                    product.setPrice(rs.getInt("p.price"));
                    product.setTexture(rs.getString("p.texture"));
                    product.setWash(rs.getString("p.wash"));
                    product.setPlace(rs.getString("p.place"));
                    product.setNote(rs.getString("p.note"));
                    product.setStory(rs.getString("p.story"));
                    product.setMainImage(rs.getString("p.main_image"));

                    // Product Color
                    ColorDto color = new ColorDto();
                    color.setName(rs.getString("c.name"));
                    color.setCode(rs.getString("c.code"));
                    Set<ColorDto> colors = new HashSet<>();
                    colors.add(color);
                    product.setColors(colors);
                    // Product Size
                    Set<String> sizes = new HashSet<>();
                    sizes.add(rs.getString("fs.size"));
                    product.setSizes(sizes);

                    // images
                    Set<String> images = new HashSet<>();
                    images.add(rs.getString("pi.image"));
                    product.setImages(images);

                    flashSale.setProduct(product);
                    productMap.put(productId, flashSale);
                }
            });

            return productMap.isEmpty() ? null : productMap.entrySet().iterator().next().getValue();
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no result is found
        }
    }

    public FlashSaleNoticeDto getFlashSaleNoticeAfterDate(Timestamp timestamp) {
        String selectSql = "SELECT * FROM flashSale WHERE end_time > ? ORDER BY end_time LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(selectSql, new Object[]{timestamp}, (rs, rowNum) -> {
                FlashSaleNoticeDto flashSale = new FlashSaleNoticeDto();
                flashSale.setId(rs.getLong("id"));
                flashSale.setStartTime(rs.getTimestamp("start_time"));
                flashSale.setEndTime(rs.getTimestamp("end_time"));
                // Map other fields of FlashSaleNoticeDto as needed
                return flashSale;
            });
        } catch (EmptyResultDataAccessException e) {
            // Variant not found
            return null;
        }
    }
}
