package com.dchuvasov.searchgithub.screen.search;

import android.text.TextUtils;
import android.util.Log;

import com.dchuvasov.searchgithub.api.core.GithubService;
import com.dchuvasov.searchgithub.api.core.ServiceCreatorUtil;
import com.dchuvasov.searchgithub.api.response.SearchResponse;
import com.dchuvasov.searchgithub.data.GithubSearch;
import com.dchuvasov.searchgithub.screen.base.BasePresenter;
import com.dchuvasov.searchgithub.utils.L;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Denis on 22.12.2016.
 */

public class RepositoryPresenter extends BasePresenter<RepositoryView> {
    private static final String TAG = RepositoryPresenter.class.getSimpleName();
    private RepositoryModel model;
    private GithubSearch githubSearch;

    RepositoryPresenter(GithubSearch githubSearch) {
        this.githubSearch = githubSearch;
        model = new RepositoryModel();
    }

    @Override
    public void attachView(RepositoryView view) {
        L.d(TAG, "attachView() called with: view = [" + view + "]");
        super.attachView(view);
        view.addSearchResult(model.getItems());
        if (model.isLoading()) {
            view.showLoading();
        } else {
            view.hideLoading();
        }
    }

    public void onSearch(String query) {
        L.d(TAG, "onSearch() called with: query = [" + query + "]");
        if (!TextUtils.isEmpty(query.trim())) {
            if (isViewAttached())
                getView().reset();

            model.setQuery(query);
            search(model);
        }
    }

    private void search(RepositoryModel model) {
        githubSearch.search(model.getQuery(), model.getCurrentPage(), model.getLimit())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    model.startLoading();
                    getView().showLoading();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    model.stopLoading();
                    if(isViewAttached())
                        getView().hideLoading();
                })
                .subscribe(this::onSearchComplete,
                        this::onSearchError);
    }

    private void onSearchError(Throwable e) {
        if (isViewAttached()) {
            getView().showError(e.toString());
        }
        model.stopLoading();
    }

    private void onSearchComplete(SearchResponse searchResponse) {
        if(searchResponse.getTotalCount() == 0) {
            if (isViewAttached()) {
                getView().showNotFound();
            }
        } else {
            if (isViewAttached()) {
                getView().addSearchResult(searchResponse.getItems());
            }

            model.addRepositories(searchResponse.getItems());
            model.setTotalCount(searchResponse.getTotalCount());
        }
    }

    public void onGetNextPage() {
        if (model.canLoadMore() && !model.isLoading()) {
            search(model);
        }
    }
}
