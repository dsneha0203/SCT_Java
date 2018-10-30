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
@Table(name = "OrderLineItemsSplit")
public class OrderLineItemsSplit {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "BEN_ID")
	private Employee beneficiary;
	
	@Column(name = "beneficiaryType")
	private String beneficiaryType;
	
	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "SPLT_RUL_ID")
	private SplitRule splitRule;
	
/*	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "ORD_LN_ID")
	private OrderLineItems orderLineItems; */
	
	@Column(name = "splitQuantity")
	private double splitQuantity;
	
	@Column(name = "splitSubTotal")
	private double splitSubTotal;

	public OrderLineItemsSplit() {
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
	 * @return the beneficiary
	 */
	public Employee getBeneficiary() {
		return beneficiary;
	}

	/**
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(Employee beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * @return the splitRule
	 */
	public SplitRule getSplitRule() {
		return splitRule;
	}

	/**
	 * @param splitRule the splitRule to set
	 */
	public void setSplitRule(SplitRule splitRule) {
		this.splitRule = splitRule;
	}

	/**
	 * @return the orderLineItems
	 */
/*	public OrderLineItems getOrderLineItems() {
		return orderLineItems;
	}

	/**
	 * @param orderLineItems the orderLineItems to set
	 */
/*	public void setOrderLineItems(OrderLineItems orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	/**
	 * @return the splitQuantity
	 */
	public double getSplitQuantity() {
		return splitQuantity;
	}

	/**
	 * @param splitQuantity the splitQuantity to set
	 */
	public void setSplitQuantity(double splitQuantity) {
		this.splitQuantity = splitQuantity;
	}

	/**
	 * @return the splitSubTotal
	 */
	public double getSplitSubTotal() {
		return splitSubTotal;
	}

	/**
	 * @param splitSubTotal the splitSubTotal to set
	 */
	public void setSplitSubTotal(double splitSubTotal) {
		this.splitSubTotal = splitSubTotal;
	}	
}
