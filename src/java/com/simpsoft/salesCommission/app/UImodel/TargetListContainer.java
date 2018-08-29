package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class TargetListContainer {
	
	
	  private List<TargetUI> targetList = new LinkedList<TargetUI>();
	   
	    public TargetListContainer() {
	    }
	 
	    public TargetListContainer(List<TargetUI> targetList) {
	        this.setTargetList(targetList);
	    }

		public List<TargetUI> getTargetList() {
			return targetList;
		}

		public void setTargetList(List<TargetUI> targetList) {
			this.targetList = targetList;
		}
	 
}
