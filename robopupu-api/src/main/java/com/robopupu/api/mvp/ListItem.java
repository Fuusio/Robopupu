package com.robopupu.api.mvp;

/**
 * {@link ListItem} ...
 */
public abstract class ListItem<T> {

    protected T modelObject;
    protected int position;

    protected ListItem() {
    }

    protected ListItem(final T object) {
        setModelObject(object);
    }

    public T getModelObject() {
        return modelObject;
    }

    public void setModelObject(final T object) {
        modelObject = object;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }
}
