package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.Currency;

public class Price implements Serializable {

	private double value;
	private Currency currency;
	
	
	public Price()
	{
		
	}
	
	public Price(double value, String currencyCode)
	{
		setValue(value);
		setCurrency(currencyCode);
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public void setCurrency(String currencyCode) {
		this.currency = Currency.getInstance(currencyCode);
	}
	
	
	
}
