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
@Table(name = "CalculationRoster")
public class CalculationRoster {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CALC_ROSTER_ID")
	@IndexColumn(name = "detailSrl")
	private List<CalculationSimple> calcSimpleList;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CALC_ROSTER_ID")
	@IndexColumn(name = "detailSrl")
	private List<CalculationComposite> calcCompositeList;

	public CalculationRoster() {
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


	public List<CalculationSimple> getCalcSimpleList() {
		return calcSimpleList;
	}


	public void setCalcSimpleList(List<CalculationSimple> calcSimpleList) {
		this.calcSimpleList = calcSimpleList;
	}


	public List<CalculationComposite> getCalcCompositeList() {
		return calcCompositeList;
	}


	public void setCalcCompositeList(List<CalculationComposite> calcCompositeList) {
		this.calcCompositeList = calcCompositeList;
	}
	
	
}
