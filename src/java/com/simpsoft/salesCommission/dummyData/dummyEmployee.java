package com.simpsoft.salesCommission.dummyData;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.Role;


@Component
public class dummyEmployee {
	
	
	
	private static long beginTime;
	private static long endTime; 
	
	private static final Logger logger = Logger.getLogger(dummyEmployee.class);
	
//	public static void setSessionFactory(SessionFactory factory) {
//		sessionFactory = factory;
//	}
	public static void main(String[] args) throws ParseException {
		
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("/applicationContext.xml");
	  EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
	  RoleAPI roleAPI = (RoleAPI) context.getBean("roleApi");
		int num = Integer.parseInt(args[0]);
		int counter=0;
		List<Role> roleList = roleAPI.listOfRoles();
		int roleSize = roleList.size();		
		int min=1;
		Random random = new Random();
		for(int i=1; i<= num; i++) {
			Employee emp = new Employee();
			emp.setEmployeeName("emp_"+String.valueOf(i));
			emp.setSalary(0);
			//logger.debug("RANDOM DATE= "+generate_random_date_java());
			Date startDate=generate_random_date_java() ;
			emp.setStartDate(startDate);

			
			List<EmployeeRoleMap> empRoleMapList = new ArrayList<EmployeeRoleMap>();

			//generate a random role id from the available list
			int roleId = random.nextInt(roleSize-min) + min;
			for(Role role: roleList) {
				if(role.getId() == roleId) {
					
						EmployeeRoleMap empRoleMap = new EmployeeRoleMap();
						empRoleMap.setRole(role);
		
						// let the start date of a role for an employee be the
						// start date of that employee
						empRoleMap.setStartDate(startDate);
						
						if(role.getRoleName().equals("CEO")) {
							if(counter==0) {
							empRoleMapList.add(empRoleMap);
							counter=counter+1;
							emp.setEmployeeRoleMap(empRoleMapList);
							break;
							}else {
								roleId = random.nextInt(roleSize-min) + min;
								continue;
							}
							
						}else {
							empRoleMapList.add(empRoleMap);
							emp.setEmployeeRoleMap(empRoleMapList);
							break;
						}
				}
			}
				
			
		
			
			logger.debug("EMPLOYEE = "+ emp.toString());
			 empAPI.createEmployee(emp);
			
			
		}
		
	}

//
//	private static void createEmployee(Employee emp) {
//		logger.debug("in method");
//		
//		try {
//			if(sessionFactory== null) {
//				logger.debug("SESSION FACTORY IS NULL");
//			}
//		Session session = sessionFactory.openSession();
//		
//		Transaction tx =session.beginTransaction();
//		try {
//					 
//			 Employee emp1 = new Employee();
//		        emp1.setEmployeeName(emp.getEmployeeName());
//		        emp1.setStartDate(emp.getStartDate());
//		        emp1.setTerminationDate(emp.getTerminationDate());
////		        emp1.setOfficeLocation(emp.getOfficeLocation());
////		        emp1.setEmployeeManagerMap(emp.getEmployeeManagerMap());
////		        emp1.setEmployeeRoleMap(emp.getEmployeeRoleMap());
////		        emp1.setTarget(emp.getTarget());
//		        session.save(emp1);
//		        tx.commit();
//		        
//			
//			logger.debug("CREATED AN EMPLOYEE INTO DATABASE" + emp);
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//}catch(Exception e) {
//			logger.debug("Null pointer exception");
//		}
//	}
	public static Date generate_random_date_java() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
	            "yyyy-MM-dd hh:mm:ss");
		Date randomDate = null;
		String date = null;
	    for (int x = 0; x < 7; x++) {
	         randomDate = new Date(getRandomTimeBetweenTwoDates());
	         date=dateFormat.format(randomDate);
	        
	    }
	   // logger.debug("DATE= "+dateFormat.parse(date));
	    return dateFormat.parse(date);
	}
	
	
	private static long getRandomTimeBetweenTwoDates () {
		 beginTime = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
		 endTime = Timestamp.valueOf("2017-12-31 00:58:00").getTime();
	    long diff = endTime - beginTime + 1;
	    return beginTime + (long) (Math.random() * diff);
	}
}
