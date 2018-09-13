package com.github.rest.yaml.test;

import com.jayway.restassured.specification.RequestSpecification;

public class CurrentRequestandResponse {
	
	private static String response;
	private static String request;
	
	public static void setState(String string, String response) {
		CurrentRequestandResponse.request = string;
		CurrentRequestandResponse.response = response;
	}

	public static String getResponse() {
		return response;
	}

	public static void setResponse(String response) {
		CurrentRequestandResponse.response = response;
	}

	public static String getRequest() {
		return request;
	}

	public static void setRequest(String request) {
		CurrentRequestandResponse.request = request;
	}
	
	

}
