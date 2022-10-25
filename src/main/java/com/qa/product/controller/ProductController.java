package com.qa.product.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.print.DocFlavor.READER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qa.product.dto.ProductDto;
import com.qa.product.exceptions.ProductAlreadyExistsException;
import com.qa.product.exceptions.ProductDoesNotExistException;
import com.qa.product.model.Product;
import com.qa.product.service.ProductServiceImpl;

@RestController
@RequestMapping("api/v1")
public class ProductController {

	@Autowired
	ProductServiceImpl productService;

	//getAllEmployees -> GET request
	//need to send list as JSON

	ResponseEntity<?> responseEntity;

	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts(){
		try {
			List<Product> productList = this.productService.getAllProducts();
			responseEntity = new ResponseEntity<>(productList, HttpStatus.OK);
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int id) throws ProductDoesNotExistException{
		try {
			Product product = this.productService.getProductById(id);
			responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
		}catch(NoSuchElementException e) {
			responseEntity = new ResponseEntity<>("This ID does not exist!", HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(ProductDoesNotExistException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Some internal error occurred...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@RequestBody Product product) throws ProductAlreadyExistsException{
		try {
			Product addedProduct = this.productService.addProduct(product);
			System.out.println("Added product successfully!" + addedProduct);
			responseEntity = new ResponseEntity<>(product, HttpStatus.CREATED);
		} catch (ProductAlreadyExistsException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@PutMapping("/products")
	public ResponseEntity<?> updateProduct(@RequestBody Product product) throws ProductDoesNotExistException{
		try {
			Product updatedProduct = this.productService.updateProduct(product);
			System.out.println("Updated product successfully!" + updatedProduct);
			responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
		} catch (ProductDoesNotExistException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) throws ProductDoesNotExistException{
		try {
			boolean status = this.productService.deleteProduct(id);
			if(status) {
				responseEntity = new ResponseEntity<>("Deleted product successfully!", HttpStatus.OK);
			}
		} catch (ProductDoesNotExistException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
	
	@GetMapping("/products/category/{category}")
	public ResponseEntity<?> getProductsByCategory(@PathVariable("category") String category){
		try {
			List<Product> productListByCategory = this.productService.findByCategory(category);
			responseEntity = new ResponseEntity<>(productListByCategory, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/products/inStock/{inStock}/discountPercentage/{discountPercentage}")
	public ResponseEntity<?> getProductsByAvailabilityAndDiscountPercentage(@PathVariable("inStock") boolean inStock, @PathVariable("discountPercentage") float discountPercentage){
		try {
			List<Product> productListByAvailabilityAndDiscount = this.productService.findProductsByAvailabilityAndDiscountPercentage(inStock, discountPercentage);
			responseEntity = new ResponseEntity<>(productListByAvailabilityAndDiscount, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/products/total_price")
	public ResponseEntity<?> getTotalPriceOfProducts(){
		try {
			float totalPriceOfProducts = this.productService.findTotalPriceOfAllProducts();
			responseEntity = new ResponseEntity<>(totalPriceOfProducts, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PutMapping("/products/update_details")
	public ResponseEntity<?> updateProductDetails(@RequestBody Product product) throws ProductDoesNotExistException{
		try {
			Product updatedProduct = this.productService.updateProductDetails(product.getId(), product.getCategory(), product.getDeliveryMode());
			responseEntity = new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		} catch (ProductDoesNotExistException e) {
			throw e;
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/products/product-category-details")
	public ResponseEntity<?> getAllProductCategoryDetails(){
		try {
			List<ProductDto> productDtoList = this.productService.getProductDtoDetails();
			responseEntity = new ResponseEntity<>(productDtoList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("An internal server error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}


}
