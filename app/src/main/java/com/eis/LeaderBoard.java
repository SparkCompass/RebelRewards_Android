package com.eis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LeaderBoard extends Activity {

	TextView ranktextview,ranktextview1,nametextview,nametextview1,pointstextview,pointstextview1,tiertextview,tiertextview1,textviewclicked;
	String rank,name,points,tier;
	ArrayList eventslist;
	ListView listView;
	String roleID,uid,rankcount,rankRole_Name,rankPoints,Rank_rank,uuid,totalusers,userpoints,Rank_rankk,rankcountt,usergroup;
	Button showallbtn,backbtn;
	TextView rankstextviews,group,pointss;
	TextView rankview, dash, pointsview, dash1;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lboard);
		ranktextview = (TextView)findViewById(R.id.txtLHRank); 
		ranktextview1 = (TextView)findViewById(R.id.txtLHRank1);
		nametextview = (TextView)findViewById(R.id.txtLHName);
		nametextview1 = (TextView)findViewById(R.id.txtLHName1);
		pointstextview = (TextView)findViewById(R.id.lnkLHPoints);
		pointstextview1 = (TextView)findViewById(R.id.lnkLHPoints1);
		tiertextview = (TextView)findViewById(R.id.lnkLHTier);
		tiertextview1 = (TextView)findViewById(R.id.lnkLHTier1);
		rankstextviews = (TextView)findViewById(R.id.textView11);
		pointss = (TextView)findViewById(R.id.textView13);
		group = (TextView)findViewById(R.id.textView4);
		showallbtn = (Button)findViewById(R.id.button2);
		backbtn = (Button)findViewById(R.id.button1);
		rankview = (TextView)findViewById(R.id.textView1);
		pointsview = (TextView)findViewById(R.id.textView12);
		
		activity=this;
		 loadimage=new LoadImage(activity.getApplicationContext());
		try{
			Adv();
			 
			 SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
				
			 if(!spLogin.getString("userId","default").equals("default"))
				{
					showallbtn.setVisibility(View.VISIBLE);
					backbtn.setVisibility(View.INVISIBLE);
					group.setVisibility(View.VISIBLE);
					rankstextviews.setVisibility(View.VISIBLE);
					rankview.setVisibility(View.VISIBLE);
					pointsview.setVisibility(View.VISIBLE);
					pointss.setVisibility(View.VISIBLE);
					
					roleID = spLogin.getString("roleId", "1");
					parselogindata(roleID);
					try{
						uid = spLogin.getString("userId","default");
						roleID = spLogin.getString("roleId", "1");
						userpoints = spLogin.getString("points", "default");
						usergroup = spLogin.getString("role", "default");
						group.setText(" - "+usergroup);
						pointss.setText( userpoints);
						
						URL url = new URL(
								"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMyRank?UserId="+uid+"&RoleId="+roleID);
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder db = dbf.newDocumentBuilder();
						Document doc = db.parse(new InputSource(url.openStream()));
						doc.getDocumentElement().normalize();
						NodeList nodeList = doc.getElementsByTagName("Table");
						
						for (int i = 0; i < nodeList.getLength(); i++) {
							Node node = nodeList.item(i);
							
							Element fstElmnt = (Element) node;
							NodeList nameList = fstElmnt.getElementsByTagName("count");
							Element nameElement = (Element) nameList.item(0);
							nameList = nameElement.getChildNodes();
							rankcount = nameList.item(0).getNodeValue();
							
							NodeList websiteList = fstElmnt.getElementsByTagName("Role_Name");
							Element websiteElement = (Element) websiteList.item(0);
							websiteList = websiteElement.getChildNodes();
							rankRole_Name = websiteList.item(0).getNodeValue();
							//group.setText(" - "+rankRole_Name);
							
							NodeList DayNameList = fstElmnt.getElementsByTagName("Points");
							Element DayNameElement = (Element) DayNameList.item(0);
							DayNameList = DayNameElement.getChildNodes();
							rankPoints = DayNameList.item(0).getNodeValue();
							
							
							NodeList EventDetailsList = fstElmnt.getElementsByTagName("Rank");
							Element EventDetailsElement = (Element) EventDetailsList.item(0);
							EventDetailsList = EventDetailsElement.getChildNodes();
							Rank_rank = EventDetailsList.item(0).getNodeValue();
							
							if((Rank_rank.equals(null))&&(rankcount.equals(null))){
								rankstextviews.setText(" - ");
							}
							else{
								rankstextviews.setText(Rank_rank+" out of "+rankcount);
							}
							
						}
						
					}
					catch(Exception e)
					{
						
					}
					
					nametextview = (TextView)findViewById(R.id.txtLHName);
					nametextview1 = (TextView)findViewById(R.id.txtLHName1);
					nametextview.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
							nametextview.setVisibility(View.GONE);
							nametextview1.setVisibility(View.VISIBLE);
							sortloginname(roleID);
						}});
					
					nametextview1.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
							nametextview.setVisibility(View.VISIBLE);
							nametextview1.setVisibility(View.GONE);
							sortloginnames(roleID);
						}});
					
					ranktextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortloginrank(roleID);
							ranktextview.setVisibility(View.GONE);
							ranktextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					ranktextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortloginranks(roleID);
							ranktextview1.setVisibility(View.GONE);
							ranktextview.setVisibility(View.VISIBLE);
						}
					 });
					
					pointstextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortloginpoints(roleID);
							pointstextview.setVisibility(View.GONE);
							pointstextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					pointstextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortloginpointss(roleID);
							pointstextview1.setVisibility(View.GONE);
							pointstextview.setVisibility(View.VISIBLE);
						}
					 });
					
					tiertextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortlogintier(roleID);
							tiertextview.setVisibility(View.GONE);
							tiertextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					tiertextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							sortlogintiers(roleID);
							tiertextview1.setVisibility(View.GONE);
							tiertextview.setVisibility(View.VISIBLE);
						}
					 });
					showallbtn.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							
							rankstextviews.setVisibility(View.VISIBLE);
							rankview.setVisibility(View.VISIBLE);
							pointsview.setVisibility(View.VISIBLE);
							pointss.setVisibility(View.VISIBLE);
							SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
							uuid = spLogin.getString("Rank","default");
							totalusers = spLogin.getString("TotalUsers","default");
							rankstextviews.setText(uuid+" of "+totalusers);
							parsedata();
							group.setText(" -"+" All");
							pointss.setText( userpoints);
							showallbtn.setVisibility(View.GONE);
							backbtn.setVisibility(View.VISIBLE);
							
							nametextview.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
									showallparsenamedataasc();
									nametextview.setVisibility(View.GONE);
									nametextview1.setVisibility(View.VISIBLE);
									
								}});
							
							nametextview1.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
									showallparsenamedatadesc();
									nametextview.setVisibility(View.VISIBLE);
									nametextview1.setVisibility(View.GONE);
									
								}});
							
							ranktextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									parsedata();
									ranktextview.setVisibility(View.GONE);
									ranktextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							ranktextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									parserankdata();
									ranktextview1.setVisibility(View.GONE);
									ranktextview.setVisibility(View.VISIBLE);
								}
							 });
							
							pointstextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									showallparsepointsdataasc();
									pointstextview.setVisibility(View.GONE);
									pointstextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							pointstextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									showallparsepointsdatadesc();
									pointstextview1.setVisibility(View.GONE);
									pointstextview.setVisibility(View.VISIBLE);
								}
							 });
							
							tiertextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									showallparsetierdataasc();
									tiertextview.setVisibility(View.GONE);
									tiertextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							tiertextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									showallparsetierdatadesc();
									tiertextview1.setVisibility(View.GONE);
									tiertextview.setVisibility(View.VISIBLE);
								}
							 });
					             
						}
					 });
					backbtn.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							rankstextviews.setVisibility(View.VISIBLE);
							rankview.setVisibility(View.VISIBLE);
							pointsview.setVisibility(View.VISIBLE);
							pointss.setVisibility(View.VISIBLE);
							SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
							uid = spLogin.getString("userId","default");
							roleID = spLogin.getString("roleId", "1");
							userpoints = spLogin.getString("points", "default");
							pointss.setText( userpoints);
							rankstextviews.setText(" - ");
							usergroup = spLogin.getString("role", "default");
							group.setText(" - "+usergroup);

							try{
															
								
								URL url = new URL(
										"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMyRank?UserId="+uid+"&RoleId="+roleID);
								DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
								DocumentBuilder db = dbf.newDocumentBuilder();
								Document doc = db.parse(new InputSource(url.openStream()));
								doc.getDocumentElement().normalize();
								NodeList nodeList = doc.getElementsByTagName("Table");
								for (int i = 0; i < nodeList.getLength(); i++) {
									Node node = nodeList.item(i);
									
									Element fstElmnt = (Element) node;
									NodeList nameList = fstElmnt.getElementsByTagName("count");
									Element nameElement = (Element) nameList.item(0);
									nameList = nameElement.getChildNodes();
									rankcountt = nameList.item(0).getNodeValue();
									
									NodeList websiteList = fstElmnt.getElementsByTagName("Role_Name");
									Element websiteElement = (Element) websiteList.item(0);
									websiteList = websiteElement.getChildNodes();
									rankRole_Name = websiteList.item(0).getNodeValue();
									//group.setText(" - "+rankRole_Name);
									
									NodeList DayNameList = fstElmnt.getElementsByTagName("Points");
									Element DayNameElement = (Element) DayNameList.item(0);
									DayNameList = DayNameElement.getChildNodes();
									rankPoints = DayNameList.item(0).getNodeValue();
									
									
									NodeList EventDetailsList = fstElmnt.getElementsByTagName("Rank");
									Element EventDetailsElement = (Element) EventDetailsList.item(0);
									EventDetailsList = EventDetailsElement.getChildNodes();
									Rank_rankk = EventDetailsList.item(0).getNodeValue();
									
									rankstextviews.setText(Rank_rankk+" of "+rankcountt);
									
									
								}
								
							}
							catch(Exception e)
							{
								
							}
							
														
							//group.setText(" - "+rankRole_Name);
							parselogindata(roleID);
							showallbtn.setVisibility(View.VISIBLE);
							backbtn.setVisibility(View.GONE);
							nametextview = (TextView)findViewById(R.id.txtLHName);
							nametextview1 = (TextView)findViewById(R.id.txtLHName1);
							
							nametextview.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
									nametextview.setVisibility(View.GONE);
									nametextview1.setVisibility(View.VISIBLE);
									sortloginname(roleID);
								}});
							
							nametextview1.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									//Toast.makeText(getApplicationContext(), "Name is clicked", 3).show();
									nametextview.setVisibility(View.VISIBLE);
									nametextview1.setVisibility(View.GONE);
									sortloginnames(roleID);
								}});
							
							ranktextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortloginrank(roleID);
									ranktextview.setVisibility(View.GONE);
									ranktextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							ranktextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortloginranks(roleID);
									ranktextview1.setVisibility(View.GONE);
									ranktextview.setVisibility(View.VISIBLE);
								}
							 });
							
							pointstextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortloginpoints(roleID);
									pointstextview.setVisibility(View.GONE);
									pointstextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							pointstextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortloginpointss(roleID);
									pointstextview1.setVisibility(View.GONE);
									pointstextview.setVisibility(View.VISIBLE);
								}
							 });
							
							tiertextview.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortlogintier(roleID);
									tiertextview.setVisibility(View.GONE);
									tiertextview1.setVisibility(View.VISIBLE);
								}
							 });
							
							tiertextview1.setOnClickListener(new OnClickListener()
							 {
								public void onClick(View v)
								{
									sortlogintiers(roleID);
									tiertextview1.setVisibility(View.GONE);
									tiertextview.setVisibility(View.VISIBLE);
								}
							 });
							
						}
					 });
				}
				else{
					
					parsedata();
					group.setVisibility(View.GONE);
					rankstextviews.setVisibility(View.GONE);
					showallbtn.setVisibility(View.INVISIBLE);
					backbtn.setVisibility(View.INVISIBLE);
					
					ranktextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							parsedata();
							ranktextview.setVisibility(View.GONE);
							ranktextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					ranktextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							parserankdata();
							ranktextview1.setVisibility(View.GONE);
							ranktextview.setVisibility(View.VISIBLE);
						}
					 });
					
					nametextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsenamedataasc();
							nametextview.setVisibility(View.GONE);
							nametextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					nametextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsenamedatadesc();
							nametextview1.setVisibility(View.GONE);
							nametextview.setVisibility(View.VISIBLE);
						}
					 });
					
					pointstextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsepointsdataasc();
							pointstextview.setVisibility(View.GONE);
							pointstextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					pointstextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsepointsdatadesc();
							pointstextview1.setVisibility(View.GONE);
							pointstextview.setVisibility(View.VISIBLE);
						}
					 });
					
					tiertextview.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsetierdataasc();
							tiertextview.setVisibility(View.GONE);
							tiertextview1.setVisibility(View.VISIBLE);
						}
					 });
					
					tiertextview1.setOnClickListener(new OnClickListener()
					 {
						public void onClick(View v)
						{
							showallparsetierdatadesc();
							tiertextview1.setVisibility(View.GONE);
							tiertextview.setVisibility(View.VISIBLE);
						}
					 });
				}
		}
				catch (Exception e) {
					System.out.println("XML Pasing Excpetion = " + e);
				}
		

	}
	
	private void Adv() {
		try {
			
			cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=10");
		
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
						ImageView img=(ImageView)findViewById(R.id.imgAddLboard);
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

	private void sortlogintier(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=tier"+"&OrderBy=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void sortlogintiers(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=tier"+"&OrderBy=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	private void sortloginpoints(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=points"+"&OrderBy=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void sortloginpointss(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=points"+"&OrderBy=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void sortloginrank(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=rank"+"&OrderBy=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void sortloginranks(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=rank"+"&OrderBy=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}

	private void sortloginname(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=Name"+"&OrderBy=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void sortloginnames(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=Name"+"&OrderBy=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void parsedata() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=Rank&Orderby=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	private void parserankdata() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=Rank&Orderby=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsenamedataasc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=name&Orderby=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsenamedatadesc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=name&Orderby=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsepointsdataasc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=points&Orderby=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsepointsdatadesc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=points&Orderby=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsetierdataasc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=tier&Orderby=asc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void showallparsetierdatadesc() {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId=0&RoleName=tier&Orderby=desc");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);

				Element fstElmnt = (Element) node;
				
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);


			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	private void parselogindata(String RoleId) {
		// TODO Auto-generated method stub
		try {
			
			final ArrayList<HashMap<String,String>> eventslist = new ArrayList<HashMap<String,String>>();
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMTop25Rankers?RoleId="+roleID+"&RoleName=Rank&OrderBy=asc");
			Log.v("url_kiran",url.toString());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Table");
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				
				Node node = nodeList.item(i);
			
				Element fstElmnt = (Element) node;
				
				NodeList nameList = fstElmnt.getElementsByTagName("Rank");
				Node n3 = (Node)nameList.item(0);
				if (n3.hasChildNodes())
				{
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					rank = nameList.item(0).getNodeValue();
				}
				else
				{
					rank = "-";
				}	
				map.put("rank",rank.toString());

				NodeList websiteList = fstElmnt.getElementsByTagName("NAME");
				Node n2 = (Node)websiteList.item(0);
				if (n2.hasChildNodes())
				{
					Element websiteElement = (Element) websiteList.item(0);
					websiteList = websiteElement.getChildNodes();
					name = websiteList.item(0).getNodeValue();
				}
				else
				{
					name = "-";
				}				
				map.put("name",name.toString());
				
				NodeList DayNameList = fstElmnt.getElementsByTagName("TotalRewards_Points");
				Node n1 = (Node)DayNameList.item(0);
				if (n1.hasChildNodes())
				{
					Element DayNameElement = (Element) DayNameList.item(0);
					DayNameList = DayNameElement.getChildNodes();
					points = DayNameList.item(0).getNodeValue();
				}
				else
				{
					points = "-";
				}
				map.put("points",points.toString());

				NodeList EventDetailsList = fstElmnt.getElementsByTagName("TotalRewards_Tier");
				Node n = (Node)EventDetailsList.item(0);
				if (n.hasChildNodes())
				{
					Element EventDetailsElement = (Element) EventDetailsList.item(0);
					EventDetailsList = EventDetailsElement.getChildNodes();
					tier = EventDetailsList.item(0).getNodeValue();
				}
				else
				{
					tier = "No Tier";
				}
				map.put("tier",tier.toString());
				
				eventslist.add(map);

			}
			SimpleAdapter adapter = new SimpleAdapter(
	        		LeaderBoard.this,
	        		eventslist,
	        		R.layout.lboardlist,
	        		new String[] {"rank","name","points","tier"},
	        		new int[] {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4}
	        		);
			listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter); 
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		
	}
	
	@Override  
	 public void onBackPressed() {  
	        HomeGroup.group.back();  
	 } 
}
