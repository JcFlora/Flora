package com.jc.flora.apps.component.signal.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2018/12/28.
 */

public class Signal3SecondFragment extends Fragment {

    private OnGoBackListener mListener;

    public void setListener(OnGoBackListener listener) {
        mListener = listener;
    }

    public void push(Fragment fragment, String tag, String backStack){
        int containerViewId = ((View)fragment.getView().getParent()).getId();
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(containerViewId, this, tag)
                .addToBackStack(backStack)
                .commitAllowingStateLoss();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_signal3_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText etShareData = (EditText) view.findViewById(R.id.et_share_data);
        view.findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onGoBack(etShareData.getText().toString().trim());
                }
                if(!getFragmentManager().popBackStackImmediate()){
                    getActivity().onBackPressed();
                }
            }
        });
    }

    public interface OnGoBackListener{
        void onGoBack(String content);
    }

}
