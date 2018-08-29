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
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "SplitRule")
public class SplitRule {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "splitRuleName")
	private String splitRuleName;
	
	@Column(name = "description")
	private String description;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER )
	@JoinColumn(name = "SPLT_RUL_ID")
	@IndexColumn(name = "detailSrl")
	private List<SplitQualifyingClause> splitQualifyingClause;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "SPLT_RUL_ID")
	@IndexColumn(name = "detailSrl")
	private List<SplitRuleBeneficiary> splitRuleBeneficiary;

	public SplitRule() {
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
	 * @return the splitRuleName
	 */
	public String getSplitRuleName() {
		return splitRuleName;
	}

	/**
	 * @param splitRuleName the splitRuleName to set
	 */
	public void setSplitRuleName(String splitRuleName) {
		this.splitRuleName = splitRuleName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the splitQualifyingClause
	 */
	public List<SplitQualifyingClause> getSplitQualifyingClause() {
		return splitQualifyingClause;
	}

	/**
	 * @param splitQualifyingClause the splitQualifyingClause to set
	 */
	public void setSplitQualifyingClause(List<SplitQualifyingClause> splitQualifyingClause) {
		this.splitQualifyingClause = splitQualifyingClause;
	}

	/**
	 * @return the splitRuleBeneficiary
	 */
	public List<SplitRuleBeneficiary> getSplitRuleBeneficiary() {
		return splitRuleBeneficiary;
	}

	/**
	 * @param splitRuleBeneficiary the splitRuleBeneficiary to set
	 */
	public void setSplitRuleBeneficiary(List<SplitRuleBeneficiary> splitRuleBeneficiary) {
		this.splitRuleBeneficiary = splitRuleBeneficiary;
	}

}
