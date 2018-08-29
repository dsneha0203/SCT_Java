package com.simpsoft.salesCommission.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private  int compensationAmount;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "qualificationFlag", nullable = false)
	private  boolean qualificationFlag;
	
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
	public int getCompensationAmount() {
		return compensationAmount;
	}

	/**
	 * @param compensationAmount the compensationAmount to set
	 */
	public void setCompensationAmount(int compensationAmount) {
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
}
