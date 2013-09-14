package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FinalOrder extends Activity {

	private static final String TAG = "FinalOrder";
	Button btn_CheckOut, btn_Apply;
	TextView txt_coupneCode, txt_subTotal, txt_finalTotal, txt_taxesFees,
			txt_couponOff;

	ListView lView_finalOrder;

	ArrayList<String> orderedItemName = new ArrayList<String>();
	ArrayList<String> orderedItemQty = new ArrayList<String>();
	ArrayList<String> orderedItemServingSize = new ArrayList<String>();
	ArrayList<String> orderedItemTotalCost = new ArrayList<String>();

	Boolean logedIn = false;
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_order);

btn_CheckOut = (Button) findViewById(R.id.btn_CheckOut);
		btn_Apply = (Button) findViewById(R.id.btn_apply);
		session = new SessionManager(getApplicationContext());

		txt_coupneCode = (TextView) findViewById(R.id.txt_couponCode);
		txt_finalTotal = (TextView) findViewById(R.id.txt_FinalTotal);
		txt_subTotal = (TextView) findViewById(R.id.txt_subTotal);
		txt_couponOff = (TextView) findViewById(R.id.txt_couponOff);
		txt_taxesFees = (TextView) findViewById(R.id.txt_taxesfees);
		lView_finalOrder = (ListView) findViewById(R.id.lView_FinalOrder);


		createOrderedItemView(); 
		createOrderSummary();

		btn_CheckOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (session.isLoggedIn())
					goToPlaceOrder();
				else
					goToSignInOrSignUp();
			}

		});

		btn_Apply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				applyCoupneCode();
			}

		});

	}

	private void goToSignInOrSignUp() {

		String from = "activity_FinalOrder";

		Bundle b = new Bundle();
		b.putString("from", from);

		Intent intent = new Intent(getApplicationContext(),
				SignInOrSignUp.class);
		intent.putExtras(b);
		startActivity(intent);
	}

	private void goToPlaceOrder() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getApplicationContext(), PlaceOrder.class);
		startActivity(intent);

		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public void createOrderedItemView() {
		ArrayList<ArrayOrderItemTemplate> listOfOrderedItem = fetchOrderedItem();

		for (ArrayOrderItemTemplate item : listOfOrderedItem) {

			if(item.itemName!=null &  item.itemServingSize!=null)
			{
			orderedItemName.add(item.itemName);
			orderedItemQty.add(String.valueOf(item.itemQty));
			orderedItemServingSize.add(item.itemServingSize);
			orderedItemTotalCost.add(String.valueOf(item.itemTotalCost));
			
			Log.e("itemName to Array",item.itemName);
			Log.e("itemQty to Array",String.valueOf(item.itemQty));
			Log.e("itemServingSize to Array",item.itemServingSize);
			Log.e("itemTotalCost to Array",String.valueOf(item.itemTotalCost));
			}
		}

		if(orderedItemName.size()>0)
		setView();
	}

	public ArrayList<ArrayOrderItemTemplate> fetchOrderedItem() {

		ArrayList<ArrayOrderItemTemplate> list = new ArrayList<ArrayOrderItemTemplate>();
		String sql = "select item_name,item_unit_cost,item_qty,item_total_cost,item_serving_size from order_details";
		Log.i(TAG, "Creating Adapter for Fetching Data");
		OnlineOrderingDataBaseAdapter mDBAdapter = new OnlineOrderingDataBaseAdapter(
				this);
		Log.i(TAG, "Adapter Ready..");
		Log.i(TAG, "Creating/Opening Database");
		mDBAdapter.createDatabase();
		mDBAdapter.open();
		Log.i(TAG, "Requesting info from getInfo function");
		list = mDBAdapter.getOrderItemIfo(sql);
		Log.i(TAG, "Information Retrived Passing it to SetView");
		mDBAdapter.close();
		return list;
	}

	/*
	 * public void addItemToView(String _itemName, int _itemQty, Double
	 * _itemTotalPrice) { final LayoutInflater inflater = (LayoutInflater)
	 * getSystemService(Context.LAYOUT_INFLATER_SERVICE); LinearLayout
	 * layout_ChildItem = (LinearLayout) inflater.inflate(
	 * R.layout.final_order_child_item, null);
	 * 
	 * TextView itemName = (TextView) layout_ChildItem
	 * .findViewById(R.id.lbl_itemName); TextView itemQty = (TextView)
	 * layout_ChildItem .findViewById(R.id.lbl_itemQty); TextView itemTotalPrice
	 * = (TextView) layout_ChildItem .findViewById(R.id.lbl_itemTotalCost);
	 * 
	 * itemName.setText(_itemName); itemQty.setText(String.valueOf(_itemQty));
	 * itemTotalPrice.setText(String.valueOf(_itemTotalPrice));
	 * 
	 * // add progress bar to main layout LinearLayout layout_parent =
	 * (LinearLayout) findViewById(R.id.layout_itemDetails);
	 * layout_parent.addView(layout_ChildItem);
	 * 
	 * }
	 */

	private void setView() {

		FinalOrderListAdapter fItemAdapter = new FinalOrderListAdapter(
				lView_finalOrder.getContext(), R.layout.final_order_child_item,
				orderedItemName, orderedItemQty, orderedItemServingSize,
				orderedItemTotalCost);
		
		Log.e("setView","setting Adapter");

		lView_finalOrder.setAdapter(fItemAdapter);

	}

	public void createOrderSummary() {

		// apply the tax in % percentage
		// applyTaxesFees
		Double tax = 3.0;
		OnlineOrderingDataBaseAdapter mDBAdapter = new OnlineOrderingDataBaseAdapter(
				this);
		Log.i(TAG, "Adapter Ready..");
		Log.i(TAG, "Creating/Opening Database");
		mDBAdapter.createDatabase();
		mDBAdapter.open();
		Log.i(TAG, "Applying Taxes");
		mDBAdapter.applyTaxesFees(tax);
		Log.i(TAG, "Tax Applied");

		// get the subTotal
		double subTotal = mDBAdapter
				.getDoubleValue("select sub_total_amount from order_summary");

		// display subTotal
		showSubTotal(subTotal);

		// get the Tax
		double taxesFees = mDBAdapter
				.getDoubleValue("select taxes_fees_amount from order_summary");

		// display final total
		showTaxesFeesTotal(taxesFees);

		// get the finalTotal
		double finalTotal = mDBAdapter
				.getDoubleValue("select final_amount from order_summary");

		// display final total
		showFinalTotal(finalTotal);

		// get Coupon Code Value
		double couponOff = mDBAdapter
				.getDoubleValue("select coupne_code_deduction from order_summary");
		// show Coupon Code Value
		showCouponCode(couponOff);

		mDBAdapter.close();
	}

	private void showTaxesFeesTotal(double taxesFees) {
		txt_taxesFees.setText(String.valueOf(taxesFees));

	}

	private void showFinalTotal(double finalTotal) {
		txt_finalTotal.setText(String.valueOf(finalTotal));
	}

	private void showSubTotal(double subTotal) {
		txt_subTotal.setText(String.valueOf(subTotal));
	}

	private void applyCoupneCode() {

		double coupneCodeAmount = validateCouponCode();

		if (validateCouponCode() > 0) {

			OnlineOrderingDataBaseAdapter mDBAdapter = new OnlineOrderingDataBaseAdapter(
					this);
			Log.i(TAG, "Adapter Ready..");
			Log.i(TAG, "Creating/Opening Database");
			mDBAdapter.createDatabase();
			mDBAdapter.open();

			// apply coupon code

			Log.i(TAG, "Applying Coupon Code");
			mDBAdapter.applyCoupneCodeInDb(coupneCodeAmount);
			Log.i(TAG, "Coupon Code");

			// get Coupon Code Value
			double couponOff = mDBAdapter
					.getDoubleValue("select coupne_code_deduction from order_summary");

			// Since Coupon Code will Change the Final Amount Refresh FinalTotal
			double finalTotal = mDBAdapter
					.getDoubleValue("select final_amount from order_summary");

			mDBAdapter.close();

			// show Coupon Code Value
			showCouponCode(couponOff);

			// display final total
			showFinalTotal(finalTotal);
		} else {

			Toast.makeText(getApplicationContext(), "Coupon Code Incorrect !",
					Toast.LENGTH_SHORT).show();
		}

	}

	private double validateCouponCode() {
		// TODO Auto-generated method stub
		return 30.0;
	}

	private void showCouponCode(double couponOff) {

		txt_couponOff.setText(String.valueOf(couponOff));
	}

	@Override
	public void onBackPressed() {
		finish();

		Intent intent = new Intent(getApplicationContext(),
				ExpandableListMenu.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
