package com.simpsoft.salesCommission.app.UImodel;

import java.util.ArrayList;
//import java.util.List;

public class Array {
	
	public ArrayList<String> addRule()
	{
		ArrayList<String> RuleList= new ArrayList<String>();
		for(int i=1;i<=20;i++)
		{		
		RuleList.add(""+i);
		}
		for(int j=0;j<RuleList.size();j++)
		{
			System.out.println(RuleList.get(j));
		}
		return RuleList;
	}
	
	public static void main(String[] args) {
		Array obj = new Array();
		//obj.addRule();
		
	}

}
