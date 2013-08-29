package com.teamXDev.onlineordering;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListSubMenu extends Activity {

	SessionManager session;
	String category, subcategory;
	LinearLayout layout_parent;
	ListView list_SubMenu;
	ArrayList<String> receivedItemName, receivedItemCost, receivedItemID;
	
	View custom_progressbar;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	private JSONArray items;

	private static final String SUBMENU_URL = "http://www.teamXdev.com/webservice/GetSubMenu.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_ITEM = "item";
	private static final String TAG_ITEM_NAME = "name";
	private static final String TAG_ITEM_COST = "cost";
	private static final String TAG_ITEM_ID = "id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sub_menu);

		TextView lbl_SubMenu = (TextView) findViewById(R.id.lbl_SubMenu);
		Bundle b = getIntent().getExtras();
		category = (String) b.get("category");
		subcategory = (String) b.get("subcategory");
		lbl_SubMenu.setText(subcategory);

		list_SubMenu = (ListView) findViewById(R.id.list_SubMenu);
		if (ConnectionsAvailable()) 
		new RequestSubMenu().execute();

		list_SubMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// get Adapter of the List to get Item Clicked Details
				ListSubMenuAdapter lAdapter = (ListSubMenuAdapter) parent
						.getAdapter();
				String itemID = (String) lAdapter.getCustomID(position);
				String itemName = (String) lAdapter.getItem(position);
				String itemCost = (String) lAdapter.getItemCost(position);

				// Create a bundle object
				Bundle b = new Bundle();

				// Inserts a String value into the mapping of this Bundle
				b.putString("itemID", itemID);
				b.putString("itemName", itemName);
				b.putString("itemCost", itemCost);

				Intent intent = new Intent(getApplicationContext(),
						ItemDetails.class);
				// Add the bundle to the intent.
				intent.putExtras(b);

				// start the DisplayActivity
				startActivity(intent);

				// show Animation
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);

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
			/*pDialog = new ProgressDialog(ListSubMenu.this);
			pDialog.setMessage("Fetching info..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();*/
			
			final LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			custom_progressbar = (View)inflater.inflate(R.layout.progress_dialog, null);
			
	        TextView message=(TextView) custom_progressbar.findViewById(R.id.tv_loading);
	        message.setText("loading..");
	        
	        // hide the list till it get load
	        list_SubMenu.setVisibility(View.GONE);
	        
	        // add progress bar to main layout
	        layout_parent =(LinearLayout)findViewById(R.id.layout_SubMenu);
	        layout_parent.addView(custom_progressbar);
			
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

				Log.e("category:", category);
				Log.e("subcategory:", subcategory);
				Log.e("SUBMENU_URL:", SUBMENU_URL);

				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(SUBMENU_URL,
						"POST", params);

				// check your log for json response
				Log.e("JSon", "Fetching JSon");
				Log.e("JSon Response:", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					items = json.getJSONArray(TAG_ITEM);
					receivedItemName = new ArrayList<String>();
					receivedItemCost = new ArrayList<String>();
					receivedItemID  = new ArrayList<String>();
					for (int i = 0; i < items.length(); i++) {
						JSONObject c = items.getJSONObject(i);

						// gets the content of each tag
						String name = c.getString(TAG_ITEM_NAME);
						String cost = c.getString(TAG_ITEM_COST);
						String id = c.getString(TAG_ITEM_ID);
						receivedItemName.add(name);
						receivedItemCost.add(cost);
						receivedItemID.add(id);

					}
					return json.getString(TAG_MESSAGE);
				} else {
					Log.e("SubMenu-Operation Failed !",
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
			//pDialog.dismiss();
			//enable the list and hide the progress bar
			layout_parent.removeView(custom_progressbar);
			list_SubMenu.setVisibility(View.VISIBLE);
			
			Log.e("Post Execute Message", file_url);
			setView();
		}

	}

	private void setView() {

		ListSubMenuAdapter subMenuAdapter = new ListSubMenuAdapter(
				list_SubMenu.getContext(), R.layout.list_sub_menu_item,
				receivedItemName, receivedItemCost, receivedItemID);

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
				Toast.makeText(ListSubMenu.this, "Connection Error",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.d("Connection Error", e.toString());
			lRet = false;
			Toast.makeText(ListSubMenu.this, "Connection Error",
					Toast.LENGTH_SHORT).show();
		}
		return lRet;
	}

}
