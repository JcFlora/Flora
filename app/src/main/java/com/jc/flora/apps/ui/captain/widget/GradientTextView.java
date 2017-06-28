package com.jc.flora.apps.ui.captain.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by David Wong on 2015/10/21.
 */
public class GradientTextView extends FrameLayout {

    private TextView mTopTextView;
    private TextView mBottomTextView;

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    private float mAlpha = 0f;

    public GradientTextView(Context context) {
        this(context, null, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        setTextViewAlpha(mAlpha);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gradient_textview_layout, this, true);
        mTopTextView = (TextView) findViewById(R.id.top_textview);
        mBottomTextView = (TextView) findViewById(R.id.bottom_textview);
    }

    public void setTopTextViewColor(int color) {
        mTopTextView.setTextColor(color);
    }

    public void setBottomTextViewColor(int color) {
        mBottomTextView.setTextColor(color);
    }

    public void setTextViewAlpha(float alpha) {
        mTopTextView.setAlpha(alpha);
        mBottomTextView.setAlpha(1 - alpha);
        this.mAlpha = alpha;
    }

    public void setGradientText(String text) {
        mTopTextView.setText(text);
        mBottomTextView.setText(text);
    }

    public void setGradientTextSize(float textSize) {
        mTopTextView.setTextSize(textSize);
        mBottomTextView.setTextSize(textSize);
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
