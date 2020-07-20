package com.jc.flora.apps.component.signal.projects;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2019/1/17.
 */

public class Signal3FirstFragment extends Fragment{

    private TextView mTvReceiveContent;

    public void addToActivity(AppCompatActivity activity, int containerViewId, String tag){
        if(activity != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(containerViewId, this, tag)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_signal3_first_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_push_second_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushSecondFragment();
            }
        });
        mTvReceiveContent = view.findViewById(R.id.tv_receive_content);
    }

    private void pushSecondFragment(){
        Signal3SecondFragment fragment = new Signal3SecondFragment();
        fragment.setListener(new Signal3SecondFragment.OnGoBackListener() {
            @Override
            public void onGoBack(String content) {
                mTvReceiveContent.setText(content);
            }
        });
        fragment.push(this, "secondFragment", getActivity().getLocalClassName());
    }

}
