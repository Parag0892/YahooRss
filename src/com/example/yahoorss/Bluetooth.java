package com.example.yahoorss;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Bluetooth extends Activity implements OnItemClickListener {

	ArrayAdapter<String> listadapter;
	ListView listview;
	Button scan;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		unregisterReceiver(Receiver);
	}

	BluetoothAdapter btadapter;
	Set<BluetoothDevice> devicesarray;

	IntentFilter filter;
	BroadcastReceiver Receiver;

	ArrayList<String> pairdevice = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth);

		listview = (ListView) findViewById(R.id.available_device);
		listview.setOnItemClickListener(Bluetooth.this);
		scan = (Button) findViewById(R.id.bConnectNew);

		listadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 0);
		listview.setAdapter(listadapter);

		btadapter = BluetoothAdapter.getDefaultAdapter();

		if (btadapter == null) {
			Toast.makeText(getApplicationContext(), "No bluetooth detected",
					2000).show();
			finish();

		} else {
			if (!btadapter.isEnabled()) {
				turnonbt();
			}
		}

		getPairedDevices();

		init();

		startDiscovery();

	}

	private void startDiscovery() {
		// TODO Auto-generated method stub

		btadapter.cancelDiscovery();
		btadapter.startDiscovery();

	}

	private void turnonbt() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

		startActivityForResult(intent, 1);

	}

	private void init() {

		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

		Receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {

					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

					listadapter.add(device.getName() + "\n"
							+ device.getAddress());
				}

				if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

				}
				if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

				}
				if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
					if (btadapter.getState() == btadapter.STATE_OFF) {
						turnonbt();
					}
				}

			}

		};

		registerReceiver(Receiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);

		registerReceiver(Receiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		registerReceiver(Receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

		registerReceiver(Receiver, filter);
	}

	private void getPairedDevices()

	{
		// TODO Auto-generated method stub

		devicesarray = btadapter.getBondedDevices();

		if (devicesarray != null && devicesarray.size() > 0) {
			for (BluetoothDevice device : devicesarray) {
				pairdevice.add(device.getName() + "\n" + device.getAddress());
			}
		}
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
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
