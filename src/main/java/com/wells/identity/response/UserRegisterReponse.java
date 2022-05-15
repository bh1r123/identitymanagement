package com.wells.identity.response;

public class UserRegisterReponse extends SuccessResponse {

	private String token;

	public UserRegisterReponse(String message, String token) {
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
