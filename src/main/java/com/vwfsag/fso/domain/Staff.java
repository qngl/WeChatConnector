package com.vwfsag.fso.domain;

import java.io.Serializable;

/**
 * @author liqiang
 */
public class Staff implements Serializable {

	private static final long serialVersionUID = 20160406L;

	private Long id;
	private String userName;
	private StaffRole role;
	private String password;
	private String fullName;
	private Integer shopId;
	private Long createTime;

	public Staff() {
	}

	public Staff(Long id, String userName, StaffRole role, String password, String fullName, Long createTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.role = role;
		this.password = password;
		this.fullName = fullName;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public StaffRole getRole() {
		return role;
	}

	public void setRole(StaffRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", userName=" + userName + ", role=" + role + ", fullName=" + fullName + "]";
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
