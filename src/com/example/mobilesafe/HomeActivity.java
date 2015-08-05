package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
	private GridView gv_home_gridview;
	private myAdapter adapter;
	
	private String names[]={
		"手机防盗","通讯卫士","软件管理",
        "进程管理","流量统计","手机杀毒",
        "缓存清理","高级工具","设置中心"	
	};
	
	//图片的id
	private int ids[]={
		R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
		R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gv_home_gridview=(GridView) findViewById(R.id.gv_home_gridview);
		adapter=new myAdapter();
		gv_home_gridview.setAdapter(adapter);
		//点击按钮的监听器
		gv_home_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8:
					//设置中心
					Intent i=new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(i);
					break;

				default:
					break;
				}
			}
		});
	}

	private class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=View.inflate(getApplicationContext(), R.layout.list_item_home, null);
			}
			
			//得到各项的参数,肯定需要使用convertView来查找
			ImageView imageView=(ImageView) convertView.findViewById(R.id.iv_item);
			TextView textView=(TextView) convertView.findViewById(R.id.tv_item);
			
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
