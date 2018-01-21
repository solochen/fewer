package com.yilan.lib.playerlib.utils;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yilan.lib.playerlib.widget.AlertDialogFragment;

/**
 * Created by chenshaolong on 2018/1/21.
 */

public class DialogUtil {

    public static AlertDialogFragment showAlertDialog(Context context, String title, String message, AlertDialogFragment.DialogOnClickListener listener) {
        FragmentActivity activity = (FragmentActivity)context;
        AlertDialogFragment newFragment = AlertDialogFragment.newInstance(0, title, message, (View)null, listener);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        newFragment.show(ft, "dialog");
        return newFragment;
    }


}
