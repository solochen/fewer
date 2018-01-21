package com.yilan.lib.playerlib.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chenshaolong on 2018/1/21.
 */

public class AlertDialogFragment extends DialogFragment {

    int mIcon;
    String mTitle;
    String mMessage;

    static DialogOnClickListener mOnClickListener;
    static View mContentView;

    public static AlertDialogFragment newInstance(int icon, String title, String message, View view, DialogOnClickListener onClickListener) {
        AlertDialogFragment f = new AlertDialogFragment();
        mOnClickListener = onClickListener;
        mContentView = view;
        Bundle args = new Bundle();
        args.putInt("icon", icon);
        args.putString("title", title);
        args.putString("message", message);
        f.setArguments(args);
        return f;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mIcon = this.getArguments().getInt("icon");
        this.mTitle = this.getArguments().getString("title");
        this.mMessage = this.getArguments().getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(), 3);
        if(this.mIcon > 0) {
            builder.setIcon(this.mIcon);
        }

        if(this.mTitle != null) {
            builder.setTitle(this.mTitle);
        }

        if(this.mMessage != null) {
            builder.setMessage(this.mMessage);
        }

        if(mContentView != null) {
            builder.setView(mContentView);
        }

        if(mOnClickListener != null) {
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(mOnClickListener != null) {
                        mOnClickListener.onPositiveClick();
                    }

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(mOnClickListener != null) {
                        mOnClickListener.onNegativeClick();
                    }

                }
            });
        }

        return builder.create();
    }


    public interface DialogOnClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }


}
