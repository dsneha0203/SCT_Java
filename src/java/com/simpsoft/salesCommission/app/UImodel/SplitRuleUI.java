package com.simpsoft.salesCommission.app.UImodel;

import java.util.ArrayList;
import java.util.List;

public class SplitRuleUI {
	private String splitRuleName;
	private String description;
	private String startDate;
	private String terminationDate;
	private List<QualifyingClauseUI> qualifyingClauseUIlist = new ArrayList<QualifyingClauseUI>();
	private List<SplitRuleBeneficiaryUI> benefUIList = new ArrayList<SplitRuleBeneficiaryUI>();
	
	
	public SplitRuleUI() {
		
	}
	
	
	public SplitRuleUI(String splitRuleName, String description, String startDate, String terminationDate,
			List<QualifyingClauseUI> qualifyingClauseUIlist, List<SplitRuleBeneficiaryUI> benefUIList) {
		
		this.splitRuleName = splitRuleName;
		this.description = description;
		this.startDate = startDate;
		this.terminationDate = terminationDate;
		this.qualifyingClauseUIlist = qualifyingClauseUIlist;
		this.benefUIList = benefUIList;
	}


	public String getSplitRuleName() {
		return splitRuleName;
	}
	public void setSplitRuleName(String splitRuleName) {
		this.splitRuleName = splitRuleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public List<QualifyingClauseUI> getQualifyingClauseUIlist() {
		return qualifyingClauseUIlist;
	}

	public void setQualifyingClauseUIlist(List<QualifyingClauseUI> qualifyingClauseUIlist) {
		this.qualifyingClauseUIlist = qualifyingClauseUIlist;
	}

	public List<SplitRuleBeneficiaryUI> getBenefUIList() {
		return benefUIList;
	}

	public void setBenefUIList(List<SplitRuleBeneficiaryUI> benefUIList) {
		this.benefUIList = benefUIList;
	}
	
	
	
}
