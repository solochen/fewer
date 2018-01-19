package com.yilan.lib.playerlib.activity.live.listener;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface OnPlayerAnswerViewListener {


    /**
     * 选择答案选项
     * @param questionCount 当前第几个问题
     * @param optionSelect 当前选择的第一个
     */
    void onAnswerSelected( int questionCount, int optionSelect);

    /**
     * 通知关闭答题窗口，并设置是否可答题
     * @param isWatch
     */
    void onDismissAnswerView(boolean isWatch);


    /**
     * 使用复活码
     */
    void useReviveCode();
}
