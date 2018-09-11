package com.simpsoft.salesCommission.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "CalculationSimple")
public class CalculationSimple {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "calStartDate")
	private Date calStartDate;

	@Column(name = "calEndDate")
	private Date calEndDate;
	
	@Column(name = "compensationAmount")
	private  double compensationAmount;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "dummyCalcInternal", nullable = false)
	private  boolean dummyCalcInternal;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "successFlag", nullable = false)
	private  boolean successFlag;
	
	@Column(name = "ruleOutput")
	private  String ruleOutput;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "RULE_ID")
	private Rule rule;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "EMP_ID")
	private Employee employee;
	
	@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "CALC_SIMPLE_ID")
	@IndexColumn(name = "detailSrl")
	private List<CalculationDetails> calcDetailsList;
	
	@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "CALC_SIMPLE_ID")
	@IndexColumn(name = "detailSrl")
	private List<CalcDetailsOrderLineItems> calcDetailsOrderLineItemsList;
	
	public CalculationSimple() {
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
	 * @return the dummyCalcInternal
	 */
	public boolean isDummyCalcInternal() {
		return dummyCalcInternal;
	}

	/**
	 * @param dummyCalcInternal the dummyCalcInternal to set
	 */
	public void setDummyCalcInternal(boolean dummyCalcInternal) {
		this.dummyCalcInternal = dummyCalcInternal;
	}

	/**
	 * @return the successFlag
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * @param successFlag the successFlag to set
	 */
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * @return the ruleOutput
	 */
	public String getRuleOutput() {
		return ruleOutput;
	}

	/**
	 * @param ruleOutput the ruleOutput to set
	 */
	public void setRuleOutput(String ruleOutput) {
		this.ruleOutput = ruleOutput;
	}

	public List<CalculationDetails> getCalcDetailsList() {
		return calcDetailsList;
	}

	public void setCalcDetailsList(List<CalculationDetails> calcDetailsList) {
		this.calcDetailsList = calcDetailsList;
	}

	public List<CalcDetailsOrderLineItems> getCalcDetailsOrderLineItemsList() {
		return calcDetailsOrderLineItemsList;
	}

	public void setCalcDetailsOrderLineItemsList(List<CalcDetailsOrderLineItems> calcDetailsOrderLineItemsList) {
		this.calcDetailsOrderLineItemsList = calcDetailsOrderLineItemsList;
	}
	
	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
