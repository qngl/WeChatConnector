package com.vwfsag.fso.domain;

import java.io.Serializable;

public class AccessToken implements Serializable {

	private static final long serialVersionUID = 20180829L;

	private final String token;
	private final int expiredIn;

	public AccessToken(String token, int expiredIn) {
		super();
		this.token = token;
		this.expiredIn = expiredIn;
	}

	public String getToken() {
		return token;
	}

	public int getExpiredIn() {
		return expiredIn;
	}

}
