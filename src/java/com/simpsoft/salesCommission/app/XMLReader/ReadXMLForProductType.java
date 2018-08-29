

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
import com.simpsoft.salesCommission.app.model.ProductType;

@Component
	public class ReadXMLForProductType {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForProductType rdx = new ReadXMLForProductType();
		  List <ProductType> productList = rdx.parseXML();
		  for (Iterator iterator = productList.iterator(); iterator.hasNext();) {
				
			  ProductType productType = (ProductType) iterator.next();
			  ordrAPI.createProductType(productType);
		  } 
	  }
	  public List<ProductType> parseXML() {
		  List<ProductType> productTypeList = new ArrayList<ProductType>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/productType.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("ProductType");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String prodType = node.getAttributes().getNamedItem("typeName").getNodeValue();
	                   System.out.println("custType :" + prodType);
	              
	                   ProductType product = new ProductType();
	                   product.setProdType(prodType);;
	                   productTypeList.add(product);
	                   
	              }
	         }

	        
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return productTypeList;
		  }

	}