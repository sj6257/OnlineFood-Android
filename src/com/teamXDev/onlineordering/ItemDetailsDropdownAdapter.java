package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemDetailsDropdownAdapter extends BaseAdapter {

	// local variables that will be assigned data from the activity
	ArrayList<String> ItemServingSize = new ArrayList<String>();
	ArrayList<String> ItemCost = new ArrayList<String>();

	// Resource ID of the list
	int resourceID;

	// To inflate the list view
	LayoutInflater inflater;

	// the Context
	Context context;
	
	
	private static class ListItemHolder {
		TextView lbl_ItemServingSize;
		TextView lbl_ItemCost;

	}

	public ItemDetailsDropdownAdapter(Context _ctx, int _rID,
			ArrayList<String> _ItemServingSize, ArrayList<String> _ItemCost) {
		super();

		// receive the info from activity variables to local class variable
		this.ItemServingSize = _ItemServingSize;
		this.ItemCost = _ItemCost;

		// receive the context from the activity
		this.context = _ctx;

		// receive the resource ID of the list
		this.resourceID = _rID;

		// initialize the inflater with the context received
		this.inflater = LayoutInflater.from(_ctx);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ItemServingSize.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	 @Override
     public View getDropDownView(int position, View convertView,ViewGroup parent)
     {
     return getCustomView(position, convertView, parent);
     }

	
	 public View getCustomView(int position, View rowView, ViewGroup parent) {
	 
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
	                 
	         listItem.lbl_ItemServingSize = (TextView) rowView
	                 .findViewById(R.id.lbl_ItemServingSize);
	         listItem.lbl_ItemCost = (TextView) rowView
	                 .findViewById(R.id.lbl_ItemCost);

	         // assigns the tag listItem to the
	         rowView.setTag(listItem);

	     } else {

	         // reading list

	    	 listItem = (ListItemHolder) rowView.getTag();
	     }

	     // setting label values from the received data from the string arrays

	     listItem.lbl_ItemServingSize.setText(ItemServingSize.get(position));
	     listItem.lbl_ItemCost.setText(ItemCost.get(position));
	    
		
		return rowView;
	 }
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return  getCustomView(position,convertView,parent);
	}

}
