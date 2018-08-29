package com.simpsoft.salesCommission.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Frequency")
public class Frequency {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "frequencyName")
	private String frequencyName;

	@Column(name = "type")
	private String type;

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
	 * @return the frequencyName
	 */
	public String getFrequencyName() {
		return frequencyName;
	}

	/**
	 * @param frequencyName
	 *            the frequencyName to set
	 */
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
