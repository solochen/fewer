package com.yilan.lib.playerlib.activity.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.data.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<Comment> mList;

    public CommentAdapter() {
        this.mList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(mContext, R.layout.adapter_lib_comment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comment comment = mList.get(position);
        String nickName = "<font color=\"#33ccff\">" + comment.getNickname() + "</font>";
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.mTvComment.setText(Html.fromHtml(nickName + " " + comment.getText()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(Comment comment){
        mList.add(comment);
        notifyItemInserted(getItemCount() - 1);
    }

    public void remove(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvComment;

        public ViewHolder(View convertView) {
            super(convertView);
            mTvComment = (TextView) convertView.findViewById(R.id.lib_player_comment);
        }
    }
}
