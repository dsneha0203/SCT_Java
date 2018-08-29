
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
import com.simpsoft.salesCommission.app.dataloader.CustomerXML;
import com.simpsoft.salesCommission.app.model.Address;
import com.simpsoft.salesCommission.app.model.Customer;
import com.simpsoft.salesCommission.app.model.CustomerType;
import com.simpsoft.salesCommission.app.model.State;

@Component
	public class ReadXMLForCustomer {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForCustomer rdx = new ReadXMLForCustomer();
		  List <CustomerXML> custList = rdx.parseXML();
		  for (Iterator iterator = custList.iterator(); iterator.hasNext();) {
				
			  CustomerXML customerXML = (CustomerXML) iterator.next();
			  
			  Customer newCustomer = new Customer();
			  
			  Address newAddress = new Address();
			  newAddress.setAddrslinen1(customerXML.getAddressLine1());
			  newAddress.setAddrslinen2(customerXML.getAddressLine2());
			  State state = new State();
			  state.setStateName(customerXML.getState());
			  newAddress.setState(state);
			  
			  CustomerType customerType = new CustomerType();
			  customerType.setCustType(customerXML.getCustomerType());
			  newCustomer.setCustomerName(customerXML.getCustomerName());
			  newCustomer.setAddress(newAddress);
			  newCustomer.setCustomerType(customerType);	
	  
			  ordrAPI.createCustomer(newCustomer);
		  } 
	  }
	  
	  public List<CustomerXML> parseXML() {
		  List<CustomerXML> custList = new ArrayList<CustomerXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/customer.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Customer");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String customerName = node.getAttributes().getNamedItem("CustomerName").getNodeValue();
	                   System.out.println("customerName :" + customerName);
	                   
	                   String add_line_1 = (elem.getElementsByTagName("add_line_1").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("add_line_1 :" + add_line_1);
	                   
	                   String add_line_2 = (elem.getElementsByTagName("add_line_2").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("add_line_2 :" + add_line_2);
	                   
	                   String state = (elem.getElementsByTagName("state").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("stateName :" + state);
	                   
	                   String custType = (elem.getElementsByTagName("custType").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("custType :" + custType);
	              
	                   CustomerXML custXML = new CustomerXML();
	                   custXML.setCustomerName(customerName);
	                   custXML.setAddressLine1(add_line_1);
	                   custXML.setAddressLine2(add_line_2);
	                   custXML.setState(state);
	                   custXML.setCustomerType(custType);
	                   custList.add(custXML);
	                   
	              }
	         }

	        
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return custList;
		  }

	}