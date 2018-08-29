package com.vwfsag.fso.view.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.vwfsag.fso.domain.Product;

/**
 * @author liqiang
 * 
 */
public class ProductForm implements Serializable {

	private static final long serialVersionUID = 20160420L;

	private String name;
	private String logoURL;
	private String iconURL;
	private List<SimpleURL> bannerURLs;
	private List<SimpleURL> detailURLs;
	private String carParameters;
	private String activityTitle;
	private String activityRules;
	private Price carPrices;
	private JsonNode colors;
	private JsonNode types;
	private double deposit;

	public ProductForm(Product prod) {
		if(prod != null) {
			setName(prod.getName());
			setLogoURL(prod.getLogo());
			setIconURL(prod.getIconUrl());
			List<SimpleURL> banners = new ArrayList<SimpleURL>();
			if (StringUtils.isNotBlank(prod.getImgsBanner())) {
				for (String url : prod.getImgsBanner().split(",")) {
					banners.add(new SimpleURL(url));
				}
			}
			setBannerURLs(banners);
			List<SimpleURL> details = new ArrayList<SimpleURL>();
			if (StringUtils.isNotBlank(prod.getImgsDetails())) {
				for (String url : prod.getImgsDetails().split(",")) {
					details.add(new SimpleURL(url));
				}
			}
			setDetailURLs(details);
			setCarParameters(prod.getCarParameters());
			setActivityTitle(prod.getActivityTitle());
			setActivityRules(prod.getActivityRules());
			setDeposit(prod.getActivityDeposit() / 100.d);
			setCarPrices(new Price(prod.getCarPrices()));
			colors = JsonUtils.fromJsonString(prod.getColors());
			types = JsonUtils.fromJsonString(prod.getTypes());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public List<SimpleURL> getBannerURLs() {
		return bannerURLs;
	}

	public void setBannerURLs(List<SimpleURL> banners) {
		this.bannerURLs = banners;
	}

	public List<SimpleURL> getDetailURLs() {
		return detailURLs;
	}

	public void setDetailURLs(List<SimpleURL> imgDetails) {
		this.detailURLs = imgDetails;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getActivityRules() {
		return activityRules;
	}

	public void setActivityRules(String activityRules) {
		this.activityRules = activityRules;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public String getCarParameters() {
		return carParameters;
	}

	public void setCarParameters(String carParameters) {
		this.carParameters = carParameters;
	}

	public Price getCarPrices() {
		return carPrices;
	}

	public void setCarPrices(Price carPrice) {
		this.carPrices = carPrice;
	}

	public JsonNode getColors() {
		return colors;
	}

	public void setColors(JsonNode colors) {
		this.colors = colors;
	}

	public JsonNode getTypes() {
		return types;
	}

	public void setTypes(JsonNode types) {
		this.types = types;
	}

}
