package com.simpsoft.salesCommission.app.dataloader;

import java.util.List;

import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleParameter;

public class RuleCompositeXML {
	
	private long id;

	private String ruleName;

	private String description;

	private String ruleType;

	private String ruleDetails;
	
	private List<RuleParameter> ruleParameter;

	private String connectionType;

	private String compensationType;

	private int fixedCompValue;

	private String compensationFormula;

	private String compensationParameter;
	
	private List<Rule> compJoinRule;

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
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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
	 * @return the ruleType
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * @return the ruleDetails
	 */
	public String getRuleDetails() {
		return ruleDetails;
	}

	/**
	 * @param ruleDetails the ruleDetails to set
	 */
	public void setRuleDetails(String ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	/**
	 * @return the ruleParameter
	 */
	public List<RuleParameter> getRuleParameter() {
		return ruleParameter;
	}

	/**
	 * @param ruleParameter the ruleParameter to set
	 */
	public void setRuleParameter(List<RuleParameter> ruleParameter) {
		this.ruleParameter = ruleParameter;
	}

	/**
	 * @return the connectionType
	 */
	public String getConnectionType() {
		return connectionType;
	}

	/**
	 * @param connectionType the connectionType to set
	 */
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * @return the compensationType
	 */
	public String getCompensationType() {
		return compensationType;
	}

	/**
	 * @param compensationType the compensationType to set
	 */
	public void setCompensationType(String compensationType) {
		this.compensationType = compensationType;
	}

	/**
	 * @return the fixedCompValue
	 */
	public int getFixedCompValue() {
		return fixedCompValue;
	}

	/**
	 * @param fixedCompValue the fixedCompValue to set
	 */
	public void setFixedCompValue(int fixedCompValue) {
		this.fixedCompValue = fixedCompValue;
	}

	/**
	 * @return the compensationFormula
	 */
	public String getCompensationFormula() {
		return compensationFormula;
	}

	/**
	 * @param compensationFormula the compensationFormula to set
	 */
	public void setCompensationFormula(String compensationFormula) {
		this.compensationFormula = compensationFormula;
	}

	/**
	 * @return the compensationParameter
	 */
	public String getCompensationParameter() {
		return compensationParameter;
	}

	/**
	 * @param compensationParameter the compensationParameter to set
	 */
	public void setCompensationParameter(String compensationParameter) {
		this.compensationParameter = compensationParameter;
	}

	/**
	 * @return the compJoinRule
	 */
	public List<Rule> getCompJoinRule() {
		return compJoinRule;
	}

	/**
	 * @param compJoinRule the compJoinRule to set
	 */
	public void setCompJoinRule(List<Rule> compJoinRule) {
		this.compJoinRule = compJoinRule;
	}
}
