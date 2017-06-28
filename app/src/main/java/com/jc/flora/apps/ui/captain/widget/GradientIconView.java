package com.jc.flora.apps.ui.captain.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by David Wong on 2015/10/20.
 */
public class GradientIconView extends FrameLayout {

    private ImageView mTopIconView;
    private ImageView mBottomIconView;

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    private float mAlpha = 0f;

    public GradientIconView(Context context) {
        this(context, null, 0);
    }

    public GradientIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        setIconAlpha(mAlpha);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gradient_icon_layout, this, true);
        mTopIconView = (ImageView) findViewById(R.id.top_icon_view);
        mBottomIconView = (ImageView) findViewById(R.id.bottom_icon_view);
    }

    public void setIconAlpha(float alpha) {
        mTopIconView.setAlpha(alpha);
        mBottomIconView.setAlpha(1 - alpha);
        this.mAlpha = alpha;
    }

    public void setTopIconView(int drawable) {
        mTopIconView.setImageResource(drawable);
    }

    public void setBottomIconView(int drawable) {
        mBottomIconView.setImageResource(drawable);
    }

    public void setTopIconView(Drawable drawable) {
        mTopIconView.setImageDrawable(drawable);
    }

    public void setBottomIconView(Drawable drawable) {
        mBottomIconView.setImageDrawable(drawable);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(STATE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

}
