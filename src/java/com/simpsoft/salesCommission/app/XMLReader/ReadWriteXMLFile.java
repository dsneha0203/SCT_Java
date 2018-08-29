package com.simpsoft.salesCommission.app.XMLReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
public class ReadWriteXMLFile {
	
	public static void main( String[] args )
	   {	
	 /*   try{
	    	
	    	 String content = new String(Files.readAllBytes(Paths.get("WebContent/WEB-INF/resources/XMLFile/orderRoster.xml")));
		     System.out.println(content);
	        //Specify the file name and path here
	    	File file =new File("WebContent/WEB-INF/resources/XMLFile/file.xml");

	    	/* This logic is to create the file if the
	    	 * file is not already present
	    	 */
	     /*	if(!file.exists()){
	    	   file.createNewFile();
	    	} */

	    	//Here true is to append the content to file
	/*   	FileWriter fw = new FileWriter(file,true);
	    	//BufferedWriter writer give better performance
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	bw.write(content);
	    	//Closing BufferedWriter Stream
	    	bw.close();

		System.out.println("Data successfully appended at the end of file");

	      }catch(IOException ioe){
	         System.out.println("Exception occurred:");
	    	 ioe.printStackTrace();
	       }
	      
	    clearFile(); */
		
		try{  
			FileInputStream fin=new FileInputStream("WebContent/WEB-INF/resources/XMLFile/orderRoster.xml");  
			FileOutputStream fout=new FileOutputStream("WebContent/WEB-INF/resources/XMLFile/abc.txt");  
			int i=0;  
			while((i=fin.read())!=-1){  
			fout.write((byte)i);  
			}  
			fin.close();  
			
		     System.out.println("success...");  
		    }catch(Exception e){System.out.println(e);}  
		clearFile();
		  
	   } 
	//Method to delete the content of a file
		public static void clearFile(){ 
			PrintWriter pw = null;
			try {
				pw = new PrintWriter("WebContent/WEB-INF/resources/XMLFile/abc.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.close(); 
	   }
		
			
	
}