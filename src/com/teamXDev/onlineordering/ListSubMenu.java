package com.teamXDev.onlineordering;




import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListSubMenu extends Activity {
	
	SessionManager session;
	String category,subcategory;
	ListView list_SubMenu;
	ArrayList<String> receivedItem;
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	
	private JSONArray items;

	private static final String SUBMENU_URL = "http://www.teamXdev.com/webservice/GetSubMenu.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_ITEM = "item";
	private static final String TAG_ITEM_NAME = "name";
	private static final String TAG_ITEM_COST = "cost";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sub_menu);
		
		TextView  lbl_SubMenu=(TextView)findViewById(R.id.lbl_SubMenu);
		Bundle b = getIntent().getExtras();
		category= (String) b.get("category");
		subcategory=(String) b.get("subcategory");
		lbl_SubMenu.setText(subcategory);
		
	
		
		list_SubMenu = (ListView) findViewById(R.id.list_SubMenu);
		
		new RequestSubMenu().execute();
		
		list_SubMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						 
				   String clickedItem="Sample";
					//Create a bundle object
					Bundle b = new Bundle();

					//Inserts a String value into the mapping of this Bundle
					b.putString("clickedItem",clickedItem);
					// b.putString("age", age.getText().toString()); we can put as many parameters we need

					Intent intent=new Intent(getApplicationContext(),ItemDetails.class);
					//Add the bundle to the intent.
					intent.putExtras(b);

					//start the DisplayActivity
					startActivity(intent);
					
					//show Animation
					overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				
			}
		});
		
		
		
		
		
	}

	@Override
	public void onBackPressed() {
	    finish();
	    overridePendingTransition(R.anim.appear, R.anim.push_right_out);
	}

	class RequestSubMenu extends AsyncTask<String, String, String> {

		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListSubMenu.this);
			pDialog.setMessage("Fetching info..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			
			// Check for success tag
			int success;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				
				params.add(new BasicNameValuePair("category", category));
				params.add(new BasicNameValuePair("subcategory", subcategory));

				Log.e("category:",category);
				Log.e("subcategory:",subcategory);
				Log.e("SUBMENU_URL:",SUBMENU_URL);
				
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(SUBMENU_URL,"POST", params);

				// check your log for json response
				Log.e("JSon","Fetching JSon");
				Log.e("JSon Response:", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					items=json.getJSONArray(TAG_ITEM);
				    receivedItem = new ArrayList<String>();
					
					for (int i = 0; i < items.length(); i++) {
						JSONObject c = items.getJSONObject(i);

						// gets the content of each tag
						String name = c.getString(TAG_ITEM_NAME);
						String cost = c.getString(TAG_ITEM_COST);
						receivedItem.add(name);
				       
					}
					return json.getString(TAG_MESSAGE);
				} else {
					Log.e("SubMenu-Operation Failed !",json.getString(TAG_MESSAGE));
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
			Log.e("Post Execute Message", file_url);
			setView();
		}

		

	}
	
	private void setView() {
		
	  Log.e("Array size:",receivedItem.get(0));
	  Log.e("Array size:",receivedItem.get(1));
	  Log.e("Array size:",receivedItem.get(2));
		
	  ListSubMenuAdapter subMenuAdapter=new ListSubMenuAdapter(list_SubMenu.getContext(),R.layout.list_sub_menu_item,receivedItem);
	  
	  list_SubMenu.setAdapter(subMenuAdapter);
	  
	}
	
	// Checks the Network Connection
				public boolean ConnectionsAvailable() {
					boolean lRet = false;
					try {
						ConnectivityManager conMgr = (ConnectivityManager) getSystemService(ListSubMenu.CONNECTIVITY_SERVICE);
						NetworkInfo info = conMgr.getActiveNetworkInfo();
						if (info != null && info.isConnected()) {
							lRet = true;
						} else {
							lRet = false;
							Toast.makeText(ListSubMenu.this, "Connection Error",Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						Log.d("Connection Error", e.toString());
						lRet = false;
						Toast.makeText(ListSubMenu.this, "Connection Error",Toast.LENGTH_SHORT).show();
					}
					return lRet;
				}

	
}
