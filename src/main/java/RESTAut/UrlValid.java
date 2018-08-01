package RESTAut;

import java.net.URL;

/**
* Helper Class
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-02-06 
*/


public class UrlValid {

	public static String isValidURL(String urlString)
	{
	    try
	    {
	        URL url = new URL(urlString);
	        url.toURI();
	        return "valid";
	    } catch (Exception exception)
	    {
	        return "not valid";
	    }
	}

}
