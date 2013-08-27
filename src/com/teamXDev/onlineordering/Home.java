package com.teamXDev.onlineordering;



import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Home extends Activity {

	private static final String TAG = "Home";
	Button btn_LocationAndHours, btn_AboutNiteFoodie, btn_MyAccount,
			btn_OrderNow;
	ImageButton img_AboutNiteFoodie;
	TextView lbl_UserName;
	String name;
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
			// Action bar code for android devices with android version more
			// than gingerbread
			Log.i(TAG, "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT);
			Log.i(TAG, "Build.VERSION_CODES.GINGERBREAD_MR1: "
					+ Build.VERSION_CODES.GINGERBREAD_MR1);
			if (ViewConfiguration.get(Home.this).hasPermanentMenuKey()) {
				Log.i(TAG, "Hardware Option Key Present");
				// to hide the action bar
				try {
					ActionBar actionBar = getActionBar();
					actionBar.hide();
				} catch (Exception ex) {
					Log.e(TAG,
							"Device Do Not Support Action Bar" + ex.toString());

				}

			}

			Log.i(TAG, "Hardware Option Key not Present");
		} else {
			Log.i(TAG, "Android version is less than 3.0");
			Log.i(TAG, "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT);
			Log.i(TAG, "Build.VERSION_CODES.GINGERBREAD_MR1: "
					+ Build.VERSION_CODES.GINGERBREAD_MR1);

		}

		
		
		
		btn_AboutNiteFoodie = (Button) findViewById(R.id.btn_NiteFoodie);
		btn_LocationAndHours = (Button) findViewById(R.id.btn_LocationAndHours);
		btn_MyAccount = (Button) findViewById(R.id.btn_MyAccount);
		btn_OrderNow = (Button) findViewById(R.id.btn_OrderNow);
		img_AboutNiteFoodie = (ImageButton) findViewById(R.id.img_logo);
		lbl_UserName=(TextView)findViewById(R.id.lbl_UserName);
		
		session=new SessionManager(getApplicationContext());
		if(session.isLoggedIn())
		{
			// get user data from session
	        HashMap<String, String> user = session.getUserDetails();
	        
	        // name
	        name = user.get(SessionManager.KEY_NAME);
	        // display Name
			lbl_UserName.setText("Hi "+name+"..");
 		}
		

		btn_MyAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(session.isLoggedIn())
					goToAccountDetails();
				else
					goToSignInOrSignUp();	
				
			}

			
		});

		btn_AboutNiteFoodie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToAbout();
			}
		});

		btn_LocationAndHours.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToLocatioanAndHours();
			}
		});

		img_AboutNiteFoodie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToAbout();
			}
		});

		btn_OrderNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToOrder();
			}

		});

	}
	
	private void goToAccountDetails() {
		// TODO Auto-generated method stub
        String from="activity_Home";		
		Bundle msg = new Bundle();
		msg.putString("from", from);
		Intent intent = new Intent(getApplicationContext(),AccountDetails.class);
		intent.putExtras(msg);
		
		startActivity(intent);
		
	}

	private void goToSignInOrSignUp() {

		String from="activity_Home";
		
		Bundle b = new Bundle();
		b.putString("from", from);
		
		Intent intent = new Intent(getApplicationContext(),SignInOrSignUp.class);
		intent.putExtras(b);
		
		startActivity(intent);
	}

	private void goToLocatioanAndHours() {
		// // TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				LocationAndHours.class);
		// start the DisplayActivity
		startActivity(intent);

	}

	private void goToAbout() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				AboutNiteFoodie.class);
		// start the DisplayActivity
		startActivity(intent);

	}

	private void goToOrder() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				ExpandableListMenu.class);
		// start the DisplayActivity
		startActivity(intent);

	}
	
	@Override
	public void onBackPressed() {
	    finish();
	   // clearing activity task so as to close app 
	}
	
	
}
