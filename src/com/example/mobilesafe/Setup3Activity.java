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
public class Setup3Activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}
	
	public void next(View view){
		Intent i=new Intent(Setup3Activity.this,Setup4Activity.class);
		startActivity(i);
		finish();
	}
	public void pref(View view){
		Intent i=new Intent(Setup3Activity.this,Setup2Activity.class);
		startActivity(i);
		finish();
	}
}
