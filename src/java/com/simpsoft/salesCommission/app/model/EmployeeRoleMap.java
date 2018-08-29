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
@Table(name = "EmployeeRoleMap")
public class EmployeeRoleMap {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;
	
	

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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
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
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param terminationDate
	 *            the terminationDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
