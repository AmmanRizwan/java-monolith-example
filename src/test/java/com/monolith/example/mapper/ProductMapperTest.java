package com.monolith.example.mapper;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

@SpringBootTest
public class ProductMapperTest {

    private final static Logger logger = LoggerFactory.getLogger(ProductMapperTest.class);

    @Autowired
    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    public void shouldReturnProductToProductResponseDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("10.20"));
        product.setDescription("This is a simple Laptop");
        product.setCreatedAt(Date.from(Instant.now()));
        product.setUpdatedAt(Date.from(Instant.now()));

        ProductResponseDto response = mapper.toProductResponseDto(product);

        // Should map the Product.class into ProductResponseDto.class
        // Without change the properties type
        Assertions.assertEquals(response.id(), product.getId());
        Assertions.assertEquals(response.name(), product.getName());
        Assertions.assertEquals(response.price(), product.getPrice());
        Assertions.assertEquals(response.description(), product.getDescription());
        Assertions.assertNotNull(response.createdAt());
        Assertions.assertNotNull(response.updatedAt());

        logger.info("Unit Test: Product->ProductResponseDto: id={}", response.id());
    }

    @Test
    public void shouldHaveNullValuesToProductResponseDto() {
        Product product = new Product();

        ProductResponseDto response = mapper.toProductResponseDto(product);

        // Should map the Product.class into ProductResponseDto.class
        // Should return properties as null value
        Assertions.assertNull(response.id());
        Assertions.assertNull(response.name());
        Assertions.assertNull(response.price());
        Assertions.assertNull(response.description());

        Assertions.assertThrows(
                NullPointerException.class,
                () -> mapper.toProductResponseDto(null)
        );

        logger.warn("Unit Test: Product->Error");
    }

    @Test
    public void shouldReturnProductDtoToProduct() {
        ProductDto dto = new ProductDto(
                "Laptop",
                new BigDecimal("10.2"),
                "This is a common laptop"
        );

        Product product = mapper.toProduct(dto);

        // Should map the ProductDto.class into Product.class
        // Without changing the properties type
        Assertions.assertEquals(dto.name(), product.getName());
        Assertions.assertNull(product.getId());
        Assertions.assertEquals(dto.price(), product.getPrice());
        Assertions.assertEquals(dto.description(), product.getDescription());

        logger.info("Unit Test: ProductDto->Product: name={}", dto.name());
    }

    @Test
    public void shouldHaveNullValuesToProduct() {
        ProductDto dto = new ProductDto(null, null, null);

        Product product = mapper.toProduct(dto);

        Assertions.assertNull(product.getId());
        Assertions.assertNull(product.getName());
        Assertions.assertNull(product.getDescription());
        Assertions.assertNull(product.getPrice());

        Assertions.assertThrows(
                NullPointerException.class,
                () -> mapper.toProduct(null)
        );

        logger.warn("Unit Test: ProductDto->Error");
    }
}
