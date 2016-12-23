package com.dchuvasov.searchgithub.screen.search;

import com.dchuvasov.searchgithub.api.dto.Repository;
import com.dchuvasov.searchgithub.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 22.12.2016.
 */

class RepositoryModel {
    private static final String TAG = RepositoryModel.class.getSimpleName();
    private static final int DEFAULT_LIMIT = 50;
    private static final int FIRST_PAGE = 1;
    private int currentPage;
    private List<Repository> mItems = new ArrayList<>();
    private boolean isLoading;
    private String query;
    private int limit;
    private int totalCount;

    public RepositoryModel() {
        currentPage = FIRST_PAGE;
        limit = DEFAULT_LIMIT;
        isLoading = false;
    }

    void addRepositories(List<Repository> repositories) {
        mItems.addAll(repositories);
        currentPage++;
    }

    List<Repository> getItems() {
        return mItems;
    }

    void startLoading() {
        isLoading = true;
    }

    void stopLoading() {
        isLoading = false;
    }

    boolean isLoading() {
        return isLoading;
    }

    int getCurrentPage() {
        return currentPage;
    }

    public String getQuery() {
        return query;
    }

    void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setQuery(String query) {
        L.d(TAG, "setQuery() called with: query = [" + query + "]");
        this.query = query;
        this.currentPage = FIRST_PAGE;
        mItems.clear();
    }

    int getLimit() {
        return limit;
    }

    boolean canLoadMore() {
        return totalCount > currentPage * limit;
    }


}
