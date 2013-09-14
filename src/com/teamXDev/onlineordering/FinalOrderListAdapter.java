package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FinalOrderListAdapter extends BaseAdapter {

	// local variables that will be assigned data from the activity
	ArrayList<String> orderedItemName = new ArrayList<String>();
	ArrayList<String> orderedItemQty = new ArrayList<String>();
	ArrayList<String> orderedItemServingSize = new ArrayList<String>();
	ArrayList<String> orderedItemTotalCost = new ArrayList<String>();

	// Resource ID of the list
	int resourceID;

	// To inflate the list view
	LayoutInflater inflater;

	// the Context
	Context context;

	// Template Class of Graphic Elements which list Item will hold
	private static class ListItemHolder {

		TextView lbl_itemName, lbl_itemQty, lbl_itemServingSize,
				lbl_itemTotalCost;

	}

	// constructor which receives the context, list Resource ID along with data
	// to be displayed from the activity class

	public FinalOrderListAdapter(Context _ctx, int _rID,
			ArrayList<String> _orderedItemName,
			ArrayList<String> _orderedItemQty,
			ArrayList<String> _orderedItemServingSize,
			ArrayList<String> _orderedItemTotalCost) {
		super();

		// receive the info from activity variables to local class variable
		this.orderedItemName = _orderedItemName;
		this.orderedItemQty = _orderedItemQty;
		this.orderedItemServingSize = _orderedItemServingSize;
		this.orderedItemTotalCost = _orderedItemTotalCost;

		// receive the context from the activity
		this.context = _ctx;

		// receive the resource ID of the list
		this.resourceID = _rID;

		// initialize the inflater with the context received
		this.inflater = LayoutInflater.from(_ctx);

	}

	@Override
	public int getCount() {
		return orderedItemName.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	     // This view will inflate UI and add the list Item to it
	 View rowView = convertView;

	  ListItemHolder listItem=new ListItemHolder();
	     /*
	      * rowView is layout of the Child Item of list parent is the parent
	      * of list view layout position of the list Item
	      */

	     if (rowView== null) {
	         // if this view is null its first Item of list that needs to be
	         // created

	         // get layout inflater
	         LayoutInflater vInflater = (LayoutInflater) context
	                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	         rowView= vInflater.inflate(resourceID, parent, false);

	         /*
	          * Initialize the Item holder class view variables with the view
	          * elements of convert view i.e. nothing but list child item layout
	          */
	         
	         listItem.lbl_itemName = (TextView) rowView
	                 .findViewById(R.id.lbl_itemName);
	         listItem.lbl_itemQty = (TextView) rowView
	                 .findViewById(R.id.lbl_itemQty);
	         listItem.lbl_itemServingSize = (TextView) rowView
	                 .findViewById(R.id.lbl_itemServingSize);
	         listItem.lbl_itemTotalCost = (TextView) rowView
	                 .findViewById(R.id.lbl_itemTotalCost);

	         // assigns the tag listItem to the
	         rowView.setTag(listItem);

	     } else {

	         // reading list

	    	 listItem = (ListItemHolder) rowView.getTag();
	     }

	     // setting label values from the received data from the string arrays

	     listItem.lbl_itemName.setText(orderedItemName.get(position));
	     listItem.lbl_itemQty.setText(orderedItemQty.get(position));
	     listItem.lbl_itemServingSize.setText(orderedItemServingSize.get(position));
	     listItem.lbl_itemTotalCost.setText(orderedItemTotalCost.get(position));


	     return rowView;
	 }
}
