package com.qa.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidInputException extends Exception {

	public InvalidInputException(String msg) {
		super(msg);
	}

}
