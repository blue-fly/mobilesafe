package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.mobilesafe.ui.SettingItemView;

/**
 * 设置向导页面2
 * @author Administrator
 *
 */
public class Setup2Activity extends BaseSetupActivity {
	
	private SettingItemView siv_sim;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		
		siv_sim=(SettingItemView) findViewById(R.id.siv_sim);
		
		String check=sp.getString("sim", "");
		
		//获取手机信息管理器
		final TelephonyManager manager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		
		//初始化选择视图
		if(!TextUtils.isEmpty(check)){
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
					editor.putString("sim", "");
				}else{
					siv_sim.setChecked(true);
					//获取手机序列号
					String sim=manager.getSimSerialNumber();
					editor.putString("sim", sim);
				}
				
				editor.commit();
			}
		});
		
	}
	
	public void next(View view){
		showNext();
	}
	public void pref(View view){
		showPref();
	}

	@Override
	public void showNext() {
		//只有选中了sim卡才能下一步
		if(!siv_sim.isChecked()){
			Toast.makeText(this,"请绑定sim卡",Toast.LENGTH_SHORT).show();
			return;
		}
		Intent i=new Intent(Setup2Activity.this,Setup3Activity.class);
		startActivity(i);
		overridePendingTransition(R.anim.page_next, R.anim.page_exit);
		finish();
	}

	@Override
	public void showPref() {
		Intent i=new Intent(Setup2Activity.this,Setup1Activity.class);
		startActivity(i);
		overridePendingTransition(R.anim.page_pref, 0);
		finish();
	}
}
