package com.vwfsag.fso.view.vo;

import java.io.Serializable;

import org.apache.commons.lang.time.DateFormatUtils;

import com.vwfsag.fso.domain.Order;
import com.vwfsag.fso.domain.OrderStatus;
import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.domain.Shop;

/**
 * @author liqiang
 *
 */
public class SynchOrder implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Long orderId;
	private String userName;
	private String phone;
	private String couponCode;
	private String carType;
	private String carColor;
	private String carCategory;
	private String shopName;
	private String shopCode;
	private String shopPreSalesId;
	private String cityName;
	private Integer discountPrice;
	private Integer price;
	private Integer guidePrice;
	private Integer actPrice;
	private Integer activityDeposit;
	private Integer activityDiscount;
	private Integer buyCount;
	private String createTime;
	private String payTime;
	private double payAmount;
	private String couponExpireTime;
	private String orderStatus;
	private Integer orderStatusCode;
	
	private Integer status;
	private String message;

	public SynchOrder(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public void setOrder(Order order) {
		this.orderId = order.getId();
		this.userName = order.getUserName();
		this.phone = order.getPhone();
		this.couponCode = order.getCouponCode();
		this.carCategory = order.getProductName();
		Price price = new Price(order.getCarPrices());
		this.discountPrice = price.getDiscountPrice();
		this.price = price.getPrice();
		this.guidePrice = price.getGuidePrice();
		this.actPrice = price.getActPrice();
		this.activityDeposit = order.getActivityDeposit() / 100;
		this.activityDiscount = order.getActivityDiscount() / 100;
		this.buyCount = order.getBuyCount();
		this.payAmount = order.getPayAmount() / 100.d;
		this.createTime = DateFormatUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm");
		this.payTime = DateFormatUtils.format(order.getPayTime(), "yyyy-MM-dd HH:mm");
		this.couponExpireTime = DateFormatUtils.format(order.getCouponExpireTime(), "yyyy-MM-dd HH:mm");
		this.orderStatus = OrderStatus.fromCode(order.getOrderStatus()).getTitle();
		this.orderStatusCode = OrderStatus.fromCode(order.getOrderStatus()).getCode();
	}
	
	public void setItem(ProductItem item) {
		this.carType = item.getTypeValue();
		this.carColor = item.getColorValue();
	}
	
	public void setShop(Shop shop) {
		this.shopName = shop.getName();
		this.shopCode = shop.getCode();
		this.shopPreSalesId = shop.getPreSalesId();
		this.cityName = shop.getProvinceName() + " " + shop.getCityName();
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getCouponExpireTime() {
		return couponExpireTime;
	}

	public void setCouponExpireTime(String couponExpireTime) {
		this.couponExpireTime = couponExpireTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopPreSalesId() {
		return shopPreSalesId;
	}

	public void setShopPreSalesId(String shopPreSalesId) {
		this.shopPreSalesId = shopPreSalesId;
	}

	public Integer getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(Integer orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}
}
