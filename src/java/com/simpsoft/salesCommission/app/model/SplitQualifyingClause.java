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
@Table(name = "SplitQualifyingClause")
public class SplitQualifyingClause {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "value")
	private String value;

	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "FLD_LST_ID")
	private FieldList fieldList;

	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "CND_LST_ID")
	private ConditionList conditionList;
	
	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "SPLT_RUL_ID")
	private SplitRule splitRule;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "notFlag", nullable = false)
	private boolean notFlag;
	
	public SplitQualifyingClause() {
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
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
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(FieldList fieldList) {
		this.fieldList = fieldList;
	}

	/**
	 * @return the conditionList
	 */
	public ConditionList getConditionList() {
		return conditionList;
	}

	/**
	 * @param conditionList the conditionList to set
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
	 * @param notFlag the notFlag to set
	 */
	public void setNotFlag(boolean notFlag) {
		this.notFlag = notFlag;
	}

	public SplitRule getSplitRule() {
		return splitRule;
	}

	public void setSplitRule(SplitRule splitRule) {
		this.splitRule = splitRule;
	}
	
	
}
