package com.simpsoft.salesCommission.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "employeeName")
	private String employeeName;
	
	@Column(name = "salary")
	private int salary;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "terminationDate")
	private Date terminationDate;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "EMP_ID")
	@IndexColumn(name = "detailSrl")
	private List<EmployeeManagerMap> employeeManagerMap;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "EMP_ID")
	@IndexColumn(name = "detailSrl")
	private List<EmployeeRoleMap> employeeRoleMap;

	@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "EMP_ID")
	@IndexColumn(name = "detailSrl")
	private List<Target> target;
	
	/*@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "EMP_ID")
	
	private List<CalculationSimple> calcSimpleList;*/
	
	@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinColumn(name = "EMP_ID")
	@IndexColumn(name = "detailSrl")
	private List<CalculationComposite> calcCompositeList;
	
	public Employee() {
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
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName
	 *            the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	/**
	 * @return the salary
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * @param salary 
	 * 			the salary to set
	 */
	public void setSalary(int salary) {
		this.salary = salary;
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
	 * @return the employeeManagerMap
	 */
	public List<EmployeeManagerMap> getEmployeeManagerMap() {
		return employeeManagerMap;
	}

	/**
	 * @param employeeManagerMap the employeeManagerMap to set
	 */
	public void setEmployeeManagerMap(List<EmployeeManagerMap> employeeManagerMap) {
		this.employeeManagerMap = employeeManagerMap;
	}

	/**
	 * @return the employeeRoleMap
	 */
	public List<EmployeeRoleMap> getEmployeeRoleMap() {
		return employeeRoleMap;
	}

	/**
	 * @param employeeRoleMap the employeeRoleMap to set
	 */
	public void setEmployeeRoleMap(List<EmployeeRoleMap> employeeRoleMap) {
		this.employeeRoleMap = employeeRoleMap;
	}

	/**
	 * @return the target
	 */
	public List<Target> getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(List<Target> target) {
		this.target = target;
	}

	

	
	/*public List<CalculationSimple> getCalcSimpleList() {
		return calcSimpleList;
	}

	public void setCalcSimpleList(List<CalculationSimple> calcSimpleList) {
		this.calcSimpleList = calcSimpleList;
	}*/

	
	public List<CalculationComposite> getCalcCompositeList() {
		return calcCompositeList;
	}

	public void setCalcCompositeList(List<CalculationComposite> calcCompositeList) {
		this.calcCompositeList = calcCompositeList;
	}

	public Employee( String empName, int salary) {
        // this.id = ID;
         this.employeeName = empName;
        
    }

    @Override
    public String toString() {
         return "<" + employeeName + ", "  
                                  + startDate +", "  
                                          + terminationDate + ">";
    }
}