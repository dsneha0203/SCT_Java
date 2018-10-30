package com.simpsoft.salesCommission.app.calculation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.simpsoft.salesCommission.app.api.CalculationAPI;
import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.api.SplitRuleAPI;
import com.simpsoft.salesCommission.app.model.CalculationSimple;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeManagerMap;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleSimple;

public class CalculateCompAmountSimpRank {
	
	private static final Logger logger = Logger.getLogger(CalculateCompAmountSimpRank.class);
	private static Date startDate=null;
	private static Date endDate=null;
	private static Map<Employee, List<Rule>> empRuleMap = new HashMap<Employee, List<Rule>>();
	private static Map<Date,Date> freqDates =null;
	private static Map<Rule,Map<Date,Date>> rule_freq_map = null;
	private static Map<Employee,Map<Rule,Map<Date,Date>>> emp_rule_freq_map = new HashMap<>();
	private static Map<OrderLineItemsSplit, OrderLineItems> splitLineItemMap =null;
	private static Map<Employee, Map <Rule,Map<Map<Date,Date>,List<List<Double>>>>> emp_rule_date_ordTotal_qty_map = new HashMap<>();
	private static Map <Rule,Map<Map<Date,Date>,List<List<Double>>>> rule_date_ordTotal_qty_map = null;
	private static List<Double> sum_ordTotal_Qty_list = null;
	private static Map<OrderLineItemsSplit, Boolean> splitQualMap = null;
	private static List<Rule> qualifiedRuleListOfEmp = null;
	private static List<Rule> listRules = null;
	private static Map<Rule,Map<Map<Date,Date>, Map<String,Integer>>> ruleMaxValuesMap = null;
	private static Map<Rule,Map<Map<Date,Date>, Map<String,Integer>>> ruleMinValuesMap = null;
	private static Map<Map<Date,Date>,Map<String,Integer>> max_map = null;
	private static Map<Map<Date,Date>,Map<String,Integer>> min_map = null;
	private static Map<Map<Date,Date>,List<List<Double>>> total_map =null;
	private static List<List<Double>> sum_ordTotal_Qty_list_main = null;
	private static Map<Employee,List<Rule>> emp_qual_rule_map = new HashMap<>();
	private static Map<Map<Rule,Map<Date,Date>>,List<Employee>> rule_date_emp_map = new HashMap<>();
	
	private static Map<Employee,Map<OrderLineItemsSplit, Boolean>> empSplitQualMap = new HashMap<>();
	private static HashMap<Long,HashMap<Rule, List<List<Double>>>> empAssg_rule_ordTotal_qty = null;
	private static Map<Long, List<Rule>> rule_ruleassg = new HashMap<Long, List<Rule>>();
	private static HashMap<Rule, List<Double>> rule_output_map = null;
	private static Map<Map<Rule, Map<Date,Date>>, List<Employee>> finalEmpRuleDatesMap = new HashMap<>();
	private static Map<Employee,Map<Rule,Map<Map<Date,Date>, Map<String,Integer>>>> max_rule_output_val_emp_map = new HashMap<>();
	private static Map<Employee,Map<Rule,Map<Map<Date,Date>, Map<String,Integer>>>> min_rule_output_val_emp_map = new HashMap<>();
	
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
		RoleAPI roleAPI = (RoleAPI)context.getBean("roleApi");
		
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
		
		
		// find all rule assignments for each employee where rule calculation mode is rank
		getRankRuleListEmpMap(empAPI, ruleAssAPI, calcAPI);
		
		
		if(empRuleMap.size() > 0) {
			logger.debug("---PRINTING EMP RULE MAP ---");
			for(Map.Entry<Employee, List<Rule>> entry : empRuleMap.entrySet()) {
				logger.debug("EMPLOYEE NAME IN EMP RULE MAP = "+entry.getKey().getEmployeeName());
				List<Rule> ruleList = entry.getValue();
				for(Rule rule : ruleList) {
					logger.debug("RULE NAME FOR EMP IN EMP RULE MAP = "+ rule.getRuleName());
				}
			}
		}
		// from the rank rule list check which rules are satisfied for an emp
				for(Map.Entry<Employee, List<Rule>> entry : empRuleMap.entrySet()) {
					Employee emp = entry.getKey();
					max_map = new HashMap<>();
					min_map = new HashMap<>();
					validateRule(ruleAPI, orderAPI, ruleAssAPI, splitRuleAPI, calcAPI, entry);
					emp_qual_rule_map.put(entry.getKey(), qualifiedRuleListOfEmp);
					
				}
				
		if(emp_rule_freq_map.size() > 0) {
			logger.debug("---PRINTING EMP RULE FREQ MAP ---");
			for(Map.Entry<Employee, Map<Rule,Map<Date,Date>>> entry : emp_rule_freq_map.entrySet()) {
				
				Employee employee = entry.getKey();
				logger.debug("EMPLOYEE NAME= "+employee.getEmployeeName());
				Map<Rule,Map<Date,Date>> map = entry.getValue();
				for(Map.Entry<Rule, Map<Date,Date>> entry2 : map.entrySet()) {
					logger.debug("RULE NAME= "+entry2.getKey().getRuleName());
					Rule qualifiedRule = entry2.getKey();
					Map<Date,Date> dateMap = entry2.getValue();
					for(Map.Entry<Date, Date> entry3 : dateMap.entrySet()) {
						logger.debug("RULE START DATE= "+entry3.getKey());
						logger.debug("RULE END DATE= "+entry3.getValue());
						
						logger.debug("---print the max values--- ");
						for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,Map<String,Integer>>>> entry_max : max_rule_output_val_emp_map.entrySet()) {
							Employee key_emp = entry_max.getKey();
							if(key_emp == employee) {
								for(Map.Entry<Rule,Map<Map<Date,Date>,Map<String,Integer>>> entry_rule_max : entry_max.getValue().entrySet()) {
									Rule keyRule = entry_rule_max.getKey();
									if(keyRule == qualifiedRule) {
										for(Map.Entry<Map<Date,Date>,Map<String,Integer>> dateMaxValueEntry : entry_rule_max.getValue().entrySet()) {
											Map<Date,Date> keyDateMap = dateMaxValueEntry.getKey();
											for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
												if(dateEntry.getKey().equals(entry3.getKey()) && dateEntry.getValue().equals(entry3.getValue())) {
													logger.debug(dateMaxValueEntry.getValue());
												}
											}
										}
									}
								}
							}
						}
						
						logger.debug("---print the min values--- ");
						for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,Map<String,Integer>>>> entry_min : min_rule_output_val_emp_map.entrySet()) {
							Employee key_emp = entry_min.getKey();
							if(key_emp == employee) {
								for(Map.Entry<Rule,Map<Map<Date,Date>,Map<String,Integer>>> entry_rule_min : entry_min.getValue().entrySet()) {
									Rule keyRule = entry_rule_min.getKey();
									if(keyRule == qualifiedRule) {
										for(Map.Entry<Map<Date,Date>,Map<String,Integer>> dateMinValueEntry : entry_rule_min.getValue().entrySet()) {
											Map<Date,Date> keyDateMap = dateMinValueEntry.getKey();
											for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
												if(dateEntry.getKey().equals(entry3.getKey()) && dateEntry.getValue().equals(entry3.getValue())) {
													logger.debug(dateMinValueEntry.getValue());
												}
											}
										}
									}
								}
							}
						}
						
						logger.debug("---print the quantity and order total values---");
						for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,List<List<Double>>>>> entry_sum : emp_rule_date_ordTotal_qty_map.entrySet()) {
							Employee key_emp = entry_sum.getKey();
							if(key_emp == employee) {
								for(Map.Entry<Rule,Map<Map<Date,Date>,List<List<Double>>>> entry_rule_sum : entry_sum.getValue().entrySet()) {
									Rule keyRule = entry_rule_sum.getKey();
									if(keyRule == qualifiedRule) {
										for(Map.Entry<Map<Date,Date>,List<List<Double>>> dateSumValueEntry : entry_rule_sum.getValue().entrySet()) {
											Map<Date,Date> keyDateMap = dateSumValueEntry.getKey();
											for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
												if(dateEntry.getKey().equals(entry3.getKey()) && dateEntry.getValue().equals(entry3.getValue())) {
													logger.debug(dateSumValueEntry.getValue());
												}
											}
										}
									}
								}
							}
						}
				}
				
			}
				
			}
		}
		
		
		// find all the employees to whom a particular satisfied rule is applied in between a particular start and end date
		logger.debug("----EMP RULE FREQ MAP EMPLIST----");
		for(Map.Entry<Employee, Map<Rule, Map<Date,Date>>> entry : emp_rule_freq_map.entrySet()) {
			List<Employee> empList = new ArrayList<>();
			logger.debug("ENTRY KEY EMP NAME= "+ entry.getKey() );
			
			Map<Rule,Map<Date,Date>> ruleDatesMap = entry.getValue();
			for(Map.Entry<Rule, Map<Date,Date>> entry_main : ruleDatesMap.entrySet()) {
				logger.debug("ENTRY RULE NAME= "+entry_main.getKey().getRuleName());
				logger.debug("ENTRY MAP DETAILS= "+entry_main.getValue());
				for(Map.Entry<Employee, Map<Rule,Map<Date,Date>>> entry2: emp_rule_freq_map.entrySet()) {
					if(entry.getKey().getEmployeeName() != entry2.getKey().getEmployeeName()) {
						logger.debug("ENTRY2 KEY EMP NAME= "+ entry.getKey() );
						logger.debug("ENTRY2 MAP DETAILS= "+entry2.getValue());
						Map<Rule,Map<Date,Date>> ruleDatesMap2 = entry2.getValue();
						for(Map.Entry<Rule, Map<Date,Date>> entry2_main : ruleDatesMap2.entrySet()) {
							if(entry_main.getValue() == entry2_main.getValue()) {
								logger.debug("MAP DETAILS= "+entry_main.getValue());
								empList.add(entry2.getKey());
							}
						}
						
					}
				}
			}
			
			empList.add(entry.getKey());
			
			for(Employee emp : empList) {
				logger.debug("EMP NAME= "+emp.getEmployeeName());
			}
			rule_date_emp_map.put(entry.getValue(), empList);
		}
		
		logger.debug("---PRINTING THE ELEMENTS OF RULE DATE EMP MAP ----");
		String prevRuleName="";
		int counter=0;
		
		for(Map.Entry<Map<Rule,Map<Date,Date>>, List<Employee>> entry : rule_date_emp_map.entrySet()) {
			Map<Rule,Map<Date,Date>> ruleDateMap = entry.getKey();
			for(Map.Entry<Rule,Map<Date,Date>> map : ruleDateMap.entrySet()) {
//				logger.debug("RULE NAME= "+ map.getKey().getRuleName());
				if(counter==0) {
					prevRuleName = map.getKey().getRuleName();
					counter+=1;

				Map<Date,Date> datesMap = map.getValue();
				for(Map.Entry<Date, Date> entry2 : datesMap.entrySet()) {
					getEmpList(map.getKey(),entry2.getKey(),entry2.getValue());
					
				}
				
				}else {
					if(!prevRuleName.equals(map.getKey().getRuleName())) {
						Map<Date,Date> datesMap = map.getValue();
						for(Map.Entry<Date, Date> entry2 : datesMap.entrySet()) {
							getEmpList(map.getKey(),entry2.getKey(),entry2.getValue());
						}
						prevRuleName = map.getKey().getRuleName();
					}
				}
				
				
			}
			
			
		}
		
		logger.debug("---PRINTING THE ELEMENTS OF FINAL RULE DATE EMP MAP ----");
		for(Map.Entry<Map<Rule,Map<Date,Date>>, List<Employee>> entry : finalEmpRuleDatesMap.entrySet()) {
			Map<Rule,Map<Date,Date>> ruleDateMap = entry.getKey();
			for(Map.Entry<Rule,Map<Date,Date>> map : ruleDateMap.entrySet()) {
				logger.debug("RULE NAME= "+ map.getKey().getRuleName());
				RuleSimple ruleSimple = ruleAPI.findSimpleRule(map.getKey().getId());
				logger.debug("RULE POPULATION TYPE= "+ ruleSimple.getPopulationType());
				Map<Date,Date> datesMap = map.getValue();
				for(Map.Entry<Date, Date> entry2 : datesMap.entrySet()) {
					logger.debug("START DATE= "+entry2.getKey());
					logger.debug("END DATE= "+entry2.getValue());
					Date start_date = entry2.getKey();
					Date end_date = entry2.getValue();
					List<Employee> empList = entry.getValue();
					logger.debug("empList size= "+ empList.size());
					for(Employee emp : empList) {
						logger.debug("EMPLOYEE NAME= "+emp.getEmployeeName());
					}
					
					//get a list of all employees with same role for whom the same rule is qualified 
					if(ruleSimple.getPopulationType().equals("SameRole")) {
						Map<Rule,Map<Role,List<Employee>>> rule_role_emplist_map =new HashMap<>();
						Map<Role,List<Employee>> role_emplist_map = new HashMap<>();
						List<Role> roleList = roleAPI.listOfRoles();
						for(Role role : roleList) {
							logger.debug("ROLE= "+role.getRoleName());
							List<Employee> employeeList = new ArrayList<>();
							for(Employee emp : empList) {
								List<EmployeeRoleMap> employeeRoleMaps = emp.getEmployeeRoleMap();
								Role currentRole= null;
								for(EmployeeRoleMap employeeRoleMap : employeeRoleMaps) {
									currentRole = employeeRoleMap.getRole();
									
								}
								logger.debug("CURRENT ROLE= "+currentRole.getRoleName());
								if(currentRole.getRoleName().equals(role.getRoleName())) {
									employeeList.add(emp);
									logger.debug("EMPLOYEE NAME WITH SAME ROLE= "+emp.getEmployeeName());
								}
								
							}
							if(employeeList.size() > 0) {
							role_emplist_map.put(role, employeeList);
							}
						}
						rule_role_emplist_map.put(map.getKey(), role_emplist_map);
						doRankingSameRole(rule_role_emplist_map,start_date,end_date,ruleAPI);
					}
					
					//get a list of all employees with same manager for whom the same rule is qualified
					if(ruleSimple.getPopulationType().equals("SameReportingManager")) {
						Map<Rule,Map<Employee,List<Employee>>> rule_manager_emplist_map =new HashMap<>();
						Map<Employee,List<Employee>> manager_emplist_map = new HashMap<>();
						List<Employee> all_emp_list = empAPI.listEmployees();
						for(Employee manager : all_emp_list) {
							logger.debug("MANAGER NAME= "+ manager.getEmployeeName());
							List<Employee> employeeList = new ArrayList<>();
							for(Employee emp : empList) {
								List<EmployeeManagerMap> employeeManagerMaps = emp.getEmployeeManagerMap();
								Employee currentManager = null;
								for(EmployeeManagerMap employeeManagerMap : employeeManagerMaps) {
									currentManager = employeeManagerMap.getManager();
								}
								logger.debug("CURRENT MANAGER NAME= "+currentManager.getEmployeeName());
								if(currentManager.getEmployeeName().equals( manager.getEmployeeName())) {
									employeeList.add(emp);
									logger.debug("EMPLOYEE NAME WITH SAME REPORTING MANAGER= "+emp.getEmployeeName());
								}
								
							}
							if(employeeList.size() > 0) {
							manager_emplist_map.put(manager, employeeList);
							}
						}
						rule_manager_emplist_map.put(map.getKey(), manager_emplist_map);
						doRankingSameMgr(rule_manager_emplist_map,start_date,end_date);
					}
					
					
					// for global upto
					if(ruleSimple.getPopulationType().equals("global upto")) {
						
					}
				}
			}
		}
		
	}

	


	private static void getEmpList(Rule key, Date startDate, Date endDate) {
		logger.debug("---IN GET EMP LIST METHOD ---");
		List<Employee> empList = new ArrayList<>();
		Map<Date,Date> dateMap = new HashMap<>();
		dateMap.put(startDate, endDate);
		Map<Rule,Map<Date,Date>> rule_date_map = new HashMap<>();
		rule_date_map.put(key, dateMap);
		logger.debug("rule_date_map = "+rule_date_map);
		for(Map.Entry<Map<Rule,Map<Date,Date>>, List<Employee>> entry : rule_date_emp_map.entrySet()) {
			Map<Rule,Map<Date,Date>> ruleDateMap = entry.getKey();
			for(Map.Entry<Rule,Map<Date,Date>> map : ruleDateMap.entrySet()) {
				Rule rule_key = map.getKey();
				Map<Date,Date> datesMap = map.getValue();
				for(Map.Entry<Date, Date> entry2 : datesMap.entrySet()) {
					Map<Rule,Map<Date,Date>> rule_date_map2 = new HashMap<>();
					Map<Date,Date> dateMap2 = new HashMap<>();
					Date startDate2 = entry2.getKey();
					Date endDate2 = entry2.getValue();
					dateMap2.put(startDate2, endDate2);
					rule_date_map2.put(rule_key, dateMap2);
					logger.debug("rule_date_map2 = "+ rule_date_map2);
					logger.debug("key= "+key.getRuleName() +" ,rule_key= "+ rule_key.getRuleName()+" ,startDate= "+startDate+" ,startDate2= "+startDate2+" ,endDate= "+endDate+" ,endDate2= "+endDate2);
					if(key.getRuleName().equals(rule_key.getRuleName()) && startDate.equals(startDate2) && endDate.equals(endDate2)) {
						logger.debug("MAPS MATCH");
						for(Employee emp : entry.getValue()) {
							empList.add(emp);
						}
						
					}else {
						logger.debug("MAPS DO NOT MATCH");
					}
				}
			}
			
			
			
		}
		finalEmpRuleDatesMap.put(rule_date_map, empList);
		
	}




	private static void doRankingSameMgr(Map<Rule, Map<Employee, List<Employee>>> rule_manager_emplist_map, Date start_date, Date end_date) {
		logger.debug("SAME MANAGER MAP SIZE= "+rule_manager_emplist_map.size());
		if(rule_manager_emplist_map.size() > 0) {
			for(Map.Entry<Rule, Map<Employee, List<Employee>>> entry : rule_manager_emplist_map.entrySet()) {
				Rule rule = entry.getKey();
				logger.debug("MAP RULE NAME= "+rule.getRuleName());
				Map<Employee, List<Employee>> mgr_emplist_map = entry.getValue();
				for(Map.Entry<Employee, List<Employee>> entry2 : mgr_emplist_map.entrySet()) {
					Employee manager = entry2.getKey();
					logger.debug("MANAGER NAME= "+manager.getEmployeeName());
					List<Employee> empList = entry2.getValue();
					for(Employee emp : empList) {
						logger.debug("QUALIFIED EMP NAME= "+emp.getEmployeeName());
						
					}
				}
			}
		}
	}




	private static void doRankingSameRole(Map<Rule, Map<Role, List<Employee>>> rule_role_emplist_map, Date start_date, Date end_date, RuleAPI ruleAPI) {
		logger.debug("SAME ROLE MAP SIZE= "+rule_role_emplist_map.size());
		logger.debug("RULE_ROLE_EMPLIST_MAP = "+rule_role_emplist_map);
		if(rule_role_emplist_map.size()> 0) {
			for(Map.Entry<Rule, Map<Role, List<Employee>>> entry : rule_role_emplist_map.entrySet()) {
				Rule rule = entry.getKey();
				logger.debug("MAP RULE NAME= "+rule.getRuleName());
				Map<Role, List<Employee>> role_emplist_map = entry.getValue();
				for(Map.Entry<Role, List<Employee>> entry2 : role_emplist_map.entrySet()) {
					Role role = entry2.getKey();
					logger.debug("ROLE NAME= "+role.getRoleName());
					List<Employee> empList = entry2.getValue();
					for(Employee emp : empList) {
						logger.debug("QUALIFIED EMP NAME= "+emp.getEmployeeName());
						
					}
					Map<Employee, Integer> empRankMap = doRank(rule,empList,start_date,end_date,ruleAPI);
				}
			}
		}
	}




	private static Map<Employee, Integer> doRank(Rule rule, List<Employee> empList, Date start_date, Date end_date, RuleAPI ruleAPI) {
		Map<Employee, Integer> empRankMap = new HashMap<>();
		RuleSimple ruleSimple = ruleAPI.findSimpleRule(rule.getId());
		int rankCount = ruleSimple.getRankCount();
		String rankingType = ruleSimple.getRankingType();
		String aggFuncName = ruleSimple.getAggregateFunctions().getFunctionName();
		String field = ruleSimple.getField();
		
		
		
		return empRankMap;
	}




	/**
	 * @param ruleAPI
	 * @param orderAPI
	 * @param ruleAssAPI 
	 * @param splitRuleAPI
	 * @param calcAPI
	 * @param entry
	 * @throws ScriptException 
	 */
	private static void validateRule(RuleAPI ruleAPI, OrderAPI orderAPI, RuleAssignmentAPI ruleAssAPI, SplitRuleAPI splitRuleAPI,
			CalculationAPI calcAPI, Map.Entry<Employee, List<Rule>> entry) throws ScriptException {
		logger.debug("---validateRule method is called---");
		Employee emp = entry.getKey();
		
		
		List<Rule> rankRuleList = entry.getValue();
		splitLineItemMap = new HashMap<>();
		rule_date_ordTotal_qty_map = new HashMap<>();
		splitQualMap = new HashMap<>();
		qualifiedRuleListOfEmp = new ArrayList<>();
		listRules = new ArrayList<Rule>();
		ruleMaxValuesMap = new HashMap<>();
		ruleMinValuesMap = new HashMap<>();
		
//			find order line item split list for the employee
				logger.debug("---FINDING ORDER LINE SPLIT DATA FOR THE EMPLOYEE= "+emp.getEmployeeName()+"---");
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
		
		
		if(rankRuleList.size() > 0) {
			
			// for each rule find its corresponding start and end dates
			for(Rule rule : rankRuleList) {
				
				Date ruleCalcStartDate=new Date();
				Date ruleCalcEndDate=new Date();
				total_map =new HashMap<>();
				for(Map.Entry<Employee,Map<Rule, Map<Date,Date>>> emp_rule_dates_map : emp_rule_freq_map.entrySet()) {
					Employee employee = emp_rule_dates_map.getKey();
					if(emp==employee) {
						logger.debug("----FOR EMPLOYEE= "+emp.getEmployeeName()+"----");
						Map<Rule,Map<Date,Date>> rule_dates_map = emp_rule_dates_map.getValue();
						for(Map.Entry<Rule, Map<Date,Date>> entry1 : rule_dates_map.entrySet()) {
							Rule keyRule = entry1.getKey();
							if(keyRule == rule) {
								logger.debug("----CORRESPONDING RULE TO BE TESTED= "+keyRule.getRuleName()+"---");
								Map<Date,Date> dates = entry1.getValue();
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
											logger.debug(items.getId()+" is qualified for rule= "+rule.getRuleName()+" for "
													+ "rule calc start date= "+ruleCalcStartDate+" and rule calc "
															+ "end date = "+ruleCalcEndDate+" for emp= "+emp.getEmployeeName());
											qualifiedLineItemsList.add(items);
										}else {
											logger.debug(items.getId()+" is NOT qualified for rule= "+rule.getRuleName()+" for "
													+ "rule calc start date= "+ruleCalcStartDate+" and rule calc "
															+ "end date = "+ruleCalcEndDate+" for emp= "+emp.getEmployeeName());
										}
									
										
									}
									logger.debug("SIZE OF QUALIFIED LINE ITEMS LIST FOR RULE ="+keyRule.getRuleName()+" AND EMP= "+emp.getEmployeeName()+"IS = "
									+ qualifiedLineItemsList.size());
									if(!qualifiedLineItemsList.isEmpty()) {
										compareLineItem(calcAPI, orderAPI, ruleAPI,ruleAssAPI, qualifiedLineItemsList, rule,emp,ruleCalcStartDate,ruleCalcEndDate);
									}
									
									
								}
							}
						}
						
				
						
						
						
						}
						
				
					
				}
				
				
			}
		}
		
		
		
	}

	private static void compareLineItem(CalculationAPI calcAPI, OrderAPI orderAPI, RuleAPI ruleAPI,
			RuleAssignmentAPI ruleAssAPI, List<OrderLineItems> qualifiedLineItemsList, Rule rule, Employee emp, Date ruleCalcStartDate, Date ruleCalcEndDate) throws ScriptException {
	if(rule.getRuleType().equalsIgnoreCase("simple")){
			
			//check whether the simple rule is individual
			RuleSimple ruleSimple = ruleAPI.findSimpleRule(rule.getId());
			if(ruleSimple.getCalculationMode().equals("rank")) {
				boolean added=false;
				sum_ordTotal_Qty_list = new ArrayList<>();
				sum_ordTotal_Qty_list_main =new ArrayList<>();
				
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
				logger.debug("FILTEREDLINEITEMSLIST FOR RULE = "+rule.getRuleName()+" AND EMP= "+emp.getEmployeeName());
				for(OrderLineItems filteredItem : filteredLineItemsList) {
					logger.debug("FILTERED LINE ITEM ID= "+filteredItem.getId());
					
				}
				
				
				// compare list of line items with agg qual clause
				if(aggQualList.size() > 0 && filteredLineItemsList.size()>0) {
					logger.debug("AGG QUAL LIST AND FILTERED LINE ITEM LIST SIZE > 0 ");
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
							logger.debug("AGG QUAL CLAUSE = SUM");
							
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

							
						}
						
						else if(aggClause.getAggregateFunctions().getFunctionName().equals("min")) {
												
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
//						logger.debug("FLAG IS ZERO");
						logger.debug("Adding "+rule.getRuleName()+"to the list");
						qualifiedRuleListOfEmp.add(rule);
						added=true;
						listRules.add(rule);
						
						Map<Date,Date> dateMap = new HashMap<>();
						dateMap.put(ruleCalcStartDate, ruleCalcEndDate);
						

						max_map.put(dateMap, maxValues);
						ruleMaxValuesMap.put(rule, max_map);
						max_rule_output_val_emp_map.put(emp, ruleMaxValuesMap);
						min_map.put(dateMap, minValues);
						ruleMinValuesMap.put(rule,min_map );
						min_rule_output_val_emp_map.put(emp, ruleMinValuesMap);
						total_map.put(dateMap, sum_ordTotal_Qty_list_main);
						rule_date_ordTotal_qty_map.put(rule,total_map);
						emp_rule_date_ordTotal_qty_map.put(emp, rule_date_ordTotal_qty_map);

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
				Map<Date,Date> dateMap = new HashMap<>();
				dateMap.put(ruleCalcStartDate, ruleCalcEndDate);
				if(added=true) {
					
					sum_ordTotal_Qty_list.add(orderTotal);
					sum_ordTotal_Qty_list.add(quantity);
					sum_ordTotal_Qty_list_main.add(sum_ordTotal_Qty_list);
					
					
					

					max_map.put(dateMap, maxValues);
					logger.debug("DATE MAP = "+dateMap);
					ruleMaxValuesMap.put(rule, max_map);
					logger.debug("RULE MAX VALUES MAP= "+ruleMaxValuesMap);
					max_rule_output_val_emp_map.put(emp, ruleMaxValuesMap);
					logger.debug("MAX RULE OUTPUT VAL EMP MAP= "+max_rule_output_val_emp_map);


					min_map.put(dateMap, minValues);
					ruleMinValuesMap.put(rule,min_map );
					logger.debug("RULE MIN VALUES MAP= "+ruleMinValuesMap);
					min_rule_output_val_emp_map.put(emp, ruleMinValuesMap);
					logger.debug("MIN RULE OUTPUT VAL EMP MAP= "+min_rule_output_val_emp_map);
					

					total_map.put(dateMap, sum_ordTotal_Qty_list_main);
					rule_date_ordTotal_qty_map.put(rule,total_map);
					emp_rule_date_ordTotal_qty_map.put(emp, rule_date_ordTotal_qty_map);
					logger.debug("EMP RULE DATE ORD TOTAL QTY MAP= "+emp_rule_date_ordTotal_qty_map);
					
					
				}

			}		
		
		}
	
		if(qualifiedRuleListOfEmp != null) {
			if(qualifiedRuleListOfEmp.size() > 0 ) {
				RuleAssignment assignment = ruleAssAPI.searchAssignedRuleForEmployee(emp.getId());
				if(assignment != null) {
					rule_ruleassg.put(assignment.getId(), qualifiedRuleListOfEmp);
				}
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
							Rule rule1 = rule_dates_map.getKey();
							if(rule1== satisfiedRule) {
								Date ruleCalcStartDate1 = new Date();
								Date ruleCalcEndDate1 = new Date();
								Map<Date, Date> fixedDates = rule_dates_map.getValue();
								for(Map.Entry<Date, Date> dateMap : fixedDates.entrySet()) {
									ruleCalcStartDate1 = dateMap.getKey();
									ruleCalcEndDate1 = dateMap.getValue();
								}
								
								logger.debug("---DATA TO BE SAVED FOR FIXED RULE---");
								logger.debug("EMP ID = "+emp.getId());
								logger.debug("RULE ID= "+rule1.getId());
								logger.debug("CALC START DATE= "+ruleCalcStartDate1);
								logger.debug("CALC END DATE= "+ruleCalcEndDate1);
								logger.debug("COMP AMT= "+satisfiedRule.getFixedCompValue());
								
//								CalculationSimple calculationSimple = new CalculationSimple();
//								calculationSimple.setCalStartDate(ruleCalcStartDate);
//								calculationSimple.setCalEndDate(ruleCalcEndDate);
//								calculationSimple.setCompensationAmount((double) satisfiedRule.getFixedCompValue());
//								calculationSimple.setDummyCalcInternal(false);
//								calculationSimple.setRule(satisfiedRule);
//								calculationSimple.setEmployee(emp);
//								
//								calcSimpList.add(calculationSimple);
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
								
								
//								logger.debug("empAssg_rule_ordTotal_qty size= "+empAssg_rule_ordTotal_qty.size());
								
								// code for rule output
								double rule_output=0;
								RuleSimple ruleSimple = ruleAPI.findSimpleRule(satisfiedRule.getId());
								String agg_func_name = ruleSimple.getAggregateFunctions().getFunctionName();
								logger.debug("agg_func_name= "+agg_func_name);
								if(agg_func_name.equals("sum")) {
									rule_output_map=new HashMap<Rule, List<Double>>();
									List<Double> rule_output_list = new ArrayList<>();		

									for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,List<List<Double>>>>> entry_sum : emp_rule_date_ordTotal_qty_map.entrySet()) {
										Employee key_emp = entry_sum.getKey();
										if(key_emp == emp) {
											for(Map.Entry<Rule,Map<Map<Date,Date>,List<List<Double>>>> entry_rule_sum : entry_sum.getValue().entrySet()) {
												Rule keyRule = entry_rule_sum.getKey();
												if(keyRule == satisfiedRule) {
													for(Map.Entry<Map<Date,Date>,List<List<Double>>> dateSumValueEntry : entry_rule_sum.getValue().entrySet()) {
														Map<Date,Date> keyDateMap = dateSumValueEntry.getKey();
														for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
															if(dateEntry.getKey().equals(ruleCalcStartDate) && dateEntry.getValue().equals(ruleCalcEndDate)) {
																logger.debug(dateSumValueEntry.getValue());
																List<List<Double>> listVal = dateSumValueEntry.getValue();
																for(List<Double> list : listVal) {
																	if(ruleSimple.getField().equalsIgnoreCase("Order Total")) {																	
																		rule_output = list.get(0);
																		logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
																		
																	}else {
																		rule_output = list.get(1);
																		logger.debug("RULE_OUPTUT_VALUE= "+rule_output);
																	}
																	rule_output_list.add(rule_output);
																}
															
																
															}
															rule_output_map.put(keyRule, rule_output_list);
														}
													}
												}
											}
										}
									}	
								}
								else if(agg_func_name.equals("max")) {
									rule_output_map=new HashMap<Rule, List<Double>>();
									List<Double> rule_output_list = new ArrayList<>();

									for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,Map<String,Integer>>>> entry_max : max_rule_output_val_emp_map.entrySet()) {
										Employee key_emp = entry_max.getKey();
										if(key_emp == emp) {
											for(Map.Entry<Rule,Map<Map<Date,Date>,Map<String,Integer>>> entry_rule_max : entry_max.getValue().entrySet()) {
												Rule keyRule = entry_rule_max.getKey();
												if(keyRule == satisfiedRule) {
													for(Map.Entry<Map<Date,Date>,Map<String,Integer>> dateMaxValueEntry : entry_rule_max.getValue().entrySet()) {
														Map<Date,Date> keyDateMap = dateMaxValueEntry.getKey();
														for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
															if(dateEntry.getKey().equals(ruleCalcStartDate) && dateEntry.getValue().equals(ruleCalcEndDate)) {
																logger.debug(dateMaxValueEntry.getValue());
																Map<String,Integer> map = dateMaxValueEntry.getValue();
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
													}
												}
											}
										}									
								}
								else if(agg_func_name.equals("min")) {
									rule_output_map=new HashMap<Rule, List<Double>>();
									List<Double> rule_output_list = new ArrayList<>();

									for(Map.Entry<Employee, Map<Rule,Map<Map<Date,Date>,Map<String,Integer>>>> entry_min : min_rule_output_val_emp_map.entrySet()) {
										Employee key_emp = entry_min.getKey();
										if(key_emp == emp) {
											for(Map.Entry<Rule,Map<Map<Date,Date>,Map<String,Integer>>> entry_rule_min : entry_min.getValue().entrySet()) {
												Rule keyRule = entry_rule_min.getKey();
												if(keyRule == satisfiedRule) {
													for(Map.Entry<Map<Date,Date>,Map<String,Integer>> dateMinValueEntry : entry_rule_min.getValue().entrySet()) {
														Map<Date,Date> keyDateMap = dateMinValueEntry.getKey();
														for(Map.Entry<Date, Date> dateEntry : keyDateMap.entrySet()) {
															if(dateEntry.getKey().equals(ruleCalcStartDate) && dateEntry.getValue().equals(ruleCalcEndDate)) {
																logger.debug(dateMinValueEntry.getValue());
																Map<String,Integer> map = dateMinValueEntry.getValue();
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
													}
												}
											}
										}
								}
//								else {
//									// for count
//									
//								}
							}else {
								if(rule_ruleassg != null && !rule_ruleassg.isEmpty()) {
									for(Map.Entry<Long, List<Rule>> entry : rule_ruleassg.entrySet()) {
										long assgId = entry.getKey();
										logger.debug("ASSGID= "+assgId);
										List<Rule> rules= entry.getValue();
										if(rules != null) {
//										logger.debug("LIST OF RULES SIZE= "+ rules.size());
										if(rules.size() > 0) {
										for(Rule rule1: rules) {
											if(satisfiedRule == rule1) {													
												logger.debug("ASSG RULE NAME= "+satisfiedRule.getRuleName());
												long detailsId = calcAPI.getAssignmentDetailId(assgId, satisfiedRule);
												logger.debug("ASSG DETAILS ID = "+detailsId);
												if(detailsId != 0) {
													//get the rule calc start and end dates of the rule
													for(Map.Entry<Employee,Map<Rule, Map<Date,Date>>> emp_rule_dates_map : emp_rule_freq_map.entrySet()) {
														Employee key_emp = emp_rule_dates_map.getKey();
														if(key_emp == emp) {
															Map<Rule, Map<Date,Date>> rule_dates= emp_rule_dates_map.getValue();
															for(Map.Entry<Rule, Map<Date,Date>>  rule_dates_map : rule_dates.entrySet()) {
																Rule keyRule = rule_dates_map.getKey();
																if(keyRule == rule1) {
																	Map<Date,Date> dates_map = rule_dates_map.getValue();
																	for(Map.Entry<Date, Date> entry2 : dates_map.entrySet()) {
																		Date ruleCalcStartDate1 = entry2.getKey();
																		Date ruleCalcEndDate1 = entry2.getValue();
																		// get the parameter value
																		int param_val = calcAPI.getParameterValue(param, detailsId,emp.getId(),
																				ruleCalcStartDate1, ruleCalcEndDate1);
																		logger.debug("PARAM VALUE= "+param_val);
																		paramValues.add((double)param_val);
																		break;
																	}
																	break;
																}
															}
														
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
						
							}
						}
						logger.debug("rule output map size= "+ rule_output_map.size());
						if(rule_output_map.size() > 0) {
							if(!prevRule.equalsIgnoreCase(satisfiedRule.getRuleName())) {
								for(Map.Entry<Rule, List<Double>> entry : rule_output_map.entrySet()) {
									Rule rule1= entry.getKey();
									if(rule1 == satisfiedRule) {
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
													if(keyRule == rule1) {
														CalculationSimple calculationSimple = new CalculationSimple();
														Date ruleCalcStartDate1=new Date();
														Date ruleCalcEndDate1=new Date();
														Map<Date,Date> dates = rule_dates_map.getValue();
														Object startDate_key = dates.keySet().toArray()[count_loop];
														Object endDate_val = dates.get(startDate_key);
															
															ruleCalcStartDate1 = (Date) startDate_key;
															ruleCalcEndDate1 = (Date) endDate_val;
															
															
															
															logger.debug("---DATA TO BE SAVED---");
															logger.debug("EMP ID = "+emp.getId());
															logger.debug("RULE ID= "+rule1.getId());
															logger.debug("CALC START DATE= "+ruleCalcStartDate1);
															logger.debug("CALC END DATE= "+ruleCalcEndDate1);
															logger.debug("COMP AMT= "+compAmt);
															
//															calculationSimple.setCalStartDate(ruleCalcStartDate1);
//															calculationSimple.setCalEndDate(ruleCalcEndDate1);
//															calculationSimple.setCompensationAmount((double) compAmt);
//															calculationSimple.setDummyCalcInternal(false);
//															calculationSimple.setRule(satisfiedRule);
//															calculationSimple.setEmployee(emp);
//															
//															calcSimpList.add(calculationSimple);
															
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
		
		
	}

	private static boolean checkAggQual(double compareValue, boolean notFlag, String condition, double value) {
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

	private static boolean checkLineItem(OrderAPI orderAPI, OrderLineItems items,
			List<QualifyingClause> qualList) {
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

	/**
	 * @param empAPI
	 * @param ruleAssAPI
	 * @param calcAPI
	 * @throws ParseException 
	 */
	private static void getRankRuleListEmpMap(EmployeeAPI empAPI, RuleAssignmentAPI ruleAssAPI,CalculationAPI calcAPI) throws ParseException {
		List<Employee> empList = empAPI.listEmployees();
		for(Employee emp : empList) {
			
			rule_freq_map = new HashMap<Rule,Map<Date,Date>>();
			// for each employee, get a list of assigned rules of connection mode rank			
			List<Rule> rankRuleList = new ArrayList<>();
			RuleAssignment assignment = ruleAssAPI.searchAssignedRuleForEmployee(emp.getId());
			if(assignment != null) {
//				empAssg_rule_ordTotal_qty = new HashMap<Long, HashMap<Rule,List<List<Double>>>>();
//				rule_ruleassg = new HashMap<Long, List<Rule>>();
				List<RuleAssignmentDetails> assignmentDetails = assignment.getRuleAssignmentDetails();
				
				
				for(RuleAssignmentDetails details : assignmentDetails) {
					Rule rule = details.getRule();
					RuleSimple ruleSimple = rule.getRuleSimple();
					if(ruleSimple.getCalculationMode().equalsIgnoreCase("rank")) {
						// add only those rules that satisfy the date range
						boolean valid= calcAPI.checkValidity(ruleAssAPI, details.getId(), startDate, endDate);
						if(valid == true) {
							rankRuleList.add(rule);
							
							
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

								}
								
								else if(freq.equals("monthly")) {
									freqDates=calcAPI.getFullMonths(planStartDate, planEndDate, startDate, endDate);
									rule_freq_map.put(details.getRule(), freqDates);

								}
								
								 else if(freq.equals("quaterly")) {
									 freqDates=calcAPI.getFullQuarters(planStartDate, planEndDate, startDate, endDate);
									 rule_freq_map.put(details.getRule(), freqDates);

								 }
								
								 else if(freq.equals("half-yearly")) {
									 freqDates=calcAPI.getFullHalves(planStartDate, planEndDate, startDate, endDate);
									 rule_freq_map.put(details.getRule(), freqDates);

									}
								
								else if(freq.equals("annually")) {
									freqDates=calcAPI.getFullYears(planStartDate, planEndDate, startDate, endDate);
									rule_freq_map.put(details.getRule(), freqDates);

									}
								
							}else {
								logger.debug("FIXED RULE");
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
							
						}
						
					}
				}
				
//				empAssg_rule_ordTotal_qty.put(assignment.getId(), rule_ordTotal_qty);
//				rule_ruleassg.put(assignment.getId(), listRules);
//				empSplitQualMap.put(emp, splitQualMap);
			}
			if(rankRuleList.size() > 0) {
				for(Rule rule : rankRuleList) {
					logger.debug("RULE IN RANK RULE LIST= "+rule.getRuleName());
				}
				empRuleMap.put(emp, rankRuleList);
			}
			
			if(!rule_freq_map.isEmpty()) {
				logger.debug("--- PRINTING RULE_FREQ_MAP ---");
				for(Map.Entry<Rule, Map<Date,Date>> entry: rule_freq_map.entrySet()) {
					logger.debug("RULE IN RULE_FRQ_MAP = "+entry.getKey().getRuleName());
					Map<Date,Date> map = entry.getValue();
					for(Map.Entry<Date, Date> entry2 : map.entrySet()) {
						logger.debug("RULE START DATE IN RULE_FREQ_MAP= "+entry2.getKey());
						logger.debug("RULE END DATE IN RULE_FREQ_MAP= "+entry2.getValue());
					}
				}
				emp_rule_freq_map.put(emp, rule_freq_map);
				
			}
		}
	}

	

	
}
