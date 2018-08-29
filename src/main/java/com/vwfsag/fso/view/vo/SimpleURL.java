package com.vwfsag.fso.view.vo;

/**
 * @author liqiang
 *
 */
public class SimpleURL {

	private long id;
	private String src;

	public SimpleURL(String src) {
		super();
		this.id = System.currentTimeMillis();
		this.src = src;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
