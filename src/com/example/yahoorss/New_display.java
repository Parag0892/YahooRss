package com.example.yahoorss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yahoorss.xmlhandler.RssFeedStructure;

@SuppressLint("NewApi")
public class New_display extends Activity

{

	TextView content, head;
	String[] display = new String[2];
	Button comm;
	String link, title, imgLink;
	Button saving, send_bluetooth;
	TextView tvno;
	WebView wb;
	String TAG = "xyxyx";

	String id;
	String results = "0";
	List<RssFeedStructure> li1;

	Set<BluetoothDevice> devicesarray;

	ArrayList<String> pairdevice = new ArrayList();

	BluetoothAdapter mBluetoothAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

		setContentView(R.layout.news_display);
		wb = (WebView) findViewById(R.id.webView1);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		tvno = (TextView) findViewById(R.id.textView1);
		// head = (TextView)findViewById(R.id.head);
		// content = (TextView)findViewById(R.id.content);
		comm = (Button) findViewById(R.id.comment1);

		saving = (Button) findViewById(R.id.saving);
		send_bluetooth = (Button) findViewById(R.id.bletooth_send);

		Bundle b = this.getIntent().getExtras();
		// li1=(List<RssFeedStructure>) b.getSerializable("ob1");
		link = b.getString("link");

		imgLink = b.getString("imgLink");
		title = b.getString("title");
		// Toast.makeText(getApplicationContext(), link, 2000).show();

		saving.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SavedDataBase de = new SavedDataBase(New_display.this);
				de.open();
				long check = 0;
				check = de.createEntry(title, imgLink, link);
				de.close();
				if (check >= 0)
					Toast.makeText(getApplicationContext(), "Saved", 2000)
							.show();
				else if (check == -999)
					Toast.makeText(getApplicationContext(), "Already Saved",
							2000).show();
				else
					Toast.makeText(getApplicationContext(), "Error in saving",
							2000).show();

			}
		});

		send_bluetooth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				if (mBluetoothAdapter == null) {
					// Device does not support Bluetooth4
					Toast.makeText(getApplicationContext(),
							"No bluetooth Detected", 2000).show();
					return;
				}

				if (!mBluetoothAdapter.isEnabled()) {
					Intent enableBtIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, 1);
				}

				if (mBluetoothAdapter.isEnabled()) {
					devicesarray = mBluetoothAdapter.getBondedDevices();

					if (devicesarray != null && devicesarray.size() > 0) {
						for (BluetoothDevice device : devicesarray) {
							pairdevice.add(device.getName() + "\n"
									+ device.getAddress());
						}

					}
					Toast.makeText(getApplicationContext(),
							"" + pairdevice.size(), 2000).show();

					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
							New_display.this);
					builderSingle.setIcon(R.drawable.ic_launcher);
					builderSingle.setTitle("Select One Name:-");
					final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							New_display.this,
							android.R.layout.select_dialog_singlechoice);

					for (String x : pairdevice) {
						arrayAdapter.add(x);
					}
					builderSingle.setNegativeButton("cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});

					builderSingle.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {
								String strName;

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									strName = arrayAdapter.getItem(which);
									AlertDialog.Builder builderInner = new AlertDialog.Builder(
											New_display.this);
									builderInner.setMessage(strName);
									builderInner
											.setTitle("Your Selected Item is");
									builderInner
											.setPositiveButton(
													"Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															String add = strName
																	.substring(strName
																			.length() - 17);
															Toast.makeText(
																	getApplicationContext(),
																	add, 2000)
																	.show();

															ConnectThread thread = new ConnectThread(
																	add);
															thread.start();

															dialog.dismiss();
														}
													});
									builderInner.show();
								}
							});
					builderSingle.show();
				}

			}

		});

		// id=li1.get(0).id;

		// id=li1.get(0).id;
		/*
		 * save.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub ob.insert(li1.get(0));
		 * 
		 * 
		 * 
		 * } });
		 */
		wb.loadUrl(link);
		new SomeTask().execute();

		// Toast.makeText(getApplicationContext(),wb.getUrl(),2000).show();

		//

		/*
		 * head.setText(li1.get(0).getTitle()) ;
		 * content.setText(li1.get(0).body) ;
		 */

		comm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(New_display.this, Post_comment.class);
				i.putExtra("link", link);
				// i.putExtra("no_of_comments",results);
				startActivity(i);

			}
		});

	}

	private class SomeTask extends AsyncTask<String, Void, String> {
		// private ProgressDialog Dialog = new
		// ProgressDialog(MainActivity.this);

		ProgressDialog d;

		// String x="";

		// ArrayList <CommentFields> li = new ArrayList ();

		@Override
		protected void onPreExecute() {
			// Toast.makeText(getApplicationContext(), "abhinav", 2000).show() ;

			d = new ProgressDialog(New_display.this);

			d.setMessage("Loading");
			d.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// HttpGet request = new
			// HttpGet("http://freakkydevill.comlu.com/vhp_comments_count.php?foreign_key="+id);
			HttpGet request = new HttpGet(
					"http://obscure-escarpment-4510.herokuapp.com/comments_count/?foreign_key="
							+ link);
			try {
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(isr);
				results = reader.readLine();
				// this.x = results;

			} catch (Exception e) {

			}
			return results;
		}

		@Override
		protected void onPostExecute(String x) {

			d.dismiss();

			tvno.setText("(" + x + ")");

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth must be enabled to send", 2000).show();

		}

		if (resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), "Bluetooth has enabeled",
					2000).show();
			// make the socket

		}
	}

	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		final UUID MY_UUID = UUID
				.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

		public ConnectThread(String add) {
			// Use a temporary object that is later assigned to mmSocket,
			// because mmSocket is final
			BluetoothSocket tmp = null;
			mmDevice = mBluetoothAdapter.getRemoteDevice(add);
			Toast.makeText(getApplicationContext(),
					"xysxys" + mmDevice.getName(), 2000).show();
			// Get a BluetoothSocket to connect with the given BluetoothDevice
			try {
				// MY_UUID is the app's UUID string, also used by the server
				// code
				tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
			}
			mmSocket = tmp;
			if (mmSocket != null) {
				Toast.makeText(getApplicationContext(), "socket obtained ",
						2000).show();
			}

		}

		public void run() {
			// Cancel discovery because it will slow down the connection
			mBluetoothAdapter.cancelDiscovery();

			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mmSocket.connect();
				System.out.print("trt");

				// Toast.makeText(getApplicationContext(), "After connect",
				// 2000).show() ;

			} catch (Exception e) {

				// Toast.makeText(getApplicationContext(), "Error" +
				// e.toString(), 2000).show() ;
				// Unable to connect; close the socket and get out
				try {
					mmSocket.close();
				} catch (IOException Exception) {

					// Toast.makeText(getApplicationContext(),
					// "Error in closing socket" + e, 2000).show() ;

				}
				return;
			}

			// Do work to manage the connection (in a separate thread)
			manageConnectedSocket(mmSocket);
		}

		private void manageConnectedSocket(BluetoothSocket mmSocket2) {
			// TODO Auto-generated method stub

			String dogs = title + "`" + imgLink + "`" + link;
			ConnectedThread wr = new ConnectedThread(mmSocket2, handler);
			wr.write(dogs.getBytes());

		}

		/** Will cancel an in-progress connection, and close the socket */
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// get the bundle and extract data by key
			Bundle b = msg.getData();
			String key = b.getString("My Key");

		}
	};

}
