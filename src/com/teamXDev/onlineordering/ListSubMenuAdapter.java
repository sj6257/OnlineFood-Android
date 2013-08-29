package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListSubMenuAdapter extends BaseAdapter {

	ArrayList<String> subMenuItemName = new ArrayList<String>();
	ArrayList<String> subMenuItemCost = new ArrayList<String>();
	ArrayList<String> subMenuItemID = new ArrayList<String>();
	// Resource ID of the list
	int resourceID;

	// To inflate the list view
	LayoutInflater inflater;

	// the Context
	Context context;

	private static class ListItemHolder {
		TextView lbl_ItemName;
		TextView lbl_ItemCost;

	}

	public ListSubMenuAdapter(Context ctx, int rID,
			ArrayList<String> subMenuItemName,ArrayList<String> subMenuItemCost ,ArrayList<String> subMenuItemID) {
		super();

		// receive the context of list, resource id of list ,list data
		// Assigned this data to class variables
		this.resourceID = rID;
		this.context = ctx;
		this.subMenuItemName = subMenuItemName;
		this.subMenuItemCost= subMenuItemCost;
         this.subMenuItemID=subMenuItemID ;
		// to inflate the layout
		this.inflater = LayoutInflater.from(ctx);
	}

	// custom function to get id to query server
	public Object getCustomID(int position)
	{
		return subMenuItemID.get(position);
		
	}
	
	// custom function to get cost of item
	public Object getItemCost(int position)
	{
		return subMenuItemCost.get(position);
	
	}
	
	
	@Override
	public int getCount() {
		return subMenuItemName.size();
	}

	@Override
	public Object getItem(int position) {
		return subMenuItemName.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
		// return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ListItemHolder listItem;
		if (convertView == null) {

			// executes when first creating list

			LayoutInflater vInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vInflater.inflate(resourceID, parent, false);
			listItem = new ListItemHolder();
			listItem.lbl_ItemName = (TextView) convertView
					.findViewById(R.id.lbl_itemName);
			listItem.lbl_ItemCost = (TextView) convertView.findViewById(R.id.lbl_itemCost);

			convertView.setTag(listItem);
		} else {

			// reading list

			listItem = (ListItemHolder) convertView.getTag();
		}

		// setting label values

		listItem.lbl_ItemName.setText(subMenuItemName.get(position));
		listItem.lbl_ItemCost.setText(subMenuItemCost.get(position));

		return convertView;
	}
}
