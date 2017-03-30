package com.proj.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: 基础ViewHolder
 */
public interface ViewHolderBase<Data> {
    View createView(LayoutInflater inflater, ViewGroup parent);

    void showData(int position, Data itemData);
}
