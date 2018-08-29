package com.vwfsag.fso.view.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author liqiang
 *
 */
public class Price implements Serializable {

	private static final long serialVersionUID = 20160423L;

	private Integer discountPrice;
	private Integer price;
	private Integer guidePrice;
	private Integer actPrice;

	public Price() {
	}

	public Price(Integer discountPrice, Integer price, Integer guidePrice, Integer actPrice) {
		super();
		this.discountPrice = discountPrice;
		this.price = price;
		this.guidePrice = guidePrice;
		this.actPrice = actPrice;
	}

	public Price(String jsonString) {
		if (StringUtils.isNotBlank(jsonString)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode actualObj = mapper.readTree(jsonString);
				this.discountPrice = actualObj.get("discountPrice").asInt() / 100;
				this.price = actualObj.get("price").asInt() / 100;
				this.guidePrice = actualObj.get("guidePrice").asInt() / 100;
				this.actPrice = actualObj.get("actPrice").asInt() / 100;
			} catch (Exception e) {
				throw new IllegalArgumentException("The Price JSON String is invalid.", e);
			}
		}
	}

	public String toJsonString() {
		return String.format("{\"discountPrice\":\"%d\",\"price\":\"%d\",\"guidePrice\":\"%d\",\"actPrice\":\"%d\"}",
				discountPrice * 100, price * 100, guidePrice * 100, actPrice * 100);
	}

	public Integer getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Integer guidePrice) {
		this.guidePrice = guidePrice;
	}

	public Integer getActPrice() {
		return actPrice;
	}

	public void setActPrice(Integer actPrice) {
		this.actPrice = actPrice;
	}

}
