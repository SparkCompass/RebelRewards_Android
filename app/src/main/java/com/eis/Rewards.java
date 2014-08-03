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
import android.widget.Toast;



public class Rewards extends ListActivity {
	
public LoadImage loadimage;
private Activity activity;
	ArrayList<String> al_tname=new ArrayList<String>();
	ArrayList<String> al_tpoints=new ArrayList<String>();
	ArrayList<String> al_tprize=new ArrayList<String>();
	ArrayList<String> al_timage=new ArrayList<String>();

	TextView imglink[];
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	SharedPreferences spLogin;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	setContentView(R.layout.rewards);
	TextView txtRpoints=(TextView)findViewById(R.id.txtRPoints);
	 try{
		 
		 activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		 
		cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
		 spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
			if(!spLogin.getString("userId","default").equals("default"))
			{
				txtRpoints.setVisibility(View.VISIBLE);
				txtRpoints.setText("You have ");
				txtRpoints.append(spLogin.getString("points", "0"));
				txtRpoints.append(" points");
			}
			else
			{
				//txtRpoints.setText(" ");
				txtRpoints.setVisibility(View.GONE);
			}
			
				
		    	Adv(); // calling ads
     	      spf=SAXParserFactory.newInstance();
     	      sp=spf.newSAXParser();
     	      xr=sp.getXMLReader();
     	      URL sourceUrl = new URL(
              "http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetRewards");
     	      MyHandler mh=new MyHandler();
     	      xr.setContentHandler(mh);
     	
     	      xr.parse(new InputSource(sourceUrl.openStream()));
	          }
		    else
		    {
		    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
		    }
     }
     catch(Exception e){
    	 
     }
     
     setListAdapter(new MyAdapter());
     getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
	}
	
	private void Adv() {
		 try {
				
			 
				
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=6");
			
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
							ImageView img=(ImageView)findViewById(R.id.imgAddRewards);
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
			return al_tname.size()-1;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		 
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
			View v=li.inflate(R.layout.reward, null);
			
			
			ImageView img=(ImageView)v.findViewById(R.id.imgRImage);
			img.setTag("http://130.74.17.62/RebelRewards"+al_timage.get(position).replace('~', ' ').trim());
			loadimage.DisplayImage("http://130.74.17.62/RebelRewards"+al_timage.get(position).replace('~', ' ').trim(), activity, img);
			TextView tv2=(TextView)v.findViewById(R.id.txtRTierName);
			tv2.setText(al_tname.get(position) + " - " + al_tprize.get(position));
			
			TextView tv3=(TextView)v.findViewById(R.id.txtRTierPoints);
			tv3.setText(al_tpoints.get(position)+" Points");
			
		
			
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
    	boolean is_image=false;
    	boolean is_tname=false;
    	boolean is_tpoints=false;
    	boolean is_tprize=false;
    
    	
    	
    	@Override
    	public void startDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.startDocument();
    	}
    	
    	@Override
    	public void startElement(String uri, String localName, String name,
    			Attributes attributes) throws SAXException {
    		super.startElement(uri, localName, name, attributes);
    		
    		if(localName.equals("Tier_Name")){
    			is_tname=true;
    		}
    		else if(localName.equals("Tier_Points")){
    			is_tpoints=true;
    		}
    		else if(localName.equals("Tier_Prize")){
    			is_tprize=true;
    		}
    		else if(localName.equals("Tier_Image")){
    			is_image=true;
    		}
    		
    		
    	}
    	
    	@Override
    	public void characters(char[] ch, int start, int length)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.characters(ch, start, length);
    		if(is_tname){
    			al_tname.add(new String(ch,start,length));
    		}
    		else if(is_tpoints){
    			al_tpoints.add(new String(ch,start,length));
    		}
    		else if(is_tprize){
    			al_tprize.add(new String(ch,start,length));
    		}
    		else if(is_image){
    			al_timage.add(new String(ch,start,length));
    		}
    		
    	}
    	
    	@Override
    	public void endElement(String uri, String localName, String name)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.endElement(uri, localName, name);
    		
    		if(localName.equals("Tier_Name")){
    			is_tname=false;
    		}
    		else if(localName.equals("Tier_Points")){
    			is_tpoints=false;
    		}
    		else if(localName.equals("Tier_Prize")){
    			is_tprize=false;
    		}
    		else if(localName.equals("Tier_Image")){
    			is_image=false;
    		}
    		
    	
    	}
    	
    	@Override
    	public void endDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.endDocument();
    	}
    	
    
    }
	@Override  
  	 public void onBackPressed() {  
  	        HomeGroup.group.back();  
  	 } 
}