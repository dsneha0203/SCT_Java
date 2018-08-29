
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

import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.api.RuleSimpleAPI;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.State;

@Component
	public class ReadXMLForState {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForState rdx = new ReadXMLForState();
		  List <State> cndList = rdx.parseXML();
		  for (Iterator iterator = cndList.iterator(); iterator.hasNext();) {
				
			  State state = (State) iterator.next();
			  ordrAPI.createState(state);
		  } 
	  }
	  public List<State> parseXML() {
		  List<State> states = new ArrayList<State>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/state.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("State");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String stateName = node.getAttributes().getNamedItem("stateName").getNodeValue();
	                   System.out.println("stateName :" + stateName);
	                   
	                   Integer stateCode = Integer.parseInt(elem.getElementsByTagName("stateCode").item(0)
								.getChildNodes().item(0).getNodeValue());
	                   System.out.println("stateCode :" + stateCode);

	                   State state = new State();
	                   state.setStateName(stateName);
	                   state.setStateCode(stateCode);
	                   states.add(state);
	                   
	              }
	         }
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return states;
		  }

	}