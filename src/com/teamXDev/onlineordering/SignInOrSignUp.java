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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInOrSignUp extends Activity {

	Button btn_SignUp, btn_SignIn;
	EditText txt_Email, txt_Password;
	String name, from, email, password;
	Class<?> targetActivity;
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
	private static final String LOGIN_URL = "http://www.teamXdev.com/webservice/login.php";

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

		setContentView(R.layout.activity_sign_in_or_sign_up);
		Bundle msg = getIntent().getExtras();
		from = (String) msg.get("from");

		btn_SignUp = (Button) findViewById(R.id.btn_SignUp);
		btn_SignIn = (Button) findViewById(R.id.btn_SignIn);

		txt_Email = (EditText) findViewById(R.id.txt_Email);
		txt_Password = (EditText) findViewById(R.id.txt_Password);

		

		session = new SessionManager(getApplicationContext());

		btn_SignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSignUp();
			}

		});

		btn_SignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				goToSignIn();

			}

		});

	}

	private void goToSignUp() {

		Intent intent = new Intent(getApplicationContext(), SignUp.class);
		Bundle msg = new Bundle();
		msg.putString("from", from);

		intent.putExtras(msg);
		startActivity(intent);

	}

	private void goToSignIn() {
		// TODO Auto-generated method stub
		email = txt_Email.getText().toString().trim();
		password = txt_Password.getText().toString().trim();
		
		if (email.length() > 0 && password.length() > 0) {
		new AttemptLogin().execute();
		}
		else
		Toast.makeText(SignInOrSignUp.this, "Invalid Credentials",
					Toast.LENGTH_LONG).show();	

	}

	

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignInOrSignUp.this); // getApplicatonContext()
																// will give
																// error
			pDialog.setMessage("Attempting login...");
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
			Log.d("URL", LOGIN_URL);
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
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
					Log.d("Login Successful!", json.toString());
					name = json.getString(TAG_NAME);

					Bundle b = new Bundle();

					// Intent i = new Intent(getApplicationContext(),
					// ExpandableListMenu.class); //will give error
					if (from.equals("activity_Home")) {
						targetActivity = AccountDetails.class;
						b.putString("from", from);

					} else if (from.equals("activity_FinalOrder"))
						targetActivity = PlaceOrder.class;
					else if (from.equals("activiy_PlaceOrder"))
						targetActivity = PlaceOrder.class;

					Log.d("Heading to: ", targetActivity.toString());

					Intent i = new Intent(SignInOrSignUp.this, targetActivity);
					i.putExtras(b);
					
					session.createLoginSession(name);
					
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
				Toast.makeText(SignInOrSignUp.this, file_url, Toast.LENGTH_LONG)
						.show();
				
			} else
				Toast.makeText(SignInOrSignUp.this, "Invalid Credentials",
						Toast.LENGTH_LONG).show();

		}

	}

}
