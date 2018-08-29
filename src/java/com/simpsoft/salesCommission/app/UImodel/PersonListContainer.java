package com.simpsoft.salesCommission.app.UImodel;

import java.util.LinkedList;
import java.util.List;

import com.simpsoft.salesCommission.app.model.RuleParameter;
 
public class PersonListContainer {
 
    private List<RuleParameter> personList1 = new LinkedList<RuleParameter>();
    
 
    public PersonListContainer() {
    }
 
    public PersonListContainer(List<RuleParameter> personList1) {
        this.personList1 = personList1;
    }
 
    public List<RuleParameter> getPersonList1() {
        return personList1;
    }
 
    public void setPersonList1(List<RuleParameter> personList1) {
        this.personList1 = personList1;
    }
}