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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Viewmyrewards extends ListActivity {
	
public LoadImage loadimage;
String uid;
TextView title, date, rewards,txtRpoints;

private Activity activity;
	ArrayList<String> al_Day=new ArrayList<String>();
	ArrayList<String> al_DayName=new ArrayList<String>();
	ArrayList<String> al_Month=new ArrayList<String>();
	ArrayList<String> al_Prize=new ArrayList<String>();
	ArrayList<String> al_Tier_Image=new ArrayList<String>();
	ArrayList<String> al_Year=new ArrayList<String>();

	TextView imglink[];
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	SharedPreferences spLogin;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	activity=this;
	 loadimage=new LoadImage(activity.getApplicationContext());
	
	setContentView(R.layout.viewmyrewards);
	
	Rewards();
    Adv();	
	
	//TextView txtRpoints=(TextView)findViewById(R.id.txtRPoints);
	 
    
	 	
     	
	}
     	
     	
     	
     	
     	
    
	
	private void Adv() {
           try {
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=9");
		
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
						ImageView img=(ImageView)findViewById(R.id.imgAddVMRewards);
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

	private void Rewards() {
		try{
			 
		
			 spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
			  uid = spLogin.getString("userId","default");
						
			  	spf=SAXParserFactory.newInstance();
	     	sp=spf.newSAXParser();
	     	xr=sp.getXMLReader();
	     	URL sourceUrl = new URL(
	        "http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetUserPrizesHistory?UserId="+uid.toString());
	     	MyHandler mh=new MyHandler();
	     	xr.setContentHandler(mh);
	     	
	     	xr.parse(new InputSource(sourceUrl.openStream()));
	     	
	     	setListAdapter(new MyAdapter());
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
		 }
	     catch(Exception e){}
	     
	     
		
		
	}

	class MyAdapter extends BaseAdapter{

		public int getCount() {
			// TODO Auto-generated method stub
			return al_Day.size();
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
			View v=li.inflate(R.layout.viewmyrewardslist, null);
			
			
			ImageView img=(ImageView)v.findViewById(R.id.imageView1);
			img.setTag("http://130.74.17.62/RebelRewards"+al_Tier_Image.get(arg0).replace('~', ' ').trim());
			loadimage.DisplayImage("http://130.74.17.62/RebelRewards"+al_Tier_Image.get(arg0).replace('~', ' ').trim(), activity, img);
			TextView tv2=(TextView)v.findViewById(R.id.textView4);
			tv2.setText(al_Prize.get(arg0));
			
			TextView tv1=(TextView)v.findViewById(R.id.textView1);
			tv1.setText(al_Day.get(arg0)+"-"+al_Month.get(arg0)+"-"+al_Year.get(arg0));
			
			TextView tv3=(TextView)v.findViewById(R.id.textView2);
			tv3.setText(al_DayName.get(arg0));
			
			/*TextView tv4=(TextView)v.findViewById(R.id.textView3);
			tv4.setText(al_Month.get(arg0));*/
			
			/*TextView tv3=(TextView)v.findViewById(R.id.txtRTierPoints);
			tv3.setText(al_Day.get(arg0) + " - " + al_DayName.get(arg0)+ " - " + al_Month.get(arg0));*/
			
		
			
			return v;
			
		}

	
    	
    }
	/*private Bitmap DownloadImage(String URL) {
		// TODO Auto-generated method stub
		 Bitmap bitmap = null;
	        InputStream in = null;       
	        try {
	            in = OpenHttpConnection(URL);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	        return bitmap;  
	}
	 private InputStream OpenHttpConnection(String urlString)
	    throws IOException
	    {
	        InputStream in = null;
	        int response = -1;
	              
	        URL url = new URL(urlString);
	        URLConnection conn = url.openConnection();
	                
	        if (!(conn instanceof HttpURLConnection))                    
	            throw new IOException("Not an HTTP connection");
	       
	        try{
	            HttpURLConnection httpConn = (HttpURLConnection) conn;
	            httpConn.setAllowUserInteraction(false);
	            httpConn.setInstanceFollowRedirects(true);
	            httpConn.setRequestMethod("GET");
	            httpConn.connect();

	            response = httpConn.getResponseCode();                
	            if (response == HttpURLConnection.HTTP_OK) {
	                in = httpConn.getInputStream();                                
	            }                    
	        }
	        catch (Exception ex)
	        {
	            throw new IOException("Error connecting");           
	        }
	        return in;    
	    }*/

	class MyHandler extends DefaultHandler{
    	boolean is_Prize=false;
    	boolean is_Day=false;
    	boolean is_DayName=false;
    	boolean is_Month=false;
    	boolean is_Tier_Image=false;
    	boolean is_Year=false;
    
    	
    	
    	@Override
    	public void startDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.startDocument();
    	}
    	
    	@Override
    	public void startElement(String uri, String localName, String name,
    			Attributes attributes) throws SAXException {
    		super.startElement(uri, localName, name, attributes);
    		
    		if(localName.equals("Day")){
    			is_Day=true;
    		}
    		else if(localName.equals("DayName")){
    			is_DayName=true;
    		}
    		else if(localName.equals("Month")){
    			is_Month=true;
    		}
    		else if(localName.equals("Prize")){
    			is_Prize=true;
    		}
    		else if(localName.equals("Tier_Image")){
    			is_Tier_Image=true;
    		}
    		else if(localName.equals("Year")){
    			is_Year=true;
    		}
    		
    		
    	}
    	
    	@Override
    	public void characters(char[] ch, int start, int length)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.characters(ch, start, length);
    		if(is_Day){
    			al_Day.add(new String(ch,start,length));
    		}
    		else if(is_DayName){
    			al_DayName.add(new String(ch,start,length));
    		}
    		else if(is_Month){
    			al_Month.add(new String(ch,start,length));
    		}
    		else if(is_Prize){
    			al_Prize.add(new String(ch,start,length));
    		}
    		else if(is_Tier_Image){
    			al_Tier_Image.add(new String(ch,start,length));
    		}
    		else if(is_Year){
    			al_Year.add(new String(ch,start,length));
    		}
    		
    	}
    	
    	@Override
    	public void endElement(String uri, String localName, String name)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.endElement(uri, localName, name);
    		
    		if(localName.equals("Day")){
    			is_Day=false;
    		}
    		else if(localName.equals("DayName")){
    			is_DayName=false;
    		}
    		else if(localName.equals("Month")){
    			is_Month=false;
    		}
    		else if(localName.equals("Prize")){
    			is_Prize=false;
    		}
    		else if(localName.equals("Tier_Image")){
    			is_Tier_Image=false;
    		}
    		else if(localName.equals("Year")){
    			is_Year=false;
    		}
    		
    	
    	}
    	
    	@Override
    	public void endDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.endDocument();
    	}
    	
    
    }
	/*@Override  
  	 public void onBackPressed() {  
  	        HomeGroup.group.back();  
  	 } */
}