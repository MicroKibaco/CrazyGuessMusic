package com.asiainfo.crazyguessmusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.utils.MyPlayer;

/**
 * 模拟闪屏页
 */

public class LaucherActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        MyPlayer.playTone(LaucherActivity.this, MyPlayer.INDEX_STONE_BEGIN);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //loadingLay.setBackgrouResource(R.mipmap.launcher_bg);
                startActivity(new Intent(LaucherActivity.this, GrazyGuessMusicActivity.class));
                finish();
            }
        }, 4000);

    }
}
