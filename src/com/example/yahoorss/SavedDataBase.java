package com.example.yahoorss;

import java.util.ArrayList;

import com.example.yahoorss.xmlhandler.RssFeedStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedDataBase {

	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "tittle";
	public static final String KEY_IMAGE_LINK = "image_link";
	public static final String KEY_LINK = "link";

	private static final String[] columns = new String[] { KEY_ID, KEY_TITLE,
			KEY_IMAGE_LINK, KEY_LINK };

	private static final String DATABASE_NAME = "Saved_News";

	private static final String DATABASE_TABLE = "News";
	private static final int DATABASE_VERSION = 1;

	private static class DBhelper extends SQLiteOpenHelper {
		public DBhelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
					+ " TEXT NOT NULL, " + KEY_IMAGE_LINK + " TEXT NOT NULL, "
					+ KEY_LINK + " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP IF TABLE EXISTS " + DATABASE_TABLE);

			onCreate(db);

		}
	}

	private Context ourcontext;
	private DBhelper ourhelper;
	private SQLiteDatabase ourdatabase;

	public SavedDataBase(Context c) {

		ourcontext = c;
	}

	public SavedDataBase open() throws SQLException {
		ourhelper = new DBhelper(ourcontext);
		ourdatabase = ourhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourhelper.close();
	}

	public long createEntry(String title, String image_link, String link) {
		// TODO Auto-generated method stub

		if (get_tittle(title)) {
			ContentValues cv = new ContentValues();
			cv.put(KEY_TITLE, title);
			cv.put(KEY_IMAGE_LINK, image_link);
			cv.put(KEY_LINK, link);
			return ourdatabase.insert(DATABASE_TABLE, null, cv);
		} else {
			return -999;
		}

	}

	public ArrayList<RssFeedStructure> getAllEntries() {

		String tittle, image_link, link;
		RssFeedStructure result = null;

		ArrayList<RssFeedStructure> send = new ArrayList();

		Cursor c = ourdatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		int irow = c.getColumnIndex(KEY_ID);
		int itittle = c.getColumnIndex(KEY_TITLE);
		int iimage_link = c.getColumnIndex(KEY_IMAGE_LINK);
		int ilink = c.getColumnIndex(KEY_LINK);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result = new RssFeedStructure();

			tittle = c.getString(itittle);
			image_link = c.getString(iimage_link);
			link = c.getString(ilink);

			result.setTitle(tittle);
			result.setImgLink(image_link);
			result.setLink(link);

			send.add(result);

		}
		return send;

	}

	public boolean get_tittle(String title) {

		String result = null;

		ArrayList<String> send = new ArrayList();

		Cursor c = ourdatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		int itittle = c.getColumnIndex(KEY_TITLE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			result = c.getString(itittle);
			send.add(result);

		}

		if (send.contains(title)) {
			return false;
		} else {
			return true;
		}

	}

}