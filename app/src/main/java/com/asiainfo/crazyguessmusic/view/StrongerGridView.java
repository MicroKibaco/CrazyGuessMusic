package com.asiainfo.crazyguessmusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.asiainfo.crazyguessmusic.R;
import com.asiainfo.crazyguessmusic.interfc.IWordButtonClickListener;
import com.asiainfo.crazyguessmusic.model.WordButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义GridView
 */

public class StrongerGridView extends GridView {

    public List<WordButton> mWordBtnList = new ArrayList<>();

    public StrongerGridAdapter mStrongerGridAdapter;

    public Context mContext;

    private Animation mScaleAnimation;

    private IWordButtonClickListener mIWordButtonClickListener;

    public StrongerGridView(Context context) {
        super(context);
    }

    public StrongerGridView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mStrongerGridAdapter = new StrongerGridAdapter();
        this.setAdapter(mStrongerGridAdapter);
        this.mContext = context;


    }

    public StrongerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateDatas(ArrayList<WordButton> mwordbuttons) {

        this.mWordBtnList = mwordbuttons;

        //重新设置数据源
        setAdapter(mStrongerGridAdapter);

    }

    /**
     * 注册监听接口
     */
    public void registerOnWordButtonClick(IWordButtonClickListener listener) {

        mIWordButtonClickListener = listener;

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

            final WordButton holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.self_view_grideview_item, null);

                holder = mWordBtnList.get(position);

                //加载动画
                mScaleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scale);

                //给动画设置延迟时间
                mScaleAnimation.setStartOffset(position * 100);
                holder.mIndex = position;
                holder.mViewBtn = (Button) convertView.findViewById(R.id.item_btn);
                holder.mViewBtn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mIWordButtonClickListener.onWordButtonClick(holder);
                    }
                });

                convertView.setTag(holder);

            } else {
                holder = (WordButton) convertView.getTag();
            }
            holder.mViewBtn.setText(holder.mWordStr);

            //动画的播放
            convertView.startAnimation(mScaleAnimation);

            return convertView;
        }
    }
}
