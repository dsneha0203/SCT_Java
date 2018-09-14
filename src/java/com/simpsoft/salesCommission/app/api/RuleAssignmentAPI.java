package com.simpsoft.salesCommission.app.api;

import java.util.ArrayList;
import java.util.Date;
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

import com.simpsoft.salesCommission.app.model.CalculationRoster;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleAssignmentParameter;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

@Component
public class RuleAssignmentAPI {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private RuleAPI ruleAPI;

	private static final Logger logger = Logger.getLogger(RuleAssignmentAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/**
	 * Method for Create assignment of rule to role or employee
	 * 
	 * @param ruleAss
	 * @return
	 */

	public long createRuleAssignment(RuleAssignment ruleAss) {
		logger.debug("---IN CREATE RULE ASSIGNMENT METHOD---");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment newRuleAss = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			Employee emp =  ruleAss.getEmployee();
			if(emp != null){
			newRuleAss.setEmployee(ruleAss.getEmployee());
			}else{
			newRuleAss.setRole(ruleAss.getRole());
			}
			
			logger.debug("----RULE ASSIGNMENT DETAILS TO BE ADDED----");
			for(Iterator itr=ruleAss.getRuleAssignmentDetails().iterator(); itr.hasNext(); ) {
				RuleAssignmentDetails assignmentDetails = (RuleAssignmentDetails)itr.next();
				//logger.debug("ID= "+assignmentDetails.getId());
				logger.debug("RULE NAME= "+assignmentDetails.getRule().getRuleName());
				logger.debug("VALIDITY TYPE= "+assignmentDetails.getValidityType());
				logger.debug("START DATE= "+assignmentDetails.getStartDate());
				logger.debug("END DATE= "+assignmentDetails.getEndDate());
				if(assignmentDetails.getRuleAssignmentParameter() != null) {
					for(Iterator itr1 = assignmentDetails.getRuleAssignmentParameter().iterator(); itr1.hasNext();) {
						logger.debug("----RULE ASSIGNMENT PARAMETERS TO BE ADDED----");
						RuleAssignmentParameter assignmentParameter = (RuleAssignmentParameter)itr1.next();
						logger.debug("PARAM NAME= "+assignmentParameter.getParameterName());
						if(assignmentParameter.getOverwriteValue() != null) {
							logger.debug("OVERWRITE VALUE= "+assignmentParameter.getOverwriteValue());
						}else {
							logger.debug("OVERWRITE VALUE= null");
						}
						
						logger.debug("VALUE TYPE= "+assignmentParameter.getValueType());
						
					}
				}
			List<RuleAssignmentDetails>  newRuleAssignmentDetailsList = new ArrayList<RuleAssignmentDetails>();
			List<RuleAssignmentDetails>  ruleAssDtl = ruleAss.getRuleAssignmentDetails();
			for (Iterator iterator = ruleAssDtl.iterator(); iterator.hasNext();) {

				RuleAssignmentDetails ruleAssignmentDetails = (RuleAssignmentDetails) iterator.next();
				RuleAssignmentDetails newRuleAssignmentDetails = new RuleAssignmentDetails();
				newRuleAssignmentDetails.setValidityType(ruleAssignmentDetails.getValidityType());
				newRuleAssignmentDetails.setFrequency(ruleAssignmentDetails.getFrequency());
				newRuleAssignmentDetails.setStartDate(ruleAssignmentDetails.getStartDate());
				newRuleAssignmentDetails.setEndDate(ruleAssignmentDetails.getEndDate());
				if(ruleAssignmentDetails.getRuleAssignmentParameter() != null) {
					newRuleAssignmentDetails.setRuleAssignmentParameter(ruleAssignmentDetails.getRuleAssignmentParameter());
				}else {
					newRuleAssignmentDetails.setRuleAssignmentParameter(null);
				}
				
				newRuleAssignmentDetails.setRule(ruleAssignmentDetails.getRule());
				newRuleAssignmentDetailsList.add(newRuleAssignmentDetails);
			}
		
			newRuleAss.setRuleAssignmentDetails(newRuleAssignmentDetailsList);
			
			session.save(newRuleAss);
			tx.commit();
			logger.debug("CREATED A RULE ASSIGNMENT INTO DATABASE" + newRuleAss);
			}
		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newRuleAss.getId();
	}

	/**
	 * Method for getting one rule assignment based on ID
	 * 
	 * @param ruleID
	 * @return
	 */
	public RuleAssignment getRuleAssignment(long ruleAssID) {
		RuleAssignment newRuleAss = new RuleAssignment();

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newRuleAss = (RuleAssignment) session.get(RuleAssignment.class, ruleAssID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newRuleAss;
	}
	
	//get rule assignment detail
	public RuleAssignmentDetails getRuleAssignmentDetail(long ruleAssDetID) {
		RuleAssignmentDetails newRuleAssDet = new RuleAssignmentDetails();

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newRuleAssDet = (RuleAssignmentDetails) session.get(RuleAssignmentDetails.class, ruleAssDetID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newRuleAssDet;
	}
	
	/**
	 * Method for editing rule assignment
	 * @param ruleAss
	 */
	public void editRuleAssignment(RuleAssignment ruleAss) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			RuleAssignment newRuleAss = (RuleAssignment) session.get(RuleAssignment.class, ruleAss.getId());
			
			Employee emp = ruleAss.getEmployee();
			if(emp!=null)
			{
				logger.debug("EMP IN EDIT METHOD= "+ruleAss.getEmployee().getEmployeeName());
				newRuleAss.setEmployee(ruleAss.getEmployee());
			}
			else
			{
				logger.debug("ROLE IN EDIT METHOD= "+ruleAss.getRole().getRoleName());
				newRuleAss.setRole(ruleAss.getRole());
				
			}
			
			
			logger.debug("----RULE ASSIGNMENT DETAILS TO BE CLEARED----");
			for(Iterator itr=newRuleAss.getRuleAssignmentDetails().iterator(); itr.hasNext(); ) {
				RuleAssignmentDetails assignmentDetails = (RuleAssignmentDetails)itr.next();
				
				logger.debug("ID= "+assignmentDetails.getId());
				logger.debug("RULE NAME= "+assignmentDetails.getRule().getRuleName());
				logger.debug("VALIDITY TYPE= "+assignmentDetails.getValidityType());
				logger.debug("START DATE= "+assignmentDetails.getStartDate());
				logger.debug("END DATE= "+assignmentDetails.getEndDate());
				if(assignmentDetails.getRuleAssignmentParameter() != null) {
					for(Iterator itr1 = assignmentDetails.getRuleAssignmentParameter().iterator(); itr1.hasNext();) {
						RuleAssignmentParameter assignmentParameter = (RuleAssignmentParameter)itr1.next();
						logger.debug("PARAM NAME= "+assignmentParameter.getParameterName());
						if(assignmentParameter.getOverwriteValue() != null) {
							logger.debug("OVERWRITE VALUE= "+assignmentParameter.getOverwriteValue());
						}else {
							logger.debug("OVERWRITE VALUE= null");
						}
						logger.debug("VALUE TYPE= "+assignmentParameter.getValueType());
						
					
					}
				}
				
			}
			
		
			
			
			
			newRuleAss.clearRuleAssignmentDetails(newRuleAss.getRuleAssignmentDetails());
			
			
			
			
			
			List<RuleAssignmentDetails>  ruleAssDtl = ruleAss.getRuleAssignmentDetails();
			
			logger.debug("----RULE ASSIGNMENT DETAILS NOW ADDED----");
			for(Iterator itr=ruleAssDtl.iterator(); itr.hasNext(); ) {
				RuleAssignmentDetails assignmentDetails = (RuleAssignmentDetails)itr.next();
				//logger.debug("ID= "+assignmentDetails.getId());
				logger.debug("RULE NAME= "+assignmentDetails.getRule().getRuleName());
				logger.debug("VALIDITY TYPE= "+assignmentDetails.getValidityType());
				logger.debug("START DATE= "+assignmentDetails.getStartDate());
				logger.debug("END DATE= "+assignmentDetails.getEndDate());
				if(assignmentDetails.getRuleAssignmentParameter() != null) {
					for(Iterator itr1 = assignmentDetails.getRuleAssignmentParameter().iterator(); itr1.hasNext();) {
						RuleAssignmentParameter assignmentParameter = (RuleAssignmentParameter)itr1.next();
						logger.debug("PARAM NAME= "+assignmentParameter.getParameterName());
						if(assignmentParameter.getOverwriteValue() != null) {
							logger.debug("OVERWRITE VALUE= "+assignmentParameter.getOverwriteValue());
						}else {
							logger.debug("OVERWRITE VALUE= null");
						}
						logger.debug("VALUE TYPE= "+assignmentParameter.getValueType());
						
					}
				}
			}

//			newRuleAss.addRuleAssignmentDetails(ruleAssDtl);
			
			List<RuleAssignmentDetails>  newRuleAssignmentDetailsList = new ArrayList<RuleAssignmentDetails>();
			for (Iterator iterator = ruleAssDtl.iterator(); iterator.hasNext();) {

				RuleAssignmentDetails ruleAssignmentDetails = (RuleAssignmentDetails) iterator.next();
				RuleAssignmentDetails newRuleAssignmentDetails = new RuleAssignmentDetails();
				newRuleAssignmentDetails.setValidityType(ruleAssignmentDetails.getValidityType());
				newRuleAssignmentDetails.setFrequency(ruleAssignmentDetails.getFrequency());
				newRuleAssignmentDetails.setStartDate(ruleAssignmentDetails.getStartDate());
				newRuleAssignmentDetails.setEndDate(ruleAssignmentDetails.getEndDate());
				if(ruleAssignmentDetails.getRuleAssignmentParameter() != null) {
					newRuleAssignmentDetails.setRuleAssignmentParameter(ruleAssignmentDetails.getRuleAssignmentParameter());
				}else {
					newRuleAssignmentDetails.setRuleAssignmentParameter(null);
				}
				
				newRuleAssignmentDetails.setRule(ruleAssignmentDetails.getRule());
				newRuleAssignmentDetailsList.add(newRuleAssignmentDetails);
			}
		
			newRuleAss.setRuleAssignmentDetails(newRuleAssignmentDetailsList);

			session.merge(newRuleAss);
			
			
	
			
			tx.commit();
			logger.debug("EDITED A RULE ASSIGNMENT INTO DATABASE" + newRuleAss);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	
//	public void editRuleAssignment(RuleAssignment ruleAss, long ruleAssId) {
//		
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			RuleAssignment newRuleAss = (RuleAssignment) session.get(RuleAssignment.class, ruleAssId);
//
//			Employee emp = ruleAss.getEmployee();
//			if(emp!=null)
//			{
//				logger.debug("EMP IN EDIT METHOD= "+ruleAss.getEmployee().getEmployeeName());
//				newRuleAss.setEmployee(ruleAss.getEmployee());
//			}
//			else
//			{
//				logger.debug("ROLE IN EDIT METHOD= "+ruleAss.getRole().getRoleName());
//				newRuleAss.setRole(ruleAss.getRole());
//				
//			}
//			
//			List<RuleAssignmentDetails>  newRuleAssignmentDetailsList = new ArrayList<RuleAssignmentDetails>();
//			List<RuleAssignmentDetails>  ruleAssDtl = ruleAss.getRuleAssignmentDetails();
//			for (Iterator iterator = ruleAssDtl.iterator(); iterator.hasNext();) {
//
//				RuleAssignmentDetails ruleAssignmentDetails = (RuleAssignmentDetails) iterator.next();
//				RuleAssignmentDetails newRuleAssignmentDetails = new RuleAssignmentDetails();
//				newRuleAssignmentDetails.setValidityType(ruleAssignmentDetails.getValidityType());
//				newRuleAssignmentDetails.setFrequency(ruleAssignmentDetails.getFrequency());
//				newRuleAssignmentDetails.setStartDate(ruleAssignmentDetails.getStartDate());
//				newRuleAssignmentDetails.setEndDate(ruleAssignmentDetails.getEndDate());
//				newRuleAssignmentDetails.setRuleAssignmentParameter(ruleAssignmentDetails.getRuleAssignmentParameter());
//				newRuleAssignmentDetails.setRule(ruleAssignmentDetails.getRule());
//				newRuleAssignmentDetailsList.add(newRuleAssignmentDetails);
//			}
//			//editRuleAssignmentDetails(session,newRuleAssignmentDetailsList);
//			
//			session.merge(newRuleAss);
//			
//			tx.commit();
//			//logger.debug("EDITED AN RULE ASSIGNMENT INTO DATABASE" + newRuleAss);
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		
//	}
	
	public List<RuleAssignmentDetails> editRuleAssignmentDetails(Session session,List<RuleAssignmentDetails> ruleAssDtl) {
		
		List<RuleAssignmentDetails>  newRuleAssignmentDetailsList = new ArrayList<RuleAssignmentDetails>();
	
			for (Iterator iterator = ruleAssDtl.iterator(); iterator.hasNext();) {

				RuleAssignmentDetails ruleAssignmentDetails = (RuleAssignmentDetails) iterator.next();
				RuleAssignmentDetails newRuleAssignmentDetails = new RuleAssignmentDetails();
				newRuleAssignmentDetails.setValidityType(ruleAssignmentDetails.getValidityType());
				newRuleAssignmentDetails.setFrequency(ruleAssignmentDetails.getFrequency());
				newRuleAssignmentDetails.setStartDate(ruleAssignmentDetails.getStartDate());
				newRuleAssignmentDetails.setEndDate(ruleAssignmentDetails.getEndDate());
				newRuleAssignmentDetails.setRuleAssignmentParameter(ruleAssignmentDetails.getRuleAssignmentParameter());
				newRuleAssignmentDetails.setRule(ruleAssignmentDetails.getRule());
				newRuleAssignmentDetailsList.add(newRuleAssignmentDetails);
//				logger.debug("RULE ASG VALIDITY TYPE= "+ruleAssignmentDetails.getValidityType());
//				logger.debug("RULE ASG FREQUENCY= "+ruleAssignmentDetails.getFrequency());
//				logger.debug("RULE ASG START DATE= "+ruleAssignmentDetails.getStartDate());
//				logger.debug("RULE ASG END DATE= "+ruleAssignmentDetails.getEndDate());
//				logger.debug("RULE ASG RULE NAME= "+ruleAssignmentDetails.getRule().getRuleName());
//				for (Iterator iterator1 = ruleAssignmentDetails.getRuleAssignmentParameter().iterator(); iterator1.hasNext();) {
//					RuleAssignmentParameter assParam = (RuleAssignmentParameter) iterator1.next();
//					logger.debug("RULE ASG PARAM NAME= "+assParam.getParameterName());
//					logger.debug("RULE ASG OVERWRITE VALUE= "+assParam.getOverwriteValue());
//					logger.debug("RULE ASG TARGET NAME= "+assParam.getTargetDefinition().getDisplayName());
//				}
			}
			session.merge(newRuleAssignmentDetailsList);
		
			logger.debug("EDITED AN RULE ASSIGNMENT INTO DATABASE" + newRuleAssignmentDetailsList);
	
		return newRuleAssignmentDetailsList;
	}

	/**
	 * Method for create Frequency
	 * 
	 * @param conditionList
	 * @return
	 */
	public long createFrequency(Frequency frequency) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Frequency freq = new Frequency();
		try {
			tx = session.beginTransaction();
			freq.setFrequencyName(frequency.getFrequencyName());
			session.save(freq);
			tx.commit();
			logger.debug("CREATED AN FREQUENCY INTO DATABASE" + freq);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return freq.getId();
	}

	/**
	 * Method for searching frequency by name
	 * 
	 * @param freqName
	 * @return
	 */
	public Frequency searchFrequency(String freqName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Frequency freq = new Frequency();
		try {
			tx = session.beginTransaction();
			List frequencies = session.createQuery("FROM Frequency").list();
			for (Iterator iterator = frequencies.iterator(); iterator.hasNext();) {

				Frequency frequency = (Frequency) iterator.next();
				if (freqName.equals(frequency.getFrequencyName())) {
					freq = frequency;
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
		return freq;
	}
	
	
	public TargetDefinition searchTargetDef(String displayName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		TargetDefinition def = new TargetDefinition();
		try {
			tx = session.beginTransaction();
			List targets = session.createQuery("FROM TargetDefinition").list();
			for (Iterator iterator = targets.iterator(); iterator.hasNext();) {

				TargetDefinition targetDef = (TargetDefinition) iterator.next();
				if (displayName.equals(targetDef.getDisplayName())) {
					def = targetDef;
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
		return def;
	}
	
	public List<Frequency> listOfFrequency() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List frequencyList=null;
		try {
		tx = session.beginTransaction();
		frequencyList = session.createQuery("FROM Frequency").list();
		for (Iterator iterator = frequencyList.iterator(); iterator.hasNext();) {
			Frequency frequency = (Frequency) iterator.next();
			logger.debug("GET THE RULE DETAILS FROM DATABASE= " + frequency.getId() +" "+ frequency.getFrequencyName());

		}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return frequencyList;
	}
	/**
	 * Method to set base parameters of chosen rule to assignment rule details
	 * table parameters
	 * 
	 * @param rule
	 * @return
	 */
	public List<RuleAssignmentParameter> setRuleAssignmentParameters(Rule rule) {

		List<RuleAssignmentParameter> assParamList = new ArrayList<RuleAssignmentParameter>();
		List<RuleParameter> rparamlist = rule.getRuleParameter();
		for (Iterator iterator = rparamlist.iterator(); iterator.hasNext();) {
			RuleParameter rparam = (RuleParameter) iterator.next();
			String paramName = rparam.getParameterName();
			String paramValue = rparam.getParameterValue();
			RuleAssignmentParameter assParam = new RuleAssignmentParameter();
			assParam.setParameterName(paramName);
			assParam.setOverwriteValue(paramValue);
			assParamList.add(assParam);
		}
		return assParamList;
	}

	public RuleAssignment searchAssignedRuleForRole(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment ruleAssignment = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			crit.add(Restrictions.isNotNull("role"));
			List assignmentList = crit.list();
			for (Iterator iterator = assignmentList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if (queryName.equals(assignment.getRole().getRoleName())) {
					ruleAssignment = assignment;
					break;
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
		return ruleAssignment;
	}
	
	private List<RuleAssignment> searchAssignedRuleListForRole(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		LinkedHashSet<RuleAssignment> list = new LinkedHashSet<>();
		List<RuleAssignment> ruleList=null;
		//RuleAssignment ruleAssignment = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			crit.add(Restrictions.isNotNull("role"));
			List assignmentList = crit.list();
			for (Iterator iterator = assignmentList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if (queryName.equals(assignment.getRole().getRoleName())) {
					//ruleAssignment = assignment;
					list.add(assignment);
					//break;
				}
			}
			 ruleList = new ArrayList<RuleAssignment>(list);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ruleList;
	}

	public RuleAssignment searchAssignedRuleForEmployee(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment ruleAssignment = null;
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			crit.add(Restrictions.isNotNull("employee"));
			List assignmentList = crit.list();
			for (Iterator iterator = assignmentList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if (queryName.equals(assignment.getEmployee().getEmployeeName())) {
					ruleAssignment = assignment;
					break;
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
		return ruleAssignment;
	}
	
	
	public RuleAssignment searchAssignedRuleForEmployee(long empID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment ruleAssignment = null;
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			crit.add(Restrictions.isNotNull("employee"));
			List assignmentList = crit.list();
			for (Iterator iterator = assignmentList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if (empID == (assignment.getEmployee().getId())) {
					ruleAssignment = assignment;
					break;
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
		return ruleAssignment;
	}
	
	private List<RuleAssignment> searchAssignedRuleListForEmployee(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<RuleAssignment> ruleEmpList = new ArrayList<>();
		//RuleAssignment ruleAssignment = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			crit.add(Restrictions.isNotNull("employee"));
			List assignmentList = crit.list();
			for (Iterator iterator = assignmentList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if (queryName.equals(assignment.getEmployee().getEmployeeName())) {
					//ruleAssignment = assignment;
					ruleEmpList.add(assignment);
					//break;
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
		return ruleEmpList;
	}

	public RuleAssignment searchAssignedRule(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Role role = new Role();
		RuleAssignment ruleAssignment = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			List roleList = session.createQuery("FROM Role").list();
			for (Iterator iterator = roleList.iterator(); iterator.hasNext();) {

				Role role1 = (Role) iterator.next();
				if (queryName.equals(role1.getRoleName())) {
					role = role1;
					ruleAssignment = searchAssignedRuleForRole(queryName);
					break;
				}
			}
			if (role != null) {
				List empList = session.createQuery("FROM Employee").list();
				for (Iterator iterator = empList.iterator(); iterator.hasNext();) {

					Employee emp1 = (Employee) iterator.next();
					if (queryName.equals(emp1.getEmployeeName())) {
						ruleAssignment = searchAssignedRuleForEmployee(queryName);
						break;
					}
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
		if(ruleAssignment != null) {
		return ruleAssignment;
		}else {
			return null;
		}
	}
	
	
	public List<RuleAssignment> searchAssignedRuleList(String queryName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Role role = new Role();
		List<RuleAssignment> ruleAssList = new ArrayList<>();
		//RuleAssignment ruleAssignment = new RuleAssignment();
		try {
			tx = session.beginTransaction();
			List roleList = session.createQuery("FROM Role").list();
			for (Iterator iterator = roleList.iterator(); iterator.hasNext();) {

				Role role1 = (Role) iterator.next();
				if (queryName.equals(role1.getRoleName())) {
					role = role1;
					ruleAssList = searchAssignedRuleListForRole(queryName);
					break;
				}
			}
			if (role != null) {
				List empList = session.createQuery("FROM Employee").list();
				for (Iterator iterator = empList.iterator(); iterator.hasNext();) {

					Employee emp1 = (Employee) iterator.next();
					if (queryName.equals(emp1.getEmployeeName())) {
						ruleAssList = searchAssignedRuleListForEmployee(queryName);
						break;
					}
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
		if(ruleAssList != null) {
		return ruleAssList;
		}else {
			return null;
		}
	}
	
	/**
	 * Method for getting rule for assignment
	 * @param ruleID
	 * @return
	 */
	public RuleAssignmentDetails getRuleToAssign(long ruleID){
		
		RuleAssignmentDetails rulAssDetail = new RuleAssignmentDetails();
		Rule rule = ruleAPI.getRule(ruleID);
		rulAssDetail.setRule(rule);
		rulAssDetail.setRuleAssignmentParameter(setRuleAssignmentParameters(rule));   
		return rulAssDetail;
	}
	
	
	public RuleParameter getParamValue(String paramName, Rule rule) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
//		List<RuleParameter> paramList = new ArrayList<>();
//		try {
//		tx = session.beginTransaction();
//		Criteria crit = session.createCriteria(RuleParameter.class);
//		crit.add(Restrictions.eq("parameterName", paramName));
//		
//		paramList = crit.list();
//				tx.commit();
//
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		if(paramList.isEmpty()) {
//			return null;
//		}else {
//		return paramList.get(0);
//		}
		List<RuleParameter> paramList = rule.getRuleParameter();
		for(RuleParameter parameter : paramList) {
			if(parameter.getParameterName().equalsIgnoreCase(paramName)) {
				return parameter;
			}
		}
		return null;
	}
	
	public RuleAssignment getPreviousRuleAssignmentForEmp(long empID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment asg = null;
		try{
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			//crit.add(Restrictions.isNotNull("emp_id"));
			List ruleAsgList = crit.list();
			for (Iterator iterator = ruleAsgList.iterator(); iterator.hasNext();) {
				
				RuleAssignment assignment = (RuleAssignment) iterator.next();
				if(assignment.getEmployee() != null) {
				if (empID == assignment.getEmployee().getId()) {					
					asg=assignment;
					break;
				}
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
		return asg;
	}
	
	public RuleAssignment getPreviousRuleAssignmentForRole(long roleID) {
		logger.debug("---in method---");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment asg = null;
		try{
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(RuleAssignment.class);
			//crit.add(Restrictions.isNotNull("role_id"));
			List ruleAsgList = crit.list();
			for (Iterator iterator = ruleAsgList.iterator(); iterator.hasNext();) {

				RuleAssignment assignment = (RuleAssignment) iterator.next();
				
				if(assignment.getRole() != null) {
					
				if (roleID == assignment.getRole().getId()) {
					logger.debug("RULE ASG ID FOR ROLE= "+assignment.getId());
					asg=assignment;
					break;                             
				}
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
		return asg;
	}
	
	public List<RuleAssignment> listRuleAssignment(){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List ruleAsgList = session.createQuery("FROM RuleAssignment").list();		
		return ruleAsgList;
	}
	
	
	
	// find list of simple rules of a composite rule
	public List<Rule> getSimpleRuleList(long id){
		List<Rule> simpleRuleList = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		
		List compRules = session.createQuery("FROM RuleComposite").list();
		for (Iterator iterator = compRules.iterator(); iterator.hasNext();) {
					
					RuleComposite ruleComp = (RuleComposite) iterator.next();
					if(ruleComp.getId() == id){
						simpleRuleList = ruleComp.getCompJoinRule();
						break;
					}  
		}
		
		for(Rule rule: simpleRuleList) {
			logger.debug("simple rule in list ="+ rule.getRuleName());
		}
		return simpleRuleList;
	}
	
	
	
	
}	