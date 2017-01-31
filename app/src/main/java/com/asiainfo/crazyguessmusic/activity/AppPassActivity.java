package com.asiainfo.crazyguessmusic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.asiainfo.crazyguessmusic.R;

/**
 * 完成通关界面
 */

public class AppPassActivity extends Activity implements View.OnClickListener {
    /**
     * 关注微信奖励50金币
     */
    private ImageButton mBtnAttenWeichat;

    /**
     * 评论奖励50金币
     */
    private ImageButton mBtnBank;

    //titleBar金币余额
    private LinearLayout mLlGameCoin;

    private ImageButton mBtnBarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_pass_view);
        initView();
        initListener();
        initDatas();
    }

    private void initView() {

        mLlGameCoin = (LinearLayout) findViewById(R.id.ll_game_coin);
        mBtnAttenWeichat = (ImageButton) findViewById(R.id.id_atten_weichat);
        mLlGameCoin.setVisibility(View.GONE);
        mBtnBank = (ImageButton) findViewById(R.id.bank_button);
        mBtnBarBack = (ImageButton) findViewById(R.id.btn_bar_back);

    }

    private void initListener() {

        mBtnAttenWeichat.setOnClickListener(this);
        mBtnBank.setOnClickListener(this);
        mBtnBarBack.setOnClickListener(this);

    }

    private void initDatas() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bank_button:
                break;

            case R.id.id_atten_weichat:
                break;

            case R.id.btn_bar_back:
                break;

            default:
                break;

        }
    }

}
