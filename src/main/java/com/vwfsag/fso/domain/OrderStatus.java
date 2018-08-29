package com.vwfsag.fso.domain;

/**
 * @author liqiang
 *
 */
public enum OrderStatus {

	PAID(4, "已支付"), VERIFIED(5, "已核销"), TO_PAY(1, "待支付"), CANCELED(2, "已取消"), EXPIRED(3, "已超时"), REFUNDED(6, "已退款"), CLOSED(7, "已结算");

	private final int code;
	private final String title;

	private OrderStatus(int code, String title) {
		this.code = code;
		this.title = title;
	}

	public int getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public static final OrderStatus fromCode(int code) {
		for (OrderStatus v : values()) {
			if (v.getCode() == code) {
				return v;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus Code: " + code);
	}
}
