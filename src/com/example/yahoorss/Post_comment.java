package com.example.yahoorss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Post_comment extends Activity 

{

	ListView comment_s ; 
	Button post ; 
	
   String []id = new String [2] ; 
   String link;
   int count = 0 ; 
   Button back ; 
   TextView t1 ; 
   EditText comment = null ;
   EditText name = null ;
   EditText location = null  ;
   int  width , height ; 
   //String no_of_comments;
   String common [] = new String [3] ; 
   ArrayList <CommentFields> li  = new ArrayList (); 
   
	//ArrayList<String> list = new ArrayList() ; 

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        String[] sidelist=new String []{"  News","  Videos","  Photos","  Movement","  Panchang","  Health Organisation","  Festival","  Jobs","  Health","  Reepie","  Music","  Technology","  Adventure","  Sports","  Beauty","  Settings","Saved Pages","  About Us"};
        sidelist=getResources().getStringArray(R.array.sidelist); 


		 
		setContentView (R.layout.com) ;
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		Bundle gotbasket = getIntent().getExtras();
		link  = gotbasket.getString("link");
		//no_of_comments = gotbasket.getString("no_of_comments");
		t1 = (TextView)findViewById(R.id.te_1) ;
		back = (Button)findViewById(R.id.b_1) ;
		comment_s = (ListView)findViewById(R.id.comments_list) ;
		post = (Button)findViewById(R.id.post) ; 
		
	//	t1.setText("comments ("+no_of_comments+")") ; 
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish () ; 
				
			}
		}) ; 
		
		/*
		 * 
		 * 
		 * code to do add comments 
		 */
		
		  Display display = getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        display.getSize(size);
	       width = size.x;
	       height = size.y;
		 new SomeTask().execute();
		
		
		post.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			//	AlertDialog.Builder ad = new AlertDialog.Builder(Post_comment.this);
			
				AlertDialog.Builder ad =  new AlertDialog.Builder(
						  new ContextThemeWrapper(Post_comment.this, android.R.style.Theme_DeviceDefault_Light_Dialog)) ;
			
				ad.setTitle("Post your comment");
				
				
				
				comment = new EditText(Post_comment.this);
	           name = new EditText(Post_comment.this);
	           location = new EditText(Post_comment.this);
	        
	            comment.setHint("Comment") ; 
	            name.setHint("Name") ; 
	            location.setHint("location") ; 
	            
	            int x = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100 , getResources().getDisplayMetrics()) ; 
	            
	           comment.setHeight(x) ; 
	            
	            name.setMaxLines(3) ;
	            location.setMaxLines(3) ;
				
				
			
	            final Button post = new Button (Post_comment.this) ; 
		        
	            post.setText("Post") ; 
	            
				  LinearLayout l1=new LinearLayout(Post_comment.this);
			        l1.setOrientation(LinearLayout.VERTICAL);
			        l1.addView(comment);
			       
			    	
			        l1.addView(name);
			        l1.addView(location);
			        l1.addView(post) ; 
			     
			      
		 	        l1.setBackgroundColor(Color.WHITE) ; 
		 	      
			         
		 	      
			        ad.setView(l1);
				    
				
			       post.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (comment.getText().toString().length() == 0 || name.getText().toString().length() == 0 ||
								location.getText().toString().length() == 0)
						{
							Toast.makeText(getApplicationContext(), "Please fill the entries carefully", 2000).show() ; 
							count = 0 ; 
	 					}
						else
						{
						 
							count = 1 ; 
							common[0]= name.getText().toString();
							common[1]= comment.getText().toString();
							common[2] =  location.getText().toString();
							new SomeTask().execute();
							
						}
						  finish () ; 
							
				        	startActivity(getIntent());
						
						
					}
				}) ; 
				
			   
					AlertDialog dialog = ad.create();
					
					
				dialog.show();
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = (int) (width*(.95)) ; 
				//lp.height = (int) (width*(.50)) ;
				//lp.x=-170;
				//lp.y=100;
				dialog.getWindow().setAttributes(lp);
			}
		}); 
		
		
	}
	
	
	  private class SomeTask extends AsyncTask<String, Void, ArrayList <CommentFields> > 
	    {
	      //  private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
	       
	        ProgressDialog d ; 
	        
	       
	        
	        @Override
	        protected void onPreExecute()
	        {
	        	
	        	t1.setText("comments");
	           // Toast.makeText(getApplicationContext(), "abhinav", 2000).show() ;
		d = new ProgressDialog (Post_comment.this) ; 
	            if (count == 0 )
	            d.setMessage("Loading") ;
	            else if (count == 1 )
	            	d.setMessage("Posting your comment") ;
	            
	            d.show() ; 
	             
	        
	        }

	        @Override
	        protected ArrayList <CommentFields>  doInBackground(String... params) 
	        {
	            if (count == 0 ){
	        	Connect ob=new Connect();
	        	
	    		       
	    		 
	    		
	    	    li=ob.retrieveComments("http://obscure-escarpment-4510.herokuapp.com/retrieve_comments/?foreign_key="+link);
	    	   
	            }
	            else if (count == 1 )
	            {
	            	 try
					    {
	            		// Toast.makeText(getApplicationContext(), "yuo", 2000).show() ;
							HttpGet request = new HttpGet("http://obscure-escarpment-4510.herokuapp.com/post_comment/?name="+common[0]+"&comment="+common[1]+"&location="+common[2]+"&foreign_key="+link);
							 
								
							 DefaultHttpClient client = new DefaultHttpClient();
							 HttpResponse response=client.execute(request);
							HttpEntity entity=response.getEntity();
							InputStream is=entity.getContent();
					        InputStreamReader isr = new InputStreamReader(is);
					        BufferedReader reader = new BufferedReader(isr);
					       String  re=reader.readLine();
					       Toast.makeText(getApplicationContext(),"in background", 2000);
					       
					       //Toast.makeText(getApplicationContext(), re+"", 2000).show() ; 
					    
					    
					    }catch (Exception e)
						     {
						         
						     }
                      
	            }
	            
	          
	            return li  ;
	        }

	        @Override
	        protected void onPostExecute(ArrayList <CommentFields> list )
	        {
	        	
	        	
	           if (count == 0 )
	           {
	        	   
	        	   if(list!=null)
	        	   {
	        	   ArrayList <String> paths_1 = new ArrayList () ;
	    		for (CommentFields n :list)
	    	       {
	    	    	   
	    	    		   paths_1.add(n.comment+"\n"+n.name+","+n.location);
	    	    	  
	    	    	  // paths.add(test);
	    	       }
	    		//ArrayAdapter adapter = new   ArrayAdapter (Post_comment.this , android.R.layout.simple_list_item_1,paths_1) ; 
	    		comment_s.setAdapter(new MyAdapter(Post_comment.this, R.layout.photos_1, paths_1));
	    		
	    		d.dismiss() ;
	    		 t1.setText("comments ("+list.size()+")");
	    		
	        	   }else
	        	   {
	        		   d.dismiss() ;
	        	   }
	        	   
	           }
	           else if (count == 1 )
	           {
	        	   d.dismiss() ;
	        	   count = 0 ; 
	           }
	            
	         
	         
	        }

	        
	    }


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish() ; 
	}
	  
	public class MyAdapter extends ArrayAdapter<String>{

    	public MyAdapter(Context context, int textViewResourceId,	ArrayList<String> paths_1) {
    		super(context, textViewResourceId, paths_1);

    	}

    	@Override
    	public View getDropDownView(int position, View convertView,ViewGroup parent) {
    		return getCustomView(position, convertView, parent);
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		return getCustomView(position, convertView, parent);
    	}

    	@SuppressLint("NewApi")
		public View getCustomView(int position, View convertView, ViewGroup parent) {
    		
    	

    		LayoutInflater inflater=getLayoutInflater();
	    	View row=inflater.inflate(R.layout.custom_comment , parent, false);
	 	           TextView label1=(TextView)row.findViewById(R.id.V1);
		    	
		 //   	label.setText(paths_1.get(position));
 		    	
		    	
		    	//ImageView icon=(ImageView)row.findViewById(R.id.image_pic);
	 	          TextView label2=(TextView)row.findViewById(R.id.V2);
	 	          
	 	          label1.setText(li.get(position).comment) ; 
	 	          label2.setText(li.get(position).name+","+li.get(position).location) ; 
			    	
		    	
             return row;
    		
    	}

   }
	

}
