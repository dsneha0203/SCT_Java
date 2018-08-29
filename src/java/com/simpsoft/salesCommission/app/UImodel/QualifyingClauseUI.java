package com.simpsoft.salesCommission.app.UImodel;

public class QualifyingClauseUI {

	private String value;
	private int conditionId;
	private String conditionValue;
	private String fieldName;
	private int fieldId;
	private String fn;
	private boolean condition;
	private String aggFuncName;
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public QualifyingClauseUI(){
		
	}
	




public QualifyingClauseUI(String value, int conditionId, String conditionValue, String fieldName, int fieldId,
			String fn, boolean condition) {
		super();
		this.value = value;
		this.conditionId = conditionId;
		this.conditionValue = conditionValue;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
		this.fn = fn;
		this.condition = condition;
	}



public QualifyingClauseUI(String value, int conditionId, String conditionValue, String fieldName, int fieldId,
		String fn, boolean condition, String aggFuncName) {
	super();
	this.value = value;
	this.conditionId = conditionId;
	this.conditionValue = conditionValue;
	this.fieldName = fieldName;
	this.fieldId = fieldId;
	this.fn = fn;
	this.condition = condition;
	this.aggFuncName = aggFuncName;
}

public boolean getCondition() {
	return condition;
}

public void setCondition(boolean condition) {
	this.condition = condition;
}

public int getConditionId() {
	return conditionId;
}

public void setConditionId(int conditionId) {
	this.conditionId = conditionId;
}

public int getFieldId() {
	return fieldId;
}

public void setFieldId(int fieldId) {
	this.fieldId = fieldId;
}

public String getFn() {
	return fn;
}

public void setFn(String fn) {
	this.fn = fn;
}

public String getAggFuncName() {
	return aggFuncName;
}

public void setAggFuncName(String aggFuncName) {
	this.aggFuncName = aggFuncName;
}

public QualifyingClauseUI(String value, String conditionValue, String fieldName, boolean condition,
		String aggFuncName) {
	super();
	this.value = value;
	this.conditionValue = conditionValue;
	this.fieldName = fieldName;
	this.condition = condition;
	this.aggFuncName = aggFuncName;
}




}
