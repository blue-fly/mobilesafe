package com.example.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 设置向导页面3
 * @author Administrator
 *
 */
public class Setup3Activity extends BaseSetupActivity {

	private Button btn_select_contact;
	private EditText et_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		btn_select_contact = (Button) findViewById(R.id.btn_select_contact);
		et_phone= (EditText) findViewById(R.id.et_phone);
		btn_select_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Setup3Activity.this, SelectContactActivity.class);
				startActivityForResult(i, 0);
			}
		});

		//获取安全号码并显示
		String phone=sp.getString("safephone", "");
		et_phone.setText(phone);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null)
			return;
		String phone=data.getStringExtra("data");
		et_phone.setText(phone);
	}

	public void next(View view){
		showNext();
	}
	public void pref(View view){
		showPref();
	}

	@Override
	public void showNext() {
		//判断安全号码是否为空，不为空就存储起来
		String phone=et_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			//为空
			Toast.makeText(this,"请输入安全号码",Toast.LENGTH_SHORT).show();
			return;
		}
		//不为空
		SharedPreferences.Editor editor=sp.edit();
		editor.putString("safephone",phone);
		editor.commit();

		Intent i=new Intent(Setup3Activity.this,Setup4Activity.class);
		startActivity(i);
		overridePendingTransition(R.anim.page_next, R.anim.page_exit);
		finish();
	}

	@Override
	public void showPref() {
		Intent i=new Intent(Setup3Activity.this,Setup2Activity.class);
		startActivity(i);
		overridePendingTransition(R.anim.page_pref, 0);
		finish();
	}


}
