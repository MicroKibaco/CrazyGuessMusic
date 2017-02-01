package com.asiainfo.crazyguessmusic.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by MicroKibaco on 2/1/17.
 */

public class WeXinUtil {

    public static final String APP_ID = "wx0b4ecb0f974c5a4b";

    private static final int THUMB_SIZE = 150;
    private static WeXinUtil mInstance;
    private IWXAPI mApi;
    private Context mContext;

    private WeXinUtil(Context context) {

        mContext = context;

        mApi = WXAPIFactory.createWXAPI(context, APP_ID, false);

        mApi.registerApp(APP_ID);
    }

    public static WeXinUtil getInstance(Context context) {

        if (mInstance == null) {

            mInstance = new WeXinUtil(context);

        }

        return mInstance;


    }

    /**
     * @date 2/1/17 10:58
     * @Method 发送文本信息到微信
     * @description WeXinUtil
     * @author MicroKibaco
     */
    public void sentRequest(String text) {

        WXTextObject textObj = new WXTextObject();

        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = textObj;

        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = "txt" + String.valueOf(System.currentTimeMillis());

        req.message = msg;

        //sendMessageToWX.Req.WXSceneSession 发送至微信对话里
        //sendMessageToWX.Req.WXSceneTimeline 发送至盆友圈

        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);

    }

    /***
     * 发送图片至微信
     */

    public void sentBitmap(Bitmap bitmap) {

        WXImageObject imgobj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = imgobj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);

        bitmap.recycle();

        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();


        req.transaction = "img" + String.valueOf(System.currentTimeMillis());

        req.message = msg;

        //sendMessageToWX.Req.WXSceneSession 发送至微信对话里
        //sendMessageToWX.Req.WXSceneTimeline 发送至盆友圈

        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);


    }


}
