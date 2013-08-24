package com.teamXDev.onlineordering;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class PlaceOrder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_order);
		
		
	}

	
	
	
	private void goToSignInOrSignUp() {

		String from="activity_PlaceOrder";
		
		Bundle b = new Bundle();
		b.putString("from", from);
		
		Intent intent = new Intent(getApplicationContext(),SignInOrSignUp.class);
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
	    finish();

		Intent intent = new Intent(getApplicationContext(),FinalOrder.class);
		startActivity(intent);
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	

}
