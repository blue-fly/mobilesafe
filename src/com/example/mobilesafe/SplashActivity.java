package com.example.mobilesafe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.utils.StreamTools;

public class SplashActivity extends Activity {

	private TextView tv_splash_version;
	private static final String TAG="SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int UPDATE_DIALOG = 1;
	protected static final int JSON_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int URL_ERROR = 4;
	
	private String description;
	private String apkurl;
	
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			//进入主界面
			case ENTER_HOME:
				enterHome();
				break;
			//升级对话框
			case UPDATE_DIALOG:
				enterHome();
				Log.d(TAG, "升级哦");
				break;
			//json解析出错
			case JSON_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "json解析出错", 0).show();
				break;
			//网络异常
			case NETWORK_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "网络异常", 0).show();
				break;
			//URL错误
			case URL_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();
				break;
			}
		}

	} ;
	
	/*
	 * 进入主界面
	 */
	private void enterHome() {
		Intent i=new Intent(SplashActivity.this,HomeActivity.class);
		startActivity(i);
		//关闭当前页面
		finish();
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version=(TextView) findViewById(R.id.tv_splash_version);
        tv_splash_version.setText(getVersionName());
        AlphaAnimation aa=new AlphaAnimation(0.2f,1.0f);
        aa.setDuration(1000);
        findViewById(R.id.rl_root_splash).startAnimation(aa);
        checkUpdate();
    }
    
    /**
     * 得到应用程序的版本名称
     */
    private String getVersionName(){
    	PackageManager pm=getPackageManager();
    	try {
			PackageInfo info=pm.getPackageInfo(getPackageName(), 0);
			return "版本号："+info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
    }

    /*
     * 检查升级，在子线程中联网
     */
    private void checkUpdate(){
    	
    	new Thread(){
    		
    		public void run() {
    			long startTime=System.currentTimeMillis();
    			Message msg=new Message();
    			try {
					URL url=new URL(getString(R.string.serverurl));
					//联网
					try {
						HttpURLConnection conn=(HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(4000);
						//得到返回码
						int code=conn.getResponseCode();
						if(code==200){
							//联网成功
							InputStream is=conn.getInputStream();
							//把流转成string
							String result=StreamTools.readFromStream(is);
							Log.d(TAG,result);
							//解析json
							try {
								JSONObject obj=new JSONObject(result);
								String version=(String) obj.get("version");
								
								description=(String) obj.get("description");
								apkurl=(String) obj.get("apkurl");
								
								//校验是否发现新版本
								if(getVersionName().equals(version)){
									//进入主界面
									msg.what=ENTER_HOME;
									
								}else{
									//弹出升级对话框
									//异步更新
									msg.what=UPDATE_DIALOG;
								}
								
							} catch (JSONException e) {
								msg.what=JSON_ERROR;
								e.printStackTrace();
							}
							
							
						}else{
							Log.d(TAG,"联网失败");
						}
						
					} catch (IOException e) {
						msg.what=NETWORK_ERROR;
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					msg.what=URL_ERROR;
					e.printStackTrace();
				}
    			
    			long endTime=System.currentTimeMillis();
    			long time=endTime-startTime;
    			if(time<3){
    				try {
						Thread.sleep(3-time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    			}
    			handler.sendMessage(msg);
    			
    		};
    		
    	}.start();
    }
}
