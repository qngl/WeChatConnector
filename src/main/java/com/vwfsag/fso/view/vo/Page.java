package com.vwfsag.fso.view.vo;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 20160419L;

	private int total;
	private int pageSize;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
