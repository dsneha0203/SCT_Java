package com.simpsoft.salesCommission.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CalcDetailsOrderLineItems")
public class CalcDetailsOrderLineItems {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "compensationAmount")
	private  double compensationAmount;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "qualificationFlag", nullable = false)
	private  boolean qualificationFlag;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ORDER_LINE_ITEMS_SPLIT_ID")
	private OrderLineItemsSplit itemsSplit;
	
	public CalcDetailsOrderLineItems() {
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
	 * @return the compensationAmount
	 */
	public double getCompensationAmount() {
		return compensationAmount;
	}

	/**
	 * @param compensationAmount the compensationAmount to set
	 */
	public void setCompensationAmount(double compensationAmount) {
		this.compensationAmount = compensationAmount;
	}

	/**
	 * @return the qualificationFlag
	 */
	public boolean isQualificationFlag() {
		return qualificationFlag;
	}

	/**
	 * @param qualificationFlag the qualificationFlag to set
	 */
	public void setQualificationFlag(boolean qualificationFlag) {
		this.qualificationFlag = qualificationFlag;
	}

	public OrderLineItemsSplit getItemsSplit() {
		return itemsSplit;
	}

	public void setItemsSplit(OrderLineItemsSplit itemsSplit) {
		this.itemsSplit = itemsSplit;
	}
	
	
}
