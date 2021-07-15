package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class App implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date lastRequestAt;
	@SerializedName(value = "shared_secret") private String sharedSecret;
	
	
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
		this.name = name;
	}
	public Date getLastRequestAt() {
		return lastRequestAt;
	}
	public void setLastRequestAt(Date lastRequestAt) {
		this.lastRequestAt = lastRequestAt;
	}
	public String getSharedSecret() {
		return sharedSecret;
	}
	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}
	
	@Override
	public String toString() {
		return getName();
				
	}
	
	
	
	
	
	
}
