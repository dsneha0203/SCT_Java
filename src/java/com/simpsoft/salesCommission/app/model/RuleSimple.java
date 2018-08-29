package com.simpsoft.salesCommission.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.Access;
import javax.persistence.AccessType;


@Entity
@Table(name = "RuleSimple")
@Access(value=AccessType.FIELD)
public class RuleSimple {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "calculationMode")
	private String calculationMode;

	@Column(name = "rankCount")
	private int rankCount;

	@Column(name = "rankingType")
	private String rankingType;

	@Column(name = "populationType")
	private String populationType;

	@Column(name = "populationUpto")
	private int populationUpto;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "AGGT_FUNC_ID")
	private AggregateFunctions aggregateFunctions;

	@Column(name = "field")
	private String field;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "RULE_SIMP_ID")
	@IndexColumn(name = "detailSrl")
	private List<QualifyingClause> qualifyingClause;
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
	 * @return the calculationMode
	 */
	public String getCalculationMode() {
		return calculationMode;
	}

	/**
	 * @param calculationMode
	 *            the calculationMode to set
	 */
	public void setCalculationMode(String calculationMode) {
		this.calculationMode = calculationMode;
	}

	/**
	 * @return the rankCount
	 */
	public int getRankCount() {
		return rankCount;
	}

	/**
	 * @param rankCount
	 *            the rankCount to set
	 */
	public void setRankCount(int rankCount) {
		this.rankCount = rankCount;
	}

	/**
	 * @return the rankingType
	 */
	public String getRankingType() {
		return rankingType;
	}

	/**
	 * @param rankingType
	 *            the rankingType to set
	 */
	public void setRankingType(String rankingType) {
		this.rankingType = rankingType;
	}

	/**
	 * @return the populationType
	 */
	public String getPopulationType() {
		return populationType;
	}

	/**
	 * @param populationType
	 *            the populationType to set
	 */
	public void setPopulationType(String populationType) {
		this.populationType = populationType;
	}

	/**
	 * @return the populationUpto
	 */
	public int getPopulationUpto() {
		return populationUpto;
	}

	/**
	 * @param populationUpto
	 *            the populationUpto to set
	 */
	public void setPopulationUpto(int populationUpto) {
		this.populationUpto = populationUpto;
	}

	
	/**
	 * @return the aggregateFunctions
	 */
	public AggregateFunctions getAggregateFunctions() {
		return aggregateFunctions;
	}

	/**
	 * @param aggregateFunctions the aggregateFunctions to set
	 */
	public void setAggregateFunctions(AggregateFunctions aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the qualifyingClause
	 */
	public List<QualifyingClause> getQualifyingClause() {
		return qualifyingClause;
	}

	/**
	 * @param qualifyingClause
	 *            the qualifyingClause to set
	 */
	public void setQualifyingClause(List<QualifyingClause> qualifyingClause) {
		this.qualifyingClause = qualifyingClause;
	}
	
	/**
	 * 
	 * @param qualifyingClause
	 */
	public void clearQualifyingClause(List<QualifyingClause> qualifyingClause) {
			this.qualifyingClause.removeAll(qualifyingClause);
		}
	/**
	 * 
	 * @param qualifyingClause
	 */
		public void addQualifyingClause(List<QualifyingClause> qualifyingClause) {
		    this.qualifyingClause.addAll(qualifyingClause);
		}
	
}