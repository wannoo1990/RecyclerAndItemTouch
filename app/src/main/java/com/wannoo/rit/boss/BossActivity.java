package com.wannoo.rit.boss;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wannoo.rit.R;
import com.wannoo.rit.ToolsToast;
import com.wannoo.rit.boss.helper.OnStartDragListener;
import com.wannoo.rit.boss.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class BossActivity extends AppCompatActivity {
    private Context context;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        context = this;
        final TextView btn = (TextView) findViewById(R.id.btn);
        RecyclerView mRecycler = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView mRecycler1 = (RecyclerView) findViewById(R.id.recycler1);

        GridLayoutManager manager = new GridLayoutManager(context, 3);
        mRecycler.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(context, 3);
        mRecycler1.setLayoutManager(manager1);
        final AdapterBoss mAdapter = new AdapterBoss(new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
        final AdapterBoss1 mAdapter1 = new AdapterBoss1();

        mAdapter.setOnClick(new InBoss() {
            @Override
            public void onClick(RecyclerView.Adapter adapter, int pos, InfoBoss info) {
                mAdapter.removeData(pos);
                mAdapter1.addData(info);
                Log.e("第一个删除完成","___"+System.currentTimeMillis());
            }
        });
        mAdapter1.setOnClick(new InBoss() {
            @Override
            public void onClick(RecyclerView.Adapter adapter, int pos, InfoBoss info) {
                if(6 <= mAdapter.getItemCount()){
                    ToolsToast.showToast(context ,"最多只能六个" );
                    return;
                }
                mAdapter.addData(info);
                mAdapter1.removeData(pos);
                Log.e("第二个删除完成","___"+System.currentTimeMillis());
            }
        });


        mRecycler.setAdapter(mAdapter);
        mRecycler1.setAdapter(mAdapter1);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecycler);

        final ArrayList<InfoBoss> list = new ArrayList<>();
        list.add(new InfoBoss("100","这个必须"));
        ArrayList<InfoBoss> list1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            InfoBoss infoBoss = new InfoBoss("" + i, "未选" + i);
            list1.add(infoBoss);
        }
        mAdapter.setData(list);
        mAdapter1.setData(list1);

        mRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.e("整个点击_","__"+event.getAction());
                if(mAdapter.isEdit() && MotionEvent.ACTION_UP == event.getAction()){
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSure = TextUtils.equals("编辑", btn.getText());
                mAdapter.setEdit(isSure);
                mAdapter1.setEdit(isSure);
                if(isSure){
                    btn.setText("完成");
                }else{
                    btn.setText("编辑");
                }
                ArrayList<InfoBoss> list1 = mAdapter.getList();
                Log.e("打印数据__",list1.toString()+"__"+System.currentTimeMillis());
//                btn.setText(list1.toString());
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f();
            }
        });
    }
    private synchronized void f(){
        Log.e("打印数据1__","__"+System.currentTimeMillis());
    }
}
