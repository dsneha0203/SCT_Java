package com.simpsoft.salesCommission.app.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleSimple;

@Component
public class RuleSimpleAPI {

	@Autowired
	private static SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(RuleSimpleAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/**
	 * 
	 * @param ruleSimpleID
	 * @return
	 */
	public RuleSimple getRuleSimple(long ruleSimpleID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		return (RuleSimple) session.get(RuleSimple.class, ruleSimpleID);
	}

	/**
	 * Method for getting list of simple rules
	 * 
	 * @return simpRules
	 */
	public List<Rule> listOfSimpleRules(String ruleType) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Rule> rule1 = new ArrayList<Rule>();
		try {
		tx = session.beginTransaction();
		List simpRules = session.createQuery("FROM Rule").list();
		for (Iterator iterator = simpRules.iterator(); iterator.hasNext();) {
			
			Rule rule = (Rule) iterator.next();
			if(ruleType.equals(rule.getRuleType())){
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
	 * Method for create Aggregate Function
	 * 
	 * @param conditionList
	 * @return
	 */
	public long createAggregateFunctions(AggregateFunctions aggregateFunction) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		AggregateFunctions aggtFn = new AggregateFunctions();
		try {
			tx = session.beginTransaction();
			aggtFn.setFunctionName(aggregateFunction.getFunctionName());
			session.save(aggtFn);
			tx.commit();
			logger.debug("CREATED AN AGGREGATE FUNCTION INTO DATABASE" + aggtFn);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return aggtFn.getId();
	}

	/**
	 * Method for getting list of aggregate functions
	 * 
	 * @return aggregatetFunction
	 */
	public List<AggregateFunctions> listOfAggregateFunctions() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List aggregatetFunction = session.createQuery("FROM AggregateFunctions").list();
		for (Iterator iterator = aggregatetFunction.iterator(); iterator.hasNext();) {
			AggregateFunctions agrFn = (AggregateFunctions) iterator.next();
			logger.debug("GET THE RULE DETAILS FROM DATABASE" + agrFn.getId() + agrFn.getFunctionName());

		}
		return aggregatetFunction;
	}

	/**
	 * Method for getting list of aggregate functions
	 * 
	 * @return aggregatetFunction
	 */
	public List<AggregateFunctions> listOfAggregateFunctionsByID(long ruleSimpID) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List aggregatetFunction = session.createQuery("FROM AggregateFunctions WHERE RULE_SIMP_ID =" + ruleSimpID)
				.list();
		for (Iterator iterator = aggregatetFunction.iterator(); iterator.hasNext();) {
			AggregateFunctions agrFn = (AggregateFunctions) iterator.next();
			logger.debug("GET THE RULE DETAILS FROM DATABASE" + agrFn.getId() + agrFn.getFunctionName());

		}
		return aggregatetFunction;
	}

	/**
	 * Method for search aggregate function by name
	 * 
	 * @param fieldVal
	 * @return
	 */
	public AggregateFunctions searchAggregateFunction1(String aggtFunName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		AggregateFunctions fn1 = new AggregateFunctions();
		try {
		tx = session.beginTransaction();
		List fields = session.createQuery("FROM AggregateFunctions").list();
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {

			AggregateFunctions fn = (AggregateFunctions) iterator.next();
			if (aggtFunName.equals(fn.getFunctionName())) {
				fn1 = fn;

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
		return fn1;
	}
	public AggregateFunctions searchAggregateFunction(String aggtFunName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List aggFuncs = session.createQuery("FROM AggregateFunctions").list();
		AggregateFunctions aggFunc = new AggregateFunctions();
		for(Iterator itr=aggFuncs.iterator(); itr.hasNext(); ) {
			AggregateFunctions aggFuncItr = (AggregateFunctions) itr.next();
			if(aggFuncItr.getFunctionName().equals(aggtFunName)) {
				aggFunc=aggFuncItr;
				break;
			}
		}
//		List<AggregateFunctions> aggtList = new ArrayList<>();
//		try {
//		tx = session.beginTransaction();
//		Criteria crit = session.createCriteria(AggregateFunctions.class);
//		crit.add(Restrictions.eq("functionName", aggtFunName));
//		aggtList = crit.list();
//				tx.commit();
//
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		if(aggtList.isEmpty()) {
//			return null;
//		}else {
//			return aggtList.get(0);
//		}
		logger.debug("AGGFUNC NAME= "+aggFunc.getFunctionName());
		return aggFunc;
	}
	/**
	 * Method for create Condition List
	 * 
	 * @param conditionList
	 * @return
	 */
	public long createCondition(ConditionList conditionList) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ConditionList cndList = new ConditionList();
		try {
			tx = session.beginTransaction();
			cndList.setConditionValue(conditionList.getConditionValue());
			session.save(cndList);
			tx.commit();
			logger.debug("CREATED AN CONDITION INTO DATABASE" + cndList);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cndList.getId();
	}

	/**
	 * Method for getting list of conditions
	 * 
	 * @return conditionList
	 */
	@SuppressWarnings("unchecked")
	public List<ConditionList> listOfConditions() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List<ConditionList> conditionList = session.createQuery("FROM ConditionList").list();
		
	
		/*
		 * for (Iterator iterator = conditionList.iterator();
		 * iterator.hasNext();) { ConditionList cdnLst = (ConditionList)
		 * iterator.next(); logger.debug("GET THE RULE DETAILS FROM DATABASE" +
		 * cdnLst.getId() + cdnLst.getConditionValue());
		 * 
		 * }
		 */
		return conditionList;
	}

	/**
	 * 
	 * @param conditionVal
	 * @return
	 */
	public ConditionList searchCondition1(String conditionVal) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ConditionList cnd = new ConditionList();
		try {
			tx = session.beginTransaction();
			List conditions = session.createQuery("FROM ConditionList").list();
			for (Iterator iterator = conditions.iterator(); iterator.hasNext();) {

				ConditionList condition = (ConditionList) iterator.next();
				if (conditionVal.equals(condition.getConditionValue())) {
					cnd = condition;

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
		return cnd;
	}
	public ConditionList searchCondition(String conditionVal) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ConditionList> conditionList = new ArrayList<>();
		try {
		tx = session.beginTransaction();
		Criteria crit = session.createCriteria(ConditionList.class);
		crit.add(Restrictions.eq("conditionValue", conditionVal));
		conditionList = crit.list();
				tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return conditionList.get(0);
	}
	/**
	 * Method for create Field List
	 * 
	 * @param conditionList
	 * @return
	 */
	public long createFieldList(FieldList fieldList) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		FieldList fldlst = new FieldList();
		try {
			tx = session.beginTransaction();
			fldlst.setFieldName(fieldList.getFieldName());
			fldlst.setDisplayName(fieldList.getDisplayName());
			session.save(fldlst);
			tx.commit();
			logger.debug("CREATED AN FIELD LIST INTO DATABASE" + fldlst);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return fldlst.getId();
	}

	/**
	 * Method for search field by field name
	 * 
	 * @param fieldVal
	 * @return
	 */
	public FieldList searchFieldList1(String fieldVal) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		FieldList fld1 = new FieldList();
		try {
		tx = session.beginTransaction();
		List fields = session.createQuery("FROM FieldList").list();
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {

			FieldList fld = (FieldList) iterator.next();
			if (fieldVal.equals(fld.getDisplayName())) {
				fld1 = fld;

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
		return fld1;
	}
	public FieldList searchFieldList(String fieldVal) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<FieldList> fieldList = new ArrayList<>();
		try {
		tx = session.beginTransaction();
		Criteria crit = session.createCriteria(FieldList.class);
		crit.add(Restrictions.eq("displayName", fieldVal));
		fieldList = crit.list();
				tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return fieldList.get(0);
	}
	/**
	 * Method for getting list of fields
	 * 
	 * @return fieldList
	 */
	@SuppressWarnings("unchecked")
	public List<FieldList> listOfFields() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List<FieldList> fieldList = session.createQuery("FROM FieldList").list();
		
		
		/*
		 * for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
		 * FieldList fldLst = (FieldList) iterator.next(); logger.debug(
		 * "GET THE RULE DETAILS FROM DATABASE" + fldLst.getId() +
		 * fldLst.getDisplayName());
		 * 
		 * }
		 */
		
		return fieldList;
	}

}
