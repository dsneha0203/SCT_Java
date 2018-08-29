
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

@Component
	public class ReadXMLForTarget {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
		  
		  ReadXMLForTarget rdx = new ReadXMLForTarget();
		  List <TargetDefinition> tgtList = rdx.parseXML();
		  for (Iterator iterator = tgtList.iterator(); iterator.hasNext();) {
				
			  TargetDefinition target = (TargetDefinition) iterator.next();
			  empAPI.createTarget(target);
		  } 
	  }
	  public List<TargetDefinition> parseXML() {
		  List<TargetDefinition> targets = new ArrayList<TargetDefinition>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/targetDefinition.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Target");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String targetName = node.getAttributes().getNamedItem("targetName").getNodeValue();
	                   String displayName = (elem.getElementsByTagName("displayName")
                                     .item(0).getChildNodes().item(0).getNodeValue());
	              
	                   TargetDefinition tgt = new TargetDefinition();
	                   tgt.setTargetName(targetName);
	                   tgt.setDisplayName(displayName);
	                   targets.add(tgt);
	                   
	              }
	         }

	         // Print all employees.
	       //  for (ConditionList cndl : conditions) 
	   //           System.out.println(cndl.toString());
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return targets;
		  }

	}