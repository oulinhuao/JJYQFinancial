package com.proj.base;

/**
 * @Description: 创建ViewHolder
 */
public abstract class ViewHolderCreate<T, Hold extends ViewHolderBase<T>> {

    public abstract Hold createHolder();

}
