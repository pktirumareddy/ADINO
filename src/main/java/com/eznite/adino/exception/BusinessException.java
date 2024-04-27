package com.eznite.adino.exception;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Component
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String statusMessage;
	private int statusCode;

	public BusinessException(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	public BusinessException() {
		
	}
}
