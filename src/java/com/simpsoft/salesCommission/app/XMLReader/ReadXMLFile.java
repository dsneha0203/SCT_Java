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
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.dataloader.EmployeeManagerMapXML;
import com.simpsoft.salesCommission.app.dataloader.EmployeeRoleMapXML;
import com.simpsoft.salesCommission.app.dataloader.EmployeeXML;
import com.simpsoft.salesCommission.app.model.Address;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.EmployeeManagerMap;
import com.simpsoft.salesCommission.app.model.EmployeeRoleMap;
import com.simpsoft.salesCommission.app.model.OfficeLocation;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.State;

@Component
	public class ReadXMLFile {	
	  public static void main(String argv[]) {
		  ApplicationContext context = 
		            new ClassPathXmlApplicationContext("/applicationContext.xml");
		  EmployeeAPI empAPI = (EmployeeAPI) context.getBean("employeeApi");
		  RoleAPI roleAPI = (RoleAPI) context.getBean("roleApi");
		  OrderAPI ordrAPI = (OrderAPI) context.getBean("orderApi");
		  
		  ReadXMLFile rdx = new ReadXMLFile();
		  List <EmployeeXML> empXmlList = rdx.parseXML();
		  for (Iterator iterator = empXmlList.iterator(); iterator.hasNext();) {
				
			  EmployeeXML employeeXml = (EmployeeXML) iterator.next();
				
			  Employee newEmployee = new Employee();
			  newEmployee.setEmployeeName(employeeXml.getEmployeeName());
			  newEmployee.setStartDate(employeeXml.getStartDate());
//			  OfficeLocation OffLoc = employeeXml.getOfficeLocation();
//			  Address address = OffLoc.getAddress();
//			  State state = address.getState();
//			  State newState = ordrAPI.searchState(state.getStateName());
//			  address.setState(newState);
//			  OffLoc.setAddress(address);
//			  newEmployee.setOfficeLocation(OffLoc);
			  List<EmployeeManagerMapXML> employeeMgrMap = employeeXml.getEmployeeManagerMapXml();
			  List<EmployeeManagerMap> newEmloyeeMgrMap = new ArrayList<EmployeeManagerMap>();
			  
			  for (Iterator iterator1 = employeeMgrMap.iterator(); iterator1.hasNext();) {
				  
				  EmployeeManagerMapXML employeeManagerMapXML  = (EmployeeManagerMapXML) iterator1.next();
				 
				  EmployeeManagerMap empMgrMap = new EmployeeManagerMap();
				  Employee manager = empAPI.searchEmployee(employeeManagerMapXML.getManager());
				  empMgrMap.setManager(manager);
				  empMgrMap.setStartDate(employeeManagerMapXML.getStartDate());
				  empMgrMap.setEndDate(employeeManagerMapXML.getEndDate());
				  newEmloyeeMgrMap.add(empMgrMap);
			  }
			  newEmployee.setEmployeeManagerMap(newEmloyeeMgrMap);
			  
			  List<EmployeeRoleMapXML> employeeRolMap = employeeXml.getEmployeeRoleMapXml();
			  List<EmployeeRoleMap> newEmloyeeRolMap = new ArrayList<EmployeeRoleMap>();
			  
			  for (Iterator iterator2 = employeeRolMap.iterator(); iterator2.hasNext();) {
				  
				  EmployeeRoleMapXML employeeRolMapXML  = (EmployeeRoleMapXML) iterator2.next();
				 
				  EmployeeRoleMap empRolMap = new EmployeeRoleMap();
				  Role role = roleAPI.searchRole(employeeRolMapXML.getRole());
				  empRolMap.setRole(role);
				  empRolMap.setStartDate(employeeRolMapXML.getStartDate());
				  empRolMap.setEndDate(employeeRolMapXML.getEndDate());
				  newEmloyeeRolMap.add(empRolMap);
			  } 
			  
			  newEmployee.setEmployeeRoleMap(newEmloyeeRolMap);
			  
			  empAPI.createEmployee(newEmployee);
		  }
	  }
	  public List<EmployeeXML> parseXML() {
		  List<EmployeeXML> employeesXml = new ArrayList<EmployeeXML>();
		    try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/employee.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			
	         NodeList nodeList = doc.getElementsByTagName("Employee");
	         for (int i = 0; i < nodeList.getLength(); i++) {
	              Node node = nodeList.item(i);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                   Element elem = (Element) node;
	 
	                   String empName = node.getAttributes().getNamedItem("Employeename").getNodeValue();
	                   
	                   String startDate = (elem.getElementsByTagName("StartDate")
	                		 		.item(0).getChildNodes().item(0).getNodeValue());                   
	                   DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                   Date date = df.parse(startDate); 
//	                   
//	                   String officeName = (elem.getElementsByTagName("OfficeName")
//               		 		.item(0).getChildNodes().item(0).getNodeValue());
//	                   System.out.println("OfficeName :" + officeName);
//	                   
//	                   int officeCode = Integer.parseInt(elem.getElementsByTagName("OfficeCode")
//               		 		.item(0).getChildNodes().item(0).getNodeValue());
//	                   System.out.println("OfficeCode :" + officeCode);
//	                   
//	                   String add_line_1 = (elem.getElementsByTagName("add_line_1")
//               		 		.item(0).getChildNodes().item(0).getNodeValue());
//	                   System.out.println("add_line_1 :" + add_line_1);
//	                   
//	                   String add_line_2 = (elem.getElementsByTagName("add_line_2")
//               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
//	                   System.out.println("add_line_2 :" + add_line_2);
//	                   
//	                   String state = (elem.getElementsByTagName("state")
//	               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
//	                   System.out.println("state :" + state);
	                   
	                   Integer salary = Integer.parseInt(elem.getElementsByTagName("Salary")
                               .item(0).getChildNodes().item(0).getNodeValue());
	                 //  System.out.println("OfficeCode :" + officeCode);
	                   
	                   List<EmployeeManagerMapXML> employeeMgrMap = new ArrayList<EmployeeManagerMapXML>();
						NodeList nodeList1 = elem.getElementsByTagName("manager");
						for (int j = 0; j < nodeList1.getLength(); j++) {
							Node node1 = nodeList1.item(j);

							if (node1.getNodeType() == Node.ELEMENT_NODE) {
								Element elem1 = (Element) node1;

								String managerName = node1.getAttributes().getNamedItem("managerName").getNodeValue();
								System.out.println("managerName :" + managerName);

								String mgrSDate = elem1.getElementsByTagName("mgrSDate").item(0).getChildNodes()
										.item(0).getNodeValue();
								System.out.println("mgrSDate :" + mgrSDate);
				                Date date1 = df.parse(mgrSDate); 
				                
				                String mgrEDate = elem1.getElementsByTagName("mgrEDate").item(0).getChildNodes()
										.item(0).getNodeValue();

								System.out.println("mgrEDate :" + mgrEDate);
				                Date date2 = df.parse(mgrEDate); 
								
				                EmployeeManagerMapXML eMgrMap = new EmployeeManagerMapXML();
				               
				                eMgrMap.setStartDate(date1);
				                eMgrMap.setEndDate(date2);
				                eMgrMap.setManager(managerName);
				                employeeMgrMap.add(eMgrMap); 

							}
						}
						
						 List<EmployeeRoleMapXML> employeeRoleMap = new ArrayList<EmployeeRoleMapXML>();
							NodeList nodeList2 = elem.getElementsByTagName("role");
							for (int j = 0; j < nodeList2.getLength(); j++) {
								Node node2 = nodeList2.item(j);

								if (node2.getNodeType() == Node.ELEMENT_NODE) {
									Element elem2 = (Element) node2;

									String roleName = node2.getAttributes().getNamedItem("roleName").getNodeValue();
									System.out.println("roleName :" + roleName);

									String rolSDate = elem2.getElementsByTagName("rolSDate").item(0).getChildNodes()
											.item(0).getNodeValue();
									System.out.println("rolSDate :" + rolSDate);
					                Date date1 = df.parse(rolSDate); 
					                
					                String rolEDate = elem2.getElementsByTagName("rolEDate").item(0).getChildNodes()
											.item(0).getNodeValue();

									System.out.println("rolEDate :" + rolEDate);
					                Date date2 = df.parse(rolEDate); 
									
					                EmployeeRoleMapXML eRoleMap = new EmployeeRoleMapXML();
					               
					                eRoleMap.setStartDate(date1);
					                eRoleMap.setEndDate(date2);
					                eRoleMap.setRole(roleName);
					                employeeRoleMap.add(eRoleMap); 

								}
							}
//							Address addr = new Address();
//							addr.setAddrslinen1(add_line_1);
//							addr.setAddrslinen2(add_line_2);
//							State newState = new State();
//							newState.setStateName(state);
//							addr.setState(newState);
//							OfficeLocation offcLoc = new OfficeLocation();
//							offcLoc.setOfficeCode(officeCode);
//							offcLoc.setOfficeName(officeName);
//							offcLoc.setAddress(addr);
	                     EmployeeXML empXml = new EmployeeXML();
	                     empXml.setEmployeeName(empName);
	                     empXml.setStartDate(date);
	                   //  emp.setSalary(salary);
	                     empXml.setEmployeeManagerMapXml(employeeMgrMap);
	                     empXml.setEmployeeRoleMapXml(employeeRoleMap);
//	                     empXml.setOfficeLocation(offcLoc);
	                   //  empXml.setAddressXml(addrxml);
	                     employeesXml.add(empXml);
	                   
	              }
	         }

	         // Print all employees.
	       //  for (Employee empl : employees) 
	         //     System.out.println(empl.toString());
	   
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    return employeesXml;
		  }

	}