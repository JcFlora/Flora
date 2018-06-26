package com.jc.flora.apps.ui.dialog.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

import com.jc.flora.R;
import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class TestBottomSheetDialog extends BottomSheetDialog{

    private static final boolean HIDE_WHEN_SWIPED_DOWN = false;

//    private BottomSheetBehavior mBottomSheetBehavior;
//    private Activity mActivity;

    public TestBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public TestBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    public TestBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        // 加载布局
        setContentView(R.layout.dialog_bottom_sheet);
        // 设置对话框的宽度和高度
        if(getWindow() != null){
            int dialogHeight = DeviceUtil.getScreenHeight(getContext());
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight);
        }
//        // 老版本高度需要去除掉StatusBar的高度，25版本已不需要
//        // 设置对话框的宽度和高度
//        if(getWindow() != null) {
//            int dialogHeight = DeviceUtil.getHeightExcludeStatusBar(getContext());
//            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight);
//        }
        // 设置是否支持滑动退出
        View bottomSheet = findViewById(android.support.design.R.id.design_bottom_sheet);
        if(bottomSheet != null){
            BottomSheetBehavior.from(bottomSheet).setHideable(HIDE_WHEN_SWIPED_DOWN);
        }
    }

//    private Activity getActivity(){
//        if(mActivity == null){
//            Context ctx = getContext();
//            if(ctx instanceof Activity){
//                mActivity = (Activity) ctx;
//            }else if(ctx instanceof ContextThemeWrapper){
//                Context base = ((ContextThemeWrapper)ctx).getBaseContext();
//                if(base instanceof Activity){
//                    mActivity = (Activity) base;
//                }
//            }
//        }
//        return mActivity;
//    }

//    //下面这段代码是处理老版本的bug，25版本已修复，如果加上去会有新的bug
//    private void init(View bottomSheet){
//        // 设置默认初始高度
//        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//        mBottomSheetBehavior.setPeekHeight(100);
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
//    }

//    //下面这段代码是处理老版本的bug，25版本已修复，如果加上去会有新的bug
//    @Override
//    public void show() {
//        super.show();
//        if(mBottomSheetBehavior != null){
//            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        }
//    }

//    //下面这段代码是处理老版本的bug，25版本已修复，如果加上去会有新的bug
//    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
//            = new BottomSheetBehavior.BottomSheetCallback() {
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet,
//                                   @BottomSheetBehavior.State int newState) {
//            if (newState >= BottomSheetBehavior.STATE_COLLAPSED) {
//                dismiss();
//            }
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//        }
//    };

}
