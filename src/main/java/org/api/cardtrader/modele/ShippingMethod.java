package org.api.cardtrader.modele;

import java.io.Serializable;

public class ShippingMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private boolean tracked;
	private String trackedCode;
	private int estimatedShippingDay;
	private Price sellerPrice;
	
	@Override
	public String toString() {
		return getName();
	}
	
	
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
	public boolean isTracked() {
		return tracked;
	}
	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}
	public String getTrackedCode() {
		return trackedCode;
	}
	public void setTrackedCode(String trackedCode) {
		this.trackedCode = trackedCode;
	}
	public int getEstimatedShippingDay() {
		return estimatedShippingDay;
	}
	public void setEstimatedShippingDay(int estimatedShippingDay) {
		this.estimatedShippingDay = estimatedShippingDay;
	}
	public Price getSellerPrice() {
		return sellerPrice;
	}
	public void setSellerPrice(Price sellerPrice) {
		this.sellerPrice = sellerPrice;
	}
	
	

}
