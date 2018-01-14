package com.yilan.lib.playerlib.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public abstract class MVPBasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
