
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
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.api.RuleAPI;
import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.dataloader.RuleAssignmentDetailsXML;
import com.simpsoft.salesCommission.app.dataloader.RuleAssignmentXML;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleAssignmentParameter;
import com.simpsoft.salesCommission.app.model.RuleParameter;

@Component
public class ReadXMLForRuleAssignment {
	public static void main(String argv[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		RuleAssignmentAPI rulAssAPI = (RuleAssignmentAPI) context.getBean("ruleAssignmentApi");
		EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
		RoleAPI roleAPI = (RoleAPI) context.getBean("roleApi");
		RuleAPI ruleAPI = (RuleAPI) context.getBean("ruleApi");

		ReadXMLForRuleAssignment rdx = new ReadXMLForRuleAssignment();
		List<RuleAssignmentXML> assList = rdx.parseXML();
		for (Iterator iterator = assList.iterator(); iterator.hasNext();) {

			RuleAssignment ruleAss = new RuleAssignment();
			RuleAssignmentXML rlAssXml = (RuleAssignmentXML) iterator.next();
			if (rlAssXml.getEmployeeName() != null) {
				Employee employee = empAPI.searchEmployee(rlAssXml.getEmployeeName());
				ruleAss.setEmployee(employee);
				ruleAss.setRole(null);
			}
			/*
			 * if (rlAssXml.getRoleName() != null) { Role role =
			 * roleAPI.searchRoleByName(rlAssXml.getRoleName());
			 * ruleAss.setRole(role); ruleAss.setEmployee(null); }
			 */
			List<RuleAssignmentDetails> rlAssDtlList = new ArrayList<RuleAssignmentDetails>();
			List<RuleAssignmentDetailsXML> rulAssDetailList = rlAssXml.getRuleAssignmentDetailsXML();
			for (Iterator iterator1 = rulAssDetailList.iterator(); iterator1.hasNext();) {

				RuleAssignmentDetailsXML rlAssDetlxml = (RuleAssignmentDetailsXML) iterator1.next();

				RuleAssignmentDetails rulAssDetails = new RuleAssignmentDetails();
				rulAssDetails.setValidityType(rlAssDetlxml.getValidityType());
				rulAssDetails.setStartDate(rlAssDetlxml.getStartDate());
				rulAssDetails.setEndDate(rlAssDetlxml.getEndDate());

				Rule rule = ruleAPI.getRule(rlAssDetlxml.getRuleId());
				rulAssDetails.setRule(rule);

				if (rlAssDetlxml.getFrequencyName() != null) {
					Frequency frequency = rulAssAPI.searchFrequency(rlAssDetlxml.getFrequencyName());
					rulAssDetails.setFrequency(frequency);
				}

				List<RuleAssignmentParameter> assParamList = rulAssAPI.setRuleAssignmentParameters(rule);
				rulAssDetails.setRuleAssignmentParameter(assParamList);

				rlAssDtlList.add(rulAssDetails);
			}
			ruleAss.setRuleAssignmentDetails(rlAssDtlList);

			rulAssAPI.createRuleAssignment(ruleAss);
		}
	}

	public List<RuleAssignmentXML> parseXML() {
		List<RuleAssignmentXML> rulAssList = new ArrayList<RuleAssignmentXML>();
		try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/ruleAssignmentToEmployee.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("RuleAssignment");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					// String empName =
					// node.getAttributes().getNamedItem("Employeename").getNodeValue();

					String employeeName = (elem.getElementsByTagName("EmployeeName").item(0).getChildNodes().item(0)
							.getNodeValue());

					System.out.println("employeeName :" + employeeName);

					String roleName = (elem.getElementsByTagName("RoleName").item(0).getChildNodes().item(0)
							.getNodeValue());

					System.out.println("roleName :" + roleName);

					List<RuleAssignmentDetailsXML> rulAssDetail = new ArrayList<RuleAssignmentDetailsXML>();
					NodeList nodeList1 = elem.getElementsByTagName("AssignmentDetail");
					for (int j = 0; j < nodeList1.getLength(); j++) {
						Node node1 = nodeList1.item(j);

						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element elem1 = (Element) node1;

							String validityType = node1.getAttributes().getNamedItem("validityType").getNodeValue();

							System.out.println("validityType name :" + validityType);

							String startDate = (elem1.getElementsByTagName("StartDate").item(0).getChildNodes().item(0)
									.getNodeValue());

							System.out.println("startDate :" + startDate);

							String endDate = (elem1.getElementsByTagName("EndDate").item(0).getChildNodes().item(0)
									.getNodeValue());

							System.out.println("endDate :" + endDate);

							Integer ruleID = Integer.parseInt(elem1.getElementsByTagName("RuleID").item(0)
									.getChildNodes().item(0).getNodeValue());

							System.out.println("ruleID :" + ruleID);

							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date sdate = df.parse(startDate);
							Date edate = df.parse(endDate);

							String frequency = elem1.getElementsByTagName("Frequency").item(0).getChildNodes().item(0)
									.getNodeValue();

							System.out.println("frequency value :" + frequency);

							RuleAssignmentDetailsXML rAssXml = new RuleAssignmentDetailsXML();
							rAssXml.setValidityType(validityType);
							if (!frequency.equals("null")) {
								rAssXml.setFrequencyName(frequency);
							}
							rAssXml.setRuleId(ruleID);
							rAssXml.setStartDate(sdate);
							rAssXml.setEndDate(edate);
							rulAssDetail.add(rAssXml);

						}
					}

					RuleAssignmentXML rlAssXml = new RuleAssignmentXML();
					rlAssXml.setEmployeeName(employeeName);
					rlAssXml.setRoleName(roleName);
					rlAssXml.setRuleAssignmentDetailsXML(rulAssDetail);
					rulAssList.add(rlAssXml);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rulAssList;
	}

}