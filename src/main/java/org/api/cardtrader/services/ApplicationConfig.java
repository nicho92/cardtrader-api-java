package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApplicationConfig {
	
	private String token;
	private static ApplicationConfig inst;
	
	
	
	public static ApplicationConfig getInstance()
	{
		if(inst ==null)
			inst=new ApplicationConfig();
		
		return inst;
	}
	
	public void setToken(File f) throws IOException
	{
		setToken(Files.readAllLines(f.toPath()).get(0));
	}
	
	public void setToken(String token)
	{
		this.token=token;
	}
	
	
	public String getToken() {
		return token;
	}
	
	public String getBearer() {
		return "Bearer "+token;
	}
	
}
