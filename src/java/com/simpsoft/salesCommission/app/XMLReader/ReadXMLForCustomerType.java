
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
import com.simpsoft.salesCommission.app.model.CustomerType;

@Component
	public class ReadXMLForCustomerType {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForCustomerType rdx = new ReadXMLForCustomerType();
		  List <CustomerType> custList = rdx.parseXML();
		  for (Iterator iterator = custList.iterator(); iterator.hasNext();) {
				
			  CustomerType custType = (CustomerType) iterator.next();
			  ordrAPI.createCustomerType(custType);
		  } 
	  }
	  public List<CustomerType> parseXML() {
		  List<CustomerType> custTypeList = new ArrayList<CustomerType>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/customerType.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("CutomerType");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String custType = node.getAttributes().getNamedItem("typeName").getNodeValue();
	                   System.out.println("custType :" + custType);
	              
	                   CustomerType cust = new CustomerType();
	                   cust.setCustType(custType);
	                   custTypeList.add(cust);
	                   
	              }
	         }

	        
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return custTypeList;
		  }

	}