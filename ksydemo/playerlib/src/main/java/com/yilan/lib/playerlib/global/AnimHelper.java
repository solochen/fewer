package com.yilan.lib.playerlib.global;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by chenshaolong on 2018/1/19.
 */

public class AnimHelper {

    public static AnimHelper singleton;

    public static AnimHelper getInstance() {
        if (singleton == null) {
            synchronized (AnimHelper.class) {
                if (singleton == null) {
                    singleton = new AnimHelper();
                }
            }
        }
        return singleton;
    }


    /**
     * 数字倒计时动画
     * @param view
     */
    public void startScaleAnim(final View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.5f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.5f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1000);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.play(anim1).with(anim2);
        animSet.start();
    }


    /**
     * 倒计时结束抖动动画
     * @param view
     */
    public void startClockRotateAnim(final View view) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "rotation", 0, 30, 0, -30, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim1, anim2, anim3);
        animSet.start();
    }


    /**
     * 观看 图标抖动动画
     * @param view
     */
    public void startWatchIconAnim(final View view) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "rotation", 0, 15, 0, -15, 0);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim1);
        animSet.start();
    }


    /**
     * 出题卡片弹出动画
     */
    public void zoomInDownAnimator(final View target) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(800);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(
        ObjectAnimator.ofFloat(target, "scaleX", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "translationY", -target.getBottom(), 60, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1));
        animSet.start();

    }

    /**
     * 出题卡片消失动画
     * @param target
     */
    public void zoomOutUpAnimator(final View target) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(800);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0, 0),
                ObjectAnimator.ofFloat(target, "scaleX", 1, 0.3f, 0),
                ObjectAnimator.ofFloat(target, "scaleY", 1, 0.3f, 0));
        animSet.start();

    }

    public void zoomOutAnimator(View target){
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.setDuration(300);
//        animSet.setInterpolator(new OvershootInterpolator());
//        animSet.playTogether(
//                ObjectAnimator.ofFloat(target, "scaleX", 0, 1.0f));
//        animSet.start();
    }

}
