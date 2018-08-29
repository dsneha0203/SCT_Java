package com.simpsoft.salesCommission.ui;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pursuit.salesCommission.app.api.EmployeeAPI;
import com.pursuit.salesCommission.app.api.RoleAPI;
import com.pursuit.salesCommission.app.model.Employee;
import com.pursuit.salesCommission.app.model.Role;
import com.pursuit.salesCommission.app.model.Rule;
import com.pursuit.salesCommission.app.model.empSerch;
import javax.servlet.http.HttpServletRequest;

@Controller
public class EmployeeSearchController {
	@Autowired
	private EmployeeAPI empApi;

	 @RequestMapping(value = "/search", method = RequestMethod.GET)
		public ModelAndView showForm() {
			return new ModelAndView("SearchEmployee", "command", new Employee());	
		}
@RequestMapping(value = "/searchempimage", method = RequestMethod.POST)
	public String addempSerch(@ModelAttribute("SpringWeb") Employee obj1, ModelMap model) {
		model.addAttribute("EmployeeName", obj1.getEmployeeName());	
		System.out.println("********" + obj1.getEmployeeName());
		empApi.searchEmployeesByName(obj1.getEmployeeName());
		model.addAttribute("listEmp", empApi.searchEmployeesByName(obj1.getEmployeeName()));
		System.out.println(".........successfully submit..........");
					
		return "SearchEmployee";	
}

 /*@RequestMapping(value = "/empList", method = RequestMethod.GET)
public String listEmp(ModelMap model) {
	//model.addAttribute("employee", new Role());
	model.addAttribute("listEmp", empApi.listEmployees());
	System.out.println("working");
	System.out.println(".........list done..........");
	return "SearchEmployee";
}

@RequestMapping("/list")
public String listEmp(@PathVariable("id") int id, ModelMap model) {
	model.addAttribute("emp",empApi.searchEmployeesByName(employeeName));
	model.addAttribute("listEmp", empApi.listEmployees());
	System.out.println(".........list emp running..........");
	return "SearchEmployee";
	
}
}*/
	

