package com.eznite.adino.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.eznite.adino.dto.ResponseDTO;
import com.eznite.adino.dto.StandardResponseDTO;
import com.eznite.adino.service.ResponseEntityService;
import com.eznite.adino.util.CommonUtils;

@Service
public class ResponseEntityServiceImpl implements ResponseEntityService {

	@Override
	public StandardResponseDTO<ResponseDTO> getStaticSuccessResponse() {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
			responseDTO.setId(1);
			responseDTO.setName("praveen");
			responseDTO.setStatus(true);
			responseDTO.setDate(LocalDateTime.now().toLocalDate());
			responseDTO.setDateTime(LocalDateTime.now().format(dateTimeFormat));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return CommonUtils.successResponse(responseDTO);
	}

	@Override
	public ResponseDTO getStaticErrorResponse() {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			int i = 10/0;
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
			responseDTO.setId(1);
			responseDTO.setName("praveen");
			responseDTO.setStatus(false);
			responseDTO.setDate(LocalDateTime.now().toLocalDate());
			responseDTO.setDateTime(LocalDateTime.now().format(dateTimeFormat));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return responseDTO;
	}

}
