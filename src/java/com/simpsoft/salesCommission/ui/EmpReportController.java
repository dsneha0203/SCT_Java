package com.simpsoft.salesCommission.ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.Role;


@Controller
public class EmpReportController{
	@Autowired
	private EmployeeAPI employeeApi;
	
	
	@RequestMapping(value = "/empReportDetails", method = RequestMethod.GET)
	public String getReport(ModelMap model, HttpSession session, HttpServletRequest request) {
		List<Employee> emp = employeeApi.listEmployees();
		model.addAttribute("empList", emp);
		return "empReportDetails";
	}
	
	@RequestMapping(value = "/empReportDetails/{id}")
	public String getReportDetails(@PathVariable("id") int id, Employee employee, ModelMap model,
			HttpServletRequest request, HttpSession session, HttpServletResponse response)
					throws ServletException, IOException{
		
		HttpSession session1 = request.getSession(false);
		if (session1 != null) {
			session1.invalidate();
		}
		List<Employee> emplist = employeeApi.listEmployees();
		model.addAttribute("empList", emplist);
		
		Employee emp = employeeApi.getEmployee(id);
		request.getSession().setAttribute("empDetailsId", id);
		
		model.addAttribute("emp", emp);
		
		List<EmployeeRoleMap> role = emp.getEmployeeRoleMap();
		System.out.println("ROLE LIST SIZE= "+role.size());
		if(role.size()>0) {
		for (Iterator iterator = role.iterator(); iterator.hasNext();) {
			EmployeeRoleMap empRoleMap = (EmployeeRoleMap) iterator.next();
			Role role1 = empRoleMap.getRole();
			String roleName = role1.getRoleName();
			System.out.println("ROLE NAME= "+roleName);
			model.put("role_Name", roleName);
			request.getSession().setAttribute("role_Name", roleName);
			
			System.out.println("ROLE NAME IN SESSION= "+request.getSession().getAttribute("role_Name"));
		}
		}else {
			request.getSession().setAttribute("role_Name", "Not assigned yet");
			model.put("role_Name", "Not assigned yet");
			System.out.println("ROLE NAME IN SESSION= "+request.getSession().getAttribute("role_Name"));
		}
		return "empReportDetails";
	}
	
}