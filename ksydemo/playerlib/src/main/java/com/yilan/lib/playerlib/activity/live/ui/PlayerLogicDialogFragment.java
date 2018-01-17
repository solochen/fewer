package com.yilan.lib.playerlib.activity.live.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.LiveEvent;
import com.yilan.lib.playerlib.global.ActivityCollector;


/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerLogicDialogFragment extends DialogFragment {

    ViewPager mViewPager;
    PlayerLogicTopFragment mTopFragment;

    private static final String KEY_GAME_INFO = "game_info";

    public static PlayerLogicDialogFragment newInstance(GameInfo info) {
        PlayerLogicDialogFragment f = new PlayerLogicDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_GAME_INFO, info);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frmt_lib_player_logic_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) view.findViewById(R.id.lib_player_viewpager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mTopFragment = new PlayerLogicTopFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_GAME_INFO, getArguments().getSerializable(KEY_GAME_INFO));
                mTopFragment.setArguments(bundle);
                return mTopFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        mViewPager.addOnPageChangeListener(new OnPageChangeListener());
        mViewPager.setCurrentItem(0);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if(ActivityCollector.isActivityExist(PlayerActivity.class) && (getDialog() != null && getDialog().isShowing() && isResumed())) {
                        EBus.send(new LiveEvent(LiveEvent.EVENT_LIVE_FINISH));
                    }
                    return true;
                }
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
        Dialog dialog = new Dialog(getActivity(), R.style.LibThemeMainDialog);
        return dialog;
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
//                mLayerFragment.hideKeyboard();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
