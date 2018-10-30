package com.simpsoft.salesCommission.app.calculation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.simpsoft.salesCommission.app.api.CalculationAPI;
import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.api.SplitRuleAPI;
import com.simpsoft.salesCommission.app.model.CalculationSimple;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleSimple;

public class CalculateCompAmountSimpleInd {
	
	private static final Logger logger = Logger.getLogger(CalculateCompAmountSimpleInd.class);
	private static Date startDate=null;
	private static Date endDate=null;
	private static RuleAssignment assignment = null;
	private static List<Rule> qualifiedRuleListOfEmp = null;
	private static Map<Long, List<Rule>> rule_ruleassg = null;
	private static List<Rule> listRules = null;
	private static List<List<Double>> sum_ordTotal_Qty_list_main = null;
	private static List<Double> sum_ordTotal_Qty_list = null;
	private static HashMap<Rule, List<List<Double>>> rule_ordTotal_qty = null;
	private static HashMap<Long,HashMap<Rule, List<List<Double>>>> empAssg_rule_ordTotal_qty = null;
	private static Map<Date,Date> freqDates =null;
	private static Map<Rule,Map<Date,Date>> rule_freq_map = null;
	private static int index;
	private static HashMap<Rule, List<Double>> rule_output_map = null;
	private static List<CalculationSimple> calcSimpList = new ArrayList<>();
	private static List<CalculationSimple> calcSimpListRule = null;
	private static Map<Rule, Map<String, Integer>> ruleMaxValuesMap = null;
	private static Map<Rule, Map<String, Integer>> ruleMinValuesMap = null;
	private static Map<Employee,Map<OrderLineItemsSplit, Boolean>> empSplitQualMap = new HashMap<>();
	private static Map<OrderLineItemsSplit, Boolean> splitQualMap = null;
	private static Map<OrderLineItemsSplit, OrderLineItems> splitLineItemMap =null;
	
	public static void main(String[] args) throws ParseException, ScriptException {
		
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("/applicationContext.xml");
		EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
		RuleAssignmentAPI ruleAssAPI =(RuleAssignmentAPI) context.getBean("ruleAssignmentApi");
		RuleSimpleAPI ruleSimpAPI =(RuleSimpleAPI) context.getBean("ruleSimpleApi");
		RuleAPI ruleAPI =(RuleAPI) context.getBean("ruleApi");
		OrderAPI orderAPI =(OrderAPI) context.getBean("orderApi");
		SplitRuleAPI splitRuleAPI = (SplitRuleAPI) context.getBean("splitRuleApi");
		CalculationAPI calcAPI = (CalculationAPI)context.getBean("calcApi");
		
		//save start and end dates in calc roster table
//		System.out.println("Enter start date in dd/MM/yyyy format: ");
//		String sDate1= new Scanner(System.in).next();
		String sDate1 = args[0];
		logger.debug("sDATE= "+sDate1);
		startDate=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		logger.debug("START DATE= "+startDate);
//		System.out.println("Enter end date in dd/MM/yyyy format: ");
//		String sDate2= new Scanner(System.in).next();		
		String sDate2 = args[1];
		logger.debug("eDATE= "+sDate2);
		 endDate=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
		logger.debug("END DATE= "+endDate);
		
		
		
		//find rule assignments for all employee
		findRuleAssgForAllEmp(calcAPI, empAPI, ruleAssAPI, ruleAPI, orderAPI, splitRuleAPI);
		
		calcAPI.saveDatesSimpList(startDate, endDate, calcSimpList, empSplitQualMap,false);
	}

	/**
	 * @param empAPI
	 * @param ruleAssAPI
	 * @param ruleAPI
	 * @param orderAPI
	 * @throws ParseException 
	 * @throws ScriptException 
	 */
	private static void findRuleAssgForAllEmp(CalculationAPI calcAPI,EmployeeAPI empAPI, RuleAssignmentAPI ruleAssAPI, RuleAPI ruleAPI,
			OrderAPI orderAPI, SplitRuleAPI splitRuleAPI) throws ParseException, ScriptException {
		logger.debug("---FINDING RULE ASSIGNMENTS---");
		List<Employee> empList = empAPI.listEmployees();
		for(Employee emp: empList) {
			
			List<CalculationSimple> calcSimpleListEmp = new ArrayList<>();
			int counter=0;
			 assignment= ruleAssAPI.searchAssignedRuleForEmployee(emp.getId());
			
			if(assignment != null) {
				qualifiedRuleListOfEmp = new ArrayList<>();
				List<Rule> rulesAssigned = new ArrayList<>();
				logger.debug("RuleAssignment id = "+ assignment.getId());
//				HashMap<Long, Long> numTimes = new HashMap<>();
				rule_ruleassg = new HashMap<Long, List<Rule>>();
				listRules = new ArrayList<Rule>();
				rule_ordTotal_qty = new HashMap<Rule, List<List<Double>>>();
				empAssg_rule_ordTotal_qty = new HashMap<Long, HashMap<Rule,List<List<Double>>>>();
				rule_freq_map = new HashMap<Rule,Map<Date,Date>>();
				ruleMaxValuesMap = new HashMap<Rule,Map<String, Integer>>();
				ruleMinValuesMap = new HashMap<Rule,Map<String, Integer>>();
				
				splitQualMap = new HashMap<>();
				splitLineItemMap = new HashMap<>();
				
					List<RuleAssignmentDetails> assignmentDetails = assignment.getRuleAssignmentDetails();
					for(RuleAssignmentDetails details : assignmentDetails) {
						logger.debug("Rule Assignment details id = "+ details.getId());
						logger.debug("VALIDITY TYPE= "+details.getValidityType());
						// check whether the start and end dates of the rule assignment detail falls between
						// the start and end date input values
						boolean valid= calcAPI.checkValidity(ruleAssAPI, details.getId(), startDate, endDate);
						logger.debug("RULE ID= "+ details.getRule().getId());							
						logger.debug("RULE NAME= "+ details.getRule().getRuleName());			
						logger.debug("Validity= "+valid);
						
						if(valid==true) {						
							
							// find the no of times the rule is qualified to obtain the final compensation amount 
							// if validity type is repeats
							if(details.getValidityType().equals("repeats")) {
								Date planStartDate = details.getStartDate();
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
								String sd = format1.format(planStartDate);
								planStartDate = format1.parse(sd);
								logger.debug("Plan start date= "+planStartDate);
								Date planEndDate = details.getEndDate();
								String ed = format1.format(planEndDate);
								planEndDate = format1.parse(ed);
								logger.debug("Plan end date= "+planEndDate);
								// get frequency
								long freqId = details.getFrequency().getId();
								String freq = details.getFrequency().getFrequencyName();
								logger.debug("FREQ= "+freq);
								
								if(freq.equals("weekly")) {
									freqDates= calcAPI.getFullWeeks(planStartDate, planEndDate, startDate, endDate);
									rule_freq_map.put(details.getRule(), freqDates);
//									logger.debug("num of weeks= "+num);
//									numTimes.put(details.getId(), num);
									
									
								}
								
								else if(freq.equals("monthly")) {
									freqDates=calcAPI.getFullMonths(planStartDate, planEndDate, startDate, endDate);
									rule_freq_map.put(details.getRule(), freqDates);
//									logger.debug("num of months= "+num);
//									numTimes.put(details.getId(), num);
								}
								
								 else if(freq.equals("quaterly")) {
									 freqDates=calcAPI.getFullQuarters(planStartDate, planEndDate, startDate, endDate);
									 rule_freq_map.put(details.getRule(), freqDates);
//									logger.debug("num of quarters= "+num);
//									numTimes.put(details.getId(), num);
								 }
								
								 else if(freq.equals("half-yearly")) {
									 freqDates=calcAPI.getFullHalves(planStartDate, planEndDate, startDate, endDate);
									 rule_freq_map.put(details.getRule(), freqDates);
//										logger.debug("num of halves= "+num);
//										numTimes.put(details.getId(), num);
									}
								
								else if(freq.equals("annually")) {
									freqDates=calcAPI.getFullYears(planStartDate, planEndDate, startDate, endDate);
									rule_freq_map.put(details.getRule(), freqDates);
//										logger.debug("num of years= "+num);
//										numTimes.put(details.getId(), num);
									}
								
							}else {
								logger.debug("FIXED RULE");
//								numTimes.put(details.getId(), (long) 1);
								Date planStartDate = details.getStartDate();
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
								String sd = format1.format(planStartDate);
								planStartDate = format1.parse(sd);
								logger.debug("Fixed rule Plan start date= "+planStartDate);
								Date planEndDate = details.getEndDate();
								String ed = format1.format(planEndDate);
								planEndDate = format1.parse(ed);
								logger.debug("Fixed rule Plan end date= "+planEndDate);
								freqDates =calcAPI.getDatesFixedRule(planStartDate, planEndDate, startDate, endDate);
								rule_freq_map.put(details.getRule(), freqDates);
							}
							
							
							rulesAssigned.add(details.getRule());		
							counter=counter+1;
							
						}else {
//							numTimes.put(details.getId(), (long) 0);
						}
						
					}
					
	//------------------------------------------------------------------------------------------------------
					
					if(counter>0) {	
						
	//				find order line item split list for the employee
						logger.debug("---FINDING ORDER LINE SPLIT DATA FOR THE EMPLOYEE---");
						List<OrderLineItemsSplit> empSplitList = orderAPI.getLineItemSplitListForEmp(emp.getId());
						//list of all line items for this emp
						List<OrderLineItems> empLineItemsList = new ArrayList<>();
						if(empSplitList != null) {
							logger.debug("---LINE ITEM SPLIT LIST FOR EMP ID ="+emp.getId()+"---");
							for(OrderLineItemsSplit itemsSplit : empSplitList) {
								logger.debug("LINE ITEM SPLIT ID = "+ itemsSplit.getId());
								
								// get line item details for this line item split
								OrderLineItems items= splitRuleAPI.getOrderLineItem(itemsSplit.getId());
								logger.debug("LINE ITEM ID = "+items.getId());
								empLineItemsList.add(items);
								
								splitLineItemMap.put(itemsSplit, items);
							}
						}
						logger.debug("empLineItemsList size = "+empLineItemsList.size());
						for(OrderLineItems items : empLineItemsList) {
							logger.debug("IN LIST LINE ITEM= "+items.getId());
						}
						
						// compare line item list with qual clause list of each rule
						for(Rule rule :rulesAssigned) {
							Date ruleCalcStartDate=new Date();
							Date ruleCalcEndDate=new Date();
							sum_ordTotal_Qty_list_main =new ArrayList<>();
							
							for(Map.Entry<Rule, Map<Date,Date>> rule_dates_map : rule_freq_map.entrySet()) {
								Rule keyRule = rule_dates_map.getKey();
								if(keyRule == rule) {
									
									Map<Date,Date> dates = rule_dates_map.getValue();
									for(Map.Entry<Date, Date> entry2 : dates.entrySet()) {
										List<OrderLineItems> qualifiedLineItemsList = new ArrayList<>();
										ruleCalcStartDate = entry2.getKey();
										ruleCalcEndDate = entry2.getValue();
										logger.debug("ruleCalcStartDate= "+ruleCalcStartDate);
										logger.debug("ruleCalcEndDate= "+ruleCalcEndDate);
										
										//filter line items based on ruleCalcStartDate and ruleCalcEndDate
										for(OrderLineItems items : empLineItemsList) {
											boolean qualified= calcAPI.checkLineItemDate(items, ruleCalcStartDate, ruleCalcEndDate);
											if(qualified == true) {
												logger.debug(items.getId()+"is qualified for rule= "+rule.getRuleName()+"for "
														+ "rule calc start date= "+ruleCalcStartDate+" and rule calc "
																+ "end date = "+ruleCalcEndDate);
												qualifiedLineItemsList.add(items);
											}
										
											
										}								
										if(!qualifiedLineItemsList.isEmpty()) {
											compareLineItem(calcAPI, orderAPI, ruleAPI, qualifiedLineItemsList, rule);
											logger.debug("rule_ordTotal_qty size= "+rule_ordTotal_qty.size());
											for(Map.Entry<Rule, List<List<Double>>> entry : rule_ordTotal_qty.entrySet()) {
												Rule key_rule = entry.getKey();
												logger.debug("KEY RULE NAME= "+key_rule.getRuleName());
												List<List<Double>> values = entry.getValue();
												for(List<Double> valueList : values) {
													for(Double value : valueList) {
														logger.debug("TEST VALUE= "+value);
													}
												}
												
												
											}
										}
										
										
									}
								}
								
								
								
								
							}
							
							
						}
						
				}
					empAssg_rule_ordTotal_qty.put(assignment.getId(), rule_ordTotal_qty);
					rule_ruleassg.put(assignment.getId(), listRules);
					empSplitQualMap.put(emp, splitQualMap);
			}
			if(qualifiedRuleListOfEmp != null) {
				if(qualifiedRuleListOfEmp.size() > 0 ) {
					logger.debug(qualifiedRuleListOfEmp.size()+" RULES ARE SATISFIED FOR EMP ID= "+emp.getId());
					logger.debug("LIST OF SATISFIED RULES FOR EMP ID = "+emp.getId());
					String prevRule = "";
					for(Rule satisfiedRule : qualifiedRuleListOfEmp) {
						
						List<CalculationSimple> calcSimpListRule = new ArrayList<>();
						
						ArrayList<Double> paramValues = new ArrayList<>();
						logger.debug("SATISFIED SIMPLE RULE NAME= "+satisfiedRule.getRuleName());
						
						if(satisfiedRule.getCompensationType().equals("Fixed")) {
							logger.debug("fixed value= "+ satisfiedRule.getFixedCompValue());
							logger.debug("COMPENSATION AMOUNT= "+satisfiedRule.getFixedCompValue());
							for(Map.Entry<Rule, Map<Date,Date>> rule_dates_map : rule_freq_map.entrySet()) {
								Rule rule = rule_dates_map.getKey();
								if(rule== satisfiedRule) {
									Date ruleCalcStartDate = new Date();
									Date ruleCalcEndDate = new Date();
									Map<Date, Date> fixedDates = rule_dates_map.getValue();
									for(Map.Entry<Date, Date> dateMap : fixedDates.entrySet()) {
										ruleCalcStartDate = dateMap.getKey();
										ruleCalcEndDate = dateMap.getValue();
									}
									
									logger.debug("---DATA TO BE SAVED FOR FIXED RULE---");
									logger.debug("EMP ID = "+emp.getId());
									logger.debug("RULE ID= "+rule.getId());
									logger.debug("CALC START DATE= "+ruleCalcStartDate);
									logger.debug("CALC END DATE= "+ruleCalcEndDate);
									logger.debug("COMP AMT= "+satisfiedRule.getFixedCompValue());
									
									CalculationSimple calculationSimple = new CalculationSimple();
									calculationSimple.setCalStartDate(ruleCalcStartDate);
									calculationSimple.setCalEndDate(ruleCalcEndDate);
									calculationSimple.setCompensationAmount((double) satisfiedRule.getFixedCompValue());
									calculationSimple.setDummyCalcInternal(false);
									calculationSimple.setRule(satisfiedRule);
									calculationSimple.setEmployee(emp);
									
									calcSimpList.add(calculationSimple);
									break;
								}
							}
							
							
							
							
						}else {
							String formula = satisfiedRule.getCompensationFormula();
							formula= formula.replaceAll(" ", "");
							logger.debug("ORIGINAL FORMULA= "+formula);
							String[] params = satisfiedRule.getCompensationParameter().split(",");
							
							logger.debug("PARAMS LIST ");
							for(String param:params) {
								param = param.trim();
							
								logger.debug("PARAM NAME= "+param);
								
								if(param.equalsIgnoreCase("$RULE_OUTPUT")){							
									
									
									logger.debug("empAssg_rule_ordTotal_qty size= "+empAssg_rule_ordTotal_qty.size());
									
									// code for rule output
									double rule_output=0;
									RuleSimple ruleSimple = ruleAPI.findSimpleRule(satisfiedRule.getId());
									String agg_func_name = ruleSimple.getAggregateFunctions().getFunctionName();
									logger.debug("agg_func_name= "+agg_func_name);
									if(agg_func_name.equals("sum")) {
											
											for(Map.Entry<Long, HashMap<Rule, List<List<Double>>>> entry : empAssg_rule_ordTotal_qty.entrySet()) {
												rule_output_map=new HashMap<Rule, List<Double>>();
												List<Double> rule_output_list = new ArrayList<>();
												HashMap<Rule, List<List<Double>>> rule_ord_qty_list = entry.getValue();
												for(Map.Entry<Rule, List<List<Double>>> entry2 : rule_ord_qty_list.entrySet()) {
													Rule rule = entry2.getKey();
													
													if(rule.getRuleName().equals(satisfiedRule.getRuleName())) {
														
															logger.debug("ADDED KEY RULE= "+rule.getRuleName());
															
															List<List<Double>> values = entry2.getValue();
															for(List<Double> valueList : values) {
																for(Double value : valueList) {
																	logger.debug("ADDED VALUE= "+value);
																}
																logger.debug("field name= "+ ruleSimple.getField());
																if(ruleSimple.getField().equalsIgnoreCase("Order Total")) {
																	rule_output = valueList.get(0);
																}else {
																	rule_output = valueList.get(1);
																}
																logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
																rule_output_list.add(rule_output);
															}	
															rule_output_map.put(rule, rule_output_list);
													}
												}
											}
											
									}
									else if(agg_func_name.equals("max")) {
										rule_output_map=new HashMap<Rule, List<Double>>();
										List<Double> rule_output_list = new ArrayList<>();
										for(Map.Entry<Rule, Map<String,Integer>> entry : ruleMaxValuesMap.entrySet()) {
											Rule keyRule = entry.getKey();
											if(keyRule.getRuleName().equals(satisfiedRule.getRuleName())) {
												Map<String, Integer> map = entry.getValue();
												logger.debug("field name= "+ ruleSimple.getField());
												if(ruleSimple.getField().equalsIgnoreCase("Quantity")) {
													rule_output = map.get("MaxQty");
													logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
													
												}else {
													rule_output = map.get("MaxOrderTotal");
													logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
												}
												rule_output_list.add(rule_output);
												
											}
											rule_output_map.put(keyRule, rule_output_list);
										}
									}
									else if(agg_func_name.equals("min")) {
										rule_output_map=new HashMap<Rule, List<Double>>();
										List<Double> rule_output_list = new ArrayList<>();
										for(Map.Entry<Rule, Map<String,Integer>> entry : ruleMinValuesMap.entrySet()) {
											Rule keyRule = entry.getKey();
											if(keyRule.getRuleName().equals(satisfiedRule.getRuleName())) {
												Map<String, Integer> map = entry.getValue();
												logger.debug("field name= "+ ruleSimple.getField());
												if(ruleSimple.getField().equalsIgnoreCase("Quantity")) {
													rule_output = map.get("MinQty");
													logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
												}else {
													rule_output = map.get("MinOrderTotal");
													logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
												}
												rule_output_list.add(rule_output);												
												
												
											}
											rule_output_map.put(keyRule, rule_output_list);
										}
									}
//									else {
//										// for count
//										
//									}
								}else {
									if(rule_ruleassg != null && !rule_ruleassg.isEmpty()) {
										for(Map.Entry<Long, List<Rule>> entry : rule_ruleassg.entrySet()) {
											long assgId = entry.getKey();
											List<Rule> rules= entry.getValue();
											for(Rule rule: rules) {
												if(satisfiedRule == rule) {													
													logger.debug("ASSGID= "+assgId);
													long detailsId = calcAPI.getAssignmentDetailId(assgId, satisfiedRule);
													logger.debug("ASSG DETAILS ID = "+detailsId);
													if(detailsId != 0) {
														//get the rule calc start and end dates of the rule
														for(Map.Entry<Rule, Map<Date,Date>> rule_dates_map : rule_freq_map.entrySet()) {
															Rule keyRule = rule_dates_map.getKey();
															if(keyRule == rule) {
																Map<Date,Date> dates_map = rule_dates_map.getValue();
																for(Map.Entry<Date, Date> entry2 : dates_map.entrySet()) {
																	Date ruleCalcStartDate = entry2.getKey();
																	Date ruleCalcEndDate = entry2.getValue();
																	// get the parameter value
																	int param_val = calcAPI.getParameterValue(param, detailsId,emp.getId(),
																			ruleCalcStartDate, ruleCalcEndDate);
																	logger.debug("PARAM VALUE= "+param_val);
																	paramValues.add((double)param_val);
																}
																break;
															}
														}
														
													}
													break;
												}
											}		
											
										}
									}
							
								}
							}
							logger.debug("rule output map size= "+ rule_output_map.size());
							if(rule_output_map.size() > 0) {
								if(!prevRule.equalsIgnoreCase(satisfiedRule.getRuleName())) {
									for(Map.Entry<Rule, List<Double>> entry : rule_output_map.entrySet()) {
										Rule rule= entry.getKey();
										if(rule == satisfiedRule) {
											int count_loop = 0;
												List<Double> values = entry.getValue();
												for(Double value : values) {
													
													
													ArrayList<Double> newParamValues = new ArrayList<>();
													newParamValues.addAll(paramValues);
													for(int a= 0; a<params.length; a++) {
														String param = params[a];
														if(param.equalsIgnoreCase("$RULE_OUTPUT")) {
															newParamValues.add(a, value);
															
															
														}
													}
													
													Object compAmt= calcAPI.replaceAndCalcCompAmt(newParamValues,formula);
													
													// get rule start and end calc dates
													 for(Map.Entry<Rule, Map<Date,Date>> rule_dates_map : rule_freq_map.entrySet()) {
														Rule keyRule = rule_dates_map.getKey();
														if(keyRule == rule) {
															CalculationSimple calculationSimple = new CalculationSimple();
															Date ruleCalcStartDate=new Date();
															Date ruleCalcEndDate=new Date();
															Map<Date,Date> dates = rule_dates_map.getValue();
															Object startDate_key = dates.keySet().toArray()[count_loop];
															Object endDate_val = dates.get(startDate_key);
																
																ruleCalcStartDate = (Date) startDate_key;
																ruleCalcEndDate = (Date) endDate_val;
																
																
																
																logger.debug("---DATA TO BE SAVED---");
																logger.debug("EMP ID = "+emp.getId());
																logger.debug("RULE ID= "+rule.getId());
																logger.debug("CALC START DATE= "+ruleCalcStartDate);
																logger.debug("CALC END DATE= "+ruleCalcEndDate);
																logger.debug("COMP AMT= "+compAmt);
																
																calculationSimple.setCalStartDate(ruleCalcStartDate);
																calculationSimple.setCalEndDate(ruleCalcEndDate);
																calculationSimple.setCompensationAmount((double) compAmt);
																calculationSimple.setDummyCalcInternal(false);
																calculationSimple.setRule(satisfiedRule);
																calculationSimple.setEmployee(emp);
																
																calcSimpList.add(calculationSimple);
																
																count_loop+=1;
															
														}
														
													}
													
												}
												
										}
										prevRule= satisfiedRule.getRuleName();
									}
								}
							}
							
						}		
			

					}
				}else {
					logger.debug("NO SIMPLE RULES ARE SATISFIED FOR EMP ID= "+emp.getId());
				}
			}
		
			assignment=null;
			qualifiedRuleListOfEmp=null;
			rule_ruleassg = null;
			empAssg_rule_ordTotal_qty=null;
			
		}
		
	}

	

	private static void compareLineItem(CalculationAPI calcAPI,OrderAPI orderAPI,RuleAPI ruleAPI,
			List<OrderLineItems> qualifiedLineItemsList, Rule rule) {
		if(rule.getRuleType().equalsIgnoreCase("simple")){
			
			//check whether the simple rule is individual
			RuleSimple ruleSimple = ruleAPI.findSimpleRule(rule.getId());
			if(ruleSimple.getCalculationMode().equals("individual")) {
				boolean added=false;
				sum_ordTotal_Qty_list = new ArrayList<>();
				double orderTotal=0;
				double quantity=0;
				Map<String, Integer> maxValues = new HashMap<>();
				Map<String, Integer> minValues = new HashMap<>();
				
				List<QualifyingClause> qualList = calcAPI.getSimpRuleDetails(ruleAPI, rule);
				List<QualifyingClause> nonAggQualList = new ArrayList<>();
				List<QualifyingClause> aggQualList = new ArrayList<>();
				int non_agg_counter=0;
				for(QualifyingClause clause : qualList) {
					if(clause.getAggregateFunctions() == null) {
						non_agg_counter+=1;
						nonAggQualList.add(clause);
					}else {
						aggQualList.add(clause);
					}
				}
				
				List<OrderLineItems> filteredLineItemsList = new ArrayList<>();
				if(non_agg_counter == 0) {
					filteredLineItemsList = qualifiedLineItemsList;
				}else {				
					//compare each line item with all the simple qual clauses
					for(OrderLineItems items : qualifiedLineItemsList) {					
						boolean isSatisfied = checkLineItem(orderAPI, items, nonAggQualList);
						logger.debug("isSatisfied= "+isSatisfied);
						if(isSatisfied == true) {
							filteredLineItemsList.add(items);
						}
						//get order line item split id from map
						for(Map.Entry<OrderLineItemsSplit, OrderLineItems> entryMap : splitLineItemMap.entrySet()) {
							OrderLineItems lineItem = entryMap.getValue();
							if(lineItem == items) {
								OrderLineItemsSplit lineItemSplit = entryMap.getKey();
								splitQualMap.put(lineItemSplit, isSatisfied);
								break;
							}
						}
					}
				}
				logger.debug("FILTEREDLINEITEMSLIST FOR RULE = "+rule.getRuleName());
				for(OrderLineItems filteredItem : filteredLineItemsList) {
					logger.debug("FILTERED LINE ITEM ID= "+filteredItem.getId());
					
				}
				
				
				// compare list of line items with agg qual clause
				if(aggQualList.size() > 0 && filteredLineItemsList.size()>0) {
					int flag=0;
					
					for(OrderLineItems items : filteredLineItemsList) {						
						orderTotal += items.getSubtotal();
						quantity += items.getQuantity();			
						
					}
					logger.debug("ORDER TOTAL= "+orderTotal);
					logger.debug("QUANTITY= "+quantity);
					
					int maxDiscPercentage=0;
					int maxDutyPercentage=0;
					int maxQty = 0;
					int maxSubTotal = 0;
					
					for(OrderLineItems items : filteredLineItemsList) {
						int discPercentage = items.getDiscountPercentage();
						int dutyPercentage = items.getDutyPercentage();
						int itemQty = items.getQuantity();
						int subTotal = (int) items.getSubtotal();
						
						if(discPercentage >= maxDiscPercentage) {
							maxDiscPercentage = discPercentage;
						}
						if(dutyPercentage >= maxDutyPercentage) {
							maxDutyPercentage = dutyPercentage;
						}
						if(itemQty >= maxQty) {
							maxQty = itemQty;
						}
						if(subTotal >= maxSubTotal) {
							maxSubTotal = subTotal;
						}
					}
					maxValues.put("MaxDiscPercent", maxDiscPercentage);
					maxValues.put("MaxDutyPercent", maxDutyPercentage);
					maxValues.put("MaxQty", maxQty);
					maxValues.put("MaxOrderTotal", maxSubTotal);
					

					logger.debug("MAXIMUM DISCOUNT PERCENTAGE= "+maxDiscPercentage);
					logger.debug("MAXIMUM DUTY PERCENTAGE= "+maxDutyPercentage);
					logger.debug("MAXIMUM QUANTITY= "+maxQty);
					logger.debug("MAXIMUM ORDER(SUB) TOTAL= "+maxSubTotal);
					
					int minDiscPercentage=999999999;
					int minDutyPercentage=999999999;
					int minQty = 999999999;
					int minSubTotal = 999999999;
					
					for(OrderLineItems items : filteredLineItemsList) {
						int discPercentage = items.getDiscountPercentage();
						int dutyPercentage = items.getDutyPercentage();
						int itemQty = items.getQuantity();
						int subTotal = (int) items.getSubtotal();
						
						if(discPercentage <= minDiscPercentage) {
							minDiscPercentage = discPercentage;
						}
						if(dutyPercentage <= minDutyPercentage) {
							minDutyPercentage = dutyPercentage;
						}
						if(itemQty <= minQty) {
							minQty = itemQty;
						}
						if(subTotal <= minSubTotal) {
							minSubTotal = subTotal;
						}
					}
					
					minValues.put("MinDiscPercent", minDiscPercentage);
					minValues.put("MinDutyPercent", minDutyPercentage);
					minValues.put("MinQty", minQty);
					minValues.put("MinOrderTotal", minSubTotal);
					
					logger.debug("MINIMUM DISCOUNT PERCENTAGE= "+minDiscPercentage);
					logger.debug("MINIMUM DUTY PERCENTAGE= "+minDutyPercentage);
					logger.debug("MINIMUM QUANTITY= "+minQty);
					logger.debug("MINIMUM ORDER(SUB) TOTAL= "+minSubTotal);
					
					
					for(QualifyingClause aggClause : aggQualList) {
						if(aggClause.getAggregateFunctions().getFunctionName().equals("sum")) {
//							
//							for(OrderLineItems items : filteredLineItemsList) {
//								
//								orderTotal += items.getSubtotal();
//								quantity += items.getQuantity();
//								
//								
//							}
//							logger.debug("ORDER TOTAL= "+orderTotal);
//							logger.debug("QUANTITY= "+quantity);
							double compareValue = 0;
							String displayName = aggClause.getFieldList().getDisplayName();
							boolean notFlag = aggClause.isNotFlag();
							String condition = aggClause.getConditionList().getConditionValue();
							String sValue = aggClause.getValue();
							Double value = Double.parseDouble(sValue);
							if(displayName.equalsIgnoreCase("Order Total")) {
								compareValue = orderTotal;
							}
							else if(displayName.equalsIgnoreCase("Quantity")) {
								compareValue = Double.valueOf(quantity);
							}
							boolean isSatisfied = checkAggQual(compareValue, notFlag, condition, value);
							logger.debug("isSatisfied for Agg Qual clause= "+isSatisfied);
							if(isSatisfied == false) {
								flag+=1;
							}
							
							
						}
						
						else if(aggClause.getAggregateFunctions().getFunctionName().equals("max")) {
							
//							int maxDiscPercentage=0;
//							int maxDutyPercentage=0;
//							int maxQty = 0;
//							int maxSubTotal = 0;
//							
//							for(OrderLineItems items : filteredLineItemsList) {
//								int discPercentage = items.getDiscountPercentage();
//								int dutyPercentage = items.getDutyPercentage();
//								int itemQty = items.getQuantity();
//								int subTotal = (int) items.getSubtotal();
//								
//								if(discPercentage >= maxDiscPercentage) {
//									maxDiscPercentage = discPercentage;
//								}
//								if(dutyPercentage >= maxDutyPercentage) {
//									maxDutyPercentage = dutyPercentage;
//								}
//								if(itemQty >= maxQty) {
//									maxQty = itemQty;
//								}
//								if(subTotal >= maxSubTotal) {
//									maxSubTotal = subTotal;
//								}
//							}
//							maxValues.put("MaxDiscPercent", maxDiscPercentage);
//							maxValues.put("MaxDutyPercent", maxDutyPercentage);
//							maxValues.put("MaxQty", maxQty);
//							maxValues.put("MaxOrderTotal", maxSubTotal);
							
							int compareValue = 0;
							String displayName = aggClause.getFieldList().getDisplayName();
							boolean notFlag = aggClause.isNotFlag();
							String condition = aggClause.getConditionList().getConditionValue();
							String sValue = aggClause.getValue();
							int value= Integer.parseInt(sValue);
							if(displayName.equalsIgnoreCase("Discount Percentage")) {
								compareValue= maxDiscPercentage;								
							}
							else if(displayName.equalsIgnoreCase("Duty Percentage")) {
								compareValue= maxDutyPercentage;
							}
							else if(displayName.equalsIgnoreCase("Quantity")) {
								compareValue = maxQty;
							}
							else if(displayName.equalsIgnoreCase("Order Total")) {
								compareValue = maxSubTotal;
							}
							
							boolean isSatisfied = checkAggQual(compareValue, notFlag, condition, (double) value);
							logger.debug("isSatisfied for Agg Qual clause= "+isSatisfied);
							if(isSatisfied == false) {
								flag+=1;
							}
							
							logger.debug("MAXIMUM DISCOUNT PERCENTAGE= "+maxDiscPercentage);
							logger.debug("MAXIMUM DUTY PERCENTAGE= "+maxDutyPercentage);
							logger.debug("MAXIMUM QUANTITY= "+maxQty);
							logger.debug("MAXIMUM ORDER(SUB) TOTAL= "+maxSubTotal);
							
						}
						
						else if(aggClause.getAggregateFunctions().getFunctionName().equals("min")) {
							
//							int minDiscPercentage=999999999;
//							int minDutyPercentage=999999999;
//							int minQty = 999999999;
//							int minSubTotal = 999999999;
//							
//							for(OrderLineItems items : filteredLineItemsList) {
//								int discPercentage = items.getDiscountPercentage();
//								int dutyPercentage = items.getDutyPercentage();
//								int itemQty = items.getQuantity();
//								int subTotal = (int) items.getSubtotal();
//								
//								if(discPercentage <= minDiscPercentage) {
//									minDiscPercentage = discPercentage;
//								}
//								if(dutyPercentage <= minDutyPercentage) {
//									minDutyPercentage = dutyPercentage;
//								}
//								if(itemQty <= minQty) {
//									minQty = itemQty;
//								}
//								if(subTotal <= minSubTotal) {
//									minSubTotal = subTotal;
//								}
//							}
//							
//							minValues.put("MinDiscPercent", minDiscPercentage);
//							minValues.put("MinDutyPercent", minDutyPercentage);
//							minValues.put("MinQty", minQty);
//							minValues.put("MinOrderTotal", minSubTotal);
							
							int compareValue = 0;
							String displayName = aggClause.getFieldList().getDisplayName();
							boolean notFlag = aggClause.isNotFlag();
							String condition = aggClause.getConditionList().getConditionValue();
							String sValue = aggClause.getValue();
							int value= Integer.parseInt(sValue);
							if(displayName.equalsIgnoreCase("Discount Percentage")) {
								compareValue= minDiscPercentage;							
							}
							else if(displayName.equalsIgnoreCase("Duty Percentage")) {
								compareValue= minDutyPercentage;
							}
							else if(displayName.equalsIgnoreCase("Quantity")) {
								compareValue = minQty;
							}
							else if(displayName.equalsIgnoreCase("Order Total")) {
								compareValue = minSubTotal;
							}
							boolean isSatisfied = checkAggQual(compareValue, notFlag, condition, (double) value);
							logger.debug("isSatisfied for Agg Qual clause= "+isSatisfied);
							if(isSatisfied == false) {
								flag+=1;
							}
							
							logger.debug("MINIMUM DISCOUNT PERCENTAGE= "+minDiscPercentage);
							logger.debug("MINIMUM DUTY PERCENTAGE= "+minDutyPercentage);
							logger.debug("MINIMUM QUANTITY= "+minQty);
							logger.debug("MINIMUM ORDER(SUB) TOTAL= "+minSubTotal);
							
						}
						
						else {
							//for count
							
							List<String> custNames = new ArrayList<>();
							List<String> prodNames = new ArrayList<>();
							List<String> saleTypes = new ArrayList<>();
							for(OrderLineItems items : filteredLineItemsList) {
								OrderDetail detail = orderAPI.getOrderDetailFromLineItem(items.getId());
								String custName = detail.getCustomer().getCustomerName();
								custNames.add(custName);
								String prodName = items.getProduct().getProductName();
								prodNames.add(prodName);
								String saleType = detail.getSaleType();
								saleTypes.add(saleType);
							}
							List<String> new_custNames = new ArrayList<String>(new HashSet<String>(custNames));
							int count_custName = new_custNames.size();
							
							List<String> new_prodNames = new ArrayList<String>(new HashSet<String>(prodNames));
							int count_prodName = new_prodNames.size();
							
							List<String> new_saleTypes = new ArrayList<String>(new HashSet<String>(saleTypes));
							int count_saleType = new_saleTypes.size();
							
							
							int compareValue = 0;
							String displayName = aggClause.getFieldList().getDisplayName();
							boolean notFlag = aggClause.isNotFlag();
							String condition = aggClause.getConditionList().getConditionValue();
							String sValue = aggClause.getValue();
							int value= Integer.parseInt(sValue);
							if(displayName.equalsIgnoreCase("Customer Name")) {
								compareValue= count_custName;							
							}
							else if(displayName.equalsIgnoreCase("Product Name")) {
								compareValue= count_prodName;
							}
							else if(displayName.equalsIgnoreCase("Sale Type")) {
								compareValue = count_saleType;
							}
							
							boolean isSatisfied = checkAggQual(compareValue, notFlag, condition, (double) value);
							logger.debug("isSatisfied for Agg Qual clause= "+isSatisfied);
							if(isSatisfied == false) {
								flag+=1;
							}
							
							logger.debug("COUNT CUSTOMER NAME= "+count_custName);
							logger.debug("COUNT PRODUCT NAME= "+count_prodName);
							logger.debug("COUNT SALE TYPE= "+count_saleType);
							
						}
						
						
						
						
					}
					
					//rule is qualified if flag value is 0
					if(flag == 0) {
						logger.debug("Adding "+rule.getRuleName()+"to the list");
						qualifiedRuleListOfEmp.add(rule);
						added=true;
						listRules.add(rule);
						ruleMaxValuesMap.put(rule, maxValues);
						ruleMinValuesMap.put(rule, minValues);
					}else {
						logger.debug("Not adding "+rule.getRuleName()+"to the list");
					}
					
				}else {
					// if filteredlineitem list size is greater than 0 then this rule is qualified
					if(filteredLineItemsList.size() > 0) {
						qualifiedRuleListOfEmp.add(rule);
						added=true;
						listRules.add(rule);
						logger.debug("Adding "+rule.getRuleName()+"to the list");
						
						int maxDiscPercentage=0;
						int maxDutyPercentage=0;
						int maxQty = 0;
						int maxSubTotal = 0;
						int minDiscPercentage=999999999;
						int minDutyPercentage=999999999;
						int minQty = 999999999;
						int minSubTotal = 999999999;
						
						for(OrderLineItems items : filteredLineItemsList) {
							orderTotal += items.getSubtotal();
							quantity += items.getQuantity();
							
							int discPercentage = items.getDiscountPercentage();
							int dutyPercentage = items.getDutyPercentage();
							int itemQty = items.getQuantity();
							int subTotal = (int) items.getSubtotal();
							if(discPercentage >= maxDiscPercentage) {
								maxDiscPercentage = discPercentage;
							}
							if(dutyPercentage >= maxDutyPercentage) {
								maxDutyPercentage = dutyPercentage;
							}
							if(itemQty >= maxQty) {
								maxQty = itemQty;
							}
							if(subTotal >= maxSubTotal) {
								maxSubTotal = subTotal;
							}
							
							
							if(discPercentage <= minDiscPercentage) {
								minDiscPercentage = discPercentage;
							}
							if(dutyPercentage <= minDutyPercentage) {
								minDutyPercentage = dutyPercentage;
							}
							if(itemQty <= minQty) {
								minQty = itemQty;
							}
							if(subTotal <= minSubTotal) {
								minSubTotal = subTotal;
							}
							
						}
						
						maxValues.put("MaxDiscPercent", maxDiscPercentage);
						maxValues.put("MaxDutyPercent", maxDutyPercentage);
						maxValues.put("MaxQty", maxQty);
						maxValues.put("MaxOrderTotal", maxSubTotal);

						minValues.put("MinDiscPercent", minDiscPercentage);
						minValues.put("MinDutyPercent", minDutyPercentage);
						minValues.put("MinQty", minQty);
						minValues.put("MinOrderTotal", minSubTotal);
						
						
						logger.debug("ORDER TOTAL= "+orderTotal);
						logger.debug("QUANTITY= "+quantity);
						logger.debug("MAXIMUM DISCOUNT PERCENTAGE= "+maxDiscPercentage);
						logger.debug("MAXIMUM DUTY PERCENTAGE= "+maxDutyPercentage);
						logger.debug("MAXIMUM QUANTITY= "+maxQty);
						logger.debug("MAXIMUM ORDER(SUB) TOTAL= "+maxSubTotal);
						logger.debug("MINIMUM DISCOUNT PERCENTAGE= "+minDiscPercentage);
						logger.debug("MINIMUM DUTY PERCENTAGE= "+minDutyPercentage);
						logger.debug("MINIMUM QUANTITY= "+minQty);
						logger.debug("MINIMUM ORDER(SUB) TOTAL= "+minSubTotal);
					}else {
						logger.debug("Not adding "+rule.getRuleName()+"to the list");
					}
				}
				if(added=true) {
					sum_ordTotal_Qty_list.add(orderTotal);
					sum_ordTotal_Qty_list.add(quantity);
					sum_ordTotal_Qty_list_main.add(sum_ordTotal_Qty_list);
					ruleMaxValuesMap.put(rule, maxValues);
					ruleMinValuesMap.put(rule, minValues);
					
				}
				rule_ordTotal_qty.put(rule, sum_ordTotal_Qty_list_main);
			}		
		
		}	
		
	}

	
	
	private static boolean checkAggQual(double compareValue, boolean notFlag, String condition, Double value) {
		if(condition.equals("equal")) {
			
			if(compareValue == value) {
				if(!notFlag) {
					logger.debug(compareValue+" is equal to "+ value);
					return true;
				}				
			}else {
				logger.debug(compareValue+" is not equal to "+ value);
				if(notFlag==true) {
					return true;
				}
			}
			
		}
		else if(condition.equals("greater than")) {
			if(compareValue > value) {
				if(!notFlag) {
					logger.debug(compareValue+" is greater than  "+ value);
					return true;
				}				
			}else {
				logger.debug(compareValue+" is not greater than  "+ value);
				if(notFlag==true) {
					return true;
				}
			}
		}
		else if(condition.equals("greater than equal to")) {
			if(compareValue >= value) {
				if(!notFlag) {
					logger.debug(compareValue+" is greater than equal to  "+ value);
					return true;
				}				
			}else {
				logger.debug(compareValue+" is not greater than equal to  "+ value);
				if(notFlag==true) {
					return true;
				}
			}
		}
		else if(condition.equals("less than")) {
			if(compareValue < value) {
				if(!notFlag) {
					logger.debug(compareValue+" is less than  "+ value);
					return true;
				}				
			}else {
				logger.debug(compareValue+" is not less than  "+ value);
				if(notFlag==true) {
					return true;
				}
			}
		}
		else if(condition.equals("less than equal to")) {
			if(compareValue <= value) {
				if(!notFlag) {
					logger.debug(compareValue+" is less than equal to "+ value);
					return true;
				}				
			}else {
				logger.debug(compareValue+" is not less than equal to "+ value);
				if(notFlag==true) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkLineItem(OrderAPI orderAPI,OrderLineItems items, List<QualifyingClause> qualList) {
		for(QualifyingClause clause : qualList) {
			String displayName = clause.getFieldList().getDisplayName();
			boolean notFlag = clause.isNotFlag();
			String condition = clause.getConditionList().getConditionValue();
			String value = clause.getValue();			
			long lineItemId = items.getId();
			boolean result = checkSimpleQual(orderAPI, displayName, notFlag, condition, value, lineItemId);
			logger.debug("Non Agg Qual check result for line item= "+result);
			if(result == false) {
				return false;
			}
		}
		return true;
	}

	


	
	

	private static boolean checkSimpleQual(OrderAPI orderAPI,String displayName, boolean notFlag, String condition, String value, long lineItemId) {
		OrderLineItems items = orderAPI.getOrderLineItem(lineItemId);
		// get order detail record of this order line item
		OrderDetail orderDetail = orderAPI.getOrderDetailFromLineItem(lineItemId);
		switch(displayName) {
		case "Discount Percentage":
			if(condition.equals("equal")) {
				if(items.getDiscountPercentage() == Integer.parseInt(value) ){
					if(!notFlag) {
						logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is equal to value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is not equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(items.getDiscountPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is less than value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is not less than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(items.getDiscountPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is greater than value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is not greaater than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getDiscountPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is less than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is not less than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getDiscountPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is greater than equal to value= "+value );
						return true;
					}
				}else {
					logger.debug("Discount Percentage "+items.getDiscountPercentage()+" is not greater than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
					
				}
			}
			break;
		case "Duty Percentage":
			if(condition.equals("equal")) {
				if(items.getDutyPercentage() == Integer.parseInt(value) ){
					if(!notFlag) {
						logger.debug("Duty Percentage "+items.getDutyPercentage()+" is equal to value= "+value );
						return true;
					}
				}else {
					logger.debug("Duty Percentage "+items.getDutyPercentage()+" is not equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(items.getDutyPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+items.getDutyPercentage()+" is less than value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+items.getDutyPercentage()+" is not less than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(items.getDutyPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+items.getDutyPercentage()+" is greater than value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+items.getDutyPercentage()+" is not greater than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getDutyPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+items.getDutyPercentage()+" is less than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+items.getDutyPercentage()+" is not less than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getDutyPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+items.getDutyPercentage()+" is greater than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+items.getDutyPercentage()+" is not greater than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Customer Name":
			String custName =orderDetail.getCustomer().getCustomerName();
			
			if(condition.equals("equal")) {
				if(custName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("starts with")) {
				if(custName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(custName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Office Location":
			String stateName = orderDetail.getOfficeLocation().getAddress().getState().getStateName();
			if(condition.equals("equal")) {
				if(stateName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				value=value.toUpperCase();
				if(stateName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(stateName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Office Name":
			String ofcName = orderDetail.getOfficeLocation().getOfficeName();			
			if(condition.equals("equal")) {
				if(ofcName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				value=value.toUpperCase();
				if(ofcName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(ofcName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Product Type":
			String prod_type = items.getProduct().getProductSubType().getProductType().getProdType();
			if(condition.equals("equal")) {
				if(prod_type.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			else if(condition.equals("starts with")) {
				if(prod_type.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			else if(condition.equals("ends with")) {
				if(prod_type.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}		
			
			break;
		case "Order Total":
			if(condition.equals("equal")) {
				if(items.getSubtotal() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(items.getSubtotal() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(items.getSubtotal() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getSubtotal() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getSubtotal() >= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Quantity":
			if(condition.equals("equal")) {
				if(items.getQuantity() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(items.getQuantity() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(items.getQuantity() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getQuantity() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getQuantity() >= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Product Name":
			String prod_name = items.getProduct().getProductName();
			if(condition.equals("equal")) {
				if(prod_name.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("starts with")) {
				if(prod_name.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(prod_name.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Sale Type":
			String sale_type = orderDetail.getSaleType();
			if(condition.equals("equal")) {
				if(sale_type.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				if(sale_type.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			else if(condition.equals("ends with")) {
				if(sale_type.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			break;
		
			
		}
		return false;
		
		
		
	}

	
	
}
