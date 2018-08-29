
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
import com.simpsoft.salesCommission.app.model.AggregateFunctions;


@Component
	public class ReadXMLForAggregateFunction {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  RuleSimpleAPI rSimpAPI = (RuleSimpleAPI) context.getBean("ruleSimpleApi"); 
		  
		  ReadXMLForAggregateFunction rdx = new ReadXMLForAggregateFunction();
		  List <AggregateFunctions> aggtfn = rdx.parseXML();
		  for (Iterator iterator = aggtfn.iterator(); iterator.hasNext();) {
				
			  AggregateFunctions aggtfn1 = (AggregateFunctions) iterator.next();
			  rSimpAPI.createAggregateFunctions(aggtfn1);
		  } 
	  }
	  public List<AggregateFunctions> parseXML() {
		  List<AggregateFunctions> aggregateFns = new ArrayList<AggregateFunctions>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/aggregateFunction.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("AggragateFunction");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String functionName = node.getAttributes().getNamedItem("functionName").getNodeValue();
	                   AggregateFunctions aggt = new AggregateFunctions();
	                   aggt.setFunctionName(functionName);
	              
	                   aggregateFns.add(aggt);
	                   
	              }
	         }

	         // Print all employees.
	     //    for (ConditionList cndl : conditions) 
	        //      System.out.println(cndl.toString());
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return aggregateFns;
		  }

	}