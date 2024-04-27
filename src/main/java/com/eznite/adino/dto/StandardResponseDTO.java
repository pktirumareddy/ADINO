package com.eznite.adino.dto;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class StandardResponseDTO<T> {

	private boolean error;
	private Map<String, String> errorMessage;
	private int statusCode;
	private T data;
	
	
}
