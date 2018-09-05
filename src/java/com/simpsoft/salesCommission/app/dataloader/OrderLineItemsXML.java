package com.simpsoft.salesCommission.app.dataloader;

import java.util.Date;

public class OrderLineItemsXML {
	
	private long id;

	private String product;

	private  int quantity;
	
	private  int rate;
	
	private  int discountPercentage;
	
	private  int dutyPercentage;
	
	private String splitRule;

	private  double subtotal;
	
    public OrderLineItemsXML() {
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * @return the discountPercentage
	 */
	public int getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage the discountPercentage to set
	 */
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return the dutyPercentage
	 */
	public int getDutyPercentage() {
		return dutyPercentage;
	}

	/**
	 * @param dutyPercentage the dutyPercentage to set
	 */
	public void setDutyPercentage(int dutyPercentage) {
		this.dutyPercentage = dutyPercentage;
	}

	public String getSplitRule() {
		return splitRule;
	}

	public void setSplitRule(String splitRule) {
		this.splitRule = splitRule;
	}
	
	

	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
}
