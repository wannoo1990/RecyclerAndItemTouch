package com.wannoo.rit.boss;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wannoo.rit.R;
import com.wannoo.rit.ToolsToast;
import com.wannoo.rit.boss.helper.ItemTouchHelperAdapter;
import com.wannoo.rit.boss.helper.ItemTouchHelperViewHolder;
import com.wannoo.rit.boss.helper.OnStartDragListener;

import java.util.ArrayList;

import static android.R.attr.isDefault;

/**
 * Created by Administrator on 2017/1/11.
 */

public class AdapterBoss extends RecyclerView.Adapter<AdapterBoss.Holder>
        implements ItemTouchHelperAdapter {

    private final ArrayList<InfoBoss> mList = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private boolean isEdit;
    private InBoss clickListen;

    public AdapterBoss(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
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
    public boolean isEdit(){
        return isEdit;
    }
    public void addData(InfoBoss infoBoss){
        mList.add(infoBoss);
        notifyItemInserted(mList.size()-1);
    }
    public void changeData(int pos ,InfoBoss infoBoss){
        mList.set(pos,infoBoss);
        notifyItemChanged(pos);
    }
    public void removeData(int pos){
        mList.remove(pos);
        notifyItemRemoved(pos);
        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }
    @Override
    public AdapterBoss.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final InfoBoss infoBoss = mList.get(position);
        holder.text.setText(infoBoss.getName());
//        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    if(isEdit && 0 != position &&null != mDragStartListener){
//                        mDragStartListener.onStartDrag(holder);
//                    }
//                }
//                Log.e("长按效果","状态__"+MotionEventCompat.getActionMasked(event));
//                return false;
//            }
//        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isEdit && null != mDragStartListener){
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("第一个删除点击开始",position+"__内容__"+infoBoss.getName()+"___"+System.currentTimeMillis());
                if(isEdit  && null != clickListen){
                    boolean isDefault = TextUtils.equals("100", infoBoss.getId());
                    if(isDefault){
                        ToolsToast.showToast(holder.itemView.getContext() ,"这个不能删除");
                    }else{
                        clickListen.onClick(AdapterBoss.this , position ,infoBoss);
                    }
                }
                Log.e("第一个删除点击结束",position+"__内容__"+infoBoss.getName()+"___"+System.currentTimeMillis());
//                ToolsToast.showToast("position__"+position+"__id__"+ infoBoss.getId());
            }
        });
        holder.btn.setVisibility(isEdit&&!TextUtils.equals("100", infoBoss.getId()) ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(mList, fromPosition, toPosition);
        InfoBoss info = mList.get(fromPosition);
        mList.remove(fromPosition);
        mList.add(toPosition, info);
//        Log.e("_位置_",fromPosition+"__"+toPosition+"__onItemMove__"+mList.toString());
        notifyItemMoved(fromPosition, toPosition);
//        notifyItemRangeChanged(Math.min(fromPosition,toPosition) ,mList.size()-1);
//        notifyDataSetChanged();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);

    }

    public class Holder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        final TextView text;
        final View btn;
        public Holder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = itemView.findViewById(R.id.btn);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(0xfff0eff5);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0xffffffff);
        }
    }

    public void setOnClick(InBoss l){
        this.clickListen = l;
    }
}
