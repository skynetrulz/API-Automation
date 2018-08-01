package RESTAut;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.TreeMap;


import RESTAut.Rest;

/**
* This program implements an application that uses Jersey 
* to make PUT call (when REST URL keep changing) and saves 
* the response based on values read from api.properties file.
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/

public class ApiAutput {

	public static void apiautput(){
		
	//	File jarPath=new File(Rest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	    // String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	
//	try (FileReader reader = new FileReader(propertiesPath +"/api.properties")){
	try (FileReader reader = new FileReader("api.properties")){
		
		Properties properties = new Properties();
		properties.load(reader);
		
	String path = properties.getProperty("path");
   
	    System.out.println("\n");	
		path = path.replace("\\", "/");
		
		 System.out.println("Path from which the request payloads are fetched - " +path);
		
		String Outpath = path +"Out\\";
		Outpath = Outpath.replace("\\", "/");
		
		System.out.println("The Test results will be saved in - " +Outpath);
		
						
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		
		    	String fname = listOfFiles[i].getName(); //FileName
		        String fpath = path + listOfFiles[i].getName(); //Filename with Path
		        //System.out.println(fpath);
		        try {
				
				 TreeMap<String, String> map = ReadFile.readFile(fpath); //ReadFile
	             //System.out.println("THE HASH MAP IS" +map);
				 String tcid =   map.get("TestCaseID"); 
				 String tcname =   map.get("TestCaseName"); 
				 System.out.println("\n");
	             System.out.println("THE TC Name is " + tcname);
	             System.out.println("THE TC Id is " + tcid);
	            // System.out.println("THE HASH MAP IS" +map);
			     String payload =   map.get("Payload");        
			    // System.out.println("THE Payload is "+payload);
			
			     String urll = map.get("URL"); 
			
                 String bool = UrlValid.isValidURL(urll); //URL Validator
				 if (bool=="not valid")
				 {
					 System.out.println("The URL in file " + fpath + " is "+ bool ); 
				 }
				 else {
									 
					 String result[] = Put.restclient(urll, payload);
						
						if (result[0]=="failed")
						{
							System.out.println("The URL in file " + fpath + " is not up/not reachable");
						}
										
						else if(result[0]=="success"){  
			       // System.out.println("Response Success"); 
			        
	 String res = result[1];
	 							 
				// System.out.println(res);
				 File f = new File(Outpath + "Response_" +tcname+".txt"); //For Writing the response to File
				 f.getParentFile().mkdirs();
				 
				 PrintWriter out = new PrintWriter(f);
				 out.print(res);
				 out.close(); 
				 
				 File f2 = new File(Outpath + "Success.csv"); //For Writing Into Success File
				 f2.getParentFile().mkdirs();
				 
				 FileWriter out2 = new FileWriter(f2,true);
				 
				 out2.write(tcname);
				 out2.append(System.lineSeparator());
				 out2.close(); 
			        
			    }else{  
			        System.out.println("Response Failed");  
			        
	 String res = result[1];
				 
				// System.out.println(res);
				 File f = new File(Outpath + "Response_" +tcname+".txt");
				 f.getParentFile().mkdirs();
				 
				 PrintWriter out = new PrintWriter(f);
				 out.print(res);
				 out.close(); 
				 
				 File f2 = new File(Outpath + "Failure.csv"); //For Writing Into Failure File
				 f2.getParentFile().mkdirs();
				 
				 FileWriter out2 = new FileWriter(f2,true);
				 
				 out2.write(tcname);
				 out2.append(System.lineSeparator());
				 out2.close(); 
			        			        
			    }  
				 }
		        } catch (IOException e) {
					e.printStackTrace();
				}   
		            
		    }
	} catch (Exception e){
		e.printStackTrace();
	}
		        
	}
	
}

