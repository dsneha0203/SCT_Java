package com.simpsoft.salesCommission.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.simpsoft.salesCommission.app.UImodel.ImportId;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer1;
import com.simpsoft.salesCommission.app.UImodel.QualifyingClauseUI;
import com.simpsoft.salesCommission.app.UImodel.RoleUI;
import com.simpsoft.salesCommission.app.UImodel.RuleUI;
import com.simpsoft.salesCommission.app.UImodel.TargetListContainer;
import com.simpsoft.salesCommission.app.UImodel.TargetUI;
import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderRoster;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.Target;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

@Controller
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	private RoleAPI roleApi;

	@Autowired
	private EmployeeAPI employeeApi;
	
	@Autowired
	private RuleAssignmentAPI ruleAssApi;
	
	
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String Role(ModelMap model, HttpSession session, HttpServletRequest request) {
		List<Role> roleList = roleApi.listOfRoles();
		model.addAttribute("roleList", roleList);
		
		List<String> boss = new ArrayList<>();
		List<String> reportees = new ArrayList<>();

		for(Role role: roleList) {
			if(role.getReportsTo()==null) {
				boss.add(role.getRoleName());
			}
			for(Role role1: roleList) {
				if(role1.getRoleName() != role.getRoleName()) {
					if( role1.getReportsTo() != null && role1.getReportsTo().getRoleName() == role.getRoleName()) {
						boss.add(role.getRoleName());
						
					}
					
				}
			}
		}
		Set<String> hs1 = new LinkedHashSet<>(boss);
        List<String> bossList = new ArrayList<>(hs1);
        model.addAttribute("bossList", bossList);
        
        
		logger.debug("---BOSSES----");
		for(String bossName : bossList) {
			logger.debug(bossName);
		}
		return "orgStructure";
		
	}

	@RequestMapping(value = "/roleDetails/{id}", method = RequestMethod.GET)
	public String RoleDetails(@PathVariable("id") Long id, ImportId Id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		System.out.println(id);
		Role role = roleApi.getRole(id);
		model.addAttribute("roleDetails", role);
		
		if (session.getAttribute("targetListContainer") == null) {

			session.setAttribute("targetListContainer", getDummytargetListContainer1());
		}
		model.addAttribute("targetListContainer", (TargetListContainer) session.getAttribute("targetListContainer"));

		model.addAttribute("targetDefinition", employeeApi.listOfTargetDefinitions());
		
		List<Target> target = role.getTarget();
		//List<Target> targetList = new ArrayList<Target>();
		for (Iterator iterator = target.iterator(); iterator.hasNext();) {
			Target targetObj = (Target) iterator.next();
			System.out.println(targetObj.getStartDate());
		}
			model.addAttribute("targetDetails", target);
			model.addAttribute("listfrequency", ruleAssApi.listOfFrequency());
			logger.debug("LIST FREQUENCY IN ROLE DETAILS: ");
			for (Iterator iterator = ruleAssApi.listOfFrequency().iterator(); iterator.hasNext();) {
				Frequency frequency = (Frequency) iterator.next();
				logger.debug( frequency.getFrequencyName());

			}
			
		return "RoleDetails";
			
	}
	
	
	@RequestMapping(value = "/roleDetailsCurrentTargets/{id}", method = RequestMethod.GET)
	public String RoleDetailsCurrentTargets(@PathVariable("id") Long id, ImportId Id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		
		System.out.println(id);
		Role role = roleApi.getRole(id);
		model.addAttribute("roleDetails", role);
		
		if (session.getAttribute("targetListContainer") == null) {

			session.setAttribute("targetListContainer", getDummytargetListContainer1());
		}
		model.addAttribute("targetListContainer", (TargetListContainer) session.getAttribute("targetListContainer"));

		model.addAttribute("targetDefinition", employeeApi.listOfTargetDefinitions());
		//get current date/time
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		
		
		List<Target> target = role.getTarget();
		List<Target> currentTargets = new ArrayList<Target>();
		logger.debug("CURRENT DATE/TIME= "+dateFormat.format(cal.getTime()));
		logger.debug("------CURRENT TARGETS FOR ROLE= "+role.getRoleName()+"-----");
		//List<Target> targetList = new ArrayList<Target>();
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
			model.addAttribute("listfrequency", ruleAssApi.listOfFrequency());
			logger.debug("LIST FREQUENCY IN ROLE DETAILS: ");
			for (Iterator iterator = ruleAssApi.listOfFrequency().iterator(); iterator.hasNext();) {
				Frequency frequency = (Frequency) iterator.next();
				logger.debug( frequency.getFrequencyName());

			}
			
		return "RoleDetails";
	}
	

	private TargetListContainer getDummytargetListContainer1() {
		List<TargetUI> targetList = new ArrayList<TargetUI>();
		for (int i = 0; i < 1; i++) {
			targetList.add(new TargetUI());
		}
		return new TargetListContainer();
	}

	@RequestMapping(value = "/submitRoleDetails/{id}", method = RequestMethod.POST)
	public String SubmitRoleDetails(@PathVariable("id") Long id,@ModelAttribute("SpringWeb") TargetUI targetUI, RoleUI roleUI,
			TargetListContainer targetListContainer, HttpSession session, ModelMap model)
					throws java.text.ParseException {

		for (TargetUI T : targetListContainer.getTargetList()) {
			logger.debug("TARGET NAME: " + T.getTargetName());
			logger.debug("START DATE: " + T.getStartDate());
			logger.debug("TERMINATION DATE: " + T.getTerminationDate());
			logger.debug("VALUE: " + T.getValue());
			logger.debug("FREQUENCY: " + T.getFrequency());
		}
		
		model.addAttribute("roleName", roleUI.getRoleName());
		logger.debug("************* ROLE NAME= " + roleUI.getRoleName());
		model.addAttribute("description", roleUI.getDescription());
		logger.debug("************* ROLE DESC=" + roleUI.getDescription());

		Role role = roleApi.searchRoleByName(roleUI.getRoleName());
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
			ptr1.add(target);
		}
		role.setTarget(ptr1);
		roleApi.editRole(role);

		return "redirect:/roleDetails/{id}";
	}
	
	@RequestMapping(value = "/submitNewRoleReportsTo/{id}", method = RequestMethod.POST)
	public String SubmitRoleReportsTo(@PathVariable("id") Long id,@RequestParam("reportsToRoleName") String reportsToRoleName,RoleUI roleUI, ModelMap model,
			HttpServletRequest request2, HttpSession session2, HttpServletResponse response)
					throws ServletException, IOException {
		logger.debug("************* ROLE NAME= " + roleUI.getRoleName());
		logger.debug("*************WANTS TO REPORT TO= " + reportsToRoleName);
		Role role = roleApi.searchRoleByName(roleUI.getRoleName());
		if(role.getRoleName().equals("CEO")) {
			JOptionPane.showMessageDialog(null, 
                    "CEO cannot report to another role!", 
                    "Cannot execute task", 
                    JOptionPane.WARNING_MESSAGE);
			return "redirect:/roleDetails/{id}";
		}
		String getReportsToRole= reportsToRoleName;
		if(role.getRoleName().equals(getReportsToRole)){
			JOptionPane.showMessageDialog(null, 
                    "A role cannot report to itself!", 
                    "Cannot execute task", 
                    JOptionPane.WARNING_MESSAGE);
			return "redirect:/roleDetails/{id}";
		}
		else {
		Role reportsToRole =  roleApi.searchRoleByName(getReportsToRole);
		role.setReportsTo(reportsToRole);
		roleApi.editRole(role);
		}
		model.addAttribute("roleDetails", role);
		return "redirect:/roleDetails/{id}";
	}

}
