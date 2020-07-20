package com.jc.flora.apps.component.router.projects;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.delegate.MultiRouterDelegate;

/**
 * Created by Shijincheng on 2018/12/28.
 */

public class ShareData2Fragment extends Fragment {

    private MultiRouterActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MultiRouterActivity)getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_router_share_data_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText etShareData = (EditText) view.findViewById(R.id.et_share_data);
        etShareData.setText(mActivity.getShareData());
        view.findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setShareData(etShareData.getText().toString().trim());
                MultiRouterDelegate.getFragmentRouter().popFragment(ShareData2Fragment.this);
            }
        });
    }

}
