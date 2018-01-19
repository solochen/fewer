package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.RongCloud.message.HTAnswerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTQuestionMessage;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerAnswerViewListener;
import com.yilan.lib.playerlib.utils.CalculateUtils;
import com.yilan.lib.playerlib.widget.CircleProgressBar;
import com.yilan.lib.playerlib.widget.ProgressButton;

import java.util.List;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerAnswerView extends FrameLayout {

//    boolean isWatching = true;
    boolean mCurrentQuestionIsAnswered = false;

    private Context mContext;
    private LayoutInflater mInflater;

    CustomPlayerView mPlayerView;
    LinearLayout mAnswerOptionLayout;
    TextView mTvQuestion;
    TextView mTvCountDown;
    CircleProgressBar mCircleProgressBar;
    FrameLayout mCountDownLayout;
    TextView mTvWatch;
    ImageView mIvRevive;
    TextView mTvReviveUsed;
    ImageView mIvAnswerError;

    OnPlayerAnswerViewListener mListener;

    public PlayerAnswerView(Context context) {
        super(context);
        init(context);
    }

    public PlayerAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerAnswerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_answer_view, this);
        mPlayerView = (CustomPlayerView) findViewById(R.id.lib_custom_player_view);
        mTvQuestion = (TextView) findViewById(R.id.lib_custom_player_question);
        mAnswerOptionLayout = (LinearLayout) findViewById(R.id.lib_custom_player_option_layout);
        mTvCountDown = (TextView) findViewById(R.id.lib_custom_player_countdown);
        mCircleProgressBar = (CircleProgressBar) findViewById(R.id.lib_answer_custom_round_progress);
        mCountDownLayout = (FrameLayout) findViewById(R.id.lib_custom_player_countdown_layout);
        mTvWatch = (TextView) findViewById(R.id.lib_custom_player_watch);
        mIvRevive = (ImageView) findViewById(R.id.lib_custom_player_revive);
        mTvReviveUsed = (TextView) findViewById(R.id.lib_custom_player_revive_used);
        mIvAnswerError = (ImageView) findViewById(R.id.lib_custom_player_answer_error);

        setClickListener();
    }

    void setClickListener() {
    }


    public void setClickListener(OnPlayerAnswerViewListener listener) {
        mListener = listener;
    }


    /**--------------  问题 ---------------------*/

    /**
     * 设置问题
     *
     * @param msg
     */
    public void setQuestion(HTQuestionMessage msg, boolean isWatching) {
        resetView();
        this.mCurrentQuestionIsAnswered = false;
        if (isWatching) {
            mTvWatch.setVisibility(VISIBLE);           //显示 『观战』
            mCountDownLayout.setVisibility(GONE);      //隐藏倒计时
            simulateProgress(msg.getSec() * 1000);
        } else {
            mTvWatch.setVisibility(GONE);              //隐藏 『观战』
            mCountDownLayout.setVisibility(VISIBLE);   //显示倒计时
            simulateProgress(msg.getSec() * 1000);          //倒计时
        }

        mTvQuestion.setText(msg.getText());
        addOptionView(msg.getOptions(), msg.getCount(), isWatching);

    }

    void resetView(){
        mCountDownLayout.setVisibility(GONE);
        mTvWatch.setVisibility(GONE);
        mIvAnswerError.setVisibility(GONE);
        mIvRevive.setVisibility(GONE);
        mTvReviveUsed.setVisibility(GONE);
    }


    /**
     * 设置选项卡
     *
     * @param options
     * @param count
     */
    void addOptionView(List<String> options, final int count, final boolean isWatching) {
        mAnswerOptionLayout.removeAllViews();
        for (int i = 0; i < options.size(); i++) {
            String optionStr = options.get(i);
            View view = mInflater.inflate(R.layout.custom_lib_player_progress, null);
            FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, CalculateUtils.dip2px(mContext, 50));
            params.topMargin = CalculateUtils.dip2px(mContext, 10);
            view.setLayoutParams(params);
            final ProgressButton progressButton = (ProgressButton) view.findViewById(R.id.lib_btn_progress_answer_a);
            progressButton.setBackgroundColor(mContext.getResources().getColor(R.color.lib_answer_progress_second_bg));
            final TextView answerOption = (TextView) view.findViewById(R.id.lib_tv_progress_answer_option);
            progressButton.setTag((i + 1));
            progressButton.setState(ProgressButton.NORMAL);
            answerOption.setText(optionStr);
            progressButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isWatching && !mCurrentQuestionIsAnswered) {
                        int optionId = (int) progressButton.getTag();
                        mListener.onAnswerSelected(count, optionId);
                        mCurrentQuestionIsAnswered = true;
                        answerOption.setTextColor(mContext.getResources().getColor(R.color.lib_answer_progress_option_highlight_color));
                        progressButton.setBackgroundColor(mContext.getResources().getColor(R.color.lib_answer_progress_bg));
                    }
                }
            });
            mAnswerOptionLayout.addView(view);
        }
    }


    /**--------------  答案 ---------------------*/


    /**
     * 设置答案
     *
     * @param msg
     */
    public void setAnswer(HTAnswerMessage msg, boolean isWatching, int myAnswerOption, boolean isUsedReviveCode, int reviveCount) {
        resetView();
        if (isWatching) {
            mCountDownLayout.setVisibility(View.GONE);      //隐藏 倒计时
            mTvWatch.setVisibility(View.VISIBLE);           //显示 『观战』
        } else {
            mTvWatch.setVisibility(View.GONE);              //隐藏 『观战』
            mCountDownLayout.setVisibility(View.GONE);      //隐藏 倒计时
        }

        /**
         * 答错题
         */
        if(myAnswerOption != msg.getQuestion_answer()) {
            if((reviveCount > 0 && !isUsedReviveCode)) {
                //复活卡使用逻辑
                mListener.useReviveCode();
                mIvRevive.setVisibility(VISIBLE);
                mTvReviveUsed.setVisibility(isUsedReviveCode ? VISIBLE : GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIvRevive.setVisibility(GONE);
                        mTvReviveUsed.setVisibility(VISIBLE);
                    }
                }, 2000);

            } else {
                //1. 打败多少人 2.isWatching = true
            }

            mIvAnswerError.setImageResource(R.mipmap.ic_lib_error);
            mIvAnswerError.setVisibility(View.VISIBLE);

        } else {
            mIvAnswerError.setImageResource(R.mipmap.ic_lib_correct);
            mIvAnswerError.setVisibility(View.VISIBLE);
        }


        mTvQuestion.setText(msg.getText());

        addAnswerOptionView(msg, myAnswerOption);
    }


    /**
     * 设置答案选项卡 选项人数，复活人数等
     */
    void addAnswerOptionView(final HTAnswerMessage message, int myQuestionOpt) {
        mAnswerOptionLayout.removeAllViews();
        for (int i = 0; i < message.getOptions().size(); i++) {
            String optionStr = message.getOptions().get(i);
            int selectedNum = message.getSelected().get(i);

            View view = mInflater.inflate(R.layout.custom_lib_player_progress, null);

            FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, CalculateUtils.dip2px(mContext, 50));
            params.topMargin = CalculateUtils.dip2px(mContext, 10);
            view.setLayoutParams(params);

            TextView answerOption = (TextView) view.findViewById(R.id.lib_tv_progress_answer_option);
            TextView answeroptionNum = (TextView) view.findViewById(R.id.lib_tv_progress_select_num);

            String optionNum;
            if (selectedNum >= 10000) {
                optionNum = CalculateUtils.formatDecimal(selectedNum / 10000, 2) + "万";
            } else {
                optionNum = String.valueOf(selectedNum);

            }
            answeroptionNum.setText(optionNum);

            final ProgressButton progressButton = (ProgressButton) view.findViewById(R.id.lib_btn_progress_answer_a);
            progressButton.setState(ProgressButton.NORMAL);

            if(message.getQuestion_answer() == (i + 1)){
                progressButton.setBackgroundColor(mContext.getResources().getColor(R.color.lib_answer_progress_bg));
            } else {
                if(myQuestionOpt == (i + 1)){
                    progressButton.setBackgroundColor(mContext.getResources().getColor(R.color.lib_answer_progress_error_bg));
                } else {
                    progressButton.setBackgroundColor(mContext.getResources().getColor(R.color.lib_answer_progress_option_normal_color));
                }
            }


            double curProgress = CalculateUtils.div(selectedNum, selectedTotal(message.getSelected()));
            curProgress = CalculateUtils.formatDecimal(curProgress, 4);
            progressButton.setState(ProgressButton.DOWNLOADING);
            progressButton.setProgressText("", (float) CalculateUtils.mul(curProgress, 100));
            answerOption.setText(optionStr);
            mAnswerOptionLayout.addView(view);
        }
    }


    int selectedTotal(List<Integer> totalList) {
        int total = 0;
        for (int i : totalList) {
            total = total + i;
        }
        return total;
    }



    /**
     * 倒计时
     *
     * @param millisTime
     */
    private void simulateProgress(int millisTime) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long currentPlayTime = animation.getCurrentPlayTime() / 1000;
                long time = (10 - currentPlayTime);
                if (time < 0) {
                    boolean isWatching = mCurrentQuestionIsAnswered ? false : true;
                    try {
                        Thread.sleep(1500);
                        mListener.onDismissAnswerView(isWatching);
                    } catch (Exception e) {

                    }
                    return;
                }

                int progress = (int) animation.getAnimatedValue();
                mCircleProgressBar.setProgress(progress);
                mTvCountDown.setText(time + "");
            }
        });
        animator.setDuration(millisTime + 1000);
        animator.start();
    }
}