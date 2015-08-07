package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * 手机防盗页面
 * @author Administrator
 *
 */

public class LostFindActivity extends Activity {
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//判断是否进行过设置向导
		boolean seted=sp.getBoolean("seted", false);
		if(seted){
			//设置过，直接停在本页面
			setContentView(R.layout.activity_lost_find);
		}else{
			//没设置过，进入设置向导
			Intent i=new Intent(this,Setup1Activity.class);
			startActivity(i);
			finish();
		}
		
	}
}













