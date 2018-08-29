package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class RuleListParamContainer {
	 private List<RuleAssParamUI> ruleList = new LinkedList<RuleAssParamUI>();
	    public RuleListParamContainer() {
	    }
	 
	    public RuleListParamContainer(List<RuleAssParamUI> ruleList) {
	        this.ruleList = ruleList;
	    }
	 
	    public List<RuleAssParamUI> getRuleList() {
	        return ruleList;
	    }
	 
	    public void setRuleList(List<RuleAssParamUI> ruleList) {
	        this.ruleList = ruleList;
	    }  
}
