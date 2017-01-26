package com.asiainfo.crazyguessmusic.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Created by MicroKibaco n 1/26/17.
 */

class getViewUtil {

    public static View getView(Context context, int layoutId) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(layoutId, null);
        return layout;
    }
}
