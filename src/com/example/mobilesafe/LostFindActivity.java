package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
	private TextView tv_lost_again;
	private TextView tv_lost_lock;
	private TextView tv_lost_phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//判断是否进行过设置向导
		boolean seted=sp.getBoolean("seted", false);
		if(seted){
			//设置过，直接停在本页面
			setContentView(R.layout.activity_lost_find);
			tv_lost_again=(TextView) findViewById(R.id.tv_lost_again);
			tv_lost_lock= (TextView) findViewById(R.id.tv_lost_lock);
			tv_lost_phone= (TextView) findViewById(R.id.tv_lost_phone);

			String phone=sp.getString("safephone","");
			Boolean lock=sp.getBoolean("lock",false);

			tv_lost_phone.setText(phone);
			if(lock){
				tv_lost_lock.setBackgroundResource(R.drawable.lock);
			}else{
				tv_lost_lock.setBackgroundResource(R.drawable.unlock);
			}


			tv_lost_again.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//重新进入向导
					Intent i = new Intent(LostFindActivity.this, Setup1Activity.class);
					startActivity(i);
					finish();
				}
			});
		}else{
			//没设置过，进入设置向导
			Intent i=new Intent(this,Setup1Activity.class);
			startActivity(i);
		}
		
		
		
	}
}













