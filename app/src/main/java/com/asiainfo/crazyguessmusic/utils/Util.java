package com.asiainfo.crazyguessmusic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.data.Const;
import com.asiainfo.crazyguessmusic.interfc.IAlertDialogButtonListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 打造通用工具类
 */

public class Util {

    private static AlertDialog mAlertDialog;

    /**
     * 界面跳转
     */
    public static void startActivity(Context context, Class desti) {
        Intent intent = new Intent();
        intent.setClass(context, desti);
        context.startActivity(intent);

        //关闭当前Activity
        ((Activity) context).finish();

    }

    /**
     * 显示自定义对话框
     */

    public static void showDialog(final Context context, String msg, final IAlertDialogButtonListener listener) {

        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_Transparent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.dialog_view, null);
        ImageButton btnOKView = (ImageButton) dialogView.findViewById(R.id.btn_dialog_ok);
        ImageButton btnCancleView = (ImageButton) dialogView.findViewById(R.id.btn_dialog_cancle);
        TextView TxtMsgView = (TextView) dialogView.findViewById(R.id.text_dialog_msg);
        TxtMsgView.setText(msg);
        btnOKView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //关闭对话框
                if (mAlertDialog != null) {

                    mAlertDialog.cancel();

                }

                //事件回调
                if (listener != null) {
                    listener.OnClick();
                }

                MyPlayer.playTone(context, MyPlayer.INDEX_STONE_ENTER);
            }
        });
        btnCancleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭对话框
                if (mAlertDialog != null) {

                    mAlertDialog.cancel();

                }

                MyPlayer.playTone(context, MyPlayer.INDEX_STONE_CANCEL);

            }
        });

        /**
         * 为Dialog设置view
         */
        builder.setView(dialogView);
        mAlertDialog = builder.create();

        //显示对话框
        mAlertDialog.show();
    }

    /**
     * @date 2/1/17 01:36
     * @Method 文件读取
     * @description Util
     * @author MicroKibaco
     */
    public static int[] loadData(Context context) {

        FileInputStream fis = null;
        int[] datas = {-1, Const.TOTAL_COINS};

        try {
            fis = context.openFileInput(Const.FILE_NAME_SAVE_DATA);
            DataInputStream dis = new DataInputStream(fis);
            datas[Const.INDEX_LOAD_DATA_STAGE] = dis.readInt();
            datas[Const.INDEX_LOAD_DATA_COINS] = dis.readInt();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {

                    fis.close();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return datas;
    }

    /**
     * @date 2/1/17 01:27
     * @Method 游戏数据保存
     * @description Util
     * @author MicroKibaco
     */

    public static void saveDatas(Context context, int stageIndex, int coins) {

        FileOutputStream fis = null;

        try {
            fis = context.openFileOutput(Const.FILE_NAME_SAVE_DATA, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fis);

            dos.writeInt(stageIndex);
            dos.writeInt(coins);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
