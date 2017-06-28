package com.jc.flora.apps.ui.dialog.projects;

import android.app.Activity;
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

    private BottomSheetBehavior mBottomSheetBehavior;
    private Activity mActivity;

    public TestBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public TestBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected TestBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        setContentView(contentView);
        init(contentView);
    }

    @Override
    public void show() {
        super.show();
        if(mBottomSheetBehavior != null)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    protected void init(View contentView){
        int dialogHeight = DeviceUtil.getHeightExcludeStatusBar(getContext());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight);
        mBottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
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

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState >= BottomSheetBehavior.STATE_COLLAPSED) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

}
