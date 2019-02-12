package com.wipro.digital.ExceptionHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wipro.digital.ExceptionHandling.ApiError;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
	@Override
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError response = new ApiError.ApiErrorResponseBuilder().withStatus(status)
				.withError_code(status.value()).withMessage(ex.getLocalizedMessage()).build();
		return new ResponseEntity<>(response, response.getStatus());
	}
	/*@Override
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError response = new ApiError.ApiErrorResponseBuilder().withStatus(status)
				.withError_code(status.value()).withMessage(ex.getLocalizedMessage()).build();

		return new ResponseEntity<>(response, response.getStatus());
	}*/
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
	  MissingServletRequestParameterException ex, HttpHeaders headers, 
	  HttpStatus status, WebRequest request) {
	     
	    ApiError response = new ApiError.ApiErrorResponseBuilder().withStatus(status)
				.withError_code(status.value()).withMessage(ex.getLocalizedMessage()).build();
	    return new ResponseEntity<>(response, response.getStatus());
	}
	
/*	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleThrowable(Exception ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError response = new ApiError.ApiErrorResponseBuilder().withStatus(status)
				.withError_code(status.value()).withMessage(ex.getLocalizedMessage()).build();
	    return new ResponseEntity<>(response, response.getStatus());
    }*/
}
