package org.api.cardtrader.modele;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private Integer id;
	private String countryCode;
	private boolean tooManyCancel;
	private String userType;
	private boolean canSellViaHub;
	private String email;
	private String phone;
	
	
	
	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public Integer getId() {
		return id;
	}
	
	
	

	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getCountryCode() {
		return countryCode;
	}




	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}




	public boolean isTooManyCancel() {
		return tooManyCancel;
	}




	public void setTooManyCancel(boolean tooManyCancel) {
		this.tooManyCancel = tooManyCancel;
	}




	public String getUserType() {
		return userType;
	}




	public void setUserType(String userType) {
		this.userType = userType;
	}




	public boolean isCanSellViaHub() {
		return canSellViaHub;
	}




	public void setCanSellViaHub(boolean canSellViaHub) {
		this.canSellViaHub = canSellViaHub;
	}




	public void setId(Integer id) {
		this.id = id;
	}

	public User() {
		
	}
	
	
	public User(String name) {
		setUsername(name);
	}
	

	public User(Integer id, String name) {
		setUsername(name);
		setId(id);
	}
	
	@Override
	public String toString() {
		return getUsername();
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
