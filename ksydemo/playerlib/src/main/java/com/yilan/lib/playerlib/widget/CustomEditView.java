package com.yilan.lib.playerlib.widget;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.listener.OnEditViewClickListener;
import com.yilan.lib.playerlib.utils.KeyBoardUtils;
import com.yilan.lib.playerlib.utils.LibToast;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class CustomEditView extends FrameLayout {

    private static final String TAG = CustomEditView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    ImageButton mBtnSend;
    EditText mEtComment;

    OnEditViewClickListener mListener;

    public CustomEditView(Context context) {
        super(context);
        init(context);
    }

    public CustomEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_editview, this);
        mBtnSend = (ImageButton) findViewById(R.id.lib_player_btn_send);
        mEtComment = (EditText) findViewById(R.id.lib_player_comment);
        mEtComment.setBackgroundResource(R.color.lib_translate_color);
        setClickListener();

    }

    void setClickListener(){
        mBtnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });


        mEtComment.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == android.view.KeyEvent.ACTION_UP) {
                    if (keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        sendComment();
                    }
                }
                return true;
            }
        });


    }

    public void showEditView(){
        mEtComment.requestFocus();
        KeyBoardUtils.openKeybord(mEtComment, mContext);
    }


    void sendComment(){
        String content = mEtComment.getText().toString();
        if(TextUtils.isEmpty(content)) {
            LibToast.showLongToast(mContext, "内容不能空");
            return;
        }
        mListener.onCommentSend(content);
        mEtComment.setText("");
        hideKeyboary();
    }

    public void hideKeyboary(){
        KeyBoardUtils.closeKeybord(mEtComment, mContext);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onHideOther();
            }
        }, 200);
    }

    public void setClickListener(OnEditViewClickListener listener) {
        mListener = listener;
    }

}