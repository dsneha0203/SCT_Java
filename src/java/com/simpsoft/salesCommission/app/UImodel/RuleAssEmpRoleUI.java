package com.simpsoft.salesCommission.app.UImodel;

public class RuleAssEmpRoleUI {
	private String empName;
	private long empId;
	
	
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	private String roleName;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	RuleAssEmpRoleUI(){
		
	}

RuleAssEmpRoleUI(String empName, String roleName){
	this.empName=empName;
	this.roleName=roleName;
		
	}
public RuleAssEmpRoleUI(long empId, String roleName) {
	super();
	this.empId = empId;
	this.roleName = roleName;
}

}
