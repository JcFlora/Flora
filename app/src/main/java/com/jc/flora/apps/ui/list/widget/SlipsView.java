package com.jc.flora.apps.ui.list.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by Samurai on 2017/9/3.
 */
public class SlipsView extends LinearLayout{

    private OnItemClickListener mOnItemClickListener;

    public SlipsView(Context context) {
        super(context);
    }

    public SlipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnItemClickListener(OnItemClickListener l){
        mOnItemClickListener = l;
    }

    public void setAdapter(BaseAdapter adapter){
        removeAllViews();
        if(adapter == null || adapter.getCount() == 0){
            return;
        }
        int count  = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int j = i;
            View v = adapter.getView(i, null, this);
            addView(v);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(j);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}