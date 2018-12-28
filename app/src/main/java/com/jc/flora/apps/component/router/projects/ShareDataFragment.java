package com.jc.flora.apps.component.router.projects;

import android.app.Activity;
import android.os.Bundle;
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

public class ShareDataFragment extends Fragment {

    private FragmentRouterActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentRouterActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_router_share_data_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText etShareData = (EditText) view.findViewById(R.id.et_share_data);
        etShareData.setText(mActivity.getShareData());
        view.findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setShareData(etShareData.getText().toString().trim());
                RouterUtil.popFragment(ShareDataFragment.this);
            }
        });
    }

}
