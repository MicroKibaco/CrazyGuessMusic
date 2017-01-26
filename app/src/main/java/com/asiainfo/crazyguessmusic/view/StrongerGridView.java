package com.asiainfo.crazyguessmusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.model.WordButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义GridView
 */

public class StrongerGridView extends GridView {

    private List<WordButton> mWordBtnList = new ArrayList<>();

    private StrongerGridAdapter mStrongerGridAdapter;

    private Context mContext;

    public StrongerGridView(Context context) {
        super(context);
    }

    public StrongerGridView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mStrongerGridAdapter = new StrongerGridAdapter();
        this.setAdapter(mStrongerGridAdapter);
        mContext = context;


    }

    public StrongerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    class StrongerGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return mWordBtnList.size();
        }

        @Override
        public Object getItem(int position) {

            return mWordBtnList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            WordButton holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.self_view_grideview_item, null);

                holder = mWordBtnList.get(position);
                holder.mIndex = position;
                holder.mViewBtn = (Button) convertView.findViewById(R.id.item_btn);
                convertView.setTag(holder);

            } else {
                holder = (WordButton) convertView.getTag();
            }
            holder.mViewBtn.setText(holder.mWordStr);
            return convertView;
        }
    }

}
