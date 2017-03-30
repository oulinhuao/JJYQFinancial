package com.proj.base;

import android.view.View;
import android.view.ViewGroup;


/**
 * @Description: 封装ListAdapter
 */
public abstract class BaseListAdapter<T, Holder
        extends BaseListAdapter.ViewHolder> extends IListAdapter<T> {

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = onCreateViewHolder(parent, position);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public static class ViewHolder {
        View itemView;

        public View findViewByID(int id) {
            return itemView.findViewById(id);
        }

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   父布�??
     * @param position 当前位置
     * @return Viewholder
     */
    protected abstract Holder onCreateViewHolder(ViewGroup parent, int position);

    /**
     * 绑定数据
     *
     * @param viewHolder 当前viewHolder
     * @param position   当前位置
     */
    protected abstract void onBindViewHolder(Holder viewHolder, int position);
}
