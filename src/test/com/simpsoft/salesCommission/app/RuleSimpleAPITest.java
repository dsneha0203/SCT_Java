/**
 * 
 */
package com.simpsoft.salesCommission.app;


import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleSimple;
import com.simpsoft.salesCommission.app.model.RuleType;

/**
 * @author User
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RuleSimpleAPITest {

	@Autowired
	private RuleSimpleAPI ruleSimpleApi;
	@Autowired
	private RuleAPI ruleApi;

	/**
	 * Test method for
	 * {@link com.simpsoft.salesCommission.app.api.RuleSimpleAPI#createSimpleRule(com.simpsoft.salesCommission.app.model.RuleSimple)}
	 * .
	 */

	/*
	 * @Test public void testGetSimpleRule() {
	 * 
	 * RuleSimple rsimp = ruleSimpleApi.getRuleSimple(4);
	 * Assert.assertEquals("Individual",rsimp.getCalculationMode()); }
	 */

	@Test
	public void testlistOfSimpleRules() {

		List<Rule> api = ruleApi.listOfRules(RuleType.Simple);
		Assert.assertEquals(5, api.size());
	}

	@Test
	public void testlistOfAggregateFunctions() {

		List<AggregateFunctions> api = ruleSimpleApi.listOfAggregateFunctions();
		for (Iterator iterator = api.iterator(); iterator.hasNext();) {
			AggregateFunctions agrFn = (AggregateFunctions) iterator.next();
			System.out.println("GET THE RULE DETAILS FROM DATABASE     " + agrFn.getId() + agrFn.getFunctionName());

		}
		Assert.assertEquals(4, api.size());
	}

	@Test
	public void testlistOfConditions() {

		List<ConditionList> api = ruleSimpleApi.listOfConditions();
		Assert.assertEquals(7, api.size());
	}

	@Test
	public void testlistOfFields() {

		List<FieldList> api = ruleSimpleApi.listOfFields();
		Assert.assertEquals(5, api.size());
	}

}
