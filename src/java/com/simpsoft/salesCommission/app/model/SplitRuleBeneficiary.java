package com.simpsoft.salesCommission.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SplitRuleBeneficiary")
public class SplitRuleBeneficiary {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "beneficiaryType")
	private String beneficiaryType;
	
	@Column(name = "splitPercentage")
	private int splitPercentage;
	
	public SplitRuleBeneficiary() {
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
	 * @return the beneficiaryType
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * @param beneficiaryType the beneficiaryType to set
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * @return the splitPercentage
	 */
	public int getSplitPercentage() {
		return splitPercentage;
	}

	/**
	 * @param splitPercentage the splitPercentage to set
	 */
	public void setSplitPercentage(int splitPercentage) {
		this.splitPercentage = splitPercentage;
	}
}
