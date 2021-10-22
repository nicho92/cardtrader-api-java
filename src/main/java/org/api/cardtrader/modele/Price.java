package org.api.cardtrader.modele;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Price implements Serializable {

	@SerializedName(value = "value", alternate = {"cents"})  private Double value;
	private String currency;
	
	
	public Price()
	{
		
	}
	
	
	@Override
	public String toString() {
		return value +" " + currency;
	}
	
	public Price(Double value, String currencyCode)
	{
		setValue(value);
		setCurrency(currencyCode);
	}
	
	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public void setValue(Double value) {
		this.value = value;
	}


	public Double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
	
}
