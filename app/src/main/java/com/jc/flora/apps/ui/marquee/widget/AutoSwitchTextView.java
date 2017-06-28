package com.jc.flora.apps.ui.marquee.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;

public class AutoSwitchTextView extends TextSwitcher {

    private int mPosition = 0;
    private int mCount = 0;
    private boolean mIsSwitching = false;
    private TurnUpAnimation mAnimInUp;
    private TurnUpAnimation mAnimOutUp;
    private SwitchAdapter mAdapter;

    public AutoSwitchTextView(Context context) {
        this(context, null);
    }

    public AutoSwitchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwitchItemClickListener(final OnSwitchItemClickListener l) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onSwitchItemClicked(AutoSwitchTextView.this, mPosition);
                }
            }
        });
    }

    public void setAdapter(final SwitchAdapter adapter) {
        if (adapter == null || adapter.getCount() == 0) {
            return;
        }
        mAdapter = adapter;
        mIsSwitching = false;
        removeAllViews();
        mCount = adapter.getCount();
        setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                return adapter.getView();
            }
        });
        mAnimInUp = createAnim(adapter.getAnimDuration(), true);
        mAnimOutUp = createAnim(adapter.getAnimDuration(), false);
        setText(adapter.getShowContent(0));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onSwitchItemClicked(AutoSwitchTextView.this, mPosition);
            }
        });
    }

    private TurnUpAnimation createAnim(int animDuration, boolean isTurnIn) {
        final TurnUpAnimation rotation = new TurnUpAnimation(isTurnIn);
        rotation.setDuration(animDuration);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    public void start() {
        if(mIsSwitching){
            return;
        }
        mIsSwitching = true;
        new Thread() {
            @Override
            public void run() {
                while (mIsSwitching) {
                    try {
                        Thread.sleep(mAdapter.getSwitchDuration());
                    } catch (InterruptedException e) {
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showNextTextViewByAnim();
            if (mAdapter != null) {
                mPosition++;
                if (mPosition >= mCount) {
                    mPosition = 0;
                }
                setText(mAdapter.getShowContent(mPosition));
            }
        }
    };

    // 定义动作，向上滚动翻页
    public void showNextTextViewByAnim() {
        if (getInAnimation() != mAnimInUp) {
            setInAnimation(mAnimInUp);
        }
        if (getOutAnimation() != mAnimOutUp) {
            setOutAnimation(mAnimOutUp);
        }
    }

    public void stop() {
        mIsSwitching = false;
    }

    public boolean isSwitching() {
        return mIsSwitching;
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    private class TurnUpAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private Camera mCamera;

        public TurnUpAnimation(boolean turnIn) {
            mTurnIn = turnIn;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 1.5f;
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final Matrix matrix = t.getMatrix();
            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, mCenterY * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public interface SwitchAdapter {
        int getCount();

        int getAnimDuration();

        long getSwitchDuration();

        View getView();

        String getShowContent(int position);

        void onSwitchItemClicked(AutoSwitchTextView parent, int position);

    }

    public interface OnSwitchItemClickListener {
        void onSwitchItemClicked(AutoSwitchTextView parent, int position);
    }

}
