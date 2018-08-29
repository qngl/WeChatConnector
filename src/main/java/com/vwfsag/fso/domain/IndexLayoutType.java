package com.vwfsag.fso.domain;

/**
 * @author liqiang
 * 
 */
public enum IndexLayoutType {

	LOGO(1, "LOGO"), BANNER(2, "BANNER"), ENTRANCES(3, "活动入口"), RECOMMEND(4, "新车推荐"), ALL(5, "全部车型"), CONTACT(6, "咨询电话");

	private final int code;
	private final String title;

	private IndexLayoutType(int code, String title) {
		this.code = code;
		this.title = title;
	}

	public int getCode() {
		return code;
	}
	
	public String getTitle() {
		return title;
	}

	public static final IndexLayoutType fromCode(int code) {
		for (IndexLayoutType v : values()) {
			if (v.getCode() == code) {
				return v;
			}
		}
		throw new IllegalArgumentException("Invalid IndexLayoutType Code: " + code);
	}
}
