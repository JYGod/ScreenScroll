package com.edu.hrbeu.screenscroll;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {

    private ArrayList<View> viewList;

    public MyAdapter(ArrayList<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (viewList != null) {
            //设置轮播最大值，等于无限循环
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    /**
     * 判断对象是否生成界面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化position位置的界面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //当前要显示的数据索引为集合长度
        int newPosition = position % viewList.size();
        container.addView(viewList.get(newPosition));
        return viewList.get(newPosition);

//        container.addView(viewList.get(position));
//        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //移除的索引为集合的长度
        int newPosition = position % viewList.size();
        container.removeView(viewList.get(newPosition));

//        container.removeView(viewList.get(position));
    }
}
