package com.jc.flora.apps.ui.banner.projects;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Samurai on 2017/4/29.
 */
public class BannerLoopPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private int mSize;

    public BannerLoopPagerAdapter(List<View> views) {
        mViews = views;
        mSize = views == null ? 1 : views.size();
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews != null && mViews.size() > 0) {
            View view = mViews.get(position % mSize);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mViews != null && mViews.size() > 0) {
            View view = mViews.get(position % mSize);
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
        }
    }
}
