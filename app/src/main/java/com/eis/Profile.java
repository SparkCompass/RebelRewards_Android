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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	
	TextView txttempPwd,txtName,txtPassword, 
	txtRole, txtDevicePlatform,txtTier,txtEmail,
	txtUserId,txtPoints, txtCellPhone , 
	txtBirthday , txtGender,txtPreferedContact,
	txtCurrentYear,txtSaSignin,txtSaJoin,txtTshirtsz,txtMobileProvider;
	SharedPreferences spLogin;
	Button editbtn;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		try
		{
			
			try {
				
				cd = new ConnectionDetector(getApplicationContext());
			    isInternetPresent = cd.isConnectingToInternet();
			    if (isInternetPresent) 
		          {
				
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=7");
			
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
							ImageView img=(ImageView)findViewById(R.id.imgAddProfile);
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
			
			
			
		spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
		if(!spLogin.getString("userId", "default").equals("default"))
		{
			txtName = (TextView)findViewById(R.id.txtPName);
			txtName.setText(spLogin.getString("fName", " "));

			 txtEmail = (TextView)findViewById(R.id.txtPEmail);
			 txtEmail.setText(spLogin.getString("email", " "));
			 
			 txtUserId = (TextView)findViewById(R.id.txtPUserId);
			 txtUserId.setText(spLogin.getString("userId", " "));
			 
			 txtPassword = (TextView)findViewById(R.id.txtPPassword);
			 txtPassword.setText(spLogin.getString("password", " "));
			 
			 txtCellPhone=(TextView)findViewById(R.id.txtPCell);
			 txtCellPhone.setText(spLogin.getString("cellPhone", " "));
			 
			 txtRole=(TextView)findViewById(R.id.txtPRole);
			 txtRole.setText(spLogin.getString("role", " "));
			 
			 txtDevicePlatform = (TextView)findViewById(R.id.txtPDevice);
			 txtDevicePlatform.setText(spLogin.getString("platform", " "));
			 
			 txtBirthday = (TextView)findViewById(R.id.txtPBirthdy);
			 String[] birthday=spLogin.getString("birthDay", "Day").split("/");
			 String bDate=birthday[1].toString()+'/'+birthday[0].toString()+'/'+birthday[2].toString();
			 txtBirthday.setText(bDate);
			 
			 txtGender = (TextView)findViewById(R.id.txtPGender);
			 txtGender.setText(spLogin.getString("gender", " "));
				
			 txtPreferedContact = (TextView)findViewById(R.id.txtPPrfdContact);
			 txtPreferedContact.setText(spLogin.getString("contactPref", " "));

			 txtPoints = (TextView)findViewById(R.id.txtPPoints);
			 txtPoints.setText(spLogin.getString("points", " "));
			 
			 txtTier = (TextView)findViewById(R.id.txtPTier);
			 txtTier.setText(spLogin.getString("tier", " "));
			 
			 txtTshirtsz=(TextView)findViewById(R.id.txtPTShirtSize);
			 txtTshirtsz.setText(spLogin.getString("TShirtsz"," " ));
			 
			 txtMobileProvider=(TextView)findViewById(R.id.txtPMobileProvider);
			 txtMobileProvider.setText(spLogin.getString("MproviderName", " "));
			 
			 Button editbtn=(Button)findViewById(R.id.btnEdit);
			 editbtn.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						cd = new ConnectionDetector(getApplicationContext());
					    isInternetPresent = cd.isConnectingToInternet();
					    if (isInternetPresent) 
				          {
						SharedPreferences.Editor edit=spLogin.edit();
						edit.commit();
						Intent in=new Intent(getApplicationContext(),Registration.class);
						startActivity(in);
				          }
					    else
					    {
					    	Toast.makeText(Profile.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
					    }
					}});
			 
			    TextView lnkhistory=(TextView)findViewById(R.id.txtViewMyHistory);
				
				lnkhistory.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							cd = new ConnectionDetector(getApplicationContext());
						    isInternetPresent = cd.isConnectingToInternet();
						    if (isInternetPresent) 
					          {
							
							Intent in=new Intent(getApplicationContext(),MyHistory.class);
							startActivity(in);
					          }
						    else
						    {
						    	Toast.makeText(Profile.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
						    }
						}});
				
				TextView lnkrewards=(TextView)findViewById(R.id.txtViewMyrewards);
				
				lnkrewards.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							cd = new ConnectionDetector(getApplicationContext());
						    isInternetPresent = cd.isConnectingToInternet();
						    if (isInternetPresent) 
					          {
							Intent in=new Intent(getApplicationContext(),Viewmyrewards.class);
							startActivity(in);
					          }
						    else
						    {
						    	Toast.makeText(Profile.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
						    }
						}});
				


		}
		else
		{
			//setContentView(R.layout.signinalert);
		}

}catch (Exception e) {
	// TODO: handle exception
}
}
}