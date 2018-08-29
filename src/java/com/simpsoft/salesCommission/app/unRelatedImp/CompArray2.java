package com.simpsoft.salesCommission.app.unRelatedImp;

import java.util.ArrayList;

public class CompArray2 {
	public ArrayList<String> addRule(){
		ArrayList<String> b1= new ArrayList<String>();
		
		//for(int i=1;i<=5;i++)
		//{		
		b1.add("select");
		b1.add("Weekly");
		b1.add("Fortnightly");
		b1.add("Monthly");
		b1.add("Quaterly");
		b1.add("Annualy");
		
		//}
		//for(int j=0;j<a1.size();j++)
		//{
		//	System.out.println(a1.get(j));
		//}
	      return b1;
	}

	public static void main(String[] args) {
	CompArray2 obj = new CompArray2();
	//obj.addRule();
	System.out.println(""+obj.addRule());
	}
}



