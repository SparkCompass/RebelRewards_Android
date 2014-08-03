
package com.eis;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class TabBar extends TabActivity implements  OnTabChangeListener, OnClickListener{
	TabHost tabHost;
	LoadImage loadimage;
	SharedPreferences spLogin;
	public void onCreate(Bundle savedInstanceState)
	{
		
	super.onCreate(savedInstanceState);
	 //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
     

	 setContentView(R.layout.tabs);
     
     //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
   
     setTabs();
     tabHost=getTabHost();
    SharedPreferences spLogin=getSharedPreferences("UserData", MODE_PRIVATE);

    if(spLogin.getString("tempPwd", "N").trim().equals("Y"))
    {
    	tabHost=getTabHost();
    	tabHost.setCurrentTab(1);
    }
    
    
	}
	
	 @Override
	public void onDestroy()
	    {
	       //loadimage=new LoadImage(getApplicationContext());
	       //loadimage.clearCache();
		 super.onDestroy();
	    }

	public void selecttab(int id) throws Exception
	{
		try
		{
			
			setContentView(R.layout.tabs);
			 setTabs();
			 tabHost=getTabHost();
			 tabHost.setCurrentTab(id);
    	
		}
		catch( Exception ex)
		{
		throw ex;
		}
	}
	
	private void setTabs() {
		try
		{
			spLogin=getSharedPreferences("UserData", MODE_PRIVATE);
		// TODO Auto-generated method stub
		     addTab("Home", R.drawable.tab_home, HomeGroup.class);
		
			
			if(!spLogin.getString("userId", "default").equals("default")){
				addTab("Profile", R.drawable.tab_profile, Profile.class);
				
			}
			else{
				addTab("Profile", R.drawable.tab_profile, SignIn.class);
			}
		
		
		   addTab("Events", R.drawable.tab_events, Events.class);
		//addTab("MyCode", R.drawable.tab_mycode, MyCode.class);
		
			
			if(!spLogin.getString("userId", "default").equals("default")){
				addTab("MyCode", R.drawable.tab_mycode, MyCode.class);
			}
			else{
				addTab("MyCode", R.drawable.tab_mycode, SignIn.class);
			}
		
		addTab("Rewards", R.drawable.tab_rewards, Rewards.class);
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG);
		}
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		// TODO Auto-generated method stub
		tabHost= getTabHost();
		//Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(new Intent(this, c).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		
		tabHost.addTab(spec);
		getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        
		    	Intent in=new Intent(getApplicationContext(),TabBar.class);
				in.setAction(Intent.ACTION_MAIN);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
		       
		    }
		});
		//tabHost.setOnClickListener(this);
	   //   tabHost.setOnTabChangedListener(this);
		
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	}

	public void onClick(View v) {
		int i = getTabHost().getCurrentTab();
   	 Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);

   	    if (i == 0) {
   	                    Log.i("@@@@@@@@@@ Inside onClick tab 0", "onClick tab");

   	                    }
   	       else if (i ==1) {
   	                    Log.i("@@@@@@@@@@ Inside onClick tab 1", "onClick tab");
   	         }
		
	}

	public void onTabChanged(String tabId) {
		int i = getTabHost().getCurrentTab();
   	 Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);

   	    if (i == 0) {
   	                    Log.i("@@@@@@@@@@ Inside onClick tab 0", "onClick tab");

   	                    }
   	       else if (i ==1) {
   	                    Log.i("@@@@@@@@@@ Inside onClick tab 1", "onClick tab");
   	         }
		
	}
}


