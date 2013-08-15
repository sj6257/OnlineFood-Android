package com.teamXDev.onlineordering;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignInOrSignUp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in_or_sign_up);
		
	    Button btn_SignUp=(Button)findViewById(R.id.btn_SignUp);
	    btn_SignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSignUp();
			}

			
		});
	}

	
	private void goToSignUp() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),SignUp.class);
				//start the DisplayActivity
				startActivity(intent);
	}

}
