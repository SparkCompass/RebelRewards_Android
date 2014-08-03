package com.eis;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Registration extends Activity {
	
	
	HashMap<String,String> mprovider = new HashMap<String,String>();
	Button btnSave,btnUpdate;
	Spinner spinState,spinYear,spinContact,spinRebel,spinMobilePlatform,spinDay,spinMonth,spinYears,spinTshirt,spinMobileProvider;
	EditText edtFname,edtLname,edtAddress,edtAddress2,edtCity,edtZip,edtCell,edtEmail,edtPassword,edtCPassword;
	RadioGroup rbGender;
	ProgressDialog progressdialog;
	TextView txtRheading,txtEPheading;
	CheckBox chkUpdateOffers,chkEmailComm;
	List<CharSequence> rebels=new ArrayList<CharSequence>();
	String[] rebel;
	ArrayAdapter<CharSequence> roleAdapter,yearAdapter,prefcontAdapter,mobileAdapter,yearsAdapter,TshirtsizeAdapter,MobileProviderAdapter;
	SAXParserFactory spf;
	SAXParser sp;
	XMLReader xr;
	SharedPreferences spLogin;
	String state,statename,MProviderIDL,MProviderNameL,Mprovider;
	ArrayAdapter<String> dataAdapter ;
	ArrayList<HashMap<String, String>> MobileProviderlist = new ArrayList<HashMap<String,String>>();
	ArrayList<String>MproviderName=new ArrayList<String>();
	ArrayList<String>MproviderId=new ArrayList<String>();
	final ArrayList<String> listdata = new ArrayList<String>();
	final ArrayList<String> Mname = new ArrayList<String>();
	final ArrayList<String> MID = new ArrayList<String>();
	String MPID;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.registration);
	
	
	btnSave=(Button)findViewById(R.id.btnRegister);
	btnUpdate=(Button)findViewById(R.id.btnUpdate);
	txtEPheading=(TextView)findViewById(R.id.txtEPHeading);
	txtRheading=(TextView)findViewById(R.id.txtRHeading);
	edtFname=(EditText)findViewById(R.id.txtFirstname);
	edtLname=(EditText)findViewById(R.id.txtLastname);
	edtEmail=(EditText)findViewById(R.id.txtEmail);
	edtPassword=(EditText)findViewById(R.id.txtPassword);
	edtCPassword=(EditText)findViewById(R.id.txtCPassword);
	edtAddress=(EditText)findViewById(R.id.txtAddress);
	chkEmailComm=(CheckBox)findViewById(R.id.chkREmailComm);
	chkUpdateOffers=(CheckBox)findViewById(R.id.chkRUpdateoffers);
	
	spinRebel=(Spinner)findViewById(R.id.spinRebel);
	 rebel =  getResources().getStringArray(R.array.rebel_array);
     for(int i=0;i<rebel.length;i++) {
         rebels.add(rebel[i]);
     }
     roleAdapter = new ArrayAdapter<CharSequence>(this,
             android.R.layout.simple_spinner_item, rebels);
     roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinRebel.setAdapter(roleAdapter);
	
	
	spinMonth=(Spinner)findViewById(R.id.spinMonth);
	spinDay=(Spinner)findViewById(R.id.spinDay);
	spinYears=(Spinner)findViewById(R.id.spinYears);
	spinState=(Spinner)findViewById(R.id.spinState);
	edtZip=(EditText)findViewById(R.id.txtZip);
	edtCell=(EditText)findViewById(R.id.txtCell);
	edtAddress2=(EditText)findViewById(R.id.txtAddress2);
	edtCity=(EditText)findViewById(R.id.txtCity);
	
	spinTshirt=(Spinner)findViewById(R.id.spinTShirt);
	TshirtsizeAdapter=ArrayAdapter.createFromResource(this, R.array.tshirt_array, android.R.layout.simple_spinner_item);
	TshirtsizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinTshirt.setAdapter(TshirtsizeAdapter);
	
	spinMobileProvider=(Spinner)findViewById(R.id.spinMobileProvider);
	
	/*spinMobileProvider=(Spinner)findViewById(R.id.spinMobileProvider);
	//MobileProviderAdapter=ArrayAdapter(this,MobileProviderlist,android.R.layout.simple_spinner_item);
	ArrayAdapter < String > adapter = new ArrayAdapter < String > (this,
			android.R.layout.simple_list_item_1,Mname);
	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	spinMobileProvider.setAdapter(adapter);*/
	
	/*//MobileProviderAdapter=ArrayAdapter(this,MobileProviderlist,android.R.layout.simple_spinner_item);
	ArrayAdapter < String > adapter = new ArrayAdapter < String > (this,
			android.R.layout.simple_list_item_1, MobileProviderlist);*/
	/*SimpleAdapter adapter = new SimpleAdapter(
    		this,
    		MobileProviderlist,
    		android.R.layout.simple_spinner_item,
    		new String[] {"dayy","dayname"},
    		new int[] {}
    		);*/
	
	
	
	spinMobilePlatform=(Spinner)findViewById(R.id.spinMobilePlatform);
	mobileAdapter=ArrayAdapter.createFromResource(this, R.array.mobileplatform_array, android.R.layout.simple_spinner_item);
	mobileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinMobilePlatform.setAdapter(mobileAdapter);
	
	spinContact=(Spinner)findViewById(R.id.spinContact);
	prefcontAdapter=ArrayAdapter.createFromResource(this, R.array.contact_array, android.R.layout.simple_spinner_item);
	prefcontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinContact.setAdapter(prefcontAdapter);
	
	spinYears=(Spinner)findViewById(R.id.spinYears);
	yearsAdapter=ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
	yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinYears.setAdapter(yearsAdapter);
	
	spinYear=(Spinner)findViewById(R.id.spinYear);
	yearAdapter=ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
	yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinYear.setAdapter(yearAdapter);
	
	rbGender=(RadioGroup)findViewById(R.id.rbGender);
	
	spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
	
	//MobileProvider();
	
	if(!spLogin.getString("userId", "default").equals("default"))
	{
		FillData();
		btnSave.setVisibility(View.GONE);
		btnUpdate.setVisibility(View.VISIBLE);
		txtRheading.setVisibility(View.GONE);
		txtEPheading.setVisibility(View.VISIBLE);
		edtEmail.setEnabled(false);
	}
	else
	{
		btnSave.setVisibility(View.VISIBLE);
		btnUpdate.setVisibility(View.GONE);
		txtRheading.setVisibility(View.VISIBLE);
		txtEPheading.setVisibility(View.GONE);
		edtEmail.setEnabled(true);
	}
	
	
	btnSave.setOnClickListener(new OnClickListener() {
		
		public void onClick(final View v) {
			if(ValidatePage())
			{
				
			final String Url="http://130.74.17.62/RebelRewardsWebService/Service.asmx/Saveentry1"; 
			final ProgressDialog progressDialog=ProgressDialog.show(Registration.this, "Rebel Rewards", "Saving....",true,true);
			
			  new Thread(new Runnable() {

			  public void run() {
				  try
				  {
					  cd = new ConnectionDetector(getApplicationContext());
				    isInternetPresent = cd.isConnectingToInternet();
				    if (isInternetPresent) 
			          {
				    	Log.v("url_kiran","save");
					  SaveandUpdate(v,Url);
			          }
				    else
				    {
				    	Toast.makeText(Registration.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
				    }
				  }
				  catch(Exception ex)
				  {
				  
				  }
				  progressDialog.dismiss();
			  }
			  }).start();
			}
			 
		}	
	});
	
	btnUpdate.setOnClickListener(new OnClickListener() {
		
		public void onClick(final View v) {
			if(ValidatePage())
			{
				
			final String Url="http://130.74.17.62/RebelRewardsWebService/Service.asmx/UpdateProfile1"; 
			final ProgressDialog progressDialog=ProgressDialog.show(Registration.this, "Rebel Rewards", "Saving....",true,true);

			  new Thread(new Runnable() {

			  public void run() {
				  try
				  {
					  cd = new ConnectionDetector(getApplicationContext());
					    isInternetPresent = cd.isConnectingToInternet();
					    if (isInternetPresent) 
				          {
						  SaveandUpdate(v,Url);
				          }
					    else
					    {
					    	Toast.makeText(Registration.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
					    }
				  }
				  catch(Exception ex)
				  {
				  
				  }
				  progressDialog.dismiss();
			  }
			  }).start();
			}
			 
		}

		
	});
	
	
	try {
		
		
		//String[] Name=null, Id=null;
		//ArrayList<String> List = new ArrayList<String>();
		URL url = new URL(
				"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileServiceProviders");
	
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Table");
			//imglink=new TextView[nodeList.getLength()];
	           


				for (int i = 0; i < nodeList.getLength(); i++) {
					
					Node node = nodeList.item(i);

					//imglink[i] =(TextView)findViewById(R.id.imgAddLogin);
					
					Element providerID = (Element) node;
					NodeList providerIDlist = providerID.getElementsByTagName("id");
					Element providerIDelement = (Element) providerIDlist.item(0);
					providerIDlist = providerIDelement.getChildNodes();
					//AddImg[i].setTag(((Node) AdvImglist.item(0)).getNodeValue());
					 MProviderIDL = providerIDlist.item(0).getNodeValue();
					 //mprovider.put("MproviderID", MProviderIDL.toString());
					 //Name[i]=MProviderIDL;
					 MID.add(MProviderIDL);
					listdata.addAll(MID);
					MPID=MProviderIDL;
					
					Element providerName = (Element) node;
					NodeList providerNamelist = providerName.getElementsByTagName("value");
					Element prividerNameElement = (Element) providerNamelist.item(0);
					providerNamelist = prividerNameElement.getChildNodes();
					//AddImg[i].setTag(((Node) AdvImglist.item(0)).getNodeValue());
					MProviderNameL = providerNamelist.item(0).getNodeValue();
					//mprovider.put("MproviderName", MProviderNameL.toString());
					//Id[i]=MProviderNameL;
					Mname.add(MProviderNameL);
					listdata.addAll(Mname);
					
					//List.addAll(Name[i], Id[i]);
					
					
					/*int pos = spinMobileProvider.getSelectedItemPosition();
					String [] spinnerArrVal = getString(Mname);
					String value = spinnerArrVal[pos];*/
					
					
					//MobileProviderlist.add(mprovider);
					}
				
				spinMobileProvider=(Spinner)findViewById(R.id.spinMobileProvider);
				//MobileProviderAdapter=ArrayAdapter(this,MobileProviderlist,android.R.layout.simple_spinner_item);
				ArrayAdapter < String > adapter = new ArrayAdapter < String > (this,
						android.R.layout.simple_spinner_item,Mname);
				adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
				spinMobileProvider.setAdapter(adapter);
				
				
				
				
				
				spinMobileProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				    	
				    	listdata.addAll(MID);
				    	listdata.addAll(Mname);
				    	MPID=MID.get(pos);
				    	
				        //Object item = parent.getItemAtPosition(pos);
				    }
				    public void onNothingSelected(AdapterView<?> parent) {
				    }
				});
				
				//String s=listdata;
				//Log.v("ss", listdata);
				//Log.v("MMMM",listdata.toString());
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
	
	

	
		
	

	private boolean ValidatePage() {
		boolean email_flag = checkEmail(edtEmail.getText().toString().trim());
		boolean password_flag=checkPassword(edtPassword.getText().toString().trim());
		boolean cell_flag=isPhoneNumberValid(edtCell.getText().toString().trim());
		if(edtAddress.getText().toString().trim().equals("") || edtFname.getText().toString().trim().equals("") ||
			edtLname.getText().toString().trim().equals("") ||  edtPassword.getText().toString().trim().equals("")||
			edtCPassword.getText().toString().trim().equals("") || edtZip.getText().toString().trim().equals("") ||
			edtCell.getText().toString().trim().equals("") || edtCity.getText().toString().trim().equals("") ||
			edtEmail.getText().toString().trim().equals("") || String.valueOf(spinContact.getSelectedItemPosition()).equals("0") ||
			String.valueOf(spinDay.getSelectedItemPosition()).equals("0") || String.valueOf(spinMonth.getSelectedItemPosition()).equals("0") || String.valueOf(spinYears.getSelectedItemPosition()).equals("0") ||
			String.valueOf(spinRebel.getSelectedItemPosition()).equals("0") || String.valueOf(spinState.getSelectedItemPosition()).equals("0") ||
			String.valueOf(spinTshirt.getSelectedItemPosition()).equals("0") ||String.valueOf(spinMobileProvider.getSelectedItemPosition()).equals("-1") ||
			 String.valueOf(rbGender.indexOfChild(findViewById(rbGender.getCheckedRadioButtonId()))).equals("-1"))
		{
			showDialog(1);
			return false;
		}
		else if(String.valueOf(spinDay.getSelectedItemPosition()).equals("0") || String.valueOf(spinMonth.getSelectedItemPosition()).equals("0") || String.valueOf(spinYears.getSelectedItemPosition()).equals("0"))
		{
			showDialog(7);
			return false;
		}

		else if(edtZip.getText().toString().trim().length()!=5)
		{
			showDialog(2);
			return false;
		}

		//Phone Number Validation
		else if(edtCell.length()!=12)
		{
			showDialog(3);
			return false;
		}

		else if(!email_flag)
		{
			showDialog(4);
			return false;
		}
		
		//if both passwords are same then "password_Comparesion" value is '0'
		else if(!edtPassword.getText().toString().trim().equals(edtCPassword.getText().toString().trim()))
		{
			showDialog(5);
			return false;
		}
		else if(!password_flag)
		{
			showDialog(6);
			return false;
		}
		
		else if(!cell_flag){
			showDialog(3);
			return false;
		}
		//Final TRUE Case
		else
		{
			return true;
		}
		
	}	
	private void FillData() {
		edtFname.setText(spLogin.getString("fName", "default"));
		edtLname.setText(spLogin.getString("lName", "default"));
		edtAddress.setText(spLogin.getString("address", "default"));
		edtAddress2.setText(spLogin.getString("address2", "default"));
		edtCity.setText(spLogin.getString("city", "default"));
		edtZip.setText(spLogin.getString("zip", "default"));
		edtCell.setText(spLogin.getString("cellPhone", "default"));
		edtEmail.setText(spLogin.getString("email", "default"));
		edtPassword.setText(spLogin.getString("password", "default"));
		//edtCPassword.setText(spLogin.getString("password", "default"));
		//Integer state=Integer.valueOf(spLogin.getString("state", "State"));
		//String state=String.valueOf(spLogin.getString("state", "State"));
		
		spinState=(Spinner)findViewById(R.id.spinState);
		spinState.setSelection(Integer.valueOf(spLogin.getString("state", "State")));
		
		/*ArrayAdapter aa = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				statename);
		aa.setDropDownViewResource(
				   android.R.layout.simple_spinner_dropdown_item);
		        
		spinState.setAdapter(aa);
		//spinState.getSelectedItem().toString();
*/		
		
		if(spLogin.getString("role", "default").equals("Admin"))
		{
			CharSequence rebel = "Admin";
			roleAdapter.add(rebel);
			spinRebel.setSelection(7);
		}
		else
		{
			spinRebel.setSelection(roleAdapter.getPosition(spLogin.getString("role", "Select Rebel")));
		}
		spinYear.setSelection(yearAdapter.getPosition(spLogin.getString("currentYear", " ")));
		spinMobilePlatform.setSelection(mobileAdapter.getPosition(spLogin.getString("platform", "Mobile Platform (Optional)")));
		
		spinContact.setSelection(prefcontAdapter.getPosition(spLogin.getString("contactPref", "Prefered method of contact")));
		String[] birthday=spLogin.getString("birthDay", "Day").split("/");
		spinDay.setSelection(Integer.valueOf(birthday[0]));
		spinMonth.setSelection(Integer.valueOf(birthday[1]));
		spinYears.setSelection(yearsAdapter.getPosition(birthday[2].toString()));
		
		String Tshirtsize=spLogin.getString("TShirtsz"," " );
		if(Tshirtsize.startsWith("NA") || Tshirtsize.equals (null))
		{
			spinTshirt.setSelection(TshirtsizeAdapter.getPosition("0"));
		}
		else{
			spinTshirt.setSelection(TshirtsizeAdapter.getPosition(spLogin.getString("TShirtsz", " ")));
		}
		
		/*String Mprovider=spLogin.getString("MproviderName", " ");
		if(Mprovider.startsWith("NA"))
		{
			spinMobileProvider.setSelection(MobileProviderAdapter.getPosition("-1"));
		}
		else{
			
			
			//spinMobileProvider.setSelection(MobileProviderAdapter.getPosition(spLogin.getString("MproviderName", "0" )));
		}*/
		
		if(spLogin.getString("gender", "M").toString().trim().equals("M"))
		{
			RadioButton rbMale=(RadioButton)findViewById(R.id.rbMale);
			rbMale.setChecked(true);
		}
		else if(spLogin.getString("gender", "M").toString().trim().equals("F"))
		{
			RadioButton rbFeMale=(RadioButton)findViewById(R.id.rbFemale);
			rbFeMale.setChecked(true);
		}
		else if(spLogin.getString("gender","2").toString().trim().equals("")){
			
		}


		if(spLogin.getString("chkEmailComm", "N").toString().trim().equals("Y"))
			chkEmailComm.setChecked(true);
		
		if(spLogin.getString("chkUpdateOffer", "N").toString().trim().equals("Y"))
		chkUpdateOffers.setChecked(true);
	}
	
	private void SaveandUpdate(View v,String Url) {

		HttpPost postMethod=new HttpPost(Url); 
		List<NameValuePair> regParams=new ArrayList<NameValuePair>();
		 DefaultHttpClient hc=new DefaultHttpClient();  
         //ResponseHandler <String> res=new BasicResponseHandler(); 
		 Log.v("url_kiran","save");
		if(v.getId()==R.id.btnUpdate)
		{
			spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
			regParams.add(new BasicNameValuePair("_userid",spLogin.getString("userId", "0")));
		}
		regParams.add(new BasicNameValuePair("_fname",edtFname.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_lname",edtLname.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_email",edtEmail.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_password",edtPassword.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_address",edtAddress.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_rebeltype", Integer.toString(spinRebel.getSelectedItemPosition())));
		
		
		String month=getResources().getStringArray(R.array.month_values)[spinMonth.getSelectedItemPosition()];
		regParams.add(new BasicNameValuePair("_month",month));
		
		
		regParams.add(new BasicNameValuePair("_day", spinDay.getSelectedItem().toString()));
		regParams.add(new BasicNameValuePair("_years", spinYears.getSelectedItem().toString()));
		
		
		regParams.add(new BasicNameValuePair("_cell",edtCell.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_address2",edtAddress2.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_city",edtCity.getText().toString()));
	
		state=spinState.getSelectedItem().toString();
		if(state.startsWith("AL")){
			state="1";
		}
		else if(state.startsWith("AK")){
			state="2";
		}
		else if(state.startsWith("AZ")){
			state="3";
		}
		else if(state.startsWith("AR")){
			state="4";
		}
		else if(state.startsWith("CO")){
			state="5";
		}
		else if(state.startsWith("CA")){
			state="6";
		}
		else if(state.startsWith("DE")){
			state="7";
		}
		else if(state.startsWith("CT")){
			state="8";
		}
		else if(state.startsWith("GA")){
			state="9";
		}
		else if(state.startsWith("FL")){
			state="10";
		}
		else if(state.startsWith("IA")){
			state="11";
		}
		else if(state.startsWith("HI")){
			state="12";
		}
		else if(state.startsWith("IL")){
			state="13";
		}
		else if(state.startsWith("ID")){
			state="14";
		}
		else if(state.startsWith("KS")){
			state="15";
		}
		else if(state.startsWith("IN")){
			state="16";
		}
		else if(state.startsWith("LA")){
			state="17";
		}
		else if(state.startsWith("KY")){
			state="18";
		}
		else if(state.startsWith("MD")){
			state="19";
		}
		else if(state.startsWith("MA")){
			state="20";
		}
		else if(state.startsWith("MI")){
			state="21";
		}
		else if(state.startsWith("ME")){
			state="22";
		}
		else if(state.startsWith("MO")){
			state="23";
		}
		else if(state.startsWith("MN")){
			state="24";
		}
		else if(state.startsWith("MT")){
			state="25";
		}
		else if(state.startsWith("MS")){
			state="26";
		}
		else if(state.startsWith("ND")){
			state="27";
		}
		else if(state.startsWith("NC")){
			state="28";
		}
		else if(state.startsWith("NH")){
			state="29";
		}
		else if(state.startsWith("NE")){
			state="30";
		}
		else if(state.startsWith("NM")){
			state="31";
		}
		else if(state.startsWith("NJ")){
			state="32";
		}
		else if(state.startsWith("NY")){
			state="33";
		}
		else if(state.startsWith("NV")){
			state="34";
		}
		else if(state.startsWith("OK")){
			state="35";
		}
		else if(state.startsWith("OH")){
			state="36";
		}
		else if(state.startsWith("PA")){
			state="37";
		}
		else if(state.startsWith("OR")){
			state="38";
		}
		else if(state.startsWith("SC")){
			state="39";
		}
		else if(state.startsWith("RI")){
			state="40";
		}
		else if(state.startsWith("TN")){
			state="41";
		}
		else if(state.startsWith("SD")){
			state="42";
		}
		
		else if(state.startsWith("UT")){
			state="43";
		}
		else if(state.startsWith("TX")){
			state="44";
		}
		else if(state.startsWith("VT")){
			state="45";
		}
		else if(state.startsWith("VA")){
			state="46";
		}
		else if(state.startsWith("WI")){
			state="47";
		}
		else if(state.startsWith("WA")){
			state="48";
		}
		else if(state.startsWith("WY")){
			state="49";
		}
		else if(state.startsWith("WV")){
			state="50";
		}
		else if(state.startsWith("GU")){
			state="51";
		}
		else if(state.startsWith("AS")){
			state="52";
		}
		else if(state.startsWith("PW")){
			state="53";
		}
		else if(state.startsWith("MH")){
			state="54";
		}
		else if(state.startsWith("VI")){
			state="55";
		}
		else if(state.startsWith("PR")){
			state="56";
		}
		else if(state.startsWith("DC")){
			state="57";
		}
		else{
			state="0";
		}
		//endregion
		regParams.add(new BasicNameValuePair("_state", state));
		
		//regParams.add(new BasicNameValuePair("_state", Long.toString(spinRebel.getSelectedItemId())));
		regParams.add(new BasicNameValuePair("_zip",edtZip.getText().toString()));
		
		
		regParams.add(new BasicNameValuePair("_mobileplatform",spinMobilePlatform.getSelectedItem().toString()));
		
		
		regParams.add(new BasicNameValuePair("_contact", spinContact.getSelectedItem().toString()));
		
		
		regParams.add(new BasicNameValuePair("_currentyear", spinYear.getSelectedItem().toString()));
		regParams.add(new BasicNameValuePair("_tShirtSize",spinTshirt.getSelectedItem().toString()));
		
		/*Mprovider=spinMobileProvider.getSelectedItem().toString();
		if(Mprovider.startsWith("AllTel")){
			Mprovider="2";
		}
		else if(Mprovider.startsWith("AT&T")){
			Mprovider="1";
		}
		else if(Mprovider.startsWith("Boost Mobile")){
			Mprovider="3";
		}
		else if(Mprovider.startsWith("Cellular South")){
			Mprovider="11";
		}
		else if(Mprovider.startsWith("Cricket")){
			Mprovider="10";
		}
		else if(Mprovider.startsWith("Sprint NexTel")){
			Mprovider="4";
		}
		else if(Mprovider.startsWith("Sprint PCS")){
			Mprovider="5";
		}
		else if(Mprovider.startsWith("T-Mobile")){
			Mprovider="6";
		}
		else if(Mprovider.startsWith("Tracfone")){
			Mprovider="12";
		}
		else if(Mprovider.startsWith("US Cellular")){
			Mprovider="7";
		}
		else if(Mprovider.startsWith("Verizon")){
			Mprovider="8";
		}
		else if(Mprovider.startsWith("Virgin Mobile USA")){
			Mprovider="9";
		}
		else if(Mprovider.startsWith("C-Spire")){
			Mprovider="13";
		}
		else {
			Mprovider="0";
		}*/
		
		//String MId=getResources().[spinMobileProvider.getSelectedItemPosition()];
		
		regParams.add(new BasicNameValuePair("_mobileSserviceProviderId",MPID));
		
		int Index=rbGender.indexOfChild(findViewById(rbGender.getCheckedRadioButtonId()));
		
		if(Index==0)
			regParams.add(new BasicNameValuePair("_gender","M"));
		else if(Index==1)
			regParams.add(new BasicNameValuePair("_gender", "F"));
		else if(Index==2)
		    regParams.add(new BasicNameValuePair("_gender","2"));
		
		if(chkEmailComm.isChecked())
		regParams.add(new BasicNameValuePair("_uEmail","Y"));
		else
			regParams.add(new BasicNameValuePair("_uEmail","N"));
		
		if(chkUpdateOffers.isChecked())
			regParams.add(new BasicNameValuePair("_uOffer","Y"));
		else
			regParams.add(new BasicNameValuePair("_uOffer","N"));
		
		if(spLogin.getString("chkPushNotify", "N").equals("Y"))
			regParams.add(new BasicNameValuePair("_uPushNotify","Y"));
		else
			regParams.add(new BasicNameValuePair("_uPushNotify","N"));
		
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(regParams));
			HttpResponse response=hc.execute(postMethod);
			HttpEntity status = response.getEntity();
			String xmlString = EntityUtils.toString(status);
			StringReader read=new StringReader(xmlString);
	       XmlPullParser parse=Xml.newPullParser();
	       parse.setInput(read);
	       try{
	        	int event=parse.next();
	        	while(event!=XmlResourceParser.END_DOCUMENT){
	        		switch(event){
			        	case XmlResourceParser.START_DOCUMENT:
			        		
			        		break;
			        	case XmlResourceParser.START_TAG:
			        		
			        			        		
			        		break;
			        	case XmlResourceParser.TEXT:
			        		
			        		xmlString = parse.getText();	        		
			        		break;
			        	case XmlResourceParser.END_TAG:
			        		        		
			        		break;		        		
			    	}
	        		event=parse.next();
	        	}        	
	        	
	        }
	        catch(Exception e){}
	        
	        
		if(xmlString.trim().equalsIgnoreCase("1"))
		{
			
try {
				
				SharedPreferences splogin = getSharedPreferences("UserData", MODE_PRIVATE);
				SharedPreferences.Editor editor = splogin.edit();

				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/getUserDetails1?MailId="+edtEmail.getText().toString());
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
					//Log.v("User_Address",User_Addresss.toString());
					editor.putString("address", User_Addresss.toString());
					
					
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
					Log.v("User_CellPhone",User_CellPhonee.toString());
					
					Element User_Birthday = (Element) node;
					NodeList User_BirthdayList = User_Birthday.getElementsByTagName("User_Birthday");
					Element User_BirthdayElement = (Element) User_BirthdayList.item(0);
					User_BirthdayList = User_BirthdayElement.getChildNodes();
					String User_Birthdayy = User_BirthdayList.item(0).getNodeValue();
					editor.putString("birthDay", User_Birthdayy.toString());
					Log.v("User_Birthday",User_Birthdayy.toString());
					
					Element User_Gender = (Element) node;
					NodeList User_GenderList = User_Gender.getElementsByTagName("User_Gender");
					Element User_GenderElement = (Element) User_GenderList.item(0);
					User_GenderList = User_GenderElement.getChildNodes();
					String User_Genderr = User_GenderList.item(0).getNodeValue();
					//Password[i].setText("Password = "+ password);
					editor.putString("gender", User_Genderr.toString());
					//Log.v("User_Gender",User_Genderr.toString());
					
					
					
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
						
						Intent in=new Intent(Registration.this,TabBar.class);
						in.setAction(Intent.ACTION_MAIN);
						in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					    startActivity(in);
					    

					}
			}
			
			catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 	
			
		//Toast.makeText(getApplicationContext(), "Registration sucessfully completed", 3).show();
		//Intent in=new Intent(getApplicationContext(),TabBar.class);
		//in.setAction(Intent.ACTION_MAIN);
		//in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//startActivity(in);
		}
		else if(xmlString.trim().equalsIgnoreCase("2"))
		{
			
			showDialog(8);
			
		}
		else
		{
			showDialog(9);
			
		}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected Dialog onCreateDialog(int id) {
		switch(id){
		
		//First Name Required Field Validation 
    	case 1:
    		AlertDialog.Builder ab_fname =new AlertDialog.Builder(this);
    		ab_fname.setTitle("Rebel Rewards");
    		ab_fname.setMessage("Please complete all required fields");
    		ab_fname.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtFname.requestFocus();
				}});
    		AlertDialog ad_fname=ab_fname.create();
    		
    		return ad_fname;
    
    		
		case 2:
			AlertDialog.Builder ab_txtZip_length =new AlertDialog.Builder(this);
			ab_txtZip_length.setTitle("Rebel Rewards");
			ab_txtZip_length.setMessage("Please enter a valid zipcode.");
			ab_txtZip_length.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
				edtZip.requestFocus();
				}});
			AlertDialog ad_txtZip_length=ab_txtZip_length.create();
			
			return ad_txtZip_length;
			
			
			
		case 3:
			AlertDialog.Builder ab_cell_10digits =new AlertDialog.Builder(this);
			ab_cell_10digits.setTitle("Rebel Rewards");
			ab_cell_10digits.setMessage("Please Enter Valid Cell Phone Number(xxx-xxx-xxxx).");
			ab_cell_10digits.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
				edtCell.requestFocus();
				}});
			AlertDialog ad_cell_10digits=ab_cell_10digits.create();
			
			return ad_cell_10digits;
			
			//Gender  Required Field Validation
		case 4:
			AlertDialog.Builder ab_Check_email =new AlertDialog.Builder(this);
			ab_Check_email.setTitle("Rebel Rewards");
			ab_Check_email.setMessage("Please Enter Email like name@domain.com");
			ab_Check_email.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
				edtEmail.requestFocus();
				}});
			AlertDialog ad_Check_email=ab_Check_email.create();
			
			return ad_Check_email;
			
			//password & Confirm password  Field Validation
		case 5:
			AlertDialog.Builder ab_Check_Password =new AlertDialog.Builder(this);
			ab_Check_Password.setTitle("Rebel Rewards");
			ab_Check_Password.setMessage("Your password entries do not match. Please try again");
			ab_Check_Password.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
				edtCPassword.setText("");
				edtPassword.setText("");
				edtPassword.requestFocus();
				}});
			AlertDialog ad_Check_Password=ab_Check_Password.create();
			
			return ad_Check_Password;
		
		
		case 6:
			AlertDialog.Builder ab_Password_strength =new AlertDialog.Builder(this);
			ab_Password_strength.setTitle("Rebel Rewards");
			ab_Password_strength.setMessage(" Password must include at least six characters and contain a combination of letters and numbers.");
			ab_Password_strength.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
				edtCPassword.setText("");
					edtPassword.setText("");
				edtPassword.requestFocus();
				}});
			AlertDialog ad_Password_strength=ab_Password_strength.create();
			
			return ad_Password_strength;
			
			
		
		case 7:
			AlertDialog.Builder ab_spn_Day =new AlertDialog.Builder(Registration.this);
			ab_spn_Day.setTitle("Rebel Rewards");
			ab_spn_Day.setMessage("Please enter the month, day and year of your birthday.");
			ab_spn_Day.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					spinDay.requestFocus();
				}});
			AlertDialog ad_spn_Day=ab_spn_Day.create();
			
			return ad_spn_Day;
			
		case 8:
			AlertDialog.Builder ab_edtEmail =new AlertDialog.Builder(this);
			ab_edtEmail.setTitle("Rebel Rewards");
			ab_edtEmail.setMessage("This EmailId is already taken.");
			ab_edtEmail.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtEmail.requestFocus();
				}});
			AlertDialog ad_edtEmail_length=ab_edtEmail.create();
			
			return ad_edtEmail_length;
			
		case 9:
			AlertDialog.Builder ab_savingError =new AlertDialog.Builder(this);
			ab_savingError.setTitle("Rebel Rewards");
			ab_savingError.setMessage("Error while saving.Please contact administrator");
			ab_savingError.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					edtEmail.requestFocus();
				}});
			AlertDialog ad_savingErr_length=ab_savingError.create();
			
			return ad_savingErr_length;
			
			
		}
		return null;
	}
	
	class  MyHandler extends DefaultHandler
	{
		
		boolean is_MProvidername=false;
		boolean is_MproviderID=false;
		
		
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
			if(localName.equals("value")){
				is_MProvidername=true;
			}
			else if(localName.equals("id")){
				is_MproviderID=true;
			}
			
		}
		

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			
			super.characters(ch, start, length);
			if(is_MProvidername){
				
				MproviderName.add(new String(ch,start,length));
			}
			else if(is_MproviderID){
				
				MproviderId.add( new String(ch,start,length));
			}
			
			
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, name);
			if(localName.equals("value")){
				is_MProvidername=false;
			}
			else if(localName.equals("id")){
				is_MproviderID=false;
			}
			
		}
		
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}
		
	}
	
	
	
	public static boolean isPhoneNumberValid(String phoneNumber){ 
		boolean isValid = false; 
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"; 
		CharSequence inputStr = phoneNumber; 
		Pattern pattern = Pattern.compile(expression); 
		Matcher matcher = pattern.matcher(inputStr); 
		if(matcher.matches()){ 
		isValid = true; 
		} 
		return isValid; 
		}
	//Email pattern check method1
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			 "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );
	//Email pattern check method2
	private boolean checkEmail(String email) {
		 try{
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
		 }
		 catch (NullPointerException e) {
			return false;
		}
	}
	
	public final Pattern Password_Pattern=Pattern.compile(
			"((?=.*\\d)(?=.*[a-zA-Z]).{6,20})");
	private boolean checkPassword(String password){
		try
		{
			return Password_Pattern.matcher(password).matches();
		}
		catch(NullPointerException e){
			return false;
		}
	}
	}
	
	
