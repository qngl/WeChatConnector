package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class ActivityPage implements Serializable {

	private static final long serialVersionUID = 20160410L;

	private Integer id;

	private Integer productId;

	private String displayTitle;

	private String displayImgs;

	private String targetURL;

	private Integer layoutType;

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

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle == null ? null : displayTitle.trim();
	}

	public String getDisplayImgs() {
		return displayImgs;
	}

	public void setDisplayImgs(String displayImgs) {
		this.displayImgs = displayImgs == null ? null : displayImgs.trim();
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL == null ? null : targetURL.trim();
	}

	public Integer getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(Integer layoutType) {
		this.layoutType = layoutType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}