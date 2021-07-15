package org.api.cardtrader.modele;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;

	public User() {
		
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
