package com.example.yahoorss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;



public class Connect {
	
	
	/*rrayList <Front_v>  front_view (String url) {
		 
    	 String results="failed";
		 
		 String id= "" , name ="" ; 
	     HttpGet request = new HttpGet(url);
	    ArrayList <Front_view> list = new ArrayList () ;
	    
	    Front_view obj ; 
	     
	     
	 try
    {
        DefaultHttpClient client = new DefaultHttpClient();
	     HttpResponse response=client.execute(request);
        HttpEntity entity=response.getEntity();
        InputStream is=entity.getContent();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        results=reader.readLine();   
        
        JSONArray array = new JSONArray(results);
        for (int i = 0; i < array.length(); i++) {
       	 
       	 obj = new Front_view () ; 
            JSONObject row = array.getJSONObject(i);
            obj.id = row.getString("id");
            obj.language = row.getString("language");
            obj.body = row.getString("body") ; 
            obj.head = row.getString("head") ; 
            obj.priority = row.getString("priority") ; 
           obj.image_url = row.getString("image_url") ; 
           obj.category = row.getString("category") ; 
     
            obj.timestamp = row.getString("timestamp") ; 
            list.add(obj); 
        }
 
   }
	 catch (Exception e)
    {
        e.printStackTrace();
   return null ; 
    }

    if(results.equals("failed")==true)
    {
   	 return null ; 
    }
    else
    {
   	 return list ;
   
    }
    
	 }*/
	
	
	
	ArrayList <CommentFields>  retrieveComments (String url) {
		 
   	 String results="failed";
		 
		 String id= "" , name ="" ; 
	     HttpGet request = new HttpGet(url);
	    ArrayList <CommentFields> list = new ArrayList () ;
	    
	    CommentFields obj ; 
	     
	     
	 try
   {
       DefaultHttpClient client = new DefaultHttpClient();
	     HttpResponse response=client.execute(request);
       HttpEntity entity=response.getEntity();
       InputStream is=entity.getContent();
       InputStreamReader isr = new InputStreamReader(is);
       BufferedReader reader = new BufferedReader(isr);
       results=reader.readLine();   
       if(results==null)
    	   return null;
       else{
       JSONArray array = new JSONArray(results);
       for (int i = 0; i < array.length(); i++) {
      	 
      	 obj = new CommentFields () ; 
           JSONObject row = array.getJSONObject(i);
           obj.id = row.getString("id");
           obj.foreign_key = row.getString("foreign_key");
           obj.comment = row.getString("comment") ; 
           obj.location = row.getString("location") ; 
           obj.name= row.getString("name") ; 
          obj.timestamp = row.getString("timestamp") ; 
                 list.add(obj); 
       }

  }}
	 catch (Exception e)
   {
       e.printStackTrace();
  return null ; 
   }
   
   if(results.equals("failed")==true)
   {
  	 return null ; 
   }
   else
   {
  	 return list ;
  
   }
   
	 }

}
