package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

/**
 * 设置向导页面1
 * @author Administrator
 *
 */
public class Setup4Activity extends Activity {
	
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		sp=getSharedPreferences("config", MODE_PRIVATE);
	}
	
	public void next(View view){
		Editor editor=sp.edit();
		editor.putBoolean("seted", true);
		editor.commit();
		Intent i=new Intent(Setup4Activity.this,LostFindActivity.class);
		startActivity(i);
		finish();
	}
	
	public void pref(View view){
		Intent i=new Intent(Setup4Activity.this,Setup3Activity.class);
		startActivity(i);
		finish();
	}
}
