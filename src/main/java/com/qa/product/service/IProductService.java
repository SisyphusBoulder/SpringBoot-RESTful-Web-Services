package com.qa.product.service;

import java.util.List;

import com.qa.product.dto.ProductDto;
import com.qa.product.exceptions.InvalidInputException;
import com.qa.product.exceptions.ProductAlreadyExistsException;
import com.qa.product.exceptions.ProductDoesNotExistException;
import com.qa.product.model.Product;

public interface IProductService {

		
		public List<Product> getAllProducts();
		public Product getProductById(int id) throws ProductDoesNotExistException;//, InvalidInputException;
		public Product addProduct(Product product) throws ProductAlreadyExistsException, ProductDoesNotExistException;//, InvalidInputException;
		public Product updateProduct(Product product) throws ProductDoesNotExistException;
		public boolean deleteProduct(int id) throws ProductDoesNotExistException;
		List<Product> findByCategory(String category);
		Float findTotalPriceOfAllProducts();
		List<Product> findProductsByAvailabilityAndDiscountPercentage(boolean inStock, float discountPercentage);
		Product updateProductDetails(int id, String category, String delivery_mode) throws ProductDoesNotExistException;
		List<ProductDto> getProductDtoDetails();

}
