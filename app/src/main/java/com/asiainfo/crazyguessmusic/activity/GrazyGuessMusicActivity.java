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
import com.asiainfo.crazyguessmusic.data.Const;
import com.asiainfo.crazyguessmusic.interfc.IWordButtonClickListener;
import com.asiainfo.crazyguessmusic.model.Songs;
import com.asiainfo.crazyguessmusic.model.WordButton;
import com.asiainfo.crazyguessmusic.view.StrongerGridView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

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

    //当前的歌曲
    private Songs mCurrentSong;

    //当前关的索引
    private int mCurrentStageIndex = 5;


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

        //读取当前关的歌曲信息
        mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);

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

    private Songs loadStageSongInfo(int stageIndex) {

        Songs songs = new Songs();

        String[] stage = Const.SONG_INFO[stageIndex];

        songs.setSongFileName(stage[Const.INDEX_FILE_NAME]);
        songs.setSongName(stage[Const.INDEX_SONG_NAME]);

        return songs;

    }

    /**
     * 初始化待选文字框
     */
    private ArrayList<WordButton> initAllWord() {

        ArrayList<WordButton> wordBtnList = new ArrayList<>();

        //获得所有待选文字

        String[] words = generateWords();

        for (int i = 0; i < COUNT_WORDS; i++) {

            WordButton wordButton = new WordButton();

            wordButton.mWordStr = words[i];

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
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {

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
        setSelectWord(wordButton);
    }


    /**
     * 设置答案
     */
    private void setSelectWord(WordButton wordButton) {

        for (int i = 0; i < mSelectWords.size(); i++) {

            /**
             * 设置答案文字框的内容和可见性
             */
            if (mSelectWords.get(i).mWordStr.length() == 0) {

                mSelectWords.get(i).mViewBtn.setText(wordButton.mWordStr);
                mSelectWords.get(i).mIsVisible = true;
                mSelectWords.get(i).mWordStr = wordButton.mWordStr;

                //记录索引
                mSelectWords.get(i).mIndex = wordButton.mIndex;

                //增加一个相应的Log类
                //Log.e("GrazyGuessMusicActivity",)

                //设置待选框的可见性
                setButtonVisible(wordButton, View.INVISIBLE);

                break;


            }

        }

    }

    /**
     * 设置待选文字框是否可见
     */
    private void setButtonVisible(WordButton wordButton, int visibility) {

        wordButton.mViewBtn.setVisibility(visibility);
        wordButton.mIsVisible = (visibility == View.VISIBLE);
    }



    /**
     * 生成随机汉字
     */
    private char getRandomChar() {

        String str = "";
        int highPos;
        int lowPos;

        Random r = new Random();

        highPos = (176 + Math.abs(r.nextInt(39)));

        lowPos = (161 + Math.abs(r.nextInt(93)));

        byte[] bytes = new byte[2];

        bytes[0] = (Integer.valueOf(highPos)).byteValue();

        bytes[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str.charAt(0);
    }

    /**
     * 生成所有的待选文字
     */
    private String[] generateWords() {

        Random random = new Random();

        String[] words = new String[COUNT_WORDS];

        //存入歌名

        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {

            words[i] = mCurrentSong.getNameCharacters()[i] + "";

        }

        //获取随机文字并存入数组中

        for (int i = mCurrentSong.getNameLength(); i < COUNT_WORDS; i++) {

            words[i] = getRandomChar() + "";

        }

        //打乱文字的顺序:首先从所有元素中随机选取一个与第一个元素进行交换
        //然后在第二个数据之后选择一个元素,让它跟第二个数据进行交换,直到最后一个元素
        //这个算法能够确保每一个元素每个位置的概率都是1/n

        for (int i = COUNT_WORDS - 1; i >= 0; i--) {

            int index = random.nextInt(i + 1);

            String buf = words[index];

            words[index] = words[i];

            words[i] = buf;

        }

        return words;
    }
}
