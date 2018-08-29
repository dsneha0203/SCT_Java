package com.simpsoft.salesCommission.app.UImodel;

public class ImportId {
	
	private int importId;
	private int orderId;

	public int getImportId() {
		return importId;
	}

	public void setImportId(int importId) {
		this.importId = importId;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public ImportId(){
		
	}

public ImportId(int importId, int orderId){
		this.importId=importId;
		this.orderId=orderId;
	}
	
}
