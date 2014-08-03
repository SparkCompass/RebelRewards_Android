package com.eis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Events extends Activity implements OnClickListener{
	
	String[] sportslist = { "View All Sports", "Baseball","Cross Country", "Football","Men's Basketball", "Men's Golf","Men's Tennis",
			"Rifle", "Soccer","Softball","Track and Field", "Volleyball","Women's Basketball", 
			 "Women's Golf",   "Women's Tennis"
			 };
	private Calendar _calendar;
	private int month, year;
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	Spinner sportspinner;
	private Button currentMonth;
	private ImageView prevMonth,prevMonth1;
	private ImageView nextMonth,nextMonth1;
	String sporttype,_sport;
	//private GridCellAdapter adapter;
	private GridView calendarView;
	private Button selectedDayMonthYearButton;
	String month_name;
	String monthname;
	String eventid,dayy,dayname,eventdetails,eventpoints,fbook;
	ListView listView;
	int years;
	SAXParserFactory spf;
	SharedPreferences spLogin;
	ArrayList eventslist;
	Button showalleventsbtn,featuredbtn;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        activity=this;
      	 loadimage=new LoadImage(activity.getApplicationContext());
       /* spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
		if(!spLogin.getString("userId", "default").equals("default"))*/
        try {    
        
			View viewToLoad = LayoutInflater.from(this.getParent()).inflate(R.layout.eventspage, null);    
     	    this.setContentView(viewToLoad); 
        
     	    ADDV();
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		//Log.v("tag", "Calendar Instance:= " + "Month: " + month + " " + "Year: " + year);
		
		//selectedDayMonthYearButton = (Button) this.findViewById(R.id.selectedDayMonthYear);
		//selectedDayMonthYearButton.setText("Selected: ");
		
		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);
		
		prevMonth1 = (ImageView) this.findViewById(R.id.prevMonthh);
		prevMonth1.setOnClickListener(this);
		
		showalleventsbtn = (Button) this.findViewById(R.id.button2);
		showalleventsbtn.setOnClickListener(this);
		
		featuredbtn = (Button) this.findViewById(R.id.button1);
		featuredbtn.setOnClickListener(this);
		
		
		currentMonth = (Button) this.findViewById(R.id.currentMonth);
		currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime()));
		
			 
			//String _sport = "Men's%20Golf";
			 String _monthh = Integer.toString(month);
			 int yearr= year;
			 //int years = year2;
				
				final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
							
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMEventsDetails?_sport=View%20All%20Sports"+"&_month="+_monthh+"&_Year="+yearr);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");

				/** Assign textview array lenght by arraylist size */
				/*name = new TextView[nodeList.getLength()];
				website = new TextView[nodeList.getLength()];
				category = new TextView[nodeList.getLength()];*/

				for (int i = 0; i < nodeList.getLength(); i++) {
					//HashMap<String, String> map = new HashMap<String, String>();
					
			    	HashMap<String,String> map = new HashMap<String,String>();

					Node node = nodeList.item(i);

		        	//HashMap<String, String> map = new HashMap<String,String>();


					Element fstElmnt = (Element) node;
					NodeList nameList = fstElmnt.getElementsByTagName("Event_Id");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					eventid = nameList.item(0).getNodeValue();
					//Event_Id.setText("Event_id="+eventid.toString());
					//Log.v("name",eventid.toString());
					map.put("eventid",eventid.toString());

					
					NodeList websiteList = fstElmnt.getElementsByTagName("Day");
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					dayy = websiteList.item(0).getNodeValue();
					//Day.setText("day="+dayy.toString());
					//Log.v("day",dayy.toString());
					map.put("dayy",dayy.toString());
									
					NodeList DayNameList = fstElmnt.getElementsByTagName("DayName");
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					dayname = DayNameList.item(0).getNodeValue();
					//DayName.setText("dayname="+dayname.toString());
					//Log.v("dayname",dayname.toString());
					map.put("dayname",dayname.toString());

					
					NodeList EventDetailsList = fstElmnt.getElementsByTagName("EventDetails");
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					eventdetails = EventDetailsList.item(0).getNodeValue();
					//EventDetails.setText("eventdetails="+eventdetails.toString());
					//Log.v("eventdetails",eventdetails.toString());
					map.put("eventdetails",eventdetails.toString());

					
					
					NodeList Event_PointsList = fstElmnt.getElementsByTagName("Event_Points");
					Element Event_PointsElement = (Element) Event_PointsList.item(0);
					Event_PointsList = Event_PointsElement.getChildNodes();
					eventpoints = Event_PointsList.item(0).getNodeValue();
					//Event_Points.setText("eventpoints="+eventpoints.toString());
					//Log.v("eventpoints",eventpoints.toString());
					map.put("eventpoints",eventpoints.toString());

					
					NodeList EventFacebookLinkList = fstElmnt.getElementsByTagName("EventFacebookLink");
					Element EventFacebookLinkElement = (Element) EventFacebookLinkList.item(0);
					EventFacebookLinkList = EventFacebookLinkElement.getChildNodes();
					fbook = EventFacebookLinkList.item(0).getNodeValue();
					//EventFacebookLink.setText("fbook="+fbook.toString());
					//Log.v("fbook",fbook.toString());
					map.put("fbook",fbook.toString());

					eventslist.add(map);
				}
					if(eventslist.isEmpty()){
						listView = (ListView)findViewById(R.id.listView1);
						listView.setVisibility(View.INVISIBLE);
						TextView noevent = (TextView)findViewById(R.id.noevent);
						noevent.setVisibility(View.VISIBLE);
						
					}
					else{
						SimpleAdapter adapter = new SimpleAdapter(
				        		this,
				        		eventslist,
				        		R.layout.eventlist,
				        		new String[] {"dayy","dayname","eventdetails","eventpoints","fbook"},
				        		new int[] {R.id.txtELDate,R.id.txtELDay, R.id.txtELdetails,R.id.textView1}
				        		);
						
						listView = (ListView)findViewById(R.id.listView1);
						listView.setVisibility(View.VISIBLE);
						TextView noevent = (TextView)findViewById(R.id.noevent);
						noevent.setVisibility(View.GONE);
						listView.setAdapter(adapter);  
						listView.setOnItemClickListener(new OnItemClickListener() {
				          public void onItemClick(AdapterView<?> parent, View view,
				              int position, long id) {
				 
				        	  Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(fbook));  
						      startActivity(in);
				 
				          }
						});
					}
					
					
					
					
					
					
				
				}
		 catch (Exception e) {
								System.out.println("XML Pasing Excpetion = " + e);
							}

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);
		
		nextMonth1 = (ImageView) this.findViewById(R.id.nextMonthh);
		nextMonth1.setOnClickListener(this);
		
		sportspinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter aa = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				sportslist);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
        
		sportspinner.setAdapter(aa);
		sportspinner.setOnItemSelectedListener(new OnItemSelectedListener() 
        {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int item = sportspinner.getSelectedItemPosition();
				
				sporttype=sportspinner.getSelectedItem().toString();
				String _monthh = Integer.toString(month);
				int yearr= year;
				//Log.v("sports",sporttype);
				if(sporttype == "View All Sports"){
				 _sport = "View%20All%20Sports";
				}
				else if(sporttype == "Baseball"){
				_sport = "Baseball";
				}
				else if(sporttype == "Men's Basketball"){
					 _sport = "Men's%20Basketball";
				}
				else if(sporttype == "Women's Basketball"){
					_sport = "Women's%20Basketball";
				}
				else if(sporttype == "Football"){
					 _sport = "Football";
				}
				else if(sporttype == "Men's Golf"){
					_sport = "Men's%20Golf";
				}
				else if(sporttype == "Women's Golf"){
					_sport = "Women's%20Golf";
				}
				else if(sporttype == "Rifle"){
					_sport = "Rifle";
				}
				else if(sporttype == "Soccer"){
					 _sport = "Soccer";
					}
				else if(sporttype == "Softball"){
					_sport = "Softball";
					}
				else if(sporttype == "Men's Tennis"){
						 _sport = "Men's%20Tennis";
					}
				else if(sporttype == "Women's Tennis"){
						_sport = "Women's%20Tennis";
					}
				else if(sporttype == "Track and Field"){
						 _sport = "Track%20and%20Field";
					}
				else if(sporttype == "Cross Country"){
						_sport = "Cross%20Country";
					}
				else if(sporttype == "Volleyball"){
						_sport = "Volleyball";
					}
				
				parsedata(_sport,month,year);
					
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
        	
        });
		
    }
      
    private void ADDV() {
    	try {
			
    		cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
			URL url1 = new URL(
			"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=4");

		DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
		DocumentBuilder db1 = dbf1.newDocumentBuilder();
		Document doc1 = db1.parse(new InputSource(url1.openStream()));
		doc1.getDocumentElement().normalize();

		NodeList nodeList1 = doc1.getElementsByTagName("Table");
		imglink=new TextView[nodeList1.getLength()];
           


			for (int i = 0; i < nodeList1.getLength(); i++) {

				Node node = nodeList1.item(i);

				//imglink[i] =(TextView)findViewById(R.id.imgAddLogin);
				
				Element AdvImg = (Element) node;
				NodeList AdvImglist = AdvImg.getElementsByTagName("AdImage");
				Element AdvImgelement = (Element) AdvImglist.item(0);
				AdvImglist = AdvImgelement.getChildNodes();
				//AddImg[i].setTag(((Node) AdvImglist.item(0)).getNodeValue());
				String AdvImgLink = AdvImglist.item(0).getNodeValue();
				ImageView img=(ImageView)findViewById(R.id.imgAddEvents);
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

	public void onClick(View v)
		{
       
		if (v == prevMonth)
				{
					if (month <= 1)
						{
							month = 12;
							year--;
						}
					else
						{
							month--;
						}
					sporttype=sportspinner.getSelectedItem().toString();
					//Log.v("sports",sporttype);
					if(sporttype == "View All Sports"){
					 _sport = "View%20All%20Sports";
					}
					else if(sporttype == "Baseball"){
					_sport = "Baseball";
					}
					else if(sporttype == "Men's Basketball"){
						 _sport = "Men's%20Basketball";
					}
					else if(sporttype == "Women's Basketball"){
						_sport = "Women's%20Basketball";
					}
					else if(sporttype == "Football"){
						 _sport = "Football";
					}
					else if(sporttype == "Men's Golf"){
						_sport = "Men's%20Golf";
					}
					else if(sporttype == "Women's Golf"){
						_sport = "Women's%20Golf";
					}
					else if(sporttype == "Rifle"){
						_sport = "Rifle";
					}
					else if(sporttype == "Soccer"){
						 _sport = "Soccer";
						}
					else if(sporttype == "Softball"){
						_sport = "Softball";
						}
					else if(sporttype == "Men's Tennis"){
							 _sport = "Men's%20Tennis";
						}
					else if(sporttype == "Women's Tennis"){
							_sport = "Women's%20Tennis";
						}
					else if(sporttype == "Track and Field"){
							 _sport = "Track%20and%20Field";
						}
					else if(sporttype == "Cross Country"){
							_sport = "Cross%20Country";
						}
					else if(sporttype == "Volleyball"){
							_sport = "Volleyball";
						}
					parsedata(_sport,month,year);
					//Log.v("tag", "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
					years = year;
					if(month == 1){
						monthname = "January";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 2){
						monthname = "February";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 3){
						monthname = "March";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 4){
						monthname = "April";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 5){
						monthname = "May";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 6){
						monthname = "June";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 7){
						monthname = "July";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 8){
						monthname = "August";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 9){
						monthname = "September";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 10){
						monthname = "October";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 11){
						monthname = "November";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 12){
						monthname = "December";
						currentMonth.setText(monthname+" "+year);
					}
					
					
				}
		
		if (v == prevMonth1)
		{
			if (month <= 1)
				{
					month = 12;
					year--;
				}
			else
				{
					month--;
				}
			sporttype=sportspinner.getSelectedItem().toString();
			//Log.v("sports",sporttype);
			if(sporttype == "View All Sports"){
			 _sport = "View%20All%20Sports";
			}
			else if(sporttype == "Baseball"){
			_sport = "Baseball";
			}
			else if(sporttype == "Men's Basketball"){
				 _sport = "Men's%20Basketball";
			}
			else if(sporttype == "Women's Basketball"){
				_sport = "Women's%20Basketball";
			}
			else if(sporttype == "Football"){
				 _sport = "Football";
			}
			else if(sporttype == "Men's Golf"){
				_sport = "Men's%20Golf";
			}
			else if(sporttype == "Women's Golf"){
				_sport = "Women's%20Golf";
			}
			else if(sporttype == "Rifle"){
				_sport = "Rifle";
			}
			else if(sporttype == "Soccer"){
				 _sport = "Soccer";
				}
			else if(sporttype == "Softball"){
				_sport = "Softball";
				}
			else if(sporttype == "Men's Tennis"){
					 _sport = "Men's%20Tennis";
				}
			else if(sporttype == "Women's Tennis"){
					_sport = "Women's%20Tennis";
				}
			else if(sporttype == "Track and Field"){
					 _sport = "Track%20and%20Field";
				}
			else if(sporttype == "Cross Country"){
					_sport = "Cross%20Country";
				}
			else if(sporttype == "Volleyball"){
					_sport = "Volleyball";
				}
			parsepreviousdata(_sport,month,year);
			//Log.v("tag", "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			years = year;
			if(month == 1){
				monthname = "January";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 2){
				monthname = "February";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 3){
				monthname = "March";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 4){
				monthname = "April";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 5){
				monthname = "May";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 6){
				monthname = "June";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 7){
				monthname = "July";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 8){
				monthname = "August";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 9){
				monthname = "September";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 10){
				monthname = "October";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 11){
				monthname = "November";
				currentMonth.setText(monthname+" "+year);
			}
			else if(month == 12){
				monthname = "December";
				currentMonth.setText(monthname+" "+year);
			}
			
			
		}
		
			
			if (v == nextMonth)
				{
					if (month > 11)
						{
							month = 1;
							year++;
						}
					else
						{
							month++;
						}
					sporttype=sportspinner.getSelectedItem().toString();
					//Log.v("sports",sporttype);
					if(sporttype == "View All Sports"){
					 _sport = "View%20All%20Sports";
					}
					else if(sporttype == "Baseball"){
					_sport = "Baseball";
					}
					else if(sporttype == "Men's Basketball"){
						 _sport = "Men's%20Basketball";
					}
					else if(sporttype == "Women's Basketball"){
						_sport = "Women's%20Basketball";
					}
					else if(sporttype == "Football"){
						 _sport = "Football";
					}
					else if(sporttype == "Men's Golf"){
						_sport = "Men's%20Golf";
					}
					else if(sporttype == "Women's Golf"){
						_sport = "Women's%20Golf";
					}
					else if(sporttype == "Rifle"){
						_sport = "Rifle";
					}
					else if(sporttype == "Soccer"){
						 _sport = "Soccer";
						}
					else if(sporttype == "Softball"){
						_sport = "Softball";
						}
					else if(sporttype == "Men's Tennis"){
							 _sport = "Men's%20Tennis";
						}
					else if(sporttype == "Women's Tennis"){
							_sport = "Women's%20Tennis";
						}
					else if(sporttype == "Track and Field"){
							 _sport = "Track%20and%20Field";
						}
					else if(sporttype == "Cross Country"){
							_sport = "Cross%20Country";
						}
					else if(sporttype == "Volleyball"){
							_sport = "Volleyball";
						}
					parsedata(_sport,month,year);
					//Log.v("tag", "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
					years = year;
					if(month == 1){
						monthname = "January";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 2){
						monthname = "February";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 3){
						monthname = "March";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 4){
						monthname = "April";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 5){
						monthname = "May";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 6){
						monthname = "June";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 7){
						monthname = "July";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 8){
						monthname = "August";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 9){
						monthname = "September";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 10){
						monthname = "October";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 11){
						monthname = "November";
						currentMonth.setText(monthname+" "+year);
					}
					else if(month == 12){
						monthname = "December";
						currentMonth.setText(monthname+" "+year);
					}
					
				}
			
			if (v == nextMonth1)
			{
				if (month > 11)
					{
						month = 1;
						year++;
					}
				else
					{
						month++;
					}
				sporttype=sportspinner.getSelectedItem().toString();
				//Log.v("sports",sporttype);
				if(sporttype == "View All Sports"){
				 _sport = "View%20All%20Sports";
				}
				else if(sporttype == "Baseball"){
				_sport = "Baseball";
				}
				else if(sporttype == "Men's Basketball"){
					 _sport = "Men's%20Basketball";
				}
				else if(sporttype == "Women's Basketball"){
					_sport = "Women's%20Basketball";
				}
				else if(sporttype == "Football"){
					 _sport = "Football";
				}
				else if(sporttype == "Men's Golf"){
					_sport = "Men's%20Golf";
				}
				else if(sporttype == "Women's Golf"){
					_sport = "Women's%20Golf";
				}
				else if(sporttype == "Rifle"){
					_sport = "Rifle";
				}
				else if(sporttype == "Soccer"){
					 _sport = "Soccer";
					}
				else if(sporttype == "Softball"){
					_sport = "Softball";
					}
				else if(sporttype == "Men's Tennis"){
						 _sport = "Men's%20Tennis";
					}
				else if(sporttype == "Women's Tennis"){
						_sport = "Women's%20Tennis";
					}
				else if(sporttype == "Track and Field"){
						 _sport = "Track%20and%20Field";
					}
				else if(sporttype == "Cross Country"){
						_sport = "Cross%20Country";
					}
				else if(sporttype == "Volleyball"){
						_sport = "Volleyball";
					}
				parsenextdata(_sport,month,year);
				//Log.v("tag", "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
				years = year;
				if(month == 1){
					monthname = "January";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 2){
					monthname = "February";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 3){
					monthname = "March";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 4){
					monthname = "April";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 5){
					monthname = "May";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 6){
					monthname = "June";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 7){
					monthname = "July";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 8){
					monthname = "August";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 9){
					monthname = "September";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 10){
					monthname = "October";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 11){
					monthname = "November";
					currentMonth.setText(monthname+" "+year);
				}
				else if(month == 12){
					monthname = "December";
					currentMonth.setText(monthname+" "+year);
				}
				
			}
			
			if (v == showalleventsbtn){
				cd = new ConnectionDetector(getApplicationContext());
			    isInternetPresent = cd.isConnectingToInternet();
			    if (isInternetPresent) 
		          {
				parseallevents(_sport,month,year);
				nextMonth.setVisibility(View.GONE);
				prevMonth.setVisibility(View.GONE);
				nextMonth1.setVisibility(View.VISIBLE);
				prevMonth1.setVisibility(View.VISIBLE);
				showalleventsbtn.setVisibility(View.GONE);
				featuredbtn.setVisibility(View.VISIBLE);
		          }
			    else
			    {
			    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
			    }
			}
			
			if (v == featuredbtn)
			{
				cd = new ConnectionDetector(getApplicationContext());
			    isInternetPresent = cd.isConnectingToInternet();
			    if (isInternetPresent) 
		          {
				parsedata(_sport,month,year);
				nextMonth.setVisibility(View.VISIBLE);
				prevMonth.setVisibility(View.VISIBLE);
				nextMonth1.setVisibility(View.GONE);
				prevMonth1.setVisibility(View.GONE);
				showalleventsbtn.setVisibility(View.VISIBLE);
				featuredbtn.setVisibility(View.GONE);
			
			 }
		    else
		    {
		    	Toast.makeText(this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
		    }
		}
			}
			
			

    

	
	private void parsedata(String _sport2, int month2, int year2) {
		// TODO Auto-generated method stub
		
		 try {
			 
			 String _sportt = _sport2;
			 String _monthh = Integer.toString(month2);
			 int yearr= year2;
			 //int years = year2;
				
				final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
							
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMEventsDetails1?_sport="+_sportt+"&_month="+_monthh+"&_Year="+yearr+"&Type=AllEvents");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");

				/** Assign textview array lenght by arraylist size */
				/*name = new TextView[nodeList.getLength()];
				website = new TextView[nodeList.getLength()];
				category = new TextView[nodeList.getLength()];*/

				for (int i = 0; i < nodeList.getLength(); i++) {
					//HashMap<String, String> map = new HashMap<String, String>();
					
			    	HashMap<String,String> map = new HashMap<String,String>();

					Node node = nodeList.item(i);

		        	//HashMap<String, String> map = new HashMap<String,String>();


					Element fstElmnt = (Element) node;
					NodeList nameList = fstElmnt.getElementsByTagName("Event_Id");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					eventid = nameList.item(0).getNodeValue();
					//Event_Id.setText("Event_id="+eventid.toString());
					//Log.v("name",eventid.toString());
					map.put("eventid",eventid.toString());

					
					NodeList websiteList = fstElmnt.getElementsByTagName("Day");
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					dayy = websiteList.item(0).getNodeValue();
					//Day.setText("day="+dayy.toString());
					//Log.v("day",dayy.toString());
					map.put("dayy",dayy.toString());
									
					NodeList DayNameList = fstElmnt.getElementsByTagName("DayName");
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					dayname = DayNameList.item(0).getNodeValue();
					//DayName.setText("dayname="+dayname.toString());
					//Log.v("dayname",dayname.toString());
					map.put("dayname",dayname.toString());

					
					NodeList EventDetailsList = fstElmnt.getElementsByTagName("EventDetails");
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					eventdetails = EventDetailsList.item(0).getNodeValue();
					//EventDetails.setText("eventdetails="+eventdetails.toString());
					//Log.v("eventdetails",eventdetails.toString());
					map.put("eventdetails",eventdetails.toString());

					
					
					NodeList Event_PointsList = fstElmnt.getElementsByTagName("Event_Points");
					Element Event_PointsElement = (Element) Event_PointsList.item(0);
					Event_PointsList = Event_PointsElement.getChildNodes();
					eventpoints = Event_PointsList.item(0).getNodeValue();
					//Event_Points.setText("eventpoints="+eventpoints.toString());
					//Log.v("eventpoints",eventpoints.toString());
					map.put("eventpoints",eventpoints.toString());

					
					NodeList EventFacebookLinkList = fstElmnt.getElementsByTagName("EventFacebookLink");
					Element EventFacebookLinkElement = (Element) EventFacebookLinkList.item(0);
					EventFacebookLinkList = EventFacebookLinkElement.getChildNodes();
					fbook = EventFacebookLinkList.item(0).getNodeValue();
					//EventFacebookLink.setText("fbook="+fbook.toString());
					//Log.v("fbook",fbook.toString());
					map.put("fbook",fbook.toString());

					eventslist.add(map);
					
					
					}
				if(eventslist.isEmpty()){
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.INVISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.VISIBLE);
					
				}
				else{
					SimpleAdapter adapter = new SimpleAdapter(
			        		this,
			        		eventslist,
			        		R.layout.eventlist,
			        		new String[] {"dayy","dayname","eventdetails","eventpoints","fbook"},
			        		new int[] {R.id.txtELDate,R.id.txtELDay, R.id.txtELdetails,R.id.textView1}
			        		);
					
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.VISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.GONE);
					listView.setAdapter(adapter);  
					listView.setOnItemClickListener(new OnItemClickListener() {
			          public void onItemClick(AdapterView<?> parent, View view,
			              int position, long id) {
			 
			        	  Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(fbook));  
					      startActivity(in);
			 
			          }
					});
				}
				
				}
		 catch (Exception e) {
								System.out.println("XML Pasing Excpetion = " + e);
							}
	}
	
	private void parsepreviousdata(String _sport2, int month2, int year2) {
		// TODO Auto-generated method stub
		
		 try {
			 
			 String _sportt = _sport2;
			 String _monthh = Integer.toString(month2);
			 int yearr= year2;
			 //int years = year2;
				
				final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
							
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMEventsDetails1?_sport="+_sportt+"&_month="+_monthh+"&_Year="+yearr+"&Type=FeaturedEvents");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");

				/** Assign textview array lenght by arraylist size */
				/*name = new TextView[nodeList.getLength()];
				website = new TextView[nodeList.getLength()];
				category = new TextView[nodeList.getLength()];*/

				for (int i = 0; i < nodeList.getLength(); i++) {
					//HashMap<String, String> map = new HashMap<String, String>();
					
			    	HashMap<String,String> map = new HashMap<String,String>();

					Node node = nodeList.item(i);

		        	//HashMap<String, String> map = new HashMap<String,String>();


					Element fstElmnt = (Element) node;
					NodeList nameList = fstElmnt.getElementsByTagName("Event_Id");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					eventid = nameList.item(0).getNodeValue();
					//Event_Id.setText("Event_id="+eventid.toString());
					//Log.v("name",eventid.toString());
					map.put("eventid",eventid.toString());

					
					NodeList websiteList = fstElmnt.getElementsByTagName("Day");
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					dayy = websiteList.item(0).getNodeValue();
					//Day.setText("day="+dayy.toString());
					//Log.v("day",dayy.toString());
					map.put("dayy",dayy.toString());
									
					NodeList DayNameList = fstElmnt.getElementsByTagName("DayName");
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					dayname = DayNameList.item(0).getNodeValue();
					//DayName.setText("dayname="+dayname.toString());
					//Log.v("dayname",dayname.toString());
					map.put("dayname",dayname.toString());

					
					NodeList EventDetailsList = fstElmnt.getElementsByTagName("EventDetails");
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					eventdetails = EventDetailsList.item(0).getNodeValue();
					//EventDetails.setText("eventdetails="+eventdetails.toString());
					//Log.v("eventdetails",eventdetails.toString());
					map.put("eventdetails",eventdetails.toString());

					
					
					NodeList Event_PointsList = fstElmnt.getElementsByTagName("Event_Points");
					Element Event_PointsElement = (Element) Event_PointsList.item(0);
					Event_PointsList = Event_PointsElement.getChildNodes();
					eventpoints = Event_PointsList.item(0).getNodeValue();
					//Event_Points.setText("eventpoints="+eventpoints.toString());
					//Log.v("eventpoints",eventpoints.toString());
					map.put("eventpoints",eventpoints.toString());

					
					NodeList EventFacebookLinkList = fstElmnt.getElementsByTagName("EventFacebookLink");
					Element EventFacebookLinkElement = (Element) EventFacebookLinkList.item(0);
					EventFacebookLinkList = EventFacebookLinkElement.getChildNodes();
					fbook = EventFacebookLinkList.item(0).getNodeValue();
					//EventFacebookLink.setText("fbook="+fbook.toString());
					//Log.v("fbook",fbook.toString());
					map.put("fbook",fbook.toString());

					eventslist.add(map);
					
					
					}
				if(eventslist.isEmpty()){
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.INVISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.VISIBLE);
					
				}
				else{
					SimpleAdapter adapter = new SimpleAdapter(
			        		this,
			        		eventslist,
			        		R.layout.eventlist,
			        		new String[] {"dayy","dayname","eventdetails","eventpoints","fbook"},
			        		new int[] {R.id.txtELDate,R.id.txtELDay, R.id.txtELdetails,R.id.textView1}
			        		);
					
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.VISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.GONE);
					listView.setAdapter(adapter);  
					listView.setOnItemClickListener(new OnItemClickListener() {
			          public void onItemClick(AdapterView<?> parent, View view,
			              int position, long id) {
			 
			        	  Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(fbook));  
					      startActivity(in);
			 
			          }
					});
				}
				
				}
		 catch (Exception e) {
								System.out.println("XML Pasing Excpetion = " + e);
							}
	}
	
	private void parsenextdata(String _sport2, int month2, int year2) {
		// TODO Auto-generated method stub
		
		 try {
			 
			 String _sportt = _sport2;
			 String _monthh = Integer.toString(month2);
			 int yearr= year2;
			 //int years = year2;
				
				final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
							
				URL url = new URL(
						"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMEventsDetails1?_sport="+_sportt+"&_month="+_monthh+"&_Year="+yearr+"&Type=FeaturedEvents");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("Table");

				/** Assign textview array lenght by arraylist size */
				/*name = new TextView[nodeList.getLength()];
				website = new TextView[nodeList.getLength()];
				category = new TextView[nodeList.getLength()];*/

				for (int i = 0; i < nodeList.getLength(); i++) {
					//HashMap<String, String> map = new HashMap<String, String>();
					
			    	HashMap<String,String> map = new HashMap<String,String>();

					Node node = nodeList.item(i);

		        	//HashMap<String, String> map = new HashMap<String,String>();


					Element fstElmnt = (Element) node;
					NodeList nameList = fstElmnt.getElementsByTagName("Event_Id");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					eventid = nameList.item(0).getNodeValue();
					//Event_Id.setText("Event_id="+eventid.toString());
					//Log.v("name",eventid.toString());
					map.put("eventid",eventid.toString());

					
					NodeList websiteList = fstElmnt.getElementsByTagName("Day");
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					dayy = websiteList.item(0).getNodeValue();
					//Day.setText("day="+dayy.toString());
					//Log.v("day",dayy.toString());
					map.put("dayy",dayy.toString());
									
					NodeList DayNameList = fstElmnt.getElementsByTagName("DayName");
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					dayname = DayNameList.item(0).getNodeValue();
					//DayName.setText("dayname="+dayname.toString());
					//Log.v("dayname",dayname.toString());
					map.put("dayname",dayname.toString());

					
					NodeList EventDetailsList = fstElmnt.getElementsByTagName("EventDetails");
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					eventdetails = EventDetailsList.item(0).getNodeValue();
					//EventDetails.setText("eventdetails="+eventdetails.toString());
					//Log.v("eventdetails",eventdetails.toString());
					map.put("eventdetails",eventdetails.toString());

					
					
					NodeList Event_PointsList = fstElmnt.getElementsByTagName("Event_Points");
					Element Event_PointsElement = (Element) Event_PointsList.item(0);
					Event_PointsList = Event_PointsElement.getChildNodes();
					eventpoints = Event_PointsList.item(0).getNodeValue();
					//Event_Points.setText("eventpoints="+eventpoints.toString());
					//Log.v("eventpoints",eventpoints.toString());
					map.put("eventpoints",eventpoints.toString());

					
					NodeList EventFacebookLinkList = fstElmnt.getElementsByTagName("EventFacebookLink");
					Element EventFacebookLinkElement = (Element) EventFacebookLinkList.item(0);
					EventFacebookLinkList = EventFacebookLinkElement.getChildNodes();
					fbook = EventFacebookLinkList.item(0).getNodeValue();
					//EventFacebookLink.setText("fbook="+fbook.toString());
					//Log.v("fbook",fbook.toString());
					map.put("fbook",fbook.toString());

					eventslist.add(map);
					
					
					}
				if(eventslist.isEmpty()){
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.INVISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.VISIBLE);
					
				}
				else{
					SimpleAdapter adapter = new SimpleAdapter(
			        		this,
			        		eventslist,
			        		R.layout.eventlist,
			        		new String[] {"dayy","dayname","eventdetails","eventpoints","fbook"},
			        		new int[] {R.id.txtELDate,R.id.txtELDay, R.id.txtELdetails,R.id.textView1}
			        		);
					
					listView = (ListView)findViewById(R.id.listView1);
					listView.setVisibility(View.VISIBLE);
					TextView noevent = (TextView)findViewById(R.id.noevent);
					noevent.setVisibility(View.GONE);
					listView.setAdapter(adapter);  
					listView.setOnItemClickListener(new OnItemClickListener() {
			          public void onItemClick(AdapterView<?> parent, View view,
			              int position, long id) {
			 
			        	  Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(fbook));  
					      startActivity(in);
			 
			          }
					});
				}
				
				}
		 catch (Exception e) {
								System.out.println("XML Pasing Excpetion = " + e);
							}
	}
				          
		 
		 private void parseallevents(String _sport2, int month2, int year2) {
				// TODO Auto-generated method stub
				
				 try {
					 
					 String _sportt = _sport2;
					 String _monthh = Integer.toString(month2);
					 int yearr= year2;
						
						final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
									
						URL url = new URL(
								"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMEventsDetails1?_sport="+_sportt+"&_month="+_monthh+"&_Year="+yearr+"&Type=FeaturedEvents");
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder db = dbf.newDocumentBuilder();
						Document doc = db.parse(new InputSource(url.openStream()));
						doc.getDocumentElement().normalize();

						NodeList nodeList = doc.getElementsByTagName("Table");

						/** Assign textview array lenght by arraylist size */
						/*name = new TextView[nodeList.getLength()];
						website = new TextView[nodeList.getLength()];
						category = new TextView[nodeList.getLength()];*/

						for (int i = 0; i < nodeList.getLength(); i++) {
							//HashMap<String, String> map = new HashMap<String, String>();
							
					    	HashMap<String,String> map = new HashMap<String,String>();

							Node node = nodeList.item(i);

				        	//HashMap<String, String> map = new HashMap<String,String>();


							Element fstElmnt = (Element) node;
							NodeList nameList = fstElmnt.getElementsByTagName("Event_Id");
							Element nameElement = (Element) nameList.item(0);
							nameList = nameElement.getChildNodes();
							eventid = nameList.item(0).getNodeValue();
							//Event_Id.setText("Event_id="+eventid.toString());
							//Log.v("name",eventid.toString());
							map.put("eventid",eventid.toString());

							
							NodeList websiteList = fstElmnt.getElementsByTagName("Day");
							Element websiteElement = (Element) websiteList.item(0);
							websiteList = websiteElement.getChildNodes();
							dayy = websiteList.item(0).getNodeValue();
							//Day.setText("day="+dayy.toString());
							//Log.v("day",dayy.toString());
							map.put("dayy",dayy.toString());
											
							NodeList DayNameList = fstElmnt.getElementsByTagName("DayName");
							Element DayNameElement = (Element) DayNameList.item(0);
							DayNameList = DayNameElement.getChildNodes();
							dayname = DayNameList.item(0).getNodeValue();
							//DayName.setText("dayname="+dayname.toString());
							//Log.v("dayname",dayname.toString());
							map.put("dayname",dayname.toString());

							
							NodeList EventDetailsList = fstElmnt.getElementsByTagName("EventDetails");
							Element EventDetailsElement = (Element) EventDetailsList.item(0);
							EventDetailsList = EventDetailsElement.getChildNodes();
							eventdetails = EventDetailsList.item(0).getNodeValue();
							//EventDetails.setText("eventdetails="+eventdetails.toString());
							//Log.v("eventdetails",eventdetails.toString());
							map.put("eventdetails",eventdetails.toString());

							
							
							NodeList Event_PointsList = fstElmnt.getElementsByTagName("Event_Points");
							Element Event_PointsElement = (Element) Event_PointsList.item(0);
							Event_PointsList = Event_PointsElement.getChildNodes();
							eventpoints = Event_PointsList.item(0).getNodeValue();
							//Event_Points.setText("eventpoints="+eventpoints.toString());
							//Log.v("eventpoints",eventpoints.toString());
							map.put("eventpoints",eventpoints.toString());

							
							NodeList EventFacebookLinkList = fstElmnt.getElementsByTagName("EventFacebookLink");
							Element EventFacebookLinkElement = (Element) EventFacebookLinkList.item(0);
							EventFacebookLinkList = EventFacebookLinkElement.getChildNodes();
							fbook = EventFacebookLinkList.item(0).getNodeValue();
							//EventFacebookLink.setText("fbook="+fbook.toString());
							//Log.v("fbook",fbook.toString());
							map.put("fbook",fbook.toString());

							eventslist.add(map);
							
							
							}
						if(eventslist.isEmpty()){
							listView = (ListView)findViewById(R.id.listView1);
							listView.setVisibility(View.INVISIBLE);
							TextView noevent = (TextView)findViewById(R.id.noevent);
							noevent.setVisibility(View.VISIBLE);
							
						}
						else{
							SimpleAdapter adapter = new SimpleAdapter(
					        		this,
					        		eventslist,
					        		R.layout.eventlist,
					        		new String[] {"dayy","dayname","eventdetails","eventpoints","fbook"},
					        		new int[] {R.id.txtELDate,R.id.txtELDay, R.id.txtELdetails,R.id.textView1}
					        		);
							
							listView = (ListView)findViewById(R.id.listView1);
							listView.setVisibility(View.VISIBLE);
							TextView noevent = (TextView)findViewById(R.id.noevent);
							noevent.setVisibility(View.GONE);
							listView.setAdapter(adapter);  
							listView.setOnItemClickListener(new OnItemClickListener() {
					          public void onItemClick(AdapterView<?> parent, View view,
					              int position, long id) {
					 
					        	  Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(fbook));  
							      startActivity(in);
					 
					          }
							});
						}
						
						}
				 catch (Exception e) {
										System.out.println("XML Pasing Excpetion = " + e);
									}
				        
					  

	
}
		 
}
