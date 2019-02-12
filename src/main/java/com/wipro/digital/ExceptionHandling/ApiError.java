package com.wipro.digital.ExceptionHandling;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    private int error_code;
    private String message;
    
    
   public HttpStatus getStatus() {
		return status;
	}


	public void setStatus(HttpStatus status) {
		this.status = status;
	}


	public int getError_code() {
		return error_code;
	}


	public void setError_code(int error_code) {
		this.error_code = error_code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	//Builder 
    public static final class ApiErrorResponseBuilder {
        private HttpStatus status;
        private int error_code;
        private String message;

        ApiErrorResponseBuilder() {
        }

        public static ApiErrorResponseBuilder anApiErrorResponse() {
            return new ApiErrorResponseBuilder();
        }

        public ApiErrorResponseBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiErrorResponseBuilder withError_code(int error_code) {
            this.error_code = error_code;
            return this;
        }

        public ApiErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiError build() {
            ApiError apiErrorResponse = new ApiError();
            apiErrorResponse.status = this.status;
            apiErrorResponse.error_code = this.error_code;
            apiErrorResponse.message = this.message;
            return apiErrorResponse;
        }
    }


}