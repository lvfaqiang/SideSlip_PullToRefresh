package com.lfq.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lfq.pulltorefresh.library.PullToRefreshBase;
import com.lfq.pulltorefresh.library.PullToRefreshSwipeRecyclerView;
import com.lfq.pulltorefresh.library.sideslip.OnSwipeMenuItemClickListener;
import com.lfq.pulltorefresh.library.sideslip.Openable;
import com.lfq.pulltorefresh.library.sideslip.SwipeMenu;
import com.lfq.pulltorefresh.library.sideslip.SwipeMenuAdapter;
import com.lfq.pulltorefresh.library.sideslip.SwipeMenuCreator;
import com.lfq.pulltorefresh.library.sideslip.SwipeMenuItem;
import com.lfq.pulltorefresh.library.sideslip.SwipeMenuRecyclerView;
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
public class PullSwipeRecyclerViewActivity extends FragmentActivity {

    private List<String> list = new ArrayList<>();
    private PullToRefreshSwipeRecyclerView pull_swipe_recyclerView;
    private SwipeMenuRecyclerView swipe_recyclerView;

    private SwipeRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recyclerview);

        DataModel.initData(list, "SwipeRecyclerView");

        pull_swipe_recyclerView = (PullToRefreshSwipeRecyclerView) findViewById(R.id.pull_swipe_recyclerView);

        pull_swipe_recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        pull_swipe_recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeMenuRecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuRecyclerView> refreshView) {
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
                                    DataModel.initData(list, "SwipeRecyclerView");
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(PullSwipeRecyclerViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                    pull_swipe_recyclerView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuRecyclerView> refreshView) {
                // 上拉加载监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(MainActivity.SLEEP_TIME);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DataModel.initData(list, "SwipeRecyclerView");
                                    adapter.notifyDataSetChanged();
                                    pull_swipe_recyclerView.onRefreshComplete();
                                    Toast.makeText(PullSwipeRecyclerViewActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        swipe_recyclerView = pull_swipe_recyclerView.getRefreshableView();

        swipe_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipe_recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        swipe_recyclerView.setSwipeMenuCreator(creator);

        adapter = new SwipeRecyclerViewAdapter(list);
        swipe_recyclerView.setAdapter(adapter);
    }

    /**
     * 右滑菜单点击事件
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         */
        @Override
        public void onItemClick(Openable closeable, int adapterPosition, int menuPosition) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            Toast.makeText(PullSwipeRecyclerViewActivity.this, "adapter Position : " + adapterPosition + " , menuposition : " + menuPosition, Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * 创建右滑菜单
     */
    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeRightMenu, int viewType) {
            String str = "";
            int color = 0;
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int widthdel = getResources().getDimensionPixelSize(R.dimen.dp_70);
            // viewType 的值来自于 Adapter 中的getItemViewType 方法；
//            switch (viewType) {
//                case 0:
//                    width = getResources().getDimensionPixelSize(R.dimen.dp_70);
//                    str = "置顶";
//                    color = R.color.c_2ecc71;
//                    widthdel = getResources().getDimensionPixelSize(R.dimen.dp_70);
//                    break;
//                case 1:
//                    width = getResources().getDimensionPixelSize(R.dimen.dp_100);
//                    widthdel = getResources().getDimensionPixelSize(R.dimen.dp_70);
//                    str = "取消置顶";
                    color = R.color.c_ff7a7a;
//                    break;
//                case 3:
//                    width = 0;
//                    widthdel = 0;
//                    break;
//            }
            str = "置顶";
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            SwipeMenuItem deleteItem = new SwipeMenuItem(PullSwipeRecyclerViewActivity.this)
                    .setBackgroundDrawable(color)
                    .setText(str) // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setTextSize(16)
                    .setWidth(width)
                    .setHeight(getResources().getDimensionPixelSize(R.dimen.dp_70));
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            SwipeMenuItem addItem = new SwipeMenuItem(PullSwipeRecyclerViewActivity.this)
                    .setBackgroundDrawable(R.color.color_99)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setTextSize(16)
                    .setWidth(widthdel)
                    .setHeight(getResources().getDimensionPixelSize(R.dimen.dp_70));
            swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

        }
    };

    class SwipeRecyclerViewAdapter extends SwipeMenuAdapter<SwipeRecyclerViewAdapter.ViewHolder> {

        private List<String> list;

        public SwipeRecyclerViewAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new ViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
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
