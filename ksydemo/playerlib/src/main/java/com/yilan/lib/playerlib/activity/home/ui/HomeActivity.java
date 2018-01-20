package com.yilan.lib.playerlib.activity.home.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.Test;
import com.yilan.lib.playerlib.activity.home.listener.OnBonusViewClickListener;
import com.yilan.lib.playerlib.activity.home.presenter.HomePresenter;
import com.yilan.lib.playerlib.activity.live.ui.PlayerActivity;
import com.yilan.lib.playerlib.activity.home.ui.custom.GameInfoView;
import com.yilan.lib.playerlib.utils.HideUtil;
import com.yilan.lib.playerlib.widget.CustomEditView;
import com.yilan.lib.playerlib.activity.home.ui.custom.HomeHeaderView;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.data.Self;
import com.yilan.lib.playerlib.event.LoginEvent;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.global.AppManager;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.listener.OnEditViewClickListener;
import com.yilan.lib.playerlib.listener.OnHeaderViewClickListener;
import com.yilan.lib.playerlib.mvp.MVPBaseActivity;
import com.yilan.lib.playerlib.utils.LibToast;
import com.yilan.lib.playerlib.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class HomeActivity extends MVPBaseActivity<IHomeView, HomePresenter> implements
        IHomeView,
        OnHeaderViewClickListener,
        OnBonusViewClickListener,
        OnEditViewClickListener {


    Context mContext;

    TextView mTvReviveCount;
    HomeHeaderView mHeaderView;
    GameInfoView mBonusView;
    ImageView mIvAdImage;
    Button mBtnLiveEnter;
    CustomEditView mEtCustomView;
    LinearLayout mLibReviveLayout;

    GameInfo mGameInfo;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_lib_home;
    }

    @Override
    public void onMVPCreate() {
        HideUtil.init(this);
        mContext = this;
        EventBus.getDefault().register(this);
        setClickListener();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        Self self = UserManager.getInstance().getSelf(mContext);
        if (!UserManager.getInstance().isLogin(this)) {
            mBonusView.showLoginBtn();
        } else {
            mPresenter.getInviteInfo(String.valueOf(self.getData().getUser_id()));
        }
        mPresenter.getGameInfo();
        mHeaderView.setUserAvatar(self.getData().getAvatar_url());
    }


    @Override
    public void initView() {
        mTvReviveCount = (TextView) findViewById(R.id.lib_tv_revive_count);
        mHeaderView = (HomeHeaderView) findViewById(R.id.lib_header_view);
        mBonusView = (GameInfoView) findViewById(R.id.lib_bonus_view);
        mIvAdImage = (ImageView) findViewById(R.id.lib_ad_image);
        mBtnLiveEnter = (Button) findViewById(R.id.lib_btn_live_enter);
        mEtCustomView = (CustomEditView) findViewById(R.id.lib_home_custom_edit_view);
        mLibReviveLayout = (LinearLayout) findViewById(R.id.lib_layout_revive);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setClickListener() {
        mHeaderView.setClickListener(this);
        mBonusView.setClickListener(this);
        mEtCustomView.setClickListener(this);
    }

    /**
     * ------------- 自定义 view 层点击事件 start ---------------
     **/

    @Override
    public void onBackClick() {
//        Test test = Test.getInstance();
//        test.share();
        finish();
    }

    @Override
    public void onAvatarClick() {
        if (!UserManager.getInstance().isLogin(mContext)) {
            AppManager.getInstance().goLogin(getSupportFragmentManager());
        } else {
            LibToast.showToast(this, "跳转用户详情");
        }

    }

    @Override
    public void onLoginClick() {
        AppManager.getInstance().goLogin(getSupportFragmentManager());
    }

    @Override
    public void onApplyClick() {
//        LibToast.showToast(this, "立即报名");
    }

    @Override
    public void onShreClick() {
        AppManager.getInstance().goShare(getSupportFragmentManager());
    }

    @Override
    public void onGetInviteCodeClick() {
        mEtCustomView.showEditView();
        mEtCustomView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCommentSend(String content) {
        mPresenter.useInviteCode(
                UserManager.getInstance().getSelf(mContext).getData().getUser_id(), content);
    }

    @Override
    public void onHideOther() {
        mEtCustomView.setVisibility(View.GONE);
    }

    /**------------- 自定义 view 层点击事件 end ---------------**/


    /**
     * -------------  presenter 数据回调更新UI  start ---------------
     **/

    @Override
    public void updateInviteCode(InviteCode inviteCode) {

        mLibReviveLayout.setVisibility(inviteCode.getCan_be_invited() ? View.GONE : View.VISIBLE);

        //保存邀请码
        SPUtils.put(this, SPConstant.KEY_INVITE_CODE, inviteCode.getInvite_code());

        //保存复活卡数量
        SPUtils.put(mContext, SPConstant.KEY_REVIVE_COUNT, inviteCode.getRevive_count());

        //显示复活卡数量
        mTvReviveCount.setText(String.valueOf(inviteCode.getRevive_count()));
        //是否显示输入邀请码按钮
        mBonusView.canBeShowInviteBtn(inviteCode.getCan_be_invited());

    }

    @Override
    public void liveReady(String adImage) {
        mIvAdImage.setVisibility(View.VISIBLE);
        mBtnLiveEnter.setVisibility(View.GONE);
        Glides.getInstance().loadNoDefault(mContext, adImage, mIvAdImage);
    }

    @Override
    public void liveOpen() {
        mIvAdImage.setVisibility(View.GONE);
        mBtnLiveEnter.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateGameInfo(GameInfo gameInfo, int displayBonus, String displayUnit) {
        mGameInfo = gameInfo;
        mBonusView.updateGameInfo(displayBonus, displayUnit,
                gameInfo.getGame_date(), gameInfo.getGame_time());

    }

    @Override
    public void updateInviteCode(int inviteCode) {
        //保存复活卡数量
        SPUtils.put(mContext, SPConstant.KEY_REVIVE_COUNT, inviteCode);
        //显示复活卡数量
        mTvReviveCount.setText(String.valueOf(inviteCode));
    }

    /**
     * -------------  presenter 数据回调更新UI  end ---------------
     **/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent e) {
        switch (e.getType()) {
            case LoginEvent.EVENT_LOGIN_RESULT:

                /**
                 * 这里收到登录完成的用户信息
                 * 1. 更新用户信息
                 * 2.获取邀请码信息
                 */
//                UserManager.getInstance().updateUserInfo();
//                mPresenter.getInviteInfo(String.valueOf(""));
                break;
        }
    }


    public void onLibBtnClick(View v) {
        if (v.getId() == R.id.lib_ll_more_revive) {
//            LibToast.showToast(mContext, "复活卡点击");
        } else if (v.getId() == R.id.lib_btn_share) {
            AppManager.getInstance().goShare(getSupportFragmentManager());
        } else if (v.getId() == R.id.lib_btn_live_enter) {
            if (mGameInfo != null) {
                String liveUrl = mGameInfo.getLive().getLive_stream().getMain_list().getMedium();
                String defRes = mGameInfo.getLive().getLive_stream().getDefault_res();
                if (defRes.equals("high")) {
                    liveUrl = mGameInfo.getLive().getLive_stream().getMain_list().getHigh();
                } else if (defRes.equals("medium")) {
                    liveUrl = mGameInfo.getLive().getLive_stream().getMain_list().getMedium();
                } else if (defRes.equals("low")) {
                    liveUrl = mGameInfo.getLive().getLive_stream().getMain_list().getLow();
                }
                PlayerActivity.startActivity(mContext, mGameInfo, "http://xdj-hdl.8686c.com/xdj-live/5a4b8ee8ac11ab9954d2d700.flv");

            }
        }
    }


}
