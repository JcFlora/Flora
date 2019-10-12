package com.jc.flora.apps.ui.stable.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/6/10.
 */
public class BottomBarStableBehavior extends CoordinatorLayout.Behavior<View> {

    public BottomBarStableBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 在嵌套滑动开始前回调
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        // 判断是否竖直滚动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        View layoutAudioBar = child.findViewById(R.id.tv_bottom_bar);
        onScrolledVertically(layoutAudioBar, dy);
    }

    private void onScrolledVertically(View layoutAudioBar, int dy) {
        boolean isHideAfterScroll = false;
        if(layoutAudioBar.getTag() != null){
            isHideAfterScroll = (boolean)layoutAudioBar.getTag();
        }
        //上滑 并且 正在显示播放条
        if (dy > 0 && !isHideAfterScroll) {
            //将Y属性变为播放条容器高度 (相当于隐藏了)
            layoutAudioBar.animate().translationY(((View)layoutAudioBar.getParent()).getHeight());
            layoutAudioBar.setTag(true);
        } else if (dy < 0 && isHideAfterScroll) {
            //将Y属性变为0  (相当于显示了)
            layoutAudioBar.animate().translationY(0);
            layoutAudioBar.setTag(false);
        }
    }

}