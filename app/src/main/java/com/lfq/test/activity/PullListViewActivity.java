package com.lfq.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lfq.pulltorefresh.library.PullToRefreshBase;
import com.lfq.pulltorefresh.library.PullToRefreshListView;
import com.lfq.test.R;
import com.lfq.test.util.DataModel;
import com.lfq.test.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * --------------------------------------------
 * auther :  Lvfq
 * 2016/11/26 15:41
 * description ：
 * -------------------------------------------
 **/
public class PullListViewActivity extends FragmentActivity {

    private PullToRefreshListView pull_listView;

    private List<String> list = new ArrayList<>();
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        DataModel.initData(list, "ListView");
//
        pull_listView = (PullToRefreshListView) findViewById(R.id.pull_listView);
//
        adapter = new ListViewAdapter(list, this);
        pull_listView.setMode(PullToRefreshBase.Mode.BOTH); // BOTH 下来刷新和上拉加载  ， PULL_FROM_END 上拉加载 ， PULL_FROM_START 下拉刷新 ，DISABLED ，禁止刷新和加载
        pull_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
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
                                    DataModel.initData(list, "ListView");
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(PullListViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                    pull_listView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(MainActivity.SLEEP_TIME);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DataModel.initData(list, "ListView");
                                    adapter.notifyDataSetChanged();
                                    pull_listView.onRefreshComplete();
                                    Toast.makeText(PullListViewActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        pull_listView.setAdapter(adapter);

    }


    public class ListViewAdapter extends BaseAdapter {

        private List<String> list;
        private Context context;

        public ListViewAdapter(List<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public String getItem(int i) {
            return list == null ? null : list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            }

            TextView textView = ViewHolder.get(view, android.R.id.text1);
            textView.setText(getItem(i));

            return view;
        }
    }

}
