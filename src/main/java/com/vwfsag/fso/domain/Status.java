package com.vwfsag.fso.domain;

/**
 * @author liqiang
 *
 */
public enum Status {

	ACTIVE(1), INACTIVE(0);

	private int code;

	private Status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
