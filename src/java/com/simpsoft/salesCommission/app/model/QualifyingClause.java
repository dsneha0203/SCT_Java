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
@Table(name = "QualifyingClause")
public class QualifyingClause {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "value")
	private String value;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "FLD_LST_ID")
	private FieldList fieldList;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "CND_LST_ID")
	private ConditionList conditionList;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "notFlag", nullable = false)
	private boolean notFlag;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "ignoreCase", nullable = false)
	private boolean ignoreCase;
	
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "AGG_FUNC_ID")
	private AggregateFunctions aggregateFunctions;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the fieldList
	 */
	public FieldList getFieldList() {
		return fieldList;
	}

	/**
	 * @param fieldList
	 *            the fieldList to set
	 */
	public void setFieldList(FieldList fieldList) {
		this.fieldList = fieldList;
	}

	
	public AggregateFunctions getAggregateFunctions() {
		return aggregateFunctions;
	}

	public void setAggregateFunctions(AggregateFunctions aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	/**
	 * @return the conditionList
	 */
	public ConditionList getConditionList() {
		return conditionList;
	}

	/**
	 * @param conditionList
	 *            the conditionList to set
	 */
	public void setConditionList(ConditionList conditionList) {
		this.conditionList = conditionList;
	}
	
	/**
	 * @return the notFlag
	 */
	public boolean isNotFlag() {
		return notFlag;
	}

	/**
	 * @param notFlag
	 *            the notFlag to set
	 */
	public void setNotFlag(boolean notFlag) {
		this.notFlag = notFlag;
	}

	/**
	 * @return the ignoreCase
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * @param ignoreCase the ignoreCase to set
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

}
