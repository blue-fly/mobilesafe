package com.example.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.utils.StreamTools;

public class SplashActivity extends Activity {

	private TextView tv_splash_version;
	private ProgressBar pb_splash_loading;
	private static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int UPDATE_DIALOG = 1;
	protected static final int JSON_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int URL_ERROR = 4;

	private String description;
	private String apkurl;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 进入主界面
			case ENTER_HOME:
				enterHome();
				break;
			// 升级对话框
			case UPDATE_DIALOG:
				showUpdateDialog();
				break;
			// json解析出错
			case JSON_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "json解析出错", 0).show();
				break;
			// 网络异常
			case NETWORK_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "网络异常", 0).show();
				break;
			// URL错误
			case URL_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();
				break;
			}
		}

	};

	/*
	 * 进入主界面
	 */
	protected void enterHome() {
		Intent i = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(i);
		// 关闭当前页面
		finish();
	}

	/*
	 * 弹出升级对话框
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		builder.setMessage(description);
		//builder.setCancelable(false);
		
		//点击返回按键时直接进入主界面
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				dialog.dismiss();
			}
		});
		
		builder.setPositiveButton("马上升级", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载apk，并安装
				// 先判断sdcard的存在
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 存在
					// 使用第三方库afinal
					FinalHttp fh = new FinalHttp();
					// 调用download方法开始下载
					HttpHandler hander = fh.download(apkurl, Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/mobilesafe2.0.apk", true,
							new AjaxCallBack<File>() {

								// 下载失败时调用
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									super.onFailure(t, errorNo, strMsg);
									Toast.makeText(getApplicationContext(),
											"下载失败", 1).show();
								}

								// 下载时显示进度用
								@Override
								public void onLoading(long count, long current) {
									super.onLoading(count, current);
									pb_splash_loading = (ProgressBar) findViewById(R.id.pb_splash_loading);
									pb_splash_loading
											.setProgress((int) (current * 100 / count));
								}

								@Override
								public void onSuccess(File t) {
									super.onSuccess(t);
									// 成功时进行替换安装
									installAPK(t);
								}

								/*
								 * 自动替换apk的核心代码
								 */
								private void installAPK(File t) {
									Intent intent = new Intent();
									intent.setAction("android.intent.action.INSTALL_PACKAGE");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t),
											"application/vnd.android.package-archive");
									startActivity(intent);
								}

							});
				} else {
					// 不存在
					Toast.makeText(getApplicationContext(), "请安装好SDcard后重试", 1)
							.show();
					return;
				}

			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 进入主界面
				enterHome();
			}
		});
		builder.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText(getVersionName());
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
		checkUpdate();
	}

	/**
	 * 得到应用程序的版本名称
	 */
	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return "版本号:" + info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 检查升级，在子线程中联网
	 */
	private void checkUpdate() {

		new Thread() {

			public void run() {
				long startTime = System.currentTimeMillis();
				Message msg = new Message();
				try {
					URL url = new URL(getString(R.string.serverurl));
					// 联网
					try {
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(4000);
						// 得到返回码
						int code = conn.getResponseCode();
						if (code == 200) {
							// 联网成功
							InputStream is = conn.getInputStream();
							// 把流转成string
							String result = StreamTools.readFromStream(is);
							Log.d(TAG, result);
							// 解析json
							try {
								JSONObject obj = new JSONObject(result);
								String version = (String) obj.get("version");

								description = (String) obj.get("description");
								apkurl = (String) obj.get("apkurl");

								// 校验是否发现新版本
								if (getVersionName().equals(version)) {
									// 进入主界面
									msg.what = ENTER_HOME;

								} else {
									// 弹出升级对话框
									// 异步更新
									msg.what = UPDATE_DIALOG;
								}

							} catch (JSONException e) {
								msg.what = JSON_ERROR;
								e.printStackTrace();
							}

						} else {
							Log.d(TAG, "联网失败");
						}

					} catch (IOException e) {
						msg.what = NETWORK_ERROR;
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					msg.what = URL_ERROR;
					e.printStackTrace();
				}

				long endTime = System.currentTimeMillis();
				long time = endTime - startTime;
				if (time < 3) {
					try {
						Thread.sleep(3 - time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				handler.sendMessage(msg);

			};

		}.start();
	}
}
