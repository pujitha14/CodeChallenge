package com.wipro.digital;

import org.springframework.http.HttpStatus;

public class Response {

	private HttpStatus status;
    private int code;
    private String data;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
    
}
