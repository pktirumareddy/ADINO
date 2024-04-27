package com.eznite.adino.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Component
@Data
public class PayloadDTO {
	
	@NotBlank
	@Size(min = 1, max = 4, message = "size should be between 1 to 4 characters")
	private String name;
	private boolean status;
}
