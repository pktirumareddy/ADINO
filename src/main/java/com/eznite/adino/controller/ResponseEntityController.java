package com.eznite.adino.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eznite.adino.dto.PayloadDTO;
import com.eznite.adino.dto.ResponseDTO;
import com.eznite.adino.dto.StandardResponseDTO;
import com.eznite.adino.service.ResponseEntityService;
import com.eznite.adino.util.CommonUtils;

import jakarta.validation.Valid;

@RestController
public class ResponseEntityController {
	
	private static final Logger logger = LogManager.getLogger(ResponseEntityController.class);

	private ResponseEntityService responseEntityService;

	public ResponseEntityController(ResponseEntityService responseEntityService) {
		this.responseEntityService = responseEntityService;
	}

	@PostMapping("/standardResponse")
	public ResponseEntity<StandardResponseDTO<ResponseDTO>> getStandardResponse(@Valid @RequestBody PayloadDTO payloadDTO) {
		logger.info("payloadDTO = "+payloadDTO);
		try {
			if (payloadDTO.isStatus()) {
				//success condition
				HttpHeaders headers = new HttpHeaders();
				headers.add("X-Custom-Header", payloadDTO.getName());
				return ResponseEntity.status(HttpStatus.OK).headers(headers)
						.body(responseEntityService.getStaticSuccessResponse());
			} else {
				if (payloadDTO.getName().equals("OK")) {
					//exception condition
					return ResponseEntity.status(HttpStatus.OK)
							.body(CommonUtils.successResponse(responseEntityService.getStaticErrorResponse()));
				} else {
					logger.info("bad request condition");
					//bad request condition
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonUtils
							.errorResponse("Request body contains neither OK nor true", HttpStatus.BAD_REQUEST));
				}

			}
		} catch (Exception e) {
			logger.info("e = "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonUtils.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/wildCardResponse")
	public ResponseEntity<?> getWildCardResponse(@Valid @RequestBody PayloadDTO payloadDTO) {
		logger.info("payloadDTO = "+payloadDTO);
		if (payloadDTO.isStatus()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Custom-Header", payloadDTO.getName());
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseEntityService.getStaticSuccessResponse());
		} else {
			return new ResponseEntity<ResponseDTO>(responseEntityService.getStaticErrorResponse(), HttpStatus.OK);
		}
	}
	

}
