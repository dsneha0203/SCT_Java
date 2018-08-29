package com.simpsoft.salesCommission.ui;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;

@Controller
public class ApplicationController {
	private static final Logger logger = Logger.getLogger(ApplicationController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.debug("ENTERING TO LANDING PAGE");
		//return "login";
		return "index";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "hello";
	}

} 
