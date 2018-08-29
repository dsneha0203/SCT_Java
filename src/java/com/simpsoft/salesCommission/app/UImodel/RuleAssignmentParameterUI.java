package com.simpsoft.salesCommission.app.UImodel;

public class RuleAssignmentParameterUI {
	private String parameterName;
	 private String overwriteValue;
	 private String defaultValue;
	 
	 
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
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	

	 RuleAssignmentParameterUI(){
		  
	 }
	 
	 RuleAssignmentParameterUI(String parameterName,String overwriteValue, String defaultValue){
	  this.parameterName=parameterName;
	  this.overwriteValue=overwriteValue;
	  this.defaultValue=defaultValue;
	  
	 }



}
