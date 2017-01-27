package com.asiainfo.crazyguessmusic.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.interfrc.IWordButtonClickListener;
import com.asiainfo.crazyguessmusic.model.WordButton;
import com.asiainfo.crazyguessmusic.view.StrongerGridView;

import java.util.ArrayList;

/**
 * 唱片相关动画
 */

public class GrazyGuessMusicActivity extends Activity implements View.OnClickListener, IWordButtonClickListener {

    private static final int COUNT_WORDS = 24;
    private static final int COUNT_SELECT_WORDS = 4;
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
    //文字框容器
    private ArrayList<WordButton> mAllWords;

    //
    private StrongerGridView mStrongerGridView;
    private ArrayList<WordButton> mSelectWords;

    //已选择文字框UI容器

    private LinearLayout mViewContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazy_guess_music);

        initView();
        initListener();
        initDatas();
    }


    /**
     * 初始化控件
     */
    public void initView() {
        /**
         *  初始化动画
         */
        mBtnPlayStart = (ImageButton) findViewById(R.id.btn_play_start);
        mBtnBarBack = (ImageButton) findViewById(R.id.btn_bar_back);
        mLlGameCoin = (LinearLayout) findViewById(R.id.ll_game_coin);

        mViewPan = (ImageView) findViewById(R.id.imv_play_pan);
        mViewPanBar = (ImageView) findViewById(R.id.imv_play_bar);
        mStrongerGridView = (StrongerGridView) findViewById(R.id.my_gridview);
        mViewContainer = (LinearLayout) findViewById(R.id.word_select_container);


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
        //
        mStrongerGridView.registerOnWordButtonClick(this);
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

    public void initDatas() {

        //初始化已选择框
        mSelectWords = initWordSelect();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140, 140);

        for (int i = 0; i < mSelectWords.size(); i++) {

            mViewContainer.addView(mSelectWords.get(i).getViewBtn(), params);
        }

        //获得数据
        mAllWords = initAllWord();

        //更新数据---StrongerGridView
        mStrongerGridView.updateDatas(mAllWords);

    }

    /**
     * 初始化待选文字框
     */
    private ArrayList<WordButton> initAllWord() {

        ArrayList<WordButton> wordBtnList = new ArrayList<>();

        //获得所有待选文字
        for (int i = 0; i < COUNT_WORDS; i++) {

            WordButton wordButton = new WordButton();

            wordButton.mWordStr = "小";

            wordBtnList.add(wordButton);

        }


        return wordBtnList;

    }

    /**
     * 初始化已选文字框
     */

    private ArrayList<WordButton> initWordSelect() {

        ArrayList<WordButton> wordBtnList = new ArrayList<>();

        //获得所有待选文字
        for (int i = 0; i < COUNT_SELECT_WORDS; i++) {

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.self_view_grideview_item, null);


            WordButton wordButton = new WordButton();

            wordButton.mViewBtn = (Button) v.findViewById(R.id.item_btn);

            wordButton.mViewBtn.setTextColor(Color.WHITE);
            wordButton.mViewBtn.setText("");
            wordButton.mIsVisible = false;
            wordButton.mViewBtn.setBackgroundResource(R.drawable.game_wordblank);

            wordBtnList.add(wordButton);


        }
        return wordBtnList;
    }


    @Override
    protected void onPause() {
        mViewPan.clearAnimation();
        super.onPause();
    }


    @Override
    public void onWordButtonClick(WordButton wordButton) {

    }
}
