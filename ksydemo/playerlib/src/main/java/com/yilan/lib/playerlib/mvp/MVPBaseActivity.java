package com.yilan.lib.playerlib.mvp;

import android.os.Bundle;

import com.yilan.lib.playerlib.global.BaseActivity;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public abstract class MVPBaseActivity<V, T extends MVPBasePresenter<V>> extends BaseActivity {

    protected T mPresenter;
    public abstract void onMVPCreate();

    @Override
    public void onCreate() {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        onMVPCreate();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
