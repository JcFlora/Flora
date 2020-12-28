package com.jc.flora.apps.component.conduct.projects;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jc.flora.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shijincheng on 2020/11/12.
 */
public class RxJava2Activity extends AppCompatActivity {

    private static final String TAG = "RxJava2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RxJava处理异常");
        setContentView(R.layout.activity_conduct_rxjava2);
    }

    public void handleException(View view) {
        testHandleException().subscribe(new ObserverAdapter<Boolean>() {
            @Override
            public void onNext(@NonNull Boolean aBoolean) {

            }
        });
    }

    public static Observable<Boolean> testHandleException() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) {
                String s = null;
                boolean b = s.equals("test");
                emitter.onNext(b);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Observer适配类
     * @param <T>
     */
    public static abstract class ObserverAdapter<T> implements Observer<T> {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "RxJava捕获到了异常");
            // 打印错误日志
            e.printStackTrace();
        }
    }

}
