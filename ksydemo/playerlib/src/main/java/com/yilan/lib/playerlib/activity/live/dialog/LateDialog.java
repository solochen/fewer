package com.yilan.lib.playerlib.activity.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.global.AppManager;


/**
 * Created by chenshaolong on 2018/1/12.
 */
public class LateDialog extends DialogFragment {


    TextView mTvTitle;
    TextView mTvContent;

    Context mContext;
    LayoutInflater mInflater;

    public static LateDialog newInstance() {
        LateDialog f = new LateDialog();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    ImageButton mCloseBtn;
    Button mShareBtn;
    Button mGoWatchBtn;

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
        View view = inflater.inflate(R.layout.dialog_lib_late, container);
        mTvTitle = (TextView) view.findViewById(R.id.lib_late_dlg_title);
        mTvContent = (TextView) view.findViewById(R.id.lib_late_dlg_msg);
        mCloseBtn = (ImageButton) view.findViewById(R.id.lib_late_dig_close);
        mShareBtn = (Button) view.findViewById(R.id.lib_late_dlg_btn_invite);
        mGoWatchBtn = (Button) view.findViewById(R.id.lib_late_dlg_btn_watch);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setClickListener();

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


        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void setClickListener(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().goShare(getChildFragmentManager());
            }
        });

        mGoWatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
