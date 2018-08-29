
package com.simpsoft.salesCommission.app.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.api.SplitRuleAPI;
import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.RuleSimple;
import com.simpsoft.salesCommission.app.model.SplitQualifyingClause;
import com.simpsoft.salesCommission.app.model.SplitRule;
import com.simpsoft.salesCommission.app.model.SplitRuleBeneficiary;

@Component
public class ReadXMLForSplitRule {
	public static void main(String argv[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		
		SplitRuleAPI splitRuleAPI = (SplitRuleAPI) context.getBean("splitRuleApi");
		RuleSimpleAPI ruleSimpleAPI = (RuleSimpleAPI) context.getBean("ruleSimpleApi");
		
		ReadXMLForSplitRule rdx = new ReadXMLForSplitRule();
		List<SplitRule> splitRuleList = rdx.parseXML();
		
		for (Iterator iterator = splitRuleList.iterator(); iterator.hasNext();) {

			SplitRule splitRule = (SplitRule) iterator.next();
			
			SplitRule newSplitRule = new SplitRule();
			
			List<SplitQualifyingClause> splitQClauselist = splitRule.getSplitQualifyingClause();
			List<SplitQualifyingClause> newSQClslst = new ArrayList<>();
			for (Iterator iterator1 = splitQClauselist.iterator(); iterator1.hasNext();) {
				SplitQualifyingClause sQCls = (SplitQualifyingClause) iterator1.next();
				SplitQualifyingClause nSQCluase = new SplitQualifyingClause();
				FieldList fldList = ruleSimpleAPI.searchFieldList(sQCls.getFieldList().getDisplayName());
				ConditionList cnd = ruleSimpleAPI.searchCondition(sQCls.getConditionList().getConditionValue());
				nSQCluase.setConditionList(cnd);
				nSQCluase.setFieldList(fldList);
				nSQCluase.setValue(sQCls.getValue());
				nSQCluase.setNotFlag(sQCls.isNotFlag());
				
				newSQClslst.add(nSQCluase);
			} 
			splitRule.setSplitQualifyingClause(newSQClslst);
			
			splitRuleAPI.createSplitRule(splitRule);
			
		}
	}

	public List<SplitRule> parseXML() {

		List<SplitRule> splitRules = new ArrayList<SplitRule>();	
		try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/splitRule.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("SplitRule");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					String splitRuleName = node.getAttributes().getNamedItem("splRuleName").getNodeValue();
					System.out.println("splitRuleName name :" + splitRuleName);

					String description = (elem.getElementsByTagName("Description").item(0).getChildNodes().item(0)
							.getNodeValue());
					System.out.println("description :" + description);
					
					 String startDate = (elem.getElementsByTagName("StartDate").item(0).getChildNodes().item(0).getNodeValue());                   
					 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					 Date sdate = df.parse(startDate);
					 System.out.println("sdate :" + sdate);
					 
					 String endDate = (elem.getElementsByTagName("EndDate").item(0).getChildNodes().item(0).getNodeValue());                   
					 Date edate = df.parse(endDate);
					 System.out.println("edate :" + edate);
					
					
					List<SplitQualifyingClause> splitQualifyingClause = new ArrayList<SplitQualifyingClause>();
					NodeList nodeList1 = elem.getElementsByTagName("SplitQualifyingClause");
					for (int k = 0; k < nodeList1.getLength(); k++) {
						Node node1 = nodeList1.item(k);

						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element elem1 = (Element) node1;

							String qValue = node1.getAttributes().getNamedItem("value").getNodeValue();
							System.out.println("value :" + qValue);

							String fieldListDisplayName = elem1.getElementsByTagName("FieldList").item(0)
									.getChildNodes().item(0).getNodeValue();
							System.out.println("value :" + fieldListDisplayName);

							Boolean notFlag = Boolean.parseBoolean(elem1.getElementsByTagName("notFlag").item(0)
									.getChildNodes().item(0).getNodeValue());
							System.out.println("value :" + notFlag);

							String conditionListValue = elem1.getElementsByTagName("ConditionList").item(0)
									.getChildNodes().item(0).getNodeValue();
							System.out.println("value :" + conditionListValue); 

							SplitQualifyingClause splitQClause = new SplitQualifyingClause();

							splitQClause.setValue(qValue);

							FieldList fldlst = new FieldList();
							fldlst.setDisplayName(fieldListDisplayName);
							splitQClause.setFieldList(fldlst);

							ConditionList cndlst = new ConditionList();
							cndlst.setConditionValue(conditionListValue);
							splitQClause.setConditionList(cndlst); 

							splitQClause.setNotFlag(notFlag);
							

							splitQualifyingClause.add(splitQClause);

						}
					} 
					
					List<SplitRuleBeneficiary> splitRuleBeneficiaries = new ArrayList<SplitRuleBeneficiary>();
					NodeList nodeList2 = elem.getElementsByTagName("SplitRuleBeneficiary");
					for (int k = 0; k < nodeList2.getLength(); k++) {
						Node node2 = nodeList2.item(k);

						if (node2.getNodeType() == Node.ELEMENT_NODE) {
							Element elem2 = (Element) node2;

							String beneficiaryType = node2.getAttributes().getNamedItem("BeneficiaryType").getNodeValue();
							System.out.println("BeneficiaryType :" + beneficiaryType);

							int splitPercentage = Integer.parseInt(elem2.getElementsByTagName("SplitPercentage").item(0)
									.getChildNodes().item(0).getNodeValue());
							System.out.println("SplitPercentage :" + splitPercentage);

							

							SplitRuleBeneficiary splitRuleBeneficiary = new SplitRuleBeneficiary();

							splitRuleBeneficiary.setBeneficiaryType(beneficiaryType);
							splitRuleBeneficiary.setSplitPercentage(splitPercentage);
							
							splitRuleBeneficiaries.add(splitRuleBeneficiary);

						}
					} 


					SplitRule splitRule = new SplitRule();

					splitRule.setSplitRuleName(splitRuleName);
					splitRule.setDescription(description);
					splitRule.setStartDate(sdate);
					splitRule.setEndDate(edate);
					splitRule.setSplitQualifyingClause(splitQualifyingClause);
					splitRule.setSplitRuleBeneficiary(splitRuleBeneficiaries);
					
					splitRules.add(splitRule);

				}
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return splitRules;
	}

}