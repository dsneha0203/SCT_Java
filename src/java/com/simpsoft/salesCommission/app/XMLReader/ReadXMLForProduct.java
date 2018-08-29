
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
import com.simpsoft.salesCommission.app.dataloader.ProductXML;
import com.simpsoft.salesCommission.app.model.Product;
import com.simpsoft.salesCommission.app.model.ProductSubType;
import com.simpsoft.salesCommission.app.model.ProductType;


@Component
	public class ReadXMLForProduct {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  
		  ReadXMLForProduct rdx = new ReadXMLForProduct();
		  List <ProductXML> productXMLList = rdx.parseXML();
		  for (Iterator iterator = productXMLList.iterator(); iterator.hasNext();) {
				
			  ProductXML ProductXML = (ProductXML) iterator.next();
			  Product newProduct = new Product();
			  newProduct.setProductName(ProductXML.getProductName());
			  newProduct.setDescription(ProductXML.getDescription());
			  ProductSubType newProductSubType = new ProductSubType();
			  newProductSubType.setSubType(ProductXML.getProductSubType());
			  newProduct.setProductSubType(newProductSubType);
			  ordrAPI.createProduct(newProduct); 
		  } 
	  }
	  public List<ProductXML> parseXML() {
		  List<ProductXML> productXMLList = new ArrayList<ProductXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/product.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Product");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String productName = node.getAttributes().getNamedItem("ProductName").getNodeValue();
	                   System.out.println("ProductName :" + productName);
	                   
	                   String description = (elem.getElementsByTagName("description").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("description :" + description);
	                   
	                   String productSubType = (elem.getElementsByTagName("productSubType").item(0).getChildNodes().item(0)
								.getNodeValue());
	                   System.out.println("productSubType :" + productSubType);
	               
	                   ProductXML productXml = new ProductXML();
	                   productXml.setProductName(productName);
	                   productXml.setDescription(description);
	                   productXml.setProductSubType(productSubType);
	                   productXMLList.add(productXml);
	                   
	              }
	         }
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return productXMLList;
		  }

	}