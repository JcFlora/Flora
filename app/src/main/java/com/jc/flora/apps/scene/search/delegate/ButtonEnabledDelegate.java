package com.jc.flora.apps.scene.search.delegate;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jc.flora.R;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 控制登录按钮是否可用（使用RxJava）
 * compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
 * Created by Samurai on 2020/11/18.
 */
public class ButtonEnabledDelegate {

    public static void setBtnSearchEnabled(EditText etSearch, final TextView btnSearch) {
        Observable<CharSequence> searchWord = RxTextView.textChanges(etSearch);
        final Disposable disposable = searchWord.map(new Function<CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence searchWord) throws Exception {
                return !TextUtils.isEmpty(searchWord);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean enabled) throws Exception {
                btnSearch.setEnabled(enabled);
                if (enabled) {
                    btnSearch.setTextColor(btnSearch.getContext().getResources().getColor(R.color.app_color_text_black));
                } else {
                    btnSearch.setTextColor(btnSearch.getContext().getResources().getColor(R.color.app_color_text_unable));
                }
            }
        });
    }

}