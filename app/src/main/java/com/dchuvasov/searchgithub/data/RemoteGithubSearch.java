package com.dchuvasov.searchgithub.data;

import android.support.annotation.Nullable;

import com.dchuvasov.searchgithub.api.core.GithubService;
import com.dchuvasov.searchgithub.api.core.ServiceCreatorUtil;
import com.dchuvasov.searchgithub.api.response.SearchResponse;

import rx.Observable;

/**
 * Created by Denis on 23.12.2016.
 */

public class RemoteGithubSearch implements GithubSearch {
    @Nullable
    private static RemoteGithubSearch INSTANCE = null;
    private final GithubService service;

    private RemoteGithubSearch() {
        service = ServiceCreatorUtil.createService(GithubService.class);
    }

    public static RemoteGithubSearch getInstance() {
        if(INSTANCE == null)
            INSTANCE = new RemoteGithubSearch();

        return INSTANCE;
    }


    @Override
    public Observable<SearchResponse> search(String query, int page, int perPage) {
        return service.search(query, page, perPage);
    }
}
