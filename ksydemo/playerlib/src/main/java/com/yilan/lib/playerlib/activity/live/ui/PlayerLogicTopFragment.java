package com.yilan.lib.playerlib.activity.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.RongCloud.message.Comment;
import com.yilan.lib.playerlib.RongCloud.message.HTAnswerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTCommentMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTFinishMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTOnlookerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTQuestionMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTResultMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTStartMessage;
import com.yilan.lib.playerlib.activity.live.dialog.LateDialog;
import com.yilan.lib.playerlib.activity.live.dialog.LosersDialog;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerAnswerViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerCommentViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerGameInfoViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerHeaderViewListener;
import com.yilan.lib.playerlib.activity.live.presenter.PlayerPresenter;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerAnswerView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerCommentView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerGameInfoView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerHeaderView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerWinnerView;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.LiveEnterInfo;
import com.yilan.lib.playerlib.data.Self;
import com.yilan.lib.playerlib.data.WinnerInfo;
import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.LiveEvent;
import com.yilan.lib.playerlib.event.RongEvent;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.global.AppManager;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.mvp.MVPBaseFragment;
import com.yilan.lib.playerlib.utils.CalculateUtils;
import com.yilan.lib.playerlib.utils.HideUtil;
import com.yilan.lib.playerlib.utils.LibToast;
import com.yilan.lib.playerlib.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imlib.model.MessageContent;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerLogicTopFragment extends MVPBaseFragment<IPlayerView, PlayerPresenter> implements
        IPlayerView,
        OnPlayerHeaderViewListener,
        OnPlayerCommentViewListener,
        OnPlayerGameInfoViewListener,
        OnPlayerAnswerViewListener,
        View.OnTouchListener{


    Context mContext;
    GameInfo mGameInfo;

    RelativeLayout mContainer;
    ImageView mContainerBg;
    PlayerHeaderView mHeaderView;
    PlayerAnswerView mAnswerView;
    PlayerCommentView mCommentView;
    PlayerGameInfoView mGameInfoView;
    PlayerWinnerView mWinnerView;
    Button mBtnLoginToAnswer;

    boolean isWatching = false;      //是否在观战
    boolean isUsedReviveCode = false; //是否本场使用过复活卡
    int mReviveCount = 0; //复活卡数量
    ArrayMap<Integer, Integer> mCurrentAnswerMap = new ArrayMap<>();

    private static final String KEY_GAME_INFO = "game_info";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.frmt_lib_player_logic_toplayer, container, false);
        mContainer = (RelativeLayout) view.findViewById(R.id.lib_player_container);
        mContainerBg = (ImageView) view.findViewById(R.id.lib_player_def_bg);
        mHeaderView = (PlayerHeaderView) view.findViewById(R.id.lib_player_headerview);
        mAnswerView = (PlayerAnswerView) view.findViewById(R.id.lib_player_answer_view);
        mCommentView = (PlayerCommentView) view.findViewById(R.id.lib_player_playercommentview);
        mGameInfoView = (PlayerGameInfoView) view.findViewById(R.id.lib_player_game_info_view);
        mBtnLoginToAnswer = (Button) view.findViewById(R.id.lib_btn_login_to_answer);
        mWinnerView = (PlayerWinnerView) view.findViewById(R.id.lib_player_winner_view);

        setClickListener();
        return view;
    }

    void setClickListener() {
        mCommentView.setClickListener(this);
        mHeaderView.setClickListener(this);
        mGameInfoView.setClickListener(this);
        mAnswerView.setClickListener(this);
        mContainer.setOnTouchListener(this);

        mBtnLoginToAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().goLogin(getChildFragmentManager());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        HideUtil.init(getActivity());
        mGameInfo = (GameInfo) getArguments().getSerializable(KEY_GAME_INFO);
        Self self = UserManager.getInstance().getSelf(mContext);
        mHeaderView.setReviveCount((self == null) ? 0 : (int) SPUtils.get(mContext, SPConstant.KEY_REVIVE_COUNT, 0));
        mPresenter.getGameLiveInfo();
        mPresenter.chatRoomStatusListener();
        mPresenter.joinChatRoom(mGameInfo.getLive().getLive_id());
        mPresenter.gameLiveEnter(String.valueOf(self.getData().getUser_id()), mGameInfo.getLive().getLive_id());

        isUsedReviveCode = (boolean) SPUtils.get(mContext, mGameInfo.getLive().getLive_id(), false);
        mReviveCount = (int) SPUtils.get(mContext, SPConstant.KEY_REVIVE_COUNT, 0);

        initSetGameInfo();

        /**
         * 设置高斯模糊背景图
         */
        Glides.getInstance()
                .loadResBlur(mContext, mContainerBg, R.mipmap.bg_lib_default, 25);
    }


    /**
     * 设置直播信息
     */
    void initSetGameInfo() {
        if (mGameInfo.getStatus() == 0) {        //开放
            updateGameInfo(mGameInfo,
                    CalculateUtils.formatBonus(mGameInfo.getBonus()),
                    CalculateUtils.formatBonusUnit(mGameInfo.getBonus()));
        } else if (mGameInfo.getStatus() == 1) { //答题中
            onAnswerStatus();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mCommentView.hideKeyboary();
                break;
        }
        return false;
    }

    /**
     * ------------------ view 层 点击事件 start -----------------------------
     **/

    @Override
    public void onBackClick() {
        EBus.send(new LiveEvent(LiveEvent.EVENT_LIVE_FINISH));
    }

    @Override
    public void onBtnShareClick() {
        AppManager.getInstance().goShare(getChildFragmentManager());
    }

    @Override
    public void showCommentEditView() {
        /**
         * 1. 未登录 弹窗提示登录
         * 2. 已登录 弹出评论框
         */
        if (UserManager.getInstance().isLogin(mContext)) {
            mCommentView.showCommentEdit();
        } else {
            AppManager.getInstance().goLogin(getChildFragmentManager());
        }
    }

    @Override
    public void onSendComment(String comment) {
        //发送评论信息
        Self self = UserManager.getInstance().getSelf(mContext);
        mCommentView.addComment(new Comment(self.getData().getName(), comment));
        mPresenter.sendCMTToServer(String.valueOf(self.getData().getUser_id()), self.getData().getName(), comment);
    }

    @Override
    public void getReviveCode() {
        AppManager.getInstance().goShare(getChildFragmentManager());
    }

    @Override
    public void goRule() {
        //活动规则

        AppManager.getInstance().goLogin(getChildFragmentManager());

    }

    @Override
    public void onAnswerSelected(int questionCount, int optionSelect) {
        mCurrentAnswerMap.put(questionCount, optionSelect);
        long uid = UserManager.getInstance().getSelf(mContext).getData().getUser_id();
        mPresenter.sendAnswer(String.valueOf(uid), questionCount, optionSelect);
    }

    @Override
    public void onSetIsWatching(boolean isWatch) {
        isWatching = isWatch;
    }

    @Override
    public void onShowLosersDialog(int losers) {
        LosersDialog.newInstance(losers).show(getChildFragmentManager(), "losers_dialog");
    }

    @Override
    public void useReviveCode() {
        isUsedReviveCode = true;
        SPUtils.put(mContext, mGameInfo.getLive().getLive_id(), isUsedReviveCode);
        mReviveCount = mReviveCount - 1;
        SPUtils.put(mContext, SPConstant.KEY_REVIVE_COUNT, mReviveCount);

    }

    /**------------------ view 层 点击事件 end -----------------------------**/


    /**
     * ------------------ presenter 层 回掉方法 start -----------------------------
     **/


    @Override
    public void updateGameInfo(GameInfo gameInfo, int bonus, String unit) {

        mGameInfoView.setGameInfo(bonus, unit, gameInfo.getGame_date(), gameInfo.getCountdown());
        mGameInfoView.setVisibility(View.VISIBLE);

    }


    /**
     * 开始答题隐藏
     * 1. 倒计时和直播信息
     * 2. 高斯模糊图
     */

    @Override
    public void onAnswerStatus() {
        mGameInfoView.cancelTimer();
        mGameInfoView.setVisibility(View.GONE);
        mContainerBg.setVisibility(View.GONE);
        if (!UserManager.getInstance().isLogin(mContext)) {
            mBtnLoginToAnswer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void playIsFinish() {
        //弹窗，提示直播已结束
    }

    @Override
    public void setLiveEnterInfo(LiveEnterInfo info) {
        isWatching = (info.getIs_player() == 0) ? true : false;
        if (isWatching) {
            String localLiveid = (String) SPUtils.get(mContext, SPConstant.KEY_IS_TELL_USER_DIALOG, "");
            if (!localLiveid.equals(mGameInfo.getLive().getLive_id())) {
                //弹窗提示，你来晚了
                LateDialog.newInstance().show(getChildFragmentManager(), "latedialog");
                SPUtils.put(mContext, SPConstant.KEY_IS_TELL_USER_DIALOG, mGameInfo.getLive().getLive_id());
            }
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        LibToast.showLongToast(mContext, msg);
    }

    @Override
    public void setWatchingStatus() {
        isWatching = true;
    }


    @Override
    public void setWinnerList(WinnerInfo info) {
        mWinnerView.setWinnerNumber(info.getWinners());
        mWinnerView.setData(info.getWinner_list());
        mWinnerView.setVisibility(View.VISIBLE);
    }

    /**
     * ------------------ presenter 层 回掉方法 end -----------------------------
     **/

    @Override
    protected PlayerPresenter createPresenter() {
        return new PlayerPresenter(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RongEvent e) {
        MessageContent message = e.getMsg().getContent();

        if (message instanceof HTCommentMessage) {

            mCommentView.addComments(((HTCommentMessage) message).getComments());
        }
        else if (message instanceof HTStartMessage) {
            HTStartMessage msg = (HTStartMessage) message;
            LibToast.showToast(mContext, msg.getLive_id());
            onAnswerStatus();
        } else if (message instanceof HTOnlookerMessage) {

            HTOnlookerMessage onlookerMessage = (HTOnlookerMessage) message;
            mHeaderView.setOnlineCount(onlookerMessage.getOnlookers());

        } else if (message instanceof HTQuestionMessage) {

            mAnswerView.setQuestion((HTQuestionMessage) message, isWatching);
            mAnswerView.setVisibility(View.VISIBLE);

        } else if (message instanceof HTAnswerMessage) {

            HTAnswerMessage answerMessage = (HTAnswerMessage) message;
            int myAnswerOption = mCurrentAnswerMap.get(answerMessage.getCount());
            mAnswerView.setAnswer(answerMessage, isWatching, myAnswerOption, isUsedReviveCode, mReviveCount);
            mAnswerView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mAnswerView != null){
                        mAnswerView.setVisibility(View.GONE);
                    }
                }
            }, answerMessage.getSec() * 1000);


        } else if (message instanceof HTResultMessage) {
            HTResultMessage resultMessage = (HTResultMessage) message;
            long uid = UserManager.getInstance().getSelf(mContext).getData().getUser_id();
            mPresenter.getWinnerList(String.valueOf(uid));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mWinnerView != null){
                        mWinnerView.setVisibility(View.GONE);
                    }
                }
            }, resultMessage.getSec() * 1000);



        } else if (message instanceof HTFinishMessage) {
            EBus.send(new LiveEvent(LiveEvent.EVENT_LIVE_FINISH));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCurrentAnswerMap.clear();
        EventBus.getDefault().unregister(this);
        if (mGameInfoView != null) {
            mGameInfoView.cancelTimer();
        }
    }


}
