package com.simpsoft.salesCommission.app.UImodel;

import java.util.List;

public class ParameterUI {
	
	private String parameterName;
	   private String parameterValue;
	
	   public String getParameterName() {
		return parameterName;
	}
	   /**
	    * 
	    * @param parameterName
	    */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	/**
	 * 
	 * @return
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * 
	 * @param parameterValue
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	
	public ParameterUI(){		
	}
	
	public ParameterUI(String parameterName, String parameterValue){
		this.parameterName=parameterName;
		this.parameterValue=parameterValue;
		
	}
	
}
