package com.eznite.adino.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eznite.adino.dto.PayloadDTO;
import com.eznite.adino.dto.ResponseDTO;
import com.eznite.adino.service.ResponseEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class GlobalExceptionController {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionController.class);

	private ResponseEntityService responseEntityService;

	public GlobalExceptionController(ResponseEntityService responseEntityService) {
		this.responseEntityService = responseEntityService;
	}

	@PostMapping("/globalExceptionHandler")
	public ResponseDTO postMethodName(@Valid  @RequestBody PayloadDTO dto) {
		ResponseDTO data = new ResponseDTO();
		data = responseEntityService.getCustomException();
		logger.info(data);
		return data;
	}

}
