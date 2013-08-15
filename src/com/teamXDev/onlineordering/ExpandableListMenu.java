package com.teamXDev.onlineordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class ExpandableListMenu extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.expandablelist_menu);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();

				if (listAdapter.getChildrenCount(groupPosition) == 0) {
					goToDetails(listDataHeader.get(groupPosition));
				}

				return false;
			}

		});

		/*
		 * Listview Group expanded listener
		 * 
		 * expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
		 * 
		 * @Override public void onGroupExpand(int groupPosition) {
		 * Toast.makeText(getApplicationContext(),
		 * listDataHeader.get(groupPosition) + " Expanded",
		 * Toast.LENGTH_SHORT).show(); } });
		 */

		/*
		 * Listview Group collasped listener
		 * expListView.setOnGroupCollapseListener(new OnGroupCollapseListener()
		 * {
		 * 
		 * @Override public void onGroupCollapse(int groupPosition) {
		 * Toast.makeText(getApplicationContext(),
		 * listDataHeader.get(groupPosition) + " Collapsed",
		 * Toast.LENGTH_SHORT).show();
		 * 
		 * } });
		 */

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*
				 * Toast.makeText( getApplicationContext(),
				 * listDataHeader.get(groupPosition) + " : " +
				 * listDataChild.get( listDataHeader.get(groupPosition)).get(
				 * childPosition), Toast.LENGTH_SHORT) .show();
				 */
				 goToDetails(listDataChild.get( listDataHeader.get(groupPosition)).get(childPosition));
				
				return false;
			}
		});

	}

	private void goToDetails(String ItemClicked) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), ListSubMenu.class);
		Bundle b = new Bundle();
		// Inserts a String value into the mapping of this Bundle
		b.putString("ItemClicked", ItemClicked);
		// b.putString("view","List");
		intent.putExtras(b);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

	}
	
	
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Chienese");
		listDataHeader.add("Breads");
		listDataHeader.add("Indian");
		listDataHeader.add("Beverages");
		listDataHeader.add("Biryani");
		listDataHeader.add("Add-Ons");
		listDataHeader.add("Combos");
		listDataHeader.add("Light-Weight");

		// Adding child data
		List<String> Chienese = new ArrayList<String>();
		Chienese.add("Starter");
		Chienese.add("Rice Noodles");
		Chienese.add("Curries");

		List<String> Breads = new ArrayList<String>();

		List<String> Indian = new ArrayList<String>();
		Indian.add("Main Course");
		Indian.add("Starter");

		List<String> Beverages = new ArrayList<String>();

		List<String> Biryani = new ArrayList<String>();
		Biryani.add("Vegetarian");
		Biryani.add("Non-Vegetarian");

		List<String> Combos = new ArrayList<String>();

		List<String> AddOns = new ArrayList<String>();
		AddOns.add("Smokes");
		AddOns.add("Desserts");
		AddOns.add("Hookah Accesories");
		List<String> LightWeight = new ArrayList<String>();

		listDataChild.put(listDataHeader.get(0), Chienese); // Header, Child
															// data
		listDataChild.put(listDataHeader.get(1), Breads);
		listDataChild.put(listDataHeader.get(2), Indian);
		listDataChild.put(listDataHeader.get(3), Beverages);
		listDataChild.put(listDataHeader.get(4), Biryani);
		listDataChild.put(listDataHeader.get(5), AddOns);
		listDataChild.put(listDataHeader.get(6), Combos);
		listDataChild.put(listDataHeader.get(7), LightWeight);

	}
}
