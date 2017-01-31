package com.asiainfo.crazyguessmusic.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @author MicroKibaco
 * @date 1/31/17 21:52
 * @Method 音乐播放器
 * @description MyPlayer
 */

public class MyPlayer {

    //索引
    public final static int INDEX_STONE_ENTER = 0;
    public final static int INDEX_STONE_CANCEL = 1;
    public final static int INDEX_STONE_COIN = 2;

    //音效的文件名
    private final static String SONGNAME[] = {"enter.mp3", "cancel.mp3", "coin.mp3",};

    //private static MyPlayer ourInstane = new MyPlayer();

    //音效
    private static MediaPlayer[] mToneMediaPlayer
            = new MediaPlayer[SONGNAME.length];

    //歌曲播放
    private static MediaPlayer mMusicMediaPlayer;

    private MyPlayer() {
    }

    public static void playSong(Context context, String fileName) {

        if (mMusicMediaPlayer == null) {

            mMusicMediaPlayer = new MediaPlayer();
        }

        //强制重置:
        mMusicMediaPlayer.reset();

        //加载声音文件
        AssetManager assertmanager = context.getAssets();
        try {
            AssetFileDescriptor fileDescription = assertmanager.openFd(fileName);
            mMusicMediaPlayer.setDataSource(fileDescription.getFileDescriptor(),
                    fileDescription.getStartOffset(),
                    fileDescription.getLength());

            mMusicMediaPlayer.prepare();

            //声音播放
            mMusicMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @date 2/1/17 00:43
     * @Method 播放声音效果
     * @description 播放音效, 按点击的声音, 无须反复加载
     * @author MicroKibaco
     */

    public static void playTone(Context context, int index) {

        //加载声音文件
        AssetManager assertmanager = context.getAssets();

        if (mToneMediaPlayer[index] == null) {

            mToneMediaPlayer[index] = new MediaPlayer();


            try {

                AssetFileDescriptor assetFileDescriptor = assertmanager.openFd(SONGNAME[index]);
                mToneMediaPlayer[index].setDataSource(assetFileDescriptor.getFileDescriptor(),
                        assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

                mToneMediaPlayer[index].prepare();


            } catch (IOException e) {
                e.printStackTrace();
            }

            mToneMediaPlayer[index].start();

        }


    }

    /**
     * @date 1/31/17 22:34
     * @Method 音乐的暂停
     * @description MyPlayer
     * @author MicroKibaco
     */

    public static void stopTheSong(Context context) {

        if (mMusicMediaPlayer != null) {

            mMusicMediaPlayer.stop();

        }

    }

 /*   public static MyPlayer genstance() {
        if (ourInstance == null){}
        return ourInstance;
    }*/
}
