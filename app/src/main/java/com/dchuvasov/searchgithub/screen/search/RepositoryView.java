package com.dchuvasov.searchgithub.screen.search;

import com.dchuvasov.searchgithub.api.dto.Repository;
import com.dchuvasov.searchgithub.screen.base.LEView;
import com.dchuvasov.searchgithub.screen.base.View;

import java.util.List;

/**
 * Created by Denis on 22.12.2016.
 */

public interface RepositoryView extends LEView {
    void addSearchResult(List<Repository> repositories);
    void reset();
    void showNotFound();
}
