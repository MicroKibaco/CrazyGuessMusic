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
import android.widget.TextView;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.data.Const;
import com.asiainfo.crazyguessmusic.interfc.IWordButtonClickListener;
import com.asiainfo.crazyguessmusic.model.Songs;
import com.asiainfo.crazyguessmusic.model.WordButton;
import com.asiainfo.crazyguessmusic.utils.LogUtil;
import com.asiainfo.crazyguessmusic.view.StrongerGridView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 唱片相关动画
 */

public class GrazyGuessMusicActivity extends Activity implements View.OnClickListener, IWordButtonClickListener {

    public final static String TAG = "GrazyGuessMusicActivity";

    /**
     * <p> 答案状态 1.正确 2.错误 3.不完整 </p>
     */

    public final static int STATUS_ANSWER_RIGHT = 1;
    public final static int STATUS_ANSWER_WRONG = 2;
    public final static int STATUS_ANSWER_LACK = 3;

    //闪烁次数
    private final static int SPASH_TIME = 6;

    private static final int COUNT_WORDS = 24;
    private static final int COUNT_SELECT_WORDS = 4;
    //当前金币的数量
    public int mCurrentCoins = Const.TOTAL_COINS;
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
    //金币view
    private TextView mViewCorrentCoins;
    //
    private StrongerGridView mStrongerGridView;

    //已选择文字框UI容器
    private ArrayList<WordButton> mSelectWords;
    private LinearLayout mViewContainer;
    //当前的歌曲
    private Songs mCurrentSong;
    //当前关的索引
    private int mCurrentStageIndex = 5;
    //代表过关界面
    private LinearLayout mPassView;

    private ImageButton mBtnDeleteword;
    private ImageButton mBtnTipword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        mPassView = (LinearLayout) findViewById(R.id.answer_right);
        mViewCorrentCoins = (TextView) findViewById(R.id.txt_bar_coins);
        mBtnDeleteword = (ImageButton) findViewById(R.id.btn_delete_word);
        mBtnTipword = (ImageButton) findViewById(R.id.btn_tip_word);


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
        mBtnDeleteword.setOnClickListener(this);
        mBtnTipword.setOnClickListener(this);
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

            case R.id.btn_delete_word:

                //handleDeleteWord();

                break;
            case R.id.btn_tip_word:

                handleTipAnswer();

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

    }


    public void initDatas() {
        handleDeleteWord();
        mViewCorrentCoins.setText(mCurrentCoins + "");

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

    /**
     * 处理过关界面和事件
     */

    private void handlePassEvent() {

        mPassView.setVisibility(View.VISIBLE);

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
     * 检查答案的状态
     */
    public int checkTheAnswer() {

        //检查长度

        for (int i = 0; i < mSelectWords.size(); i++) {
            //如果有空的,说明答案不完整
            if (mSelectWords.get(i).mWordStr.length() == 0) {

                return STATUS_ANSWER_LACK;
            }

        }

        //答案完整,继续检查正确性
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mSelectWords.size(); i++) {

            sb.append(mSelectWords.get(i).mWordStr);
        }

        return (sb.toString().equals(mCurrentSong.getSongName())) ?
                STATUS_ANSWER_RIGHT : STATUS_ANSWER_WRONG;

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


            final WordButton wordButton = new WordButton();

            wordButton.mViewBtn = (Button) v.findViewById(R.id.item_btn);

            wordButton.mViewBtn.setTextColor(Color.WHITE);
            wordButton.mViewBtn.setText("");
            wordButton.mIsVisible = false;
            wordButton.mViewBtn.setBackgroundResource(R.drawable.game_wordblank);
            wordButton.mViewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTheAnswear(wordButton);
                }
            });

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

        //获取答案的状态
        int checkResult = checkTheAnswer();

        //检查答案
        if (checkResult == STATUS_ANSWER_RIGHT) {
            //过关并获得奖励
            handlePassEvent();

        } else if (checkResult == STATUS_ANSWER_WRONG) {
            //闪烁文字并提示用户
            sparkTheWords();

        } else if (checkResult == STATUS_ANSWER_LACK) {

            //设置文字颜色为白色(Normal)
            for (int i = 0; i < mSelectWords.size(); i++) {
                mSelectWords.get(i).mViewBtn.setTextColor(Color.WHITE);
            }

        }
    }

    /**
     * 文字闪烁
     */
    public void sparkTheWords() {
        //声明定时器

        TimerTask task = new TimerTask() {
            boolean mChange = false;
            int mSparkTimes = 0;

            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //显示闪烁的次数

                        if (++mSparkTimes > SPASH_TIME) {

                            return;

                        }

                        //执行闪烁的逻辑:交替显示红色和白色
                        for (int i = 0; i < mSelectWords.size(); i++) {
                            mSelectWords.get(i).mViewBtn.setTextColor(mChange ? Color.RED : Color.WHITE);
                        }
                        mChange = !mChange;
                    }
                });

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1, 150);

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
                LogUtil.e(TAG, mSelectWords.get(i).mIndex + "");

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

        LogUtil.e(TAG, wordButton.mIsVisible + "");
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
     * 清除文本框的答案
     */
    private void clearTheAnswear(WordButton wordButton) {

        wordButton.mViewBtn.setText("");
        wordButton.mWordStr = "";
        wordButton.mIsVisible = false;

        //设置待选框的可见性
        setButtonVisible(mAllWords.get(wordButton.mIndex), View.VISIBLE);
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

    /**
     * 处理删除待选文字事件
     */
    private void handleDeleteWord() {

        mBtnDeleteword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOneWord();
            }
        });
    }

    /**
     * 处理提示按键的事件
     */
    private void handleTipAnswer() {

        tipOneWord();
    }

    /**
     * 增加或者减少指定数量的金币
     */

    private boolean handleCoins(int data) {

        //判断当前总的金币数量是否可被减少
        if (mCurrentCoins + data >= 0) {

            mCurrentCoins += data;
            mViewCorrentCoins.setText(mCurrentCoins + "");
            return false;

        } else {

            //金币不够

            return true;
        }
    }

    /**
     * 配置文件中删除花费金币数量
     */
    private int getDeleteWordCoins() {
        return this.getResources().getInteger(R.integer.pay_delete_word);

    }

    /**
     * 配置文件中提示字体花费的金币数量
     */
    private int getTipAnswerCoins() {
        return this.getResources().getInteger(R.integer.pay_tip_answer);

    }

    /**
     * 删除文字所花费的金币数量
     */
    private void deleteOneWord() {

        //减少金币
        if (handleCoins(-getDeleteWordCoins())) {

            //金币不够显示提示对话框
            return;
        }
        //将这个索引对应的待选文字框设置为不可见
        setButtonVisible(findNotAnswer(), View.INVISIBLE);
    }

    /**
     * 找到一个不是答案的文件,并且当前是可见的
     */
    private WordButton findNotAnswer() {

        Random random = new Random();

        WordButton buf = null;

        while (true) {

            int index = random.nextInt(COUNT_WORDS);

            buf = mAllWords.get(index);

            if (buf.mIsVisible && !isTheAnswerWord(buf)) {

                return buf;
            }

        }


    }

    /**
     * 判断某个文字是否为答案
     */

    public boolean isTheAnswerWord(WordButton wordButton) {

        boolean result = false;

        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {

            if (wordButton.mWordStr.equals
                    ("" + mCurrentSong.getNameCharacters()[i])) {

                result = true;
                break;
            }

        }

        return result;

    }

    /**
     * 删除文字所花费的金币数量
     */
    private void tipOneWord() {

        //减少金币
        if (handleCoins(-getDeleteWordCoins())) {

            //金币不够显示提示对话框

        }

    }
}
