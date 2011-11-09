package cs490.purdue.news;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;
import org.apache.http.util.ByteArrayBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class PurdueNewsActivity extends ListActivity {
	
	public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	
	//public ArrayList<article> articleList = new ArrayList<article>();
	
	ArrayAdapter<CharSequence> adapter;
	
	public Document dom;
	public ArrayList<article> articles = new ArrayList<article>();
		
	public void parseNews() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();		
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//parse using builder to get DOM representation of the XML file
			URL url = new URL("http://www.purdue.edu/newsroom/rss/FeaturedNews.xml");
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			dom = db.parse(is);
			
	
		}catch(ParserConfigurationException pce) {
			Log.d("Parser", "failed1");
		}catch(SAXException se) {
			Log.d("Parser", "failed2:" + se);
		}catch(IOException ioe) {
			Log.d("Parser", "failed3");
		}
		
		Log.d("Parser", "Obtained dom");
		if(dom == null)
		{
			Log.d("Parser", "dom is null bro");
		}
		Element root = dom.getDocumentElement();
		
		Log.d("Parser", "obtained document element");
		
		NodeList nl = root.getElementsByTagName("item");
		
		Log.d("Parser", "Obtained nl");
		
		
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0; i < nl.getLength(); i++) {
				
				//get the news info
				Element news_element = (Element)nl.item(i);
				
				//make a new artical element
				article new_article = getArticleInfo(news_element);
				
				//add to list
				articles.add(new_article);
			}
		}
		Log.d("Parser", "Done Parsing");
	}
	
	public article getArticleInfo(Element e) {
		String date = getTextValue(e, "pubDate");
		String title = getTextValue(e, "title");
		String desc =  getTextValue(e, "description");
		Log.d("desc", desc);
		String link = getTextValue(e, "link");
		
		article a = new article(date, title, desc, link);
		return a;
		
	}
	
	private static String getTextValue(Element ele, String tagName) {
		String textVal = "";
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			NodeList nnl = el.getChildNodes();
			for(int i = 0; i < nnl.getLength(); i++) {
				if(nnl.item(i).getNodeValue() == null ) 
					textVal += '\'';
				else
					textVal += nnl.item(i).getNodeValue();
				
			}
		}

		return textVal;
	}
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  String location = "http://www.purdue.edu/newsroom/rss/FeaturedNews.xml";
	  //String location = "http://www.physics.purdue.edu/academic_programs/courses/phys220/";
	  //DownloadFromUrl(location, "/sdcard/FeaturedNews.xml");
	  parseNews();
	  
	  String[] artArr = new String[articles.size()];
	  for(int i = 0; i < articles.size(); i++){
		  article temp = articles.get(i);
		  //HashMap<String, String> tempHash = new HashMap<String, String>(); 
		  //tempHash.put("title", temp.title);
		  //tempHash.put("date", temp.date);
		  //tempHash.put("story", temp.story);
		  //list.add(tempHash);
		  
		  artArr[i] = temp.title + '\n' + "----------------"+ '\n' + temp.date + '\n' + "----------------"+ '\n' + temp.story;
	  }
	  
	  
	  //SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.article_list_item, new String[] { "title", "date", "story" } , new int[] { }  );
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.article_list_item, artArr));
	  
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	  
	}
    
    static final String[] ARTICLES = new String[] { "art1", "ar2", "art3", "art4", "art5" };
    
    
    
}