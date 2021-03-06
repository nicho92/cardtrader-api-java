package org.api.cardtrader.modele;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private Integer id;
	private String countryCode;
	private Boolean tooManyCancel;
	private String userType;
	private Boolean canSellViaHub;
	private String email;
	private String phone;
	

	public User() {
		
	}
	

	public User(Integer id, String name) {
		setUsername(name);
		setId(id);
	}
	
	
	
	public User( Integer id,String username, String email, String phone) {
		this.username = username;
		this.id = id;
		this.email = email;
		this.phone = phone;
	}

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

	
	public User(String name) {
		setUsername(name);
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
