package com.simpsoft.salesCommission.app.UImodel;

public class TargetUI {
	
	private String targetName;
	private String frequency;
	private String startDate;
	private String terminationDate;
	private int value;
	
	



public TargetUI(){
		
	}


	public TargetUI(String targetName, String startDate, String terminationDate,String frequency, String roleName, int value){
		
		this.targetName = targetName;
		this.frequency=frequency;
		this.startDate = startDate;
		this.terminationDate = terminationDate;
		this.value=value;
	
	
	}
	
	
	
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	
}
