package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 设置向导页面3
 * @author Administrator
 *
 */
public class Setup3Activity extends BaseSetupActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}
	
	public void next(View view){
		showNext();
	}
	public void pref(View view){
		showPref();
	}

	@Override
	public void showNext() {
		Intent i=new Intent(Setup3Activity.this,Setup4Activity.class);
		startActivity(i);
		finish();
		overridePendingTransition(R.anim.page_exit, 0);
	}

	@Override
	public void showPref() {
		Intent i=new Intent(Setup3Activity.this,Setup2Activity.class);
		startActivity(i);
		finish();
		overridePendingTransition(R.anim.page_jump, 0);
	}
}
