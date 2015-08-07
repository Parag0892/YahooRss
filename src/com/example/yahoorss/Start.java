package com.example.yahoorss;

import com.example.yahoorss.xmlhandler.*;

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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Start extends Activity {

	ArrayList<RssFeedStructure> rssStr = new ArrayList();

	ArrayList<Bitmap> bitmap = new ArrayList();

	ArrayList<RssFeedStructure> list = new ArrayList();
	ArrayList<Bitmap> list_1 = new ArrayList();
	ProgressBar download;

	// Bitmap bitmap ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		 * if (getIntent().getBooleanExtra("EXIT", false)) { finish () ; }
		 */
		final SharedPreferences preferences2;
		preferences2 = this.getSharedPreferences("Language", 0);
		final String languageToLoad = preferences2.getString("languageToLoad",
				"en_US");

		// String languageToLoad1 = "hi";

		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration(getResources()
				.getConfiguration());
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		setContentView(R.layout.start_1);

		// Toast.makeText(this, sidelist[0],100).show();

		download = (ProgressBar) findViewById(R.id.B1);

		for (int j = 0; j < 25; j++) {

			Drawable myDrawable = getResources().getDrawable(
					R.drawable.ic_launcher);

			Bitmap deff_1 = ((BitmapDrawable) myDrawable).getBitmap();
			bitmap.add(deff_1);
		}

		new SomeTask().execute();

	}

	/** Inner class for implementing progress bar before fetching data **/
	private class SomeTask extends
			AsyncTask<String, Void, ArrayList<RssFeedStructure>> {
		// private ProgressDialog Dialog = new
		// ProgressDialog(MainActivity.this);
		Bitmap[] ob;

		ProgressDialog d;

		@Override
		protected ArrayList<RssFeedStructure> doInBackground(String... params) {

			String results = "success";
			try {

				String feed = "http://news.yahoo.com/rss/";

				XmlHandler rh = new XmlHandler();
				rssStr = (ArrayList<RssFeedStructure>) rh
						.getLatestArticles(feed);

				if (rssStr == null) {
					rssStr = new ArrayList();
				}
				if (rssStr != null && rssStr.size() != 0) {
					// download_image () ;
				}

			} catch (Exception ex) {
				results = "faild";
			}

			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<RssFeedStructure> result) {

			download.setVisibility(View.INVISIBLE);
			Intent login = new Intent(Start.this, Menu_click.class);
			Bitmap b = null;
			for (int i = 0; i < rssStr.size(); i++) {
				if (bitmap != null && bitmap.get(i) != null)
					b = bitmap.get(i);
				else

				{
					Drawable myDrawable = getResources().getDrawable(
							R.drawable.load);
					b = ((BitmapDrawable) myDrawable).getBitmap();
				}
				// login.putExtra("BitmapImage_"+String.valueOf(i), b);
			}
			login.putExtra("notifications_list", rssStr);

			// Toast.makeText(getApplicationContext(), ""+bitmap.size() +
			// "_jhjhjh" + rssStr.size() ,2000 ).show() ;
			finish();
			startActivity(login);
		}

	}

	int download_image() {
		Bitmap deff;
		if (rssStr != null) {
			for (int i = 0; i < rssStr.size(); i++) {
				try {
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
					}

					else {
						Drawable myDrawable = getResources().getDrawable(
								R.drawable.load);
						deff = ((BitmapDrawable) myDrawable).getBitmap();
						bitmap.remove(i);
						bitmap.add(i, deff);
					}
				} catch (Exception ex) {
					return 0;
				}
			}
		}

		else {

			Toast.makeText(getApplicationContext(), "rssStr is null", 2000)
					.show();
			finish();
		}

		return 1;
	}

	boolean internet() {
		boolean wifi = false;
		boolean data = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					wifi = true;

			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					data = true;

		}
		return wifi | data;

	}

}