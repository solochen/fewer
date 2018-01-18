package com.yilan.lib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yilan.lib.playerlib.global.RouterConstant;


/**
 * Created by chenshaolong on 2018/1/12.
 */

@Route(path = RouterConstant.ROUTER_SHARE_DIALOG)
public class ShareDialog extends DialogFragment {

    Context mContext;
    LayoutInflater mInflater;
    RelativeLayout mViewOutSideContainer;

    public static ShareDialog newInstance() {
        ShareDialog f = new ShareDialog();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.dlg_share, container);
        mViewOutSideContainer = (RelativeLayout) view.findViewById(R.id.rlv_outside_container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        mViewOutSideContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });

        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogAnswerStyle);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
