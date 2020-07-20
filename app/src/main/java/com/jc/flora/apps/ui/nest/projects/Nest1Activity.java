package com.jc.flora.apps.ui.nest.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.request.volley.L;

/**
 * Created by shijincheng on 2018/2/13.
 */
public class Nest1Activity extends AppCompatActivity {

    private RecyclerView mRv1, mRv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用NestedScrollView，无复用机制");
        setContentView(R.layout.activity_list_nest1);
        findViews();
        initViews();
    }

    private void findViews() {
        mRv1 = (RecyclerView) findViewById(R.id.rv_1);
        mRv2 = (RecyclerView) findViewById(R.id.rv_2);
    }

    private void initViews() {
        mRv1.setNestedScrollingEnabled(false);
        mRv2.setNestedScrollingEnabled(false);
        mRv1.setHasFixedSize(false);
        mRv1.setLayoutManager(new LinearLayoutManager(this));
        mRv2.setLayoutManager(new LinearLayoutManager(this));
        mRv1.setAdapter(new MyAdapter());
        mRv2.setAdapter(new MyAdapter());
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_list, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            L.e("ListNest1Activity",position+"");
            holder.tvTitle.setText(position+"");
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}