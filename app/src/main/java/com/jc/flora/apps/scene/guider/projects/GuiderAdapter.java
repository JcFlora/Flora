package com.jc.flora.apps.scene.guider.projects;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Samurai on 2017/4/29.
 */
public class GuiderAdapter extends PagerAdapter {
    private List<View> mViews;

    public GuiderAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
     public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews != null && mViews.size() > 0) {
            View view = mViews.get(position);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mViews != null && mViews.size() > 0) {
            View view = mViews.get(position);
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
        }
    }
}
