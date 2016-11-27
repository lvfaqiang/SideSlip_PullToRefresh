package com.lfq.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lfq.pulltorefresh.library.PullToRefreshBase;
import com.lfq.pulltorefresh.library.PullToRefreshRecyclerView;
import com.lfq.test.R;
import com.lfq.test.util.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * --------------------------------------------
 * auther :  Lvfq
 * 2016/11/26 15:41
 * description ：
 * -------------------------------------------
 **/
public class PullRecyclerViewActivity extends FragmentActivity {

    private List<String> list = new ArrayList<>();

    private PullToRefreshRecyclerView pull_recyclerView;
    private RecyclerView recyclerView;
    private RecylerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        DataModel.initData(list, "RecyclerView");

        pull_recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.pull_recyclerView);

        recyclerView = pull_recyclerView.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pull_recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        pull_recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                // 下拉刷新监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(MainActivity.SLEEP_TIME);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list.clear();
                                    DataModel.initData(list, "RecyclerView");
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(PullRecyclerViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                    pull_recyclerView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                // 上拉加载监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(MainActivity.SLEEP_TIME);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DataModel.initData(list, "RecyclerView");
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(PullRecyclerViewActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                    pull_recyclerView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        adapter = new RecylerViewAdapter(list);
        recyclerView.setAdapter(adapter);

    }


    public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
        private List<String> list;

        public RecylerViewAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }

}
