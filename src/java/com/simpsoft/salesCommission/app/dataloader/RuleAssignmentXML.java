package com.simpsoft.salesCommission.app.dataloader;

import java.util.Date;
import java.util.List;

public class RuleAssignmentXML {
	 
	private long id;

	private String employeeName;

	private String roleName;
	
	private List<RuleAssignmentDetailsXML> ruleAssignmentDetailsXML;
	
	public RuleAssignmentXML() {
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
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	/**
	 * @return the ruleAssignmentDetailsXML
	 */
	public List<RuleAssignmentDetailsXML> getRuleAssignmentDetailsXML() {
		return ruleAssignmentDetailsXML;
	}

	/**
	 * @param ruleAssignmentDetailsXML the ruleAssignmentDetailsXML to set
	 */
	public void setRuleAssignmentDetailsXML(List<RuleAssignmentDetailsXML> ruleAssignmentDetailsXML) {
		this.ruleAssignmentDetailsXML = ruleAssignmentDetailsXML;
	}

}
