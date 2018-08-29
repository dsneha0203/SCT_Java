package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class PersonListContainerBenef {
	 private List<SplitRuleBeneficiaryUI> personList1 = new LinkedList<SplitRuleBeneficiaryUI>();
	   
	    public PersonListContainerBenef() {
	    }
	 
	    public PersonListContainerBenef(List<SplitRuleBeneficiaryUI> personList1) {
	        this.personList1 = personList1;
	    }
	 
	    public List<SplitRuleBeneficiaryUI> getPersonListBenef() {
	        return personList1;
	    }
	 
	    public void setPersonListBenef(List<SplitRuleBeneficiaryUI> personList1) {
	        this.personList1 = personList1;
	    }
}
