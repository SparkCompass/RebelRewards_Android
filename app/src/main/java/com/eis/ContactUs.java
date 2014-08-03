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
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends Activity {
	ArrayList<String> al_ctext=new ArrayList<String>();
	ArrayList<String> al_cmail=new ArrayList<String>();
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	TextView txtRules;
	ImageView mv;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactus);
		//mv=(ImageView)findViewById(R.id.imagecont);
		
		activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		 
		 cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
		         Adv();
		         Contact();
	          }
		    else
		    {
		    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
		    }
		 
	}
		
		private void Contact() {
		try{
			spf=SAXParserFactory.newInstance();
	     	sp=spf.newSAXParser();
	     	xr=sp.getXMLReader();
	     	URL sourceUrl = new URL(
	        "http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetContact");
	     	MyHandler mh=new MyHandler();
	     	xr.setContentHandler(mh);
	     	
	     	xr.parse(new InputSource(sourceUrl.openStream()));
		}
		
		catch(Exception ex){
			
		}
			
	}

		private void Adv() {
		// TODO Auto-generated method stub
			
			try
			{
			URL url = new URL(
			"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=12");

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
				ImageView img=(ImageView)findViewById(R.id.imgAddcont);
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

		
			
			
			
			
			
			
			
			
			
			
			
	     		
	
	
	
	
class MyHandler extends DefaultHandler{
    	
    	boolean is_cemail=false;
    	
    
    	
    	
    	@Override
    	public void startDocument() throws SAXException {
    		// TODO Auto-generated method stub
    		super.startDocument();
    	}
    	
    	@Override
    	public void startElement(String uri, String localName, String name,
    			Attributes attributes) throws SAXException {
    		super.startElement(uri, localName, name, attributes);
    		
    		if(localName.equals("Contact_Email")){
    			is_cemail=true;
    		}
    		
    		
    		
    	}
    	
    	@Override
    	public void characters(char[] ch, int start, int length)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.characters(ch, start, length);
    		if(is_cemail){
    			TextView txtRules=(TextView)findViewById(R.id.txtcemail);
    			txtRules.setText(ch, start, length);
    		}
    			
    		
    	}
    	
    	@Override
    	public void endElement(String uri, String localName, String name)
    			throws SAXException {
    		// TODO Auto-generated method stub
    		super.endElement(uri, localName, name);
    		
    		if(localName.equals("Contact_Email")){
    			is_cemail=false;
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
