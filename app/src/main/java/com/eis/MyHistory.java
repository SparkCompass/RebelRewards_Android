package com.eis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHistory  extends ListActivity {
	
	
	String UserID;
	ArrayList<String> al_mhdate=new ArrayList<String>();
	ArrayList<String> al_mhmonth=new ArrayList<String>();
	ArrayList<String> al_mhplace=new ArrayList<String>();
	ArrayList<String> al_mhpoints=new ArrayList<String>();
	
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	TextView lnkWebLnk;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState); 
	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	 setContentView(R.layout.viewmyhistory);
	 
	 
	SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
	 activity=this;
	 loadimage=new LoadImage(activity.getApplicationContext());
	 Addv();
	if(!spLogin.getString("userId","default").equals("default"))
	{
		UserID= spLogin.getString("userId", "default");
	 try{
     	
		
     	spf=SAXParserFactory.newInstance();
     	sp=spf.newSAXParser();
     	xr=sp.getXMLReader();
     	URL sourceUrl = new URL(
        "http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetHistory?UserId="+UserID.toString() );
     	MyHandler mh=new MyHandler();
     	xr.setContentHandler(mh);
     	
     	xr.parse(new InputSource(sourceUrl.openStream()));
    
     
     setListAdapter(new MyAdapter());
     //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
     
     lnkWebLnk=(TextView)findViewById(R.id.lnkHWebsite);
     lnkWebLnk.setText(Html.fromHtml("View more than your last 5 events.<a href='http://130.74.17.62/RebelRewards/SignIn.aspx'><b>Visit the Rebel Rewards website<b></a>"));
     lnkWebLnk.setMovementMethod(LinkMovementMethod.getInstance());
	 }
     catch(Exception e){}
	}
	}
	
	
	
	private void Addv() {
		
		try {
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=8");
		
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");
				imglink=new TextView[nodeList.getLength()];
		           


					for (int i = 0; i < nodeList.getLength(); i++) {

						Node node = nodeList.item(i);

						//imglink[i] =(TextView)findViewById(R.id.imgAddLogin);
						
						Element AdvImg = (Element) node;
						NodeList AdvImglist = AdvImg.getElementsByTagName("AdImage");
						Element AdvImgelement = (Element) AdvImglist.item(0);
						AdvImglist = AdvImgelement.getChildNodes();
						//AddImg[i].setTag(((Node) AdvImglist.item(0)).getNodeValue());
						String AdvImgLink = AdvImglist.item(0).getNodeValue();
						ImageView img=(ImageView)findViewById(R.id.imgAddHistory);
						img.setTag("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim());
						loadimage.DisplayImage("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim(), activity, img);
					}
		}
			
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	class MyAdapter extends BaseAdapter{

		public int getCount() {
			// TODO Auto-generated method stub
			return al_mhdate.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
			View v=li.inflate(R.layout.myhistorylist, null);
			
			TextView tv1=(TextView)v.findViewById(R.id.mhDate);
			tv1.setText( al_mhdate.get(arg0));
			
			TextView tv2=(TextView)v.findViewById(R.id.mhMonth);
			tv2.setText(al_mhmonth.get(arg0));
			
			
			TextView tv3=(TextView)v.findViewById(R.id.mhPlace);
			tv3.setText( al_mhplace.get(arg0));
			
			TextView tv4=(TextView)v.findViewById(R.id.mhPoints);
			tv4.setText(al_mhpoints.get(arg0));
			
			return v;
			
		}
    	
    }

	class MyHandler extends DefaultHandler{
    	
    	boolean is_mhdate=false;
    	boolean is_mhmonth=false;
    	boolean is_mhplace=false;
    	boolean is_mhpoints=false;
    	
    	 boolean mIsSegment = false;
			
 	    int mCurrentIndex = -1;
    	
    	
    	
    	
    	
    	@Override
    	public void startDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.startDocument();
    	}
    	
    	@Override
    	public void startElement(String uri, String localName, String name,
    			Attributes attributes) throws SAXException {
    		super.startElement(uri, localName, name, attributes);
    		
    		
    		
    		if (localName.equals("Table")) {
    	        mCurrentIndex++;
    	        al_mhdate.add("");
    	        al_mhmonth.add("");
    	        al_mhplace.add("");
    	        al_mhpoints.add("");
    		}
    		
    		else if(localName.equals("Day")){
    			is_mhdate=true;
    		}
    		else if(localName.equals("DayName")){
    			is_mhmonth=true;
    		}
    		else if(localName.equals("EventDetails")){
    			is_mhplace=true;
    		}
    		else if(localName.equals("Event_Points")){
    			is_mhpoints=true;
    		}
    		
    	}
    	
    	@Override
    	public void characters(char[] ch, int start, int length)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.characters(ch, start, length);
    		if(is_mhdate){
    			if (!mIsSegment) {
                    al_mhdate.set(mCurrentIndex, new String(ch, start, length));
                } else {
                	al_mhdate.set(mCurrentIndex,
                			al_mhdate.get(al_mhdate.size() - 1)
                                    + new String(ch, start, length));
                }
		    	 mIsSegment = true;
    		}
    		else if(is_mhmonth){
    			if (!mIsSegment) {
                    al_mhmonth.set(mCurrentIndex, new String(ch, start, length));
                } else {
                	al_mhmonth.set(mCurrentIndex,
                			al_mhmonth.get(al_mhmonth.size() - 1)
                                    + new String(ch, start, length));
                }
		    	 mIsSegment = true;
    		}
    		else if(is_mhplace){
    			if (!mIsSegment) {
                    al_mhplace.set(mCurrentIndex, new String(ch, start, length));
                } else {
                	al_mhplace.set(mCurrentIndex,
                			al_mhplace.get(al_mhplace.size() - 1)
                                    + new String(ch, start, length));
                }
		    	 mIsSegment = true;
    		}
    		else if(is_mhpoints){
    			if (!mIsSegment) {
                    al_mhpoints.set(mCurrentIndex, new String(ch, start, length));
                } else {
                	al_mhpoints.set(mCurrentIndex,
                			al_mhpoints.get(al_mhpoints.size() - 1)
                                    + new String(ch, start, length));
                }
		    	 mIsSegment = true;
    		}
    	}
    	
    	@Override
    	public void endElement(String uri, String localName, String name)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.endElement(uri, localName, name);
    		
    		if(localName.equals("Day")){
    			is_mhdate=false;
    			mIsSegment = false;
    		}
    		else if(localName.equals("DayName")){
    			is_mhmonth=false;
    			mIsSegment = false;
    		}
    		else if(localName.equals("EventDetails")){
    			is_mhplace=false;
    			mIsSegment = false;
    		}
    		else if(localName.equals("Event_Points")){
    			is_mhpoints=false;
    			mIsSegment = false;
    		}
    		
    	}
    	
    	@Override
    	public void endDocument() throws SAXException {
    		super.endDocument();
    	}
    }
	
}