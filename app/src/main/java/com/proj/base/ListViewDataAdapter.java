package com.proj.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: Adapter
 */

public class ListViewDataAdapter<T, Holder extends ViewHolderBase<T>> extends IListAdapter<T> {

    ViewHolderCreate<T, Holder> holderCreate;

    public ListViewDataAdapter(ViewHolderCreate<T, Holder> holderCreate) {
        this.holderCreate = holderCreate;
    }


    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = holderCreate.createHolder();
            convertView = holder.createView(LayoutInflater.from(parent.getContext()), parent);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.showData(position, getItem(position));
        return convertView;
    }


}
