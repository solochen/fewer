package com.yilan.lib.playerlib.activity.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.data.WinnerInfo;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.utils.CalculateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class WinnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<WinnerInfo.WinnerList> mList;

    public WinnerAdapter() {
        this.mList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(mContext, R.layout.adapter_lib_winner, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WinnerInfo.WinnerList winnerInfo = mList.get(position);
        ViewHolder mHolder = (ViewHolder) holder;

        if(position % 2 != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.mTvAvatar.getLayoutParams();
            params.topMargin = CalculateUtils.dip2px(mContext, 100);
            mHolder.mTvAvatar.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.mTvAvatar.getLayoutParams();
            params.topMargin = 0;
            mHolder.mTvAvatar.setLayoutParams(params);
        }

        Glides.getInstance().loadAvatar(mContext, winnerInfo.getAvatar(), mHolder.mTvAvatar);
        mHolder.mTvNickname.setText(winnerInfo.getNickname());
        double bonus = CalculateUtils.div(winnerInfo.getBouns(), 100);
        mHolder.mTvBonus.setText("¥" + CalculateUtils.formatHalfDown(bonus, 2));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<WinnerInfo.WinnerList> info){
        mList.clear();
        mList.addAll(info);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mTvAvatar;
        public TextView mTvNickname;
        public TextView mTvBonus;

        public ViewHolder(View convertView) {
            super(convertView);
            mTvAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            mTvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            mTvBonus = (TextView) convertView.findViewById(R.id.tv_bonus);
        }
    }
}
