package com.qa.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product does not exist!")
public class ProductDoesNotExistException extends Exception {

		public ProductDoesNotExistException() {
		//super(msg);
	}

}
