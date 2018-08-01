package RESTAut;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
* Helper Class Post Call
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/


public class PostDynamic {
	
	static String[] restclient(String urll,String payload, String fpath)
	{
	//	File jarPath=new File(Rest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	  //   String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	
	//try (FileReader reader = new FileReader(propertiesPath +"/api.properties")){
		try (FileReader reader = new FileReader(fpath)){
			
			Properties properties = new Properties();
			properties.load(reader);
			
			String h2k=	properties.getProperty("header1key");
			String h2v=	properties.getProperty("header1value");
			String h3k=	properties.getProperty("header2key");
			String h3v=	properties.getProperty("header2value");
			String h4k=	properties.getProperty("header3key");
			String h4v=	properties.getProperty("header3value");
			String h5k=	properties.getProperty("header4key");
			String h5v=	properties.getProperty("header4value");
			
		Client client = ClientBuilder.newClient();
		
		try {
		
		 Response resp = client.target(urll)  
			  .request()
			  .header(h2k, h2v)
			  .header(h3k, h3v)
			  .header(h4k, h4v)
			  .header(h5k, h5v)
			  .post(Entity.json(payload));
		 
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
