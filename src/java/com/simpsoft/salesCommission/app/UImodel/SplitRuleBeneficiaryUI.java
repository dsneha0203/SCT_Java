package com.simpsoft.salesCommission.app.UImodel;

public class SplitRuleBeneficiaryUI {
	private String beneficiaryType;
	private int splitPercentage;
	
	
	public String getBeneficiaryType() {
		return beneficiaryType;
	}
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}
	public int getSplitPercentage() {
		return splitPercentage;
	}
	public void setSplitPercentage(int splitPercentage) {
		this.splitPercentage = splitPercentage;
	}
	
	public SplitRuleBeneficiaryUI(){
		
	}
	public SplitRuleBeneficiaryUI(String beneficiaryType, int splitPercentage) {
		
		this.beneficiaryType = beneficiaryType;
		this.splitPercentage = splitPercentage;
	}
	
	
	
}
