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
	@SerializedName(value="order_as") private String orderAs;
	private User seller;
	private User buyer;
	private StateEnum state;
	private int size;
	private Price total;
	@SerializedName(value = "credit_added_to_seller_at") private Date dateCreditAddedToSeller;
	@SerializedName(value = "sent_at") private Date dateSend;
	@SerializedName(value = "cancelled_at") private Date dateCancel;
	@SerializedName(value = "created_at") private Date dateCreation;
	@SerializedName(value = "paid_at") private Date datePaid;
	private User cancelRequester;
	private Date datePresaleEnd;
	private Date dateUpdate;
	@SerializedName(value = "fee_percentage") private double feePercentage;
	private String feeReason="seller";
	private int packagingNumber;
	@SerializedName(value = "order_shipping_address") private Address shippingAddress;
	@SerializedName(value = "order_billing_address") private Address billingAddress;
	private ShippingMethod shippingMethod;
	private Boolean presale;
	private Price feeAmount;
	private Price sellerFeeAmount;
	private Price subTotal;
	@SerializedName(value = "order_items") private List<OrderItem> orderItems;
	
	
	
	public Order() {
		orderItems = new ArrayList<>();
	}

		
		
		public String getOrderAs() {
		return orderAs;
	}



	public void setOrderAs(String orderAs) {
		this.orderAs = orderAs;
	}



		public Price getSellerFeeAmount() {
			return sellerFeeAmount;
		}



		public Price getSubTotal() {
			return subTotal;
		}



		public void setSubTotal(Price subTotal) {
			this.subTotal = subTotal;
		}



		public void setSellerFeeAmount(Price sellerFeeAmount) {
			this.sellerFeeAmount = sellerFeeAmount;
		}



		public Price getFeeAmount() {
			return feeAmount;
		}


		public void setFeeAmount(Price feeAmount) {
			this.feeAmount = feeAmount;
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
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}


	public Price getTotal() {
		return total;
	}


	public void setTotal(Price total) {
		this.total = total;
	}



	public Date getDateUpdate() {
		return dateUpdate;
	}



	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	
	
	
}
