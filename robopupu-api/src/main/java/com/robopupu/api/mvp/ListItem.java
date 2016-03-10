package com.robopupu.api.mvp;

/**
 * {@link ListItem} ...
 */
public abstract class ListItem<T> {

    protected T mModelObject;
    protected int mPosition;

    protected ListItem() {
    }

    protected ListItem(final T object) {
        setModelObject(object);
    }

    public T getModelObject() {
        return mModelObject;
    }

    public void setModelObject(final T object) {
        mModelObject = object;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(final int position) {
        mPosition = position;
    }
}
