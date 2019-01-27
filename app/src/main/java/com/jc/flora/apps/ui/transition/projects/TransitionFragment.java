package com.jc.flora.apps.ui.transition.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.projects.NotFoundFragment;

/**
 * Created by Shijincheng on 2019/1/27.
 */
public class TransitionFragment extends Fragment{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transition_root, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_slide_next_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideNextFragment();
            }
        });
        view.findViewById(R.id.btn_fade_next_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeNextFragment();
            }
        });
    }

    private void slideNextFragment(){
        pushNotFoundFragment(R.anim.page_enter_slide, R.anim.page_exit_slide, R.anim.page_reenter_slide, R.anim.page_return_slide);
    }

    private void fadeNextFragment(){
        pushNotFoundFragment(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void pushNotFoundFragment(int enter, int exit, int popEnter, int popExit) {
        NotFoundFragment fg = new NotFoundFragment();
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enter, exit, popEnter, popExit)
                .hide(TransitionFragment.this)
                .add(getContainerViewIdByFragment(TransitionFragment.this), fg, "notFoundFragment")
                .addToBackStack(getActivity().getLocalClassName())
                .commitAllowingStateLoss();
    }

    private static int getContainerViewIdByFragment(Fragment fragment){
        return ((View)fragment.getView().getParent()).getId();
    }

}