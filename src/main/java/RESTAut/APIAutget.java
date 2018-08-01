package RESTAut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* This method uses Jersey to make GET call
* and saves the response based on values 
* read from api.properties file.
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/

public class APIAutget {

	public static void apiautget() {
	
		//File jarPath=new File(Rest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	  //   String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	
	//try (FileReader reader = new FileReader(propertiesPath +"/api.properties")){
	try (FileReader reader = new FileReader("api.properties")){
		
		Properties properties = new Properties();
		properties.load(reader);

	String path = properties.getProperty("path");
   // System.out.println("Path from Prop" +path);
			
		path = path.replace("\\", "/");
		
		String Outpath = path +"Out\\";
		Outpath = Outpath.replace("\\", "/");
		
		
				
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		
		        String fpath = path + listOfFiles[i].getName(); //Filename with Path
		        System.out.println("\n");	
		        System.out.println("The File from which URL's are getting fetched - " +fpath);
		        System.out.println("The Test results will be saved in - " +Outpath);
		        try {       	
		        	
		        	FileInputStream fstream = new FileInputStream(fpath);
		        	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		        	String urll;
		        	int linenum = 0;
		        	//Read File Line By Line
		        	while ((urll = br.readLine()) != null)   {
		        	    linenum++ ;
		        							
						String bool = UrlValid.isValidURL(urll); //URL Validator
                         if (bool=="not valid")
						 {
                        	 System.out.println("\n");	
							 System.out.println("The URL - " + urll + " in line number " +linenum +" is " + bool ); 
						 }
						 else {
						
							 
						 String[] questionMarkTokens = urll.split("\\?");
						 String beforeQuestionMark = questionMarkTokens[0];
						 String[] slashTokens = beforeQuestionMark.split("/");
						 String rest1 = slashTokens[slashTokens.length - 1];
						 String rest2 = slashTokens[slashTokens.length - 2];
						
						 System.out.println("\n");	
						 System.out.println("THE URL - " + urll);
						 String result[] = Get.restclient(urll);
						//System.out.println(result[0]);	
							
							if (result[0].indexOf("fail") != -1) // will match both failed and fail
							
							{
								//System.out.println("\n");	
								System.out.println("The URL in line number " +linenum  + " is not found/reachable");
								
								 File f2 = new File(Outpath + "Failure.csv"); //For Writing Into Failure File
								 f2.getParentFile().mkdirs();
								 
								 FileWriter out2 = new FileWriter(f2,true);
								 
								 out2.write(urll);
								 out2.append(System.lineSeparator());
								 out2.close();  
							}
											
							else if(result[0]=="success"){  
				       // System.out.println("Response Success"); 
				        
		 String res = result[1];
		 
		  ObjectMapper mapper = new ObjectMapper();
	      Object obj = mapper.readValue(res, Object.class);
	    	 
		 String res1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj); // Beautified JSON	
				
						 File f = new File(Outpath + "Response_" +rest2+"_"+rest1+".txt"); //For Writing the response to File
						 f.getParentFile().mkdirs();
						 
						 PrintWriter out = new PrintWriter(f);
						 out.print(res1);
						 out.close(); 
						 
						 File f2 = new File(Outpath + "Success.csv"); //For Writing Into Success File
						 f2.getParentFile().mkdirs();
						 
						 FileWriter out2 = new FileWriter(f2,true);
						 
						 out2.write(urll);
						 out2.append(System.lineSeparator());
						 out2.close(); 
					        
					    }else{  
					       System.out.println("Response Failed");  
					        
					    	String res = result[1];
					    	
					    	  ObjectMapper mapper = new ObjectMapper();
						      Object obj = mapper.readValue(res, Object.class);
						    	 
							 String res1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj); // Beautified JSON
						 
						// System.out.println(res);
						 File f = new File(Outpath + "Response_" +rest2+"_"+rest1+".txt");
						 f.getParentFile().mkdirs();
						 
						 PrintWriter out = new PrintWriter(f);
						 out.print(res1);
						 out.close(); 
						 
						 File f2 = new File(Outpath + "Failure.csv"); //For Writing Into Failure File
						 f2.getParentFile().mkdirs();
						 
						 FileWriter out2 = new FileWriter(f2,true);
						 
						 out2.write(urll);
						 out2.append(System.lineSeparator());
						 out2.close();    			        
					    }
							 }
		        	}

		        	//Close the input stream
		        	br.close();

		        }
		        catch (IOException e) {
					e.printStackTrace();
				}   
		            
		    }
	} catch (Exception e){
		e.printStackTrace();
	}
		        
	}
	
}
