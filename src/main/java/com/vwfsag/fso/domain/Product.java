package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Integer id;

	private String name;

	private String logo;

	private String iconUrl;

	private String imgsBanner;

	private String imgsDetails;

	private String carParameters;

	private String activityTitle;

	private String activityRules;

	private String carPrices;

	private String colors;

	private String types;

	private Integer activityDeposit;

	private Integer productStatus;

	private Long createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo == null ? null : logo.trim();
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl == null ? null : iconUrl.trim();
	}

	public String getImgsBanner() {
		return imgsBanner;
	}

	public void setImgsBanner(String imgsBanner) {
		this.imgsBanner = imgsBanner == null ? null : imgsBanner.trim();
	}

	public String getImgsDetails() {
		return imgsDetails;
	}

	public void setImgsDetails(String imgsDetails) {
		this.imgsDetails = imgsDetails == null ? null : imgsDetails.trim();
	}

	public String getCarParameters() {
		return carParameters;
	}

	public void setCarParameters(String carParameters) {
		this.carParameters = carParameters == null ? null : carParameters.trim();
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle == null ? null : activityTitle.trim();
	}

	public String getActivityRules() {
		return activityRules;
	}

	public void setActivityRules(String activityRules) {
		this.activityRules = activityRules == null ? null : activityRules.trim();
	}

	public String getCarPrices() {
		return carPrices;
	}

	public void setCarPrices(String carPrices) {
		this.carPrices = carPrices == null ? null : carPrices.trim();
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getActivityDeposit() {
		return activityDeposit;
	}

	public void setActivityDeposit(Integer activityDeposit) {
		this.activityDeposit = activityDeposit;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}