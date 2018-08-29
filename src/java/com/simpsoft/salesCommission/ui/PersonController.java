package com.simpsoft.salesCommission.ui;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.UImodel.Person;
import com.simpsoft.salesCommission.app.UImodel.Person1;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer1;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer2;
import com.simpsoft.salesCommission.app.UImodel.QualifyingClauseUI;
import com.simpsoft.salesCommission.app.model.RuleParameter;


 
@Controller
public class PersonController {
 
	@Autowired
	private RuleAPI ruleApi;
	@Autowired
	private RuleSimpleAPI ruleSimpleApi;
	
    @RequestMapping("/persons")
    public String index1( ModelMap map, HttpSession session,  HttpServletRequest request, String message ) {
 
    //	map.addAttribute("listCompRule1", ruleApi.listRules());
      
        if( session.getAttribute("personListContainer1") == null )
            session.setAttribute("personListContainer1", getDummyPersonListContainer());
        map.addAttribute("personListContainer1", (PersonListContainer1)session.getAttribute("personListContainer1"));
        
        map.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		map.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
        
        return "personList";
    }
 
    @RequestMapping(value="/editpersonlistcontainer", method= RequestMethod.POST)
    public String editpersonListContainer(@ModelAttribute PersonListContainer1 personListContainer1, HttpSession session) {
        for( QualifyingClauseUI p : personListContainer1.getPersonList() ) {
            System.out.println("Value: " + p.getValue());
            System.out.println("ConditionValue: " + p.getConditionValue());
            System.out.println("FieldName: " + p.getFieldName());
            System.out.println("condition: " + p.getCondition());
        }
        session.setAttribute("personListContainer1",personListContainer1);
        return "hello";
    }
 
    private PersonListContainer1 getDummyPersonListContainer() {
        List<QualifyingClauseUI> personList = new ArrayList<QualifyingClauseUI>();
        for( int i=0; i<1; i++ ) {
            personList.add( new QualifyingClauseUI() );
        }
        return new PersonListContainer1();
    }
}