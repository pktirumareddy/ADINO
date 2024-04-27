package com.eznite.adino.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ResponseDTO {
	private int id;
	private String name;
	private boolean status;
	private LocalDate date;
	private String dateTime;
}
