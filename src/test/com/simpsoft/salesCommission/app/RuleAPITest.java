/**
 * 
 */
package com.simpsoft.salesCommission.app;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.RuleSimple;

/**
 * @author NEW2
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RuleAPITest extends TestCase {
	@Autowired
	private RuleAPI ruleAPI;
	@Autowired
	private RuleSimpleAPI ruleSimpleAPI;
	
	private Rule rule;

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
		rule = new Rule();
		rule.setRuleName("abcd");
		rule.setDescription("efgh");
		rule.setRuleDetails("Details of Rule");
		rule.setCompensationFormula("Compensation formula");
		rule.setCompensationParameter("Compensation Parameter");
		rule.setCompensationType("fixed");
		rule.setFixedCompValue(10);
		List<RuleParameter> rpm = new ArrayList<>();
		RuleParameter rl1 = new RuleParameter();
		RuleParameter rl2 = new RuleParameter();
		rl1.setParameterName("Parameter 7");
		rl1.setParameterValue("value 7");
		rl2.setParameterName("Parameter 8");
		rl2.setParameterValue("value 8");
		rpm.add(rl1);
		rpm.add(rl2);
		rule.setRuleParameter(rpm);
		
	}

		 
	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#getRule(long)}.
	 */

	
	  @Test
	  public void testGetRule() { 
		 Rule rule1 = ruleAPI.getRule(5);
		 Assert.assertEquals("Rule for getting incentive to top saler of company", rule1.getRuleName());
			
		  }
	
	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#createRule(com.simpsoft.salesCommission.app.model.Rule)}
	 * .
	 */
/*	@Test
	public void testCreateRuleSimpleRank() {

		rule.setRuleType("s");
		RuleSimple ruleSimple = new RuleSimple();
		ruleSimple.setCalculationMode("r");
		ruleSimple.setPopulationUpto(6);
		
		List<RuleParameter> rpm = new ArrayList<>();
		RuleParameter rl1 = new RuleParameter();
		RuleParameter rl2 = new RuleParameter();
		rl1.setParameterName("Parameter 7");
		rl1.setParameterValue("value 7");
		rl2.setParameterName("Parameter 8");
		rl2.setParameterValue("value 8");
		rpm.add(rl1);
		rpm.add(rl2);
		ruleSimple.setRuleParameter(rpm);

		List<FieldList> fldlst = new ArrayList<FieldList>();
		FieldList fld1 = new FieldList();
		FieldList fld2 = new FieldList();
		fld1.setFieldName("fld 3");
		fld1.setDisplayName("fii");
		fld2.setFieldName("fld 6");
		fld2.setDisplayName("fello");
		fldlst.add(fld1);
		fldlst.add(fld2);
		ruleSimple.setFieldList(fldlst);
		
		List <AggregateFunctions> aggtfn = new ArrayList<AggregateFunctions>();
		AggregateFunctions fn1 = new AggregateFunctions();
		AggregateFunctions fn2 = new AggregateFunctions();
		fn1.setFunctionName("Funtion 8");
		fn2.setFunctionName("Function 3");
		aggtfn.add(fn2);
		aggtfn.add(fn1); 
		ruleSimple.setAggregateFunctions(aggtfn);
		
		List<QualifyingClause> qClause = new ArrayList<QualifyingClause>();
		QualifyingClause qc1 = new QualifyingClause();
		QualifyingClause qc2 = new QualifyingClause();
		qc1.setValue("Qualifying Clause1");
		FieldList fldlst1 = new FieldList();
		fldlst1.setFieldName("fieldlstvalue1");
		fldlst1.setDisplayName("hello1");
		qc1.setFieldList(fldlst1);
		ConditionList cndlst1 = new ConditionList();
		cndlst1.setNotFlag(true);
		cndlst1.setConditionValue("condition1");
		qc1.setConditionList(cndlst1); 
		qClause.add(qc1);
		qc2.setValue("Qualifying Clause2");
		FieldList fldlst2 = new FieldList();
		fldlst2.setFieldName("fieldlstvalue2");
		fldlst2.setDisplayName("hello2");
		qc2.setFieldList(fldlst2);
		ConditionList cndlst2 = new ConditionList();
		cndlst2.setNotFlag(false);
		cndlst2.setConditionValue("condition2");
		qc2.setConditionList(cndlst2); 
		qClause.add(qc2);
		ruleSimple.setQualifyingClause(qClause);

		rule.setRuleSimple(ruleSimple);

		long id = ruleAPI.createRule(rule);
		rule.setId(id);
		Rule rule1 = ruleAPI.getRule(id);
		
		Assert.assertEquals("Details of Rule", rule1.getRuleDetails());
		Assert.assertEquals("rank", rule1.getRuleSimple().getCalculationMode());
		System.out.println("Running: testDummyRule");

	} */
	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#createRule(com.simpsoft.salesCommission.app.model.Rule)}
	 * .
	 */
/*	@Test
	public void testCreateRuleComposite() {
		  rule.setRuleType("c");
		  RuleComposite ruleComposite = new RuleComposite();
		  rule.setConnectionType("all");
		  Rule simple1 = ruleAPI.searchRuleByName("Rule for getting commission on max sale of computer"); 
		  Rule simple2 =  ruleAPI.searchRuleByName("Rule for getting commission on max sale of simcard");
		  System.out.println("*************************************          " + simple1.getRuleName());
		  System.out.println("#####################################          " + simple2.getRuleName());
		  List<Rule> rl = new ArrayList<Rule>();
		  rl.add(simple1);
		  rl.add(simple2);
		  ruleComposite.setCompJoinRule(rl);
		  rule.setRuleComposite(ruleComposite); 
	//	  long ruleId = ruleAPI.createRule(rule);
	//	  rule.setId(ruleId);
	//		Rule r = ruleAPI.getRule(ruleId);
	//		Assert.assertEquals("abcd", r.getRuleName());
	//		Assert.assertEquals("Composite", r.getRuleType());
			System.out.println("Running: testDummyRule");
		System.out.println("Running: testDummyRule");

	} */

	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#createRule(com.simpsoft.salesCommission.app.model.Rule)}
	 * .
	 */
/*	@Test
	public void testCreateRuleSimpleIndv() {

		rule.setRuleType("s");
		RuleSimple ruleSimple = new RuleSimple();
		ruleSimple.setCalculationMode("i");

		List<RuleParameter> rpm = new ArrayList<>();
		RuleParameter rl1 = new RuleParameter();
		RuleParameter rl2 = new RuleParameter();
		rl1.setParameterName("Parameter 7");
		rl1.setParameterValue("value 7");
		rl2.setParameterName("Parameter 8");
		rl2.setParameterValue("value 8");
		rpm.add(rl1);
		rpm.add(rl2); 
		ruleSimple.setRuleParameter(rpm);

		List<FieldList> fldlst = new ArrayList<FieldList>();
		FieldList fld1 = new FieldList();
		FieldList fld2 = new FieldList();
		fld1.setFieldName("fld 3");
		fld1.setDisplayName("fii");
		fld2.setFieldName("fld 6");
		fld2.setDisplayName("fello");
		fldlst.add(fld1);
		fldlst.add(fld2); 
		ruleSimple.setFieldList(fldlst);
		
		List <AggregateFunctions> aggtfn = new ArrayList<AggregateFunctions>();
		AggregateFunctions fn1 = new AggregateFunctions();
		AggregateFunctions fn2 = new AggregateFunctions();
		fn1.setFunctionName("Funtion 8");
		fn2.setFunctionName("Function 3");
		aggtfn.add(fn2);
		aggtfn.add(fn1); 
		ruleSimple.setAggregateFunctions(aggtfn);
		
	
		QualifyingClause qClause = new QualifyingClause();
		qClause.setValue("Qualifying Clause");
		FieldList fldlst1 = new FieldList();
		fldlst1.setFieldName("fieldlstvalue1");
		fldlst1.setDisplayName("hello");
		qClause.setFieldList(fldlst1);
		ConditionList cndlst1 = new ConditionList();
		cndlst1.setNotFlag(true);
		cndlst1.setConditionValue("condition1");
		qClause.setConditionList(cndlst1); 
		ruleSimple.setQualifyingClause(qClause);

	rule.setRuleSimple(ruleSimple);
		
		Rule r = new Rule();

		Rule rule1 = ruleAPI.createRule(rule);
		//rule.setId(ruleId);
		r = ruleAPI.getRule(rule1.getId());

		Assert.assertEquals("abcd", r.getRuleName());
		Assert.assertEquals("individual", r.getRuleSimple().getCalculationMode());
		System.out.println("Running: testDummyRule");

	}*/
	/**
	 * Test method for edit rule simple rank 
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#editRule()}.
	 */
	@Test
	public void testEditRuleSimpleRank() {

		rule.setRuleType("s");
		RuleSimple ruleSimple = new RuleSimple();
		ruleSimple.setCalculationMode("r");
		ruleSimple.setPopulationUpto(6);

		ruleSimple.setField("fld 3");
		String fnVal = "sum";
		AggregateFunctions fn1 = ruleSimpleAPI.searchAggregateFunction(fnVal);
		ruleSimple.setAggregateFunctions(fn1);
		
		List<QualifyingClause> qClause = new ArrayList<QualifyingClause>();
		QualifyingClause qc1 = new QualifyingClause();
		QualifyingClause qc2 = new QualifyingClause();
		qc1.setValue("Qualifying Clause1");
		String fldVal = "Order Total";
		FieldList fldlst1 = ruleSimpleAPI.searchFieldList(fldVal);
		qc1.setFieldList(fldlst1);
		qc1.setNotFlag(true);
		String cndVal1 = "less than";
		ConditionList cnd1 = ruleSimpleAPI.searchCondition(cndVal1);
		qc1.setConditionList(cnd1); 
		qClause.add(qc1);
		qc2.setValue("Qualifying Clause2");
		String fldVal2 = "Discount Percentage";
		FieldList fldlst2 = ruleSimpleAPI.searchFieldList(fldVal2);
		qc2.setFieldList(fldlst2);
		qc2.setNotFlag(false);
		String cndVal2 = "equal";
		ConditionList cnd2 = ruleSimpleAPI.searchCondition(cndVal2);
		qc2.setConditionList(cnd2); 
		qClause.add(qc2);
		ruleSimple.setQualifyingClause(qClause);

		rule.setRuleSimple(ruleSimple);

	//	long id = ruleAPI.createRule(rule);
	//	rule.setId(id);
		Rule rule1 = ruleAPI.getRule(7);
		
		//Assert.assertEquals("Details of Rule", rule1.getRuleDetails());
		//Assert.assertEquals("rank", rule1.getRuleSimple().getCalculationMode());
		
		rule1.setRuleDetails("Mascott Rober2");
		RuleSimple rsimp = rule1.getRuleSimple();
		rsimp.setPopulationUpto(30);
		rsimp.setPopulationType("best of 5 person1");
		rsimp.setField("field 51");
		//rule1.setRuleSimple(rsimp);
		List <RuleParameter> rpmList1 = new ArrayList<RuleParameter>();
		RuleParameter rl1 = new RuleParameter();
		RuleParameter rl2 = new RuleParameter();
		RuleParameter rl3 = new RuleParameter();
		rl1.setParameterName("Parameter 71");
		rl1.setParameterValue("value 71");
		rl2.setParameterName("Parameter 561");
		rl2.setParameterValue("value 341");
		rl3.setParameterName("Parameter 121");
		rl3.setParameterValue("value 566");
		rpmList1.add(rl1);
		rpmList1.add(rl2);
		rpmList1.add(rl3);
		
		rule1.setRuleParameter(rpmList1);
		
		List<QualifyingClause> qClause11 = new ArrayList<QualifyingClause>();
		QualifyingClause qc11 = new QualifyingClause();
		QualifyingClause qc22 = new QualifyingClause();
		qc11.setValue("Qualifying Clause99");
		String fldVal11 = "Order Total";
		FieldList fldlst11 = ruleSimpleAPI.searchFieldList(fldVal11);
		qc11.setFieldList(fldlst11);
		qc11.setNotFlag(true);
		String cndVal11 = "equal";
		ConditionList cnd11 = ruleSimpleAPI.searchCondition(cndVal11);
		qc11.setConditionList(cnd11); 
		qClause11.add(qc11);
		qc22.setValue("Qualifying Clause88");
		String fldVal22 = "Discount Percentage";
		FieldList fldlst22 = ruleSimpleAPI.searchFieldList(fldVal22);
		qc22.setFieldList(fldlst22);
		qc22.setNotFlag(false);
		String cndVal22 = "equal";
		ConditionList cnd22 = ruleSimpleAPI.searchCondition(cndVal22);
		qc22.setConditionList(cnd22); 
		qClause11.add(qc22);
		
		rsimp.setQualifyingClause(qClause11);
		
		rule1.setRuleSimple(rsimp);
		ruleAPI.editRule(rule1);
		Rule r1 = ruleAPI.getRule(rule1.getId());
		Assert.assertEquals("abcd", r1.getRuleName());
		Assert.assertEquals("Mascott Rober2", r1.getRuleDetails());
		Assert.assertEquals("rank", r1.getRuleSimple().getCalculationMode());
		Assert.assertEquals(30, r1.getRuleSimple().getPopulationUpto());
		System.out.println("Running: testDummyRule"); 
	}
	
	/**
	 * Test method for edit rule simple individual
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#editRule()}.
	 */
/*	@Test
	public void testEditRuleSimpleIndividual() {

		rule.setRuleType("s");
		RuleSimple ruleSimple = new RuleSimple();
		ruleSimple.setCalculationMode("i");
		
		
		List<RuleParameter> rpm = new ArrayList<>();
		RuleParameter rl1 = new RuleParameter();
		RuleParameter rl2 = new RuleParameter();
		rl1.setParameterName("Parameter 7");
		rl1.setParameterValue("value 7");
		rl2.setParameterName("Parameter 8");
		rl2.setParameterValue("value 8");
		rpm.add(rl1);
		rpm.add(rl2);
		ruleSimple.setRuleParameter(rpm);

		List<FieldList> fldlst = new ArrayList<FieldList>();
		FieldList fld1 = new FieldList();
		FieldList fld2 = new FieldList();
		fld1.setFieldName("fld 3");
		fld1.setDisplayName("fii");
		fld2.setFieldName("fld 6");
		fld2.setDisplayName("fello");
		fldlst.add(fld1);
		fldlst.add(fld2);
		ruleSimple.setFieldList(fldlst);
		
		List <AggregateFunctions> aggtfn = new ArrayList<AggregateFunctions>();
		AggregateFunctions fn1 = new AggregateFunctions();
		AggregateFunctions fn2 = new AggregateFunctions();
		fn1.setFunctionName("Funtion 16");
		fn2.setFunctionName("Function 7");
		aggtfn.add(fn2);
		aggtfn.add(fn1); 
		ruleSimple.setAggregateFunctions(aggtfn);
		
		List<QualifyingClause> qClause = new ArrayList<QualifyingClause>();
		QualifyingClause qc1 = new QualifyingClause();
		QualifyingClause qc2 = new QualifyingClause();
		qc1.setValue("Qualifying Clause1");
		FieldList fldlst1 = new FieldList();
		fldlst1.setFieldName("fieldlstvalue1");
		fldlst1.setDisplayName("hello1");
		qc1.setFieldList(fldlst1);
		ConditionList cndlst1 = new ConditionList();
		cndlst1.setNotFlag(true);
		cndlst1.setConditionValue("condition1");
		qc1.setConditionList(cndlst1); 
		qClause.add(qc1);
		qc2.setValue("Qualifying Clause2");
		FieldList fldlst2 = new FieldList();
		fldlst2.setFieldName("fieldlstvalue2");
		fldlst2.setDisplayName("hello2");
		qc2.setFieldList(fldlst2);
		ConditionList cndlst2 = new ConditionList();
		cndlst2.setNotFlag(false);
		cndlst2.setConditionValue("condition2");
		qc2.setConditionList(cndlst2); 
		qClause.add(qc2);
		ruleSimple.setQualifyingClause(qClause);

		rule.setRuleSimple(ruleSimple);

		long id = ruleAPI.createRule(rule);
		rule.setId(id);
		Rule rule1 = ruleAPI.getRule(id);
		//Assert.assertEquals("Details of Rule", rule1.getRuleDetails());
		//Assert.assertEquals("rank", rule1.getRuleSimple().getCalculationMode());
		
		rule1.setRuleDetails("Mascott Rober");
		RuleSimple rsimp = rule1.getRuleSimple();
		rule1.setRuleSimple(rsimp);
		 ruleAPI.editRule(rule1);
		Rule r1 = ruleAPI.getRule(rule1.getId());
		Assert.assertEquals("abcd", r1.getRuleName());
		Assert.assertEquals("Mascott Rober", r1.getRuleDetails());
		Assert.assertEquals("individual", r1.getRuleSimple().getCalculationMode());
		System.out.println("Running: testDummyRule"); 
	} */
	
	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleAPI#listRules()}.
	 */
	/*
	 * @Test public void testListRules() { 
	 * fail("Not yet implemented"); }
	 * 
	 */
	
	/**
	 * @throws java.lang.Exception
	 */
	
	@After
	public void tearDown() throws Exception {
		System.out.println("Running: tearDown");
		//ruleAPI.deleteRule(rule.getId());
		//Assert.assertNull(ruleAPI.getRule(rule.getId()));
	}
}
