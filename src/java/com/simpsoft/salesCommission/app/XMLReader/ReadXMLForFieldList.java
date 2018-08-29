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
import com.simpsoft.salesCommission.app.model.FieldList;



@Component
	public class ReadXMLForFieldList {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  RuleSimpleAPI rSimpAPI = (RuleSimpleAPI) context.getBean("ruleSimpleApi"); 
		  
		  ReadXMLForFieldList rdx = new ReadXMLForFieldList();
		  List <FieldList> fldList = rdx.parseXML();
		  for (Iterator iterator = fldList.iterator(); iterator.hasNext();) {
				
			  FieldList fldList1 = (FieldList) iterator.next();
			  rSimpAPI.createFieldList(fldList1);
		  } 
	  }
	  public List<FieldList> parseXML() {
		  List<FieldList> fldlsts = new ArrayList<FieldList>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/FieldList.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("FieldList");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String fieldName = node.getAttributes().getNamedItem("fieldName").getNodeValue();
	                   String displayName = elem.getElementsByTagName("DisplayName").item(0).getChildNodes().item(0)
								.getNodeValue();
	                   FieldList fld = new FieldList();
	                   fld.setFieldName(fieldName);
	                   fld.setDisplayName(displayName);
	                   fldlsts.add(fld);
	                   
	              }
	         }

	         // Print all employees.
	     //    for (ConditionList cndl : conditions) 
	        //      System.out.println(cndl.toString());
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return fldlsts;
		  }

	}