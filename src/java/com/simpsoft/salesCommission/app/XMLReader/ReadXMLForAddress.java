
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
import com.simpsoft.salesCommission.app.dataloader.AddressXML;
import com.simpsoft.salesCommission.app.model.Address;
import com.simpsoft.salesCommission.app.model.ConditionList;
import com.simpsoft.salesCommission.app.model.State;

@Component
	public class ReadXMLForAddress {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForAddress rdx = new ReadXMLForAddress();
		  List <AddressXML> addXMLList = rdx.parseXML();
		  for (Iterator iterator = addXMLList.iterator(); iterator.hasNext();) {
				
			  AddressXML add = (AddressXML) iterator.next();
			  Address newAddress = new Address();
			  newAddress.setAddrslinen1(add.getAddrslinen1());
			  newAddress.setAddrslinen2(add.getAddrslinen2());
			  State state = new State();
			  state.setStateName(add.getState());
			  newAddress.setState(state);
			  ordrAPI.createAddress(newAddress);
		  } 
	  }
	  public List<AddressXML> parseXML() {
		  List<AddressXML> addressesXMLList = new ArrayList<AddressXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/address.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Address");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String add_line_1 = node.getAttributes().getNamedItem("add_line_1").getNodeValue();
	                   System.out.println("stateName :" + add_line_1);
	                   
	                   String add_line_2 = (elem.getElementsByTagName("add_line_2").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("stateName :" + add_line_2);
	                   
	                   String state = (elem.getElementsByTagName("state").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("stateName :" + state);
	                   

	                   AddressXML add = new AddressXML();
	                   add.setAddrslinen1(add_line_1);
	                   add.setAddrslinen2(add_line_2);
	                   add.setState(state);
	                   addressesXMLList.add(add);
	                   
	              }
	         }
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return addressesXMLList;
		  }

	}