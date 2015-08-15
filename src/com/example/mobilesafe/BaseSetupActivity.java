package com.example.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public abstract class BaseSetupActivity extends Activity {
	
	
	private GestureDetector gd;
	protected SharedPreferences sp;
	
	//在子类中实现切换页面
	public abstract void showNext();
	public abstract void showPref();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		
		gd = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {

					/**
					 * 手势滑动
					 */
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						//如果上下滑动幅度太大就忽略
						if(Math.abs(e2.getRawY()-e1.getRawY())>100){
							Toast.makeText(getApplicationContext(), "滑动无效", Toast.LENGTH_SHORT).show();
							return true;
						}
						//如果滑动速度太慢就忽略
						if(Math.abs(velocityX)<200){
							Toast.makeText(getApplicationContext(), "滑动无效", Toast.LENGTH_SHORT).show();
							return true;
						}
						
						
						if (e2.getRawX() - e1.getRawX() > 200) {
							//显示上一个页面
							showPref();
							return true;
						}
						if(e1.getRawX()-e2.getRawX()>200){
							//显示下一个页面
							showNext();
							return true;
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}

				});
	}
	
	/**
	 * 使用手势识别器
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
