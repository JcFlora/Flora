package com.jc.flora.apps.scene.telephone.delegate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Samurai on 2017/7/2.
 */
public class TelephoneDelegate {

    /** 当前界面 */
    private Activity mActivity;
    /** 拨打电话布局 */
    private View mBtnTelephone;
    /** 电话号码字样 */
    private TextView mTvTelephone;
    /** 电话号码 */
    private String mPhoneNumber;

    public TelephoneDelegate(Activity activity, View btnTelephone, TextView tvTelephone) {
        mActivity = activity;
        mBtnTelephone = btnTelephone;
        mTvTelephone = tvTelephone;
    }

    /**
     * 设置电话信息
     * @param phoneNumber 电话号码
     */
    public void setTelephoneInfo(String phoneNumber) {
        mPhoneNumber = phoneNumber;
        // 展示或隐藏电话号码，并检查号码是否可以直拨（无分机）
        if (checkExist() && checkPhoneNumberCanDirectDial()) {
            // 如果电话号码存在，并且该号码可以直拨（无分机），设置按钮的直拨功能
            setDirectDialEnable();
        }
    }

    public void clear(){
        mBtnTelephone.setVisibility(View.GONE);
        mBtnTelephone.setOnClickListener(null);
    }

    /**
     * 展示电话号码并检查其是否存在
     * @return
     */
    private boolean checkExist() {
        if (TextUtils.isEmpty(mPhoneNumber)) {
            mBtnTelephone.setVisibility(View.GONE);
            return false;
        }else{
            mBtnTelephone.setVisibility(View.VISIBLE);
            mTvTelephone.setText(mPhoneNumber);
            return true;
        }
    }

    /**
     * 检查号码是否可以直拨（无分机）
     * @return
     */
    private boolean checkPhoneNumberCanDirectDial() {
        if (isMobilePhoneNumber() || isLandlineTelephoneNumber()) {
            setTelephoneInfo(R.drawable.telephone_logo, 0xffffda33);
            return true;
        }else{
            setTelephoneInfo(R.drawable.telephone_logo_gray, 0xff909090);
            return false;
        }
    }

    /**
     * 检查号码是否是手机号码（11位纯数字，以1开头）
     * @return
     */
    private boolean isMobilePhoneNumber() {
        return !TextUtils.isEmpty(mPhoneNumber) && mPhoneNumber.length() == 11
                && mPhoneNumber.startsWith("1") && isNumber(mPhoneNumber);
    }

    /**
     * 检查号码是否是固定电话号码（区号验证+主机号验证）
     * @return
     */
    private boolean isLandlineTelephoneNumber() {
        String[] str = mPhoneNumber.split("-");
        return str.length == 2 && checkZone(str[0]) && checkPhone(str[1]);
    }

    /**
     * 验证区号是否合法
     * @param zone
     * @return
     */
    private boolean checkZone(String zone) {
        if (TextUtils.isEmpty(zone) || zone.length() > 4 || zone.length() < 3) {
            return false;
        }
        return isNumber(zone);
    }

    /**
     * 验证主机号是否合法
     * @param phone
     * @return
     */
    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() > 9
                || phone.length() < 7) {
            return false;
        }
        return isNumber(phone);
    }

    /**
     * 是否是纯数字
     * @param str
     * @return
     */
    private boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 设置电话信息
     * @param logo 图标
     * @param textColor 电话文字颜色
     */
    private void setTelephoneInfo(int logo,int textColor){
        mTvTelephone.setCompoundDrawablesWithIntrinsicBounds(logo, 0, 0, 0);
        mTvTelephone.setTextColor(textColor);
    }

    /**
     * 设置电话按钮的直拨功能
     */
    private void setDirectDialEnable() {
        mBtnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示去打电话的对话框
                showDialTelephoneDialog();
            }
        });
    }

    /**
     * 显示去打电话的对话框
     */
    private void showDialTelephoneDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("拨打电话");
        builder.setMessage(mPhoneNumber);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 去打电话
                go2DialTelephone();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    /**
     * 去打电话
     */
    private void go2DialTelephone() {
        try {
            Intent phoneIntent = new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + mPhoneNumber));
            mActivity.startActivity(phoneIntent);
        } catch (Exception e) {
        }
    }

}
