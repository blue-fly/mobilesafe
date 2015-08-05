package com.example.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.ui.SettingItemView;

public class SettingActivity extends Activity {
	

	private SettingItemView siv_update;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		//获取SharedPreferences对象，将数据进行存储
		sp=getSharedPreferences("config",MODE_PRIVATE);
		boolean check=sp.getBoolean("check", false);
		
		siv_update=(SettingItemView) findViewById(R.id.siv_update);
		
		if(check){
			siv_update.setChecked(true);
//			siv_update.setDesc("自动更新已经开启");
		}else{
			siv_update.setChecked(false);
//			siv_update.setDesc("自动更新已经关闭");
		}
		
		//设置整个自定义界面的点击事件
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor edit=sp.edit();
				//如果已经点击了
				if(siv_update.isChecked()){
					siv_update.setChecked(false);
//					siv_update.setDesc("自动更新已经关闭");
					edit.putBoolean("check", false);
					
				}else{
					siv_update.setChecked(true);
//					siv_update.setDesc("自动更新已经开启");
					edit.putBoolean("check", true);
				}
				edit.commit();
			}
		});
	}
	

}
