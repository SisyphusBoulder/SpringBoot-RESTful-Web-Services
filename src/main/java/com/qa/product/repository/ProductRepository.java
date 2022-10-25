package com.qa.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	/*List<Product> productList;

	public ProductRepository() {
		this.productList = new ArrayList<>();
		this.productList.add(new Product(1, "product1", 8.54f, 4.5f , "home", true, 2.3f, "Free", true, "Tesco"));
		this.productList.add(new Product(2, "product2", 9.41f, 5.6f, "food", true, 4.5f, "Free", true, "Asda"));
		this.productList.add(new Product(3, "product3", 10.53f, 8.9f, "food", true, 1.2f, "Paid", true, "Sainsbury"));
		this.productList.add(new Product(4, "product4", 7.54f, 7.1f, "luxury", true, 4.6f, "Free", true, "Tesco"));
		this.productList.add(new Product(5, "product5", 8.98f, 6.1f, "home", true, 8.7f, "Paid", true, "Tesco"));
		this.productList.add(new Product(6, "product6", 1.25f, 4.5f, "outdoors", true, 10f, "Paid", true, "Aldi"));
		this.productList.add(new Product(7, "product7", 2.21f, 3.8f, "food", true, 5.6f, "Free", true, "Asda"));
		this.productList.add(new Product(8, "product8", 3.65f, 7.8f, "food", true, 2.3f, "Free", true, "Tesco"));
		this.productList.add(new Product(9, "product9", 7.45f, 9.4f, "recreational", true, 0.5f, "Paid", true, "Sainsbury"));
		this.productList.add(new Product(10, "product10", 14.21f, 6.6f, "home", true, 7.1f, "Free", true, "Morrisons"));

	}

	public List<Product> getAllProducts(){
		return this.productList;
	}

	public Product addProduct (Product product) {
		this.productList.add(product);
		return product;
	}

	public Product getProductById(int id) {
		return this.productList.stream().filter(emp -> emp.getId() == id).findFirst().get();
	}*/
	
	List<Product> findByCategory(String category);
	
	@Query(value = "select sum(price) from product_details", nativeQuery = true)
	Float findTotalPriceOfAllProducts();
	
	@Query("select p from Product p where p.inStock = :in_stock and p.discountPercentage >= :discount_percentage")
	List<Product> findProductsByAvailabilityAndDiscountPercentage(boolean in_stock, float discount_percentage);
	
	@Modifying //for update and delete queries
	@Query("update Product p set p.category = :category, p.deliveryMode = :delivery_mode where p.id = :id")
	int updateProductDetails(int id, String category, String delivery_mode);

}
