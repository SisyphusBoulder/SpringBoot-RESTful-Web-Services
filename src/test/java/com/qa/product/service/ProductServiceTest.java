package com.qa.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.product.exceptions.ProductAlreadyExistsException;
import com.qa.product.model.Product;
import com.qa.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@Autowired
	@InjectMocks
	private ProductServiceImpl productService;
	
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
	
	public void given_Product_To_Save_Should_Return_The_Saved_Product() throws ProductAlreadyExistsException {
		when(productRepository.findByName(any())).thenReturn(null);
		when(productRepository.save(any())).thenReturn(product1);
		Product savedProduct = productService.addProduct(product1);
		assertNotNull(savedProduct);
		assertEquals(1,savedProduct.getId());
		verify(productRepository,times(1)).findByName(any());
		verify(productRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("save-product-throws-exception-test")
	
	public void given_Existing_Product_To_Save_Method_Should_Throw_Exception() throws ProductAlreadyExistsException {
		when(productRepository.findByName(any())).thenReturn(product1);
		
		//productService.addProduct(product1);
		assertThrows(ProductAlreadyExistsException.class,()-> productService.addProduct(product1));
	}

}
