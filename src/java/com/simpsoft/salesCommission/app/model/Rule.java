package com.simpsoft.salesCommission.app.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Rule")
@Access(value=AccessType.FIELD)
public class Rule {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "ruleName")
	private String ruleName;

	@Column(name = "description")
	private String description;

	@Column(name = "ruleType")
	private String ruleType;

	@Column(name = "ruleDetails")
	private String ruleDetails;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "RULE_SIMP_ID")
	private RuleSimple ruleSimple;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "RULE_COMP_ID")
	private RuleComposite ruleComposite;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "RULE_ID")
	@IndexColumn(name = "detailSrl")
	private List<RuleParameter> ruleParameter;
	
	@Column(name = "connectionType")
	private String connectionType;

	@Column(name = "compensationType")
	private String compensationType;

	@Column(name = "fixedCompValue")
	private int fixedCompValue;

	@Column(name = "compensationFormula")
	private String compensationFormula;

	@Column(name = "compensationParameter")
	private String compensationParameter;
	
	/*@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "RULE_ID")
	
	private List<CalculationSimple> calcSimpleList;*/
	
	
	public Rule() {
	}

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
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName
	 *            the ruleName to set
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
	 * @param description
	 *            the description to set
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
	 * @param ruleType
	 *            the ruleType to set
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
	 * @param ruleDetails
	 *            the ruleDetails to set
	 */
	public void setRuleDetails(String ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	/**
	 * @return the ruleSimple
	 */
	public RuleSimple getRuleSimple() {
		return ruleSimple;
	}

	/**
	 * @param ruleSimple
	 *            the ruleSimple to set
	 */
	public void setRuleSimple(RuleSimple ruleSimple) {
		this.ruleSimple = ruleSimple;
	}

	/**
	 * @return the ruleComposite
	 */
	public RuleComposite getRuleComposite() {
		return ruleComposite;
	}

	/**
	 * @param ruleComposite
	 *            the ruleComposite to set
	 */
	public void setRuleComposite(RuleComposite ruleComposite) {
		this.ruleComposite = ruleComposite;
	}
	
	


	/**
	 * @return the ruleParameter
	 */
	public List<RuleParameter> getRuleParameter() {
		return ruleParameter;
	}

	/**
	 * @param ruleParameter
	 *            the ruleParameter to set
	 */
	public void setRuleParameter(List<RuleParameter> ruleParameter) {
		this.ruleParameter = ruleParameter;
	}
	
	
	
	public void clearRuleParameter(List<RuleParameter> ruleParameter) {
	   // this.ruleParameter.clear();
		this.ruleParameter.removeAll(ruleParameter);
	}

	public void addRuleParameter(List<RuleParameter> ruleParameter) {
	    this.ruleParameter.addAll(ruleParameter);
	}
	/**
	 * @return the connectionType
	 */
	public String getConnectionType() {
		return connectionType;
	}

	/**
	 * @param connectionType
	 *            the connectionType to set
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
	 * @param compensationType
	 *            the compensationType to set
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
	 * @param fixedCompValue
	 *            the fixedCompValue to set
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
	 * @param compensationFormula
	 *            the compensationFormula to set
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
	 * @param compensationParameter
	 *            the compensationParameter to set
	 */
	public void setCompensationParameter(String compensationParameter) {
		this.compensationParameter = compensationParameter;
	}
	
	@Override
	    public String toString() {
	         return "<" + ruleName + ", "  
	                                  + description + ", "  
	    	                                  + ruleType + ">";
	    }
	
	
	/*public List<CalculationSimple> getCalcSimpleList() {
		return calcSimpleList;
	}


	public void setCalcSimpleList(List<CalculationSimple> calcSimpleList) {
		this.calcSimpleList = calcSimpleList;
	}*/



}
