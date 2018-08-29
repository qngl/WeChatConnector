package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class ProductItem implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Integer id;

	private Integer productId;

	private Integer typeId;

	private String typeName;

	private String typeValue;

	private Integer colorId;

	private String colorName;

	private String colorValue;

	private String skuPrice;

	private Integer skuStatus;

	private Long createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue == null ? null : typeValue.trim();
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName == null ? null : colorName.trim();
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue == null ? null : colorValue.trim();
	}

	public String getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice == null ? null : skuPrice.trim();
	}

	public Integer getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(Integer skuStatus) {
		this.skuStatus = skuStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}