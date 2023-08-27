package com.example.questionservice.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor gives you parameterized and default condtructor

@Data
@RequiredArgsConstructor
public class Response {
	// this is response class for quiz submit
	
	@Id
	public Integer id;
	public String response;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
