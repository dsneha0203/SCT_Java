package com.simpsoft.salesCommission.app.api;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.simpsoft.salesCommission.app.dataloader.OrderLineItemsXML;
import com.simpsoft.salesCommission.app.dataloader.OrderRosterXML;
import com.simpsoft.salesCommission.app.dataloader.OrderXML;
import com.simpsoft.salesCommission.app.model.Address;
import com.simpsoft.salesCommission.app.model.Customer;
import com.simpsoft.salesCommission.app.model.CustomerType;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.OfficeLocation;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;
import com.simpsoft.salesCommission.app.model.OrderRoster;
import com.simpsoft.salesCommission.app.model.Product;
import com.simpsoft.salesCommission.app.model.ProductSubType;
import com.simpsoft.salesCommission.app.model.ProductType;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.SplitQualifyingClause;
import com.simpsoft.salesCommission.app.model.SplitRule;
import com.simpsoft.salesCommission.app.model.SplitRuleBeneficiary;
import com.simpsoft.salesCommission.app.model.State;

@Component
public class OrderAPI {

	@Autowired
	private static SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(RuleAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	public Long createState(State state) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		State newState = new State();
		try {
			tx = session.beginTransaction();
			newState.setStateName(state.getStateName());
			newState.setStateCode(state.getStateCode());
			session.save(newState);
			tx.commit();
			logger.debug("CREATED AN AGGREGATE FUNCTION INTO DATABASE" + newState);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newState.getId();
	}

	/**
	 * 
	 * @param address
	 * @return
	 */
	public Address createAddress(Address address) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Address newAddress = new Address();
		try {
			tx = session.beginTransaction();
			newAddress.setAddrslinen1(address.getAddrslinen1());
			newAddress.setAddrslinen2(address.getAddrslinen2());
			State state = searchState(address.getState().getStateName());
			newAddress.setState(state);
			// newAddress.setState(address.getState());
			session.save(newAddress);
			tx.commit();
			logger.debug("CREATED AN AGGREGATE FUNCTION INTO DATABASE" + newAddress);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newAddress;
	}

	/**
	 * 
	 * @param stateName
	 * @return
	 */
	public State searchState(String stateName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<State> stateList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(State.class);
			crit.add(Restrictions.eq("stateName", stateName));
			stateList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return stateList.get(0);
	}

	/**
	 * 
	 * @param customerType
	 * @return
	 */
	public Long createCustomerType(CustomerType customerType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CustomerType newCustomerType = new CustomerType();
		try {
			tx = session.beginTransaction();
			newCustomerType.setCustType(customerType.getCustType());
			session.save(newCustomerType);
			tx.commit();
			logger.debug("CREATED AN CUSTOMER TYPE INTO DATABASE" + newCustomerType);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newCustomerType.getId();
	}

	/**
	 * 
	 * @param customerType
	 * @return
	 */
	public CustomerType searchCustomerType(String customerType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<CustomerType> customerTypeList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(CustomerType.class);
			crit.add(Restrictions.eq("custType", customerType));
			customerTypeList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customerTypeList.get(0);
	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	public Long createCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Customer newCustomer = new Customer();
		try {
			tx = session.beginTransaction();
			newCustomer.setCustomerName(customer.getCustomerName());
			CustomerType customerType = searchCustomerType(customer.getCustomerType().getCustType());
			newCustomer.setCustomerType(customerType);
			Address newAddress = createAddress(customer.getAddress());
			newCustomer.setAddress(newAddress);
			session.save(newCustomer);
			tx.commit();
			logger.debug("CREATED AN CUSTOMER TYPE INTO DATABASE" + newCustomer);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newCustomer.getId();
	}

	public static Customer searchCustomer(String custName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Customer> custList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Customer.class);
			crit.add(Restrictions.eq("customerName", custName));
			custList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return custList.get(0);
	}
	
	private OfficeLocation searchOffLoc(int officeCode) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<OfficeLocation> offList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(OfficeLocation.class);
			crit.add(Restrictions.eq("officeCode", officeCode));
			offList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return offList.get(0);
	}
	/**
	 * 
	 * @param productType
	 * @return
	 */

	public Long createProductType(ProductType productType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ProductType newProductType = new ProductType();
		try {
			tx = session.beginTransaction();
			newProductType.setProdType(productType.getProdType());
			session.save(newProductType);
			tx.commit();
			logger.debug("CREATED AN PRODUCT TYPE INTO DATABASE" + newProductType);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newProductType.getId();
	}

	/**
	 * 
	 * @param productSubType
	 * @return
	 */
	public ProductSubType createProductSubType(ProductSubType productSubType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ProductSubType newproductSubType = new ProductSubType();
		try {
			tx = session.beginTransaction();
			newproductSubType.setSubType(productSubType.getSubType());
			ProductType productType = searchProductType(productSubType.getProductType().getProdType());
			newproductSubType.setProductType(productType);
			session.save(newproductSubType);
			tx.commit();
			logger.debug("CREATED AN AGGREGATE FUNCTION INTO DATABASE" + newproductSubType);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productSubType;
	}

	/**
	 * 
	 * @param productType
	 * @return
	 */
	public ProductType searchProductType(String productType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ProductType> productTypeList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(ProductType.class);
			crit.add(Restrictions.eq("prodType", productType));
			productTypeList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productTypeList.get(0);
	}

	/**
	 * 
	 * @param productSubType
	 * @return
	 */
	public ProductSubType searchProductSubType(String productSubType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ProductSubType> productSubTypeList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(ProductSubType.class);
			crit.add(Restrictions.eq("subType", productSubType));
			productSubTypeList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productSubTypeList.get(0);
	}
	
	//search product sub type by id
	public ProductSubType searchProductSubTypeById(long productSubTypeId) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ProductSubType> productSubTypeList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(ProductSubType.class);
			crit.add(Restrictions.eq("id", productSubTypeId));
			productSubTypeList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productSubTypeList.get(0);
	}

	/**
	 * Method for creating product
	 * 
	 * @param product
	 * @return
	 */
	public Product createProduct(Product product) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Product newProduct = new Product();
		try {
			tx = session.beginTransaction();
			newProduct.setProductName(product.getProductName());
			newProduct.setDescription(product.getDescription());
			ProductSubType productSubType = searchProductSubType(product.getProductSubType().getSubType());
			newProduct.setProductSubType(productSubType);
			session.save(newProduct);
			tx.commit();
			logger.debug("CREATED AN PRODUCT INTO DATABASE" + newProduct);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newProduct;
	}

	/**
	 * 
	 * @param productType
	 * @return
	 */
	public static Product searchProduct(String product) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Product> productList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Product.class);
			crit.add(Restrictions.eq("productName", product));
			productList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productList.get(0);
	}
	
	
	public static SplitRule searchSplitRule(String splitRule) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<SplitRule> splitRuleList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(SplitRule.class);
			crit.add(Restrictions.eq("splitRuleName", splitRule));
			splitRuleList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return splitRuleList.get(0);
	}

	/**
	 * Method for creating order roster
	 * 
	 * @param orderRoster
	 * @return
	 */
	public static Long createOrderRoster(OrderRoster orderRoster) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		OrderRoster newOrderRoster = new OrderRoster();
		try {
			tx = session.beginTransaction();
			newOrderRoster.setImportDate(orderRoster.getImportDate());
			newOrderRoster.setCountOfOrders(orderRoster.getCountOfOrders());
			newOrderRoster.setStatus(orderRoster.getStatus());
			newOrderRoster.setImportedBy(orderRoster.getImportedBy());
			newOrderRoster.setOrderDetail(orderRoster.getOrderDetail());
			session.save(newOrderRoster);
			tx.commit();
			logger.debug("CREATED AN ORDER ROSTER IN DATABASE" + newOrderRoster);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newOrderRoster.getId();
	}

	/**
	 * Method for getting list of orderRoster
	 * 
	 * @return
	 */
	public List<OrderRoster> listOfOrderRosters() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<OrderRoster> orderRosters = new ArrayList<OrderRoster>();
		try {
			tx = session.beginTransaction();
			orderRosters = session.createQuery("FROM OrderRoster").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return orderRosters;
	}

	public void importOrders(InputStream is) throws ParseException {

		List<OrderRosterXML> importOrderList = parseXML(is);
		
		for (Iterator iterator = importOrderList.iterator(); iterator.hasNext();) {

			OrderRosterXML orderRoster = (OrderRosterXML) iterator.next();
			OrderRoster newOrderRoster = new OrderRoster();
			logger.debug("IMPORT DATE= "+orderRoster.getImportDate());
			
			
			newOrderRoster.setImportDate(orderRoster.getImportDate());
			newOrderRoster.setCountOfOrders(orderRoster.getCountOfOrders());
			newOrderRoster.setStatus(orderRoster.getStatus());
			List<OrderXML> orderList = orderRoster.getOrderXML();
			List<OrderDetail> newOrderList = new ArrayList<OrderDetail>();
			for (Iterator iterator1 = orderList.iterator(); iterator1.hasNext();) {

				OrderXML order = (OrderXML) iterator1.next();

				OrderDetail newOrder = new OrderDetail();
				newOrder.setOrderDate(order.getOrderDate());

				Employee salesRepresentative = searchEmployee(order.getSalesRepresentative());
				newOrder.setSalesRepresentative(salesRepresentative);
				
				if(!order.getManager().equals("null")) {
				Employee manager = searchEmployee(order.getManager());
				newOrder.setManager(manager);
				}else {
					newOrder.setManager(null);
				}
				
				if(!order.getSecondLvlMgr().equals("null")) {
				Employee secLvlMgr = searchEmployee(order.getSecondLvlMgr());
				newOrder.setSecondLevelManager(secLvlMgr);
				}else {
					newOrder.setSecondLevelManager(null);
				}

				Employee administrator = searchEmployee(order.getAdministrator());
				newOrder.setAdministrator(administrator);

				Employee supportEngineer = searchEmployee(order.getSupportEngineer());
				newOrder.setSupportEngineer(supportEngineer);

				Customer customer = searchCustomer(order.getCustomer());
				newOrder.setCustomer(customer);
				
				newOrder.setSaleType(order.getSaleType());
				
				OfficeLocation ofcLoc = searchOffLoc(order.getOfficeLocation().getOfficeCode());
				newOrder.setOfficeLocation(ofcLoc);
				
				logger.debug("OFFICE CODE= "+order.getOfficeLocation().getOfficeCode());

//				newOrder.setOrderTotal(order.getOrderTotal());

				List<OrderLineItemsXML> orderLineItemList = order.getOrderLineItemsXML();
				List<OrderLineItems> newOrderLineItemList = new ArrayList<OrderLineItems>();
				for (Iterator iterator2 = orderLineItemList.iterator(); iterator2.hasNext();) {

					OrderLineItemsXML orderLineItem = (OrderLineItemsXML) iterator2.next();

					OrderLineItems newOrderLineItem = new OrderLineItems();

					Product product = searchProduct(orderLineItem.getProduct());
					newOrderLineItem.setProduct(product);

					newOrderLineItem.setQuantity(orderLineItem.getQuantity());
					newOrderLineItem.setRate(orderLineItem.getRate());
					newOrderLineItem.setDiscountPercentage(orderLineItem.getDiscountPercentage());
					newOrderLineItem.setDutyPercentage(orderLineItem.getDutyPercentage());
					
					List<OrderLineItemsSplit> itemsSplits = new ArrayList<>();
					if(!order.getManager().equals("null")) {
						OrderLineItemsSplit orderLineItemsSplit = new OrderLineItemsSplit();
						SplitRule splitRule = searchSplitRule(orderLineItem.getSplitRule());
						orderLineItemsSplit.setSplitRule(splitRule);
						orderLineItemsSplit.setBeneficiary(searchEmployee(order.getManager()));
						orderLineItemsSplit.setBeneficiaryType("MANAGER");
						
						boolean satisfied = checkSplitRule(splitRule, orderLineItem,order);
						if(satisfied == true) {
							logger.debug("SPLIT PERCENTAGE= "+getSplitPercentage("MANAGER", splitRule));
							int splitPercentage = getSplitPercentage("MANAGER", splitRule);
							assignSplitValues(splitPercentage,orderLineItem, orderLineItemsSplit, splitRule);
						}else {
							orderLineItemsSplit.setSplitQuantity(0);
							orderLineItemsSplit.setSplitSubTotal(0);
						}
						
						itemsSplits.add(orderLineItemsSplit);
						
					}
					if(!order.getSupportEngineer().equals("null")) {
						OrderLineItemsSplit orderLineItemsSplit = new OrderLineItemsSplit();
						SplitRule splitRule = searchSplitRule(orderLineItem.getSplitRule());
						orderLineItemsSplit.setSplitRule(splitRule);
						orderLineItemsSplit.setBeneficiary(searchEmployee(order.getSupportEngineer()));
						orderLineItemsSplit.setBeneficiaryType("SUPPORTING ENGINEER");
						
						boolean satisfied = checkSplitRule(splitRule, orderLineItem, order);
						if(satisfied == true) {
							logger.debug("SPLIT PERCENTAGE= "+getSplitPercentage("SUPPORTING ENGINEER", splitRule));
							int splitPercentage = getSplitPercentage("SUPPORTING ENGINEER", splitRule);
							assignSplitValues(splitPercentage,orderLineItem, orderLineItemsSplit, splitRule);
						}else {
							orderLineItemsSplit.setSplitQuantity(0);
							orderLineItemsSplit.setSplitSubTotal(0);
						}
						
						itemsSplits.add(orderLineItemsSplit);
					}
					if(!order.getSecondLvlMgr().equals("null")) {
						OrderLineItemsSplit orderLineItemsSplit = new OrderLineItemsSplit();
						SplitRule splitRule = searchSplitRule(orderLineItem.getSplitRule());
						orderLineItemsSplit.setSplitRule(splitRule);
						orderLineItemsSplit.setBeneficiary(searchEmployee(order.getSecondLvlMgr()));
						orderLineItemsSplit.setBeneficiaryType("SECOND LEVEL MANAGER");
						
						boolean satisfied = checkSplitRule(splitRule, orderLineItem, order);
						if(satisfied == true) {
							logger.debug("SPLIT PERCENTAGE= "+getSplitPercentage("SECOND LEVEL MANAGER", splitRule));
							int splitPercentage = getSplitPercentage("SECOND LEVEL MANAGER", splitRule);
							assignSplitValues(splitPercentage,orderLineItem, orderLineItemsSplit, splitRule);
						}else {
							orderLineItemsSplit.setSplitQuantity(0);
							orderLineItemsSplit.setSplitSubTotal(0);
						}
				
						itemsSplits.add(orderLineItemsSplit);
					}
					if(!order.getSalesRepresentative().equals("null")) {
						OrderLineItemsSplit orderLineItemsSplit = new OrderLineItemsSplit();
						SplitRule splitRule = searchSplitRule(orderLineItem.getSplitRule());
						orderLineItemsSplit.setSplitRule(splitRule);
						orderLineItemsSplit.setBeneficiary(searchEmployee(order.getSalesRepresentative()));
						orderLineItemsSplit.setBeneficiaryType("SALES REPRESENTATIVE");
						
						boolean satisfied = checkSplitRule(splitRule, orderLineItem, order);
						if(satisfied == true) {
							logger.debug("SPLIT PERCENTAGE= "+getSplitPercentage("SALES REPRESENTATIVE", splitRule));
							int splitPercentage = getSplitPercentage("SALES REPRESENTATIVE", splitRule);
							assignSplitValues(splitPercentage,orderLineItem, orderLineItemsSplit, splitRule);
						}else {
							orderLineItemsSplit.setSplitQuantity(0);
							orderLineItemsSplit.setSplitSubTotal(0);
						}
						
						itemsSplits.add(orderLineItemsSplit);
					}
					if(!order.getAdministrator().equals("null")) {
						OrderLineItemsSplit orderLineItemsSplit = new OrderLineItemsSplit();
						SplitRule splitRule = searchSplitRule(orderLineItem.getSplitRule());
						orderLineItemsSplit.setSplitRule(splitRule);
						orderLineItemsSplit.setBeneficiary(searchEmployee(order.getAdministrator()));
						orderLineItemsSplit.setBeneficiaryType("ADMIN");
						
						boolean satisfied = checkSplitRule(splitRule, orderLineItem, order);
						if(satisfied == true) {
							logger.debug("SPLIT PERCENTAGE= "+getSplitPercentage("ADMIN", splitRule));
							int splitPercentage = getSplitPercentage("ADMIN", splitRule);
							assignSplitValues(splitPercentage,orderLineItem, orderLineItemsSplit, splitRule);
						}else {
							orderLineItemsSplit.setSplitQuantity(0);
							orderLineItemsSplit.setSplitSubTotal(0);
						}
				
						itemsSplits.add(orderLineItemsSplit);
					}
					
					
					newOrderLineItem.setOrderLineItemsSplit(itemsSplits);
					
					
					newOrderLineItem.setSubtotal(orderLineItem.getSubtotal());

					newOrderLineItemList.add(newOrderLineItem);
				}

				newOrder.setOrderLineItems(newOrderLineItemList);
				newOrderList.add(newOrder);
			}

			Employee employee = searchEmployee(orderRoster.getImportedBy());
			newOrderRoster.setImportedBy(employee);
			newOrderRoster.setOrderDetail(newOrderList);
			createOrderRoster(newOrderRoster);
		}

	}

	private boolean checkSplitRule(SplitRule splitRule, OrderLineItemsXML lineItem, OrderXML order) {
		
		Date orderDate =order.getOrderDate();
		Date startDateSplitRule = splitRule.getStartDate();
		Date endDateSplitRule = splitRule.getEndDate();
		if((startDateSplitRule.compareTo(orderDate) * orderDate.compareTo(endDateSplitRule) >= 0) == true) {
			List<SplitQualifyingClause> clauses = splitRule.getSplitQualifyingClause();
			if(clauses != null && !clauses.isEmpty()) {
				for(SplitQualifyingClause clause : clauses) {
					boolean check= checkSplitQualClause(lineItem, order, clause);
					if(check==false) {
						return false;
					}
				}
				
			}else {
				return true;
			}
		}else {
			return false;
		}
	
		return true;
	}

	/**
	 * @param lineItem
	 * @param order
	 * @param clause
	 */
	private boolean checkSplitQualClause(OrderLineItemsXML lineItem, OrderXML order, SplitQualifyingClause clause) {
		String displayName = clause.getFieldList().getDisplayName();
		String condition = clause.getConditionList().getConditionValue();
		boolean notFlag = clause.isNotFlag();
		String value = clause.getValue();
		
		switch(displayName) {
		case "Discount Percentage":
			if(condition.equals("equal")) {
				if(lineItem.getDiscountPercentage() == Integer.parseInt(value) ){
					if(!notFlag) {
						logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is equal to value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is not equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(lineItem.getDiscountPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is less than value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is not less than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(lineItem.getDiscountPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is greater than value= "+value );
						return true;
					}					
				}else {
					logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is not greaater than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(lineItem.getDiscountPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is less than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is not less than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(lineItem.getDiscountPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is greater than equal to value= "+value );
						return true;
					}
				}else {
					logger.debug("Discount Percentage "+lineItem.getDiscountPercentage()+" is not greater than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
					
				}
			}
			break;
		case "Duty Percentage":
			if(condition.equals("equal")) {
				if(lineItem.getDutyPercentage() == Integer.parseInt(value) ){
					if(!notFlag) {
						logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is equal to value= "+value );
						return true;
					}
				}else {
					logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is not equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(lineItem.getDutyPercentage() < Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is less than value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is not less than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(lineItem.getDutyPercentage() > Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is greater than value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is not greater than value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(lineItem.getDutyPercentage() <= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is less than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is not less than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(lineItem.getDutyPercentage() >= Integer.parseInt(value)) {
					if(!notFlag) {
						logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is greater than equal to value= "+value );
						return true;
					}
				}else {

					logger.debug("Duty Percentage "+lineItem.getDutyPercentage()+" is not greater than equal to value= "+value );
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Customer Name":
			String custName =order.getCustomer();
			
			if(condition.equals("equal")) {
				if(custName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("starts with")) {
				if(custName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(custName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Office Location":
			String stateName = order.getOfficeLocation().getAddress().getState().getStateName();
			if(condition.equals("equal")) {
				if(stateName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				value=value.toUpperCase();
				if(stateName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(stateName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Office Name":
			String ofcName = order.getOfficeLocation().getOfficeName();			
			if(condition.equals("equal")) {
				if(ofcName.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				value=value.toUpperCase();
				if(ofcName.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(ofcName.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Product Type":
			
			String prod_type = searchProduct(lineItem.getProduct()).getProductSubType().getProductType().getProdType();
			if(condition.equals("equal")) {
				if(prod_type.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			else if(condition.equals("starts with")) {
				if(prod_type.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			else if(condition.equals("ends with")) {
				if(prod_type.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}		
			
			break;
		case "Order Total":
			if(condition.equals("equal")) {
				if(lineItem.getSubtotal() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(lineItem.getSubtotal() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(lineItem.getSubtotal() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(lineItem.getSubtotal() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(lineItem.getSubtotal() >= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Quantity":
			if(condition.equals("equal")) {
				if(lineItem.getQuantity() == Integer.parseInt(value) ){
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than")) {
				if(lineItem.getQuantity() < Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than")) {
				if(lineItem.getQuantity() > Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("less than equal to")) {
				if(lineItem.getQuantity() <= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
					
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("greater than equal to")) {
				if(lineItem.getQuantity() >= Integer.parseInt(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Product Name":
			String prod_name = lineItem.getProduct();
			if(condition.equals("equal")) {
				if(prod_name.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}else if(condition.equals("starts with")) {
				if(prod_name.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("ends with")) {
				if(prod_name.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			break;
		case "Sale Type":
			String sale_type = order.getSaleType();
			if(condition.equals("equal")) {
				if(sale_type.equalsIgnoreCase(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}
			else if(condition.equals("starts with")) {
				if(sale_type.startsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			else if(condition.equals("ends with")) {
				if(sale_type.endsWith(value)) {
					if(!notFlag) {
						return true;
					}
				}else {
					if(notFlag==true) {
						return true;
					}
				}
			}	
			
			break;
		
			
		}
		return false;
	}

	/**
	 * @param splitPercentage 
	 * @param orderLineItem
	 * @param orderLineItemsSplit
	 * @param splitRule
	 */
	private void assignSplitValues(double splitPercentage, OrderLineItemsXML orderLineItem, OrderLineItemsSplit orderLineItemsSplit,
			SplitRule splitRule) {
		logger.debug("SPLIT PERCENTAGE= "+splitPercentage);
//		double qty = orderLineItem.getQuantity();
//		logger.debug("QTY= "+qty);
//		double split_qty = (splitPercentage/100)*qty;
		logger.debug("SPLIT_QTY= "+splitPercentage);
		orderLineItemsSplit.setSplitQuantity(splitPercentage);
		double subTotal = orderLineItem.getSubtotal();
		logger.debug("SUBTOTAL= "+subTotal);
		double split_subTotal = (splitPercentage/100)*subTotal;
		logger.debug("SPLT_SUB_TOTAL= "+split_subTotal);
		orderLineItemsSplit.setSplitSubTotal(split_subTotal);
	}

	

	private int getSplitPercentage(String benType, SplitRule splitRule) {
		
		List<SplitRuleBeneficiary> beneficiaries = splitRule.getSplitRuleBeneficiary();
		for(SplitRuleBeneficiary beneficiary : beneficiaries) {
			if(beneficiary.getBeneficiaryType().equalsIgnoreCase(benType)) {
				return beneficiary.getSplitPercentage();
			}
		}
		return 0;
	}

	public static List<OrderRosterXML> parseXML(InputStream is) {
		
		List<OrderRosterXML> importOrderList = new ArrayList<OrderRosterXML>();
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Import");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					
					String importDate = node.getAttributes().getNamedItem("importDate").getNodeValue();
					Date date=null;
					if(!importDate.equals("")) {
					System.out.println("importDate :" + importDate);

					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					 date = df.parse(importDate);
				}else {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String importDateCurrent=dateFormat.format(cal.getTime());
					 date=dateFormat.parse(importDateCurrent);
				}
					String importedBy = (elem.getElementsByTagName("importedBy").item(0).getChildNodes().item(0)
							.getNodeValue());
					System.out.println("importedBy :" + importedBy);

					Integer orderCounts = Integer.parseInt(
							elem.getElementsByTagName("orderCounts").item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("orderCounts :" + orderCounts);

					String status = (elem.getElementsByTagName("status").item(0).getChildNodes().item(0)
							.getNodeValue());

					List<OrderXML> orderList = new ArrayList<OrderXML>();
					NodeList nodeList1 = elem.getElementsByTagName("order");
					for (int j = 0; j < nodeList1.getLength(); j++) {
						Node node1 = nodeList1.item(j);

						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element elem1 = (Element) node1;

							String date1 = node1.getAttributes().getNamedItem("orderDate").getNodeValue();
							logger.debug("orderDate :" + date1);
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							//Date orderDate = df.parse(orderDate);
							Date orderDate = df.parse(date1);
							
							
							
							String salesRep = elem1.getElementsByTagName("salesRep").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("salesRep :" + salesRep);
							
							String manager = elem1.getElementsByTagName("manager").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("manager :" + manager);
							
							String secondLvlMgr = elem1.getElementsByTagName("secondLvlMgr").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("secondLvlMgr :" + secondLvlMgr);

							String admin = elem1.getElementsByTagName("admin").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("admin :" + admin);

							String supportEngineer = elem1.getElementsByTagName("supportEngineer").item(0)
									.getChildNodes().item(0).getNodeValue();
							logger.debug("supportEngineer :" + supportEngineer);

							String customer = elem1.getElementsByTagName("customer").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("customer :" + customer);
							
							String saleType = elem1.getElementsByTagName("saleType").item(0).getChildNodes().item(0)
									.getNodeValue();
							logger.debug("saleType :" + saleType);


//							long orderTotal = Integer.parseInt(elem1.getElementsByTagName("orderTotal").item(0)
//									.getChildNodes().item(0).getNodeValue());
//							System.out.println("orderTotal :" + orderTotal);
							
							String officeName = (elem1.getElementsByTagName("OfficeName")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
							logger.debug("OfficeName :" + officeName);
			                   
			                   int officeCode = Integer.parseInt(elem1.getElementsByTagName("OfficeCode")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
			                   logger.debug("OfficeCode :" + officeCode);
			                   
			                   String add_line_1 = (elem1.getElementsByTagName("add_line_1")
		               		 		.item(0).getChildNodes().item(0).getNodeValue());
			                   logger.debug("add_line_1 :" + add_line_1);
			                   
			                   String add_line_2 = (elem1.getElementsByTagName("add_line_2")
		               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
			                   logger.debug("add_line_2 :" + add_line_2);
			                   
			                   String state = (elem1.getElementsByTagName("state")
			               		 		.item(0).getChildNodes().item(0).getNodeValue()); 
			                   logger.debug("state :" + state);

							List<OrderLineItemsXML> orderLineItemList = new ArrayList<OrderLineItemsXML>();
							NodeList nodeList2 = elem1.getElementsByTagName("orderLineItem");
							for (int k = 0; k < nodeList2.getLength(); k++) {
								Node node2 = nodeList2.item(k);

								if (node2.getNodeType() == Node.ELEMENT_NODE) {
									Element elem2 = (Element) node2;

									String product = node2.getAttributes().getNamedItem("product").getNodeValue();
									logger.debug("product :" + product);

									float quantity = Integer.parseInt(elem2.getElementsByTagName("quantity").item(0)
											.getChildNodes().item(0).getNodeValue());
									logger.debug("quantity :" + quantity);

									float rate = Integer.parseInt(elem2.getElementsByTagName("rate").item(0)
											.getChildNodes().item(0).getNodeValue());
									logger.debug("rate :" + rate);

									float discountPercentage = Integer
											.parseInt(elem2.getElementsByTagName("discountPercentage").item(0)
													.getChildNodes().item(0).getNodeValue());
									logger.debug("discountPercentage :" + discountPercentage);

									float dutyPercentage = Integer.parseInt(elem2.getElementsByTagName("dutyPercentage")
											.item(0).getChildNodes().item(0).getNodeValue());
									logger.debug("dutyPercentage :" + dutyPercentage);
									
									String splitRule=elem1.getElementsByTagName("splitRule").item(0).getChildNodes().item(0)
											.getNodeValue();
									logger.debug("splitRule :" + splitRule);

//									long subtotal = Integer.parseInt(elem2.getElementsByTagName("subtotal").item(0)
//											.getChildNodes().item(0).getNodeValue());
//									System.out.println("subtotal :" + subtotal);
									
									double subTotal = (1+(float)(dutyPercentage/100))*((1-(float)(discountPercentage/100))*(quantity*rate));

									OrderLineItemsXML orderLineItem = new OrderLineItemsXML();
									orderLineItem.setProduct(product);
									orderLineItem.setQuantity((int) quantity);
									orderLineItem.setRate((int) rate);
									orderLineItem.setDiscountPercentage((int) discountPercentage);
									orderLineItem.setDutyPercentage((int) dutyPercentage);
									orderLineItem.setSplitRule(splitRule);
									orderLineItem.setSubtotal(subTotal);
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
							order.setManager(manager);
							order.setSecondLvlMgr(secondLvlMgr);
							order.setAdministrator(admin);
							order.setSupportEngineer(supportEngineer);
							order.setCustomer(customer);
							order.setSaleType(saleType);
//							order.setOrderTotal(orderTotal);
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

	public static Employee searchEmployee(String empName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Employee> empList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Employee.class);
			crit.add(Restrictions.eq("employeeName", empName));
			empList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return empList.get(0);
	}

	/**
	 * Method for getting orderRoster details by import ID
	 * 
	 * @param importID
	 * @return
	 */
	public OrderRoster getOrderRoster(long importID) {

		OrderRoster newOrderRoster = new OrderRoster();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newOrderRoster = (OrderRoster) session.get(OrderRoster.class, importID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newOrderRoster;
	}

	public OrderDetail getOrderDetail(long orderID) {

		OrderDetail newOrderDetail = new OrderDetail();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newOrderDetail = (OrderDetail) session.get(OrderDetail.class, orderID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newOrderDetail;
	}
	
	//get order detail from order line item id
	public OrderDetail getOrderDetailFromLineItem(long lineItemId) {
		logger.debug("---FINDING ORDER DETAIL---");
		OrderDetail ordDetail=null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			List orderDetailList = session.createQuery("FROM OrderDetail").list();
			for (Iterator iterator = orderDetailList.iterator(); iterator.hasNext();) {
				
				OrderDetail orderDetail = (OrderDetail) iterator.next();
				if(orderDetail.getOrderLineItems() != null){
					for(OrderLineItems items : orderDetail.getOrderLineItems()) {
						if(items.getId() == lineItemId) {
							ordDetail = orderDetail;
							break;
						}
					}
				}  
				
			}
			
		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		
		
		return ordDetail;
		
	}
	
	public OrderLineItems getOrderLineItem(long lineItemID) {

		OrderLineItems newOrderLineItem = new OrderLineItems();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			newOrderLineItem = (OrderLineItems) session.get(OrderLineItems.class, lineItemID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newOrderLineItem;
	}
	
	

	public List<Product> listOfProductSubTypes(){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List prod_sub_type_list = session.createQuery("FROM ProductSubType").list();
		
		return prod_sub_type_list;
		
	}
	
	public void deleteOrderRoster(long rosterId) {
		Session session = sessionFactory.openSession();
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			OrderRoster ors = (OrderRoster)session.get(OrderRoster.class, rosterId);
			List<OrderDetail> detailList = ors.getOrderDetail();
			for(OrderDetail detail : detailList) {
				List<OrderLineItems> items = detail.getOrderLineItems();
				for(OrderLineItems lineItems: items) {
					List<OrderLineItemsSplit> itemsSplits = lineItems.getOrderLineItemsSplit();
					for(OrderLineItemsSplit itemsSplit: itemsSplits) {
						String hql = "delete from OrderLineItemsSplit where id= :id";
						session.createQuery(hql).setLong("id", itemsSplit.getId()).executeUpdate();
					}
					String hql = "delete from OrderLineItems where id= :id";
					session.createQuery(hql).setLong("id", lineItems.getId()).executeUpdate();
				}
				String hql = "delete from OrderDetail where id= :id";
				session.createQuery(hql).setLong("id", detail.getId()).executeUpdate();
			}
			
			String hql = "delete from OrderRoster where id= :id";
			session.createQuery(hql).setLong("id", ors.getId()).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// list of all order line split data
		public List<OrderLineItemsSplit> listLineSplits() {
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			tx = session.beginTransaction();
			List lineSplits = session.createQuery("FROM OrderLineItemsSplit").list();			
			return lineSplits;
		}
		
	// find list of all order line split data for an employee
		public List<OrderLineItemsSplit> getLineItemSplitListForEmp(long empId){
			List<OrderLineItemsSplit> itemsSplitsForEmp = new ArrayList<>();
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				List lineSplits = session.createQuery("FROM OrderLineItemsSplit").list();
				for(Iterator iterator = lineSplits.iterator(); iterator.hasNext();) {
					OrderLineItemsSplit itemsSplit = (OrderLineItemsSplit)iterator.next();
					if(itemsSplit.getBeneficiary().getId() == empId) {
						itemsSplitsForEmp.add(itemsSplit);
					}
				}
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			if(itemsSplitsForEmp.isEmpty()) {
				return null;
			}else {
				return itemsSplitsForEmp;
			}
			
		}
}
