# SideSlip_PullToRefresh
在 PullToRefresh 的基础上整合了 RecyclerView 的刷新加载，以及带有侧滑菜单的 RecyclerView.

[ ![Download](https://api.bintray.com/packages/lvfaqiang/maven/pulltorefresh/images/download.svg) ](https://bintray.com/lvfaqiang/maven/pulltorefresh/_latestVersion)

blog : [Android RecyclerView 的刷新加载，及侧滑菜单](http://blog.csdn.net/lv_fq/article/details/53371871)

#效果图：

![image](http://img.blog.csdn.net/20161127232241473)
 


# 添加 JCenter 依赖：
### Gradle

    compile 'com.lfq:pulltorefresh:1.1'
### Maven

    <dependency>
      <groupId>com.lfq</groupId>
      <artifactId>pulltorefresh</artifactId>
      <version>1.1</version>
      <type>pom</type>
    </dependency>

#### 刷新加载模式： BOTH 下来刷新和上拉加载  ， PULL_FROM_END 上拉加载 ， PULL_FROM_START 下拉刷新 ，DISABLED ，禁止刷新和加载

## PullToRefreshRecyclerView 用法：

    pull_recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.pull_recyclerView);
    
    recyclerView = pull_recyclerView.getRefreshableView();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    // 设置刷新加载模式：
    pull_recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
    // 刷新加载监听
    pull_recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                    // 下拉刷新监听
                }
    
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                    // 上拉加载监听
                   
                }
            });
            
    pull_recyclerView.onRefreshComplete()   // 结束刷新加载状态
    
    使用 默认 Adapter ;
    
## PullToRefreshSwipeRecyclerView (侧滑菜单 RecyclerView):

       pull_swipe_recyclerView = (PullToRefreshSwipeRecyclerView) findViewById(R.id.pull_swipe_recyclerView);
       
       pull_swipe_recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
       pull_swipe_recyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeMenuRecyclerView>() {
           @Override
           public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuRecyclerView> refreshView) {
               // 下拉刷新监听
           }

           @Override
           public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuRecyclerView> refreshView) {
               // 上拉加载监听
           }
       });

       swipe_recyclerView = pull_swipe_recyclerView.getRefreshableView();

       swipe_recyclerView.setLayoutManager(new LinearLayoutManager(this));
       swipe_recyclerView.setSwipeMenuCreator(creator); // 添加右侧滑动菜单。
       swipe_recyclerView.setSwipeMenuItemClickListener(menuItemClickListener); // 设置右滑菜单点击事件；
       
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
#### PulltoRefreshSwipeRecyclerView Adapter 写法：

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

## PullToRefreshListView (PullToRefresh框架的基本用法)：

        pull_listView = (PullToRefreshListView) findViewById(R.id.pull_listView);

        adapter = new ListViewAdapter(list, this);
        pull_listView.setMode(PullToRefreshBase.Mode.BOTH); // BOTH 下来刷新和上拉加载  ， PULL_FROM_END 上拉加载 ， PULL_FROM_START 下拉刷新 ，DISABLED ，禁止刷新和加载
        pull_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新监听
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载监听
            }
        });
        
        使用 默认 Adapter 
