package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.api.cardtrader.enums.StateEnum;

import com.google.gson.annotations.SerializedName;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;
	@SerializedName(value="transaction_code") private String transactionCode;
	private User seller;
	private User buyer;
	private StateEnum state;
	private int size;
	@SerializedName(value = "credit_added_to_seller_at") private Date dateCreditAddedToSeller;
	@SerializedName(value = "sent_at") private Date dateSend;
	@SerializedName(value = "cancelled_at") private Date dateCancel;
	@SerializedName(value = "created_at") private Date dateCreation;
	@SerializedName(value = "paid_at") private Date datePaid;
	private User cancelRequester;
	private Money sellerTotal;
	private Date datePresaleEnd;
	@SerializedName(value = "fee_percentage") private double feePercentage;
	private String feeReason="seller";
	private Money sellerSubtotal;
	private int packagingNumber;
	@SerializedName(value = "order_shipping_address") private Address shippingAddress;
	@SerializedName(value = "order_billing_address") private Address billingAddress;
	private ShippingMethod shippingMethod;
	private Boolean presale;
	@SerializedName(value = "order_items") private List<MarketProduct> orderItems;
	
	
	public Order() {
		orderItems = new ArrayList<>();
	}
	

	public Date getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Boolean getPresale() {
		return presale;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public StateEnum getState() {
		return state;
	}
	public void setState(StateEnum state) {
		this.state = state;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Date getDateCreditAddedToSeller() {
		return dateCreditAddedToSeller;
	}
	public void setDateCreditAddedToSeller(Date dateCreditAddedToSeller) {
		this.dateCreditAddedToSeller = dateCreditAddedToSeller;
	}
	public Date getDateSend() {
		return dateSend;
	}
	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}
	public Date getDateCancel() {
		return dateCancel;
	}
	public void setDateCancel(Date dateCancel) {
		this.dateCancel = dateCancel;
	}
	public User getCancelRequester() {
		return cancelRequester;
	}
	public void setCancelRequester(User cancelRequester) {
		this.cancelRequester = cancelRequester;
	}
	public Money getSellerTotal() {
		return sellerTotal;
	}
	public void setSellerTotal(Money sellerTotal) {
		this.sellerTotal = sellerTotal;
	}
	public Date getDatePresaleEnd() {
		return datePresaleEnd;
	}
	public void setDatePresaleEnd(Date datePresaleEnd) {
		this.datePresaleEnd = datePresaleEnd;
	}
	public double getFeePercentage() {
		return feePercentage;
	}
	public void setFeePercentage(double feePercentage) {
		this.feePercentage = feePercentage;
	}
	public String getFeeReason() {
		return feeReason;
	}
	public void setFeeReason(String feeReason) {
		this.feeReason = feeReason;
	}
	public Money getSellerSubtotal() {
		return sellerSubtotal;
	}
	public void setSellerSubtotal(Money sellerSubtotal) {
		this.sellerSubtotal = sellerSubtotal;
	}
	public int getPackagingNumber() {
		return packagingNumber;
	}
	public void setPackagingNumber(int packagingNumber) {
		this.packagingNumber = packagingNumber;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public Boolean isPresale() {
		return presale;
	}
	public void setPresale(Boolean presale) {
		this.presale = presale;
	}
	public List<MarketProduct> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<MarketProduct> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
	
}
