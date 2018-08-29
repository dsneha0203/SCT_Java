
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
import com.simpsoft.salesCommission.app.dataloader.RuleCompositeXML;
import com.simpsoft.salesCommission.app.model.AggregateFunctions;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.FieldList;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleParameter;
import com.simpsoft.salesCommission.app.model.RuleSimple;

@Component
public class ReadXMLForRuleComposite {
	public static void main(String argv[]) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		RuleAPI ruleAPI = (RuleAPI) context.getBean("ruleApi");
		RuleSimpleAPI ruleSimpleAPI = (RuleSimpleAPI) context.getBean("ruleSimpleApi");
		
		ReadXMLForRuleComposite rdx = new ReadXMLForRuleComposite();
		List<RuleCompositeXML> rList = rdx.parseXML();
		for (Iterator iterator = rList.iterator(); iterator.hasNext();) {

			RuleCompositeXML ruleComp = (RuleCompositeXML) iterator.next();
			Rule newRule = new Rule();
			RuleComposite compRule = new RuleComposite();
			newRule.setRuleName(ruleComp.getRuleName());
			newRule.setDescription(ruleComp.getDescription());
			newRule.setCompensationFormula(ruleComp.getCompensationFormula());
			newRule.setCompensationParameter(ruleComp.getCompensationParameter());
			newRule.setCompensationType(ruleComp.getCompensationType());
			newRule.setFixedCompValue(ruleComp.getFixedCompValue());
			newRule.setRuleType(ruleComp.getRuleType());
			newRule.setConnectionType(ruleComp.getConnectionType());
//			newRule.setRuleParameter(ruleComp.getRuleParameter());
			List<Rule> listOfRules = ruleComp.getCompJoinRule();
			List<Rule> listOfJoinRules = new ArrayList<Rule>();
			for (Iterator iterator1 = listOfRules.iterator(); iterator1.hasNext();) {
				Rule simpRule = (Rule) iterator1.next();
				Rule simple = ruleAPI.searchRuleByName(simpRule.getRuleName());
				listOfJoinRules.add(simple);
			}
			
			
			compRule.setCompJoinRule(listOfJoinRules);
			newRule.setRuleComposite(compRule); 
			ruleAPI.createRule(newRule);
		}
	}

	public List<RuleCompositeXML> parseXML() {

		List<RuleCompositeXML> compRules = new ArrayList<RuleCompositeXML>();	
		try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/ruleComposite.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Rule");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					String ruleName = node.getAttributes().getNamedItem("RuleName").getNodeValue();
					 System.out.println("ruleName :" + ruleName);

					String description = (elem.getElementsByTagName("Description").item(0).getChildNodes().item(0)
							.getNodeValue());
					 System.out.println("description :" + description);

					String ruleType = (elem.getElementsByTagName("RuleType").item(0).getChildNodes().item(0)
							.getNodeValue());
					 System.out.println("ruleType :" + ruleType);
					
					String connectionType = (elem.getElementsByTagName("ConnectionType").item(0).getChildNodes()
							.item(0).getNodeValue());
					 System.out.println("connectionType :" + connectionType);

					String compensationType = (elem.getElementsByTagName("CompensationType").item(0).getChildNodes()
							.item(0).getNodeValue());
					 System.out.println("compensationType :" + compensationType);

					Integer CompensationValue = Integer.parseInt(elem.getElementsByTagName("CompensationValue").item(0)
							.getChildNodes().item(0).getNodeValue());
					 System.out.println("CompensationValue :" + CompensationValue);
					
					String compensationFormula = (elem.getElementsByTagName("CompensationFormula").item(0).getChildNodes()
							.item(0).getNodeValue());
					 System.out.println("compensationFormula :" + compensationFormula);
					
					String compensationParameter = (elem.getElementsByTagName("CompensationParameter").item(0).getChildNodes()
							.item(0).getNodeValue());
					 System.out.println("compensationParameter :" + compensationParameter);
					
//					List<RuleParameter> rulesParameter = new ArrayList<RuleParameter>();
//					NodeList nodeList1 = elem.getElementsByTagName("RuleParameter");
//					for (int j = 0; j < nodeList1.getLength(); j++) {
//						Node node1 = nodeList1.item(j);
//
//						if (node1.getNodeType() == Node.ELEMENT_NODE) {
//							Element elem1 = (Element) node1;
//
//							String parameterName = node1.getAttributes().getNamedItem("ParameterName").getNodeValue();
//
//							System.out.println("param name :" + parameterName);
//
//							String parameterValue = elem1.getElementsByTagName("ParameterValue").item(0).getChildNodes()
//									.item(0).getNodeValue();
//
//							System.out.println("param value :" + parameterValue);
//
//							RuleParameter rp = new RuleParameter();
//							rp.setParameterName(parameterName);
//							rp.setParameterValue(parameterValue);
//							rulesParameter.add(rp);
//
//						}
//					}
						List<Rule> rulesList = new ArrayList<Rule>();
						NodeList nodeList2 = elem.getElementsByTagName("SimpleRule");
						for (int k = 0; k < nodeList2.getLength(); k++) {
							Node node2 = nodeList2.item(k);

							if (node2.getNodeType() == Node.ELEMENT_NODE) {
								Element elem2 = (Element) node2;

								String simpleRuleName = node2.getAttributes().getNamedItem("simpleRuleName").getNodeValue();

								System.out.println("simpleRuleName :" + simpleRuleName);

			
								Rule r = new Rule();
								r.setRuleName(simpleRuleName);
								rulesList.add(r);

							}
					}	

					RuleCompositeXML ruleCompXML = new RuleCompositeXML();

					ruleCompXML.setRuleName(ruleName);
					ruleCompXML.setDescription(description);
					ruleCompXML.setRuleType(ruleType);
//					ruleCompXML.setRuleParameter(rulesParameter);
					ruleCompXML.setCompensationType(compensationType);
					ruleCompXML.setFixedCompValue(CompensationValue);
					ruleCompXML.setCompensationFormula(compensationFormula);
					ruleCompXML.setCompensationParameter(compensationParameter);
					ruleCompXML.setConnectionType(connectionType);
					ruleCompXML.setCompJoinRule(rulesList);

					compRules.add(ruleCompXML);

				}
			}
			
			//for (Rule rl : rules)
			//	System.out.println(rl.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compRules;
	}

}