package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class SplitRuleListContainer {
	
	 private List<SplitRuleUI> splitRuleList = new LinkedList<SplitRuleUI>();
	   
	    public SplitRuleListContainer() {
	    }
	 
	    public SplitRuleListContainer(List<SplitRuleUI> splitRuleList) {
	        this.setSplitRuleList(splitRuleList);
	    }

		public List<SplitRuleUI> getSplitRuletList() {
			return splitRuleList;
		}

		public void setSplitRuleList(List<SplitRuleUI> splitRuleList) {
			this.splitRuleList = splitRuleList;
		}

}
