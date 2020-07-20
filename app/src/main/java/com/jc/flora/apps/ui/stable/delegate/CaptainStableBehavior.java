package com.jc.flora.apps.ui.stable.delegate;

import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Samurai on 2017/6/10.
 */
public class CaptainStableBehavior extends CoordinatorLayout.Behavior<View> {

    public CaptainStableBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 确定所提供的子视图是否有另一个特定的同级视图作为布局从属。
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        // 这个方法是说明这个子控件是依赖AppBarLayout的
        return dependency instanceof AppBarLayout;
    }

    // 用于响应从属布局的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // 获取跟随布局的顶部位置
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;
    }

}
