package com.teamXDev.onlineordering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FinalOrder extends Activity {
	
	Button btn_CheckOut;
    Boolean logedIn=false;
    SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_order);
		
	    btn_CheckOut=(Button)findViewById(R.id.btn_CheckOut);
	    session=new SessionManager(getApplicationContext());
	    
	    
	    btn_CheckOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(session.isLoggedIn())
				goToPlaceOrder();
				else
				goToSignInOrSignUp();
			}

			
		});
		
	}
	
	private void goToSignInOrSignUp() {

		String from="activity_FinalOrder";
		
		Bundle b = new Bundle();
		b.putString("from", from);
		
		Intent intent = new Intent(getApplicationContext(),SignInOrSignUp.class);
		intent.putExtras(b);
		startActivity(intent);
	}


	private void goToPlaceOrder() {
		// TODO Auto-generated method stub
		
		Intent intent=new Intent(getApplicationContext(),PlaceOrder.class);
		startActivity(intent);
		
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}
	
	@Override
	public void onBackPressed() {
	    finish();

		Intent intent = new Intent(getApplicationContext(),ExpandableListMenu.class);
		startActivity(intent);
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
}
