package RESTAut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

/**
* Helper Class
*
* @author  Maheswar Nandakumar
* @version 1.0
* @since   2018-02-06 
*/


public class ReadFile {
	
	public static TreeMap<String, String> readFile(String infile) throws IOException {
	    final int lhs = 0;
	    final int rhs = 1;

	    TreeMap<String, String> map = new TreeMap<String, String>();
	  BufferedReader  bfr = new BufferedReader(new FileReader(new File(infile)));

	    String line;
	    while ((line = bfr.readLine()) != null) {
	        if (!line.startsWith("#") && !line.isEmpty() && line.contains("=")) {
	            String[] pair = line.trim().split("=");
	            map.put(pair[lhs].trim(), pair[rhs].trim());
	        }
	      
	    }

	    bfr.close();
	 
	    return(map);
	}

}
