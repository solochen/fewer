package com.yilan.lib.playerlib.activity.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.RongCloud.message.HTAnswerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTCommentMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTFinishMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTOnlookerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTQuestionMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTResultMessage;
import com.yilan.lib.playerlib.activity.live.dialog.LateDialog;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerAnswerViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerCommentViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerGameInfoViewListener;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerHeaderViewListener;
import com.yilan.lib.playerlib.activity.live.presenter.PlayerPresenter;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerAnswerView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerGameInfoView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerCommentView;
import com.yilan.lib.playerlib.activity.live.ui.custom.PlayerHeaderView;
import com.yilan.lib.playerlib.data.Comment;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.LiveEnterInfo;
import com.yilan.lib.playerlib.data.Self;
import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.LiveEvent;
import com.yilan.lib.playerlib.event.RongEvent;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.global.AppManager;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.mvp.MVPBaseFragment;
import com.yilan.lib.playerlib.utils.CalculateUtils;
import com.yilan.lib.playerlib.utils.KeyBoardUtils;
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
        OnPlayerAnswerViewListener {


    Context mContext;
    GameInfo mGameInfo;

    RelativeLayout mContainer;
    ImageView mContainerBg;
    PlayerHeaderView mHeaderView;
    PlayerAnswerView mAnswerView;
    PlayerCommentView mCommentView;
    PlayerGameInfoView mGameInfoView;
    Button mBtnLoginToAnswer;

    boolean isWatching = true;      //是否在观战
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

        setClickListener();
        return view;
    }

    void setClickListener(){
        mCommentView.setClickListener(this);
        mHeaderView.setClickListener(this);
        mGameInfoView.setClickListener(this);
        mAnswerView.setClickListener(this);
        mBtnLoginToAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().goLogin(getChildFragmentManager());
            }
        });

        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCommentView.hideKeyboary();
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mGameInfo = (GameInfo) getArguments().getSerializable(KEY_GAME_INFO);
        Self self = UserManager.getInstance().getSelf(mContext);
        mHeaderView.setReviveCount((self == null) ? 0 : (int) SPUtils.get(mContext, SPConstant.KEY_REVIVE_COUNT, 0));
        mPresenter.getGameLiveInfo();
        mPresenter.gameLiveEnter(String.valueOf(self.getData().getUser_id()), mGameInfo.getLive().getLive_id());
        initSetGameInfo();

        Glides.getInstance().loadCenterCrop(mContext, "http://c.hiphotos.baidu.com/image/h%3D300/sign=1ac3db8a3aadcbef1e3478069cae2e0e/cdbf6c81800a19d8765f664b38fa828ba61e4624.jpg", mContainerBg);

        HTQuestionMessage message = new HTQuestionMessage();
        message.setSec(10);
        message.setCount(1);
        message.setOptions(JSON.parseArray("[\n" +
                " \"就卡死极度疯狂\",\n" +
                "\"啊空间上大概了解阿三\",\n" +
                "\"啊水电费娃啊水电费\"\n" +
                "]", String.class));
        message.setText("1.看见了卡就算了快递费");
        message.setId("1123");
        message.setNumber(12);
        mAnswerView.setQuestion(message);
    }

    /**
     * 设置直播信息
     */
    void initSetGameInfo(){
        if(mGameInfo.getStatus() == 0){        //开放
            updateGameInfo(mGameInfo,
                    CalculateUtils.formatBonus(mGameInfo.getBonus()),
                    CalculateUtils.formatBonusUnit(mGameInfo.getBonus()));
        } else if(mGameInfo.getStatus() == 1){ //答题中
            onAnswerStatus();
        }
    }

    /**------------------ view 层 点击事件 start -----------------------------**/

    @Override
    public void onBackClick() {
        EBus.send(new LiveEvent(LiveEvent.EVENT_LIVE_FINISH));
    }

    @Override
    public void onBtnShareClick() {
        //分享
    }

    @Override
    public void showCommentEditView() {
        /**
         * 1. 未登录 弹窗提示登录
         * 2. 已登录 弹出评论框
         */
        if(UserManager.getInstance().isLogin(mContext)){
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
        //获取复活卡  分享
    }

    @Override
    public void goRule() {
        //活动规则

        AppManager.getInstance().goLogin(getChildFragmentManager());

    }

    @Override
    public void onAnswerSelected(int questionCount, int optionSelect) {
        long uid = UserManager.getInstance().getSelf(mContext).getData().getUser_id();
        mPresenter.sendAnswer(String.valueOf(uid), questionCount, optionSelect);
    }

    /**------------------ view 层 点击事件 end -----------------------------**/



    /**------------------ presenter 层 回掉方法 start -----------------------------**/


    @Override
    public void updateGameInfo(GameInfo gameInfo, int bonus, String unit) {

        mGameInfoView.setGameInfo(bonus, unit, gameInfo.getGame_date(), gameInfo.getGame_time());
        mGameInfoView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAnswerStatus() {
        mGameInfoView.setVisibility(View.GONE);
        if(!UserManager.getInstance().isLogin(mContext)) {
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
        if(isWatching) {
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

    /**------------------ presenter 层 回掉方法 end -----------------------------**/

    @Override
    protected PlayerPresenter createPresenter() {
        return new PlayerPresenter(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RongEvent e){
        MessageContent message = e.getMsg().getContent();
        if(message instanceof HTCommentMessage){

            HTCommentMessage c = (HTCommentMessage) message;
            mCommentView.addComment(new Comment(c.getNickname(), c.getText()));

        } else if(message instanceof HTOnlookerMessage){

        } else if(message instanceof HTQuestionMessage){

            mAnswerView.setQuestion((HTQuestionMessage) message);

        } else if(message instanceof HTAnswerMessage){

            mAnswerView.setAnswer((HTAnswerMessage) message);

        } else if(message instanceof HTResultMessage){

        } else if(message instanceof HTFinishMessage){

        }
//        else if(){
//            如果当前页面为开放状态，在答题开始的时候会收到融云消息
//        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
