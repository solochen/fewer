package com.yilan.lib.playerlib.global;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public abstract class BaseActivity extends FragmentActivity {

    public abstract int getLayout();
    public abstract void initView();
    public abstract void onCreate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        onCreate();
        ActivityCollector.addActivity(this, getClass());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        ActivityCollector.removeActivity(this);
    }
}