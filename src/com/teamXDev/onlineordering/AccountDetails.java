package com.teamXDev.onlineordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountDetails extends Activity {

	String from, task, name, email, streetAddress, unitApt, city, state,
			pincode, phoneNumber;
	Class<?> targetActivity;
	LinearLayout layout_parent;
	RelativeLayout layout_info;
	View custom_progressbar;
	Button btn_SaveInfromation, btn_LogOut;
	EditText txt_PhoneNumber, txt_StreetAddress, txt_UnitApt, txt_City,
			txt_State, txt_Pincode;
	TextView lbl_Name, lbl_Email;
	SessionManager session;

	// Progress Dialog
		private Dialog dialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// testing Website
	private static final String REQUEST_INFO_URL = "http://www.teamXdev.com/webservice/GetUserInfo.php";
	private static final String SAVE_INFO_URL = "http://www.teamXdev.com/webservice/SaveUserInfo.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PHONENUMBER = "phoneNumber";
	private static final String TAG_STREETADDRESS = "streetAddress";
	private static final String TAG_UNITAPT = "flatNumber";
	private static final String TAG_CITY = "city";
	private static final String TAG_STATE = "state";
	private static final String TAG_PINCODE = "pincode";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_details);

		Bundle msg = getIntent().getExtras();
		from = (String) msg.get("from");

		session = new SessionManager(getApplicationContext());

		btn_SaveInfromation = (Button) findViewById(R.id.btn_SaveInformation);
		btn_LogOut = (Button) findViewById(R.id.btn_LogOut);

		lbl_Name = (TextView) findViewById(R.id.lbl_Name);
		lbl_Email = (TextView) findViewById(R.id.lbl_Email);

		txt_PhoneNumber = (EditText) findViewById(R.id.txt_PhoneNumber);
		txt_StreetAddress = (EditText) findViewById(R.id.txt_StreetAddress);
		txt_UnitApt = (EditText) findViewById(R.id.txt_UnitApt);
		txt_City = (EditText) findViewById(R.id.txt_City);
		txt_State = (EditText) findViewById(R.id.txt_State);
		txt_Pincode = (EditText) findViewById(R.id.txt_Pincode);

		HashMap<String, String> user = session.getUserDetails();
		// name
		name = user.get(SessionManager.KEY_NAME);
		email = user.get(SessionManager.KEY_EMAIL);

		

		btn_SaveInfromation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SaveInfo();

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
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getUserInfo();
	}
	

	private void getUserInfo() {
		if(ConnectionsAvailable())
		new RequestInfo().execute();

	}

	private void setView() {

		lbl_Name.setText(name);
		lbl_Email.setText(email);
		txt_PhoneNumber.setText(phoneNumber);
		txt_StreetAddress.setText(streetAddress);
		txt_UnitApt.setText(unitApt);
		txt_City.setText(city);
		txt_State.setText(state);
		txt_Pincode.setText(pincode);
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

	private void SaveInfo() {
		// Save Information;
		if(ConnectionsAvailable())
		new SaveInfo().execute();
	}

	@Override
	public void onBackPressed() {
		finish();

		if (from.equals("activity_Home"))
			targetActivity = Home.class;
		else if (from.equals("activity_FinalOrder"))
			targetActivity = PlaceOrder.class;
		else if (from.equals("activiy_PlaceOrder"))
			targetActivity = PlaceOrder.class;
		Intent i = new Intent(AccountDetails.this, targetActivity);
		startActivity(i);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		Log.d("Heading to: ", targetActivity.toString());

	}

	class RequestInfo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			final LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			custom_progressbar = (View)inflater.inflate(R.layout.progress_dialog, null);
			
	        TextView message=(TextView) custom_progressbar.findViewById(R.id.tv_loading);
	        message.setText("loading..");
	        
	        // hide the layouts till it get load
	        layout_info =(RelativeLayout)findViewById(R.id.layout_info);
	        layout_info.setVisibility(View.GONE);
	        btn_SaveInfromation.setVisibility(View.GONE);
	        
	         
	        // add progress bar to main layout
	        layout_parent =(LinearLayout)findViewById(R.id.layout_parent);
	        layout_parent.addView(custom_progressbar);
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("email", email));

				Log.e("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(REQUEST_INFO_URL,
						"POST", params);

				// check your log for json response
				Log.e("Retrieving Infromtion ", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.e("Information Retrived Successfully!", json.toString());

					phoneNumber = json.getString(TAG_PHONENUMBER);
					streetAddress = json.getString(TAG_STREETADDRESS);
					unitApt = json.getString(TAG_UNITAPT);
					city = json.getString(TAG_CITY);
					state = json.getString(TAG_STATE);
					pincode = json.getString(TAG_PINCODE);

					return json.getString(TAG_MESSAGE);
				} else {
					Log.e("Information Retrivation Failed!",
							json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			layout_parent.removeView(custom_progressbar);
			layout_info.setVisibility(View.VISIBLE);
			btn_SaveInfromation.setVisibility(View.VISIBLE);
			
			Log.e("Post Execute Message", file_url);
			setView();
		}

	}

	class SaveInfo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//alpha can be changed (0-255) to acheive desired opacity.
	        // 0 transparent
			Drawable d = new ColorDrawable(Color.BLACK);
	        d.setAlpha(130);

	        
	        dialog = new Dialog(AccountDetails.this);
	        // for dialog w/o title
	        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
			dialog.setContentView(R.layout.progress_dialog);
			TextView message=(TextView)dialog.findViewById(R.id.tv_loading);
	        message.setText("Saving Info...");
	        dialog.setCancelable(true);
	        dialog.getWindow().setBackgroundDrawable(d);
	        dialog.show();
			
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			city = txt_City.getText().toString();
			phoneNumber = txt_PhoneNumber.getText().toString();
			unitApt = txt_UnitApt.getText().toString();
			pincode = txt_PhoneNumber.getText().toString();
			state = txt_State.getText().toString();
			streetAddress = txt_StreetAddress.getText().toString();
			pincode = txt_Pincode.getText().toString();

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("email", email));

				params.add(new BasicNameValuePair(TAG_PHONENUMBER, phoneNumber));
				params.add(new BasicNameValuePair(TAG_STREETADDRESS,
						streetAddress));
				params.add(new BasicNameValuePair(TAG_UNITAPT, unitApt));
				params.add(new BasicNameValuePair(TAG_CITY, city));
				params.add(new BasicNameValuePair(TAG_STATE, state));
				params.add(new BasicNameValuePair(TAG_PINCODE, pincode));

				Log.e("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(SAVE_INFO_URL,
						"POST", params);

				// check your log for json response
				Log.e("Saving Infromtion ", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.e("Information Saved Successfully!", json.toString());

					return json.getString(TAG_MESSAGE);
				} else {
					Log.e("Information Saving Failed!",
							json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			dialog.dismiss();
			Log.e("Post Execute Message", file_url);

		}

	}
	
	// Checks the Network Connection
			public boolean ConnectionsAvailable() {
				boolean lRet = false;
				try {
					ConnectivityManager conMgr = (ConnectivityManager) getSystemService(AccountDetails.CONNECTIVITY_SERVICE);
					NetworkInfo info = conMgr.getActiveNetworkInfo();
					if (info != null && info.isConnected()) {
						lRet = true;
					} else {
						lRet = false;
						Toast.makeText(AccountDetails.this, "Connection Error",Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Log.d("Connection Error", e.toString());
					lRet = false;
					Toast.makeText(AccountDetails.this, "Connection Error",Toast.LENGTH_SHORT).show();
				}
				return lRet;
			}

}
