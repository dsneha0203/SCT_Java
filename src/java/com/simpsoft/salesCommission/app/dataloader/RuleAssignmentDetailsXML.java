package com.simpsoft.salesCommission.app.dataloader;

import java.util.Date;

public class RuleAssignmentDetailsXML {

	private String validityType;
	
	private long ruleId;
	
	private String frequencyName;
	
	private Date startDate;
	
	private Date endDate;
	
	public RuleAssignmentDetailsXML() {
	}	
	/**
	 * @return the validityType
	 */
	public String getValidityType() {
		return validityType;
	}

	/**
	 * @param validityType the validityType to set
	 */
	public void setValidityType(String validityType) {
		this.validityType = validityType;
	}

	/**
	 * @return the ruleId
	 */
	public long getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the frequencyName
	 */
	public String getFrequencyName() {
		return frequencyName;
	}

	/**
	 * @param frequencyName the frequencyName to set
	 */
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
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

}
