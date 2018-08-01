package RESTAut;

import java.io.FileReader;
import java.util.Properties;


/**
* Main Class
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-01-18 
*/


public class Rest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try (FileReader reader = new FileReader("api.properties")){
			
			
			
			Properties properties = new Properties();
			properties.load(reader);
			
		String met = properties.getProperty("method");
	    	    
	    if (met.equals("PostStatic"))
	    {
				
	    	APIAut.apiautstatic();
	   
	    }
	    
	    else if (met.equals("PostDynamic"))
	    {
	    	ApiAutpost.apiautpost();
		    
	    }
	    
	    else if (met.equals("Get"))
	    {
		    APIAutget.apiautget();
		    
	    }
	    
	    else if (met.equals("Put"))
	    {
		    ApiAutput.apiautput();
		    
	    }
	    
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
