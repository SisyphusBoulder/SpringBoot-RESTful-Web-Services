package com.qa.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "product_details")
public class Product {

	//Constructor for testing
	public Product(int id, String name, float price, String category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull
	@Size(min=2, max=20, message = "name must be between 2 and 20 characters!")
	@Pattern(regexp = "^[A-Za-z]*", message = "invalid name, must only contain alphabet characters!")
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Min(0)
	@Column(name = "price")
	private float price;
	
	@NotNull
	@Min(0)
	@Max(10)
	@Column(name = "rating")
	private float rating;
	
	@NotNull
	@Size(min=2, max=20, message = "category must be between 2 and 20 characters!")
	@Pattern(regexp = "^[A-Za-z]*", message = "invalid category, must only contain alphabet characters!")
	@Column(name = "category")
	private String category;
	
	@NotNull
	@Column(name = "in_stock")
	private boolean inStock;
	
	@NotNull
	@Min(0)
	@Max(100)
	@Column(name = "discount_percentage")
	private float discountPercentage;
	
	@NotNull
	@Size(min=4, max=4, message = "Delivery must be Free or Paid!")
	@Pattern(regexp = "^[A-Za-z]*", message = "invalid delivery mode, must only contain alphabet characters!")
	@Column(name = "delivery_mode")
	private String deliveryMode;
	
	@NotNull
	@Column(name = "is_return_accepted")
	private boolean isReturnAccepted;
	
	@NotNull
	@Size(min=2, max=20, message = "Seller name must be between 2 and 20 characters!")
	@Pattern(regexp = "^[A-Za-z]*", message = "invalid seller name, must only contain alphabet characters!")
	@Column(name = "seller_name")
	private String sellerName;
}
