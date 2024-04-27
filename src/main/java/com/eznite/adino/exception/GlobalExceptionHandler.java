package com.eznite.adino.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eznite.adino.dto.StandardResponseDTO;
import com.eznite.adino.util.CommonUtils;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

	// custom exception handling
	@ExceptionHandler(BusinessException.class)
	private ResponseEntity<StandardResponseDTO<String>> busException(BusinessException businessException) {
		LOGGER.info(businessException);
		logStackTrace(businessException);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(CommonUtils.errorResponse("business condition failed", HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(ArithmeticException.class)
	private ResponseEntity<StandardResponseDTO<String>> arithException(ArithmeticException arithmeticException) {
		LOGGER.info(arithmeticException);
		logStackTrace(arithmeticException);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				CommonUtils.errorResponse("Arithmetic Exception, Please check code", HttpStatus.INTERNAL_SERVER_ERROR));
	}

	// inbuilt exceptions handling
	@Override
	protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.info(ex);
		logStackTrace(ex);
		return super.handleHandlerMethodValidationException(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.info(ex);
		logStackTrace(ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(CommonUtils.errorResponse("Method not allowed, please check", HttpStatus.BAD_REQUEST));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.info(ex);
		logStackTrace(ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(CommonUtils.errorResponse("Body not readable, please check", HttpStatus.BAD_REQUEST));
	}

	private void logStackTrace(Exception ex) {
		StackTraceElement[] stackTrace = ex.getStackTrace();
		if (stackTrace != null && stackTrace.length > 0) {
			StackTraceElement element = stackTrace[0];
			LOGGER.error("Error occurred in class: {}, method: {}, line: {}", element.getClassName(),
					element.getMethodName(), element.getLineNumber());
		}

	}
}
