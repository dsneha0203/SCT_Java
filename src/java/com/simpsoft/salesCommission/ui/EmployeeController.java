
package com.simpsoft.salesCommission.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.simpsoft.salesCommission.app.UImodel.EmployeeUI;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer1;
import com.simpsoft.salesCommission.app.UImodel.RoleUI;
import com.simpsoft.salesCommission.app.UImodel.RuleUI;
import com.simpsoft.salesCommission.app.UImodel.TargetListContainer;
import com.simpsoft.salesCommission.app.UImodel.TargetUI;
import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeManagerMap;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.OfficeLocation;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.Target;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeAPI employeeApi;
	@Autowired
	private RuleAPI ruleApi;

	@Autowired
	private RoleAPI roleApi;
	
	@Autowired
	private RuleAssignmentAPI ruleAssApi;

	private static final Logger logger = Logger.getLogger(EmployeeController.class);

	@RequestMapping(value = "/employeeList", method = RequestMethod.GET)
	public String simpleRule(ModelMap model, HttpSession session, HttpServletRequest request) {
		List<Employee> emp = employeeApi.listEmployees();
		for (Employee employee : emp) {
			long empId = employee.getId();
			System.out.println("EMP ID: "+empId);
			String empName = employee.getEmployeeName();
			System.out.println("EMP NAME: "+empName);
			
			Employee emp1 = employeeApi.getEmployee(empId);
			
			request.getSession().setAttribute("empDetailsId", empId);
			
			List<EmployeeManagerMap> manager = emp1.getEmployeeManagerMap();
			List<EmployeeRoleMap> role = emp1.getEmployeeRoleMap();
			for (Iterator iterator = role.iterator(); iterator.hasNext();) {
				EmployeeRoleMap empRoleMap = (EmployeeRoleMap) iterator.next();
				Role role1 = empRoleMap.getRole();
				String roleName = role1.getRoleName();
				model.addAttribute("roleName"+empId, roleName);
				
			}

			for (Iterator iterator = manager.iterator(); iterator.hasNext();) {
				EmployeeManagerMap managerMap = (EmployeeManagerMap) iterator.next();
				Employee managerName = managerMap.getManager();
				String mName = managerName.getEmployeeName();
				long managerId = managerName.getId();
				model.addAttribute("managerName"+empId, mName);
				model.addAttribute("managerId"+empId, managerId);
			}
		}
		
		model.addAttribute("empList", emp);
		
		System.out.println(".......servlet running.......");
		return "employeeList";
	}

	@RequestMapping("/empDetails/{id}")
	public String EmployeeDetails(@PathVariable("id") int id, Employee employee, ModelMap model,
			HttpServletRequest request1, HttpSession session2, HttpServletResponse response)
					throws ServletException, IOException {

		session2.setAttribute("targetListContainer", getDummytargetListContainer1());
		HttpSession session1 = request1.getSession(false);
		if (session1 != null)
			session1.invalidate();
		Employee emp = employeeApi.getEmployee(id);
		request1.getSession().setAttribute("empDetailsId", id);
		model.addAttribute("emp", emp);
		List<EmployeeManagerMap> manager = emp.getEmployeeManagerMap();
		List<EmployeeRoleMap> role = emp.getEmployeeRoleMap();
		for (Iterator iterator = role.iterator(); iterator.hasNext();) {
			EmployeeRoleMap empRoleMap = (EmployeeRoleMap) iterator.next();
			Role role1 = empRoleMap.getRole();
			String roleName = role1.getRoleName();
			request1.getSession().setAttribute("roleName", roleName);
		}

		for (Iterator iterator = manager.iterator(); iterator.hasNext();) {
			EmployeeManagerMap managerMap = (EmployeeManagerMap) iterator.next();
			Employee managerName = managerMap.getManager();
			String mName = managerName.getEmployeeName();
			request1.getSession().setAttribute("managerName", mName);
		}
		
		
		List<Target> target = emp.getTarget();
		//List<Target> targetList = new ArrayList<Target>();
		for (Iterator iterator = target.iterator(); iterator.hasNext();) {
			Target targetObj = (Target) iterator.next();
			System.out.println(targetObj.getStartDate());
		}
			model.addAttribute("targetDetails", target);
			model.addAttribute("listfrequency", ruleAssApi.listOfFrequency());
			logger.debug("LIST FREQUENCY IN EMPLOYEE DETAILS: ");
			for (Iterator iterator = ruleAssApi.listOfFrequency().iterator(); iterator.hasNext();) {
				Frequency frequency = (Frequency) iterator.next();
				logger.debug( frequency.getFrequencyName());

			}

		model.addAttribute("targetDefinition", employeeApi.listOfTargetDefinitions());
		model.addAttribute("roleMap", role);
		model.addAttribute("managerMap", manager);
		model.addAttribute("targetDefinition", employeeApi.listOfTargetDefinitions());
		model.addAttribute("listfrequency", ruleAssApi.listOfFrequency());
		return "empDetails";
	}
	
	
	
	@RequestMapping("/empDetailsCurrentTargets/{id}")
	public String EmployeeDetailsCurrentTargets(@PathVariable("id") int id, Employee employee, ModelMap model,
			HttpServletRequest request1, HttpSession session2, HttpServletResponse response)
					throws ServletException, IOException {

		session2.setAttribute("targetListContainer", getDummytargetListContainer1());
		HttpSession session1 = request1.getSession(false);
		if (session1 != null)
			session1.invalidate();
		Employee emp = employeeApi.getEmployee(id);
		request1.getSession().setAttribute("empDetailsId", id);
		model.addAttribute("emp", emp);
		List<EmployeeManagerMap> manager = emp.getEmployeeManagerMap();
		List<EmployeeRoleMap> role = emp.getEmployeeRoleMap();
		for (Iterator iterator = role.iterator(); iterator.hasNext();) {
			EmployeeRoleMap empRoleMap = (EmployeeRoleMap) iterator.next();
			Role role1 = empRoleMap.getRole();
			String roleName = role1.getRoleName();
			request1.getSession().setAttribute("roleName", roleName);
		}

		for (Iterator iterator = manager.iterator(); iterator.hasNext();) {
			EmployeeManagerMap managerMap = (EmployeeManagerMap) iterator.next();
			Employee managerName = managerMap.getManager();
			String mName = managerName.getEmployeeName();
			request1.getSession().setAttribute("managerName", mName);
		}
		
		//get current date/time
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				
				
				
				List<Target> target = emp.getTarget();
				List<Target> currentTargets = new ArrayList<Target>();
				logger.debug("CURRENT DATE/TIME= "+dateFormat.format(cal.getTime()));
				logger.debug("------CURRENT TARGETS FOR EMPLOYEE= "+emp.getEmployeeName()+"-----");
				
				for (Iterator iterator = target.iterator(); iterator.hasNext();) {
					Target targetObj = (Target) iterator.next();
					logger.debug("START DATE= "+targetObj.getStartDate());
					logger.debug("END DATE= "+targetObj.getTerminationDate());
					if(targetObj.getTerminationDate().after(cal.getTime()) && targetObj.getStartDate().before(cal.getTime()) ) {
						logger.debug("ADDING TARGET '"+targetObj.getTargetDefinition().getDisplayName() +"' TO LIST");
						currentTargets.add(targetObj);
						logger.debug("ADDED TARGET '"+targetObj.getTargetDefinition().getDisplayName() +"' TO LIST");
					}else {
						logger.debug(targetObj.getTargetDefinition().getDisplayName()+ "IS OLD TARGET. NOT ADDED");
					}
					
				}
				
					model.addAttribute("targetDetails", currentTargets);

		
		model.addAttribute("roleMap", role);
		model.addAttribute("managerMap", manager);
		model.addAttribute("targetDefinition", employeeApi.listOfTargetDefinitions());
		model.addAttribute("listfrequency", ruleAssApi.listOfFrequency());
		return "empDetails";
	}

	private TargetListContainer getDummytargetListContainer1() {
		List<TargetUI> targetList = new ArrayList<TargetUI>();
		for (int i = 0; i < 1; i++) {
			targetList.add(new TargetUI());
		}
		return new TargetListContainer();
	}

	@RequestMapping("/showAllRoles/{id}")
	public String ShowAllRoles(@PathVariable("id") int id, Employee employee, ModelMap model) {
		Employee emp = employeeApi.getEmployee(id);
		List<EmployeeRoleMap> role = emp.getEmployeeRoleMap();
		model.addAttribute("roleDetails", role);

		return "empRoleDetails";
	}

	@RequestMapping("/setEndDate")
	public String SetEndDate(Employee employee, ModelMap model) {
		return "SetEndDate";
	}

	
	@RequestMapping(value = "/submitenddate/{id}", method = RequestMethod.POST)
	public String setEndDate(@PathVariable("id") int id, RuleUI ruleUI, ModelMap model){
		logger.debug("_______________________________________________________________");
		logger.debug("====CALLING SET END DATE METHOD OF EMP API====");
		logger.debug("ruleUI.getSetDate()= "+ruleUI.getSetDate());
		Date endDate = ruleUI.getSetDate();
		logger.debug("====CALLING SET END DATE METHOD OF EMP API====");
		employeeApi.setEndDate(endDate, id);
		return "redirect:/showAllRoles/{id}";
	}

	@RequestMapping("/selectRole")
	public String SelectRole(ModelMap model, HttpSession session, HttpServletRequest request) {

		List<Role> roleList = roleApi.listOfRoles();
		model.addAttribute("roleList", roleList);
		return "SelectRole";

	}

	@RequestMapping(value = "/submitNewRole/{id}", method = RequestMethod.POST)
	public String SubmitSelectRole(@PathVariable("id") int id, RoleUI roleUI, ModelMap model,
			HttpServletRequest request2, HttpSession session2, HttpServletResponse response)
					throws ServletException, IOException {
		model.addAttribute("selectRole", roleUI.getSelectRole());
		String selectRoleName = roleUI.getSelectRole();
		long empId = id;
		employeeApi.createEmployeeRoleMap(selectRoleName, empId);
		return "redirect:/empDetails/{id}";

	}

	@RequestMapping("/selectManager")
	public String SelectManager(ModelMap model, HttpSession session, HttpServletRequest request) {
		return "SelectEmpForManager";

	}

	@RequestMapping(value = "/submitEmpManager", method = RequestMethod.POST)
	public String addempSerch(@ModelAttribute("SpringWeb") Employee obj1, ModelMap model) {
		model.addAttribute("EmployeeName", obj1.getEmployeeName());
		List<Employee> emp = employeeApi.searchEmployeesByName(obj1.getEmployeeName());
		model.addAttribute("emp", emp);
		return "SelectEmpForManager";
	}
	
	@RequestMapping(value = "/submitCurrentManager/{id}", method = RequestMethod.POST)
	public String SubmitCurrentManager(@PathVariable("id") int id, EmployeeUI employeeUi, ModelMap model,
			HttpServletRequest request2, HttpSession session2, HttpServletResponse response)
					throws ServletException, IOException {
		model.addAttribute("currentManager", employeeUi.getCurrentManager());
		String currentmanager = employeeUi.getCurrentManager();
		long empId = id;
		employeeApi.createEmployeeManagerMap(currentmanager, empId);
		return "redirect:/empDetails/{id}";

	}

	@RequestMapping("/showAllManagers/{id}")
	public String ShowAllManagers(@PathVariable("id") int id, Employee employee, ModelMap model) {
		Employee emp = employeeApi.getEmployee(id);
		List<EmployeeManagerMap> managers = emp.getEmployeeManagerMap();
		model.addAttribute("managers", managers);

		return "empManagerDetails";

	}
	
	
	
	@RequestMapping(value = "/submitEmpDetails/{id}", method = RequestMethod.POST)
	public String SubmitEmpDetails(@PathVariable("id") Long id, RoleUI roleUI,
			TargetListContainer targetListContainer, HttpSession session, ModelMap model)
					throws java.text.ParseException {

		for (TargetUI T : targetListContainer.getTargetList()) {
			logger.debug("TARGET NAME: " + T.getTargetName());
			logger.debug("START DATE: " + T.getStartDate());
			logger.debug("TERMINATION DATE: " + T.getTerminationDate());
			logger.debug("VALUE: " + T.getValue());
			logger.debug("FREQUENCY: " + T.getFrequency());
		}

		model.addAttribute("empName",employeeApi.getEmployee(id).getEmployeeName());
		logger.debug("************* EMPLOYEE NAME= " + employeeApi.getEmployee(id).getEmployeeName());
		
		//Role role = roleApi.searchRoleByName(roleUI.getRoleName());
		
		Employee emp= employeeApi.searchEmployee(employeeApi.getEmployee(id).getEmployeeName());
		logger.debug("EMP ID= "+emp.getId()+",EMP NAME= "+emp.getEmployeeName());
		
		List<TargetUI> ptr = targetListContainer.getTargetList();		
		List<Target> ptr1 = new ArrayList<>();
		for (Iterator iterator = ptr.iterator(); iterator.hasNext();) {
			TargetUI targetUi = (TargetUI) iterator.next();
			Target target = new Target();
			TargetDefinition targetDeff = employeeApi.searchTargetDefinition(targetUi.getTargetName());
			
			Frequency freq= employeeApi.searchFrequency(targetUi.getFrequency());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString1 = targetUi.getStartDate();
			String dateInString2 = targetUi.getTerminationDate();

			try {

				Date date1 = formatter.parse(dateInString1);
				Date date2 = formatter.parse(dateInString2);
				if(date2.before(date1)) {
					JOptionPane.showMessageDialog(null, 
                            "The termination date cannot be earlier than the start date", 
                            "Cannot add target", 
                            JOptionPane.WARNING_MESSAGE);
					continue;
				}
				target.setStartDate(date1);
				target.setTerminationDate(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			target.setTargetDefinition(targetDeff);
			target.setFrequency(freq);
			
			target.setValue(targetUi.getValue());
			logger.debug("TARGET TO BE ADDED= "+target.getTargetDefinition().getDisplayName());
			logger.debug("FREQUENCY FOR TARGET= "+target.getFrequency().getFrequencyName());
			ptr1.add(target);
		}
		emp.setTarget(ptr1);
		employeeApi.editEmployee(emp);
		

		return "redirect:/empDetails/{id}";
	}

}
