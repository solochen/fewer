package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerCommentViewListener;
import com.yilan.lib.playerlib.activity.live.ui.adapter.CommentAdapter;
import com.yilan.lib.playerlib.data.Comment;
import com.yilan.lib.playerlib.utils.KeyBoardUtils;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerCommentView extends FrameLayout {

    private static final String TAG = PlayerCommentView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    RecyclerView mPlayerCommentView;
    EditText mPlayerCommentText;
    ImageButton mBtnShowComment;
    EditText mEtInputComment;
    ImageButton mBtnSendComment;
    RelativeLayout mPlayerEditLayout;

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
        mEtInputComment = (EditText) findViewById(R.id.lib_player_comment);
        mBtnSendComment = (ImageButton) findViewById(R.id.lib_player_btn_send);
        mPlayerEditLayout = (RelativeLayout) findViewById(R.id.lib_player_edit_layout);
        setClickListener();
        setAdapter();
    }

    void setClickListener(){
        mBtnShowComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showCommentEditView();
            }
        });

        mBtnSendComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSendComment(mEtInputComment.getText().toString());
                hideKeyboary();
            }
        });

        mEtInputComment.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        mListener.onSendComment(mEtInputComment.getText().toString());
                        hideKeyboary();
                    }
                }
                return false;
            }
        });
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
        mEtInputComment.requestFocus();
        mBtnShowComment.setVisibility(View.GONE);
        mPlayerEditLayout.setVisibility(View.VISIBLE);
        KeyBoardUtils.openKeybord(mEtInputComment, mContext);
    }

    public void addComment(Comment comment){

        cleanData();
        mCommentAdapter.add(comment);

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
        mBtnShowComment.setVisibility(View.VISIBLE);
        mPlayerEditLayout.setVisibility(View.GONE);
        KeyBoardUtils.closeKeybord(mEtInputComment, mContext);
    }


    public void setClickListener(OnPlayerCommentViewListener listener) {
        mListener = listener;
    }

}