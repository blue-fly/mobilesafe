package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 设置向导页面1
 * @author Administrator
 *
 */
public class Setup1Activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	
	public void next(View view){
		Intent i=new Intent(Setup1Activity.this,Setup2Activity.class);
		startActivity(i);
		finish();
	}
}
