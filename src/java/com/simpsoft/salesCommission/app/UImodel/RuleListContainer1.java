package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class RuleListContainer1 {
	
	 private List<RuleAss1UI> ruleList1 = new LinkedList<RuleAss1UI>();
	    public RuleListContainer1() {
	    }
	 
	    public RuleListContainer1(List<RuleAss1UI> ruleList1) {
	        this.ruleList1 = ruleList1;
	    }
	 
	    public List<RuleAss1UI> getRuleList1() {
	        return ruleList1;
	    }
	 
	    public void setRuleList(List<RuleAss1UI> ruleList1) {
	        this.ruleList1 = ruleList1;
	    }  
	    

}
