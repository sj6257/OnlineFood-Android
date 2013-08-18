package com.teamXDev.onlineordering;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ItemDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		TextView lbl_Item=(TextView)findViewById(R.id.lbl_Item);
		Bundle b = getIntent().getExtras();
		String selecteItem= (String) b.get("clickedItem");
		lbl_Item.setText(selecteItem);
		
	}

	@Override
	public void onBackPressed() {
	    finish();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	
}
