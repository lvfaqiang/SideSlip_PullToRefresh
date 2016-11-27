package com.lfq.test.util;

import java.util.ArrayList;
import java.util.List;

/**
 * --------------------------------------------
 * auther :  Lvfq
 * 2016/11/27 14:36
 * description ：
 * -------------------------------------------
 **/
public class DataModel {

    public static void initData(List list, String string) {
        if (list == null) {
            list = new ArrayList();
        }
        int count = list.size();
        for (int i = count; i < count + 20; i++) {
            list.add(string + i);
        }
    }
}
