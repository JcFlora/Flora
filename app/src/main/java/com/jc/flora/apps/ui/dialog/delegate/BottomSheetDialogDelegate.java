package com.jc.flora.apps.ui.dialog.delegate;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * Created by Shijincheng on 2018/6/26.
 */

public class BottomSheetDialogDelegate {

    private static final boolean HIDE_WHEN_SWIPED_DOWN = false;

    private static final String ROLE_STUDENT = "1";
    private static final String ROLE_TEACHER = "2";

    private AppCompatActivity mActivity;
    private TextView mBtn;
    private BottomSheetDialog mDialog;
    private BottomSheetDialogDelegate.ViewHolder mHolder;
    private String mRole = "";

    private OnRoleCheckedListener mOnRoleCheckedListener;

    public BottomSheetDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
        mDialog = new BottomSheetDialog(activity);
        onDialogCreated();
        onCreateViewHolder();
        onBindViewHolder();
    }

    public void setOnRoleCheckedListener(OnRoleCheckedListener l) {
        mOnRoleCheckedListener = l;
    }

    public void init(TextView button){
        mBtn = button;
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    private void onDialogCreated(){
        // 设置自定义布局
        mDialog.setContentView(R.layout.dialog_bottom_sheet2);
        // 设置可关闭
        mDialog.setCancelable(true);
        // 设置对话框的宽度和高度
        if(mDialog.getWindow() != null){
            int dialogHeight = DeviceUtil.getScreenHeight(mActivity);
            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight);
        }
        // 设置是否支持滑动退出
        View bottomSheet = mDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if(bottomSheet != null){
            BottomSheetBehavior.from(bottomSheet).setHideable(HIDE_WHEN_SWIPED_DOWN);
        }
    }

    private void onCreateViewHolder(){
        mHolder = new BottomSheetDialogDelegate.ViewHolder();
        mHolder.layoutStudent = mDialog.findViewById(R.id.layout_student);
        mHolder.layoutTeacher = mDialog.findViewById(R.id.layout_teacher);
        mHolder.ivStudentCheck = mDialog.findViewById(R.id.iv_student_check);
        mHolder.ivTeacherCheck = mDialog.findViewById(R.id.iv_teacher_check);
    }

    private void onBindViewHolder(){
        mHolder.layoutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRoleChecked(ROLE_STUDENT);
            }
        });
        mHolder.layoutTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRoleChecked(ROLE_TEACHER);
            }
        });
        switch (mRole){
            case ROLE_STUDENT:
                mHolder.layoutTeacher.performClick();
                break;
            case ROLE_TEACHER:
                mHolder.layoutStudent.performClick();
                break;
        }
    }

    private void onRoleChecked(String role){
        mRole = role;
        boolean isStudent = ROLE_STUDENT.equals(role);
        boolean isTeacher = ROLE_TEACHER.equals(role);
        mBtn.setText(isStudent ? "学生" : "老师");
        mHolder.ivStudentCheck.setVisibility(isStudent ? View.VISIBLE : View.GONE);
        mHolder.ivTeacherCheck.setVisibility(isTeacher ? View.VISIBLE : View.GONE);
        if(mOnRoleCheckedListener != null){
            mOnRoleCheckedListener.onRoleChecked(role);
        }
    }

    private static class ViewHolder{
        private View layoutStudent;
        private View layoutTeacher;
        private View ivStudentCheck;
        private View ivTeacherCheck;
    }

    public interface OnRoleCheckedListener{
        void onRoleChecked(String role);
    }

}
