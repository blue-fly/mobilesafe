package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

/**
 * 设置向导页面4
 *
 * @author Administrator
 */
public class Setup4Activity extends BaseSetupActivity {


    private CheckBox cb_setup4_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        cb_setup4_status = (CheckBox) findViewById(R.id.cb_setup4_status);
    }

    public void next(View view) {
        showNext();
    }

    public void pref(View view) {
        showPref();
    }

    @Override
    public void showNext() {
        //记录是否完成设置向导
        Editor editor = sp.edit();
        editor.putBoolean("seted", true);

        //开启防盗
        if(cb_setup4_status.isChecked()){
            editor.putBoolean("lock", true);
        }else{
            editor.putBoolean("lock",false);
        }
        editor.commit();


        Intent i = new Intent(Setup4Activity.this, LostFindActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.page_next, R.anim.page_exit);
        finish();
    }

    @Override
    public void showPref() {
        Intent i = new Intent(Setup4Activity.this, Setup3Activity.class);
        startActivity(i);
        overridePendingTransition(R.anim.page_pref, 0);
        finish();
    }
}
