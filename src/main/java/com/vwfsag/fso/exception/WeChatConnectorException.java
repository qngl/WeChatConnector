package com.vwfsag.fso.exception;

/**
 * @author qngl
 *
 */
public class WeChatConnectorException extends RuntimeException {

	private static final long serialVersionUID = 20160415L;

	public WeChatConnectorException() {
	}

	public WeChatConnectorException(String message) {
		super(message);
	}

	public WeChatConnectorException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public WeChatConnectorException(String message, Throwable cause) {
		super(message, cause);
	}

}
