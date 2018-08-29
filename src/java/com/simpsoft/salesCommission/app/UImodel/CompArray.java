package com.simpsoft.salesCommission.app.UImodel;

import java.awt.List;
import java.util.ArrayList;

import com.simpsoft.salesCommission.app.unRelatedImp.Rules;
//import java.util.List;

/*public class CompArray {
	public ArrayList<String> addRule(){
	 ArrayList<String[]> value = new ArrayList<String[]>(2);
	 String[] element1 = new String[] { "101", "102", "103", "104"};
	 String[] element2 = new String[] { "hello1", "hello2", "hello3", "hello4" };
	 
	 value.add(element1);
	 value.add(element2);
	 
     
     ArrayList<String> temp = new ArrayList<String>();
     for(int j=0;j<value.size(); j++) {
         for (int i = 0; i < value.get(0).length; i++) {
             temp.add(value.get(0)[i]);
             temp.add(value.get(1)[i]);
         }
     }
	return temp;
	}*/
	
	/*public ArrayList<Rules> addRule(){
		ArrayList<Rules> a1= new ArrayList<Rules>();
		
		Rules rul = new Rules();
			rul.setId("1001");
			rul.setDetails("khagfkj");
			rul.setParameter("lsrkjglkrs");
			rul.setValue("lskdjfk");
			
		a1.add(rul);
		
		return a1;
		
		
	}
	public ArrayList<Rules> getRules(){
	    ArrayList<Rules> a1= new ArrayList<Rules>();
	        Rules rul = new Rules();
	        rul.setId("1001");
	        rul.setDetails("khagfkj");
	        rul.setParameter("lsrkjglkrs");
	        rul.setValue("lskdjfk");

	    a1.add(rul);

	    return a1;
	}*/

public class CompArray {
	   public ArrayList<Rules> a1= new ArrayList<Rules>();

	public CompArray (){
	        Rules rul = new Rules();
	        
	        rul.setId("1001");
	        rul.setDetails("Mascot Rober");
	        rul.setParameter("Parameter1");
	        rul.setValue("007");
	        //rul.setFrequency("Weekly");
	        //rul.setOverwrite("Employee Sales Target");
	    a1.add(rul);
	 }
	public ArrayList<Rules> getRules(){
	    return a1;
	}
	
	public static void main(String[] args) {
	CompArray obj = new CompArray();
	}
}
