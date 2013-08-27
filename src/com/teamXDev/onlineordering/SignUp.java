package com.teamXDev.onlineordering;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {

	EditText txt_Email, txt_Password, txt_Name;
	String name, from, email, password;
	SessionManager session;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// php login script location:

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String LOGIN_URL =
	// "http://xxx.xxx.x.x:1234/webservice/login.php";

	// testing on Emulator:
	// private static final String LOGIN_URL =
	// "http://10.0.2.2:80/webservice/login.php";

	// testing Website
	private static final String LOGIN_URL = "http://www.teamXdev.com/webservice/register.php";

	// testing from a real server:
	// private static final String LOGIN_URL =
	// "http://www.yourdomain.com/webservice/login.php";

	// JSON element ids from repsonse of php script:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_NAME = "name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		Bundle b = getIntent().getExtras();
		from = (String) b.get("from");

		Button btn_Continue = (Button) findViewById(R.id.btn_Continue);
		btn_Continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToAccontDetails();
			}

		});

		txt_Email = (EditText) findViewById(R.id.txt_Email);
		txt_Password = (EditText) findViewById(R.id.txt_Password);
		txt_Name = (EditText) findViewById(R.id.txt_Name);

		

		session = new SessionManager(getApplicationContext());
	}

	private void goToAccontDetails() {
		// TODO Auto-generated method stub
		/*
		 * Bundle msg=new Bundle(); msg.putString("from", from); Intent intent=
		 * new Intent(getApplicationContext(),AccountDetails.class);
		 * intent.putExtras(msg); startActivity(intent);
		 * overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
		 */
		email = txt_Email.getText().toString().trim();
		password = txt_Password.getText().toString().trim();
		name = txt_Name.getText().toString().trim();

		if (email.length() > 0 && password.length() > 0
				&& name.length() > 0) {
			if (ConnectionsAvailable())
			new AttemptRegister().execute();
		} else
			Toast.makeText(SignUp.this, "Invalid Credentials",
					Toast.LENGTH_LONG).show();

	}

	class AttemptRegister extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUp.this); // getApplicatonContext()
														// will give
														// error
			pDialog.setMessage("Registering Account...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			Log.d("email", email.trim());
			Log.d("Password", password.trim());
			Log.d("name", name.trim());

			Log.d("URL", LOGIN_URL);
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("email", email));
				params.add(new BasicNameValuePair("password", password));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("Register Successful!", json.toString());
					name = json.getString(TAG_NAME);

					Bundle b = new Bundle();
					b.putString("from", from);

					Intent i = new Intent(SignUp.this, AccountDetails.class);
					i.putExtras(b);
					session.createLoginSession(name,email);
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Register Failure!", json.getString(TAG_MESSAGE));
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
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(SignUp.this, file_url, Toast.LENGTH_SHORT).show();
				
			} else
				Toast.makeText(SignUp.this, "Registration Failure",Toast.LENGTH_SHORT).show();

		}

	}
	
	// Checks the Network Connection
		public boolean ConnectionsAvailable() {
			boolean lRet = false;
			try {
				ConnectivityManager conMgr = (ConnectivityManager) getSystemService(SignUp.CONNECTIVITY_SERVICE);
				NetworkInfo info = conMgr.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					lRet = true;
				} else {
					lRet = false;
					Toast.makeText(SignUp.this, "Connection Error",Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Log.d("Connection Error", e.toString());
				lRet = false;
				Toast.makeText(SignUp.this, "Connection Error",Toast.LENGTH_SHORT).show();
			}
			return lRet;
		}

}
