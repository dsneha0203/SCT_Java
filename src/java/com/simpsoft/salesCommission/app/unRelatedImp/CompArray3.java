package com.simpsoft.salesCommission.app.unRelatedImp;

import java.util.ArrayList;

public class CompArray3 {
	public ArrayList<String> addRule(){
		ArrayList<String> c1= new ArrayList<String>();
		
		//for(int i=1;i<=5;i++)
		//{	
		c1.add("select");
		c1.add("Employee Sales target");
		c1.add("Employee order count target");
		c1.add("Employee product count target");
		
		
		//}
		//for(int j=0;j<a1.size();j++)
		//{
		//	System.out.println(a1.get(j));
		//}
	      return c1;
	}

	public static void main(String[] args) {
	CompArray2 obj = new CompArray2();
	//obj.addRule();
	System.out.println(""+obj.addRule());
	}
}
