package RESTAut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import RESTAut.Rest;

/**
* This program implements an application that uses Jersey 
* to make POST call (when REST URL remains the same) and saves 
* the response based on values read from api.properties file.
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/

public class APIAut {

	public static void apiautstatic(){
		
		File jarPath=new File(Rest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	
	try (FileReader reader = new FileReader(propertiesPath +"/api.properties")){
		//try (FileReader reader = new FileReader("api.properties")){
		
		Properties properties = new Properties();
		properties.load(reader);
		
	String path = properties.getProperty("path");
	String url=	properties.getProperty("url");
   
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
		    	
		    	String b="Out";
	    	 	if (!fname.equals(b))
	    	{
		    	
		        String fpath = path + listOfFiles[i].getName(); //Filename with Path
		      //  System.out.println(fpath);
		        try {
				
					 TreeMap<String, String> map = ReadFile.readFile(fpath); //ReadFile
		             //System.out.println("THE HASH MAP IS" +map);
					 String tcid =   map.get("tcid"); 
					 String tcname =   map.get("tcname"); 
					 System.out.println("\n");
		             System.out.println("THE TC Name is " + tcname);
		             System.out.println("THE TC Id is " + tcid);
		            // System.out.println("THE HASH MAP IS" +map);
				     String payload =   map.get("Payload");     
				     
				     
				     
				     FileReader fileReader = new FileReader(fpath);
						BufferedReader bufferedReader = new BufferedReader(fileReader);
						StringBuffer stringBuffer = new StringBuffer();
						String line;
						//int linenumber = 0;
						while ((line = bufferedReader.readLine()) != null ) {
						//	linenumber++;
							//System.out.println("THE LINE is "+linenumber);
							
						
							stringBuffer.append(line);
							stringBuffer.append("\n");
						
							
						}
						fileReader.close();
					//	System.out.println("Contents of file:");
					String pay =stringBuffer.toString();
					
					pay = pay.split("payload=")[1];
				     
				     
					//System.out.println("THE New Payload is "+pay);
				     
				     
				  //  System.out.println("THE Payload is "+payload);
				
				     String urll = map.get("url"); 
				
	                 String bool = UrlValid.isValidURL(url); //URL Validator
					 if (bool=="not valid")
					 {
						 System.out.println("The URL in file " + fpath + " is "+ bool ); 
					 }
					 else {
										 
						 String result[] = Post.restclient(url, pay);
							
							if (result[0]=="failed")
							{
								System.out.println("The URL in file " + fpath + " is not up/not reachable");
							}
											
							else if(result[0]=="success"){  
				       // System.out.println("Response Success"); 
				        
		 String res = result[1];
		 
		  ObjectMapper mapper = new ObjectMapper();
	      Object obj = mapper.readValue(res, Object.class);
	    	 
		 String res1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj); // Beautified JSON	
		 
		 
		 							 
					// System.out.println(res);
					 File f = new File(Outpath + "Response_" +tcname+".txt"); //For Writing the response to File
					 f.getParentFile().mkdirs();
					 
					 PrintWriter out = new PrintWriter(f);
					 out.print(res1);
					 out.close(); 
					 
					 File f2 = new File(Outpath + "Success.csv"); //For Writing Into Success File
					 f2.getParentFile().mkdirs();
					 
					 FileWriter out2 = new FileWriter(f2,true);
					 
					 out2.write(tcname);
					
					// out2.write(tcid);
					 out2.append(System.lineSeparator());
					 out2.close(); 
				        
				    }else{  
				        System.out.println("Response Failed");  
				        
		 String res = result[1];
		 
		  ObjectMapper mapper = new ObjectMapper();
	      Object obj = mapper.readValue(res, Object.class);
	    	 
		 String res1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj); // Beautified JSON	
		 
					 
					// System.out.println(res);
					 File f = new File(Outpath + "Response_" +tcname+".txt");
					 f.getParentFile().mkdirs();
					 
					 PrintWriter out = new PrintWriter(f);
					 out.print(res1);
					 out.close(); 
					 
					 File f2 = new File(Outpath + "Failure.csv"); //For Writing Into Failure File
					 f2.getParentFile().mkdirs();
					 
					 FileWriter out2 = new FileWriter(f2,true);
					 
					 out2.write(tcname);
					// out2.write("");
					// out2.write(tcid);
					 out2.append(System.lineSeparator());
					 out2.close(); 
				        			        
				    }  
					 }
			        } catch (IOException e) {
						e.printStackTrace();
					}   
	    	}     
			    }
		} catch (Exception e){
			e.printStackTrace();
		}
			        
		}
		
	}