package com.dchuvasov.searchgithub;

import com.dchuvasov.searchgithub.data.GithubSearch;
import com.dchuvasov.searchgithub.data.RemoteGithubSearch;

/**
 * Created by Denis on 23.12.2016.
 */

public class Injection {
    public static GithubSearch provideGithubSearch() {
        return RemoteGithubSearch.getInstance();
    }
}
