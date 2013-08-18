package com.teamXDev.onlineordering;

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

public class Home extends Activity {

	private static final String TAG = "Home";
	Button btn_LocationAndHours, btn_ViewPastOrder, btn_MyAccount,
			btn_OrderNow;
	ImageButton img_AboutNiteFoodie;

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

		btn_ViewPastOrder = (Button) findViewById(R.id.btn_ViewPastOrders);
		btn_LocationAndHours = (Button) findViewById(R.id.btn_LocationAndHours);
		btn_MyAccount = (Button) findViewById(R.id.btn_MyAccount);
		btn_OrderNow = (Button) findViewById(R.id.btn_OrderNow);
		img_AboutNiteFoodie = (ImageButton) findViewById(R.id.img_logo);

		btn_MyAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSignInOrSignUp("btn_MyAccount");
			}
		});

		btn_ViewPastOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSignInOrSignUp("btn_ViewPastOrder");
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

	private void goToSignInOrSignUp(String from) {

		Intent intent = new Intent(getApplicationContext(),
				SignInOrSignUp.class);

		Bundle b = new Bundle();
		// Inserts a String value into the mapping of this Bundle
		b.putString("from", from);
		// b.putString("view","List");
		intent.putExtras(b);
		// start the DisplayActivity
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
}
