package com.simpsoft.salesCommission.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "productName")
	private String productName;

	@Column(name = "description")
	private String description;	

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "PROD_STYP_ID")
	private ProductSubType productSubType;
	
	public Product() {
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
	 * @return the productType
	 */
	public ProductSubType getProductSubType() {
		return productSubType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductSubType(ProductSubType productSubType) {
		this.productSubType = productSubType;
	}

}
