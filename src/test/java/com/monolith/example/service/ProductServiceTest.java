package com.monolith.example.service;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.mapper.ProductMapper;
import com.monolith.example.model.Product;
import com.monolith.example.repository.ProductRepository;
import com.monolith.example.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateProductServiceTest() {
        ProductDto dto = new ProductDto(
                "Phone",
                new BigDecimal("2.0"),
                "This is a simple phone.");

        Product product = new Product();
        product.setName("Phone");
        product.setPrice(new BigDecimal("2.0"));
        product.setDescription("This is a simple phone.");

        Product save = new Product();
        save.setId(1L);
        save.setName("Phone");
        save.setPrice(new BigDecimal("2.0"));
        save.setDescription("This is a simple phone.");
        save.setCreatedAt(Date.from(Instant.now()));
        save.setUpdatedAt(Date.from(Instant.now()));

        ProductResponseDto response = new ProductResponseDto(
                save.getId(),
                save.getName(),
                save.getPrice(),
                save.getDescription(),
                save.getCreatedAt(),
                save.getUpdatedAt()
                );

        Mockito.when(mapper.toProduct(dto)).thenReturn(product);
        Mockito.when(repository.save(product)).thenReturn(save);
        Mockito.when(mapper.toProductResponseDto(save)).thenReturn(response);

        ProductResponseDto expected = service.createProduct(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "Phone");
        Assertions.assertEquals(expected.price(), new BigDecimal("2.0"));
        Assertions.assertNotEquals(expected.price(), 2.0);
        Assertions.assertEquals(expected.description(), "This is a simple phone.");
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito
                .verify(mapper, Mockito.times(1))
                .toProduct(dto);
        Mockito
                .verify(repository, Mockito.times(1))
                .save(product);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(save);

        logger.info("Unit Test: Create Product id={}, name{}", expected.id(), expected.name());
    }

    @Test
    public void shouldReturnListProductsServiceTest() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("Microwave");
        product.setPrice(new BigDecimal("2.3"));
        product.setDescription("This is a microwave");
        product.setCreatedAt(Date.from(Instant.now()));
        product.setUpdatedAt(Date.from(Instant.now()));

        products.add(product);

        ProductResponseDto response = new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
                );

        Mockito
                .when(repository.findAll())
                .thenReturn(products);
        Mockito
                .when(mapper.toProductResponseDto(ArgumentMatchers.any(Product.class)))
                .thenReturn(response);

        List<ProductResponseDto> expected = service.findProducts();

        Assertions.assertEquals(expected.size(), products.size());

        Mockito
                .verify(repository, Mockito.times(1))
                .findAll();
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(ArgumentMatchers.any(Product.class));

        logger.info("Unit Test: List Products: size={}", expected.size());
    }

    @Test
    public void shouldReturnProductByNameTest() {
        String productName = "Phone";

        Product product = new Product();
        product.setName("Phone");
        product.setDescription("This is a phone");
        product.setPrice(new BigDecimal("2.3"));

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Phone",
                new BigDecimal("2.3"),
                "This is a phone",
                Date.from(Instant.now()),
                Date.from(Instant.now())
                );

        Mockito.when(repository.findByName(productName)).thenReturn(Optional.of(product));
        Mockito.when(mapper.toProductResponseDto(product)).thenReturn(response);

        ProductResponseDto expected = service.findProductByName(productName);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.name(), "Phone");
        Assertions.assertEquals(expected.price(), new BigDecimal("2.3"));
        Assertions.assertEquals(expected.description(), "This is a phone");
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito
                .verify(repository, Mockito.times(1))
                .findByName(productName);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(product);

        logger.info("Unit Test: Find Product By Name: name={}, id={}", expected.name(), expected.id());
    }

    @Test
    public void shouldThrowErrorFindProductByNameTest() {
        String productName = "Microwave";

        Product product = new Product();
        product.setName("Phone");
        product.setDescription("This is a phone");
        product.setPrice(new BigDecimal("2.3"));

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Phone",
                new BigDecimal("2.3"),
                "This is a phone",
                Date.from(Instant.now()),
                Date.from(Instant.now())
                );

        Mockito
                .when(repository.findByName(productName))
                .thenThrow(
                        new IllegalArgumentException("cannot find product by name: " + productName));
        Mockito
                .when(mapper.toProductResponseDto(product))
                .thenReturn(response);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.findProductByName(productName));

        try {
            ProductResponseDto expected = service.findProductByName(productName);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find product by name: Microwave");
        }

        Mockito
                .verify(repository, Mockito.times(2))
                .findByName(productName);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toProductResponseDto(product);

        logger.warn("Unit Test: Error Find Product By Name");
    }

    @Test
    public void shouldReturnProductByIdTest() {
        Long productId = 1L;

        Product product = new Product();
        product.setName("Phone");
        product.setDescription("This is a phone");
        product.setPrice(new BigDecimal("2.3"));

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Phone",
                new BigDecimal("2.3"),
                "This is a phone",
                Date.from(Instant.now()),
                Date.from(Instant.now())
                );

        Mockito.when(repository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(mapper.toProductResponseDto(product)).thenReturn(response);

        ProductResponseDto expected = service.findProduct(productId);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.name(), "Phone");
        Assertions.assertEquals(expected.price(), new BigDecimal("2.3"));
        Assertions.assertEquals(expected.description(), "This is a phone");
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito
                .verify(repository, Mockito.times(1))
                .findById(productId);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(product);

        logger.info("Unit Test: Find Product By Id: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldThrowErrorFindProductByIdTest() {
        Long productId = 2L;

        Product product = new Product();
        product.setName("Phone");
        product.setDescription("This is a phone");
        product.setPrice(new BigDecimal("2.3"));

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Phone",
                new BigDecimal("2.3"),
                "This is a phone",
                Date.from(Instant.now()),
                Date.from(Instant.now())
                );

        Mockito
                .when(repository.findById(productId))
                .thenThrow(
                        new IllegalArgumentException("cannot find product by id: " + productId));
        Mockito
                .when(mapper.toProductResponseDto(product))
                .thenReturn(response);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.findProduct(productId));

        try {
            ProductResponseDto expected = service.findProduct(productId);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find product by id: 2");
        }

        Mockito
                .verify(repository, Mockito.times(2))
                .findById(productId);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toProductResponseDto(product);

        logger.warn("Unit Test: Error Find Product By Id");
    }

    @Test
    public void shouldReturnUpdateProductByIdTest() {
        Long productId = 1L;

        Product product = new Product();
        product.setId(1L);
        product.setName("Camera");
        product.setPrice(new BigDecimal("10.0"));
        product.setDescription("This is a simple Camera");

        ProductDto dto = new ProductDto(
                "Camera",
                new BigDecimal("10.12"),
                "This is a new simple camera");

        Product updateProduct = new Product();
        updateProduct.setId(1L);
        updateProduct.setName(dto.name());
        updateProduct.setDescription(dto.description());
        updateProduct.setPrice(dto.price());
        updateProduct.setCreatedAt(Date.from(Instant.now()));
        updateProduct.setUpdatedAt(Date.from(Instant.now()));

        Mockito.when(repository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(repository.save(product)).thenReturn(updateProduct);
        Mockito.when(mapper.toProductResponseDto(updateProduct)).thenReturn(
                new ProductResponseDto(
                        updateProduct.getId(),
                        updateProduct.getName(),
                        updateProduct.getPrice(),
                        updateProduct.getDescription(),
                        updateProduct.getCreatedAt(),
                        updateProduct.getUpdatedAt()
                )
        );

        ProductResponseDto expected = service.updateProduct(dto, productId);

        Assertions.assertNotNull(expected);

        Assertions.assertEquals(expected.id(), updateProduct.getId());
        Assertions.assertEquals(expected.description(), updateProduct.getDescription());
        Assertions.assertEquals(expected.price(), updateProduct.getPrice());
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito
                .verify(repository, Mockito.times(1))
                .findById(productId);
        Mockito
                .verify(repository, Mockito.times(1))
                .save(product);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(updateProduct);

        logger.info("Unit Test: Update Product By Id: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldThrowErrorUpdateProductByIdTest() {
        Long productId = 1L;

        Product product = new Product();
        product.setId(1L);
        product.setName("Camera");
        product.setPrice(new BigDecimal("10.0"));
        product.setDescription("This is a simple Camera");

        ProductDto dto = new ProductDto(
                "Camera",
                new BigDecimal("10.12"),
                "This is a new simple camera");

        Product updateProduct = new Product();
        updateProduct.setId(1L);
        updateProduct.setName(dto.name());
        updateProduct.setDescription(dto.description());
        updateProduct.setPrice(dto.price());
        updateProduct.setCreatedAt(Date.from(Instant.now()));
        updateProduct.setUpdatedAt(Date.from(Instant.now()));

        Mockito.when(repository.findById(productId))
                .thenThrow(new IllegalArgumentException("cannot find product by id: " + productId));
        Mockito.when(repository.save(product)).thenReturn(updateProduct);
        Mockito.when(mapper.toProductResponseDto(updateProduct)).thenReturn(
                new ProductResponseDto(
                        updateProduct.getId(),
                        updateProduct.getName(),
                        updateProduct.getPrice(),
                        updateProduct.getDescription(),
                        updateProduct.getCreatedAt(),
                        updateProduct.getUpdatedAt()
                )
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateProduct(dto, productId));

        try {
            ProductResponseDto expected = service.updateProduct(dto, productId);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find product by id: 1");
        }

        Mockito
                .verify(repository, Mockito.times(2))
                .findById(productId);
        Mockito
                .verify(repository, Mockito.times(0))
                .save(product);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toProductResponseDto(updateProduct);

        logger.warn("Unit Test: Error Update Product By Id");
    }

    @Test
    public void shouldReturnUpdateProductByNameTest() {
        String productName = "Camera";

        Product product = new Product();
        product.setId(1L);
        product.setName("Camera");
        product.setPrice(new BigDecimal("10.0"));
        product.setDescription("This is a simple Camera");

        ProductDto dto = new ProductDto(
                "Camera",
                new BigDecimal("10.12"),
                "This is a new simple camera");

        Product updateProduct = new Product();
        updateProduct.setId(1L);
        updateProduct.setName(dto.name());
        updateProduct.setDescription(dto.description());
        updateProduct.setPrice(dto.price());
        updateProduct.setCreatedAt(Date.from(Instant.now()));
        updateProduct.setUpdatedAt(Date.from(Instant.now()));

        Mockito.when(repository.findByName(productName)).thenReturn(Optional.of(product));
        Mockito.when(repository.save(product)).thenReturn(updateProduct);
        Mockito.when(mapper.toProductResponseDto(updateProduct)).thenReturn(
                new ProductResponseDto(
                        updateProduct.getId(),
                        updateProduct.getName(),
                        updateProduct.getPrice(),
                        updateProduct.getDescription(),
                        updateProduct.getCreatedAt(),
                        updateProduct.getUpdatedAt()
                )
        );

        ProductResponseDto expected = service.updateProductByName(dto, productName);

        Assertions.assertNotNull(expected);

        Assertions.assertEquals(expected.id(), updateProduct.getId());
        Assertions.assertEquals(expected.description(), updateProduct.getDescription());
        Assertions.assertEquals(expected.price(), updateProduct.getPrice());
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito
                .verify(repository, Mockito.times(1))
                .findByName(productName);
        Mockito
                .verify(repository, Mockito.times(1))
                .save(product);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toProductResponseDto(updateProduct);

        logger.info("Unit Test: Update Product By Name: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldThrowErrorUpdateProductByNameTest() {
        String productName = "Camera";

        Product product = new Product();
        product.setId(1L);
        product.setName("Camera");
        product.setPrice(new BigDecimal("10.0"));
        product.setDescription("This is a simple Camera");

        ProductDto dto = new ProductDto(
                "Camera",
                new BigDecimal("10.12"),
                "This is a new simple camera");

        Product updateProduct = new Product();
        updateProduct.setId(1L);
        updateProduct.setName(dto.name());
        updateProduct.setDescription(dto.description());
        updateProduct.setPrice(dto.price());
        updateProduct.setCreatedAt(Date.from(Instant.now()));
        updateProduct.setUpdatedAt(Date.from(Instant.now()));

        Mockito.when(repository.findByName(productName))
                .thenThrow(new IllegalArgumentException("cannot find product by name: " + productName));
        Mockito.when(repository.save(product)).thenReturn(updateProduct);
        Mockito.when(mapper.toProductResponseDto(updateProduct)).thenReturn(
                new ProductResponseDto(
                        updateProduct.getId(),
                        updateProduct.getName(),
                        updateProduct.getPrice(),
                        updateProduct.getDescription(),
                        updateProduct.getCreatedAt(),
                        updateProduct.getUpdatedAt()
                )
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateProductByName(dto, productName));

        try {
            ProductResponseDto expected = service.updateProductByName(dto, productName);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find product by name: Camera");
        }

        Mockito
                .verify(repository, Mockito.times(2))
                .findByName(productName);
        Mockito
                .verify(repository, Mockito.times(0))
                .save(product);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toProductResponseDto(updateProduct);

        logger.warn("Unit Test: Error Update Product By Name");
    }
}
