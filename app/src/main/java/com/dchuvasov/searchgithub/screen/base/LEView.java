package com.dchuvasov.searchgithub.screen.base;

/**
 * Created by Denis on 23.12.2016.
 */

public interface LEView extends View {
    void showError(String error);
    void showLoading();
    void hideLoading();
}
