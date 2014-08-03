package com.eis;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity {
	public LoadImage loadimage;
	private Activity activity;
	TextView txtForgotpwd,txtJoinnow,txtErrormsg;
	Button btnlogin,btnForPwd;
	EditText edtMailId,edtPwd,edtFPwd;
	ProgressDialog progressdialog;
	CheckBox chkRem;
	String UserID;
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	SharedPreferences spLogin;
	TableRow fpRow;
	TableRow jnRow;
	ImageView AddImg[];
	SharedPreferences splogin;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		//loading Ads image 
		 activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		 
		 
		 //declaring all text
		 //AddImg=(ImageView)findViewById(R.id.imageView1);
		chkRem=(CheckBox)findViewById(R.id.chkRemember);
		btnlogin=(Button)findViewById(R.id.btnLogin);
		edtMailId=(EditText)findViewById(R.id.edtEmailId);
		edtPwd=(EditText)findViewById(R.id.edtPwd);
		txtErrormsg=(TextView)findViewById(R.id.txtLErrormsg);
		txtJoinnow=(TextView)findViewById(R.id.lnkjoinNow);
		txtForgotpwd=(TextView)findViewById(R.id.lnkforgotPwd);
		btnForPwd=(Button)findViewById(R.id.btnFPSubmit);
		edtFPwd=(EditText)findViewById(R.id.edtFPMailid);
		spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
		
		
        splogin = getSharedPreferences("UserData", MODE_PRIVATE);

		if(!spLogin.getString("remUsername", "UnChecked").equals("UnChecked"))
		{
			edtMailId.setText(spLogin.getString("remUsername", "UnChecked"));
			chkRem.setChecked(true);
		}
		else
			chkRem.setChecked(false);
		
		//login button code.
		btnlogin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				//checkNetworkStatus();
				try
				{
					//Declare and getting network status.
				    cd = new ConnectionDetector(getApplicationContext());
				    isInternetPresent = cd.isConnectingToInternet();	
					
				 if (isInternetPresent==false) 
		          {
					showDialog(3);    	
				  }
				
				else if(edtMailId.getText().toString().trim().equals(""))
				{
					showDialog(1);
				}
				else if(edtPwd.getText().toString().trim().equals(""))
				{
					showDialog(2);
				}
				else
				{
			final ProgressDialog progressdialog=ProgressDialog.show(SignIn.this, "Rebel Rewards", "Signing In...",true,true);
			Handler handler=new Handler();
					Runnable r=new Runnable() {
						
						public void run() {
							try
							{
								loginWebService();
							}
							catch(Exception e)
							{
								txtErrormsg.setVisibility(View.VISIBLE);
								txtErrormsg.setText("Login Failed");
								 Log.e("SignIn", e.getMessage());
							}
							progressdialog.dismiss();
							
						}
					};
					
				handler.postDelayed(r, 1000);
			}
			}
			
			catch(Exception ex)
			{
				
			}

			}
		});
		
		
		//forgot password button 
		txtForgotpwd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				 fpRow=(TableRow)findViewById(R.id.tabForgotpwd);
				jnRow=(TableRow)findViewById(R.id.tabLJoinnow);
				txtErrormsg.setText(" ");
				txtErrormsg.setVisibility(View.GONE);
				if(fpRow.getVisibility()==View.GONE)
				{
					jnRow.setVisibility(View.GONE);
					fpRow.setVisibility(View.VISIBLE);
				}
				else if(fpRow.getVisibility()==View.VISIBLE)
				{
					jnRow.setVisibility(View.VISIBLE);
					fpRow.setVisibility(View.GONE);
				}
				
			}
		});
		
		
		//redirects to registration page for new account
		txtJoinnow.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
			    isInternetPresent = cd.isConnectingToInternet();
			    if (isInternetPresent) 
		          {
				Intent in=new Intent(getApplicationContext(),Registration.class);
				startActivity(in);
		          }
			    else
			    {
			    	Toast.makeText(SignIn.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
			    }
			}
		});
		
		edtFPwd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				txtErrormsg.setText(" ");
				txtErrormsg.setVisibility(View.GONE);
				
			}
		});
		
		edtMailId.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				txtErrormsg.setText(" ");
				txtErrormsg.setVisibility(View.GONE);
				
			}
		});
		
		edtPwd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				txtErrormsg.setText(" ");
				txtErrormsg.setVisibility(View.GONE);
				
			}
		});
		
		
		//requesting for forgot password
		btnForPwd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				final ProgressDialog progressdialog=ProgressDialog.show(SignIn.this, "Rebel Rewards", "Sending...");

				Handler handler=new Handler();
					Runnable r=new Runnable() {
						
						public void run() {
							try
							{
								
								
								if(edtFPwd.getText().toString().trim().equals(""))
								{
									showDialog(1);
								}
								else
								{
							    String _mailId=edtFPwd.getText().toString();
								WebService ws=new WebService();
								boolean validMail=ws.forgotPassword(_mailId);
								if(validMail)
								{
									jnRow.setVisibility(View.VISIBLE);
									fpRow.setVisibility(View.GONE);
									txtErrormsg.setVisibility(View.VISIBLE);
									txtErrormsg.setText("An Email has been sent to reset your password.");
								
								}
								else
								{//Declare and getting network status.
								    cd = new ConnectionDetector(getApplicationContext());
								    isInternetPresent = cd.isConnectingToInternet();
								    if (isInternetPresent==false) 
							          {
										showDialog(3);    	
									  }
								    else
								    {
								    	txtErrormsg.setVisibility(View.VISIBLE);
										txtErrormsg.setText("Invalid MailId.");
								    }
									
								}
								}
							}
							catch(Exception e)
							{
								
								//Log.e("ForgotPassword",e.getMessage());
							}
							progressdialog.dismiss();
						}
					};
					handler.postDelayed(r, 500);
				
					}
				
		});
		
		
		try {
			
			cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
			
			  URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=3");
		
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
						ImageView img=(ImageView)findViewById(R.id.imgAddLogin);
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
		
	
	protected void loginWebService() {

		
		String _mailId=edtMailId.getText().toString();
		
		
		String _passWord=edtPwd.getText().toString();
		if(chkRem.isChecked())
		{
		getSharedPreferences("UserData", MODE_PRIVATE).edit().putString("remUsername",edtMailId.getText().toString()).commit();
		}
		else
		{
			getSharedPreferences("UserData", MODE_PRIVATE).edit().putString("remUsername","UnChecked").commit();
		}
		WebService ws=new WebService();
		
		boolean loginResult=ws.checkUser(_mailId, _passWord);
		
		if(loginResult){
			try {
				
				SharedPreferences splogin = getSharedPreferences("UserData", MODE_PRIVATE);
				SharedPreferences.Editor editor = splogin.edit();

				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/getUserDetails1?MailId="+edtMailId.getText().toString());
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");
				
				for (int i = 0; i < nodeList.getLength(); i++) {

					Node node = nodeList.item(i);

									
					Element User_Fname = (Element) node;
					NodeList User_FnameList = User_Fname.getElementsByTagName("User_Fname");
					Element User_FnameElement = (Element) User_FnameList.item(0);
					User_FnameList = User_FnameElement.getChildNodes();
					String User_Fnamee = User_FnameList.item(0).getNodeValue();
					//EmailId[i].setText("EmailId = "+ emailid);
					editor.putString("fName", User_Fnamee.toString());
					//Log.v("User_Fname",User_Fnamee.toString());
					
					Element User_Lname = (Element) node;
					NodeList User_LnameList = User_Lname.getElementsByTagName("User_Lname");
					Element User_LnameElement = (Element) User_LnameList.item(0);
					User_LnameList = User_LnameElement.getChildNodes();
					String User_Lnamee = User_LnameList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("lName", User_Lnamee.toString());
					//Log.v("User_Lname",User_Lnamee.toString());
					
					Element User_Email = (Element) node;
					NodeList User_EmailList = User_Email.getElementsByTagName("User_Email");
					Element User_EmailElement = (Element) User_EmailList.item(0);
					User_EmailList = User_EmailElement.getChildNodes();
					String User_Emaill = User_EmailList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("email", User_Emaill.toString());
					//Log.v("User_Email",User_Emaill.toString());
					
					Element Role_Id = (Element) node;
					NodeList Role_IdList = Role_Id.getElementsByTagName("Role_Id");
					Element Role_IdElement = (Element) Role_IdList.item(0);
					Role_IdList = Role_IdElement.getChildNodes();
					String Role_Idd = Role_IdList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("roleId", Role_Idd.toString());
					//Log.v("Role_Id",Role_Idd.toString());
					
					Element User_Id = (Element) node;
					NodeList User_IdList = User_Id.getElementsByTagName("User_Id");
					Element User_IdElement = (Element) User_IdList.item(0);
					User_IdList = User_IdElement.getChildNodes();
					String User_Idd = User_IdList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("userId", User_Idd.toString());
					//Log.v("User_Id",User_Idd.toString());
					
					Element User_Address = (Element) node;
					NodeList User_AddressList = User_Address.getElementsByTagName("User_Address");
					Element User_AddressElement = (Element) User_AddressList.item(0);
					User_AddressList = User_AddressElement.getChildNodes();
					String User_Addresss = User_AddressList.item(0).getNodeValue();;
					//TeamName[i].setText("UserID = "+ uid);
					///Log.v("User_Address",User_Addresss.toString());
					editor.putString("address", User_Addresss.toString());
					
					/*Element User_Address2 = (Element) node;
					NodeList User_Address2List = User_Address2.getElementsByTagName("User_Address2");
					Element User_Address2Element = (Element)User_Address2List.item(0);
					User_Address2List = User_Address2Element.getChildNodes();
					String User_Address22 = User_Address2List.item(0).getNodeValue();;
					//TeamName[i].setText("UserID = "+ uid);
					Log.v("User_Address2",User_Address22.toString());
					editor.putString("address2", User_Address22.toString());*/
					
					Element User_City = (Element) node;
					NodeList User_CityList = User_City.getElementsByTagName("User_City");
					Element User_CityElement = (Element) User_CityList.item(0);
					User_CityList = User_CityElement.getChildNodes();
					String User_Cityy = User_CityList.item(0).getNodeValue();;
					//FirstName[i].setText("FirstName = "+ fname);
					editor.putString("city",User_Cityy.toString());
					//Log.v("User_City",User_Cityy.toString());

					Element User_State = (Element) node;
					NodeList User_StateList = User_State.getElementsByTagName("User_State");
					Element User_StateElement = (Element) User_StateList.item(0);
					User_StateList = User_StateElement.getChildNodes();
					String User_Statee = User_StateList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("state", User_Statee.toString());
					//Log.v("User_State",User_Statee.toString());
					
					Element User_Zipcode = (Element) node;
					NodeList User_ZipcodeList = User_Zipcode.getElementsByTagName("User_Zipcode");
					Element User_ZipcodeElement = (Element) User_ZipcodeList.item(0);
					User_ZipcodeList = User_ZipcodeElement.getChildNodes();
					String User_Zipcodee = User_ZipcodeList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("zip", User_Zipcodee.toString());
					//Log.v("User_Zipcode",User_Zipcodee.toString());
					
					Element TotalRewards_Points = (Element) node;
					NodeList TotalRewards_PointsList = TotalRewards_Points.getElementsByTagName("TotalRewards_Points");
					Element TotalRewards_PointsElement = (Element) TotalRewards_PointsList.item(0);
					TotalRewards_PointsList = TotalRewards_PointsElement.getChildNodes();
					String TotalRewards_Pointss = TotalRewards_PointsList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("points", TotalRewards_Pointss.toString());
					//Log.v("TotalRewards_Points",TotalRewards_Pointss.toString());
					
					
					Element User_Password = (Element) node;
					NodeList User_PasswordList = User_Password.getElementsByTagName("User_Password");
					Element User_PasswordElement = (Element) User_PasswordList.item(0);
					User_PasswordList = User_PasswordElement.getChildNodes();
					String User_Passwordd = User_PasswordList.item(0).getNodeValue();
					editor.putString("password", User_Passwordd.toString());
					//Log.v("User_Password",User_Passwordd.toString());
					
					Element Role_Name = (Element) node;
					NodeList Role_NameList = Role_Name.getElementsByTagName("Role_Name");
					Element Role_NameElement = (Element) Role_NameList.item(0);
					Role_NameList = Role_NameElement.getChildNodes();
					String Role_Namee = Role_NameList.item(0).getNodeValue();
					//EmailId[i].setText("EmailId = "+ emailid);
					editor.putString("role", Role_Namee.toString());
					//Log.v("Role_Name",Role_Namee.toString());
					
					Element User_DevicePlatform = (Element) node;
					NodeList User_DevicePlatformList = User_DevicePlatform.getElementsByTagName("User_DevicePlatform");
					Element User_DevicePlatformElement = (Element) User_DevicePlatformList.item(0);
					User_DevicePlatformList = User_DevicePlatformElement.getChildNodes();
					String User_DevicePlatformm = User_DevicePlatformList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("platform", User_DevicePlatformm.toString());
					//Log.v("User_DevicePlatform",User_DevicePlatformm.toString());
					
					Element User_CellPhone = (Element) node;
					NodeList User_CellPhoneList = User_CellPhone.getElementsByTagName("User_CellPhone");
					Element User_CellPhoneElement = (Element) User_CellPhoneList.item(0);
					User_CellPhoneList = User_CellPhoneElement.getChildNodes();
					String User_CellPhonee = User_CellPhoneList.item(0).getNodeValue();
					editor.putString("cellPhone", User_CellPhonee.toString());
					//Log.v("User_CellPhone",User_CellPhonee.toString());
					
					Element User_Birthday = (Element) node;
					NodeList User_BirthdayList = User_Birthday.getElementsByTagName("User_Birthday");
					Element User_BirthdayElement = (Element) User_BirthdayList.item(0);
					User_BirthdayList = User_BirthdayElement.getChildNodes();
					String User_Birthdayy = User_BirthdayList.item(0).getNodeValue();
					editor.putString("birthDay", User_Birthdayy.toString());
					//Log.v("User_Birthday",User_Birthdayy.toString());
					
					Element User_Gender = (Element) node;
					NodeList User_GenderList = User_Gender.getElementsByTagName("User_Gender");
					Element User_GenderElement = (Element) User_GenderList.item(0);
					User_GenderList = User_GenderElement.getChildNodes();
					String User_Genderr = User_GenderList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("gender", User_Genderr.toString());
					//Log.v("User_Gender",User_Genderr.toString());
					
					/*Element User_CurrentYear = (Element) node;
					NodeList User_CurrentYearList = User_CurrentYear.getElementsByTagName("User_CurrentYear");
					Element User_CurrentYearElement = (Element) User_CurrentYearList.item(0);
					User_CurrentYearList = User_CurrentYearElement.getChildNodes();
					String User_CurrentYearr = User_CurrentYearList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("currentYear", User_CurrentYearr.toString());
					Log.v("User_CurrentYear",User_CurrentYearr.toString());*/
					
					Element User_CommPreference = (Element) node;
					NodeList User_CommPreferenceList = User_CommPreference.getElementsByTagName("User_CommPreference");
					Element User_CommPreferenceElement = (Element) User_CommPreferenceList.item(0);
					User_CommPreferenceList = User_CommPreferenceElement.getChildNodes();
					String User_CommPreferencee = User_CommPreferenceList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("contactPref", User_CommPreferencee.toString());
					//Log.v("User_CommPreference",User_CommPreferencee.toString());


					
					Element User_TempPwd = (Element) node;
					NodeList User_TempPwdList = User_TempPwd.getElementsByTagName("User_TempPwd");
					Element User_TempPwdElement = (Element) User_TempPwdList.item(0);
					User_TempPwdList = User_TempPwdElement.getChildNodes();
					String User_TempPwdd = User_TempPwdList.item(0).getNodeValue();
					editor.putString("tempPwd", User_TempPwdd.toString());
					//Log.v("User_TempPwd",User_TempPwdd.toString());
					
					Element UpdateEmail = (Element) node;
					NodeList UpdateEmailList = UpdateEmail.getElementsByTagName("UpdateEmail");
					Element UpdateEmailElement = (Element) UpdateEmailList.item(0);
					UpdateEmailList = UpdateEmailElement.getChildNodes();
					String UpdateEmaill = UpdateEmailList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("chkEmailComm", UpdateEmaill.toString());
					//Log.v("UpdateEmail",UpdateEmaill.toString());
					
					Element UpdateOffer = (Element) node;
					NodeList UpdateOfferList = UpdateOffer.getElementsByTagName("UpdateOffer");
					Element UpdateOfferElement = (Element) UpdateOfferList.item(0);
					UpdateOfferList = UpdateOfferElement.getChildNodes();
					String UpdateOfferr = UpdateOfferList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("chkUpdateOffer", UpdateOfferr.toString());
					//Log.v("UpdateOffer",UpdateOfferr.toString());
					
					Element User_PushNotifications = (Element) node;
					NodeList User_PushNotificationsList = User_PushNotifications.getElementsByTagName("User_PushNotifications");
					Element User_PushNotificationsElement = (Element) User_PushNotificationsList.item(0);
					User_PushNotificationsList = User_PushNotificationsElement.getChildNodes();
					String User_PushNotificationss = User_PushNotificationsList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("chkPushNotify", User_PushNotificationss.toString());
					//Log.v("User_PushNotifications",User_PushNotificationss.toString());
					
					Element TotalRewards_Tier = (Element) node;
					NodeList TotalRewards_TierList = TotalRewards_Tier.getElementsByTagName("TotalRewards_Tier");
					Element TotalRewards_TierElement = (Element) TotalRewards_TierList.item(0);
					TotalRewards_TierList = TotalRewards_TierElement.getChildNodes();
					String TotalRewards_Tierr = TotalRewards_TierList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("tier", TotalRewards_Tierr.toString());
					//Log.v("TotalRewards_Tier",TotalRewards_Tierr.toString());
					
					Element TShirtSize = (Element) node;
					NodeList TShirtSizeList = TShirtSize.getElementsByTagName("T_ShirtSize");
					Element TShirtSizeElement = (Element) TShirtSizeList.item(0);
					TShirtSizeList = TShirtSizeElement.getChildNodes();
					String TShirtS = TShirtSizeList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("TShirtsz", TShirtS.toString());
					
					Element MproviderName = (Element) node;
					NodeList MproviderNameList = MproviderName.getElementsByTagName("MServiceProviderName");
					Element MproviderNameElement = (Element) MproviderNameList.item(0);
					MproviderNameList = MproviderNameElement.getChildNodes();
					String MproviderNm = MproviderNameList.item(0).getNodeValue();
					//LastName[i].setText("LastName = "+ lname);
					editor.putString("MproviderName", MproviderNm.toString());			
					editor.commit();
				    
				   /* Intent in1=new Intent(getApplicationContext(),TabBar.class);
					//clearCache();
					startActivity(in1);*/
					

			}
					NodeList nodeList11 = doc.getElementsByTagName("Table1");
					
					for (int j = 0; j < nodeList11.getLength(); j++) {

						Node nodee = nodeList11.item(j);

										
						Element Rank = (Element) nodee;
						NodeList RankList = Rank.getElementsByTagName("Rank");
						Element RankElement = (Element) RankList.item(0);
						RankList = RankElement.getChildNodes();
						String Rankk = RankList.item(0).getNodeValue();
						//EmailId[i].setText("EmailId = "+ emailid);
						editor.putString("Rank", Rankk.toString());
						//Log.v("Rank",Rankk.toString());
						
						Element TotalUsers = (Element) nodee;
						NodeList TotalUsersList = TotalUsers.getElementsByTagName("TotalUsers");
						Element TotalUsersElement = (Element)TotalUsersList.item(0);
						TotalUsersList = TotalUsersElement.getChildNodes();
						String TotalUserss = TotalUsersList.item(0).getNodeValue();
						//Password[i].setText("Password = "+ password);
						editor.putString("TotalUsers", TotalUserss.toString());
						//Log.v("TotalUsers",TotalUserss.toString());
						
						editor.commit();
						 Intent in1=new Intent(SignIn.this,TabBar.class);
							//clearCache();
							startActivity(in1);

					}
			}
			
			catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
			/*Intent in=new Intent(getApplicationContext(),TabBar.class);
			in.setAction(Intent.ACTION_MAIN);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);*/
		}
		else
		{
			txtErrormsg.setVisibility(View.VISIBLE);
			txtErrormsg.setText("Login Failed");
		}
	}
		
		
		
	
	/*class  MyHandler extends DefaultHandler
	{
		
		boolean is_Fname=false;
		boolean is_Lname=false;
		boolean is_Email=false;
		boolean is_Address=false;
		boolean is_Address2=false;
		boolean is_State=false;
		boolean is_City=false;
		boolean is_Zip=false;
		boolean is_roleid=false;
		boolean is_User_Id=false;
		boolean is_Gender=false;
		boolean is_TotalRewards_Points=false;
		boolean is_TotalRewards_Tier=false;
		boolean is_User_Password=false;
		boolean is_Role_Name=false;
		boolean is_User_DevicePlatform=false;
		boolean is_User_CellPhone=false;
		boolean is_User_Birthday=false;
		boolean is_User_Gender=false; //tv_User_CurrentYear
		boolean is_User_CurrentYear=false;
		boolean is_ContactPref=false;
		boolean is_TempPwd=false;
		boolean is_ChkEmailComm=false;
		boolean is_ChkUpdateOffer=false;
		boolean is_ChkPushNotify=false;
		boolean is_UserRank=false;
		boolean is_TotalUsers=false;
		
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
		}
		
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			
			
			super.startElement(uri, localName, name, attributes);
			if(localName.equals("User_Fname")){
				is_Fname=true;
			}
			else if(localName.equals("User_Lname")){
				is_Lname=true;
			}
			else if(localName.equals("User_Email")){
				is_Email=true;
			}
			else if(localName.equals("Role_Id")){
				is_roleid=true;
			}
			else if(localName.equals("User_Id")){
				is_User_Id=true;
			}
			else if(localName.equals("User_Address"))
			{
				is_Address=true;
			}
			else if(localName.equals("User_Address2"))
			{
				is_Address2=true;
			}
			else if(localName.equals("User_City"))
			{
				is_City=true;
			}
			else if(localName.equals("User_State"))
			{
				is_State=true;
			}
			else if(localName.equals("User_Zipcode"))
			{
				is_Zip=true;
			}
			else if(localName.equals("TotalRewards_Points")){
				is_TotalRewards_Points=true;
			}
			else if(localName.equals("TotalRewards_Tier")){
				is_TotalRewards_Tier=true;
			}
			else if(localName.equals("User_Password")){
				is_User_Password=true;
			}
			else if(localName.equals("Role_Name")){
				is_Role_Name=true;
			}
			else if(localName.equals("User_DevicePlatform")){
				is_User_DevicePlatform=true;
			}
			else if(localName.equals("User_CellPhone")){
				is_User_CellPhone=true;
			}
			else if(localName.equals("User_Birthday")){
				is_User_Birthday=true;
			}
			else if(localName.equals("User_Gender")){
				is_User_Gender=true;
			}//is_User_CurrentYear
			else if(localName.equals("User_CurrentYear")){
				is_User_CurrentYear=true;
			}
			else if(localName.equals("User_CommPreference")){
				is_ContactPref=true;
			}
			else if(localName.equals("User_TempPwd")){
				is_TempPwd=true;
			}
			else if(localName.equals("UpdateEmail")){
				is_ChkEmailComm=true;
			}
			else if(localName.equals("UpdateOffer")){
				is_ChkUpdateOffer=true;
			}
			else if(localName.equals("User_PushNotifications")){
				is_ChkPushNotify=true;
			}
			else if(localName.equals("Rank")){
				is_UserRank=true;
			}
			else if(localName.equals("TotalUsers")){
				is_TotalUsers=true;
			}
			
		}
		

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			SharedPreferences.Editor edit=spLogin.edit();
			super.characters(ch, start, length);
			if(is_Fname){
				//al_User_Id.add(new String(ch,start,length));
				
				edit.putString("fName", new String(ch,start,length));
			}
			else if(is_Lname){
				//al_TotalRewards_Points.add(new String(ch,start,length));
				
				edit.putString("lName", new String(ch,start,length));
			}
			else if(is_Email){
				//al_TotalRewards_Tier.add(new String(ch,start,length));
				
				edit.putString("email", new String(ch,start,length));
			}
			else if(is_roleid){
				//al_User_Id.add(new String(ch,start,length));
				
				edit.putString("roleId", new String(ch,start,length));
			}
			else if(is_User_Id){
				//al_User_Id.add(new String(ch,start,length));
				
				edit.putString("userId", new String(ch,start,length));
			}
			else if(is_Address){
				//al_User_Id.add(new String(ch,start,length));
				edit.putString("address", new String(ch,start,length));
			}
			else if(is_Address2){
				//al_User_Id.add(new String(ch,start,length));
				edit.putString("address2", new String(ch,start,length));
			}
			else if(is_State){
				//al_User_Id.add(new String(ch,start,length));
				edit.putString("state", new String(ch,start,length));
			}
			else if(is_City){
				//al_User_Id.add(new String(ch,start,length));
				edit.putString("city", new String(ch,start,length));
			}
			else if(is_Zip){
				//al_User_Id.add(new String(ch,start,length));
				edit.putString("zip", new String(ch,start,length));
			}
			else if(is_TotalRewards_Points){
				//al_TotalRewards_Points.add(new String(ch,start,length));
				
				edit.putString("points", new String(ch,start,length));
			}
			else if(is_TotalRewards_Tier){
				//al_TotalRewards_Tier.add(new String(ch,start,length));
				
				edit.putString("tier", new String(ch,start,length));
			}
			else if(is_User_Password){
				//al_User_Password.add(new String(ch,start,length));
				
				edit.putString("password", new String(ch,start,length));
			}
			else if(is_Role_Name){
				//al_Role_Name.add(new String(ch,start,length));
				
				edit.putString("role", new String(ch,start,length));
			}
			else if(is_User_DevicePlatform){
				//al_User_DevicePlatform.add(new String(ch,start,length));
				
				edit.putString("platform", new String(ch,start,length));
			}
			else if(is_User_CellPhone){
				//al_User_CellPhone.add(new String(ch,start,length));
				
				edit.putString("cellPhone", new String(ch,start,length));
			}
			else if(is_User_Birthday){
				//al_User_Birthday.add(new String(ch,start,length));
				
				edit.putString("birthDay", new String(ch,start,length));
			}
			else if(is_User_Gender){
				//al_User_Gender.add(new String(ch,start,length));
				
				edit.putString("gender", new String(ch,start,length));
			}
			else if(is_User_CurrentYear){
				//al_User_Gender.add(new String(ch,start,length));
				
				edit.putString("currentYear", new String(ch,start,length));
			}
			else if(is_ContactPref){
				//al_User_Gender.add(new String(ch,start,length));
				
				edit.putString("contactPref", new String(ch,start,length));
			}
			else if(is_TempPwd){
				edit.putString("tempPwd", new String(ch,start,length));
			}
			else if(is_ChkEmailComm){
				edit.putString("chkEmailComm", new String(ch,start,length));
			}
			else if(is_ChkUpdateOffer){
				edit.putString("chkUpdateOffer", new String(ch,start,length));
			}
			else if(is_ChkPushNotify){
				edit.putString("chkPushNotify", new String(ch,start,length));
			}
			
			else if(is_UserRank){
				edit.putString("Rank", new String(ch,start,length));
			}
			else if(is_TotalUsers){
				edit.putString("TotalUsers", new String(ch,start,length));
			}
			edit.commit();
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, name);
			if(localName.equals("User_Fname")){
				is_Fname=false;
			}
			else if(localName.equals("User_Lname")){
				is_Lname=false;
			}
			else if(localName.equals("User_Email")){
				is_Email=false;
			}
			else if(localName.equals("Role_Id")){
				is_roleid=false;
			}
			
			else if(localName.equals("User_Id")){
				is_User_Id=false;
			}
			else if(localName.equals("User_Address"))
			{
				is_Address=false;
			}
			else if(localName.equals("User_Address2"))
			{
				is_Address2=false;
			}
			else if(localName.equals("User_City"))
			{
				is_City=false;
			}
			else if(localName.equals("User_State"))
			{
				is_State=false;
			}
			else if(localName.equals("User_Zipcode"))
			{
				is_Zip=false;
			}
			else if(localName.equals("TotalRewards_Points")){
				is_TotalRewards_Points=false;
			}
			else if(localName.equals("TotalRewards_Tier")){
				is_TotalRewards_Tier=false;
			}
			
			else if(localName.equals("User_Password")){
				is_User_Password=false;
			}
			else if(localName.equals("Role_Name")){
				is_Role_Name=false;
			}
			else if(localName.equals("User_DevicePlatform")){
				is_User_DevicePlatform=false;
			}
			
			else if(localName.equals("User_CellPhone")){
				is_User_CellPhone=false;
			}
			else if(localName.equals("User_Birthday")){
				is_User_Birthday=false;
			}
			else if(localName.equals("User_Gender")){
				is_User_Gender=false;
			}
			else if(localName.equals("User_CurrentYear")){
				is_User_CurrentYear=false;
			}
			else if(localName.equals("User_CommPreference")){
				is_ContactPref=false;
			}
			else if(localName.equals("User_TempPwd")){
				is_TempPwd=false;
			}
			else if(localName.equals("UpdateEmail")){
				is_ChkEmailComm=false;
			}
			else if(localName.equals("UpdateOffer")){
				is_ChkUpdateOffer=false;
			}
			else if(localName.equals("User_PushNotifications")){
				is_ChkPushNotify=false;
			}
			else if(localName.equals("Rank")){
				is_UserRank=false;
			}
			else if(localName.equals("TotalUsers")){
				is_TotalUsers=false;
			}
			
		}
		
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}
		
	}*/
	
	
	
	public void  checkNetworkStatus(){

	    final ConnectivityManager connMgr = (ConnectivityManager)
	     this.getSystemService(Context.CONNECTIVITY_SERVICE);

	     final android.net.NetworkInfo wifi =
	     connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	     final android.net.NetworkInfo mobile =
	     connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	     if( wifi.isAvailable() ){

	     //Toast.makeText(this, "Wifi" , Toast.LENGTH_LONG).show();
	     }
	     else if( mobile.isAvailable() ){

	     //Toast.makeText(this, "Mobile 3G " , Toast.LENGTH_LONG).show();
	     }
	     else
	     {

	         Toast.makeText(this, "Please Check your Internet Connection and Try Again. " , Toast.LENGTH_LONG).show();
	     }

	}
	
	protected Dialog onCreateDialog(int id) {
		switch(id){
		
		//First Name Required Field Validation 
    	case 1:
    		AlertDialog.Builder ab_edtMailId =new AlertDialog.Builder(this);
    		ab_edtMailId.setTitle("Rebel Rewards");
    		ab_edtMailId.setMessage("Please Enter Your Email");
    		ab_edtMailId.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtMailId.requestFocus();
				}});
    		AlertDialog ad_edtMailId=ab_edtMailId.create();
    		
    		return ad_edtMailId;
    		
    	case 2:
    		AlertDialog.Builder ab_edtPwd =new AlertDialog.Builder(this);
    		ab_edtPwd.setTitle("Rebel Rewards");
    		ab_edtPwd.setMessage("Please Enter Your Password");
    		ab_edtPwd.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtPwd.requestFocus();
				}});
    		AlertDialog ad_edtPwd=ab_edtPwd.create();
    		
    		return ad_edtPwd;
    		
    	case 3:
    		AlertDialog.Builder ab_noConnection =new AlertDialog.Builder(this);
    		ab_noConnection.setTitle("Rebel Rewards");
    		ab_noConnection.setMessage("No Internet Connection.");
    		ab_noConnection.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtMailId.requestFocus();
				}});
    		AlertDialog ad_noConnection=ab_noConnection.create();
    		
    		return ad_noConnection;
		}
		return null;
	}
		
	}

