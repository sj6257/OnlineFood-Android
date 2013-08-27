package com.teamXDev.onlineordering;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListSubMenuAdapter extends BaseAdapter {

	ArrayList<String> subMenuItems = new ArrayList<String>();
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
			ArrayList<String> subMenuItems) {
		super();

		// receive the context of list, resource id of list ,list data
		// Assigned this data to class variables
		this.resourceID = rID;
		this.context = ctx;
		this.subMenuItems = subMenuItems;

		// to inflate the layout
		this.inflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		return subMenuItems.size();
	}

	@Override
	public Object getItem(int position) {
		return subMenuItems.get(position);
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
			//listItem.lbl_ItemName = (TextView) convertView.findViewById(R.id.lbl_itemCost);

			convertView.setTag(listItem);
		} else {

			// reading list

			listItem = (ListItemHolder) convertView.getTag();
		}

		// setting label values

		listItem.lbl_ItemName.setText(subMenuItems.get(position));
		//listItem.lbl_ItemCost.setText(subMenuItems.get(position));

		return convertView;
	}
}
