package com.qa.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qa.product.model.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository productRepository;
	
	Product product1;
	Product product2;
	Product product3;
	Product product4;
	
	List<Product> productList;
	
	@BeforeEach
	public void setUp() {
		product1 = new Product(1, "prod1", 5.99f, "food");
		product2 = new Product(2, "prod2", 7.49f, "home");
		product3 = new Product(3, "prod3", 9.78f, "food");
		product4 = new Product(4, "prod4", 15.99f, "clothing");
		productList = Arrays.asList(product1, product2, product3, product4);
	}
	
	@AfterEach
	public void tearDown() {
		product1 = product2 = product3 = product4 = null;
		productList = null;
		productRepository.deleteAll();
	}
	
	@Test
	@DisplayName("save-product-test")
	
	public void given_Product_To_Save_Should_Return_The_Saved_Product() {
		Product savedProduct = productRepository.save(product1);
		assertNotNull(savedProduct);
		assertEquals("emp1", savedProduct.getName());
	}
	
	@Test
	@DisplayName("get-products-as-list-test")
	public void given_GetAllProducts_Should_Return_Product_List() {
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		productRepository.save(product4);
		
		List<Product> productList = productRepository.findAll();
		assertEquals(4, productList.size(), "Product list size should be 4");
		assertEquals("prod2", productList.get(1).getName(), "Product at index 1 should be named prod2");
	}
	
	@Test
	@DisplayName("get-product-non-existing-id-test")
	//@Disabled
	public void given_Non_Existing_Id_Should_Return_Optional_Empty() {
		productRepository.save(product1);
		assertThat(productRepository.findById(2)).isEmpty();
	}

}
