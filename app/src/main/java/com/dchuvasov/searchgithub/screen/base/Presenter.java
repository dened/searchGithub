package com.dchuvasov.searchgithub.screen.base;

import android.support.annotation.UiThread;

/**
 * Created by Denis on 22.12.2016.
 */

public interface Presenter<V extends View> {
    @UiThread
    void attachView(V view);

    @UiThread
    void detachView();
}

