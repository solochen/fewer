package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.ui.adapter.WinnerAdapter;
import com.yilan.lib.playerlib.data.WinnerInfo;
import com.yilan.lib.playerlib.widget.CustomRecycleView;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerWinnerView extends FrameLayout {

    private Context mContext;
    private LayoutInflater mInflater;

    CustomRecycleView mPlayerWinnerView;
    WinnerAdapter mAdapter;

    public PlayerWinnerView(Context context) {
        super(context);
        init(context);
    }

    public PlayerWinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerWinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_winner_view, this);
        mPlayerWinnerView = (CustomRecycleView) findViewById(R.id.lib_player_winner);
        setClickListener();
        setAdapter();
    }

    void setClickListener() {
    }


    void setAdapter(){
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPlayerWinnerView.setLayoutManager(mLinearLayoutManager);
        mPlayerWinnerView.setHasFixedSize(true);
        mAdapter = new WinnerAdapter();
        mPlayerWinnerView.setAdapter(mAdapter);
    }

    public void setData(WinnerInfo.WinnerList winnerList){
        mAdapter.add(winnerList);
    }

}