package com.simpsoft.salesCommission.app.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeManagerMap;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

/**
 * Class for database operations on Employee
 * 
 * @author NEW2
 *
 */
@Component
public class EmployeeAPI {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private EmployeeAPI employeeApi;
	
	private RoleAPI roleAPI;

	private static final Logger logger = Logger.getLogger(EmployeeAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/**
	 * Method for Create an Employee in database This method is used to create
	 * new employee record in database table.
	 * 
	 * @param employee
	 *            the object that taking all parameters of employee from
	 *            controller
	 */
	public long createEmployee(Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Employee emp = new Employee();
		try {
			tx = session.beginTransaction();
			emp.setEmployeeName(employee.getEmployeeName());
			emp.setStartDate(employee.getStartDate());
			emp.setTerminationDate(employee.getTerminationDate());
			emp.setEmployeeManagerMap(employee.getEmployeeManagerMap());
			emp.setEmployeeRoleMap(employee.getEmployeeRoleMap());
			//emp.setOfficeLocation(employee.getOfficeLocation());
			emp.setTarget(employee.getTarget());
			session.save(emp);	
			tx.commit();
			logger.debug("CREATED AN EMPLOYEE INTO DATABASE" + emp);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return emp.getId();
	}

	/**
	 * Method for getting employee by employeeId from database
	 * 
	 * @param employeeID
	 *            the Id of the employee for whom the details searching for
	 * @return the complete employee details of whom the Id has entered
	 */
	public Employee getEmployee(long employeeID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		logger.debug("GET THE EMPLOYEE DETAILS FROM DATABASE");
		Employee emp= (Employee) session.get(Employee.class, employeeID);
		session.close();
		return emp;
	}

	/**
	 * Method for getting list of employees from database
	 * 
	 * @return the complete employee details of all employees in database
	 */
	public List<Employee> listEmployees() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List employees = session.createQuery("FROM Employee").list();

//		for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
//			Employee employee = (Employee) iterator.next();
//			// logger.debug("GET THE EMPLOYEE DETAILS FROM DATABASE" +
//			// employee.getFirstName()+ employee.getLastName()
//			// +employee.getSalary() );
//
//		}
		return employees;
	}
	

	
	
	/**
	 * Method for getting list of employees from database search by employee name 
	 * 
	 * @return the complete employee details of all employees in database
	 */
	public List<Employee> searchEmployeesByName(String employeeName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List employees = session.createQuery("FROM Employee").list();
		List empList = new ArrayList<>();
		for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
			
			Employee employee = (Employee) iterator.next();
			if(employeeName.equals(employee.getEmployeeName())){
				empList.add(employee);
			}  
			// logger.debug("GET THE EMPLOYEE DETAILS FROM DATABASE" +
			// employee.getFirstName()+ employee.getLastName()
			// +employee.getSalary() );

		}
		return empList;
	}
	
	/**
	 * Method for search one employee by name
	 * @param empName
	 * @return
	 */

	public Employee searchEmployee(String empName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Employee> empList = new ArrayList<>();
		try {
		tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Employee.class);
		crit.add(Restrictions.eq("employeeName", empName));
		empList = crit.list();
				tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		if(empList.isEmpty()) {
			return null;
		}else {
		return empList.get(0);
		}
	}
	
	public Employee searchEmployeeById(long empID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Employee> empList = new ArrayList<>();
		try {
		tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Employee.class);
		crit.add(Restrictions.eq("id", empID));
		empList = crit.list();
				tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		if(empList.isEmpty()) {
			return null;
		}else {
		return empList.get(0);
		}
	}
	
	public void createEmployeeRoleMap(String roleName, long empID) {
		
		
		Role role = searchRole(roleName);
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
		Employee emp =	(Employee) session.get(Employee.class, empID);
		List<EmployeeRoleMap> empRolMapList = emp.getEmployeeRoleMap();
		
		//check if the employee with id empID already has a role
		// if so then set the end date of the previous role to current timestamp
		// before assigning a new role to him/her
		
		if(empRolMapList != null) {
			for (Iterator iterator = empRolMapList.iterator(); iterator.hasNext();) {
				EmployeeRoleMap empOldRoleMap = (EmployeeRoleMap) iterator.next();
//				Role role1 = empOldRoleMap.getRole();
//				String oldRoleName = role1.getRoleName();
				Calendar cal = Calendar.getInstance();
				empOldRoleMap.setEndDate(cal.getTime());
						
			}	
		}
		
		
		//create a new employee role map 
		EmployeeRoleMap empRolMap = new EmployeeRoleMap();
		empRolMap.setRole(role);
		Calendar cal = Calendar.getInstance();	
		
		// set start date to current timestamp and end date to null while creating
		// a new employee role map
		
		empRolMap.setStartDate(cal.getTime());
		empRolMap.setEndDate(null);
		empRolMapList.add(empRolMap);
		emp.setEmployeeRoleMap(empRolMapList);
		session.save(emp);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	//employee manager map
	public void createEmployeeManagerMap(String managerName, long empID) {
		Employee manager = searchEmployee(managerName);
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee emp =	(Employee) session.get(Employee.class, empID);
			List<EmployeeManagerMap> empMgrMapList =emp.getEmployeeManagerMap();
			EmployeeManagerMap empMgrMap = new EmployeeManagerMap();
			empMgrMap.setManager(manager);
			Calendar cal = Calendar.getInstance();
			empMgrMap.setStartDate(cal.getTime());
			empMgrMap.setEndDate(null);
			empMgrMapList.add(empMgrMap);
			emp.setEmployeeManagerMap(empMgrMapList);
			session.save(emp);
				tx.commit();

		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void setEndDate(Date endDate, long empID) {
		logger.debug("_______________________________________________________________");
		logger.debug("=====IN EMP API SET END DATE METHOD===");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee emp =	(Employee) session.get(Employee.class, empID);
			List<EmployeeRoleMap> empRolMapList = emp.getEmployeeRoleMap();
			List<EmployeeRoleMap> newEmpRolMapList = new ArrayList<>();
			for (Iterator iterator = empRolMapList.iterator(); iterator.hasNext();) {
				EmployeeRoleMap erm = (EmployeeRoleMap) iterator.next();
				EmployeeRoleMap empRoleMap = new EmployeeRoleMap();
				
				empRoleMap.setRole(erm.getRole());
				empRoleMap.setId(erm.getId());
				empRoleMap.setStartDate(erm.getStartDate());
				Date startDate= erm.getStartDate();
				Date enddate = erm.getEndDate();
				
				logger.debug("FOR ROLE= "+erm.getRole().getRoleName());
				logger.debug("START DATE= "+startDate);
				logger.debug("END DATE CURRENT= "+enddate);				
				logger.debug("END DATE TO BE ASSIGNED = "+endDate);
				
		if(enddate == null)
				{
					 if(endDate.after(startDate) )
					{
						empRoleMap.setEndDate(endDate);			
						logger.debug("NEW END DATE ASSIGNED TO ROLE= "+erm.getRole().getRoleName());
						 
					}
					 else {
							JOptionPane.showMessageDialog(null, "The end date cannot be earlier than the start date", null,JOptionPane.WARNING_MESSAGE);
							empRoleMap.setEndDate(null);
							
						}
					
							 
				}else {
					empRoleMap.setEndDate(enddate);		
				}
		
			newEmpRolMapList.add(empRoleMap); 
			for (EmployeeRoleMap employeeRoleMap : newEmpRolMapList) {
				logger.debug("ROLE NAME= "+employeeRoleMap.getRole().getRoleName());
				logger.debug("ROLE START DATE= "+employeeRoleMap.getStartDate());
				logger.debug("ROLE END DATE= "+employeeRoleMap.getEndDate());
			}
			} 
			
			emp.setEmployeeRoleMap(newEmpRolMapList);
			session.merge(emp);
			tx.commit();
		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Role searchRole(String roleName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Role> roleList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Role.class);
			crit.add(Restrictions.eq("roleName", roleName));
			roleList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return roleList.get(0);
	}

	
	/**
	 * Method for delete an employee from database
	 * 
	 * @param EmployeeID
	 *            the Id of the employee whom to be delete
	 */
	public void deleteEmployee(Integer EmployeeID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			session.delete(employee);
			logger.debug("DELETE THE EMPLOYEE DETAILS FROM DATABASE" + employee);
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
	 * Method for edit an employee in database
	 * 
	 * @param employee
	 *            the Id of the employee for whom to be edit
	 */
	public void editEmployee(Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee1 = (Employee) session.get(Employee.class, employee.getId());
			employee1.setId(employee.getId());
			employee1.setEmployeeName(employee.getEmployeeName());
			employee1.setTarget(employee.getTarget());
			/*logger.debug("EMP CALC SIMP LIST SIZE = "+employee.getCalcSimpleList().size());
			employee1.setCalcSimpleList(employee.getCalcSimpleList());*/
			session.merge(employee1);
			logger.debug("EDIT THE EMPLOYEE DETAILS FROM DATABASE" + employee1);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	public Long createTarget(TargetDefinition targetDefinition) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		TargetDefinition newTargetDefinition = new TargetDefinition();
		try {
			tx = session.beginTransaction();
			newTargetDefinition.setTargetName(targetDefinition.getTargetName());
			newTargetDefinition.setDisplayName(targetDefinition.getDisplayName());
			session.save(newTargetDefinition);
			tx.commit();
			logger.debug("CREATED AN TARGET DEFINITION INTO DATABASE" + newTargetDefinition);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newTargetDefinition.getId();
	}
	
	public List<TargetDefinition> listOfTargetDefinitions() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		LinkedHashSet<TargetDefinition> defs = new LinkedHashSet<>();
		List<TargetDefinition> defsList=null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TargetDefinition.class);
			criteria.add(Restrictions.isNotNull("targetName"));
			//List targetDefinition = session.createQuery("FROM TargetDefinition").list();
			List targetDefinition =criteria.list();
			for (Iterator iterator = targetDefinition.iterator(); iterator.hasNext();) {
				TargetDefinition target = (TargetDefinition) iterator.next();
				logger.debug("GET THE TARGET DEFINITION DETAILS FROM DATABASE" + target.getId() + target.getDisplayName());
				defs.add(target);
			}
			defsList = new ArrayList<TargetDefinition>(defs);
			tx.commit();
		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return defsList;
	}
	
	public TargetDefinition searchTargetDefinition(String targetName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TargetDefinition> targetList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(TargetDefinition.class);
			crit.add(Restrictions.eq("displayName", targetName));
			targetList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return targetList.get(0);
	}
	
	
	public Frequency searchFrequency(String frequency) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Frequency> freqList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Frequency.class);
			crit.add(Restrictions.eq("frequencyName", frequency));
			freqList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return freqList.get(0);
	}

}