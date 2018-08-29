package com.simpsoft.salesCommission.app.dataloader;

import java.util.Date;
import java.util.List;

public class OrderRosterXML {

	private long id;
	
	private Date importDate;

	private int countOfOrders;
		
	private String importedBy;
	
	private String status;
	
	private List<OrderXML> orderXML;
	
	public OrderRosterXML() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the importDate
	 */
	public Date getImportDate() {
		return importDate;
	}

	/**
	 * @param importDate the importDate to set
	 */
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	/**
	 * @return the countOfOrders
	 */
	public int getCountOfOrders() {
		return countOfOrders;
	}

	/**
	 * @param countOfOrders the countOfOrders to set
	 */
	public void setCountOfOrders(int countOfOrders) {
		this.countOfOrders = countOfOrders;
	}

	/**
	 * @return the importedBy
	 */
	public String getImportedBy() {
		return importedBy;
	}

	/**
	 * @param importedBy the importedBy to set
	 */
	public void setImportedBy(String importedBy) {
		this.importedBy = importedBy;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the orderXML
	 */
	public List<OrderXML> getOrderXML() {
		return orderXML;
	}

	/**
	 * @param orderXML the orderXML to set
	 */
	public void setOrderXML(List<OrderXML> orderXML) {
		this.orderXML = orderXML;
	}
}
