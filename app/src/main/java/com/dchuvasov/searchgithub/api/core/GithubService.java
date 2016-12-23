package com.dchuvasov.searchgithub.api.core;

import com.dchuvasov.searchgithub.api.response.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by denis_chuvasov on 19.12.16.
 */

public interface GithubService {

    @GET("/search/repositories")
    Observable<SearchResponse> search(@Query("q") String query, @Query("page") int page, @Query("per_page") int perPage);

}
