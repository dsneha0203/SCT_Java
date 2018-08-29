package com.simpsoft.salesCommission.ui;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.pursuit.salesCommission.app.model.Rule;
import com.pursuit.salesCommission.app.model.Array;

@Controller
public class TestController {
	
	

	@RequestMapping(value = "/ruledetails", method = RequestMethod.GET)
	public String listRules(ModelMap model) {
		Array obj = new Array();
		model.addAttribute("listRule", obj.addRule());
		return "compRule";
	}

	@RequestMapping(value = "/simpRule", method = RequestMethod.GET)
	public ModelAndView rule1() {
		return new ModelAndView("simpRuleDetails", "command", new Rule());
	}

	
	@RequestMapping(value = "/compRule", method = RequestMethod.GET)
	public String listRules1(ModelMap model) {
		Array obj = new Array();
		model.addAttribute("listRule", obj.addRule());
		return "ruleDetails";
	}
}
*/