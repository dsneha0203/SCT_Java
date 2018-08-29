package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;



public class RuleListContainer {
	
	 private List<RuleAssUI> ruleList = new LinkedList<RuleAssUI>();
	    public RuleListContainer() {
	    }
	 
	    public RuleListContainer(List<RuleAssUI> ruleList) {
	        this.ruleList = ruleList;
	    }
	 
	    public List<RuleAssUI> getRuleList() {
	        return ruleList;
	    }
	 
	    public void setRuleList(List<RuleAssUI> ruleList) {
	        this.ruleList = ruleList;
	    }  
	    
	    
}
