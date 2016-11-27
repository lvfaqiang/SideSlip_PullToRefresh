package com.lfq.test.util;

import android.util.SparseArray;
import android.view.View;

/**
 * --------------------------------------------
 * 作 者 :  lfq
 * 描 述 ：
 * 简化在主程序中的代码
 * 示例:TextView shopName=ViewHolder.get(contentView, R.id.xxx);
 * -------------------------------------------
 */
public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
