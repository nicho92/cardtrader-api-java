package org.api.cardtrader.modele;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	@SerializedName(value = "display_name") String displayName;
	
	
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	

	@Override
	public String toString() {
		return getDisplayName();
	}
	
	
}
