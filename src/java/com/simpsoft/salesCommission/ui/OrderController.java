package com.simpsoft.salesCommission.ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.simpsoft.salesCommission.app.UImodel.DetailsId;
import com.simpsoft.salesCommission.app.UImodel.FileList;
import com.simpsoft.salesCommission.app.UImodel.ImportId;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainer1;
import com.simpsoft.salesCommission.app.UImodel.PersonListContainerBenef;
import com.simpsoft.salesCommission.app.UImodel.QualifyingClauseUI;
import com.simpsoft.salesCommission.app.UImodel.SplitRuleBeneficiaryUI;
import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.api.SplitRuleAPI;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderRoster;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.SplitQualifyingClause;
import com.simpsoft.salesCommission.app.model.SplitRule;
import com.simpsoft.salesCommission.app.model.SplitRuleBeneficiary;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;

@Controller
public class OrderController {

	@Autowired
	private OrderAPI orderApi;
	
	@Autowired
	private RuleSimpleAPI ruleSimpleApi;
	
	@Autowired
	private SplitRuleAPI splitRuleApi;
	
	
	
	
	

	// private static final Logger logger =
	// Logger.getLogger(EmployeeController.class);
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	

	@RequestMapping(value = "/importOrders", method = RequestMethod.GET)
	public String ImportOrder(ModelMap model, HttpSession session, HttpServletRequest request) {
		List<OrderRoster> orm = orderApi.listOfOrderRosters();
		model.addAttribute("listOrder", orm);

		return "Orders";
	}

	private void msgbox(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	public String crunchifySave(@ModelAttribute("uploadForm") FileList uploadForm, Model map)
			throws IllegalStateException, IOException, ParseException {
		//String saveDirectory = "c:/NewFile/";

		List<MultipartFile> fileList = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != fileList && fileList.size() > 0) {
			for (MultipartFile multipartFile : fileList) {

				String fileName = multipartFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					// System.out.println(multipartFile.getInputStream());
					System.out.println(multipartFile.getSize());
					//multipartFile.transferTo(new File(saveDirectory + fileName));
					orderApi.importOrders(multipartFile.getInputStream());
				}
			}
		}

		map.addAttribute("files", fileNames);
		return "redirect:/importOrders";
	}

	@RequestMapping(value = "/orderDetails/{id}", method = RequestMethod.GET)
	public String OrderDetails(@PathVariable("id") int id, ImportId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws ServletException, IOException {
		//model.addAttribute("importId", Id.getImportId());
		//int id = Id.getImportId();
		request.getSession().setAttribute("id",id);
		OrderRoster ors = orderApi.getOrderRoster(id);
		List<OrderDetail> ord = ors.getOrderDetail();
		List<OrderDetail> ord1 = new ArrayList<OrderDetail>();
		List<OrderLineItems> ordLineItems1=new ArrayList<OrderLineItems>();
		List<OrderLineItems> ordLineItems=null;
		List<Float> prodTotal = new ArrayList<Float>();
		List<Float> servTotal = new ArrayList<Float>();;
		List<Float> ordTotal = new ArrayList<Float>();;
		Iterator it = ord.iterator();		
		while (it.hasNext()) {
			float subtotal=0;
			float productTotal=0;
			float serviceTotal=0;
			
			OrderDetail ord2 = (OrderDetail) it.next();
			System.out.println(ord2.getId());
			System.out.println(ord2.getOrderDate());
			ord1.add(ord2);
			
			ordLineItems= ord2.getOrderLineItems();
			
			Iterator it2 = ordLineItems.iterator();
			while(it2.hasNext()) {
				OrderLineItems ordLineItems2 = (OrderLineItems) it2.next();
				ordLineItems1.add(ordLineItems2);
				
				//calculate subtotal for each orderlineitem
				 float rate= ordLineItems2.getRate();
				 float quantity= ordLineItems2.getQuantity();
				 float dutyPercent = ordLineItems2.getDutyPercentage();
				 float discountPercent= ordLineItems2.getDiscountPercentage();
				 
				 
				 subtotal= ((1+(dutyPercent/100))*((1-(discountPercent/100))*(quantity*rate)));
				
				 logger.debug("SUBTOTAL FOR ORDER LINE ITEM "+ordLineItems2.getId()+"= "+subtotal);
				 
				 
				 //get product type of orderlineitem
				 String prodType = ordLineItems2.getProduct().getProductSubType().getProductType().getProdType();
				 
				 if(prodType.equals("Product")) {
					 logger.debug("TYPE PRODUCT. SUBTOTAL ADDED TO PRODUCT TOTAL");
					 productTotal += subtotal;
				 }else {
					 logger.debug("TYPE SERVICE. SUBTOTAL ADDED TO SERVICE TOTAL");
					 serviceTotal += subtotal;
				 }
				
				 logger.debug("product total= "+productTotal);	 
				 logger.debug("service total= "+serviceTotal);	
				 
			
			
			}
			float orderTotal = productTotal + serviceTotal;
			logger.debug("order total= " +orderTotal);
			
			prodTotal.add(productTotal);
			servTotal.add(serviceTotal);
			ordTotal.add(orderTotal);
		}
		model.addAttribute("ordetails", ord1);
		model.addAttribute("ordLineItemsList", ordLineItems1);
		model.addAttribute("prodTotal", prodTotal);
		model.addAttribute("servTotal", servTotal);
		model.addAttribute("ordTotal", ordTotal);
		
		
		logger.debug("PRODUCT TOTAL LIST: ");
		for(float pTot : prodTotal) {
			logger.debug(pTot);
		}
		
		logger.debug("SERVICE TOTAL LIST: ");
		for(float sTot : servTotal) {
			logger.debug(sTot);
		}
		
		logger.debug("ORDER TOTAL LIST: ");
		for(float oTot : ordTotal) {
			logger.debug(oTot);
		}
		
		return "OrderDetails";
	}
	
	@RequestMapping(value = "/verifyOrder/{id}", method = RequestMethod.GET)
	public String VerifyOrder(@PathVariable("id") int id, ImportId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws ServletException, IOException {
		
		OrderRoster ors = orderApi.getOrderRoster(id);
		splitRuleApi.assignSplitRule(ors);
		return "redirect:/importOrders";
	}
	
	@RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable("id") int id, ImportId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws ServletException, IOException {
		
		orderApi.deleteOrderRoster(id);
		
		return "redirect:/importOrders";
	}
	

	@RequestMapping(value = "/lineItemDetails/{id}", method = RequestMethod.GET)
	public String OrderLineItems(@PathVariable("id") int id, DetailsId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		OrderDetail ord1 = orderApi.getOrderDetail(id);
		List<OrderLineItems> ordLi = ord1.getOrderLineItems();
		List<OrderLineItems> ordLi2 = new ArrayList<OrderLineItems>();
		request.getSession().setAttribute("ordDetailId", id);
		Iterator it = ordLi.iterator();
		while (it.hasNext()) {
			OrderLineItems ordLi1 = (OrderLineItems) it.next();
			System.out.println(ordLi1.getDiscountPercentage());
			System.out.println(ordLi1.getDutyPercentage());
			ordLi2.add(ordLi1);
		}
		model.addAttribute("OrderLineItems", ordLi2);
		return "LineItems";

	}
	
	@RequestMapping(value = "/lineItemSplitDetails/{id}", method = RequestMethod.GET)
	public String OrderLineItemsSplit(@PathVariable("id") int id, DetailsId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		OrderLineItems orderLineItem = orderApi.getOrderLineItem(id);
		List<OrderLineItemsSplit> splitlist = orderLineItem.getOrderLineItemsSplit();
		model.addAttribute("splitList",splitlist);
		request.getSession().setAttribute("lineItemId", id);
		return "lineItemsSplitDetails";

	}

	@RequestMapping(value = "/splitDetails/{id}", method = RequestMethod.GET)
	public String SplitOrders(@PathVariable("id") int id, ModelMap model, HttpSession session, HttpServletRequest request) {
		
		session.setAttribute("personListContainer1", getDummyPersonListContainer1());
		session.setAttribute("personListContainer", getDummyPersonListContainer());
		
	

		model.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		model.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
		
		
		
		return "splitOrders";
	}
	
	private PersonListContainer1 getDummyPersonListContainer1() {
		List<QualifyingClauseUI> personList = new ArrayList<QualifyingClauseUI>();
			personList.add(new QualifyingClauseUI());
		return new PersonListContainer1();
	}
	
	
	
	private PersonListContainer getDummyPersonListContainer() {
		List<RuleParameter> personList1 = new ArrayList<RuleParameter>();
		
			personList1.add(new RuleParameter());
	
		return new PersonListContainer(personList1);
	}
	
	
	@RequestMapping(value = "/orderSplitRule", method = RequestMethod.GET)
	public String OrderSplitRule(ImportId Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		
		List<SplitRule> splitRule = splitRuleApi.listOfSplitRule();
		model.addAttribute("splitRuleDetails", splitRule);
		

		
		return "OrderSplitRule";
	}
	
	
	@RequestMapping(value = "/splitRuleDetails/{id}", method = RequestMethod.GET)
	public String SplitRuleDetails(@PathVariable("id") int id, ModelMap model, HttpSession session, HttpServletRequest request) {
		
		session.setAttribute("personListContainer1", getDummyPersonListContainer1()); //qualifying clause
		//session.setAttribute("personListContainer1", getDummyPersonListContainerSplit());
		session.setAttribute("personListContainer", getDummyPersonListContainer());		// beneficiary (rule param)
		
		
		SplitRule splitRule = splitRuleApi.getSplitRule(id);
		model.addAttribute("splitOrdersId", splitRule.getId());
		List<SplitQualifyingClause> qualifyingList = splitRule.getSplitQualifyingClause();
		model.addAttribute("qualifyingList", qualifyingList);
		model.addAttribute("splitRuleDetails", splitRule);
		List<SplitRuleBeneficiary> beneficiary = splitRule.getSplitRuleBeneficiary();
		model.addAttribute("beneficiary", beneficiary);

		
		
		model.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		model.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
		
		List<FieldList> fieldList = ruleSimpleApi.listOfFields();
		logger.debug("SIZE OF FIELD LIST= "+fieldList.size());
		LinkedHashSet<String> fieldNames = new LinkedHashSet<String>();
		for(FieldList f : fieldList) {
			fieldNames.add(f.getDisplayName());
		}
		ArrayList<String> uniqueFieldNameList = new ArrayList<String>(fieldNames);
		logger.debug("SIZE OF UNIQUE FIELD NAME LIST= "+uniqueFieldNameList.size());
		model.addAttribute("fieldNameList", uniqueFieldNameList);
		
		
		List<ConditionList> condList = ruleSimpleApi.listOfConditions();
		LinkedHashSet<String> condNames = new LinkedHashSet<String>();
		for(ConditionList c : condList) {
			condNames.add(c.getConditionValue());
		}
		ArrayList<String> uniqueCondNameList = new ArrayList<String>(condNames);
		logger.debug("SIZE OF UNIQUE CONDITION NAME LIST= "+uniqueCondNameList.size());
		model.addAttribute("condNameList", uniqueCondNameList);
		
		

		return "splitOrders";
	}
	
	@RequestMapping(value = "/newSplitRule", method = RequestMethod.GET)
	public String CreateSplitRule( ModelMap model, HttpSession session, HttpServletRequest request) {
		session.setAttribute("personListContainer1", getDummyPersonListContainer1()); //qualifying clause
		//session.setAttribute("personListContainer1", getDummyPersonListContainerSplit());
		session.setAttribute("personListContainer", getDummyPersonListContainer());		// beneficiary (rule param)


		
		
		model.addAttribute("listRule2", ruleSimpleApi.listOfFields());
		model.addAttribute("listRule3", ruleSimpleApi.listOfConditions());
		
		List<FieldList> fieldList = ruleSimpleApi.listOfFields();
		logger.debug("SIZE OF FIELD LIST= "+fieldList.size());
		LinkedHashSet<String> fieldNames = new LinkedHashSet<String>();
		for(FieldList f : fieldList) {
			fieldNames.add(f.getDisplayName());
		}
		ArrayList<String> uniqueFieldNameList = new ArrayList<String>(fieldNames);
		logger.debug("SIZE OF UNIQUE FIELD NAME LIST= "+uniqueFieldNameList.size());
		model.addAttribute("fieldNameList", uniqueFieldNameList);
		
		
		List<ConditionList> condList = ruleSimpleApi.listOfConditions();
		LinkedHashSet<String> condNames = new LinkedHashSet<String>();
		for(ConditionList c : condList) {
			condNames.add(c.getConditionValue());
		}
		ArrayList<String> uniqueCondNameList = new ArrayList<String>(condNames);
		logger.debug("SIZE OF UNIQUE CONDITION NAME LIST= "+uniqueCondNameList.size());
		model.addAttribute("condNameList", uniqueCondNameList);
		
		
		return "newSplitOrder";
	}
	
	@RequestMapping(value = "/updateSplitRule/{id}", method = RequestMethod.POST)
	public String updateSplitRuleDetails(@PathVariable("id") int id, PersonListContainer1 qualListContainer,PersonListContainer benefListContainer, 
			 ModelMap model, HttpSession session, HttpServletRequest request) {
	
		SplitRule splitRule = splitRuleApi.getSplitRule(id);
		
		List<QualifyingClauseUI> qualList = qualListContainer.getPersonList();
		List<SplitQualifyingClause> splitList = new ArrayList<>();
		for (Iterator iterator = qualList.iterator(); iterator.hasNext();) {
			QualifyingClauseUI tempQual = (QualifyingClauseUI) iterator.next();
			SplitQualifyingClause tempSplit = new SplitQualifyingClause();
			ConditionList condList = new ConditionList();
			condList.setId(tempQual.getConditionId());
			condList.setConditionValue(tempQual.getConditionValue());
			tempSplit.setConditionList(condList);
			FieldList fieldList = new FieldList();
			fieldList.setId(tempQual.getFieldId());
			fieldList.setDisplayName(tempQual.getFieldName());
			fieldList.setFieldName(tempQual.getFn());
			tempSplit.setFieldList(fieldList);
			tempSplit.setValue(tempQual.getValue());
			tempSplit.getConditionList().setConditionValue(tempQual.getConditionValue());
			tempSplit.setNotFlag(tempQual.getCondition());
			
			splitList.add(tempSplit);
		}
		splitRule.setSplitQualifyingClause(splitList);
		
		
		for(RuleParameter B : benefListContainer.getPersonList1()) {
			logger.debug("BENEFICIARY TYPE: " + B.getParameterName());
			logger.debug("SPLIT PERCENTAGE: " + B.getParameterValue());
		}
	
		List<RuleParameter> paramList = benefListContainer.getPersonList1();
		List<SplitRuleBeneficiary> benefList = new ArrayList<>();
		for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
			RuleParameter tempParam = (RuleParameter) iterator.next();
			SplitRuleBeneficiary tempBenef = new SplitRuleBeneficiary();
			tempBenef.setBeneficiaryType(tempParam.getParameterName());
			tempBenef.setSplitPercentage(Integer.parseInt(tempParam.getParameterValue()));
			benefList.add(tempBenef);
		}
		splitRule.setSplitRuleBeneficiary(benefList);
		
		logger.debug("SPLIT RULE NAME= "+request.getParameter("splitRuleName"));
		logger.debug("SPLIT RULE DESC= "+request.getParameter("splitRuleDesc"));
		logger.debug("SPLIT RULE START DATE= "+request.getParameter("startDate"));
		logger.debug("SPLIT RULE END DATE= "+request.getParameter("endDate"));
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString1 = request.getParameter("startDate");
		String dateInString2 = request.getParameter("endDate");

		try {

			Date date1 = formatter.parse(dateInString1);
			Date date2 = formatter.parse(dateInString2);
			logger.debug("DATE1= "+date1);
			logger.debug("DATE2= "+date2);
			if(date2.before(date1)) {
				JOptionPane.showMessageDialog(null, 
                        "The termination date cannot be earlier than the start date", 
                        "Cannot update split rule", 
                        JOptionPane.WARNING_MESSAGE);
				
			}else {
			splitRule.setStartDate(date1);
			splitRule.setEndDate(date2);
			splitRule.setSplitRuleName(request.getParameter("splitRuleName"));
			splitRule.setDescription(request.getParameter("splitRuleDesc"));
			
			splitRuleApi.editSplitRule(splitRule);
			
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "redirect:/splitRuleDetails/"+id;
	
	}
	
	@RequestMapping(value = "/updateSplitRule", method = RequestMethod.POST)
	public String createSplitRule( PersonListContainer1 qualListContainer,PersonListContainer benefListContainer, 
			 ModelMap model, HttpSession session, HttpServletRequest request) {
	
		SplitRule splitRule = new SplitRule();
		
		
		logger.debug("SPLIT RULE NAME= "+request.getParameter("splitRuleName"));
		logger.debug("SPLIT RULE DESC= "+request.getParameter("splitRuleDesc"));
		logger.debug("SPLIT RULE START DATE= "+request.getParameter("startDate"));
		logger.debug("SPLIT RULE END DATE= "+request.getParameter("endDate"));
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString1 = request.getParameter("startDate");
		String dateInString2 = request.getParameter("endDate");

		try {

			Date date1 = formatter.parse(dateInString1);
			Date date2 = formatter.parse(dateInString2);
			logger.debug("DATE1= "+date1);
			logger.debug("DATE2= "+date2);
			if(date2.before(date1)) {
				JOptionPane.showMessageDialog(null, 
                        "The termination date cannot be earlier than the start date", 
                        "Cannot update split rule", 
                        JOptionPane.WARNING_MESSAGE);
				
			}else {
			splitRule.setStartDate(date1);
			splitRule.setEndDate(date2);
			splitRule.setSplitRuleName(request.getParameter("splitRuleName"));
			splitRule.setDescription(request.getParameter("splitRuleDesc"));
			
			long id =splitRuleApi.createSplitRule(splitRule);
			
			SplitRule splitRule2 = splitRuleApi.getSplitRule(id);
			
			List<QualifyingClauseUI> qualList = qualListContainer.getPersonList();
			List<SplitQualifyingClause> splitList = new ArrayList<>();
			for (Iterator iterator = qualList.iterator(); iterator.hasNext();) {
				QualifyingClauseUI tempQual = (QualifyingClauseUI) iterator.next();
				SplitQualifyingClause tempSplit = new SplitQualifyingClause();
				ConditionList condList = new ConditionList();
				condList.setId(tempQual.getConditionId());
				condList.setConditionValue(tempQual.getConditionValue());
				tempSplit.setConditionList(condList);
				FieldList fieldList = new FieldList();
				fieldList.setId(tempQual.getFieldId());
				fieldList.setDisplayName(tempQual.getFieldName());
				fieldList.setFieldName(tempQual.getFn());
				tempSplit.setFieldList(fieldList);
				tempSplit.setValue(tempQual.getValue());
				tempSplit.getConditionList().setConditionValue(tempQual.getConditionValue());
				tempSplit.setNotFlag(tempQual.getCondition());
				
				splitList.add(tempSplit);
			}
			splitRule2.setSplitQualifyingClause(splitList);
			
			
			for(RuleParameter B : benefListContainer.getPersonList1()) {
				logger.debug("BENEFICIARY TYPE: " + B.getParameterName());
				logger.debug("SPLIT PERCENTAGE: " + B.getParameterValue());
			}
		
			List<RuleParameter> paramList = benefListContainer.getPersonList1();
			List<SplitRuleBeneficiary> benefList = new ArrayList<>();
			for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
				RuleParameter tempParam = (RuleParameter) iterator.next();
				SplitRuleBeneficiary tempBenef = new SplitRuleBeneficiary();
				tempBenef.setBeneficiaryType(tempParam.getParameterName());
				tempBenef.setSplitPercentage(Integer.parseInt(tempParam.getParameterValue()));
				benefList.add(tempBenef);
			}
			splitRule2.setSplitRuleBeneficiary(benefList);
			splitRuleApi.editSplitRule(splitRule2);
			return "redirect:/splitRuleDetails/"+id ;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/newSplitRule/";
	}
	
	

	
}
