package com.dchuvasov.searchgithub.screen.base;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * Created by Denis on 22.12.2016.
 */

public class BasePresenter<V extends View> implements Presenter<V> {
    private WeakReference<V> viewRef;

    @UiThread
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if(viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }
}
