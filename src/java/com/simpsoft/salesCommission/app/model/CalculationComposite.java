package com.simpsoft.salesCommission.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CalculationComposite")
public class CalculationComposite {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "RULE_ID")
	private Rule rule;
	
	@Column(name = "calStartDate")
	private Date calStartDate;

	@Column(name = "calEndDate")
	private Date calEndDate;
	
	@Column(name = "compensationAmount")
	private  int compensationAmount;
	
	public CalculationComposite() {
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
	 * @return the calStartDate
	 */
	public Date getCalStartDate() {
		return calStartDate;
	}

	/**
	 * @param calStartDate the calStartDate to set
	 */
	public void setCalStartDate(Date calStartDate) {
		this.calStartDate = calStartDate;
	}

	/**
	 * @return the calEndDate
	 */
	public Date getCalEndDate() {
		return calEndDate;
	}

	/**
	 * @param calEndDate the calEndDate to set
	 */
	public void setCalEndDate(Date calEndDate) {
		this.calEndDate = calEndDate;
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

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
}
