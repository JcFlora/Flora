package com.jc.flora.apps.ui.dialog.delegate;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * implementation 'com.contrarywind:Android-PickerView:4.1.3'
 * Created by Samurai on 2018/5/18.
 */
public class PickerDelegate2 {

    private static final String[] PROVINCES = {"江苏", "广东", "湖南"};
    private static final ArrayList<List<String>> CITYES = new ArrayList<List<String>>() {
        {
            add(Arrays.asList(new String[]{"南京", "苏州", "扬州", "淮安"}));
            add(Arrays.asList(new String[]{"广州", "佛山", "东莞", "珠海"}));
            add(Arrays.asList(new String[]{"长沙", "岳阳", "株洲", "衡阳"}));
        }
    };

    private AppCompatActivity mActivity;
    private TextView mBtn;
    private OptionsPickerView<String> mPicker;
    private int mSelectedIndex = -1;
    private int mSelectedIndex2 = -1;

    private OnPickerItemSelectedListener mItemSelectedListener;

    public PickerDelegate2(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void init(TextView button){
        mBtn = button;
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }

    public void setOnItemSelectedListener(OnPickerItemSelectedListener l) {
        mItemSelectedListener = l;
    }

    public void setSelectedIndex(int options1, int options2) {
        setSelectedIndex(options1, options2, false);
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public int getSelectedIndex2() {
        return mSelectedIndex2;
    }

    private void setSelectedIndex(int options1, int options2, boolean byUser) {
        if(options1 < 0 || options2 < 0){
            return;
        }
        mSelectedIndex = options1;
        mSelectedIndex2 = options2;
        mBtn.setText(PROVINCES[options1] + CITYES.get(options1).get(options2));
        if(mItemSelectedListener != null){
            mItemSelectedListener.onItemSelected(options1, options2, byUser);
        }
    }

    private void showPicker(){
        if(mPicker == null){
            mPicker = new OptionsPickerBuilder(mActivity, mOnOptionsSelectListener)
                    .isRestoreItem(true).build();
            mPicker.setPicker(Arrays.asList(PROVINCES), CITYES);
        }
        mPicker.setSelectOptions(mSelectedIndex > 0 ? mSelectedIndex : 0, mSelectedIndex2 > 0 ? mSelectedIndex2 : 0);
        mPicker.show();
    }

    private OnOptionsSelectListener mOnOptionsSelectListener = new OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            setSelectedIndex(options1, options2, true);
        }
    };

    public interface OnPickerItemSelectedListener{
        void onItemSelected(int options1, int options2, boolean byUser);
    }

}