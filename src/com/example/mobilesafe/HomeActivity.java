package com.example.mobilesafe;

import com.example.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends Activity {

	private static final String TAG = "HomeActivity";

	private GridView gv_home_gridview;
	private myAdapter adapter;
	private SharedPreferences sp;

	private String names[] = { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };

	// 图片的id
	private int ids[] = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		gv_home_gridview = (GridView) findViewById(R.id.gv_home_gridview);
		adapter = new myAdapter();
		gv_home_gridview.setAdapter(adapter);
		// 点击按钮的监听器
		gv_home_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8:
					// 设置中心
					Intent i = new Intent(HomeActivity.this,
							SettingActivity.class);
					startActivity(i);
					break;
				case 0:
					// 手机防盗
					showLostFindDialog();
					break;
				default:
					break;
				}
			}
		});
	}

	/*
	 * 加载自定义对话框
	 */
	protected void showLostFindDialog() {
		// 使用sharedPreferences存储
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if (isSetupPass()) {
			// 有过密码设置
			showEnterPassword();
		} else {
			showSetupPassword();
		}

	}

	private EditText et_setup;
	private EditText et_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;

	/*
	 * 提交密码对话框
	 */
	private void showSetupPassword() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = null;

		// 设置对话框的布局文件
		view = View.inflate(HomeActivity.this, R.layout.dialog_setup_password,
				null);

		// 设置按钮的点击事件以及密码的保存
		et_setup = (EditText) view.findViewById(R.id.et_setup);
		et_confirm = (EditText) view.findViewById(R.id.et_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);

		// 取消
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// 确定
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 对密码输入的非空判断
				String setup = et_setup.getText().toString().trim();
				String confirm = et_confirm.getText().toString().trim();
				if (TextUtils.isEmpty(setup) || TextUtils.isEmpty(confirm)) {
					Toast.makeText(HomeActivity.this, "密码不能为空", 1).show();
					return;
				}
				// 对两次输入的一致性进行判断
				if (setup.equals(confirm)) {
					// 进行保存
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.md5Password(setup));
					editor.commit();
					dialog.dismiss();
					// 然后进入防盗页面
					Intent i = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(i);
				} else {
					et_setup.setText("");
					et_confirm.setText("");
					Toast.makeText(HomeActivity.this, "两次密码不一致", 1).show();
				}

			}
		});

		builder.setView(view);
		dialog = builder.show();
	}

	/*
	 * 输入密码对话框
	 */
	private void showEnterPassword() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = null;

		// 设置对话框的布局文件
		view = View.inflate(HomeActivity.this, R.layout.dialog_enter_password,
				null);

		// 设置按钮的点击事件以及密码的保存
		et_setup = (EditText) view.findViewById(R.id.et_setup);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);

		// 取消
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// 确定
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 对密码输入的非空判断
				String setup = et_setup.getText().toString().trim();
				if (TextUtils.isEmpty(setup)) {
					Toast.makeText(HomeActivity.this, "密码不能为空", 1).show();
					return;
				}

				// 将存储的数据读出并校验
				String value = sp.getString("password", null);
				if (value.equals(MD5Utils.md5Password(setup))) {
					// 密码正确进入防盗页面
					dialog.dismiss();
					Intent i = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(i);
				} else {
					// 密码错误提示
					Toast.makeText(HomeActivity.this, "密码错误", 0).show();
				}
			}
		});

		builder.setView(view);
		dialog = builder.show();

	}

	/*
	 * 判断是否有密码设置 有的话返回true
	 */
	private boolean isSetupPass() {
		// 进行校验是否已经有密码设置过
		String pass = sp.getString("password", null);
		return !TextUtils.isEmpty(pass);
	}

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.list_item_home, null);
			}

			// 得到各项的参数,肯定需要使用convertView来查找
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.iv_item);
			TextView textView = (TextView) convertView
					.findViewById(R.id.tv_item);

			imageView.setImageResource(ids[position]);
			textView.setText(names[position]);
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
}
