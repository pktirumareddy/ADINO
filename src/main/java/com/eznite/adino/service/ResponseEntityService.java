package com.eznite.adino.service;

import com.eznite.adino.dto.ResponseDTO;
import com.eznite.adino.dto.StandardResponseDTO;

public interface ResponseEntityService {
	StandardResponseDTO<ResponseDTO> getStaticSuccessResponse();
	ResponseDTO getStaticErrorResponse();
}
