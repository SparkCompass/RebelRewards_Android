package com.eis;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeGroup extends ActivityGroup{
	public static HomeGroup group;
	private ArrayList<View> history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
/*	    showAlert();
*/	    this.history = new ArrayList<View>();
	    group = this;

	    View view = getLocalActivityManager().startActivity
	                ("ParentActivity", 
	                new Intent(this, Home.class)
	                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	                .getDecorView();

	    replaceView(view);
	}

	/*private void showAlert() {
		// TODO Auto-generated method stub
		 final Dialog dialog = new Dialog(this);
         dialog.setContentView(R.layout.abc);
         dialog.setTitle("Rebel Rewards");
         dialog.setCancelable(true);
 
        
         ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
         img.setImageResource(R.drawable.rebel_icon);
 
         Button button = (Button) dialog.findViewById(R.id.Button01);
         button.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
        	 dialog.dismiss();
             }
         });
         dialog.show();
	}*/

	public void replaceView(View v) {
	    // Adds the old one to history
	    history.add(v);
	    // Changes this Groups View to the new View.
	    setContentView(v);
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

	@Override  
	public void onBackPressed() {  
	    HomeGroup.group.back();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	    	HomeGroup.group.back();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
