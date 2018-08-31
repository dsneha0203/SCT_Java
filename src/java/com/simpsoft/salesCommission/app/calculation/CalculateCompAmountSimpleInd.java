package com.simpsoft.salesCommission.app.calculation;

import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

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
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleSimple;

public class CalculateCompAmountSimpleInd {
	
	private static final Logger logger = Logger.getLogger(CalculateCompAmountSimpleInd.class);
	private static Date startDate=null;
	private static Date endDate=null;
	private static List<Rule> qualifiedRuleListOfEmp = null;
	public static void main(String[] args) throws ParseException {
		
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
		calcAPI.saveDates(startDate, endDate);
		
		
		//find rule assignments for all employee
		findRuleAssgForAllEmp(calcAPI, empAPI, ruleAssAPI, ruleAPI, orderAPI, splitRuleAPI);
	
	}

	/**
	 * @param empAPI
	 * @param ruleAssAPI
	 * @param ruleAPI
	 * @param orderAPI
	 * @throws ParseException 
	 */
	private static void findRuleAssgForAllEmp(CalculationAPI calcAPI,EmployeeAPI empAPI, RuleAssignmentAPI ruleAssAPI, RuleAPI ruleAPI,
			OrderAPI orderAPI, SplitRuleAPI splitRuleAPI) throws ParseException {
		logger.debug("---FINDING RULE ASSIGNMENTS---");
		List<Employee> empList = empAPI.listEmployees();
		for(Employee emp: empList) {
			int counter=0;
			RuleAssignment assignment= ruleAssAPI.searchAssignedRuleForEmployee(emp.getId());
			
			if(assignment != null) {
				qualifiedRuleListOfEmp = new ArrayList<>();
				List<Rule> rulesAssigned = new ArrayList<>();
				logger.debug("RuleAssignment id = "+ assignment.getId());
				Hashtable<Long, Long> numTimes = new Hashtable<>();
					List<RuleAssignmentDetails> assignmentDetails = assignment.getRuleAssignmentDetails();
					for(RuleAssignmentDetails details : assignmentDetails) {
						logger.debug("VALIDITY TYPE= "+details.getValidityType());
						// check whether the start and end dates of the rule assignment detail falls between
						// the start and end date input values
						boolean valid= calcAPI.checkValidity(ruleAssAPI, details.getId(), startDate, endDate);
						logger.debug("RULE ID= "+ details.getRule().getId());							
						logger.debug("RULE NAME= "+ details.getRule().getRuleName());			
						logger.debug("Validity= "+valid);
						
						if(valid==true) {						
							
							// find the no of times the rule output should be added to the compensation 
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
									long num= calcAPI.getFullWeeks(planStartDate, planEndDate, startDate, endDate);
									logger.debug("num of weeks= "+num);
									numTimes.put(details.getId(), num);
								}
								
								else if(freq.equals("monthly")) {
									long num=calcAPI.getFullMonths(planStartDate, planEndDate, startDate, endDate);
									logger.debug("num of months= "+num);
									numTimes.put(details.getId(), num);
								}
								
								 else if(freq.equals("quaterly")) {
									long num=calcAPI.getFullQuarters(planStartDate, planEndDate, startDate, endDate);
									logger.debug("num of quarters= "+num);
									numTimes.put(details.getId(), num);
								 }
								
								 else if(freq.equals("half-yearly")) {
										long num=calcAPI.getFullHalves(planStartDate, planEndDate, startDate, endDate);
										logger.debug("num of halves= "+num);
										numTimes.put(details.getId(), num);
									}
								
								else if(freq.equals("annually")) {
										long num=calcAPI.getFullYears(planStartDate, planEndDate, startDate, endDate);
										logger.debug("num of years= "+num);
										numTimes.put(details.getId(), num);
									}
								
							}else {
								logger.debug("FIXED RULE");
								numTimes.put(details.getId(), (long) 1);
							}
							
							
							rulesAssigned.add(details.getRule());		
							counter=counter+1;
							
						}else {
							numTimes.put(details.getId(), (long) 0);
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
							}
						}
						
						// compare line item list with qual clause list of each rule
						for(Rule rule :rulesAssigned) {
							compareLineItem(calcAPI, orderAPI, ruleAPI, empLineItemsList, rule);
						}					
				}			
		
					
			}
			
			if(qualifiedRuleListOfEmp != null) {
				if(qualifiedRuleListOfEmp.size() > 0 ) {
					logger.debug("LIST OF SATISFIED RULES FOR EMP ID = "+emp.getId());
					for(Rule satisfiedRule : qualifiedRuleListOfEmp) {
						logger.debug("SATISFIED SIMPLE RULE NAME= "+satisfiedRule.getRuleName());
					}
				}else {
					logger.debug("NO SIMPLE RULES ARE SATISFIED FOR EMP ID= "+emp.getId());
				}
			}
			
			qualifiedRuleListOfEmp=null;
		}
		
	}

	private static void compareLineItem(CalculationAPI calcAPI,OrderAPI orderAPI,RuleAPI ruleAPI,
			List<OrderLineItems> empLineItemsList, Rule rule) {
		if(rule.getRuleType().equalsIgnoreCase("simple")){
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
				filteredLineItemsList = empLineItemsList;
			}else {				
				//compare each line item with all the simple qual clauses
				for(OrderLineItems items : empLineItemsList) {					
					boolean isSatisfied = checkLineItem(orderAPI, items, nonAggQualList);
					if(isSatisfied == true) {
						filteredLineItemsList.add(items);
					}					
				}
			}
			// compare list of line items with agg qual clause
			if(aggQualList.size() > 0) {
				int flag=0;
				for(QualifyingClause aggClause : aggQualList) {
					if(aggClause.getAggregateFunctions().getFunctionName().equals("sum")) {
						double orderTotal=0;
						int quantity=0;
						for(OrderLineItems items : filteredLineItemsList) {
							int dut_percent = items.getDutyPercentage();
							int dis_percent = items.getDiscountPercentage();
							int qty = items.getQuantity();
							int rate = items.getRate();
							float r1=dut_percent/100;
							float r2= dis_percent/100;
							float r3= qty*rate;
//							orderTotal += items.getSubtotal();
							orderTotal += ((1.0+r1)*((1.0-r2)*(r3)));
							quantity += items.getQuantity();
						}
						logger.debug("ORDER TOTAL= "+orderTotal);
						logger.debug("QUANTITY= "+quantity);
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
						if(isSatisfied == false) {
							flag+=1;
						}
					}
				}
				
				//rule is qualified if flag value is 0
				if(flag == 0) {
					qualifiedRuleListOfEmp.add(rule);
				}
				
			}else {
				// if filteredlineitem list size is greater than 0 then this rule is qualified
				if(filteredLineItemsList.size() > 0) {
					qualifiedRuleListOfEmp.add(rule);
				}
			}				
		
		}	
		
	}

	private static boolean checkAggQual(double compareValue, boolean notFlag, String condition, Double value) {
		if(condition.equals("equal")) {
			if(compareValue == value) {
				if(!notFlag) {
					return true;
				}
			}			
		}
		else if(condition.equals("greater than")) {
			if(compareValue > value) {
				if(!notFlag) {
					return true;
				}
			}
		}
		else if(condition.equals("greater than equal to")) {
			if(compareValue >= value) {
				if(!notFlag) {
					return true;
				}
			}
		}
		else if(condition.equals("less than")) {
			if(compareValue < value) {
				if(!notFlag) {
					return true;
				}
			}
		}
		else if(condition.equals("less than equal to")) {
			if(compareValue <= value) {
				if(!notFlag) {
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
						return true;
					}
					
				}
			}else if(condition.equals("less than")) {
				if(items.getDiscountPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than")) {
				if(items.getDiscountPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getDiscountPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getDiscountPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}
			break;
		case "Duty Percentage":
			if(condition.equals("equal")) {
				if(items.getDutyPercentage() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("less than")) {
				if(items.getDutyPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than")) {
				if(items.getDutyPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getDutyPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getDutyPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
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
				}
			}else if(condition.equals("starts with")) {
				if(custName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(custName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}
			break;
		case "Office Location":
			long ofc_loc_id = orderDetail.getOfficeLocation().getId();
			long id = Long.parseLong(value);
			if(condition.equals("equal")) {
				if(ofc_loc_id == id) {
					if(!notFlag) {
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
				}
			}		
			break;
		case "Quantity":
			if(condition.equals("equal")) {
				if(items.getQuantity() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("less than")) {
				if(items.getQuantity() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than")) {
				if(items.getQuantity() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(items.getQuantity() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}
			}else if(condition.equals("greater than equal to")) {
				if(items.getQuantity() >= Integer.parseInt(value)) {
					if(!notFlag) {
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
				}
			}else if(condition.equals("starts with")) {
				if(prod_name.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(prod_name.endsWith(value)) {
					if(!notFlag) {
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
				}
			}
			break;
		
			
		}
		return false;
		
		
		
	}

	
	
}
