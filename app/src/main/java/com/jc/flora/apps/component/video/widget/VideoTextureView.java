package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class VideoTextureView extends TextureView {

    private int mVideoWidth, mVideoHeight;

    public VideoTextureView(Context context) {
        super(context);
    }

    public VideoTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取父容器提供的宽高
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

        if (mVideoWidth > 0 && mVideoHeight > 0) {
            // 获取宽高模式和数值
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            // 宽高都是EXACTLY模式（父容器宽高已知，并且当前未设置wrapContent）
            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {

                // 首先配置成父容器提供的宽高
                width = widthSpecSize;
                height = heightSpecSize;

                // 为了实现控件尺寸自适应，根据视频实际尺寸修改当前控件的宽高
                if (mVideoWidth * height  < width * mVideoHeight) {
                    // 控件相对于视频来说，"太宽了"，调小控件宽度显示，实际效果左右带黑边
                    width = height * mVideoWidth / mVideoHeight;
                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
                    // 控件相对于视频来说，"太高了"，调小控件高度显示，实际效果上下带黑边
                    height = width * mVideoHeight / mVideoWidth;
                }
            }
            // 只有宽度是EXACTLY模式（父容器宽高已知，当前控件高度设置wrapContent）
            else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // 宽度和父容器提供的一致, 根据视频实际尺寸修改当前控件的高度
                width = widthSpecSize;
                height = width * mVideoHeight / mVideoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // 高度大于父容器高度，使用父容器高度，实际效果是高度被压缩变形
                    height = heightSpecSize;
                }
            }
            // 只有高度是EXACTLY模式（父容器宽高已知，当前控件宽度设置wrapContent）
            else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // 高度和父容器提供的一致, 根据视频实际尺寸修改当前控件的宽度
                height = heightSpecSize;
                width = height * mVideoWidth / mVideoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // 宽度大于父容器宽度，使用父容器宽度，实际效果是宽度被压缩变形
                    width = widthSpecSize;
                }
            }
            // 其他情况（比如都设置wrapContent）
            else {
                // 默认使用视频本身的尺寸
                width = mVideoWidth;
                height = mVideoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // 高度大于父容器高度，使用父容器高度，宽度按比例自适应
                    height = heightSpecSize;
                    width = height * mVideoWidth / mVideoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // 宽度大于父容器宽度，使用父容器宽度，高度按比例自适应
                    width = widthSpecSize;
                    height = width * mVideoHeight / mVideoWidth;
                }
            }
        } else {
            // 未获取到视频尺寸，按父容器提供宽高显示
        }
        setMeasuredDimension(width, height);
    }

}