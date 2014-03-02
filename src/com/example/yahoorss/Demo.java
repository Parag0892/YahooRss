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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Demo extends Activity {
	 private LinearLayout MenuList;
		private Button btnToggleMenuList ;
		ListView listmenu ;
		private int screenWidth;
		private boolean isExpanded;
		int count1 = 0  ; 
		 TextView tv  ; 
			ListView  l3 ;
			int width , height ; 
			
			ArrayList <String> paths = new ArrayList () ;
			ArrayList <String> paths_1 = new ArrayList () ;
			
			ArrayList<RssFeedStructure> list = new ArrayList () ;
			
			 ArrayList <Bitmap>  bitmap = new ArrayList (); 
			 Button refresh ; 
			 String ss = "ff" ; 
					 String ss1 ;
			 int menu_click = 0  ; 
			
			 ArrayList<RssFeedStructure> rssStr; 
			 
			
			 
			 
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_demo);
     
        String[] sidelist=new String []{"Weather","Entertainment","Sports","Politics","Save"};
 	   
        int a = 0;
       Bundle gotbasket = getIntent().getExtras();
		    ss1 = gotbasket.getString("data");
		// ss = ss1 ;
       /*for(String s: sidelist){
    	   if(s.equals(ss1))
    	   {
    		   break;
    	   }
    	   else
    		   a++;
       }
       
        sidelist=getResources().getStringArray(R.array.sidelist); 
    ss = sidelist[a];
		
		*/
    tv = (TextView)findViewById(R.id.textView1_n);
    //Toast.makeText(this,"+"+tv.getText()+"+", 1000).show();
    tv.setText(ss1); 
		
	
		 
		
	//Toast.makeText(this,"+"+ss1+"+", 1000).show();
	//Toast.makeText(this,"+"+ss+"+", 1000).show();
		
		
		  MenuList = (LinearLayout) findViewById(R.id.linearLayout2_n);
	        btnToggleMenuList = (Button) findViewById(R.id.button1_n);
	        
	        listmenu= (ListView)findViewById(R.id.button2_n) ;
	        ArrayAdapter adapter	 = new   ArrayAdapter (this , android.R.layout.simple_list_item_1,sidelist) ;
	        listmenu.setAdapter(adapter) ;
	        listmenu.setAdapter(adapter) ;
	      listmenu.setVisibility(View.GONE) ; 
	     
	        DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        screenWidth = metrics.widthPixels;
	        
	    	l3 = (ListView)findViewById(R.id.lv_1_n) ;
	    	refresh = (Button)findViewById(R.id.refresh_2) ; 
	    			
	    	refresh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = getIntent();
					finish();
					 intent.putExtra( "data",ss) ;
					startActivity(intent);
				}
			}) ; 
	    	
	        btnToggleMenuList.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				
	        		if (isExpanded) {
	        			
	        			isExpanded = false;
	        		 listmenu.setVisibility(View.GONE) ; 
	        		 MenuList.startAnimation(new CollapseAnimation(MenuList, 0,(int)(screenWidth*0.7), 20,l3,tv));
			        	}
	        		else {
	            		isExpanded = true;
	            		
	            				MenuList.startAnimation(new ExpandAnimation(MenuList, 0,(int)(screenWidth*0.7), 20,listmenu));
	            				 
	            				
	        		}			
	        		}
	        });
	        
	      //  final SavedDataBase ob=new SavedDataBase(this.getApplicationContext());
	      //  ob.open();
	        listmenu.setOnItemClickListener(new OnItemClickListener (){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position ,
						long arg3) {
					// TODO Auto-generated method stub

					if (position == 0)
					{
						/*
						Intent intent = getIntent();
						finish();
						 intent.putExtra( "data","Weather") ;
						startActivity(intent);
					  */
					
					}
					
					else if(position== 4)
					{
						
					/*	ArrayList<Front_view> temp=ob.getAll();
						
						if(!temp.isEmpty())
						
							
							//Toast.makeText(this,"Nothing Saved In Database",2000).show();
						{
						Bitmap bitmap = null;
						 Intent login = new Intent (Demo.this , Menu_click.class);
				          
				    		//Drawable myDrawable = getResources().getDrawable(R.drawable.fb_icon);
				         //	bitmap =  ((BitmapDrawable) myDrawable).getBitmap();
				         	
				          //  login.putExtra("BitmapArray", output);
				            login.putExtra("notifications_list", temp);
				            
				           
				            login.putExtra("BitmapImage"+String.valueOf(1), bitmap);
				            //login.putExtra("data",  list ) ; 
				            startActivity(login);*/
					//	}
					}
					else if (position == 1 )
					{
						Intent intent = getIntent();
						finish();
						intent.putExtra( "data","Entertainment") ;
						startActivity(intent);
					}

					else if (position == 2 )
					{
						Intent intent = getIntent();
						finish();
						intent.putExtra( "data","Sports") ;
						startActivity(intent);
					}
					
					else if (position == 3 )
					{
						Intent intent = getIntent();
						finish();
						intent.putExtra( "data","Politics") ;
						startActivity(intent);
					}
								}

		    });
	        
	        l3.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					// TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), rssStr.get(position).getLink(), 2000).show();
					Intent i = new Intent(Demo.this,New_display.class);
				    i.putExtra("link",rssStr.get(position).getLink());
				    startActivity(i);					

				}


	         });
	        
	        

	        new loadsome().execute() ; 
	 
	}
	

	public class loadsome extends AsyncTask<String,Integer,ArrayList<RssFeedStructure>>
	{

		ProgressDialog d ; 
		
		@Override
		protected ArrayList<RssFeedStructure> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try 
			{
				
			
	 	       if (menu_click == 0) {
	 	      
              if(ss1.equals("Politics" )== true)
	 	     
              {
            	  
            	  String feed = "http://news.yahoo.com/rss/politics/";
              	
              	XmlHandler rh = new XmlHandler();
          	    rssStr = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);
          	    
          	  download_image (rssStr)  ; 
          	   
            	  
              }
              if(ss1.equals("Sports" )== true )
     	 	     
              {
            	  
            	  String feed = "http://news.yahoo.com/rss/sports/";
              	
              	XmlHandler rh = new XmlHandler();
          	    rssStr = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);
          	  download_image (rssStr)  ;
            	  
              }
              if(ss1.equals("Entertainment")== true )
     	 	     
              {
            	  
            	  String feed = "http://news.yahoo.com/rss/entertainment/";
              	
              	XmlHandler rh = new XmlHandler();
          	    rssStr = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);
          	  download_image (rssStr)  ;
            	  
              }
            	  
            	  
            	 
            	  
              for (RssFeedStructure n :rssStr  )
   	          {
   	    	   int num=50;
   	    	   if(n.getTitle().length()>num)
   	    	   {
   	    		   paths_1.add(n.getTitle().substring(0,num)+"...");
   	    	   }
   	    	   else
   	    	   paths_1.add(n.getTitle()+"\n") ;
   	    	  // paths.add(test);
   	       }

	 	       }
	 	       else if (menu_click == 1)
	 	       {
	 	    	  
	 	    	  String feed = "http://news.yahoo.com/rss/";
	              	
	              	XmlHandler rh = new XmlHandler();
	          	    rssStr = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);
	               
	 	       }
				
			}catch (Exception e )
			{
				
			}
			
			return rssStr;
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		
		@Override
		protected void onPostExecute(ArrayList<RssFeedStructure> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (menu_click == 0 )
			{
	    	l3.setAdapter(new MyAdapter(Demo.this, R.layout.list_news , paths_1));
	        d.dismiss() ;
			l3.setVisibility(View.VISIBLE) ;
			Toast.makeText(getApplicationContext(), ""+bitmap.size()+"yuyu"+rssStr.size(), 2000).show()  ;
			}
			else if (menu_click == 1 )
			{
				  /*Intent login = new Intent(Demo.this , Menu_click.class);
				  Bitmap bitmap = null;
		    		Drawable myDrawable = getResources().getDrawable(R.drawable.fb_icon);
		         	bitmap =  ((BitmapDrawable) myDrawable).getBitmap();
		         	 login.putExtra("notifications_list", result);
		             
		             login.putExtra("BitmapImage"+String.valueOf(1), bitmap);
		             d.dismiss() ; 
		             startActivity(login);
		             
		             finish() ; */
				
				
				 Intent login = new Intent (Demo.this , Menu_click.class);
			        tv.setText("Headlines") ;
		      	 login.putExtra("notifications_list", rssStr);
		       // login.putExtra("images", bitmap);
		      	 startActivity(login);
		         finish() ;
		             
			}
			
			//Toast.makeText(getApplicationContext(), ss.toString(),2000).show() ; 
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			 l3.setVisibility(View.GONE) ;
			
			//tv.setText("H App4Hindus");
			d = new ProgressDialog (Demo.this) ; 
            
            d.setMessage("Loading") ;
            
            d.show() ; 
            
			
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
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
    		
    		
    		 Display display = getWindowManager().getDefaultDisplay();
 	        Point size = new Point();
 	        display.getSize(size);
 	        width = size.x;
 	        height = size.y;
 	      

    		LayoutInflater inflater=getLayoutInflater();
	    	View row=inflater.inflate(R.layout.photos_1 , parent, false);
	    
		         
		         
		           TextView label=(TextView)row.findViewById(R.id.company);
		    	
		    	android.view.ViewGroup.LayoutParams params3 = label.getLayoutParams();
		         params3.height = width*8/45;
		         params3.width =  width*7/10;
		         label.setLayoutParams(params3);
		         
		    	
		    	label.setText(paths_1.get(position));
 		    	
		    	
		    	ImageView icon=(ImageView)row.findViewById(R.id.image_pic);
		    	 
		    	 android.view.ViewGroup.LayoutParams params2 = icon.getLayoutParams();
		         params2.height = width*8/45;
		         params2.width =  width*44/100 ;
		         icon.setLayoutParams(params2);
		   	
      	     	
		       
		      /*   Drawable myDrawable = getResources().getDrawable(R.drawable.fb_icon);
		         Bitmap	bimp =  ((BitmapDrawable) myDrawable).getBitmap();
		        */ 
		         icon.setImageBitmap(bitmap.get(position)) ; 
      	     	
		       
		         return row;
    		
    	}

   }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
		menu_click = 1 ; 
		 new loadsome().execute();
	
	}
	
	
	int  download_image (ArrayList<RssFeedStructure> rss)
    {
		
		 Bitmap deff ;
    	for (int i = 0 ; i < rss.size() ; i++ ){
    	try {
    		
    		    if (rssStr.get(i).getImgLink() != null) {
    	        URL url = new URL(rssStr.get(i).getImgLink());
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
    
    	       
    	        
    	        if (bit != null)
    	        {
    	        	bitmap.add(bit) ; 
    	        }
    	        else 
    	        {
    	        	 
    	        	Drawable myDrawable = getResources().getDrawable(R.drawable.fb_icon);
		         	deff  =  ((BitmapDrawable) myDrawable).getBitmap();
		         	bitmap.add(deff) ; 
    	        }
    		    }else
    		    {
    		    	Drawable myDrawable = getResources().getDrawable(R.drawable.fb_icon);
		         	deff  =  ((BitmapDrawable) myDrawable).getBitmap();
		         	bitmap.add(deff) ;
    		    }
    	  

    	    } catch (Exception ex) {
                     return 0 ; 
    	    }
    	    
    	    
    	}
    	
    	return 1 ; 
    }
	
	
	
}
