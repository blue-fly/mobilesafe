package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 手机防盗页面
 * @author Administrator
 *
 */

public class LostFindActivity extends Activity {
	
	private SharedPreferences sp;
	private TextView tv_lost_set;
	
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
		
		tv_lost_set=(TextView) findViewById(R.id.tv_lost_set);
		tv_lost_set.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//重新进入向导
				Intent i=new Intent(LostFindActivity.this,Setup1Activity.class);
				startActivity(i);
			}
		});
		
	}
}













