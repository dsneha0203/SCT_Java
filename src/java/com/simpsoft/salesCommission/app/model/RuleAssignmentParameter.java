package com.simpsoft.salesCommission.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "RuleAssignmentParameter")
public class RuleAssignmentParameter {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "parameterName")
	private String parameterName;

	@Column(name = "valueType")
	private String valueType;

	@Column(name = "defaultValue")
	private String defaultValue;
	
	@Column(name = "overwriteValue")
	private String overwriteValue;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "TGT_DEF_ID")
	private TargetDefinition targetDefinition;

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
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName
	 *            the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * @param valueType
	 *            the valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the overwriteValue
	 */
	public String getOverwriteValue() {
		return overwriteValue;
	}

	/**
	 * @param overwriteValue
	 *            the overwriteValue to set
	 */
	public void setOverwriteValue(String overwriteValue) {
		this.overwriteValue = overwriteValue;
	}

	/**
	 * @return the targetDefinition
	 */
	public TargetDefinition getTargetDefinition() {
		return targetDefinition;
	}

	/**
	 * @param targetDefinition
	 *            the targetDefinition to set
	 */
	public void setTargetDefinition(TargetDefinition targetDefinition) {
		this.targetDefinition = targetDefinition;
	}

}
