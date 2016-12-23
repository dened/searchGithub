package com.dchuvasov.searchgithub.screen.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dchuvasov.searchgithub.Injection;

/**
 * Created by Denis on 22.12.2016.
 */

public class RepositoryPresenterStorageFragment extends Fragment {
    private RepositoryPresenter presenter;

    public static RepositoryPresenterStorageFragment newInstance() {
        return new RepositoryPresenterStorageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public @NonNull RepositoryPresenter getRepositoryPresenter() {
        if(presenter == null)
            presenter = new RepositoryPresenter(Injection.provideGithubSearch());

        return presenter;
    }
}
