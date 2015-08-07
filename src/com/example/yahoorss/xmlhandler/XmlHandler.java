package com.example.yahoorss.xmlhandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XmlHandler extends DefaultHandler {
	private RssFeedStructure feedStr = new RssFeedStructure();
	private List<RssFeedStructure> rssList = new ArrayList<RssFeedStructure>();

	private int articlesAdded = 0;

	// Number of articles to download
	private static final int ARTICLES_LIMIT = 20;

	StringBuffer chars = new StringBuffer();

	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		chars = new StringBuffer();

		if (qName.equalsIgnoreCase("media:content"))

		{
			if (!atts.getValue("url").toString().equalsIgnoreCase("null")) {
				feedStr.setImgLink(atts.getValue("url").toString());
			} else {
				feedStr.setImgLink("");
			}
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("title")) {
			feedStr.setTitle(chars.toString());
		} else if (localName.equalsIgnoreCase("description")) {

			feedStr.setDescription(chars.toString());
		} else if (localName.equalsIgnoreCase("pubDate")) {

			feedStr.setPubDate(chars.toString());
		} else if (localName.equalsIgnoreCase("encoded")) {

			feedStr.setEncodedContent(chars.toString());
		} else if (qName.equalsIgnoreCase("media:content"))

		{

		} else if (localName.equalsIgnoreCase("link")) {
			feedStr.setLink(chars.toString());

		}
		if (localName.equalsIgnoreCase("item")) {
			rssList.add(feedStr);

			feedStr = new RssFeedStructure();
			articlesAdded++;
			if (articlesAdded >= ARTICLES_LIMIT) {
				throw new SAXException();
			}
		}
	}

	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}

	public List<RssFeedStructure> getLatestArticles(String feedUrl) {
		URL url = null;
		try {
			System.out.println(feedUrl);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			url = new URL(feedUrl);
			System.out.println(url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// System.out.println("Hello");
			connection.connect();
			Log.d("check", "Connected");
			// InputStream in = new
			// BufferedInputStream(connection.getInputStream());
			// connection.disconnect();
			System.out.println("Hello");
			xr.setContentHandler(this);
			Log.d("check", "Test Message");
			Log.d("check", "URL: " + connection.getInputStream());
			xr.parse(new InputSource(url.openStream()));
		} catch (Exception e) {
			Log.d("check", "error");

		}
		return rssList;
	}

}
