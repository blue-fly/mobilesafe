package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 设置向导页面1
 * 
 * @author Administrator
 * 
 */
public class Setup1Activity extends BaseSetupActivity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	public void next(View view) {
		showNext();
	}


	@Override
	public void showNext() {
		Intent i = new Intent(Setup1Activity.this, Setup2Activity.class);
		startActivity(i);
		finish();
		// 必须在startActivity或finish之后再调用这个过渡动画方法
		overridePendingTransition(R.anim.page_exit, 0);
	}

	@Override
	public void showPref() {
		
	}
	

}
