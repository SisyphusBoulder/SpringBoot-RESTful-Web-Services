package com.qa.product.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.product.dto.ProductDto;
import com.qa.product.exceptions.InvalidInputException;
import com.qa.product.exceptions.ProductAlreadyExistsException;
import com.qa.product.exceptions.ProductDoesNotExistException;
import com.qa.product.model.Product;
import com.qa.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Product> getAllProducts() {

		return this.productRepository.findAll();
	}

	@Override
	public Product getProductById(int id) throws ProductDoesNotExistException{//, InvalidInputException{
		Optional<Product> product = null; 
			product = this.productRepository.findById(id);
		if(!product.isPresent()){
			throw new ProductDoesNotExistException();
		}

		return product.get();

	}

	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistsException{//, InvalidInputException{
		Product newProduct = null;
		try {
			Product productExists = getProductById(product.getId());
			if(productExists != null) {
				throw new ProductAlreadyExistsException("This product already exists!");
			}
		} catch (ProductDoesNotExistException e) {
			newProduct =  this.productRepository.save(product);
			e.printStackTrace();
		}
		return newProduct;
	}

	@Override
	public Product updateProduct(Product product) throws ProductDoesNotExistException {
		Optional<Product> updatedProduct = null;
		updatedProduct = this.productRepository.findById(product.getId());
		if(!updatedProduct.isPresent()) {
			throw new ProductDoesNotExistException();
		}
		else {
			return this.productRepository.save(product);
		}
		
	}

	@Override
	public boolean deleteProduct(int id) throws ProductDoesNotExistException {
		boolean status = false;
		Optional<Product> deletedProduct = null;
		deletedProduct = this.productRepository.findById(id);
		if(!deletedProduct.isPresent()) {
			throw new ProductDoesNotExistException();
		}
		this.productRepository.delete(deletedProduct.get());		
		status = true;
		
		return status;
	}
	
	@Override
	public List<Product> findByCategory(String category) {
		
		return this.productRepository.findByCategory(category);
	}

	@Override
	public List<Product> findProductsByAvailabilityAndDiscountPercentage(boolean inStock, float discountPercentage) {
		
		return this.productRepository.findProductsByAvailabilityAndDiscountPercentage(inStock, discountPercentage);
	}

	@Override
	public Float findTotalPriceOfAllProducts() {
		
		return this.productRepository.findTotalPriceOfAllProducts();
	}

	@Override
	public Product updateProductDetails(int id, String category, String delivery_mode) throws ProductDoesNotExistException {
		Product updatedProduct = null;
		Optional<Product> findProductById = this.productRepository.findById(id);
		if(!findProductById.isPresent()) {
			throw new ProductDoesNotExistException();
		}
		else {
			int rows = this.productRepository.updateProductDetails(id, category, delivery_mode);
			if (rows>0) {
				updatedProduct = this.productRepository.findById(id).get();
			}
		}
		return updatedProduct;
	}
	
	private ProductDto mapToProductDto(Product product) {
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductDtoDetails() {
		/*No stream method
		 * 
		 * List<Product> productList = this.productRepository.findAll();
		 * List<ProductDto> productDtoList = new ArrayList<>();
		 * productList.forEach(product -> (ProductDto productDto = ProductDto.builder()
		 * 														.id(product.getId()
		 * 														.name(product.getName()
		 * 														.category(product.getCategory()).build();
		 * productDtoList.add(productDto);
		 * });
		 * 
		 * return productDtoList;
		*/ 		
		//Using Streams
		
		return this.productRepository.findAll().stream().map(this::mapToProductDto).collect(Collectors.toList());
		 
	}


}
