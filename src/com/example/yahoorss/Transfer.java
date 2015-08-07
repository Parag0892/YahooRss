package com.example.yahoorss;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class Transfer {

}

class ConnectedThread extends Thread

{

	String buff = "";

	private final BluetoothSocket mmSocket;
	private final InputStream mmInStream;
	private final OutputStream mmOutStream;
	private final Handler mmHandel;

	public ConnectedThread(BluetoothSocket socket, Handler handel) {
		mmSocket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;

		// Get the input and output streams, using temp objects because
		// member streams are final
		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
		}

		mmInStream = tmpIn;
		mmOutStream = tmpOut;

		mmHandel = handel;
	}

	public void run() {

		byte[] buffer = new byte[1024];

		int bytes = 0; // bytes returned from read()

		System.out.println("before" + buffer.toString());

		// Keep listening to the InputStream until an exception occurs

		try {
			// Read from the InputStream

			bytes = mmInStream.read(buffer);

			// Send the obtained bytes to the UI activity

		} catch (IOException e) {

		}

		for (int i = 0; i < bytes; i++) {
			buff += (char) buffer[i];
		}

		System.out.println("after" + buff);

		// save_to_database (buff) ;

		Message msg = new Message();
		Bundle b = new Bundle();
		b.putString("My Key", "My Value: " + buff);
		msg.setData(b);
		// send message to the handler with the current message handler
		mmHandel.sendMessage(msg);

	}

	private void save_to_database(String buff2)

	{
		// TODO Auto-generated method stub
		String title, imglink, link;

		String temp;

		int index = 0;

		index = buff2.indexOf("`");

		title = buff2.substring(0, index);

		temp = buff2.substring(index + 1);

		index = temp.indexOf("`");

		imglink = temp.substring(0, index);

		link = temp.substring(index + 1);

		System.out.println("title==" + title);
		System.out.println("image==" + imglink);

		System.out.println("link==" + link);

	}

	/* Call this from the main activity to send data to the remote device */
	public void write(byte[] bytes) {
		try {
			mmOutStream.write(bytes);

		} catch (IOException e) {
		}
	}

	/* Call this from the main activity to shutdown the connection */
	public void cancel() {
		try {
			mmSocket.close();
		} catch (IOException e) {
		}
	}
}
