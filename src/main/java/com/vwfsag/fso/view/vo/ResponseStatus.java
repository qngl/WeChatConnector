package com.vwfsag.fso.view.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * @author liqiang
 * 
 */
public class ResponseStatus implements Serializable {

	private static final long serialVersionUID = 20160412L;

	public static final ResponseStatus success() {
		return new ResponseStatus(0);
	}

	public static final ResponseStatus failure() {
		return new ResponseStatus(-1);
	}

	private int status;
	private String[] message;
	private Map<String, String> errors;

	public ResponseStatus(int status) {
		super();
		this.status = status;
	}

	public ResponseStatus(int status, String... message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseStatus [status=" + status + ", message=" + Arrays.toString(message) + "]";
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
