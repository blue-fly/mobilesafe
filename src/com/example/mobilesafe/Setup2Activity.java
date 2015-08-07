package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.ui.SettingItemView;

/**
 * 设置向导页面2
 * @author Administrator
 *
 */
public class Setup2Activity extends Activity {
	
	private SettingItemView siv_sim;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		
		siv_sim=(SettingItemView) findViewById(R.id.siv_sim);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean check=sp.getBoolean("sim", false);
		
		//初始化选择视图
		if(check){
			siv_sim.setChecked(true);
			siv_sim.setDesc("sim卡已经绑定");
		}else{
			siv_sim.setChecked(false);
			siv_sim.setDesc("sim卡没有绑定");
		}
		
		siv_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor=sp.edit();
				
				//判断是否是选中状态，如果是，则取消选中
				if(siv_sim.isChecked()){
					siv_sim.setChecked(false);
					editor.putBoolean("sim", false);
				}else{
					siv_sim.setChecked(true);
					editor.putBoolean("sim", true);
				}
				
				editor.commit();
			}
		});
		
	}
	
	public void next(View view){
		Intent i=new Intent(Setup2Activity.this,Setup3Activity.class);
		startActivity(i);
		finish();
	}
	public void pref(View view){
		Intent i=new Intent(Setup2Activity.this,Setup1Activity.class);
		startActivity(i);
		finish();
	}
}
