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
@Table(name = "Target")
public class Target {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "TGT_DEF_ID")
	private TargetDefinition targetDefinition;
	
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "FREQ_ID")
	private Frequency frequency;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "terminationDate")
	private Date terminationDate;

	@Column(name = "value")
	private int value;

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
	
	
	/**
	 * @return the frequency
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the terminationDate
	 */
	public Date getTerminationDate() {
		return terminationDate;
	}

	/**
	 * @param terminationDate
	 *            the terminationDate to set
	 */
	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}


	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
