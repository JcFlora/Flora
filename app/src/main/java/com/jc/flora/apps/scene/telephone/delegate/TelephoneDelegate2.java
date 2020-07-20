package com.jc.flora.apps.scene.telephone.delegate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


/**
 * Created by Samurai on 2017/7/4.
 */
public class TelephoneDelegate2 {

    /** 当前界面 */
    private Activity mActivity;
    /** 拨打电话布局 */
    private View mBtnTelephone;
    /** 电话号码字样 */
    private TextView mTvTelephone;

    public TelephoneDelegate2(Activity activity, View btnTelephone, TextView tvTelephone) {
        mActivity = activity;
        mBtnTelephone = btnTelephone;
        mTvTelephone = tvTelephone;
    }

    /**
     * 设置电话信息
     * @param phoneNumber 电话号码
     */
    public void setTelephoneInfo(String phoneNumber) {
        clear();
        Flowable.just(phoneNumber).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return !TextUtils.isEmpty(s);
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mBtnTelephone.setVisibility(View.VISIBLE);
                mTvTelephone.setText(s);
            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return isMobilePhoneNumber(s) || isLandlineTelephoneNumber(s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                setTelephoneInfo(R.drawable.telephone_logo, 0xffffda33);
                setDirectDialEnable(s);
            }
        });
    }

    public void clear(){
        mBtnTelephone.setVisibility(View.GONE);
        mBtnTelephone.setOnClickListener(null);
        setTelephoneInfo(R.drawable.telephone_logo_gray, 0xff909090);
    }

    /**
     * 检查号码是否是手机号码（11位纯数字，以1开头）
     * @return
     */
    private boolean isMobilePhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11
                && phoneNumber.startsWith("1") && isNumber(phoneNumber);
    }

    /**
     * 检查号码是否是固定电话号码（区号验证+主机号验证）
     * @return
     */
    private boolean isLandlineTelephoneNumber(String phoneNumber) {
        String[] str = phoneNumber.split("-");
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
    private void setDirectDialEnable(final String phoneNumber) {
        mBtnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示去打电话的对话框
                showDialTelephoneDialog(phoneNumber);
            }
        });
    }

    /**
     * 显示去打电话的对话框
     */
    private void showDialTelephoneDialog(final String phoneNumber){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("拨打电话");
        builder.setMessage(phoneNumber);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 去打电话
                go2DialTelephone(phoneNumber);
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
    private void go2DialTelephone(String phoneNumber) {
        try {
            Intent phoneIntent = new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + phoneNumber));
            mActivity.startActivity(phoneIntent);
        } catch (Exception e) {
        }
    }

}
