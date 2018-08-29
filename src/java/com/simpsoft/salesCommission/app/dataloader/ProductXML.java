package com.simpsoft.salesCommission.app.dataloader;


public class ProductXML {
	
	private long id;

	private String productName;

	private String description;	

	private String productSubType;
	
	public ProductXML() {
		
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the productSubType
	 */
	public String getProductSubType() {
		return productSubType;
	}

	/**
	 * @param productSubType the productSubType to set
	 */
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

}
