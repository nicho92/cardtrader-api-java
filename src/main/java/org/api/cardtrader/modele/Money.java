package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.Currency;

public class Money implements Serializable {

	private static final long serialVersionUID = 1L;
	private double cents;
	private Currency currency;
	
	
	public double getCents() {
		return cents;
	}
	public void setCents(double cents) {
		this.cents = cents;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	@Override
	public String toString() {
		return getCents() + " "+  getCurrency();
	}
	
	

}
