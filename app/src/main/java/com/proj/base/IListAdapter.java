package com.proj.base;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 封装ListAdapter
 */
public abstract class IListAdapter<T> extends BaseAdapter {
    private ArrayList<T> mList = new ArrayList<T>();


    public void addList(List<T> list) {
        if (list != null) {
            mList.addAll(list);
        }
    }

    public ArrayList<T> getList(){
        return  mList;
    }

    public void clear() {
        mList.clear();
    }

    public void add(T data) {
        if (data != null) {
            mList.add(data);
        }
    }

    public void remove(T data) {
        mList.remove(data);
    }

    public void remove(int position) {
        mList.remove(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
