package com.simpsoft.salesCommission.app.unRelatedImp;

public class Rules {
	String id;
	String details;
	String parameter;
	String value;
	String frequency;
	String overwrite;

	public String getOverwrite() {
		return overwrite;
	}
	public void setOverwrite(String overwrite) {
		this.overwrite = overwrite;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Rules(){}
	public Rules(String id,String details,String parameter, String value,String frequency,String overwrite){
		this.id=id;
		this.details=details;
		this.value=value;
		this.parameter=parameter;
		this.frequency=frequency;
		
	}
	
}
