package com.teamXDev.onlineordering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetails extends Activity {

	Button btn_AddToOrder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		TextView lbl_Item=(TextView)findViewById(R.id.lbl_Item);
		Bundle b = getIntent().getExtras();
		String selecteItem= (String) b.get("clickedItem");
		lbl_Item.setText(selecteItem);
		
		btn_AddToOrder=(Button)findViewById(R.id.btn_AddToOrder);
		
		btn_AddToOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addItemAndGoToMenu();
			}

			
		});
		
		
	}
	
	
	private void addItemAndGoToMenu() {
		// TODO Auto-generated method stub
		Intent i=new Intent(getApplicationContext(),ExpandableListMenu.class);
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
