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


    //private static MyPlayer ourInstance = new MyPlayer();

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
