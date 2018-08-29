package com.simpsoft.salesCommission.dummyData;

import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.model.Product;

public class dummyProduct {
	public static void main(String[] args) {
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("/applicationContext.xml");
		 OrderAPI orderAPI = (OrderAPI) context.getBean("orderApi");
		 int min=1;
		 int prod_subtype_list_size = orderAPI.listOfProductSubTypes().size();
		 Random random = new Random();
		 int num = Integer.parseInt(args[0]);
		 for(int i=1; i<= num; i++) {
			 Product product = new Product();
			 product.setProductName("prod_"+String.valueOf(i));
			 
			 //generate a random product sub type id
			 long prodSubTypeId = random.nextInt(prod_subtype_list_size-min) + min;
			product.setProductSubType(orderAPI.searchProductSubTypeById(prodSubTypeId));
			orderAPI.createProduct(product); 
		 }
		 
	}
}
