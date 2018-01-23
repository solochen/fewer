package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.RongCloud.message.Comment;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerCommentViewListener;
import com.yilan.lib.playerlib.activity.live.ui.adapter.CommentAdapter;
import com.yilan.lib.playerlib.widget.CustomEditView;
import com.yilan.lib.playerlib.listener.OnEditViewClickListener;

import java.util.List;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerCommentView extends FrameLayout implements OnEditViewClickListener {

    private static final String TAG = PlayerCommentView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    RecyclerView mPlayerCommentView;
    EditText mPlayerCommentText;
    ImageButton mBtnShowComment;
    CustomEditView mEtCommentView;

    CommentAdapter mCommentAdapter;
    LinearLayoutManager mLinearLayoutManager;
    OnPlayerCommentViewListener mListener;

    public PlayerCommentView(Context context) {
        super(context);
        init(context);
    }

    public PlayerCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_comment_view, this);
        mPlayerCommentView = (RecyclerView) findViewById(R.id.lib_player_comment_recyclerview);
        mPlayerCommentText = (EditText) findViewById(R.id.lib_player_comment);
        mBtnShowComment = (ImageButton) findViewById(R.id.lib_player_btn_show_comment);
        mEtCommentView = (CustomEditView) findViewById(R.id.et_input_view);
        mPlayerCommentText.setBackgroundResource(R.color.lib_translate_color);
        setClickListener();
        setAdapter();
    }

    void setClickListener(){
        mEtCommentView.setClickListener(this);
        mBtnShowComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showCommentEditView();
            }
        });
    }

    @Override
    public void onCommentSend(String content) {
        mListener.onSendComment(content);
    }

    @Override
    public void onHideOther() {
        mBtnShowComment.setVisibility(View.VISIBLE);
        mEtCommentView.setVisibility(View.GONE);
    }


    void setAdapter(){
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPlayerCommentView.setLayoutManager(mLinearLayoutManager);
        mPlayerCommentView.setHasFixedSize(true);
        mCommentAdapter = new CommentAdapter();
        mPlayerCommentView.setAdapter(mCommentAdapter);
    }

    public void showCommentEdit(){
        mEtCommentView.showEditView();
        mEtCommentView.setVisibility(View.VISIBLE);
        mBtnShowComment.setVisibility(View.INVISIBLE);
    }

    public void addComment(Comment comment){
        cleanData();
        mCommentAdapter.add(comment);
        scrollToPosition();
    }

    public void addComments(List<Comment> comments){
        if(comments == null || comments.size() <= 0) return;
        cleanData();
        for(Comment comment : comments){
            mCommentAdapter.add(comment);
        }
        scrollToPosition();
    }

    private void scrollToPosition(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mLinearLayoutManager.scrollToPosition(mCommentAdapter.getItemCount() - 1);
        } else {
            mLinearLayoutManager.smoothScrollToPosition(mPlayerCommentView, null, mCommentAdapter.getItemCount() - 1);
        }
    }

    private void cleanData() {
        int maxSize = 200;
        if (mCommentAdapter.getItemCount() > maxSize) {
            for (int i = 0; i < maxSize - 50; i++) {
                mCommentAdapter.remove(i);
            }
        }
    }


    public void hideKeyboary(){
        mEtCommentView.hideKeyboary();
    }

    public void setClickListener(OnPlayerCommentViewListener listener) {
        mListener = listener;
    }

}