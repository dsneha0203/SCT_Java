
package com.simpsoft.salesCommission.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;

import com.simpsoft.salesCommission.app.model.RuleSimple;
import com.simpsoft.salesCommission.app.model.RuleType;
import com.simpsoft.salesCommission.app.UImodel.RuleUI;
import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.UImodel.ParameterUI;
import com.simpsoft.salesCommission.app.UImodel.Person1;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer1;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer2;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.UImodel.QualifyingClauseUI;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;

@Controller
public class RuleController {
	@Autowired
	private RuleAPI ruleApi;
	@Autowired
	private RuleSimpleAPI ruleSimpleApi;

	
	private static final Logger logger = Logger.getLogger(RuleController.class);
	@RequestMapping(value = "/simpleRule", method = RequestMethod.GET)
	public String simpleRule(ModelMap model, HttpSession session, HttpServletRequest request, String message) {

			session.setAttribute("personListContainer", getDummyPersonListContainer());
			session.setAttribute("personListContainer1", getDummyPersonListContainer1());
			session.setAttribute("personListContainer2", getDummyPersonListContainer1());
		

		model.addAttribute("listRule1", ruleSimpleApi.listOfAggregateFunctions());
		model.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		model.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
		
		List<FieldList> fieldList = ruleSimpleApi.listOfFields();
		logger.debug("SIZE OF FIELD LIST= "+fieldList.size());
		LinkedHashSet<String> fieldNames = new LinkedHashSet<String>();
		for(FieldList f : fieldList) {
			fieldNames.add(f.getDisplayName());
		}
		ArrayList<String> uniqueFieldNameList = new ArrayList<String>(fieldNames);
		logger.debug("SIZE OF UNIQUE FIELD NAME LIST= "+uniqueFieldNameList.size());
		model.addAttribute("fieldNameList", uniqueFieldNameList);
		
		
		List<ConditionList> condList = ruleSimpleApi.listOfConditions();
		LinkedHashSet<String> condNames = new LinkedHashSet<String>();
		for(ConditionList c : condList) {
			condNames.add(c.getConditionValue());
		}
		ArrayList<String> uniqueCondNameList = new ArrayList<String>(condNames);
		logger.debug("SIZE OF UNIQUE CONDITION NAME LIST= "+uniqueCondNameList.size());
		model.addAttribute("condNameList", uniqueCondNameList);
		
		System.out.println(".......servlet running.......");
		return "simpRuleDetails";
	}

	private PersonListContainer getDummyPersonListContainer() {
		List<RuleParameter> personList1 = new ArrayList<RuleParameter>();
		
			personList1.add(new RuleParameter());
	
		return new PersonListContainer(personList1);
	}


	
	private PersonListContainer1 getDummyPersonListContainer1() {
		List<QualifyingClause> personList = new ArrayList<QualifyingClause>();
	
			personList.add(new QualifyingClause());
	
		return new PersonListContainer1();
	}

	@RequestMapping(value = "/submitSimpRule", method = RequestMethod.POST)
	public String addRule(@ModelAttribute("SpringWeb") RuleUI ruleUI, PersonListContainer personListContainer,
			PersonListContainer1 personListContainer1, HttpSession session, ModelMap model) {
		
		System.out.println("*********************" + ruleUI.getId());
		if (ruleUI.getId() != 0) {
		
		for (RuleParameter R : personListContainer.getPersonList1()) {
			System.out.println("ParameterName: " + R.getParameterName());
			System.out.println("ParameterValue: " + R.getParameterValue());
		}

		for (QualifyingClauseUI p : personListContainer1.getPersonList()) {
			logger.debug("QualifyingClauseValue: " + p.getValue());
			logger.debug("condition: " + p.getCondition());
			logger.debug("ConditionValue: " + p.getConditionValue());
			logger.debug("FieldName: " + p.getFieldName());
			logger.debug("Aggregate function: "+p.getAggFuncName());

		}
		
//		for (QualifyingClauseUI p : personListContainer2.getPersonList()) {
//			logger.debug("QualifyingClauseValue: " + p.getValue());
//			logger.debug("condition: " + p.getCondition());
//			logger.debug("ConditionValue: " + p.getConditionValue());
//			logger.debug("FieldName: " + p.getFieldName());
//			logger.debug("Aggregate function: "+p.getAggFuncName());
//
//		}

		
		model.addAttribute("id", ruleUI.getId());
		model.addAttribute("ruleName", ruleUI.getRuleName());
		System.out.println("***************************" + ruleUI.getRuleName());
		model.addAttribute("description", ruleUI.getDescription());
		System.out.println("***************************" + ruleUI.getDescription());
		model.addAttribute("ruleDetails", ruleUI.getRuleDetails());
		model.addAttribute("ruleType", ruleUI.getRuleType());

		model.addAttribute("rankCount", ruleUI.getRankCount());
		model.addAttribute("rankType", ruleUI.getRankType());
		model.addAttribute("populationType", ruleUI.getPopulationType());
		model.addAttribute("populationUpto", ruleUI.getPopulationUpto());

		model.addAttribute("compensationType", ruleUI.getCompensationType());
		model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
		model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
		model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());
		model.addAttribute("calculationMode", ruleUI.getCalculationMode());
		System.out.println("***************************" + ruleUI.getCalculationMode());
		model.addAttribute("aggregateFunctions", ruleUI.getAggregateFunctions());
		System.out.println("***************************" + ruleUI.getAggregateFunctions());
		model.addAttribute("field", ruleUI.getField());
		System.out.println("***************************" + ruleUI.getField());
		
		long id = ruleUI.getId();
		System.out.println(id);
		Rule rule = ruleApi.getRule(id);

		rule.setRuleName(ruleUI.getRuleName());
		rule.setDescription(ruleUI.getDescription());
		rule.setRuleDetails(ruleUI.getRuleDetails());
		rule.setRuleType(ruleUI.getRuleType());
		rule.setCompensationType(ruleUI.getCompensationType());
		rule.setFixedCompValue(ruleUI.getFixedCompValue());
		rule.setCompensationFormula(ruleUI.getCompensationFormula());
		rule.setCompensationParameter(ruleUI.getCompensationParameter());

		
		List<RuleParameter> rp1 = personListContainer.getPersonList1();
		List<RuleParameter> rp = new ArrayList<>();
		for (Iterator iterator = rp1.iterator(); iterator.hasNext();) {
			RuleParameter rpm = (RuleParameter) iterator.next();
			RuleParameter obj1 = new RuleParameter();
			obj1.setParameterName(rpm.getParameterName());
			obj1.setParameterValue(rpm.getParameterValue());
			rp.add(obj1);
		}
		
		rule.setRuleParameter(rp);
		

		RuleSimple ruleSimple = rule.getRuleSimple();
		List<QualifyingClauseUI> ptr = personListContainer1.getPersonList();
		//List<QualifyingClauseUI> ptr_1 = personListContainer2.getPersonList();
		List<QualifyingClause> ptr1 = new ArrayList<>();
		for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
			QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
			QualifyingClause obj1 = new QualifyingClause();
			if(qcui.getAggFuncName()!=null) {
				AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
				obj1.setAggregateFunctions(aggregateFunctions);
			}
			if(qcui.getAggFuncName().equals("")) {
				obj1.setAggregateFunctions(null);
			}
			

			
			FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
			ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
			

			
			obj1.setConditionList(cnd);
			obj1.setFieldList(fldList);
			obj1.setValue(qcui.getValue());
			obj1.setNotFlag(qcui.getCondition());
			

			
			ptr1.add(obj1);
		}
//		for (Iterator iterator = ptr_1.iterator(); iterator.hasNext();) {
//			QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
//			QualifyingClause obj1 = new QualifyingClause();
//			if(qcui.getAggFuncName()!=null) {
//				AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
//				obj1.setAggregateFunctions(aggregateFunctions);
//			}
//			if(qcui.getAggFuncName().equals("")) {
//				obj1.setAggregateFunctions(null);
//			}
//			
//
//			
//			FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
//			ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
//			
//
//			
//			obj1.setConditionList(cnd);
//			obj1.setFieldList(fldList);
//			obj1.setValue(qcui.getValue());
//			obj1.setNotFlag(qcui.getCondition());
//			
//
//			
//			ptr1.add(obj1);
//		}
		logger.debug("---- QUAL CLAUSE LIST THAT IS ADDED ----");
		for(QualifyingClause clause : ptr1) {
			logger.debug("NOT FLAG= "+ clause.isNotFlag());
			logger.debug("VALUE= "+clause.getValue());
			if(clause.getAggregateFunctions() != null) {
				logger.debug("AGG FUNC NAME= "+clause.getAggregateFunctions().getFunctionName());
				logger.debug("AGG FUNC ID= "+clause.getAggregateFunctions().getId());
			}			
			logger.debug("COND NAME= "+clause.getConditionList().getConditionValue());
			logger.debug("COND ID=" + clause.getConditionList().getId());
			logger.debug("FIELD NAME= "+clause.getFieldList().getDisplayName());
			logger.debug("FIELD ID= "+clause.getFieldList().getId());
		}
		
		AggregateFunctions agFun = ruleSimpleApi.searchAggregateFunction(ruleUI.getAggregateFunctions());

		ruleSimple.setQualifyingClause(ptr1);
		ruleSimple.setRankCount(ruleUI.getRankCount());
		ruleSimple.setRankingType(ruleUI.getRankType());
		ruleSimple.setPopulationType(ruleUI.getPopulationType());
		ruleSimple.setPopulationUpto(ruleUI.getPopulationUpto());
		ruleSimple.setCalculationMode(ruleUI.getCalculationMode());
		ruleSimple.setAggregateFunctions(agFun);
		ruleSimple.setField(ruleUI.getField());

		rule.setRuleSimple(ruleSimple);
		personListContainer1.getPersonList().clear();
	

		ruleApi.editRule(rule);
		}
		
		else{
			
			for (RuleParameter p : personListContainer.getPersonList1()) {
				System.out.println("ParameterName: " + p.getParameterName());
				System.out.println("ParameterValue: " + p.getParameterValue());
			}

			for (QualifyingClauseUI p : personListContainer1.getPersonList()) {
				logger.debug("QualifyingClauseValue: " + p.getValue());
				logger.debug("condition: " + p.getCondition());
				logger.debug("ConditionValue: " + p.getConditionValue());
				logger.debug("FieldName: " + p.getFieldName());
				logger.debug("Aggregate function: "+p.getAggFuncName());

			}
			

			model.addAttribute("id", ruleUI.getId());
			model.addAttribute("ruleName", ruleUI.getRuleName());
			System.out.println("***************************" + ruleUI.getRuleName());
			model.addAttribute("description", ruleUI.getDescription());
			System.out.println("***************************" + ruleUI.getDescription());
			model.addAttribute("ruleDetails", ruleUI.getRuleDetails());
			model.addAttribute("ruleType", ruleUI.getRuleType());

			model.addAttribute("rankCount", ruleUI.getRankCount());
			model.addAttribute("rankType", ruleUI.getRankType());
			model.addAttribute("populationType", ruleUI.getPopulationType());
			model.addAttribute("populationUpto", ruleUI.getPopulationUpto());

			model.addAttribute("compensationType", ruleUI.getCompensationType());
			model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
			model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
			model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());
			model.addAttribute("calculationMode", ruleUI.getCalculationMode());
			System.out.println("***************************" + ruleUI.getCalculationMode());
			model.addAttribute("aggregateFunctions", ruleUI.getAggregateFunctions());
			System.out.println("***************************" + ruleUI.getAggregateFunctions());
			model.addAttribute("field", ruleUI.getField());
			System.out.println("***************************" + ruleUI.getField());

			Rule rule = new Rule();

			rule.setId(ruleUI.getId());
			rule.setRuleName(ruleUI.getRuleName());
			rule.setDescription(ruleUI.getDescription());
			rule.setRuleDetails(ruleUI.getRuleDetails());
			rule.setRuleType(ruleUI.getRuleType());
			rule.setCompensationType(ruleUI.getCompensationType());
			rule.setFixedCompValue(ruleUI.getFixedCompValue());
			rule.setCompensationFormula(ruleUI.getCompensationFormula());
			rule.setCompensationParameter(ruleUI.getCompensationParameter());
			rule.setRuleParameter(personListContainer.getPersonList1());
			

			RuleSimple ruleSimple = new RuleSimple();
			List<QualifyingClauseUI> ptr = personListContainer1.getPersonList();
			List<QualifyingClause> ptr1 = new ArrayList<>();
			for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
				QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
				QualifyingClause obj1 = new QualifyingClause();
				if(qcui.getAggFuncName()!=null) {
					AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
					obj1.setAggregateFunctions(aggregateFunctions);
				}
				if(qcui.getAggFuncName().equals("")) {
					obj1.setAggregateFunctions(null);
				}
				FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
				ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
				obj1.setConditionList(cnd);
				obj1.setFieldList(fldList);
				obj1.setValue(qcui.getValue());
				obj1.setNotFlag(qcui.getCondition());
				// System.out.println(ptr.size());
				ptr1.add(obj1);
			

			
			
			}
			AggregateFunctions agFun = ruleSimpleApi.searchAggregateFunction(ruleUI.getAggregateFunctions());

			ruleSimple.setQualifyingClause(ptr1);
			ruleSimple.setRankCount(ruleUI.getRankCount());
			ruleSimple.setRankingType(ruleUI.getRankType());
			ruleSimple.setPopulationType(ruleUI.getPopulationType());
			ruleSimple.setPopulationUpto(ruleUI.getPopulationUpto());
			ruleSimple.setCalculationMode(ruleUI.getCalculationMode());
			ruleSimple.setAggregateFunctions(agFun);
			ruleSimple.setField(ruleUI.getField());

			rule.setRuleSimple(ruleSimple);
			personListContainer1.getPersonList().clear();

			ruleApi.createRule(rule);
		
		}
		// logger.info("A NEW rule HAS CREATED" + rule);
		return "redirect:/ruleList";
	}
	
	
	@RequestMapping(value = "/updateSimpRule", method = RequestMethod.POST)
	public String updateSimpRule(@ModelAttribute("SpringWeb") RuleUI ruleUI, PersonListContainer personListContainer,
			PersonListContainer1 personListContainer1, HttpSession session, ModelMap model) {
		logger.debug("------------UPDATING---------");	
		System.out.println("*********************" + ruleUI.getId());
		long id = ruleUI.getId();
		if (ruleUI.getId() != 0) {
		
		for (RuleParameter R : personListContainer.getPersonList1()) {
			System.out.println("ParameterName: " + R.getParameterName());
			System.out.println("ParameterValue: " + R.getParameterValue());
		}

		for (QualifyingClauseUI p : personListContainer1.getPersonList()) {
			logger.debug("QualifyingClauseValue: " + p.getValue());
			logger.debug("condition: " + p.getCondition());
			logger.debug("ConditionValue: " + p.getConditionValue());
			logger.debug("FieldName: " + p.getFieldName());
			logger.debug("Aggregate function: "+p.getAggFuncName());

		}
		
//		for (QualifyingClauseUI p : personListContainer2.getPersonList()) {
//			logger.debug("QualifyingClauseValue: " + p.getValue());
//			logger.debug("condition: " + p.getCondition());
//			logger.debug("ConditionValue: " + p.getConditionValue());
//			logger.debug("FieldName: " + p.getFieldName());
//			logger.debug("Aggregate function: "+p.getAggFuncName());
//
//		}

		
		model.addAttribute("id", ruleUI.getId());
		model.addAttribute("ruleName", ruleUI.getRuleName());
		System.out.println("***************************" + ruleUI.getRuleName());
		model.addAttribute("description", ruleUI.getDescription());
		System.out.println("***************************" + ruleUI.getDescription());
		model.addAttribute("ruleDetails", ruleUI.getRuleDetails());
		model.addAttribute("ruleType", ruleUI.getRuleType());

		model.addAttribute("rankCount", ruleUI.getRankCount());
		model.addAttribute("rankType", ruleUI.getRankType());
		model.addAttribute("populationType", ruleUI.getPopulationType());
		model.addAttribute("populationUpto", ruleUI.getPopulationUpto());

		model.addAttribute("compensationType", ruleUI.getCompensationType());
		model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
		model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
		model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());
		model.addAttribute("calculationMode", ruleUI.getCalculationMode());
		System.out.println("***************************" + ruleUI.getCalculationMode());
		model.addAttribute("aggregateFunctions", ruleUI.getAggregateFunctions());
		System.out.println("***************************" + ruleUI.getAggregateFunctions());
		model.addAttribute("field", ruleUI.getField());
		System.out.println("***************************" + ruleUI.getField());
		
		
		System.out.println(id);
		Rule rule = ruleApi.getRule(id);

		rule.setRuleName(ruleUI.getRuleName());
		rule.setDescription(ruleUI.getDescription());
		rule.setRuleDetails(ruleUI.getRuleDetails());
		rule.setRuleType(ruleUI.getRuleType());
		rule.setCompensationType(ruleUI.getCompensationType());
		rule.setFixedCompValue(ruleUI.getFixedCompValue());
		rule.setCompensationFormula(ruleUI.getCompensationFormula());
		rule.setCompensationParameter(ruleUI.getCompensationParameter());

		
		List<RuleParameter> rp1 = personListContainer.getPersonList1();
		List<RuleParameter> rp = new ArrayList<>();
		for (Iterator iterator = rp1.iterator(); iterator.hasNext();) {
			RuleParameter rpm = (RuleParameter) iterator.next();
			RuleParameter obj1 = new RuleParameter();
			obj1.setParameterName(rpm.getParameterName());
			obj1.setParameterValue(rpm.getParameterValue());
			rp.add(obj1);
		}
		
		rule.setRuleParameter(rp);
		

		RuleSimple ruleSimple = rule.getRuleSimple();
		List<QualifyingClauseUI> ptr = personListContainer1.getPersonList();
		//List<QualifyingClauseUI> ptr_1 = personListContainer2.getPersonList();
		List<QualifyingClause> ptr1 = new ArrayList<>();
		for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
			QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
			QualifyingClause obj1 = new QualifyingClause();
			if(qcui.getAggFuncName()!=null) {
				AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
				obj1.setAggregateFunctions(aggregateFunctions);
			}
			if(qcui.getAggFuncName().equals("")) {
				obj1.setAggregateFunctions(null);
			}
			

			
			FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
			ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
			

			
			obj1.setConditionList(cnd);
			obj1.setFieldList(fldList);
			obj1.setValue(qcui.getValue());
			obj1.setNotFlag(qcui.getCondition());
			

			
			ptr1.add(obj1);
		}
//		for (Iterator iterator = ptr_1.iterator(); iterator.hasNext();) {
//			QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
//			QualifyingClause obj1 = new QualifyingClause();
//			if(qcui.getAggFuncName()!=null) {
//				AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
//				obj1.setAggregateFunctions(aggregateFunctions);
//			}
//			if(qcui.getAggFuncName().equals("")) {
//				obj1.setAggregateFunctions(null);
//			}
//			
//
//			
//			FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
//			ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
//			
//
//			
//			obj1.setConditionList(cnd);
//			obj1.setFieldList(fldList);
//			obj1.setValue(qcui.getValue());
//			obj1.setNotFlag(qcui.getCondition());
//			
//
//			
//			ptr1.add(obj1);
//		}
		logger.debug("---- QUAL CLAUSE LIST THAT IS ADDED ----");
		for(QualifyingClause clause : ptr1) {
			logger.debug("NOT FLAG= "+ clause.isNotFlag());
			logger.debug("VALUE= "+clause.getValue());
			if(clause.getAggregateFunctions() != null) {
				logger.debug("AGG FUNC NAME= "+clause.getAggregateFunctions().getFunctionName());
				logger.debug("AGG FUNC ID= "+clause.getAggregateFunctions().getId());
			}			
			logger.debug("COND NAME= "+clause.getConditionList().getConditionValue());
			logger.debug("COND ID=" + clause.getConditionList().getId());
			logger.debug("FIELD NAME= "+clause.getFieldList().getDisplayName());
			logger.debug("FIELD ID= "+clause.getFieldList().getId());
		}
		
		AggregateFunctions agFun = ruleSimpleApi.searchAggregateFunction(ruleUI.getAggregateFunctions());

		ruleSimple.setQualifyingClause(ptr1);
		ruleSimple.setRankCount(ruleUI.getRankCount());
		ruleSimple.setRankingType(ruleUI.getRankType());
		ruleSimple.setPopulationType(ruleUI.getPopulationType());
		ruleSimple.setPopulationUpto(ruleUI.getPopulationUpto());
		ruleSimple.setCalculationMode(ruleUI.getCalculationMode());
		ruleSimple.setAggregateFunctions(agFun);
		ruleSimple.setField(ruleUI.getField());

		rule.setRuleSimple(ruleSimple);
		personListContainer1.getPersonList().clear();
	

		ruleApi.editRule(rule);
		}
		
		else{
			
			for (RuleParameter p : personListContainer.getPersonList1()) {
				System.out.println("ParameterName: " + p.getParameterName());
				System.out.println("ParameterValue: " + p.getParameterValue());
			}

			for (QualifyingClauseUI p : personListContainer1.getPersonList()) {
				logger.debug("QualifyingClauseValue: " + p.getValue());
				logger.debug("condition: " + p.getCondition());
				logger.debug("ConditionValue: " + p.getConditionValue());
				logger.debug("FieldName: " + p.getFieldName());
				logger.debug("Aggregate function: "+p.getAggFuncName());

			}
			

			model.addAttribute("id", ruleUI.getId());
			model.addAttribute("ruleName", ruleUI.getRuleName());
			System.out.println("***************************" + ruleUI.getRuleName());
			model.addAttribute("description", ruleUI.getDescription());
			System.out.println("***************************" + ruleUI.getDescription());
			model.addAttribute("ruleDetails", ruleUI.getRuleDetails());
			model.addAttribute("ruleType", ruleUI.getRuleType());

			model.addAttribute("rankCount", ruleUI.getRankCount());
			model.addAttribute("rankType", ruleUI.getRankType());
			model.addAttribute("populationType", ruleUI.getPopulationType());
			model.addAttribute("populationUpto", ruleUI.getPopulationUpto());

			model.addAttribute("compensationType", ruleUI.getCompensationType());
			model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
			model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
			model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());
			model.addAttribute("calculationMode", ruleUI.getCalculationMode());
			System.out.println("***************************" + ruleUI.getCalculationMode());
			model.addAttribute("aggregateFunctions", ruleUI.getAggregateFunctions());
			System.out.println("***************************" + ruleUI.getAggregateFunctions());
			model.addAttribute("field", ruleUI.getField());
			System.out.println("***************************" + ruleUI.getField());

			Rule rule = new Rule();

			rule.setId(ruleUI.getId());
			rule.setRuleName(ruleUI.getRuleName());
			rule.setDescription(ruleUI.getDescription());
			rule.setRuleDetails(ruleUI.getRuleDetails());
			rule.setRuleType(ruleUI.getRuleType());
			rule.setCompensationType(ruleUI.getCompensationType());
			rule.setFixedCompValue(ruleUI.getFixedCompValue());
			rule.setCompensationFormula(ruleUI.getCompensationFormula());
			rule.setCompensationParameter(ruleUI.getCompensationParameter());
			rule.setRuleParameter(personListContainer.getPersonList1());
			

			RuleSimple ruleSimple = new RuleSimple();
			List<QualifyingClauseUI> ptr = personListContainer1.getPersonList();
			List<QualifyingClause> ptr1 = new ArrayList<>();
			for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
				QualifyingClauseUI qcui = (QualifyingClauseUI) iterator.next();
				QualifyingClause obj1 = new QualifyingClause();
				if(qcui.getAggFuncName()!=null) {
					AggregateFunctions aggregateFunctions = ruleSimpleApi.searchAggregateFunction(qcui.getAggFuncName());
					obj1.setAggregateFunctions(aggregateFunctions);
				}
				if(qcui.getAggFuncName().equals("")) {
					obj1.setAggregateFunctions(null);
				}
				FieldList fldList = ruleSimpleApi.searchFieldList(qcui.getFieldName());
				ConditionList cnd = ruleSimpleApi.searchCondition(qcui.getConditionValue());
				obj1.setConditionList(cnd);
				obj1.setFieldList(fldList);
				obj1.setValue(qcui.getValue());
				obj1.setNotFlag(qcui.getCondition());
				// System.out.println(ptr.size());
				ptr1.add(obj1);
			

			
			
			}
			AggregateFunctions agFun = ruleSimpleApi.searchAggregateFunction(ruleUI.getAggregateFunctions());

			ruleSimple.setQualifyingClause(ptr1);
			ruleSimple.setRankCount(ruleUI.getRankCount());
			ruleSimple.setRankingType(ruleUI.getRankType());
			ruleSimple.setPopulationType(ruleUI.getPopulationType());
			ruleSimple.setPopulationUpto(ruleUI.getPopulationUpto());
			ruleSimple.setCalculationMode(ruleUI.getCalculationMode());
			ruleSimple.setAggregateFunctions(agFun);
			ruleSimple.setField(ruleUI.getField());

			rule.setRuleSimple(ruleSimple);
			personListContainer1.getPersonList().clear();

			ruleApi.createRule(rule);
		
		}
		// logger.info("A NEW rule HAS CREATED" + rule);
		return "redirect:/editSimple/"+id;
	}


	@RequestMapping(value = "/editSimple/{id}", method = RequestMethod.GET)
	public String EditSimpRule(@PathVariable("id") int id, ModelMap model, HttpSession session,
			HttpServletRequest request, String message) {
		model.addAttribute("listRule1", ruleSimpleApi.listOfAggregateFunctions());
		model.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		model.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
		
		List<FieldList> fieldList = ruleSimpleApi.listOfFields();
		logger.debug("SIZE OF FIELD LIST= "+fieldList.size());
		LinkedHashSet<String> fieldNames = new LinkedHashSet<String>();
		for(FieldList f : fieldList) {
			fieldNames.add(f.getDisplayName());
		}
		ArrayList<String> uniqueFieldNameList = new ArrayList<String>(fieldNames);
		logger.debug("SIZE OF UNIQUE FIELD NAME LIST= "+uniqueFieldNameList.size());
		model.addAttribute("fieldNameList", uniqueFieldNameList);
		
		
		List<ConditionList> condList = ruleSimpleApi.listOfConditions();
		LinkedHashSet<String> condNames = new LinkedHashSet<String>();
		for(ConditionList c : condList) {
			condNames.add(c.getConditionValue());
		}
		ArrayList<String> uniqueCondNameList = new ArrayList<String>(condNames);
		logger.debug("SIZE OF UNIQUE CONDITION NAME LIST= "+uniqueCondNameList.size());
		model.addAttribute("condNameList", uniqueCondNameList);
		
		
		Rule qRule = ruleApi.getRule(id);
		model.addAttribute("listRule4", qRule);
		RuleSimple sRule = qRule.getRuleSimple();
		
		List<QualifyingClause> qList = sRule.getQualifyingClause();
		Set<QualifyingClause> uniqueList = new HashSet<>(qList);
		List<QualifyingClause> uniqueQualList = new ArrayList<>(uniqueList);
		
		
		List<QualifyingClause> qualiCListNonAgg = new ArrayList<QualifyingClause>();
		List<QualifyingClause> qualiCList = new ArrayList<QualifyingClause>();
		for (Iterator iterator = uniqueQualList.iterator(); iterator.hasNext();) {
			QualifyingClause qcui = (QualifyingClause) iterator.next();
		      System.out.println(qcui.getValue());
		      System.out.println(qcui.isNotFlag());
		      if(qcui.getAggregateFunctions() == null || qcui.getAggregateFunctions().getFunctionName().equals("") ) {
		    	  qualiCListNonAgg.add(qcui);
		      }else {
		    	  qualiCList.add(qcui);
		      }
		 
		}
		model.addAttribute("qualifyingListNonAgg",qualiCListNonAgg);
		model.addAttribute("qualifyingList",qualiCList);
		
		
		
		Rule rule1 = ruleApi.getRule(id);
		List<RuleParameter> ptr = rule1.getRuleParameter();
		List<RuleParameter> parameterList = new ArrayList<RuleParameter>();
		Iterator it = ptr.iterator();	 
	    while(it.hasNext()) {
	    	RuleParameter rp = (RuleParameter)it.next();
	      System.out.println(rp.getParameterName());
	      System.out.println(rp.getParameterValue());
	      parameterList.add(rp);
	    }
	  model.addAttribute("parList",parameterList);

	  if (session.getAttribute("personListContainer") == null)
			session.setAttribute("personListContainer", getDummyPersonListContainer());
		model.addAttribute("personListContainer", (PersonListContainer) session.getAttribute("personListContainer"));

		session.setAttribute("personListContainer1", getDummyPersonListContainer1());
		model.addAttribute("personListContainer1", (PersonListContainer1) session.getAttribute("personListContainer1"));
		
		session.setAttribute("personListContainer2", getDummyPersonListContainer1());
		model.addAttribute("personListContainer2", (PersonListContainer1) session.getAttribute("personListContainer2"));
		
		return "EditSimple";
	}

	@RequestMapping(value = "/compositeRule", method = RequestMethod.GET)
	public String compRule(ModelMap map, HttpSession session, HttpServletRequest request, String message) {
		map.addAttribute("listSimpRule", ruleApi.listOfRules(RuleType.Simple));

			//session.setAttribute("personListContainer", getDummyPersonListContainer3());
			session.setAttribute("personListContainer2", getDummyPersonListContainer2());

		return "ruleDetails";
	}

	private PersonListContainer getDummyPersonListContainer3() {
		List<RuleParameter> personList1 = new ArrayList<RuleParameter>();
			personList1.add(new RuleParameter());
		
		return new PersonListContainer(personList1);
	}

	private PersonListContainer2 getDummyPersonListContainer2() {
		List<Person1> personList = new ArrayList<Person1>();
			personList.add(new Person1());
		return new PersonListContainer2();
	}

	@RequestMapping(value = "/submitCompRule", method = RequestMethod.POST)
	public String addRule2(ModelMap map,@ModelAttribute("SpringWeb") RuleUI ruleUI, PersonListContainer personListContainer, PersonListContainer2 personListContainer2,
			HttpSession session, ModelMap model) {
		
		if(ruleUI.getId() != 0){
		
		for (Person1 p : personListContainer2.getPersonList()) {
			System.out.println("listOfSimpRule: " + p.getSimpRule());
		}
		for (RuleParameter p : personListContainer.getPersonList1()) {
			System.out.println("ParameterName: " + p.getParameterName());
			System.out.println("ParameterValue: " + p.getParameterValue());
		}
		model.addAttribute("id", ruleUI.getId());
		model.addAttribute("ruleName", ruleUI.getRuleName());
		model.addAttribute("description", ruleUI.getDescription());
		model.addAttribute("ruleType", ruleUI.getRuleType());		
		model.addAttribute("connectionType", ruleUI.getConnectionType());
		logger.debug("CONNECTION TYPE= "+ ruleUI.getConnectionType());
		model.addAttribute("compensationType", ruleUI.getCompensationType());
		model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
		model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
		model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());
		long id = ruleUI.getId();
		Rule rule = ruleApi.getRule(id);
		//RuleSimple ruleSimple = new RuleSimple();
		List<Person1> ptr = personListContainer2.getPersonList();
		if(ptr.isEmpty()) {
			JOptionPane.showMessageDialog(null, 
                    "Select a simple rule!", 
                    "Cannot save composite rule", 
                    JOptionPane.WARNING_MESSAGE);
			return "redirect:/editComposite/"+ruleUI.getId();
		}else {
				List<Rule> ruleObj = new ArrayList<Rule>();
				for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
					Person1 person1 = (Person1) iterator.next();
					RuleComposite ruleComp = new RuleComposite();
					Rule  rulSimple = ruleApi.searchRuleByName(person1.getSimpRule());
					if(!ruleObj.isEmpty()) {
						for(Rule rule2 : ruleObj) {
							if(rule2.getRuleName().equals(rulSimple.getRuleName())) {
								JOptionPane.showMessageDialog(null, 
					                    "Cannot save the same simple rule multiple times!", 
					                    "Cannot save composite rule", 
					                    JOptionPane.WARNING_MESSAGE);
								

								return "redirect:/editComposite/"+ruleUI.getId();
							}
						}
						ruleObj.add(rulSimple);
						ruleComp.setCompJoinRule(ruleObj);
						rule.setRuleComposite(ruleComp);
						
					}else {
						ruleObj.add(rulSimple);
						ruleComp.setCompJoinRule(ruleObj);
						rule.setRuleComposite(ruleComp);
					}
					
				}
		
				rule.setId(ruleUI.getId());
				rule.setRuleName(ruleUI.getRuleName());
				rule.setDescription(ruleUI.getDescription());
				rule.setRuleType(ruleUI.getRuleType());
				rule.setConnectionType(ruleUI.getConnectionType());
				rule.setCompensationType(ruleUI.getCompensationType());
				rule.setFixedCompValue(ruleUI.getFixedCompValue());
				rule.setCompensationFormula(ruleUI.getCompensationFormula());
				rule.setCompensationParameter(ruleUI.getCompensationParameter());
				rule.setRuleParameter(personListContainer.getPersonList1());
		
				ruleApi.editRule(rule);
			}
		}
		
		else{
			
			for (Person1 p : personListContainer2.getPersonList()) {
				System.out.println("listOfSimpRule: " + p.getSimpRule());
			}
			session.setAttribute("personListContainer2", personListContainer2);
			for (RuleParameter p : personListContainer.getPersonList1()) {
				System.out.println("ParameterName: " + p.getParameterName());
				System.out.println("ParameterValue: " + p.getParameterValue());
			}
			session.setAttribute("personListContainer3", personListContainer);

			model.addAttribute("id", ruleUI.getId());
			model.addAttribute("ruleName", ruleUI.getRuleName());
			model.addAttribute("description", ruleUI.getDescription());
			model.addAttribute("ruleType", ruleUI.getRuleType());		
			model.addAttribute("connectionType", ruleUI.getConnectionType());
			model.addAttribute("compensationType", ruleUI.getCompensationType());
			model.addAttribute("fixedCompValue", ruleUI.getFixedCompValue());
			model.addAttribute("compensationFormula", ruleUI.getCompensationFormula());
			model.addAttribute("compensationParameter", ruleUI.getCompensationParameter());

			Rule rule = new Rule();
			//RuleSimple ruleSimple = new RuleSimple();
			List<Person1> ptr = personListContainer2.getPersonList();
			
			if(ptr.isEmpty()) {
				JOptionPane.showMessageDialog(null, 
	                    "Select a simple rule!", 
	                    "Cannot save composite rule", 
	                    JOptionPane.WARNING_MESSAGE);
				map.addAttribute("listSimpRule", ruleApi.listOfRules(RuleType.Simple));
				session.setAttribute("personListContainer2", getDummyPersonListContainer2());

			return "ruleDetails";
			}else {
				List<Rule> ruleObj = new ArrayList<Rule>();
				for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
					Person1 person1 = (Person1) iterator.next();
					RuleComposite ruleComp = new RuleComposite();
					Rule  rulSimple = ruleApi.searchRuleByName(person1.getSimpRule());
					if(!ruleObj.isEmpty()) {
						for(Rule rule2 : ruleObj) {
							if(rule2.getRuleName().equals(rulSimple.getRuleName())) {
								JOptionPane.showMessageDialog(null, 
					                    "Cannot save the same simple rule multiple times!", 
					                    "Cannot save composite rule", 
					                    JOptionPane.WARNING_MESSAGE);
								

								map.addAttribute("listSimpRule", ruleApi.listOfRules(RuleType.Simple));
								session.setAttribute("personListContainer2", getDummyPersonListContainer2());

							return "ruleDetails";
							}
						}						
						ruleObj.add(rulSimple);
						ruleComp.setCompJoinRule(ruleObj);
						rule.setRuleComposite(ruleComp);
						
					}else {
						ruleObj.add(rulSimple);
						ruleComp.setCompJoinRule(ruleObj);
						rule.setRuleComposite(ruleComp);
					}
					
				}
	
				rule.setId(ruleUI.getId());
				rule.setRuleName(ruleUI.getRuleName());
				rule.setDescription(ruleUI.getDescription());
				rule.setRuleType(ruleUI.getRuleType());
				rule.setConnectionType(ruleUI.getConnectionType());
				rule.setCompensationType(ruleUI.getCompensationType());
				rule.setFixedCompValue(ruleUI.getFixedCompValue());
				rule.setCompensationFormula(ruleUI.getCompensationFormula());
				rule.setCompensationParameter(ruleUI.getCompensationParameter());
				rule.setRuleParameter(personListContainer.getPersonList1());
	
				ruleApi.createRule(rule);
			}
		}
		return "redirect:/editComposite/"+ruleUI.getId();

		
	}

	@RequestMapping(value = "/ruleList", method = RequestMethod.GET)
	public String listRules(ModelMap model) {
		model.addAttribute("listRule", ruleApi.listOfRules());
		// logger.info("A NEW List HAS CREATED");
		System.out.println("*****************ListDone**********************");
		return "compRule";
	}

	@RequestMapping(value = "/editComposite/{id}", method = RequestMethod.GET)
	public String EditCompRule(@PathVariable("id") int id,HttpSession session, ModelMap model) {
		model.addAttribute("listRule1", ruleApi.getRule(id));
		Rule rule1 = ruleApi.getRule(id);
		
		List<Rule> ruleName = new ArrayList<Rule>();
		RuleComposite rc1 = rule1.getRuleComposite();
		List<Rule> cjr = rc1.getCompJoinRule();
		Iterator it1 = cjr.iterator();	 
	    while(it1.hasNext()) {
	    	Rule r = (Rule)it1.next();
	      System.out.println(r.getRuleName());
	      ruleName.add(r);
	    }
	    model.addAttribute("rName",ruleName);
		
//	    List<RuleParameter> ptr = rule1.getRuleParameter();
//	    List<RuleParameter> parameterList = new ArrayList<RuleParameter>();
//		Iterator it = ptr.iterator();	 
//	    while(it.hasNext()) {
//	    	RuleParameter rp = (RuleParameter)it.next();
//	      System.out.println(rp.getParameterName());
//	      System.out.println(rp.getParameterValue());
//	      parameterList.add(rp);
//	    }
//	  model.addAttribute("parList",parameterList);
	    
//		if (session.getAttribute("personListContainer") == null)
//			session.setAttribute("personListContainer", getDummyPersonListContainer3());
//		model.addAttribute("personListContainer", (PersonListContainer) session.getAttribute("personListContainer"));

		session.setAttribute("personListContainer2", getDummyPersonListContainer2());
		model.addAttribute("personListContainer2", (PersonListContainer2) session.getAttribute("personListContainer2"));
		model.addAttribute("listSimpRule", ruleApi.listOfRules(RuleType.Simple));
		return "EditComposite";
	}

}
