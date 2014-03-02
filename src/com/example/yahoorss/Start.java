package com.example.yahoorss;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Start  extends Activity {
    
	ArrayList<RssFeedStructure> rssStr;
	ArrayList<RssFeedStructure> rssStr_en ;
	ArrayList<RssFeedStructure> rssStr_spor;
	ArrayList<RssFeedStructure> rssStr_pol ;
	
	
	
	ArrayList<Bitmap> bitmap = new ArrayList () ;
	ArrayList<Bitmap> bitmap_en = new ArrayList () ;
	ArrayList<Bitmap> bitmap_spor = new ArrayList () ;
	ArrayList<Bitmap> bitmap_pol  = new ArrayList () ;
	
    ArrayList<RssFeedStructure> list = new ArrayList () ;
    ArrayList<Bitmap> list_1 = new ArrayList () ;
    ProgressBar download ; 
    
  //  Bitmap bitmap ; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    /*    if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish () ; 
        }
        
        */
        final SharedPreferences preferences2;
        preferences2 = this.getSharedPreferences("Language", 0);
        final String languageToLoad = preferences2.getString("languageToLoad","en_US");
        
         //String languageToLoad1 = "hi";
        
        
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration(getResources().getConfiguration());
       config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
       getBaseContext().getResources().getDisplayMetrics());       

		 
        setContentView(R.layout.start_1) ; 

    	String[] sidelist=new String []{"  News","  Videos","  Photos","  Movement","  Panchang","  Health Organisation","  Festival","  Jobs","  Health","  Reepie","  Music","  Technology","  Adventure","  Sports","  Beauty","  Settings","Saved Pages","  About Us"};
        sidelist=getResources().getStringArray(R.array.sidelist); 
      //  Toast.makeText(this, sidelist[0],100).show();
     
        download = (ProgressBar)findViewById(R.id.B1) ; 
        new SomeTask().execute();
        
    }
    
    
    
    /** Inner class for implementing progress bar before fetching data **/
    private class SomeTask extends AsyncTask<String, Void, ArrayList<RssFeedStructure> > 
    {
      //  private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        Bitmap[] ob;
        
        ProgressDialog d ; 
        
        @Override
        protected void onPreExecute()
        {
          
        
        
        }

        @Override
        protected ArrayList<RssFeedStructure> doInBackground(String... params) 
        {
          
            
            String results = "success" ; 
            try {
           
                
                
          /*       x= 22 ; y = 44 ; 
                 
                //Toast.makeText(getApplicationContext(), "hiiiii", Toast.LENGTH_SHORT).show() ;
                
                code for image fetch
                       URL url = new URL("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");
                 //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
                 HttpGet httpRequest = null;

                 httpRequest = new HttpGet(url.toURI());
      
                 HttpClient httpclient = new DefaultHttpClient();
                 HttpResponse response = (HttpResponse) httpclient
                         .execute(httpRequest);
         
                 
                 HttpEntity entity = response.getEntity();
                 BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                 InputStream inpusdfdf1t = b_entity.getContent();

                 bitmap  = BitmapFactory.decodeStream(input);

                 /*	
           */      
                 //Toast.makeText(getApplicationContext(), "success", 2000).show() ;
          //      Toast.makeText(getApplicationContext(), results, 2000).show() ;
             /*    ob=new Bitmap[list.size()];
                 for(int i=0;i<list.size();i++)
                 {
                     URL url = new URL(list.get(i).image_url);
                     //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
                     HttpGet httpRequest = null;

                     httpRequest = new HttpGet(url.toURI());
          
                     HttpClient httpclient = new DefaultHttpClient();
                     HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
             
                     
                     HttpEntity entity = response.getEntity();
                     BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                     InputStream input = b_entity.getContent();

                     ob[i]  = BitmapFactory.decodeStream(input);

                 }*/
            	
            	/*
                 Connect c = new Connect () ; 
                 list = c.front_view("http://freakkydevill.comlu.com/vhp_recent.php") ; 
                */
              
            	String feed = "http://news.yahoo.com/rss/";
            	
            	
            	
            	
        		XmlHandler rh = new XmlHandler();
        	    rssStr = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);
       /* 	    
        * 
        *       String feed_en = "http://news.yahoo.com/rss/entertainment" ; 
            	String feed_pol  = "http://news.yahoo.com/rss/politics" ; 
            	String feed_spor = "http://news.yahoo.com/rss/sports"  ;
            	
        	    rssStr_en = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed_en);
        	    rssStr_spor = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed_spor);
        	    rssStr_pol = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed_pol); 
        	    
        */
        	    
        	    if (rssStr != null && rssStr.size() != 0 )
        	    {
        	    	//download_image ()  ; 
        	    }
        	    
                
                 }
              catch (Exception ex) {
                    
                 
                  results = "faild" ; 
             //    Toast.makeText(getApplicationContext(), ex.toString(), 2000).show() ; 
                 
             }
            
          /*  for (int i = 0 ; i < list.size() ; i++)
            {
                if (list.get(i).image_url != null)
                {
                    
                }
            }
            */
            return list  ;
        }

        @Override
        protected void onPostExecute(ArrayList<RssFeedStructure> result)
        {
  
        	
        	download.setVisibility(View.INVISIBLE) ; 
        	

        //img.setImageBitmap(bitmap);
      
        // Parcelable[] output = new Parcelable[list.size()];
        /*  for (int i=0; i<list.size(); i++) {
             login.putExtra("bitmap"+String.valueOf(i),ob[i]);
         }*/
       
        	
        	//Toast.makeText(getApplicationContext(), String.valueOf(rssStr.get(0).getLink()), 2000).show(); 
        Intent login = new Intent (Start.this , Menu_click.class);
        
      	 login.putExtra("notifications_list", rssStr);
       // login.putExtra("images", bitmap);
      	 startActivity(login);
         finish() ;
         
        	
        	
        	
      	 
       //  login.putExtra("BitmapArray", output);
        
        
        
         //login.putExtra("data",  list ) ; 
         
            
         
        }

        
    }
    
    int  download_image ()
    {
    	for (int i = 0 ; i < rssStr.size() ; i++ ){
    	try {
    	        URL url = new URL(rssStr.get(0).getImgLink());
    	        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
    	        HttpGet httpRequest = null;

    	        httpRequest = new HttpGet(url.toURI());

    	        HttpClient httpclient = new DefaultHttpClient();
    	        HttpResponse response = (HttpResponse) httpclient
    	                .execute(httpRequest);

    	        HttpEntity entity = response.getEntity();
    	        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
    	        InputStream input = b_entity.getContent();

    	        Bitmap bit = BitmapFactory.decodeStream(input);

    	        bitmap.add(bit) ;
    	        
    	        

    	    } catch (Exception ex) {
                     return 0 ; 
    	    }
    	}
    	
    	return 1 ; 
    }


}