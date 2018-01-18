package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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

public class PlayerAnswerView extends FrameLayout implements View.OnClickListener{

    boolean mIsRelease = false;
    boolean isCanAnswer = false;

    private Context mContext;
    private LayoutInflater mInflater;

    LinearLayout mAnswerOptionLayout;
    TextView mTvQuestion;
    TextView mTvCountDown;
    CircleProgressBar mCircleProgressBar;

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
        mTvQuestion = (TextView) findViewById(R.id.lib_custom_player_question);
        mAnswerOptionLayout = (LinearLayout) findViewById(R.id.lib_custom_player_option_layout);
        mTvCountDown = (TextView) findViewById(R.id.lib_custom_player_countdown);
        mCircleProgressBar = (CircleProgressBar) findViewById(R.id.lib_answer_custom_round_progress);

        setClickListener();
    }

    void setClickListener(){
    }


    public void setClickListener(OnPlayerAnswerViewListener listener) {
        mListener = listener;
    }

    /**--------------  问题 ---------------------*/

    public void setQuestion(HTQuestionMessage msg){
        mAnswerOptionLayout.removeAllViews();
        mTvQuestion.setText(msg.getText());
        addOptionView(msg.getOptions(), msg.getCount());
        simulateProgress(msg.getSec() * 1000);
    }


    /**
     * 设置选项卡
     * @param options
     * @param count
     */
    void addOptionView(List<String> options, final int count){
        for(int i = 0; i < options.size(); i ++) {
            String optionStr = options.get(i);
            View view = mInflater.inflate(R.layout.custom_lib_player_progress, null);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) new LayoutParams(LayoutParams.MATCH_PARENT, CalculateUtils.dip2px(mContext, 50));
            params.topMargin = CalculateUtils.dip2px(mContext, 15);
            view.setLayoutParams(params);
            final ProgressButton progressButton = (ProgressButton) view.findViewById(R.id.lib_btn_progress_answer_a);
            TextView answerOption = (TextView) view.findViewById(R.id.lib_tv_progress_answer_option);
            progressButton.setTag((i + 1));
            progressButton.setState(ProgressButton.NORMAL);
            answerOption.setText(optionStr);
            progressButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCanAnswer) {
                        int optionId = (int) progressButton.getTag();
                        mListener.onAnswerSelected(count, optionId);
                    }
                }
            });
            mAnswerOptionLayout.addView(view);
        }
    }


    /**--------------  答案 ---------------------*/


    public void setAnswer(HTAnswerMessage msg){
        mTvQuestion.setText(msg.getText());
        addAnswerOptionView(msg);
    }


    /**
     * 设置答案选项卡 选项人数，复活人数等
     */
    void addAnswerOptionView(final HTAnswerMessage message){
        for(int i = 0; i < message.getOptions().size(); i ++) {
            String optionStr = message.getOptions().get(i);
            int selectedNum = message.getSelected().get(i);

            View view = mInflater.inflate(R.layout.custom_lib_player_progress, null);
            TextView answerOption = (TextView) view.findViewById(R.id.lib_tv_progress_answer_option);
            TextView answeroptionNum = (TextView) view.findViewById(R.id.lib_tv_progress_select_num);

            int _newSelectdNum = (selectedNum >= 10000) ? selectedNum / 10000 : selectedNum;
            String unit = (selectedNum >= 10000) ? "万" : "";
            answeroptionNum.setText(CalculateUtils.formatDecimal(_newSelectdNum, 2) + unit);

            final ProgressButton progressButton = (ProgressButton) findViewById(R.id.lib_btn_progress_answer_a);
            progressButton.setTag((i + 1));
            progressButton.setState(ProgressButton.NORMAL);

            float progress = selectedNum / selectedTotal(message.getSelected());
            progressButton.setProgress(progress);
            answerOption.setText(optionStr);
            progressButton.setState(ProgressButton.DOWNLOADING);
            mAnswerOptionLayout.addView(view);
        }
    }


    int selectedTotal(List<Integer> totalList){
        int total = 0;
        for(int i : totalList){
            total = total + i;
        }
        return total;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

    }


    private void simulateProgress(int millisTime) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mCircleProgressBar.setProgress(progress);
                long currentPlayTime = animation.getCurrentPlayTime() / 1000;
                long time = (10 - currentPlayTime);
                if (time < 0) {
                    isCanAnswer = false;
                    return;
                }
                mTvCountDown.setText(time + "");
            }
        });
        animator.setDuration(millisTime + 1000);
        animator.start();
    }
}