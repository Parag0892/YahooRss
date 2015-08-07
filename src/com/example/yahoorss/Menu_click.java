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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yahoorss.animation.CollapseAnimation;
import com.example.yahoorss.animation.ExpandAnimation;
import com.example.yahoorss.xmlhandler.RssFeedStructure;
import com.example.yahoorss.xmlhandler.XmlHandler;

public class Menu_click extends Activity

{

	static ImageButton menu;
	ListView l3;

	int count = 0;
	String height1;

	boolean to_do = true;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	ImageButton refresh, facebook;
	int width, height;
	ArrayList<RssFeedStructure> list = new ArrayList();
	Bitmap[] ob;
	// ArrayList<News> p_1 ;
	ArrayList<String> paths = new ArrayList();
	ArrayList<String> paths_1 = new ArrayList();
	ArrayAdapter adapter;

	ArrayList<Bitmap> bitmap = new ArrayList();

	ArrayList<Bitmap> b_p = new ArrayList();

	// String test = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
	// ;

	TextView tv;

	private LinearLayout MenuList;
	private Button btnToggleMenuList;
	ListView listmenu;
	private int screenWidth;
	private boolean isExpanded;
	int count1 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		// Language
		super.onCreate(savedInstanceState);
		final SharedPreferences preferences2;
		preferences2 = this.getSharedPreferences("Language", 0);
		final String languageToLoad = preferences2.getString("languageToLoad",
				"en_US");
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration(getResources()
				.getConfiguration());
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		setContentView(R.layout.main);

		SavedDataBase de = new SavedDataBase(this);
		de.open();
		long check = 0;
		check = de.createEntry("Tap here to receive from bluetooth", "false",
				"false");
		de.close();

		facebook = (ImageButton) findViewById(R.id.facebook_share);
		facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri
						.parse("https://www.facebook.com/yahoorssreader?ref=br_tf");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		Bundle b = this.getIntent().getExtras();
		list = (ArrayList<RssFeedStructure>) b
				.getSerializable("notifications_list");

		int i = 0;

		if (b_p.size() == 0) {
			b_p = new ArrayList();
			for (int i_j = 0; i < 30; i++) {
				Drawable myDrawable = getResources().getDrawable(
						R.drawable.load);
				Bitmap deff_1 = ((BitmapDrawable) myDrawable).getBitmap();
				b_p.add(deff_1);
			}
		}

		int img_count = 0;

		for (RssFeedStructure n : list) {
			Drawable myDrawable = getResources().getDrawable(R.drawable.load);
			Bitmap deff_1 = ((BitmapDrawable) myDrawable).getBitmap();
			b_p.remove(img_count);
			b_p.add(img_count, deff_1);
			img_count++;

		}

		tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Headlines");

		MenuList = (LinearLayout) findViewById(R.id.linearLayout2);
		btnToggleMenuList = (Button) findViewById(R.id.button1);
		refresh = (ImageButton) findViewById(R.id.refresh_1);

		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				new SomeTask().execute();

			}
		});

		listmenu = (ListView) findViewById(R.id.button2);
		String[] sidelist = new String[] { "Weather", "Entertainment",
				"Sports", "Politics", "Education", "Opinion", "Business",
				"Tech", "World", "Health", "Save" };

		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, sidelist);
		listmenu.setAdapter(adapter);
		listmenu.setAdapter(adapter);
		listmenu.setVisibility(View.GONE);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth = metrics.widthPixels;
		btnToggleMenuList.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isExpanded) {

					isExpanded = false;
					refresh.setVisibility(View.VISIBLE);
					listmenu.setVisibility(View.GONE);
					MenuList.startAnimation(new CollapseAnimation(MenuList, 0,
							(int) (screenWidth * 0.7), 20, l3, tv));
				} else {
					isExpanded = true;
					// l3.setVisibility(View.GONE) ;
					tv.setVisibility(View.GONE);
					refresh.setVisibility(View.INVISIBLE);
					MenuList.startAnimation(new ExpandAnimation(MenuList, 0,
							(int) (screenWidth * 0.7), 20, listmenu));

				}
			}
		});

		for (RssFeedStructure n : list) {
			int num = 50;
			if (n.getTitle().length() > num) {
				paths_1.add(n.getTitle().substring(0, num) + "...");
			} else
				paths_1.add(n.getTitle() + "\n");
			// paths.add(test);
		}

		l3 = (ListView) findViewById(R.id.lv_1);
		l3.setAdapter(new MyAdapter(Menu_click.this, R.layout.list_news,
				paths_1));
		new Image_load().execute();

		listmenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if (position == 0) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Weather");
					startActivity(i);

				} else if (position == 1) {

					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Entertainment");
					startActivity(i);

					// Toast.makeText(getApplicationContext(),""+ position,
					// 2000).show() ;
				}

				else if (position == 2) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Sports");
					startActivity(i);

				}

				else if (position == 3) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Politics");
					startActivity(i);

				}

				else if (position == 4)

				{
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Education");
					startActivity(i);

				} else if (position == 5) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Opinion");
					startActivity(i);

				} else if (position == 6) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Business");
					startActivity(i);

				} else if (position == 7) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Tech");

					startActivity(i);

				} else if (position == 8) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "World");

					startActivity(i);

				} else if (position == 9) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Health");

					startActivity(i);

				}

				else if (position == 10) {
					Intent i = new Intent(Menu_click.this, Demo.class);
					i.putExtra("data", "Save");

					startActivity(i);

				}

			}

		});

		l3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				// Bundle basket = new Bundle ();

				// basket.putStringArray("data",new String
				// []{list.get(position).head.toString(),list.get(position).body.toString(),list.get(position).id}
				// );
				Intent i = new Intent(Menu_click.this, New_display.class);
				i.putExtra("link", list.get(position).getLink());
				i.putExtra("imgLink", list.get(position).getImgLink());
				i.putExtra("title", list.get(position).getTitle());
				startActivity(i);

			}

		});

		RssFeedStructure a = new RssFeedStructure();
		a.setLink("test");
		a.setImgLink("test");
		a.setTitle("as");

		// Toast.makeText(this, String.valueOf(jkl.size()), 2000).show();

		String results = "failed";

	}

	public class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int textViewResourceId,
				ArrayList<String> paths_1) {
			super(context, textViewResourceId, paths_1);

		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@SuppressLint("NewApi")
		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.photos_1, parent, false);

			TextView label = (TextView) row.findViewById(R.id.company);

			android.view.ViewGroup.LayoutParams params3 = label
					.getLayoutParams();
			params3.height = width * 8 / 45;
			params3.width = width * 7 / 10;
			label.setLayoutParams(params3);

			label.setText(paths_1.get(position));

			ImageView icon = (ImageView) row.findViewById(R.id.image_pic);

			android.view.ViewGroup.LayoutParams params2 = icon
					.getLayoutParams();
			params2.height = width * 8 / 45;
			params2.width = width * 44 / 100;
			icon.setLayoutParams(params2);

			icon.setImageBitmap(b_p.get(position));

			return row;

		}

	}

	public class loading extends AsyncTask<String, Integer, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

	private class SomeTask extends
			AsyncTask<String, Void, ArrayList<RssFeedStructure>> {
		// private ProgressDialog Dialog = new
		// ProgressDialog(MainActivity.this);
		Bitmap[] ob;

		ProgressDialog d;

		@Override
		protected void onPreExecute() {
			// Toast.makeText(getApplicationContext(), "abhinav", 2000).show() ;
			d = new ProgressDialog(Menu_click.this);

			d.setMessage("Loading");
			l3.setVisibility(View.GONE);
			d.show();

		}

		@Override
		protected ArrayList<RssFeedStructure> doInBackground(String... params) {

			String results = "success";
			try {
				list = new ArrayList();
				paths_1 = new ArrayList();
				/*
				 * Connect c = new Connect () ; list =
				 * c.front_view("http://freakkydevill.comlu.com/vhp_recent.php")
				 * ;
				 */

				String feed = "http://news.yahoo.com/rss/";
				XmlHandler rh = new XmlHandler();
				list = (ArrayList<RssFeedStructure>) rh.getLatestArticles(feed);

				if (list != null) {

				} else {
					list = new ArrayList();
				}

			} catch (Exception ex) {
				results = "faild";
			}

			finally {
				for (RssFeedStructure n : list) {
					int num = 50;
					if (n.getTitle().length() > num) {
						paths_1.add(n.getTitle().substring(0, num) + "...");
					} else
						paths_1.add(n.getTitle() + "\n");
					// paths.add(test);
				}
			}
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<RssFeedStructure> result) {
			d.dismiss();

			l3.setAdapter(new MyAdapter(Menu_click.this, R.layout.list_news,
					paths_1));

			l3.setVisibility(View.VISIBLE);

			new Image_load().execute();

			refresh.setVisibility(View.VISIBLE);

		}

	}

	int download_image() {

		Bitmap deff;
		// b_p = new ArrayList () ;
		View v = null;
		ImageView image = null;

		int vis = 0;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				try {
					if (list.get(i).getImgLink() != null) {
						URL url = new URL(list.get(i).getImgLink());
						// try this url =
						// "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
						HttpGet httpRequest = null;

						httpRequest = new HttpGet(url.toURI());

						HttpClient httpclient = new DefaultHttpClient();
						HttpResponse response = (HttpResponse) httpclient
								.execute(httpRequest);

						HttpEntity entity = response.getEntity();
						BufferedHttpEntity b_entity = new BufferedHttpEntity(
								entity);
						InputStream input = b_entity.getContent();

						Bitmap bit = BitmapFactory.decodeStream(input);

						if (bit != null) {
							b_p.remove(i);
							b_p.add(i, bit);
							deff = bit;
							// image.setImageBitmap(bit) ;
						}

						else {
							Drawable myDrawable = getResources().getDrawable(
									R.drawable.load);
							deff = ((BitmapDrawable) myDrawable).getBitmap();
							b_p.remove(i);
							b_p.add(i, deff);
							// image.setImageBitmap(deff) ;
						}
					}

					else {
						Drawable myDrawable = getResources().getDrawable(
								R.drawable.load);
						deff = ((BitmapDrawable) myDrawable).getBitmap();
						b_p.remove(i);
						b_p.add(i, deff);
						//
					}

				} catch (Exception ex) {
					return 0;
				}
			}
		}

		else {
			finish();
		}
		return 1;
	}

	public class Image_load extends AsyncTask<Void, Void, Void> {

		ProgressDialog qwert;

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			l3.setAdapter(new MyAdapter(Menu_click.this, R.layout.list_news,
					paths_1));
			// qwert.dismiss() ;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			qwert = new ProgressDialog(Menu_click.this);
			// qwert.show() ;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			if (list != null)
				download_image();

			return null;
		}

	}

}
