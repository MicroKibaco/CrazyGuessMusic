package com.asiainfo.crazyguessmusic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asiainfo.crazyguessmusic.R;

/**
 * 唱片相关动画
 */

public class GrazyGuessMusicActivity extends Activity implements View.OnClickListener {

    /**
     * 与唱片相关动画
     */
    private Animation mPanAnim;

    //代表动画的运动速度
    private LinearInterpolator mPanLin;

    /**
     * 与播杆相关动画
     */
    private Animation mBarInAnim;

    private LinearInterpolator mBarInLin;

    private Animation mBarOutAnim;

    private LinearInterpolator mBarOutLin;

    /**
     * Play 按键处理事件
     *
     * @param savedInstanceState
     */

    private ImageButton mBtnPlayStart;
    private ImageButton mBtnBarBack;
    private LinearLayout mLlGameCoin;

    private ImageView mViewPan;
    private ImageView mViewPanBar;

    /**
     * 判断是否处于播放状态
     */
    private boolean mIsRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazy_guess_music);

        initView();
        initListener();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        /**
         *  初始化动画
         */
        mBtnPlayStart = (ImageButton) findViewById(R.id.btn_play_start);
        mBtnBarBack = (ImageButton) findViewById(R.id.btn_bar_back);
        mLlGameCoin = (LinearLayout) findViewById(R.id.ll_game_coin);

        mViewPan = (ImageView) findViewById(R.id.imv_play_pan);
        mViewPanBar = (ImageView) findViewById(R.id.imv_play_bar);


        mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mPanLin = new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setFillAfter(true);

        mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
        mBarInLin = new LinearInterpolator();
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setFillAfter(true);

        mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
        mBarOutLin = new LinearInterpolator();
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setFillAfter(true);


    }


    private void initListener() {

        mBtnPlayStart.setOnClickListener(this);
        mBtnBarBack.setOnClickListener(this);
        mLlGameCoin.setOnClickListener(this);
        initAnimListener();
    }

    private void initAnimListener() {
        /**
         * 对盘片动画的监听
         */
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mViewPanBar.startAnimation(mBarOutAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        /**
         * 对播放杆动画的监听
         */

        mBarInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPan.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsRunning = false;
                mBtnPlayStart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_play_start:
                handlePlayButton();
                break;
            case R.id.btn_bar_back:
                break;

            case R.id.ll_game_coin:
                break;
            default:
                break;

        }
    }

    /**
     * 控制播杆和唱片的动画,处理圆盘中间的播放按钮
     */
    private void handlePlayButton() {

        if (!mIsRunning && mViewPanBar != null) {
            mIsRunning = true;
            mViewPanBar.startAnimation(mBarInAnim);
            mBtnPlayStart.setVisibility(View.INVISIBLE);
        }

        //mViewPan.startAnimation();
    }

    @Override
    protected void onPause() {
        mViewPan.clearAnimation();
        super.onPause();
    }
}
