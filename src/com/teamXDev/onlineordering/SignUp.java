package com.teamXDev.onlineordering;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignUp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		Button btn_Continue=(Button)findViewById(R.id.btn_Continue);
		btn_Continue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToAccontDetails();
			}

			
		});
	}

	private void goToAccontDetails() {
		// TODO Auto-generated method stub
		Intent intent= new Intent(getApplicationContext(),AccountDetails.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}
	
}
