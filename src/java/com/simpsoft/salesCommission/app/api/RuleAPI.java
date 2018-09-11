package com.simpsoft.salesCommission.app.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.UImodel.QualifyingClauseUI;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.RuleSimple;
import com.simpsoft.salesCommission.app.model.RuleType;

@Component
public class RuleAPI {

	@Autowired
	private static SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(RuleAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/**
	 * Method for creating rule in Database
	 * 
	 * @param rule
	 */
	public long createRule(Rule rule) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Rule newRule = new Rule();
		try {
			tx = session.beginTransaction();
			newRule.setRuleName(rule.getRuleName());
			newRule.setDescription(rule.getDescription());
			newRule.setRuleDetails(rule.getRuleDetails());
			newRule.setCompensationType(rule.getCompensationType());
			newRule.setFixedCompValue(rule.getFixedCompValue());
			newRule.setCompensationFormula(rule.getCompensationFormula());
			newRule.setCompensationParameter(rule.getCompensationParameter());
			newRule.setRuleParameter(rule.getRuleParameter());
			if (rule.getRuleType().equals("c")) {
				newRule.setConnectionType(rule.getConnectionType());
				newRule.setRuleComposite(rule.getRuleComposite());
				newRule.setRuleType("Composite");
			} else if (rule.getRuleType().equals("s") && rule.getRuleSimple().getCalculationMode().equals("r")) {
				newRule.setRuleType("Simple");
				RuleSimple simpleRule = createSimpleRuleRank(rule.getRuleSimple());
				newRule.setRuleSimple(simpleRule);
			} else if (rule.getRuleType().equals("s") && rule.getRuleSimple().getCalculationMode().equals("i")) {
				newRule.setRuleType("Simple");
				RuleSimple simpleRule = createSimpleRuleIndivdual(rule.getRuleSimple());
				newRule.setRuleSimple(simpleRule);
			}

			session.save(newRule);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return newRule.getId();

	}

	/**
	 * Private Method for Create rule simple individual
	 * 
	 * @param simpRule
	 * @return newRuleSimple
	 */
	private RuleSimple createSimpleRuleIndivdual(RuleSimple simpRule) {

		RuleSimple newRuleSimple = new RuleSimple();
		newRuleSimple.setCalculationMode("individual");
		newRuleSimple.setField(simpRule.getField());
		newRuleSimple.setQualifyingClause(simpRule.getQualifyingClause());
		newRuleSimple.setAggregateFunctions(simpRule.getAggregateFunctions());

		return newRuleSimple;
	}

	/**
	 * Private Method for Create rule simple rank
	 * 
	 * @param simpRule
	 * @return newRuleSimple
	 */

	private RuleSimple createSimpleRuleRank(RuleSimple simpRule) {

		RuleSimple newRuleSimple = new RuleSimple();
		newRuleSimple.setCalculationMode("rank");
		newRuleSimple.setAggregateFunctions(simpRule.getAggregateFunctions());
		newRuleSimple.setField(simpRule.getField());
		newRuleSimple.setPopulationType(simpRule.getPopulationType());
		newRuleSimple.setPopulationUpto(simpRule.getPopulationUpto());
		newRuleSimple.setQualifyingClause(simpRule.getQualifyingClause());
		newRuleSimple.setRankCount(simpRule.getRankCount());
		newRuleSimple.setRankingType(simpRule.getRankingType());

		return newRuleSimple;

	}

	/**
	 * Method for editing rule
	 * 
	 * @param rule
	 */
	public void editRule(Rule rule) {
		logger.debug("---IN EDIT METHOD---");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Rule newRule = (Rule) session.get(Rule.class, rule.getId());
			logger.debug("RULE NAME= "+rule.getRuleName());
			newRule.setRuleName(rule.getRuleName());
			newRule.setDescription(rule.getDescription());
			newRule.setRuleDetails(rule.getRuleDetails());
			newRule.setCompensationType(rule.getCompensationType());
			newRule.setFixedCompValue(rule.getFixedCompValue());
			newRule.setCompensationFormula(rule.getCompensationFormula());
			newRule.setCompensationParameter(rule.getCompensationParameter());
			newRule.clearRuleParameter(newRule.getRuleParameter());
			for(Iterator itr= rule.getRuleParameter().iterator(); itr.hasNext();) {
				RuleParameter parameter = (RuleParameter)itr.next();
				logger.debug("PARAM NAME= "+parameter.getParameterName());
				logger.debug("PARAM VALUE= "+parameter.getParameterValue());
			}
			newRule.addRuleParameter(rule.getRuleParameter());
//			newRule.setRuleParameter(rule.getRuleParameter());
			if (rule.getRuleType().equals("Composite")) {
				newRule.setConnectionType(rule.getConnectionType());
				newRule.setRuleComposite(rule.getRuleComposite());
				newRule.setRuleType("Composite");
			} else if (rule.getRuleType().equals("Simple")
					&& rule.getRuleSimple().getCalculationMode().equals("rank")) {
				newRule.setRuleType("Simple");

				RuleSimple simp = editRuleSimpleRank(session, rule.getRuleSimple());
				newRule.setRuleSimple(simp);

			} else if (rule.getRuleType().equals("Simple")
					&& rule.getRuleSimple().getCalculationMode().equals("individual")) {
				newRule.setRuleType("Simple");

				RuleSimple simp = editRuleSimpleIndividual(session, rule.getRuleSimple());
				newRule.setRuleSimple(simp);

			}
			/*logger.debug("RULE CALC SIMP LIST = "+ rule.getCalcSimpleList().size());
			newRule.setCalcSimpleList(rule.getCalcSimpleList());*/
			session.merge(newRule);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	/**
	 * Private method for edit simple rule with rank
	 * 
	 * @param session
	 * @param simpRule
	 * @return newsimp
	 */
	private RuleSimple editRuleSimpleRank(Session session, RuleSimple simpRule) {
		
		RuleSimple newsimp = new RuleSimple();
		newsimp = (RuleSimple) session.get(RuleSimple.class, simpRule.getId());
		newsimp.setCalculationMode("rank");
		newsimp.setPopulationType(simpRule.getPopulationType());
		newsimp.setPopulationUpto(simpRule.getPopulationUpto());
		newsimp.setRankCount(simpRule.getRankCount());
		newsimp.setRankingType(simpRule.getRankingType());
		newsimp.setField(simpRule.getField());
		newsimp.clearQualifyingClause(newsimp.getQualifyingClause());
		newsimp.addQualifyingClause(simpRule.getQualifyingClause());
	//	newsimp.setQualifyingClause(simpRule.getQualifyingClause());
		newsimp.setAggregateFunctions(simpRule.getAggregateFunctions());

		session.merge(newsimp);

		return newsimp;
	}

	/**
	 * Private method for edit simple rule with individual
	 * 
	 * @param session
	 * @param simpRule
	 * @return newsimp
	 */
	private RuleSimple editRuleSimpleIndividual(Session session, RuleSimple simpRule) {

		RuleSimple newsimp = new RuleSimple();

		newsimp = (RuleSimple) session.get(RuleSimple.class, simpRule.getId());
		newsimp.setCalculationMode("individual");
		newsimp.setField(simpRule.getField());
		newsimp.clearQualifyingClause(newsimp.getQualifyingClause());
		newsimp.addQualifyingClause(simpRule.getQualifyingClause());
		for(Iterator itr= simpRule.getQualifyingClause().iterator(); itr.hasNext();) {
			QualifyingClause p = (QualifyingClause)itr.next();
			logger.debug("Edit QualifyingClauseValue: " + p.getValue());
			logger.debug("Edit Condition: " + p.isNotFlag());
			logger.debug("Edit ConditionValue: " + p.getConditionList().getConditionValue());
			logger.debug("Edit FieldName: " + p.getFieldList().getDisplayName());
			if(p.getAggregateFunctions() != null) {
				logger.debug("Edit Aggregate function: "+p.getAggregateFunctions().getFunctionName());
			}
		}
//		newsimp.setQualifyingClause(simpRule.getQualifyingClause());
		if(simpRule.getAggregateFunctions() != null) {
			newsimp.setAggregateFunctions(simpRule.getAggregateFunctions());
		}else {
			newsimp.setAggregateFunctions(null);
		}
		session.merge(newsimp);

		return newsimp;
	}

	/**
	 * Method for getting list of rules
	 * 
	 * @return
	 */
	public List<Rule> listOfRules() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Rule> rules = new ArrayList<Rule>();
		try {
		tx = session.beginTransaction();
	    rules = session.createQuery("FROM Rule").list();
		for (Iterator iterator = rules.iterator(); iterator.hasNext();) {
			Rule rule = (Rule) iterator.next();
			logger.debug("GET THE RULE DETAILS FROM DATABASE" + rule.getRuleName() + rule.getRuleDetails()
					+ rule.getRuleType());
		}
		tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rules;
	}

	/**
	 * Method for getting list of simple rules
	 * 
	 * @return simpRules
	 */
	public List<Rule> listOfRules(RuleType typeOfRule) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Rule> rule1 = new ArrayList<Rule>();
		try {
			tx = session.beginTransaction();
			List simpRules = session.createQuery("FROM Rule").list();
			for (Iterator iterator = simpRules.iterator(); iterator.hasNext();) {

				Rule rule = (Rule) iterator.next();
				if (typeOfRule.toString().equals(rule.getRuleType())) {
					rule1.add(rule);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return rule1;
	}

	/**
	 * Method for getting one rule details by ID
	 * 
	 * @param ruleID
	 * @return
	 */
	public Rule getRule(long ruleID) {
		
		Rule newRule = new Rule();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newRule = (Rule) session.get(Rule.class, ruleID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newRule;
	}

	/**
	 * Method for search rule by rule name
	 * 
	 * @param fieldVal
	 * @return
	 */
	public Rule searchRuleByName(String ruleName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Rule rule1 = new Rule();
		try {
			tx = session.beginTransaction();
			List fields = session.createQuery("FROM Rule").list();
			for (Iterator iterator = fields.iterator(); iterator.hasNext();) {

				Rule rule = (Rule) iterator.next();
				if (ruleName.equals(rule.getRuleName())) {
					rule1 = rule;

				}

			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rule1;
	}

	/**
	 * Method for delete rule
	 * 
	 * @param ruleID
	 */
	public void deleteRule(long ruleID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Rule rule = (Rule) session.get(Rule.class, ruleID);
			session.delete(rule);
			logger.debug("DELETE THE RULE DETAILS FROM DATABASE" + rule);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	//find composite rule
public RuleComposite findCompRule(long ruleID) {
		
		Rule newRule = new Rule();
		RuleComposite ruleComp = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newRule = (Rule) session.get(Rule.class, ruleID);
			ruleComp = newRule.getRuleComposite();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ruleComp;
	}
	
	//find simple rule
public RuleSimple findSimpleRule(long ruleID) {
		
		Rule newRule = new Rule();
		RuleSimple ruleSimple = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newRule = (Rule) session.get(Rule.class, ruleID);
			ruleSimple = newRule.getRuleSimple();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ruleSimple;
	}
}