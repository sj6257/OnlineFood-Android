package com.teamXDev.onlineordering;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListSubMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sub_menu);
		
		TextView  lbl_SubMenu=(TextView)findViewById(R.id.lbl_SubMenu);
		Bundle b = getIntent().getExtras();
		String selecteItem= (String) b.get("ItemClicked");
		lbl_SubMenu.setText(selecteItem);
		
		ListView list_SubMenu = (ListView) findViewById(R.id.list_SubMenu);
		
		list_SubMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// TextView text1 = (TextView) view.findViewById(android.R.id.);
				// Log.i(TAG,"You Clicked:"+ text1.getText().toString());
				

					//ListViewCustomAdapter lAdapter=(ListViewCustomAdapter)parent.getAdapter();
				//	String clickedItem=(String) lAdapter.getItem(position);		 
				   String clickedItem="Sample";
					//Create a bundle object
					Bundle b = new Bundle();

					//Inserts a String value into the mapping of this Bundle
					b.putString("clickedItem",clickedItem);
					// b.putString("age", age.getText().toString()); we can put as many parameters we need

					Intent intent=new Intent(getApplicationContext(),ItemDetails.class);
					//Add the bundle to the intent.
					intent.putExtras(b);

					//start the DisplayActivity
					startActivity(intent);
				
			}
		});
		
		
		lbl_SubMenu.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 String clickedItem="Item";
					//Create a bundle object
					Bundle b = new Bundle();

					//Inserts a String value into the mapping of this Bundle
					b.putString("clickedItem",clickedItem);
					// b.putString("age", age.getText().toString()); we can put as many parameters we need

					Intent intent=new Intent(getApplicationContext(),ItemDetails.class);
					//Add the bundle to the intent.
					intent.putExtras(b);

					//start the DisplayActivity
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				return false;
			}
		});
		
		
	}

	@Override
	public void onBackPressed() {
	    finish();
	    overridePendingTransition(R.anim.appear, R.anim.push_right_out);
	}

	
	
}
