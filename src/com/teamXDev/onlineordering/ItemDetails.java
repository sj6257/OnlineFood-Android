package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ItemDetails extends Activity {

	private static final String TAG = "ItemDetails";
	Button btn_AddToOrder;
	EditText txt_itemQty;
	String itemID, itemName, itemRegularCost, itemQty,itemSmallCost,itemLargeCost;
	ArrayList<String> itemServingSizeArray, itemCostArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		TextView lbl_ItemName = (TextView) findViewById(R.id.lbl_Item);
		// TextView lbl_ItemCost=(TextView)findViewById(R.id.txt_ServingSize);

		txt_itemQty = (EditText) findViewById(R.id.txt_NoOfOrder);

		Bundle b = getIntent().getExtras();

		itemID = (String) b.get("itemID");
		itemName = (String) b.get("itemName");
		itemRegularCost = (String) b.get("itemCost");
		
		//itemSmallCost = (String) b.get("itemSmallCost");
		//itemLargeCost = (String) b.get("itemLargeCost");

		itemSmallCost="50S";
		itemLargeCost="60L";
		
		lbl_ItemName.setText(itemName);
		// lbl_ItemCost.setText("REGULAR   "+itemCost);
		// default quantity of item
		txt_itemQty.setText("1");
		
		itemServingSizeArray=new ArrayList<String>();
		
		itemServingSizeArray.add("SMALL");
		itemServingSizeArray.add("REGULAR");
		itemServingSizeArray.add("LARGE");
		
		itemCostArray=new ArrayList<String>();
		
		itemCostArray.add(itemSmallCost);
		itemCostArray.add(itemRegularCost);
		itemCostArray.add(itemLargeCost);

		Spinner dropdown_ServingSize = (Spinner) findViewById(R.id.dropdown_ServingSize);

		ItemDetailsDropdownAdapter dAdapter = new ItemDetailsDropdownAdapter(
				getApplicationContext(), R.layout.dropdownlist_child_item,
				itemServingSizeArray, itemCostArray);

		dropdown_ServingSize.setAdapter(dAdapter);
		
		

		btn_AddToOrder = (Button) findViewById(R.id.btn_AddToOrder);

		btn_AddToOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addItemAndGoToMenu();
			}

		});

	}

	private void addItemAndGoToMenu() {

		itemQty = txt_itemQty.getText().toString();
		int _itemQty;

		if (itemQty.isEmpty())
			_itemQty = 1;
		else
			_itemQty = Integer.valueOf(itemQty);

		double _itemCost = Double.valueOf(itemRegularCost);
		OnlineOrderingDataBaseAdapter mDBAdapter = new OnlineOrderingDataBaseAdapter(
				this);
		Log.i(TAG, "Adapter Ready..");
		Log.i(TAG, "Creating/Opening Database");
		mDBAdapter.createDatabase();
		mDBAdapter.open();
		// String orderID,String itemName,String itemServingSize,int
		// itemQty,double itemUnitCost
		mDBAdapter.addItemToOrder("OR1234", itemName, "REGULAR", _itemQty,
				_itemCost);
		mDBAdapter.close();
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), ExpandableListMenu.class);
		startActivity(i);

		// for coming back
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
