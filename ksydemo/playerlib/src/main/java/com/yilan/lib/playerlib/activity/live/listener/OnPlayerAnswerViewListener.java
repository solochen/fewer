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
}
