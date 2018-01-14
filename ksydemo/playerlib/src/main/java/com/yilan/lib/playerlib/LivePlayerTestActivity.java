package com.yilan.lib.playerlib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yilan.lib.playerlib.utils.SPUtils;

/**
 * Created by chenshaolong on 2018/1/13.
 */

public class LivePlayerTestActivity extends AppCompatActivity {

    public static final void startActivity(Context context){
        Intent intent = new Intent(context, LivePlayerTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_play);
        SPUtils.put(this, "chenshaolong", "你好啊，哈哈");

        TextView mTvName = (TextView) findViewById(R.id.tv_chsoalong);
        String ddd = (String) SPUtils.get(this, "chenshaolong", "");
        mTvName.setText(ddd);

        mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/player/playeractivity")
//                        .withString("nickname", "chenshaolong")
//                        .withInt("age", 30)
//                        .navigation();

                DialogFragment fragment = (DialogFragment) ARouter.getInstance().build("/player/fragmentdialog").navigation();
                fragment.show(getSupportFragmentManager(), "jaja");
                Toast.makeText(LivePlayerTestActivity.this,
                        "找到Fragment:" + fragment.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


}
