package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class City implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Integer id;

	private String code;

	private String name;

	private Integer parentId;

	private Integer citySort;

	private String nameEn;

	private String shortnameEn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCitySort() {
		return citySort;
	}

	public void setCitySort(Integer citySort) {
		this.citySort = citySort;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn == null ? null : nameEn.trim();
	}

	public String getShortnameEn() {
		return shortnameEn;
	}

	public void setShortnameEn(String shortnameEn) {
		this.shortnameEn = shortnameEn == null ? null : shortnameEn.trim();
	}
}