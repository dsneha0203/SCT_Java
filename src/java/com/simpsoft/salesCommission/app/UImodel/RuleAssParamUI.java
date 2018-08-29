package com.simpsoft.salesCommission.app.UImodel;

public class RuleAssParamUI {
	
	private int id;
	private String parameterName;
	private String overwriteValue;
	
	
	
	public RuleAssParamUI() {
		
	}
	
	
	public RuleAssParamUI(int id, String parameterName, String overwriteValue) {
		this.id = id;
		this.parameterName = parameterName;
		this.overwriteValue = overwriteValue;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getOverwriteValue() {
		return overwriteValue;
	}
	public void setOverwriteValue(String overwriteValue) {
		this.overwriteValue = overwriteValue;
	}
	
	
	
}
