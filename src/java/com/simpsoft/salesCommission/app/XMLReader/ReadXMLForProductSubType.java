
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
import com.simpsoft.salesCommission.app.dataloader.ProductSubTypeXML;
import com.simpsoft.salesCommission.app.model.ProductSubType;
import com.simpsoft.salesCommission.app.model.ProductType;


@Component
	public class ReadXMLForProductSubType {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForProductSubType rdx = new ReadXMLForProductSubType();
		  List <ProductSubTypeXML> productSubTypeXMLList = rdx.parseXML();
		  for (Iterator iterator = productSubTypeXMLList.iterator(); iterator.hasNext();) {
				
			  ProductSubTypeXML productSubType = (ProductSubTypeXML) iterator.next();
			  ProductSubType newProductSubType = new ProductSubType();
			  newProductSubType.setSubType(productSubType.getSubType());
			  ProductType productType = new ProductType();
			  productType.setProdType(productSubType.getProductType());
			  newProductSubType.setProductType(productType);
			  
			  ordrAPI.createProductSubType(newProductSubType);
		  } 
	  }
	  public List<ProductSubTypeXML> parseXML() {
		  List<ProductSubTypeXML> productSubTypeXMLList = new ArrayList<ProductSubTypeXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/productSubType.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("ProductSubType");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String subType = node.getAttributes().getNamedItem("subtypeName").getNodeValue();
	                   System.out.println("subtypeName :" + subType);
	                   
	                   String productType = (elem.getElementsByTagName("typeName").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("typeName :" + productType);
	               
	                   ProductSubTypeXML productSub = new ProductSubTypeXML();
	                   productSub.setSubType(subType);
	                   productSub.setProductType(productType);
	                   productSubTypeXMLList.add(productSub);
	                   
	              }
	         }
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return productSubTypeXMLList;
		  }

	}