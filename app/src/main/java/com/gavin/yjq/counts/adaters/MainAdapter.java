package com.gavin.yjq.counts.adaters;

/**
 * Created by Gavin_Y on 2017/4/4.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.gavin.yjq.counts.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<ViewGroup> mDatas;
    private LayoutInflater mInflater;

    public MainAdapter(Context context, List<ViewGroup> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }
    public MainAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.re_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        ViewGroup parent = (ViewGroup) mDatas.get(position).getParent();
        if (parent!=null){
            parent.removeView(mDatas.get(position));
        }
        holder.layout.removeAllViews();
        holder.layout.addView(mDatas.get(position));
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    public ViewGroup getItem(int i){
        return mDatas.get(i);
    }

    public void addData(ViewGroup view){
        mDatas.add(view);
        notifyItemRangeInserted(mDatas.size()-1,1);
    }


    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAllData(){
        for (int i = mDatas.size()-1; i >=0 ; i--) {
            mDatas.remove(i);
            notifyItemRemoved(i);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;

        public MyViewHolder(View view) {
            super(view);
            layout = (RelativeLayout)view.findViewById(R.id.layout);
        }
    }
}
