
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

import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.ConditionList;

@Component
	public class ReadXMLForConditionList {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  RuleSimpleAPI rSimpAPI = (RuleSimpleAPI) context.getBean("ruleSimpleApi"); 
		  
		  ReadXMLForConditionList rdx = new ReadXMLForConditionList();
		  List <ConditionList> cndList = rdx.parseXML();
		  for (Iterator iterator = cndList.iterator(); iterator.hasNext();) {
				
			  ConditionList conditionList = (ConditionList) iterator.next();
			  rSimpAPI.createCondition(conditionList);
		  } 
	  }
	  public List<ConditionList> parseXML() {
		  List<ConditionList> conditions = new ArrayList<ConditionList>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/ConditionList.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Condition");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String conditionValue = node.getAttributes().getNamedItem("conditionValue").getNodeValue();
	                  // Boolean flag = Boolean.parseBoolean(elem.getElementsByTagName("notFlag")
                              //     .item(0).getChildNodes().item(0).getNodeValue());
	              
	                   ConditionList cnd = new ConditionList();
	                   cnd.setConditionValue(conditionValue);
	               //    cnd.setNotFlag(flag);
	                     conditions.add(cnd);
	                   
	              }
	         }

	         // Print all employees.
	         for (ConditionList cndl : conditions) 
	              System.out.println(cndl.toString());
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return conditions;
		  }

	}