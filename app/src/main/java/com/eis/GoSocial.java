package com.eis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoSocial extends Activity {
	
	private ArrayList<View> history;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gosocial);
		
	  try {	
		activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		 
		 TextView tv1 =(TextView)findViewById(R.id.lnkYoutube);
		//img1.setOnClickListener(new View.OnClickListener()
		   tv1.setOnClickListener(new OnClickListener() 
				
		{
		    public void onClick(View v){
		    	Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com"));
		        startActivity(in);
		    }
		});
		   
		   
		   
		 
				
		   cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
				
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=11");
			
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
							ImageView img=(ImageView)findViewById(R.id.imgAddGosocial);
							img.setTag("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim());
							loadimage.DisplayImage("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim(), activity, img);
						}
	          }
		    else
		    {
		    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
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
	
	
	
	
	
	
		
		/*ImageView img2 =(ImageView)findViewById(R.id.googleimg);
		img2.setOnClickListener(new OnClickListener(){
		    public void onClick(View v){
		    	Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.gmail.com"));  
		        startActivity(in);
		    }
		});
		
		ImageView img3 =(ImageView)findViewById(R.id.msnimg);
		img3.setOnClickListener(new OnClickListener(){
		    public void onClick(View v){
		   Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.msn.com/?st=1"));
		        
		        startActivity(in);
		    }
		});
		ImageView img4 =(ImageView)findViewById(R.id.yahooimg);
		img4.setOnClickListener(new OnClickListener(){
		    public void onClick(View v){
		    Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("http://in.yahoo.com/?p=us"));
		        //intent.setData(Uri.parse("http://in.yahoo.com/?p=us"));
		        startActivity(in);
		    }
		});
		ImageView img5 =(ImageView)findViewById(R.id.redditimg);
		img5.setOnClickListener(new OnClickListener(){
		    public void onClick(View v){
		    Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("http://digg.com")); 
		        //intent.setData(Uri.parse("http://digg.com"));
		        startActivity(in);
		    }
		});
		ImageView img6 =(ImageView)findViewById(R.id.squidooimg);
		img6.setOnClickListener(new OnClickListener(){
		    public void onClick(View v){
		    	Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.squidoo.com")); 
		        //intent.setData(Uri.parse("http://www.squidoo.com"));
		        startActivity(in);
		    }
		});
	}*/
	
	
	@SuppressWarnings("unused")
	private void replaceView(View view) {
		// TODO Auto-generated method stub
		 // Adds the old one to history
	    history.add(view);
	    // Changes this Groups View to the new View.
	    setContentView(view);
	}
	
	public void back() {  
	    if(history.size() > 0) {  
	        history.remove(history.size()-1);
	        if(history.size()<=0){
	            finish();
	        }else{
	            setContentView(history.get(history.size()-1));
	        }
	    }else {  
	        finish();  
	    }  
	}
	
	
}
