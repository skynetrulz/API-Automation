package RESTAut;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
* Helper Class Put Call
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/


public class Put {
	
	static String[] restclient(String urll,String payload)
	{
		File jarPath=new File(Rest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	     String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	
	try (FileReader reader = new FileReader(propertiesPath +"/api.properties")){
		//try (FileReader reader = new FileReader("api.properties")){
			
			Properties properties = new Properties();
			properties.load(reader);
			
			String h2k=	properties.getProperty("header2key");
			String h2v=	properties.getProperty("header2value");
			String h3k=	properties.getProperty("header3key");
			String h3v=	properties.getProperty("header3value");
			String h4k=	properties.getProperty("header4key");
			String h4v=	properties.getProperty("header4value");
			String h5k=	properties.getProperty("header5key");
			String h5v=	properties.getProperty("header5value");
			
		Client client = ClientBuilder.newClient();
		
		try {
		
		 Response resp = client.target(urll)  
			  .request()
			  .header(h2k, h2v)
			  .header(h3k, h3v)
			  .header(h4k, h4v)
			  .header(h5k, h5v)
			  .put(Entity.json(payload));
		 
		int status= resp.getStatus();
		
		System.out.println("THE Status is " + status);
		
		
		if(status==200){  
	       String stat = "success";
	        
String res = (resp.readEntity(String.class)).toString();
		
return new String[] { stat, res};
		}
		else
		{
		     String stat = "fail";
		        
		     String res = (resp.readEntity(String.class)).toString();
		     		
		     return new String[] { stat, res};
		}
		
	}catch (Exception e) {
    	
    	return new String[] {"failed"};
	}
		}catch (Exception e){
		e.printStackTrace();
	}
return null;
}
}
