package com.jc.flora.apps.scene.search.delegate;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.apps.component.deviceinfo.DeviceUtil;
import com.jc.flora.apps.scene.login.delegate.InputClearDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Created by Shijincheng on 2020/11/19.
 */

public class SearchBarDelegate extends Fragment {

    private AppCompatActivity mActivity;
    private EditText mEtSearch;
    private View mBtnSearchClear;
    private TextView mBtnSearch;

    private String mSearchWord = "";

    public void setEtSearch(EditText etSearch) {
        mEtSearch = etSearch;
    }

    public void setBtnSearchClear(View btnSearchClear) {
        mBtnSearchClear = btnSearchClear;
    }

    public void setBtnSearch(TextView btnSearch) {
        mBtnSearch = btnSearch;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mActivity = activity;
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(final CallSearchRequestBridge callSearchRequestBridge){
        initInputClearPresenter();
        initBtnSearchEnabledPresenter();
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    gotoSearch(callSearchRequestBridge);
                    return true;
                }
                return false;
            }
        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearch(callSearchRequestBridge);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 输入框自动获得焦点并且打开输入法键盘
        mEtSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceUtil.showSoftKeyboard(mEtSearch);
            }
        },150);
    }

    @Override
    public void onPause() {
        super.onPause();
        DeviceUtil.hideSoftKeyboard(mActivity);
    }

    public String getSearchWord(){
        return mSearchWord;
    }

    private void initInputClearPresenter(){
        // 调用以下方法实现按钮点击清空输入内容
        InputClearDelegate.init(mEtSearch, mBtnSearchClear);
    }

    private void initBtnSearchEnabledPresenter(){
        // 调用这个方法实现控制登录按钮是否可用
        ButtonEnabledDelegate.setBtnSearchEnabled(mEtSearch, mBtnSearch);
    }

    private void gotoSearch(CallSearchRequestBridge callSearchRequestBridge){
        //DeviceUtil.hideSoftKeyboard(mActivity);
        mSearchWord = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearchWord)){
            ToastDelegate.show(mActivity,"请输入搜索关键词");
            return;
        }
        if(callSearchRequestBridge != null){
            callSearchRequestBridge.requestSearch(mSearchWord);
        }
    }

    public interface CallSearchRequestBridge{
        void requestSearch(String searchWord);
    }

}