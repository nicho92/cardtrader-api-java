package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.Currency;

public class Price implements Serializable {

	private Double value;
	private Currency currency;
	
	
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
	
	public Double getValue() {
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
