package com.yilan.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class PlayerActivity extends AppCompatActivity {


    private static final String KEY_URL = "liveUrl";
    String mPlayerUrl;

    public static void startActivity(Context context, String url) {
        Intent mIntent = new Intent(context, PlayerActivity.class);
        mIntent.putExtra(KEY_URL, url);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_play);

        mPlayerUrl = getIntent().getStringExtra(KEY_URL);

        String nickname = getIntent().getStringExtra("nickname");
        int age = getIntent().getIntExtra("age", 0);
        Toast.makeText(this, nickname + age, Toast.LENGTH_LONG).show();

        FragmentTransaction mTrx = getSupportFragmentManager().beginTransaction();
        PlayerFragment fragment = PlayerFragment.getPlayerFragment(mPlayerUrl);
        mTrx.add(R.id.flmain, fragment);
        mTrx.show(fragment);
        mTrx.commitAllowingStateLoss();

    }
}
