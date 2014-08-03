package com.eis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCode extends Activity {
	TextView txtBarcode,txtName,txtUserid,txtPoints;
	ImageView imgBarcode;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
	
	try
	{
	activity=this;
	 loadimage=new LoadImage(activity.getApplicationContext());
	
	 
	    
	 if(!spLogin.getString("userId", "default").equals("default"))
		{
		   setContentView(R.layout.mycode);
		   Adv();
	 txtBarcode=(TextView)findViewById(R.id.txtMcBarcode);
	
	 Typeface font = Typeface.createFromAsset(this.getAssets(), 
     "fonts/EanP72Tt.ttf");
	 txtBarcode.setTypeface(font);
	 
	 String code= CalculateChecksum(spLogin.getString("userId", "default"));
	 EAN13CodeBuilder bb = new EAN13CodeBuilder(code);

	 txtBarcode.setText(bb.getCode());
	  
	 
	 
	 
	 //EAN13CodeBuilder bb = new EAN13CodeBuilder(code);

	 //txtBarcode.setText(bb.getCode());
	 
	 //Typeface 
	 //BarcodeFontFace = Typeface.createFromAsset(getAssets(),"Fonts/IDAutomationC128.ttf");
	 //BarcodeTextView.setTypeface(BarcodeFontFace);

	 ///initialize the Linear encoder to generate the encoded string

	 //LinearFontEncoder BCEnc = new LinearFontEncoder();

	 ///set the barcode text view to the BarcodeView ID

	 //TextView BarcodeTextView = (TextView) findViewById(R.id.BarcodeView);

	 ///set the text of the TextView to the encoded string

	 //BarcodeTextView.setText(String.format("%s",BCEnc.Code128(barcodedata,true)));
     
	 txtName=(TextView)findViewById(R.id.txtMcName);
	 txtName.setText(spLogin.getString("lName", " "));
	 txtName.append(" ");
	 txtName.append(spLogin.getString("fName", " "));
	 
	 txtPoints=(TextView)findViewById(R.id.txtMcPoints);
	 txtPoints.setText(spLogin.getString("points", "0"));
	 txtPoints.append(" Points");
	 
	 txtUserid=(TextView)findViewById(R.id.txtMcUserId);
	 txtUserid.setText("ID: ");
	 txtUserid.append(spLogin.getString("userId", "default"));
	 
	 TextView lnkHistory=(TextView)findViewById(R.id.lnkMcMyHistory);
		
	 	lnkHistory.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try
				{
				//View view = HomeGroup.group.getLocalActivityManager()
               // .startActivity("MyHistory", new Intent(getApplicationContext(),MyHistory.class)
               // .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
               // .getDecorView();
				
				//HomeGroup.group.replaceView(view);
					Intent in=new Intent(getApplicationContext(),MyHistory.class);
					startActivity(in);
				}
				catch(Exception ex)
				{
					return;
				}
				
			}});
	 
     
	}
	else
	{
		
		Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
		/*setContentView(R.layout.signinalert);
		TextView txtMycode=(TextView)findViewById(R.id.txtSAmycode);
		txtMycode.setVisibility(View.VISIBLE);
		TextView txtSaSignin=(TextView)findViewById(R.id.lnkSAsignin);
		TextView txtSaJoin=(TextView)findViewById(R.id.lnkSAJoinnow);
		txtSaSignin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent in=new Intent(getApplicationContext(),SignIn.class);
				startActivity(in);
				
			}});
		
		txtSaJoin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent in=new Intent(getApplicationContext(),Registration.class);
				startActivity(in);
				
			}});*/
		
	}
	}
	
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	}
	
	 private void Adv() {
		 URL url;
		try {
			cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
			url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=5");
		
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
						ImageView img=(ImageView)findViewById(R.id.imgAddMycode);
						img.setTag("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim());
						loadimage.DisplayImage("http://130.74.17.62/RebelRewards"+AdvImgLink.replace('~', ' ').trim(), activity, img);
					}
	          }
		    else
		    {
		    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
		    }
		} catch (MalformedURLException e) {
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

	public String CalculateChecksum(String code)
	    {
		 try
		 {
	        String sTemp = code.trim();
	        int iSum = 0;
	        int iDigit = 0;

	        // Calculate the checksum digit here.
	        for (int i = sTemp.length(); i >= 1; i--)
	        {
	            iDigit = Integer.parseInt(sTemp.substring(i - 1));
	            if (i % 2 == 0)
	            {	// odd
	                iSum += iDigit * 3;
	            }
	            else
	            {	// even
	                iSum += iDigit * 1;
	            }
	        }

	        int iCheckSum = (10 - (iSum % 10)) % 10;
	        String Code=code+String.valueOf(iCheckSum);
	       return Code;
		 }
		 catch(Exception e)
		 {
			 Log.e("My Code", e.getMessage().toString());
			 return code;
		 }
	    }
	
	 @Override  
	 public void onBackPressed() {  
	        HomeGroup.group.back();  
	 } 
}