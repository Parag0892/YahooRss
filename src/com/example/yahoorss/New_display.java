package com.example.yahoorss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class New_display extends Activity 

{

	 
	TextView content , head ; 
	String [] display = new String [2] ; 
	Button comm ;
	String link;
	Button save;
	TextView tvno ;		WebView wb;
	
	String id;
	 String results = "0" ;
	 List<RssFeedStructure> li1;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final SharedPreferences preferences2;
        preferences2 = this.getSharedPreferences("Language", 0);
        final String languageToLoad = preferences2.getString("languageToLoad","en_US");
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration(getResources().getConfiguration());
       config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
       getBaseContext().getResources().getDisplayMetrics());       

		 
		setContentView (R.layout.news_display) ;
	 wb = (WebView)findViewById(R.id.webView1);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	    tvno=(TextView)findViewById(R.id.textView1);
		//head = (TextView)findViewById(R.id.head);
		//content = (TextView)findViewById(R.id.content);
		comm = (Button)findViewById(R.id.comment1);
		save=(Button)findViewById(R.id.save);
		//final SavedDataBase ob=new SavedDataBase(this.getApplicationContext());
		//ob.open();
		
		 Bundle b = this.getIntent().getExtras();
		 //li1=(List<RssFeedStructure>) b.getSerializable("ob1");
		 link = b.getString("link");	
         Toast.makeText(getApplicationContext(), link, 2000).show();

		 
		 
		 //id=li1.get(0).id;
		/* save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		     ob.insert(li1.get(0));
		     
			
			
			}
		});*/
		 new SomeTask().execute();
       
		
		Toast.makeText(getApplicationContext(),wb.getUrl(),2000).show();
		 
	
		
		// 
		 
		 
	/*	 head.setText(li1.get(0).getTitle()) ;
		 content.setText(li1.get(0).body) ;*/
		 
		  
	    comm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent (New_display.this, Post_comment.class);
				i.putExtra("link",link);
				//i.putExtra("no_of_comments",results);
				startActivity(i);
				
			}
		}); 
	
	
	}
	
	  private class SomeTask extends AsyncTask<String, Void, String > 
	    {
	      //  private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
	       
	        ProgressDialog d ; 
	       // String x="";
	        
	      // ArrayList <CommentFields> li  = new ArrayList (); 
	        
	        @Override
	        protected void onPreExecute()
	        {
	           // Toast.makeText(getApplicationContext(), "abhinav", 2000).show() ;
	        	save.setVisibility(View.GONE);
		d = new ProgressDialog (New_display.this) ; 
	           
	            d.setMessage("Loading") ;
	            d.show() ; 
	             
	        
	        }

	        @Override
	        protected String  doInBackground(String... params) 
	        {
	        	// HttpGet request = new HttpGet("http://freakkydevill.comlu.com/vhp_comments_count.php?foreign_key="+id);
	        	HttpGet request = new HttpGet("http://obscure-escarpment-4510.herokuapp.com/comments_count/?foreign_key="+link);
	    		 try
	    		    {
	    		        HttpClient client = new DefaultHttpClient();
	    			     HttpResponse response=client.execute(request);
	    		        HttpEntity entity=response.getEntity();
	    		        InputStream is=entity.getContent();
	    		        InputStreamReader isr = new InputStreamReader(is);
	    		        BufferedReader reader = new BufferedReader(isr);
	    		        results = reader.readLine(); 
	    		        //this.x = results;
	    		        
	    		        
	    		    }
	    		 catch(Exception e)
	    		 {
	    		 
	    		 }
	    		 return results ; 
	        }

	        @Override
	        protected void onPostExecute(String x)
	        {
	        	 
	        	 d.dismiss() ; 
	        	 save.setVisibility(View.VISIBLE);
	             tvno.setText("("+x+")") ; 
	             wb.loadUrl(link);
	        }

	        
	    }

}
