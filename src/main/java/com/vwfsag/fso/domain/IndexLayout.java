package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 */
public class IndexLayout implements Serializable {

	private static final long serialVersionUID = 20160410L;

	private Long id;
	private Integer layoutType;
	private String sourceURL;
	private String targetURL;
	private String displayTitle;
	private String displaySubtitle;
	private String displayDetails;
	private Integer productId;
	private Integer activityLayoutId;
	private Integer layoutSort;
	private Long onlineTime;
	private Integer status;
	
	private Product product;
	private ActivityPage activityPage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(Integer layoutType) {
		this.layoutType = layoutType;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public String getDisplaySubtitle() {
		return displaySubtitle;
	}

	public void setDisplaySubtitle(String displaySubtitle) {
		this.displaySubtitle = displaySubtitle;
	}

	public String getDisplayDetails() {
		return displayDetails;
	}

	public void setDisplayDetails(String displayDetails) {
		this.displayDetails = displayDetails;
	}

	public Integer getActivityLayoutId() {
		return activityLayoutId;
	}

	public void setActivityLayoutId(Integer activityLayoutId) {
		this.activityLayoutId = activityLayoutId;
	}

	public Integer getLayoutSort() {
		return layoutSort;
	}

	public void setLayoutSort(Integer layoutSort) {
		this.layoutSort = layoutSort;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ActivityPage getActivityPage() {
		return activityPage;
	}

	public void setActivityPage(ActivityPage activityPage) {
		this.activityPage = activityPage;
	}

}
