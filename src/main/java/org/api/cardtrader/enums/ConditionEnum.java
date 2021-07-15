package org.api.cardtrader.enums;

import com.google.gson.annotations.SerializedName;

public enum ConditionEnum {

	@SerializedName(value = "Mint") 		MINT, 				
	@SerializedName(value = "Near Mint") 		NEAR_MINT, 		
	@SerializedName(value = "Slightly Played") 		SLIGHTLY_PLAYED, 		
	@SerializedName(value = "Moderately Played") 		MODERATELY_PLAYED, 		
	@SerializedName(value = "Played") 		PLAYED, 		
	@SerializedName(value = "Heavily Played") 		HEAVILY_PLAYED,
	@SerializedName(value = "Poor") 		POOR,
	
	
}
