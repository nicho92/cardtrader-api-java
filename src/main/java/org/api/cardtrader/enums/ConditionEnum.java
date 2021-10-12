package org.api.cardtrader.enums;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public enum ConditionEnum {

	@SerializedName(value = "Mint") 						 MINT("Mint"), 				
	@SerializedName(value = "Near Mint") 			 NEAR_MINT("Near Mint"), 		
	@SerializedName(value = "Slightly Played") 		 SLIGHTLY_PLAYED ("Slightly Played"), 		
	@SerializedName(value = "Moderately Played")MODERATELY_PLAYED("Moderately Played"), 		
	@SerializedName(value = "Played") 					 PLAYED("Played"), 		
	@SerializedName(value = "Heavily Played") 		 HEAVILY_PLAYED("Heavily Played"),
	@SerializedName(value = "Poor") 						 POOR("Poor");
	
	
	private String value;

	ConditionEnum(String value)
	{
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
	
	
	public static ConditionEnum parseByLabel(String s)
	{
		return Arrays.stream(values())
        .filter(bl -> bl.getValue().equalsIgnoreCase(s))
        .findFirst().orElse(null);
	}

	
	
}
