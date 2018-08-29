package com.simpsoft.salesCommission.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ConditionList")
public class ConditionList {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "conditionValue")
	private String conditionValue;

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
	 * @return the conditionValue
	 */
	public String getConditionValue() {
		return conditionValue;
	}

	/**
	 * @param conditionValue
	 *            the conditionValue to set
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	
	@Override
    public String toString() {
         return "<" + conditionValue + ">";
    }

}
