package org.opengis.cite.sensorml20.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class UrlValidate {
	public static Boolean ValidateHttpUrl(String urlStr)
	{
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();  
            if(code != 200)
            {
            	return false;
            }
        }
        catch(Exception e)
        {
        	return false;
        }
        
        return true;
	}
}
