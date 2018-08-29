package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

import com.simpsoft.salesCommission.app.model.QualifyingClause;

public class PersonListContainer1 {

	   private List<QualifyingClauseUI> personList = new LinkedList<QualifyingClauseUI>();
	   
	   
	    public PersonListContainer1() {
	    }
	 
	    public PersonListContainer1(List<QualifyingClauseUI> personList) {
	        this.personList = personList;
	    }
	    
	   
	 
	    public List<QualifyingClauseUI> getPersonList() {
	        return personList;
	    }
	    
	    
	 
	    public void setPersonList(List<QualifyingClauseUI> personList) {
	        this.personList = personList;
	    }
	
	    
	
}
