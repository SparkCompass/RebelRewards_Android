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


import com.eis.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.eis.CommonUtilities.SENDER_ID;
import static com.eis.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.eis.CommonUtilities.EXTRA_MESSAGE;

import static com.eis.CommonUtilities.SERVER_URL;
//import com.google.android.gcm.GCMRegistrar;


public class Home extends Activity{
	ImageButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,lgout;
	TextView txtHUserId,txtHFullName,txtLogOut,txtPoints,mDisplay;
	ProgressDialog progressDialog;
	SharedPreferences spLogin;
	ImageView imgvw,imgvw1;
	public LoadImage loadimage;
	private Activity activity;
	TextView imglink[];
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	//AsyncTask<Void, Void, Void> mRegisterTask;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.main);
	  activity=this;
	 loadimage=new LoadImage(activity.getApplicationContext());
	 
	 
	 
	 
	 
		//Loading ads here.
		try {
			
			cd = new ConnectionDetector(getApplicationContext());
		    isInternetPresent = cd.isConnectingToInternet();
		    if (isInternetPresent) 
	          {
			
			URL url = new URL(
					"http://130.74.17.62/RebelRewardsWebService/Service.asmx/GetMobileAdInfo?PageId=2");
		
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
						ImageView img=(ImageView)findViewById(R.id.imgaddHome);
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
		
		
	    
		btn1=(ImageButton)findViewById(R.id.imgbtn1);
		 btn2=(ImageButton)findViewById(R.id.imgbtn2);
		 btn3=(ImageButton)findViewById(R.id.imgbtn3);
		 btn4=(ImageButton)findViewById(R.id.imgbtn4);
		 btn5=(ImageButton)findViewById(R.id.imgbtn5);
		 btn6=(ImageButton)findViewById(R.id.imgbtn6);
		 btn7=(ImageButton)findViewById(R.id.imgbtn7);
		 btn8=(ImageButton)findViewById(R.id.imgbtn8);
		 btn9=(ImageButton)findViewById(R.id.imgbtn9);
		 btn10=(ImageButton)findViewById(R.id.imgbtn10);
		 //imgvw1=(ImageView)findViewById(R.id.imgaddHome);
		 lgout=(ImageButton)findViewById(R.id.logout);
		 
	txtHFullName=(TextView)findViewById(R.id.txtHFullName);
	//txtHPoints=(TextView)findViewById(R.id.txtHPoints);
	//txtHTierLevel=(TextView)findViewById(R.id.txtHTierLevel);
	txtHUserId=(TextView)findViewById(R.id.txtHUserId);
	//txtLogOut=(TextView)findViewById(R.id.lnkLogOut);
	txtPoints=(TextView)findViewById(R.id.txtLPoints);
	//txtContactUs = (TextView) findViewById(R.id.lnkContactUs);
	//lnkRules=(TextView)findViewById(R.id.lnkRules);
	
	
	spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
	
	if(!spLogin.getString("userId", "default").equals("default"))
	{
		
		txtHFullName.setText(spLogin.getString("fName", " "));
		//txtHFullName.append(" ");
		//txtHFullName.append(spLogin.getString("lName", " "));
		txtHUserId.setText(" - ");
		txtHUserId.append(spLogin.getString("userId", "default"));
		txtHUserId.append("         Points:");
		txtPoints.setText(spLogin.getString("points", "0"));
	    //txtPoints.append("Points");
		
		//txtHTierLevel.setText(spLogin.getString("tier", " "));
		//txtLogOut.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.GONE);
		btn10.setVisibility(View.VISIBLE);
		btn9.setVisibility(View.GONE);
		lgout.setVisibility(View.VISIBLE);
		/*lgout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				spLogin=getSharedPreferences("UserData",MODE_PRIVATE);
			SharedPreferences.Editor logoutEditor=spLogin.edit();
			logoutEditor.putString("userName", "default");
			logoutEditor.putString("userId", "default");
			logoutEditor.putString("points", "default");
			logoutEditor.putString("tier", "default");
			logoutEditor.putString("tempPwd", "N");
			logoutEditor.commit();
			
			Intent in=new Intent(getApplicationContext(),TabBar.class);
			in.setAction(Intent.ACTION_MAIN);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			}});*/
		
	}
	else
	{
		txtHFullName.setVisibility(View.INVISIBLE);
		txtHUserId.setVisibility(View.INVISIBLE);
		txtPoints.setVisibility(View.INVISIBLE);
		//txtHTierLevel.setVisibility(View.INVISIBLE);
		//txtLogOut.setVisibility(View.INVISIBLE);
		btn10.setVisibility(View.GONE);
		btn1.setVisibility(View.VISIBLE);
		btn9.setVisibility(View.VISIBLE);
		
	}
		
		
		lgout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			spLogin=getSharedPreferences("UserData",MODE_PRIVATE);
			SharedPreferences.Editor logoutEditor=spLogin.edit();
			//logoutEditor.putString("userName", "default");
			logoutEditor.remove("userName");
			//logoutEditor.putString("userId", "default");
			logoutEditor.remove("userId");
			//logoutEditor.putString("points", "default");
			logoutEditor.remove("points");
			//logoutEditor.putString("tier", "default");
			logoutEditor.remove("tier");
			//logoutEditor.putString("tempPwd", "N");
			logoutEditor.remove("tempPwd");
			logoutEditor.commit();
			finish();
			
			Intent in=new Intent(getApplicationContext(),TabBar.class);
			in.setAction(Intent.ACTION_MAIN);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			
			}});
		
	
		btn8.setOnClickListener (new OnClickListener() {
		
		public void onClick(View v) {
			try
			{
			View view = HomeGroup.group.getLocalActivityManager()
            .startActivity("ContactUs", new Intent(getApplicationContext(),ContactUs.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            .getDecorView();
			HomeGroup.group.replaceView(view);
			}
			catch(Exception ex)
			{
				return;
			}
			
		}
	});
	//lnkRules.setOnClickListener(new OnClickListener() {
		
		//public void onClick(View v) {
			// TODO Auto-generated method stub
		//	Intent in=new Intent(getApplicationContext(),ProgramRules.class);
		//	startActivity(in);
		//}
	//});
	//lnkRules.setText(Html.fromHtml("<a href='http://130.74.17.62/RebelRewards/ProgramRules.aspx'>Rules</a>"));
	//lnkRules.setMovementMethod(LinkMovementMethod.getInstance());
	
	
	
	  btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent in=new Intent(getApplicationContext(),SignIn.class);
				startActivity(in);
				
				
			}});
	  
	  
	 
	  btn7.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				View view = HomeGroup.group.getLocalActivityManager()
                .startActivity("GoSocial", new Intent(getApplicationContext(),GoSocial.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
				HomeGroup.group.replaceView(view);
			}});
	  
	  
	  
	  btn3.setOnClickListener(new OnClickListener() {
			
		  public void onClick(View v) {

			  //start the progress dialog

			  //progressDialog=ProgressDialog.show(Home.this, "Rebel Rewards", "Loading please wait....",true,true);

			 // new Thread(new Runnable() {

			 // public void run() {

			  try
			  {
				
				  View view =HomeGroup.group.getLocalActivityManager()
	                .startActivity("Leaderboard", new Intent(getApplicationContext(),LeaderBoard.class)
	                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	                .getDecorView();
				  HomeGroup.group.replaceView(view);
			  }
			  catch (Exception e) 
			  {
				  Log.e("LeaderboardLoading", e.getMessage());
			  }
			 // progressDialog.dismiss();
			  }

			//  }).start();

			 // }

			  });
			
	  
	 
	  btn4.setOnClickListener(new OnClickListener() {

		  public void onClick(View v) {

		  //start the progress dialog

		 // progressDialog=ProgressDialog.show(Home.this, "Rebel Rewards", "Loading please wait....",true,true);

		  //new Thread(new Runnable() {

		  //public void run() {

		  try
		  {
			
			  View view = HomeGroup.group.getLocalActivityManager()
              .startActivity("Rewards", new Intent(getApplicationContext(),Rewards.class)
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
              .getDecorView();
			  HomeGroup.group.replaceView(view);
		  }
		  catch (Exception e) 
		  {
			  Log.e("Rewards", e.getMessage());
		  }
		 // progressDialog.dismiss();
		  }

		//  }).start();

		//  }

		  });
	  
	 
	  
	 /* btn2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				View view = HomeGroup.group.getLocalActivityManager()
                .startActivity("Events", new Intent(getApplicationContext(),RRevents.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
				HomeGroup.group.replaceView(view);
			}});*/
	  
	  btn2.setOnClickListener(new OnClickListener()  {
		  	public void onClick(View v) {
		  		@SuppressWarnings("deprecation")
				View view = HomeGroup.group.getLocalActivityManager().
		  		startActivity("Events",new Intent(Home.this,Events.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		  		
				
				HomeGroup.group.replaceView(view);
		  	}});
	  
	  btn5.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				try{
					spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
					if(!spLogin.getString("userId", "default").equals("default"))
					{
						View view = HomeGroup.group.getLocalActivityManager()
					              .startActivity("MyCode", new Intent(getApplicationContext(),MyCode.class)
					              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					              .getDecorView();
									
									HomeGroup.group.replaceView(view);
					}
					else{
						
						Intent intent = new Intent(getApplicationContext(),SignIn.class); 
						startActivity(intent);
					}
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG);
				}
				
				/*View view = HomeGroup.group.getLocalActivityManager()
              .startActivity("MyCode", new Intent(getApplicationContext(),MyCode.class)
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
              .getDecorView();
				
				HomeGroup.group.replaceView(view);*/
			}});
	  
	  btn6.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
			    isInternetPresent = cd.isConnectingToInternet();
			    if (isInternetPresent) 
		          {
				Uri uri = Uri.parse( "http://130.74.17.62/RebelRewards/ProgramRules.aspx" );
				startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
		          }
			    else
			    {
			    	Toast.makeText(Home.this, "Please check your internet connection and try again. " , Toast.LENGTH_LONG).show();
			    }
			}});
	  
	  btn8.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				View view = HomeGroup.group.getLocalActivityManager()
          .startActivity("ContactUs", new Intent(getApplicationContext(),ContactUs.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
          .getDecorView();
				
				HomeGroup.group.replaceView(view);
			}});
	  
	  btn9.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//lgout.setVisibility(View.INVISIBLE);
				finish();
			}});
	  
	  
	  btn10.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				View view = HomeGroup.group.getLocalActivityManager()
                .startActivity("Profile", new Intent(getApplicationContext(),Profile.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
				HomeGroup.group.replaceView(view);
			}});
	  
	/*//}
	
	catch(Exception ex)
	{
	}*/

}
	
	 /*@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch(item.getItemId()) {
	            
	             * Typically, an application registers automatically, so options
	             * below are disabled. Uncomment them if you want to manually
	             * register or unregister the device (you will also need to
	             * uncomment the equivalent options on options_menu.xml).
	             
	            
	            case R.id.options_register:
	                GCMRegistrar.register(this, SENDER_ID);
	                return true;
	            case R.id.options_unregister:
	                GCMRegistrar.unregister(this);
	                return true;
	             
	            case R.id.options_clear:
	                mDisplay.setText(null);
	                return true;
	            case R.id.options_exit:
	                finish();
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }

	    @Override
	    protected void onDestroy() {
	        if (mRegisterTask != null) {
	            mRegisterTask.cancel(true);
	        }
	        unregisterReceiver(mHandleMessageReceiver);
	        GCMRegistrar.onDestroy(this);
	        super.onDestroy();
	    }

	    private void checkNotNull(Object reference, String name) {
	        if (reference == null) {
	            throw new NullPointerException(
	                    getString(R.string.error_config, name));
	        }
	    }

	    private final BroadcastReceiver mHandleMessageReceiver =
	            new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
	            mDisplay.append(newMessage + "\n");
	        }
	    };*/
	
	//Note:  unable try/Catch For Push Notifications if u need it.
	   
		//try
		//{
			/*checkNotNull(SERVER_URL, "SERVER_URL");
		     checkNotNull(SENDER_ID, "SENDER_ID");
		     // Make sure the device has the proper dependencies.
		     GCMRegistrar.checkDevice(this);
		     // Make sure the manifest was properly set - comment out this line
		     // while developing the app, then uncomment it when it's ready.
		     GCMRegistrar.checkManifest(this);
		     
		     mDisplay = (TextView) findViewById(R.id.display);
		     registerReceiver(mHandleMessageReceiver,
		             new IntentFilter(DISPLAY_MESSAGE_ACTION));
		     final String regId = GCMRegistrar.getRegistrationId(this);
		     if (regId.equals("")) {
		         // Automatically registers application on startup.
		         GCMRegistrar.register(this, SENDER_ID);
		     } else {
		         // Device is already registered on GCM, check server.
		         if (GCMRegistrar.isRegisteredOnServer(this)) {
		             // Skips registration.
		             mDisplay.append(getString(R.string.already_registered) + "\n");
		         } else {
		             // Try to register again, but not in the UI thread.
		             // It's also necessary to cancel the thread onDestroy(),
		             // hence the use of AsyncTask instead of a raw thread.
		             final Context context = this;
		             mRegisterTask = new AsyncTask<Void, Void, Void>() {

		                 @Override
		                 protected Void doInBackground(Void... params) {
		                     boolean registered =
		                             ServerUtilities.register(context, regId);
		                     // At this point all attempts to register with the app
		                     // server failed, so we need to unregister the device
		                     // from GCM - the app will try to register again when
		                     // it is restarted. Note that GCM will send an
		                     // unregistered callback upon completion, but
		                     // GCMIntentService.onUnregistered() will ignore it.
		                     if (!registered) {
		                         GCMRegistrar.unregister(context);
		                     }
		                     return null;
		                 }

		                 @Override
		                 protected void onPostExecute(Void result) {
		                     mRegisterTask = null;
		                 }

		             };
		             mRegisterTask.execute(null, null, null);
		         }
		     }*/
			
	
}
