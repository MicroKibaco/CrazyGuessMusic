package com.asiainfo.crazyguessmusic.model;

import android.widget.Button;

/**
 * 文字按钮
 */

public class WordButton {

    public int mIndex;
    public boolean mIsVisible;
    public String mWordStr;
    public Button mViewBtn;

    public WordButton() {
        mIsVisible = true;
        mWordStr = "";
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public boolean isVisible() {
        return mIsVisible;
    }

    public void setVisible(boolean visible) {
        mIsVisible = visible;
    }

    public String getWordStr() {
        return mWordStr;
    }

    public void setWordStr(String wordStr) {
        mWordStr = wordStr;
    }

    public Button getViewBtn() {
        return mViewBtn;
    }

    public void setViewBtn(Button viewBtn) {
        mViewBtn = viewBtn;
    }
}
