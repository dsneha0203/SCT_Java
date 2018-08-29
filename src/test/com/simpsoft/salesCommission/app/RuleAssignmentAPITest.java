package com.simpsoft.salesCommission.app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleAssignmentParameter;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RuleAssignmentAPITest {

	@Autowired
	private RuleAssignmentAPI ruleAssignmentApi;

	@Autowired
	private RoleAPI roleAPI;

	@Autowired
	private EmployeeAPI employeeApi;

	@Autowired
	private RuleAPI ruleAPI;

	@BeforeClass
	public static void oneTimeSetUp() {
		// one-time initialization code
		System.out.println("@BeforeClass - oneTimeSetUp");
	}

	@AfterClass
	public static void oneTimeTearDown() {
		// one-time cleanup code
		System.out.println("@AfterClass - oneTimeTearDown");
	}

	@Before
	public void setUp() throws Exception {

		System.out.println("Setting it up!");
		// ruleAss = new RuleAssignment();

	}

	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#createRule(com.simpsoft.salesCommission.app.model.RuleAssignment)}
	 * .
	 */
/*	@Test
	public void testCreateRuleAssignmentToEmployee() {

		RuleAssignment ruleAss = new RuleAssignment();
		String empName = "Tom";
		Employee employee = employeeApi.searchEmployee(empName);
		ruleAss.setEmployee(employee);
		List<RuleAssignmentDetails> rlAssDtlList = new ArrayList<RuleAssignmentDetails>();
		RuleAssignmentDetails rlAssDtl1 = new RuleAssignmentDetails();
		rlAssDtl1.setValidityType("Fixed");
		Rule rule1 = ruleAPI.getRule(2);
		rlAssDtl1.setRule(rule1);
		List<RuleAssignmentParameter> assParamList = ruleAssignmentApi.setRuleAssignmentParameters(rule1);
		rlAssDtl1.setRuleAssignmentParameter(assParamList);
		rlAssDtlList.add(rlAssDtl1);
		RuleAssignmentDetails rlAssDtl2 = new RuleAssignmentDetails();
		rlAssDtl2.setValidityType("Repeats");
		String frequencyName2 = "weekly";
		Frequency frequency2 = ruleAssignmentApi.searchFrequency(frequencyName2);
		rlAssDtl2.setFrequency(frequency2);
		Rule rule2 = ruleAPI.getRule(3);
		rlAssDtl2.setRule(rule2);
		List<RuleAssignmentParameter> assParamList2 = ruleAssignmentApi.setRuleAssignmentParameters(rule2);
		rlAssDtl2.setRuleAssignmentParameter(assParamList2);
		rlAssDtlList.add(rlAssDtl2);
		ruleAss.setRuleAssignmentDetails(rlAssDtlList);

		long ruleId = ruleAssignmentApi.createRuleAssignment(ruleAss);
		ruleAss.setId(ruleId);
		RuleAssignment newRuleAss = ruleAssignmentApi.getRuleAssignment(ruleId);

		Assert.assertEquals("Tom", newRuleAss.getEmployee().getEmployeeName());
		Assert.assertEquals(1500, newRuleAss.getEmployee().getSalary());
		System.out.println("Running: testDummyRuleAssignment");

	} 

	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#createRule(com.simpsoft.salesCommission.app.model.RuleAssignment)}
	 * .
	 */
	@Test
	public void testCreateRuleAssignmentToRole() {

		RuleAssignment ruleAss = new RuleAssignment();
		String roleName = "VP Sales";
		Role role = roleAPI.searchRoleByName(roleName);
		ruleAss.setRole(role);
		List<RuleAssignmentDetails> rlAssDtlList = new ArrayList<RuleAssignmentDetails>();
		RuleAssignmentDetails rlAssDtl = new RuleAssignmentDetails();
		rlAssDtl.setValidityType("Repeats");
		String frequencyName = "monthly";
		Frequency frequency = ruleAssignmentApi.searchFrequency(frequencyName);
		rlAssDtl.setFrequency(frequency);
		Rule rule = ruleAPI.getRule(2);
		rlAssDtl.setRule(rule);
		List<RuleAssignmentParameter> assParamList = ruleAssignmentApi.setRuleAssignmentParameters(rule);
		rlAssDtl.setRuleAssignmentParameter(assParamList);
		rlAssDtlList.add(rlAssDtl);
		RuleAssignmentDetails rlAssDtl2 = new RuleAssignmentDetails();
		rlAssDtl2.setValidityType("Repeats");
		String frequencyName2 = "annually";
		Frequency frequency2 = ruleAssignmentApi.searchFrequency(frequencyName2);
		rlAssDtl2.setFrequency(frequency2);
		Rule rule2 = ruleAPI.getRule(1);
		rlAssDtl2.setRule(rule2);
		List<RuleAssignmentParameter> assParamList2 = ruleAssignmentApi.setRuleAssignmentParameters(rule2);
		rlAssDtl2.setRuleAssignmentParameter(assParamList2);
		rlAssDtlList.add(rlAssDtl2);
		ruleAss.setRuleAssignmentDetails(rlAssDtlList);

		long ruleId = ruleAssignmentApi.createRuleAssignment(ruleAss);
		ruleAss.setId(ruleId);
		RuleAssignment newRuleAss = ruleAssignmentApi.getRuleAssignment(ruleId);

		Assert.assertEquals("VP Sales", newRuleAss.getRole().getRoleName());
		Assert.assertEquals("Good", newRuleAss.getRole().getDescription());
		System.out.println("Running: testDummyRuleAssignment");

	} 
	
	@Test
	public void testSearchAssignedRuleToEmployee() {
		
		String queryName = "Harry";
		RuleAssignment assignment = ruleAssignmentApi.searchAssignedRule(queryName);
		Assert.assertEquals("Harry", assignment.getEmployee().getEmployeeName());
		//Assert.assertEquals("Good", assignment.getRole().getDescription());
		
	} 
	
	@Test
	public void testSearchAssignedRuleToRole() {
		
		String queryName = "Analyst I";
		RuleAssignment assignment = ruleAssignmentApi.searchAssignedRule(queryName);	
		Assert.assertEquals("Analyst I", assignment.getRole().getRoleName());
		Assert.assertEquals("Good", assignment.getRole().getDescription());
		
	}
	/**
	 * @throws java.lang.Exception
	 */

	@After
	public void tearDown() throws Exception {
		System.out.println("Running: tearDown");
		// ruleAPI.deleteRule(rule.getId());
		// Assert.assertNull(ruleAPI.getRule(rule.getId()));
	}
}