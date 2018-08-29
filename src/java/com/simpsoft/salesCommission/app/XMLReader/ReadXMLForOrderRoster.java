
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.simpsoft.salesCommission.app.api.EmployeeAPI;
import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.dataloader.OrderLineItemsXML;
import com.simpsoft.salesCommission.app.dataloader.OrderRosterXML;
import com.simpsoft.salesCommission.app.dataloader.OrderXML;
import com.simpsoft.salesCommission.app.model.Address;
import com.simpsoft.salesCommission.app.model.Customer;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.OfficeLocation;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderRoster;
import com.simpsoft.salesCommission.app.model.Product;
import com.simpsoft.salesCommission.app.model.State;

@Component
	public class ReadXMLForOrderRoster {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi"); 
		  EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
		  
		  ReadXMLForOrderRoster rdx = new ReadXMLForOrderRoster();
		  List <OrderRosterXML> orderRosterList = rdx.parseXML();
		  for (Iterator iterator = orderRosterList.iterator(); iterator.hasNext();) {
				
			  OrderRosterXML orderRoster = (OrderRosterXML) iterator.next();
			  OrderRoster newOrderRoster = new OrderRoster();
			  
			  newOrderRoster.setImportDate(orderRoster.getImportDate());			  
			  newOrderRoster.setCountOfOrders(orderRoster.getCountOfOrders());
			  newOrderRoster.setStatus(orderRoster.getStatus());
			  List <OrderXML> orderList = orderRoster.getOrderXML();
			  List<OrderDetail> newOrderList = new ArrayList<OrderDetail>();
			  for (Iterator iterator1 = orderList.iterator(); iterator1.hasNext();) {
					
				  OrderXML order = (OrderXML) iterator1.next();
				 
				 
				  OrderDetail newOrder = new OrderDetail(); 
				  newOrder.setOrderDate(order.getOrderDate());			  
				 
				  Employee salesRepresentative = empAPI.searchEmployee(order.getSalesRepresentative());
				  newOrder.setSalesRepresentative(salesRepresentative);
				  
				  Employee administrator = empAPI.searchEmployee(order.getAdministrator());
				  newOrder.setAdministrator(administrator);
				  
				  Employee supportEngineer = empAPI.searchEmployee(order.getSupportEngineer());
				  newOrder.setSupportEngineer(supportEngineer);
				  
				  Customer customer = ordrAPI.searchCustomer(order.getCustomer());
				  newOrder.setCustomer(customer);
				  
				  //newOrder.setOrderTotal(order.getOrderTotal());
				  OfficeLocation OffLoc = order.getOfficeLocation();
				  Address address = OffLoc.getAddress();
				  State state = address.getState();
				  State newState = ordrAPI.searchState(state.getStateName());
				  address.setState(newState);
				  OffLoc.setAddress(address);
				  newOrder.setOfficeLocation(OffLoc);
				  
				  List <OrderLineItemsXML> orderLineItemList = order.getOrderLineItemsXML();
				  List <OrderLineItems> newOrderLineItemList = new ArrayList<OrderLineItems>();
				  for (Iterator iterator2 = orderLineItemList.iterator(); iterator2.hasNext();) {
						
					  OrderLineItemsXML orderLineItem = (OrderLineItemsXML) iterator2.next();
					 
					 
					  OrderLineItems newOrderLineItem = new OrderLineItems();
					  
					  Product product = ordrAPI.searchProduct(orderLineItem.getProduct());
					  newOrderLineItem.setProduct(product);	  
					 
					  newOrderLineItem.setQuantity(orderLineItem.getQuantity());
					  newOrderLineItem.setRate(orderLineItem.getRate());
					  newOrderLineItem.setDiscountPercentage(orderLineItem.getDiscountPercentage());
					  newOrderLineItem.setDutyPercentage(orderLineItem.getDutyPercentage());
//					  newOrderLineItem.setSubtotal(orderLineItem.getSubtotal());
					 
					  newOrderLineItemList.add(newOrderLineItem);
				  } 
				  
				  newOrder.setOrderLineItems(newOrderLineItemList);
				  newOrderList.add(newOrder);
			  } 
			  
			 Employee employee = empAPI.searchEmployee(orderRoster.getImportedBy());
			  newOrderRoster.setImportedBy(employee);
			  newOrderRoster.setOrderDetail(newOrderList);
			  ordrAPI. createOrderRoster(newOrderRoster);
		  } 
	  }
	  public List<OrderRosterXML> parseXML() {
		  List<OrderRosterXML> importOrderList = new ArrayList<OrderRosterXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/orderRoster.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
	         NodeList nodeList = doc.getElementsByTagName("Import");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String importDate = node.getAttributes().getNamedItem("importDate").getNodeValue();
	                   System.out.println("importDate :" + importDate);
	              
	                   DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                   Date date = df.parse(importDate); 
	                   
	                   String importedBy = (elem.getElementsByTagName("importedBy")
	               		 		.item(0).getChildNodes().item(0).getNodeValue());
	                   System.out.println("importedBy :" + importedBy);
	                   
	                   Integer orderCounts = Integer.parseInt(elem.getElementsByTagName("orderCounts")
                               .item(0).getChildNodes().item(0).getNodeValue());
	                   System.out.println("orderCounts :" + orderCounts);
	                   
	                   String status = (elem.getElementsByTagName("status")
               		 		.item(0).getChildNodes().item(0).getNodeValue());
	                   
	                   List<OrderXML> orderList = new ArrayList<OrderXML>();
						NodeList nodeList1 = elem.getElementsByTagName("order");
						for (int j = 0; j < nodeList1.getLength(); j++) {
							Node node1 = nodeList1.item(j);

							if (node1.getNodeType() == Node.ELEMENT_NODE) {
								Element elem1 = (Element) node1;

								String date1 = node1.getAttributes().getNamedItem("orderDate").getNodeValue();
								System.out.println("orderDate :" + date1);
								
				                Date orderDate = df.parse(importDate); 

								String salesRep = elem1.getElementsByTagName("salesRep").item(0).getChildNodes()
										.item(0).getNodeValue();
								System.out.println("salesRep :" + salesRep);
								
								String admin = elem1.getElementsByTagName("admin").item(0).getChildNodes()
										.item(0).getNodeValue();
								System.out.println("admin :" + admin);
								
								String supportEngineer = elem1.getElementsByTagName("supportEngineer").item(0).getChildNodes()
										.item(0).getNodeValue();
								System.out.println("supportEngineer :" + supportEngineer);
								
								String customer = elem1.getElementsByTagName("customer").item(0).getChildNodes()
										.item(0).getNodeValue();
								System.out.println("customer :" + customer);
								
//								long orderTotal = Integer.parseInt(elem1.getElementsByTagName("orderTotal").item(0).getChildNodes()
//										.item(0).getNodeValue());
//								System.out.println("orderTotal :" + orderTotal);
								
								String officeName = (elem.getElementsByTagName("OfficeName")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
			                   System.out.println("OfficeName :" + officeName);
			                   
			                   int officeCode = Integer.parseInt(elem.getElementsByTagName("OfficeCode")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
			                   System.out.println("OfficeCode :" + officeCode);
			                   
			                   String add_line_1 = (elem.getElementsByTagName("add_line_1")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
			                   System.out.println("add_line_1 :" + add_line_1);
			                   
			                   String add_line_2 = (elem.getElementsByTagName("add_line_2")
		               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
			                   System.out.println("add_line_2 :" + add_line_2);
			                   
			                   String state = (elem.getElementsByTagName("state")
			               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
			                   System.out.println("state :" + state);
								
								 List<OrderLineItemsXML> orderLineItemList = new ArrayList<OrderLineItemsXML>();
									NodeList nodeList2 = elem1.getElementsByTagName("orderLineItem");
									for (int k = 0; k < nodeList2.getLength(); k++) {
										Node node2 = nodeList2.item(k);

										if (node2.getNodeType() == Node.ELEMENT_NODE) {
											Element elem2 = (Element) node2;

											String product = node2.getAttributes().getNamedItem("product").getNodeValue();
											System.out.println("product :" + product);
											
											int quantity = Integer.parseInt(elem2.getElementsByTagName("quantity").item(0).getChildNodes()
													.item(0).getNodeValue());
											System.out.println("quantity :" + quantity);
											
											int rate = Integer.parseInt(elem2.getElementsByTagName("rate").item(0).getChildNodes()
													.item(0).getNodeValue());
											System.out.println("rate :" + rate);
											
											int discountPercentage = Integer.parseInt(elem2.getElementsByTagName("discountPercentage").item(0).getChildNodes()
													.item(0).getNodeValue());
											System.out.println("discountPercentage :" + discountPercentage);
											
											int dutyPercentage = Integer.parseInt(elem2.getElementsByTagName("dutyPercentage").item(0).getChildNodes()
													.item(0).getNodeValue());
											System.out.println("dutyPercentage :" + dutyPercentage);
											
//											long subtotal = Integer.parseInt(elem2.getElementsByTagName("subtotal").item(0).getChildNodes()
//													.item(0).getNodeValue());
//											System.out.println("subtotal :" + subtotal);
											
											
											
											
											OrderLineItemsXML orderLineItem = new OrderLineItemsXML();
											orderLineItem.setProduct(product);
											orderLineItem.setQuantity(quantity);
											orderLineItem.setRate(rate);
											orderLineItem.setDiscountPercentage(discountPercentage);
											orderLineItem.setDutyPercentage(dutyPercentage);
//											orderLineItem.setSubtotal(subtotal);											
											orderLineItemList.add(orderLineItem); 

										}
									}
									Address addr = new Address();
									addr.setAddrslinen1(add_line_1);
									addr.setAddrslinen2(add_line_2);
									State newState = new State();
									newState.setStateName(state);
									addr.setState(newState);
									OfficeLocation offcLoc = new OfficeLocation();
									offcLoc.setOfficeCode(officeCode);
									offcLoc.setOfficeName(officeName);
									offcLoc.setAddress(addr);
									
								OrderXML order = new OrderXML();
								order.setOrderDate(orderDate);
								 order.setOfficeLocation(offcLoc);
								order.setSalesRepresentative(salesRep);
								order.setAdministrator(admin);
								order.setSupportEngineer(supportEngineer);
								order.setCustomer(customer);
								//order.setOrderTotal(orderTotal);
								order.setOrderLineItemsXML(orderLineItemList);
								
								orderList.add(order);

							}
						}
						
						
						
	                   OrderRosterXML order = new OrderRosterXML();	                  
	                   order.setImportDate(date);
	                   order.setImportedBy(importedBy);
	                   order.setCountOfOrders(orderCounts);
	                   order.setStatus(status);
	                   order.setOrderXML(orderList);
	                   importOrderList.add(order);
	                   
	              }
	         }

	       	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return importOrderList;
		  }

	}