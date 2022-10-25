package com.qa.product.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.product.model.Product;
import com.qa.product.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	
	@Mock
	ProductServiceImpl productService;
	
	@Autowired
	@InjectMocks
	ProductController productController;
	
	@Autowired
	private MockMvc mockMvc;

	
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
		
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@AfterEach
	public void tearDown() {
		product1 = product2 = product3 = product4 = null;
		productList = null;
	}
	
	@Test
	@DisplayName("save-product-test")
	public void given_Product_To_Save_Product_Should_Return_Product_As_JSON_With_Status_Created() throws Exception {
		when(productService.addProduct(any())).thenReturn(product1);
		mockMvc.perform(post("/api/v1/product-service/product")
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(asJsonString(product1)))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.name").value("product1"));
	}
	
	@Test
	@DisplayName("get-product-test")
	public void given_GetAllProducts_Should_Return_List() throws Exception {
		when(productService.getAllProducts()).thenReturn(productList);
		mockMvc.perform(get("/api/v1/product-service/product")
				        .accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[1].name").value("product2"));
	}

	
	public static String asJsonString(Object obj) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		try {
             // Getting organisation object as a json string
            jsonStr = Obj.writeValueAsString(obj);
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
	}
}
