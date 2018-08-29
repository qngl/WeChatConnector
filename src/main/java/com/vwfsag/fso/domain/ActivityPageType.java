package com.vwfsag.fso.domain;

/**
 * @author liqiang
 * 
 */
public enum ActivityPageType {

	IMAGE(1, "图片"), MEDIA(2, "多媒体");

	private final int code;
	private final String title;

	private ActivityPageType(int code, String title) {
		this.code = code;
		this.title = title;
	}

	public int getCode() {
		return code;
	}
	
	public String getTitle() {
		return title;
	}

	public static final ActivityPageType fromCode(int code) {
		for (ActivityPageType v : values()) {
			if (v.getCode() == code) {
				return v;
			}
		}
		throw new IllegalArgumentException("Invalid ActivityLayoutType Code: " + code);
	}
}
