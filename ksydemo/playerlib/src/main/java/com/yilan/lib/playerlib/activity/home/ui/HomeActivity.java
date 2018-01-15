package com.yilan.lib.playerlib.activity.home.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.RongCloud.RcSingleton;
import com.yilan.lib.playerlib.event.LoginEvent;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.activity.home.listener.OnBonusViewClickListener;
import com.yilan.lib.playerlib.activity.home.presenter.HomePresenter;
import com.yilan.lib.playerlib.customview.BonusView;
import com.yilan.lib.playerlib.customview.HeaderView;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.global.RouterConstant;
import com.yilan.lib.playerlib.listener.OnHeaderViewClickListener;
import com.yilan.lib.playerlib.data.Self;
import com.yilan.lib.playerlib.mvp.MVPBaseActivity;
import com.yilan.lib.playerlib.utils.LibToast;
import com.yilan.lib.playerlib.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by chenshaolong on 2018/1/14.
 */
@Route(path = RouterConstant.HOME_REDIRECT_PATH)
public class HomeActivity extends MVPBaseActivity<IHomeView, HomePresenter> implements
        IHomeView,
        OnHeaderViewClickListener,
        OnBonusViewClickListener {


    Context mContext;

    TextView mTvReviveCount;
    HeaderView mHeaderView;
    BonusView mBonusView;
    ImageView mIvAdImage;
    Button mBtnLiveEnter;

    GameInfo mGameInfo;


    @Override
    public int getLayout() {
        return R.layout.act_lib_home;
    }

    @Override
    public void onMVPCreate() {

        mContext = this;
        EventBus.getDefault().register(this);
        setClickListener();

        //连接融云 param rctoken
        String RcToen = "TTV0dNF+5aJ8pog/v0lc5nZ6IRyuHvRWxsFGzQLiIT+ZKsKNcmioSoI/V9fkGZD4qRrNFgY2Hvq2RpAJQQtYfhmz4T96qKNzanybqIc6ZVfct3GfsWgaXg==";
        RcSingleton.getInstance().connect(RcToen);

        Self self = UserManager.getInstance().getSelf(mContext);
        if(self == null) {
            mBonusView.showLoginBtn();
        } else {
            mPresenter.getInviteInfo(String.valueOf(self.getData().getUser_id()));
        }

        mHeaderView.setUserAvatar((self == null) ? "" :self.getData().getAvatar_url());
        mPresenter.getGameInfo();

    }


    @Override
    public void initView() {
        mTvReviveCount = (TextView) findViewById(R.id.lib_tv_revive_count);
        mHeaderView = (HeaderView) findViewById(R.id.lib_header_view);
        mBonusView = (BonusView) findViewById(R.id.lib_bonus_view);
        mIvAdImage = (ImageView) findViewById(R.id.lib_ad_image);
        mBtnLiveEnter = (Button) findViewById(R.id.lib_btn_live_enter);
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
    }

    /**
     * ------------- 自定义 view 层点击事件 start ---------------
     **/

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onAvatarClick() {
        LibToast.showToast(this, "avatar click");
    }

    @Override
    public void onLoginClick() {
        LibToast.showToast(this, "登录赢钱按钮点击");
    }

    @Override
    public void onApplyClick() {
        LibToast.showToast(this, "立即报名");
    }

    @Override
    public void onShreClick() {
        LibToast.showToast(this, "邀请好友分享");
    }

    @Override
    public void onGetInviteCodeClick() {
        LibToast.showToast(this, "输入邀请码按钮点击");
    }

    /**------------- 自定义 view 层点击事件 end ---------------**/


    /**
     * -------------  presenter 数据回调更新UI  start ---------------
     **/

    @Override
    public void updateInviteCode(InviteCode inviteCode) {

        //保存邀请码
        SPUtils.put(this, SPConstant.KEY_INVITE_CODE, inviteCode.getInvite_code());
        //复活卡数量
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

    /**
     * -------------  presenter 数据回调更新UI  end ---------------
     **/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent e){
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
            LibToast.showToast(mContext, "复活卡点击");
        } else if (v.getId() == R.id.lib_btn_share) {
            LibToast.showToast(mContext, "分享好友点击");
        } else if (v.getId() == R.id.lib_btn_live_enter) {
            if (mGameInfo != null) {
                //进入直播间传一些参数
            }
            LibToast.showToast(mContext, "进入直播间");
        }
    }
}
