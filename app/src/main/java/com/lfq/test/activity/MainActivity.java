package com.lfq.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lfq.test.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_listview).setOnClickListener(this);
        findViewById(R.id.tv_recyclerView).setOnClickListener(this);
        findViewById(R.id.tv_swipeRecyclerView).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_listview:
                startActivity(PullListViewActivity.class);
                break;
            case R.id.tv_recyclerView:
                startActivity(PullRecyclerViewActivity.class);
                break;
            case R.id.tv_swipeRecyclerView:
                startActivity(PullSwipeRecyclerViewActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
