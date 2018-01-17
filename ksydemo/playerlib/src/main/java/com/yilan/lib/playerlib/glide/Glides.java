package com.yilan.lib.playerlib.glide;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yilan.lib.playerlib.R;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class Glides {

    public static Glides singleton;

    public static Glides getInstance() {
        if(singleton == null) {
            synchronized(Glides.class) {
                if(singleton == null) {
                    singleton = new Glides();
                }
            }
        }
        return singleton;
    }

    public void load(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_lib_placeholder)
                .transform(new CenterCrop(context), new CircleTransform(context))
                .into(view);
    }

    public void loadNoDefault(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(view);
    }

    public void loadCenterCrop(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view);
    }

}
