package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Long id;

	private Integer productId;

	private Integer itemSkuId;

	private Integer shopId;

	private Integer cityId;

	private String cityName;

	private Integer userId;

	private String productName;

	private String userName;

	private String phone;

	private String carPrices;

	private Integer activityDeposit;

	private Integer activityDiscount;

	private Integer buyCount;

	private Integer orderStatus;

	private String payId;

	private Integer payAmount;

	private String couponCode;

	private Long createTime;

	private Long payTime;

	private Long expireTime;

	private Long cancelTime;

	private Long couponDestroyTime;

	private Long couponExpireTime;

	public Order() {
		super();
	}

	public Order(Long id, Integer productId, Integer itemSkuId, Integer shopId, Integer cityId, String cityName,
			Integer userId, String productName, String userName, String phone, String carPrices,
			Integer activityDeposit, Integer activityDiscount, Integer buyCount, Integer orderStatus, Long createTime,
			Long expireTime, String payId, Long payTime, Integer payAmount, Long cancelTime, Long couponDestroyTime,
			String couponCode, Long couponExpireTime) {
		super();
		this.id = id;
		this.productId = productId;
		this.itemSkuId = itemSkuId;
		this.shopId = shopId;
		this.cityId = cityId;
		this.cityName = cityName;
		this.userId = userId;
		this.productName = productName;
		this.userName = userName;
		this.phone = phone;
		this.carPrices = carPrices;
		this.activityDeposit = activityDeposit;
		this.activityDiscount = activityDiscount;
		this.buyCount = buyCount;
		this.orderStatus = orderStatus;
		this.createTime = createTime;
		this.expireTime = expireTime;
		this.payId = payId;
		this.payTime = payTime;
		this.payAmount = payAmount;
		this.cancelTime = cancelTime;
		this.couponDestroyTime = couponDestroyTime;
		this.couponCode = couponCode;
		this.couponExpireTime = couponExpireTime;
	}

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

	public Integer getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Integer itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName == null ? null : cityName.trim();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getCarPrices() {
		return carPrices;
	}

	public void setCarPrices(String carPrices) {
		this.carPrices = carPrices == null ? null : carPrices.trim();
	}

	public Integer getActivityDeposit() {
		return activityDeposit;
	}

	public void setActivityDeposit(Integer activityDeposit) {
		this.activityDeposit = activityDeposit;
	}

	public Integer getActivityDiscount() {
		return activityDiscount;
	}

	public void setActivityDiscount(Integer activityDiscount) {
		this.activityDiscount = activityDiscount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId == null ? null : payId.trim();
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Long getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Long cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getCouponDestroyTime() {
		return couponDestroyTime;
	}

	public void setCouponDestroyTime(Long couponDestroyTime) {
		this.couponDestroyTime = couponDestroyTime;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode == null ? null : couponCode.trim();
	}

	public Long getCouponExpireTime() {
		return couponExpireTime;
	}

	public void setCouponExpireTime(Long couponExpireTime) {
		this.couponExpireTime = couponExpireTime;
	}
}