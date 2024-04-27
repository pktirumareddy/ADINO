package com.eznite.adino.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.eznite.adino.dto.StandardResponseDTO;

public class CommonUtils {

	public static <T> StandardResponseDTO<T> successResponse(T data) {
		StandardResponseDTO<T> standardResponseDTO = new StandardResponseDTO<>();
		standardResponseDTO.setError(false);
		standardResponseDTO.setErrorMessage(null);
		standardResponseDTO.setStatusCode(HttpStatus.OK.value());
		standardResponseDTO.setData(data);
		return standardResponseDTO;
	}
	
	public static <T> StandardResponseDTO<T> errorResponse(String data, HttpStatus httpStatus) {
		StandardResponseDTO<T> standardResponseDTO = new StandardResponseDTO<>();
		Map<String, String> errorMessageMap = new HashMap<>();
		errorMessageMap.put("error", data);
		
		standardResponseDTO.setError(true);
		standardResponseDTO.setErrorMessage(errorMessageMap);
		standardResponseDTO.setStatusCode(httpStatus.value());
		standardResponseDTO.setData(null);
		return standardResponseDTO;
	}
}
