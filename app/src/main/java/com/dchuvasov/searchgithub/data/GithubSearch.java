package com.dchuvasov.searchgithub.data;

import com.dchuvasov.searchgithub.api.response.SearchResponse;

import rx.Observable;

/**
 * Created by Denis on 23.12.2016.
 */

public interface GithubSearch {
    Observable<SearchResponse> search(String query, int page, int perPage);
}
