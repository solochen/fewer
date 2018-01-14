package com.yilan.lib.playerlib.activity.home.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.datamodel.GameInfo;
import com.yilan.lib.playerlib.activity.datamodel.InviteCode;
import com.yilan.lib.playerlib.activity.home.listener.OnBonusViewClickListener;
import com.yilan.lib.playerlib.activity.home.presenter.HomePresenter;
import com.yilan.lib.playerlib.customview.BonusView;
import com.yilan.lib.playerlib.customview.HeaderView;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.global.RouterHelper;
import com.yilan.lib.playerlib.listener.OnHeaderViewClickListener;
import com.yilan.lib.playerlib.mvp.MVPBaseActivity;
import com.yilan.lib.playerlib.utils.LibToast;


/**
 * Created by chenshaolong on 2018/1/14.
 */
@Route(path = RouterHelper.HOME_REDIRECT_PATH)
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
        String appToken = getIntent().getStringExtra(RouterHelper.HOME_REDIRECT_PARAMS_TOKEN);

        setClickListener();

        mHeaderView.setUserAvatar("");
        mPresenter.getInviteInfo("");
        mPresenter.getGameInfo("");
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

    private void setClickListener(){
        mHeaderView.setClickListener(this);
        mBonusView.setClickListener(this);
    }

    /**------------- 自定义 view 层点击事件 start ---------------**/

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onAvatarClick() {
        LibToast.showToast(this, "avatar click");
    }

    @Override
    public void onLoginBtnClick() {
        LibToast.showToast(this, "登录赢钱按钮点击");
    }

    @Override
    public void onGetInviteCodeClick() {
        LibToast.showToast(this, "输入邀请码按钮点击");
    }

    /**------------- 自定义 view 层点击事件 end ---------------**/




    /**-------------  presenter 数据回调更新UI  start ---------------**/

    @Override
    public void updateInviteCode(InviteCode inviteCode) {

        mTvReviveCount.setText(String.valueOf(inviteCode.getRevive_count()));
        mBonusView.showInviteBtn(inviteCode.getCan_be_invited());
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

    /**-------------  presenter 数据回调更新UI  end ---------------**/


    public void onLibBtnClick(View v) {
        if(v.getId() == R.id.lib_ll_more_revive){
            LibToast.showToast(mContext, "复活卡点击");
        } else if(v.getId() == R.id.lib_btn_share){
            LibToast.showToast(mContext, "分享好友点击");
        }else if(v.getId() == R.id.lib_btn_live_enter){
            if(mGameInfo != null){
                //进入直播间传一些参数
            }
            LibToast.showToast(mContext, "进入直播间");
        }
    }
}
