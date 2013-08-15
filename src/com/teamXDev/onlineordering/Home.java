package com.teamXDev.onlineordering;

import android.os.Build;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;

public class Home extends Activity {

	private static final String TAG = "Home";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1)
		{
			//Action bar code for android devices with android version more than gingerbread
			Log.i(TAG,"Build.VERSION.SDK_INT : "+Build.VERSION.SDK_INT);
			Log.i(TAG,"Build.VERSION_CODES.GINGERBREAD_MR1: "+Build.VERSION_CODES.GINGERBREAD_MR1);
			if(ViewConfiguration.get(Home.this).hasPermanentMenuKey())
			{
				Log.i(TAG,"Hardware Option Key Present");
				// to hide the action bar
				try
				{
					ActionBar actionBar = getActionBar();
					actionBar.hide();
				}
				catch (Exception ex)
				{
					Log.e(TAG,"Device Do Not Support Action Bar"+ex.toString());

				}
				
			}

			Log.i(TAG,"Hardware Option Key not Present");
		}
		else
		{
			Log.i(TAG,"Android version is less than 3.0");
			Log.i(TAG,"Build.VERSION.SDK_INT : "+Build.VERSION.SDK_INT);
			Log.i(TAG,"Build.VERSION_CODES.GINGERBREAD_MR1: "+Build.VERSION_CODES.GINGERBREAD_MR1);

		}
		
		Button btn_ViewPastOrder=(Button)findViewById(R.id.btn_ViewPastOrders);
		btn_ViewPastOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSignInOrSignUp();
			}

			
		});
		
		
		Button btn_LocationAndHours=(Button)findViewById(R.id.btn_LocationAndHours);
		btn_LocationAndHours.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToLocatioanAndHours();
			}

			

			
		});
		
		Button btn_AboutNiteFoodie=(Button)findViewById(R.id.btn_NiteFoodie);
		btn_AboutNiteFoodie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToAbout();
			}

			

			

			
		});
		
		Button btn_OrderNow=(Button)findViewById(R.id.btn_OrderNow);
		btn_OrderNow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToOrder();
			}

			

		

			
		});
		
		
	}

	
	
	private void goToSignInOrSignUp() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),SignInOrSignUp.class);
		//start the DisplayActivity
		startActivity(intent);
	}

	private void goToLocatioanAndHours() {
		// // TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),LocationAndHours.class);
		//start the DisplayActivity
		startActivity(intent);
		
	}
	
	private void goToAbout() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),AboutNiteFoodie.class);
		//start the DisplayActivity
		startActivity(intent);
		
	}
	
	private void goToOrder() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),ExpandableListMenu.class);
		//start the DisplayActivity
		startActivity(intent);
		
	}
}
