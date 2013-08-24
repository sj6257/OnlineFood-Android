package com.teamXDev.onlineordering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountDetails extends Activity {

	String from, task;
	Class<?> targetActivity;
	Button btn_SaveInfromation, btn_LogOut;
	EditText txt_Email, txt_PhoneNumber, txt_StreetAddress, txt_UnitApt,
			txt_City, txt_State;
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_details);

		Bundle msg = getIntent().getExtras();
		from = (String) msg.get("from");

		session = new SessionManager(getApplicationContext());

		btn_SaveInfromation = (Button) findViewById(R.id.btn_SaveInformation);
		btn_LogOut = (Button) findViewById(R.id.btn_LogOut);
		txt_Email = (EditText) findViewById(R.id.txt_Email);
		txt_PhoneNumber = (EditText) findViewById(R.id.txt_PhoneNumber);
		txt_StreetAddress = (EditText) findViewById(R.id.txt_StreetAddress);
		txt_UnitApt = (EditText) findViewById(R.id.txt_UnitApt);
		txt_City = (EditText) findViewById(R.id.txt_City);
		txt_State = (EditText) findViewById(R.id.txt_State);

		getUserInfo();

		btn_SaveInfromation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SaveInfoAndGoToDesiredView();

			}
		});

		btn_LogOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogOutAnGoToDesiredView();
			}

		});

	}

	private void getUserInfo() {

	}

	private void LogOutAnGoToDesiredView() {
		// TODO Auto-generated method stub

		session.logoutUser();
		if (from.equals("activity_Home"))
			targetActivity = Home.class;
		else if (from.equals("activity_FinalOrder"))
			targetActivity = SignInOrSignUp.class;
		else if (from.equals("activiy_PlaceOrder"))
			targetActivity = SignInOrSignUp.class;
		Intent i = new Intent(AccountDetails.this, targetActivity);
		startActivity(i);

		Log.d("Heading to: ", targetActivity.toString());
	}

	private void SaveInfoAndGoToDesiredView() {

		// Save Information;

		if (from.equals("activity_Home"))
			targetActivity = Home.class;
		else if (from.equals("activity_FinalOrder"))
			targetActivity = PlaceOrder.class;
		else if (from.equals("activiy_PlaceOrder"))
			targetActivity = PlaceOrder.class;
		Intent i = new Intent(AccountDetails.this, targetActivity);
		startActivity(i);

		Log.d("Heading to: ", targetActivity.toString());

	}

}
