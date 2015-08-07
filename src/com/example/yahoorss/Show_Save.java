package com.example.yahoorss;

import java.util.ArrayList;

import com.example.yahoorss.xmlhandler.RssFeedStructure;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Show_Save extends Activity {

	ArrayList<RssFeedStructure> show = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState)

	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_save);

		SavedDataBase de = new SavedDataBase(Show_Save.this);
		de.open();
		show = de.getAllEntries();
		de.close();

		TextView tv = (TextView) findViewById(R.id.show_save_text);

		String result = "";

		for (RssFeedStructure x : show) {
			result += "\t" + x.getTitle();
			result += "\t" + x.getTitle();
			result += "\t" + x.getTitle();
			result += "\n";

		}
		tv.setText(result);

	}

}
