package com.vwfsag.fso.view.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vwfsag.fso.domain.ProductItem;

/**
 * @author liqiang
 * 
 */
public class ProductItemForm implements Serializable {

	private static final long serialVersionUID = 20160420L;

	private ProductItem item;
	private ObjectNode type;
	private ObjectNode color;
	private Price skuPrices;
	private Integer skuStatus;
	
	public ProductItemForm(ProductItem item) {
		if(item != null) {
			this.item = item;
			this.type = JsonNodeFactory.instance.objectNode();
			this.type.put("id", item.getTypeId());
			this.type.put("value", item.getTypeValue());
			this.color = JsonNodeFactory.instance.objectNode();
			this.color.put("id", item.getColorId());
			this.color.put("value", item.getColorValue());
			this.skuPrices = new Price(item.getSkuPrice());
			this.skuStatus = item.getSkuStatus();
		}
	}

	public Price getSkuPrices() {
		return skuPrices;
	}

	public void setSkuPrices(Price skuPrices) {
		this.skuPrices = skuPrices;
	}

	public ObjectNode getType() {
		return type;
	}

	public void setType(ObjectNode type) {
		this.type = type;
	}

	public ObjectNode getColor() {
		return color;
	}

	public void setColor(ObjectNode color) {
		this.color = color;
	}

	public Integer getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(Integer skuStatus) {
		this.skuStatus = skuStatus;
	}

	public ProductItem getItem() {
		return item;
	}

	public void setItem(ProductItem item) {
		this.item = item;
	}

}
