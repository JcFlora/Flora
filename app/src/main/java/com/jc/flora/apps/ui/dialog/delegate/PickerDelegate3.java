package com.jc.flora.apps.ui.dialog.delegate;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.Arrays;

/**
 * implementation 'com.contrarywind:Android-PickerView:4.1.3'
 * Created by Samurai on 2018/5/18.
 */
public class PickerDelegate3 {

    private static final String[] FOODS = {"KFC", "MacDonald", "Pizza hut"};
    private static final String[] CLOTHES = {"Nike", "Adidas", "Armani"};
    private static final String[] COMPUTERS = {"ASUS", "Lenovo", "Apple", "HP"};

    private AppCompatActivity mActivity;
    private TextView mBtn;
    private OptionsPickerView<String> mPicker;
    private int mSelectedIndex = -1;
    private int mSelectedIndex2 = -1;
    private int mSelectedIndex3 = -1;

    private OnPickerItemSelectedListener mItemSelectedListener;

    public PickerDelegate3(AppCompatActivity activity) {
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

    public void setSelectedIndex(int options1, int options2, int options3) {
        setSelectedIndex(options1, options2, options3, false);
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public int getSelectedIndex2() {
        return mSelectedIndex2;
    }

    public int getSelectedIndex3() {
        return mSelectedIndex3;
    }

    private void setSelectedIndex(int options1, int options2, int options3, boolean byUser) {
        if(options1 < 0 || options2 < 0 || options3 < 0){
            return;
        }
        mSelectedIndex = options1;
        mSelectedIndex2 = options2;
        mSelectedIndex3 = options3;
        mBtn.setText(FOODS[options1] +"--"+ CLOTHES[options2] +"--" + COMPUTERS[options3]);
        if(mItemSelectedListener != null){
            mItemSelectedListener.onItemSelected(options1, options2, options3, byUser);
        }
    }

    private void showPicker(){
        if(mPicker == null){
            mPicker = new OptionsPickerBuilder(mActivity, mOnOptionsSelectListener)
                    .isRestoreItem(true).build();
            mPicker.setNPicker(Arrays.asList(FOODS), Arrays.asList(CLOTHES),Arrays.asList(COMPUTERS));
        }
        mPicker.setSelectOptions(mSelectedIndex > 0 ? mSelectedIndex : 0, mSelectedIndex2 > 0 ? mSelectedIndex2 : 0, mSelectedIndex3 > 0 ? mSelectedIndex3 : 0);
        mPicker.show();
    }

    private OnOptionsSelectListener mOnOptionsSelectListener = new OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            setSelectedIndex(options1, options2, options3, true);
        }
    };

    public interface OnPickerItemSelectedListener{
        void onItemSelected(int options1, int options2, int options3, boolean byUser);
    }

}