package com.wells.identity.response;

public class LoginResponse extends SuccessResponse {

	private String token;

	public LoginResponse(String message, String token) {
		setToken(token);
		setMessage(message);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
