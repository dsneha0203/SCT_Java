package com.simpsoft.salesCommission.app.UImodel;

public class AssignmentUI {

	private	long id;
	private String name;
	private String empName;
	private	long assid;
	
	public long getAssid() {
		return assid;
	}
	public void setAssid(long assid) {
		this.assid = assid;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AssignmentUI(){
		
	}
	
public AssignmentUI(long id,long assid, String name,String empName){
		
	this.id=id;
	this.assid=assid;
	this.name=name;
	this.empName=empName;
	
	}
	

}
