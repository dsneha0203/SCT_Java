package com.simpsoft.salesCommission.app.UImodel;

public class RoleUI {
	
	private String roleName;
	private String description;
	private String reportsTo;
	private String selectRole;
	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RoleUI(){		
	}
	
	public RoleUI(String roleName, String description, String reportsTo, String selectRole){
		this.roleName=roleName;
		this.description=description;
		this.reportsTo=reportsTo;
		this.selectRole=selectRole;
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getSelectRole() {
		return selectRole;
	}

	public void setSelectRole(String selectRole) {
		this.selectRole = selectRole;
	}
}
