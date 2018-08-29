package com.simpsoft.salesCommission.app.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.ui.OrderController;
import com.simpsoft.salesCommission.app.dataloader.RoleXML;

@Component
public class ReadXMLForRole {
	private  final Logger logger = Logger.getLogger(ReadXMLForRole.class);
	public static void main(String argv[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		RoleAPI roleAPI = (RoleAPI) context.getBean("roleApi");

		ReadXMLForRole rdx = new ReadXMLForRole();
		List<RoleXML> roleList = rdx.parseXML();
		for (Iterator iterator = roleList.iterator(); iterator.hasNext();) {

			RoleXML nRoleXML = (RoleXML) iterator.next();
			Role nRole = new Role();
			nRole.setRoleName(nRoleXML.getRoleName());
			nRole.setDescription(nRoleXML.getDescription());
			if (nRoleXML.getReportsTo() != null) {
				Role rl = roleAPI.searchRoleByName(nRoleXML.getReportsTo());
				nRole.setReportsTo(rl);
			}
			roleAPI.createRole(nRole);
		}
	}

	public List<RoleXML> parseXML() {
		List<RoleXML> rolesXML = new ArrayList<RoleXML>();
		try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/role.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Role");
			logger.debug("NODELIST SIZE= "+nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					String roleName = node.getAttributes().getNamedItem("Rolename").getNodeValue();
					 System.out.println("ProductName :" + roleName);
					String description = elem.getElementsByTagName("Description").item(0).getChildNodes().item(0)
							.getNodeValue();
					 System.out.println("ProductName :" + description);
					RoleXML nrole = new RoleXML();
					nrole.setRoleName(roleName);
					nrole.setDescription(description);
					NodeList reportsToNodes = elem.getElementsByTagName("ReportsTo");
					 System.out.println("ProductName :" + reportsToNodes);
					if (reportsToNodes != null && reportsToNodes.getLength() > 0) {
						nrole.setReportsTo(reportsToNodes.item(0).getChildNodes().item(0).getNodeValue());
					}
					rolesXML.add(nrole);

				}
			}

			// Print all employees.
			// for (Employee empl : employees)
			// System.out.println(empl.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rolesXML;
	}

}
