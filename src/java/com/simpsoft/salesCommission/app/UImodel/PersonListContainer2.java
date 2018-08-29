package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

public class PersonListContainer2 {

	   private List<Person1> personList = new LinkedList<Person1>();
	   
	    public PersonListContainer2() {
	    }
	 
	    public PersonListContainer2(List<Person1> personList) {
	        this.personList = personList;
	    }
	 
	    public List<Person1> getPersonList() {
	        return personList;
	    }
	 
	    public void setPersonList(List<Person1> personList) {
	        this.personList = personList;
	    }
		


}
