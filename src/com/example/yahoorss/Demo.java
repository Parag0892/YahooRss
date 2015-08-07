package com.example.yahoorss;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
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

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Demo extends Activity {
	private LinearLayout MenuList;
	private Button btnToggleMenuList;
	ListView listmenu;
	private int screenWidth;
	private boolean isExpanded;
	int count1 = 0;
	TextView tv;
	ListView l3;
	int width, height;

	ArrayList<String> paths = new ArrayList();
	ArrayList<String> paths_1 = new ArrayList();

	ArrayList<RssFeedStructure> list = new ArrayList();

	ArrayList<Bitmap> bitmap;
	ImageButton refresh;
	String ss = "ff";
	String ss1;

	int menu_click = 0;

	ArrayList<RssFeedStructure> rssStr = new ArrayList();

	boolean dogs = false;
	byte[] buffer;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.activity_demo);

		String[] sidelist = new String[] { "Weather", "Entertainment",
				"Sports", "Politics", "Education", "Opinion", "Business",
				"Tech", "World", "Health", "Save" };

		int a = 0;
		Bundle gotbasket = getIntent().getExtras();
		ss1 = gotbasket.getString("data");

		ss = ss1;
		// Toast.makeText(this, "2", 2000).show();

		tv = (TextView) findViewById(R.id.textView1_n);
		// Toast.makeText(this,"+"+tv.getText()+"+", 1000).show();
		tv.setText(ss1);

		MenuList = (LinearLayout) findViewById(R.id.linearLayout2_n);
		btnToggleMenuList = (Button) findViewById(R.id.button1_n);

		listmenu = (ListView) findViewById(R.id.button2_n);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, sidelist);
		listmenu.setAdapter(adapter);
		listmenu.setAdapter(adapter);
		listmenu.setVisibility(View.GONE);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth = metrics.widthPixels;

		l3 = (ListView) findViewById(R.id.lv_1_n);
		refresh = (ImageButton) findViewById(R.id.refresh_2);

		bitmap = new ArrayList();
		for (int j = 0; j < 30; j++) {
			Drawable myDrawable = getResources().getDrawable(R.drawable.load);
			Bitmap deff_1 = ((BitmapDrawable) myDrawable).getBitmap();
			bitmap.add(deff_1);
		}

		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				finish();
				intent.putExtra("data", ss);
				startActivity(intent);
			}
		});

		btnToggleMenuList.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isExpanded) {

					isExpanded = false;

					refresh.setVisibility(View.VISIBLE);
					tv.setVisibility(View.VISIBLE);
					listmenu.setVisibility(View.GONE);
					MenuList.startAnimation(new CollapseAnimation(MenuList, 0,
							(int) (screenWidth * 0.7), 20, l3, tv));
				} else {
					isExpanded = true;
					refresh.setVisibility(View.INVISIBLE);
					tv.setVisibility(View.INVISIBLE);
					MenuList.startAnimation(new ExpandAnimation(MenuList, 0,
							(int) (screenWidth * 0.7), 20, listmenu));

				}
			}
		});

		// final SavedDataBase ob=new
		// SavedDataBase(this.getApplicationContext());
		// ob.open();
		listmenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == 0) {

					Intent intent = getIntent();
					finish();
					intent.putExtra("data", "Weather");
					startActivity(intent);

				}

				else if (position == 1) {
					Intent intent = getIntent();
					finish();
					intent.putExtra("data", "Entertainment");
					startActivity(intent);
				}

				else if (position == 2) {
					Intent intent = getIntent();
					finish();
					intent.putExtra("data", "Sports");
					startActivity(intent);
				}

				else if (position == 3) {
					Intent intent = getIntent();
					finish();
					intent.putExtra("data", "Politics");
					startActivity(intent);
				} else if (position == 4) {
					Intent i = getIntent();
					i.putExtra("data", "Education");
					startActivity(i);
					finish();
				} else if (position == 5) {
					Intent i = getIntent();
					i.putExtra("data", "Opinion");
					startActivity(i);
					finish();
				} else if (position == 6) {
					// Intent i = new Intent(Menu_click.this , Demo.class);
					Intent i = getIntent();
					i.putExtra("data", "Business");
					startActivity(i);
					finish();
				} else if (position == 7) {
					// Intent i = new Intent(Menu_click.this , Demo.class);
					Intent i = getIntent();
					i.putExtra("data", "Tech");

					startActivity(i);
					finish();
				} else if (position == 8) {
					// Intent i = new Intent(Menu_click.this , Demo.class);
					Intent i = getIntent();
					i.putExtra("data", "World");

					startActivity(i);
					finish();
				} else if (position == 9) {
					// Intent i = new Intent(Menu_click.this , Demo.class);
					Intent i = getIntent();
					i.putExtra("data", "Health");

					startActivity(i);
					finish();
				} else if (position == 10) {
					// Intent i = new Intent(Menu_click.this , Demo.class);
					Intent i = getIntent();
					i.putExtra("data", "Save");

					startActivity(i);
					finish();
				}

			}

		});

		l3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(),
				// rssStr.get(position).getLink(), 2000).show();

				if (position == 0 && ss1.equals("Save")) {
					b_receive();

				} else {

					Intent i = new Intent(Demo.this, New_display.class);
					i.putExtra("link", rssStr.get(position).getLink());
					i.putExtra("imgLink", rssStr.get(position).getImgLink());
					i.putExtra("title", rssStr.get(position).getTitle());
					startActivity(i);

				}

			}

		});

		new loadsome().execute();

	}

	public class loadsome extends
			AsyncTask<String, Integer, ArrayList<RssFeedStructure>> {

		ProgressDialog d;

		@Override
		protected ArrayList<RssFeedStructure> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				if (menu_click == 0) {

					if (ss1.equals("Politics") == true)

					{

						String feed = "http://news.yahoo.com/rss/politics/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Weather") == true)

					{

						String feed = "http://news.yahoo.com/rss/weather/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Sports") == true)

					{

						String feed = "http://news.yahoo.com/rss/sports/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Entertainment") == true)

					{

						String feed = "http://news.yahoo.com/rss/entertainment/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}

					if (ss1.equals("Education") == true)

					{

						String feed = "http://news.yahoo.com/rss/education/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Opinion") == true)

					{

						String feed = "http://news.yahoo.com/rss/opinion/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Business") == true)

					{

						String feed = "http://news.yahoo.com/rss/business/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Tech") == true)

					{

						String feed = "http://news.yahoo.com/rss/tech/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("World") == true)

					{

						String feed = "http://news.yahoo.com/rss/world/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Health") == true)

					{

						String feed = "http://news.yahoo.com/rss/health/";

						XmlHandler rh = new XmlHandler();
						rssStr = (ArrayList<RssFeedStructure>) rh
								.getLatestArticles(feed);

					}
					if (ss1.equals("Save") == true)

					{

						SavedDataBase de = new SavedDataBase(Demo.this);
						de.open();
						rssStr = de.getAllEntries();

						de.close();

						for (int i = 0; i < rssStr.size(); i++) {
							/*
							 * Drawable myDrawable =
							 * getResources().getDrawable(R
							 * .drawable.twitter_icon);
							 * 
							 * 
							 * bitmap.remove(i) ; bitmap.add(i,deff_1) ;
							 */

							try {
								if (rssStr.get(i).getImgLink() != null
										&& rssStr.get(i).getImgLink()
												.equals("false") == false) {
									URL url = new URL(rssStr.get(i)
											.getImgLink());
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

									Bitmap bits_1 = BitmapFactory
											.decodeStream(input);
									bitmap.remove(i);
									bitmap.add(i, bits_1);
								}

								else {
									Drawable myDrawable = getResources()
											.getDrawable(R.drawable.load);
									Bitmap deff_1 = ((BitmapDrawable) myDrawable)
											.getBitmap();
									bitmap.remove(i);
									bitmap.add(i, deff_1);
								}
							} catch (Exception e) {

							}
						}

					}

					for (RssFeedStructure n : rssStr) {
						int num = 50;
						if (n.getTitle().length() > num) {
							paths_1.add(n.getTitle().substring(0, num) + "...");
						} else
							paths_1.add(n.getTitle() + "\n");
						// paths.add(test);
					}

				} else if (menu_click == 1) {

				}

			} catch (Exception e) {

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

			d.dismiss();

			l3.setAdapter(new MyAdapter(Demo.this, R.layout.list_news, paths_1));

			l3.setVisibility(View.VISIBLE);
			System.out.println("call for image download");

			if (ss1.equals("Save") == false)
				new FUCK().start();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			l3.setVisibility(View.GONE);

			// tv.setText("H App4Hindus");
			d = new ProgressDialog(Demo.this);

			d.setMessage("Loading");

			d.show();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

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

			icon.setImageBitmap(bitmap.get(position));

			return row;

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (isExpanded) {

			isExpanded = false;
			refresh.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);
			listmenu.setVisibility(View.GONE);

			MenuList.startAnimation(new CollapseAnimation(MenuList, 0,
					(int) (screenWidth * 0.7), 20, l3, tv));
		} else {
			finish();

		}

	}

	int download_image(ArrayList<RssFeedStructure> rss) {
		System.out.println("inside image download");
		Bitmap deff;

		for (int i = 0; i < rss.size(); i++) {
			try {
				//

				if (rssStr.get(i).getImgLink() != null) {

					System.out.println("inside image download" + rss.size());

					Toast.makeText(getApplicationContext(),
							rssStr.get(i).getImgLink(), 2000).show();
					URL url = new URL(rssStr.get(i).getImgLink());
					// try this url =
					// "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"

					HttpGet httpRequest = null;

					httpRequest = new HttpGet(url.toURI());

					HttpClient httpclient = new DefaultHttpClient();
					HttpResponse response = (HttpResponse) httpclient
							.execute(httpRequest);

					HttpEntity entity = response.getEntity();
					BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
					InputStream input = b_entity.getContent();

					Bitmap bit = BitmapFactory.decodeStream(input);

					if (bit != null) {
						bitmap.remove(i);
						bitmap.add(i, bit);
					} else {

						Drawable myDrawable = getResources().getDrawable(
								R.drawable.load);
						deff = ((BitmapDrawable) myDrawable).getBitmap();
						bitmap.remove(i);
						bitmap.add(i, deff);
					}
				} else {
					Drawable myDrawable = getResources().getDrawable(
							R.drawable.load);
					deff = ((BitmapDrawable) myDrawable).getBitmap();
					bitmap.remove(i);
					bitmap.add(i, deff);
				}

			} catch (Exception ex) {
				return 0;
			}

			/*
			 * Drawable myDrawable =
			 * getResources().getDrawable(R.drawable.twitter_icon); deff =
			 * ((BitmapDrawable) myDrawable).getBitmap(); bitmap.remove(i) ;
			 * bitmap.add(i,deff) ;
			 */
		}
		return 1;
	}

	public class Image_load extends AsyncTask<Void, Void, Void> {

		ProgressDialog qwert;

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			l3.setAdapter(new MyAdapter(Demo.this, R.layout.list_news, paths_1));
			// qwert.dismiss() ;
			System.out.print("in post");

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			System.out.print("in pre");
			// qwert = new ProgressDialog (Demo.this) ;
			// qwert.show() ;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			System.out.print("in do");

			// download_image(rssStr) ;

			return null;
		}

	}

	BluetoothAdapter mBluetoothAdapter;

	public void b_receive() {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			Toast.makeText(getApplicationContext(),
					"Device does not support bluetooth", 2000).show();
			finish();

		}

		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivityForResult(discoverableIntent, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth must be enabled to send", 2000).show();
			finish();
		}

		if (resultCode == 300) {
			// Toast.makeText(getApplicationContext(), "connected", 2000).show()
			// ;

			AcceptThread thread = new AcceptThread();
			thread.start();
		}
	}

	class AcceptThread extends Thread {
		private final BluetoothServerSocket mmServerSocket;

		public AcceptThread() {
			// Use a temporary object that is later assigned to mmServerSocket,
			// because mmServerSocket is final
			BluetoothServerSocket tmp = null;
			final UUID MY_UUID = UUID
					.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

			try {
				// MY_UUID is the app's UUID string, also used by the client
				// code
				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
						"Yahoo", MY_UUID);
			} catch (IOException e) {
			}
			mmServerSocket = tmp;

			if (mmServerSocket == null) {
				// Toast.makeText(getApplicationContext(),
				// "ServerSocket not obtained", 2000).show() ;
			}

		}

		public void run() {

			BluetoothSocket socket = null;
			// Keep listening until exception occurs or a socket is returned
			while (true) {
				try {
					socket = mmServerSocket.accept();

					if (socket != null)
						System.out.println("Hello");
					else {
						System.out.println("o");
					}
				} catch (Exception e) {
					// Toast.makeText(getApplicationContext(),
					// "Error Occured"+"\n"+"Try again.", 2000).show() ;
					// cancel () ;
					break;
				}
				// If a connection was accepted
				if (socket != null) {
					// Do work to manage the connection (in a separate thread)

					// Toast.makeText(getApplicationContext(),
					// socket.getRemoteDevice().getName(), 2000).show() ;
					System.out.println(socket.getRemoteDevice().getName());
					manageConnectedSocket(socket);
					try {
						mmServerSocket.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// Toast.makeText(getApplicationContext(),
						// "Error Occured"+"\n"+"Try again.", 2000).show() ;

						e.printStackTrace();
					}
					break;
				}

			}

		}

		/** Will cancel the listening socket, and cause the thread to finish */
		public void cancel() {
			try {
				mmServerSocket.close();
			} catch (IOException e) {
			}
		}

	}

	String message = "0";

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// get the bundle and extract data by key
			Bundle b = msg.getData();
			String key = b.getString("My Key");
			save_to_database(key);

		}
	};

	void manageConnectedSocket(BluetoothSocket socket) {
		// TODO Auto-generated method stub

		ConnectedThread connected = new ConnectedThread(socket, handler);
		connected.start();

	}

	private void save_to_database(String buff2)

	{
		// TODO Auto-generated method stub
		String title, img, link;

		String temp;

		int index = 0;

		index = buff2.indexOf("`");

		title = buff2.substring(0, index);

		temp = buff2.substring(index + 1);

		index = temp.indexOf("`");

		img = temp.substring(0, index);

		link = temp.substring(index + 1);

		System.out.println("title==" + title);
		System.out.println("image==" + img);

		System.out.println("link==" + link);

		SavedDataBase de = new SavedDataBase(this);
		de.open();
		long check = 0;
		check = de.createEntry(title, img, link);
		de.close();
		System.out.println("" + check);
		// Toast.makeText(getApplicationContext(), ""+check , 2000).show() ;

	}

	class FUCK extends Thread {
		public void run() {
			System.out.println("thread kkkjjkjk");
			Bitmap bits = null;

			try {

				if (rssStr != null) {
					for (int i = 0; i < rssStr.size(); i++) {
						if (rssStr.get(i).getImgLink() != null) {
							URL url = new URL(rssStr.get(i).getImgLink());
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

							bits = BitmapFactory.decodeStream(input);

							if (bits != null) {
								System.out.println("downloaded");
								bitmap.remove(i);
								bitmap.add(i, bits);
							}
						}

						else {
							Drawable myDrawable = getResources().getDrawable(
									R.drawable.load);
							Bitmap deff_1 = ((BitmapDrawable) myDrawable)
									.getBitmap();
							bitmap.remove(i);
							bitmap.add(i, deff_1);
						}
					}

				}

			}

			catch (Exception e) {

			}

			finally {
				dogiiii = true;
				// l3.setAdapter(new MyAdapter(Demo.this, R.layout.list_news ,
				// paths_1));
			}

			// download_image(rssStr) ;
		}
	}

	boolean dogiiii = false;

}
