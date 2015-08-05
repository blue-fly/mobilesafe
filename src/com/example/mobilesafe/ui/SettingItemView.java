package com.example.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

/**
 * 我们自定义的组合控件
 * 
 * @author Administrator
 * 
 */
public class SettingItemView extends RelativeLayout {

	private TextView tv_title;
	private TextView tv_desc;
	private CheckBox cb_status;

	/*
	 * 初始化视图
	 */
	private void initView(Context context) {
		View.inflate(context, R.layout.setting_item_view, this);

		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

	/*
	 * 检验组合控件是否有焦点
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}

	/*
	 * 设置组合控件的状态
	 */
	public void setChecked(boolean check) {
		cb_status.setChecked(check);
	}

	/*
	 * 设置内容更改
	 */
	public void setDesc(String desc) {
		tv_desc.setText(desc);
	}
}
