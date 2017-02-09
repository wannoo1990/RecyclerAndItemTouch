package com.wannoo.rit.boss;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wannoo.rit.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class AdapterBoss1 extends RecyclerView.Adapter<AdapterBoss1.Holder>{

    private final ArrayList<InfoBoss> mList = new ArrayList<>();
    private boolean isEdit;
    private InBoss clickListen;

    public AdapterBoss1() {

    }
    public void setData( ArrayList<InfoBoss> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public ArrayList<InfoBoss> getList(){
        return mList;
    }
    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }
    public void addData(InfoBoss infoBoss){
        mList.add(0,infoBoss);
        notifyItemInserted(0);
    }
    public void removeData(int pos){
        mList.remove(pos);
        notifyItemRemoved(pos);
        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }
    @Override
    public AdapterBoss1.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final InfoBoss infoBoss = mList.get(position);
        holder.text.setText(infoBoss.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("第二个删除点击开始",position+"__内容__"+infoBoss.getName()+"___"+System.currentTimeMillis());
                if(isEdit && clickListen != null){
//                    Log.e("第二个删除",mList.size()+"__长度和下标__"+position+"__内容__"+infoBoss.getName());
                    clickListen.onClick(AdapterBoss1.this ,position ,infoBoss);
                }
//                ToolsToast.showToast("position__"+position+"__id__"+ infoBoss.getId());
                Log.e("第二个删除点击结束",position+"__内容__"+infoBoss.getName()+"___"+System.currentTimeMillis());
            }
        });
        holder.btn.setVisibility(isEdit? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class Holder extends RecyclerView.ViewHolder {

        final TextView text;
        final View btn;
        public Holder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = itemView.findViewById(R.id.btn);
        }
    }

    public void setOnClick(InBoss l){
        this.clickListen = l;
    }
}
