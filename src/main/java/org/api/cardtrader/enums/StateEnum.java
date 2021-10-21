package org.api.cardtrader.enums;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public enum StateEnum {
	
	@SerializedName(value = "paid") 		PAID("Paid"), 				
	@SerializedName(value = "done") 		DONE("Done"), 		
	@SerializedName(value = "closed") 		CLOSED("Closed");

	private String value;

	StateEnum(String value)
	{
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
	public static StateEnum parseByLabel(String s)
	{
		return Arrays.stream(values())
        .filter(bl -> bl.getValue().equalsIgnoreCase(s))
        .findFirst().orElse(null);
	}

}
