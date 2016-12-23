package com.dchuvasov.searchgithub;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dchuvasov.searchgithub.api.dto.Repository;
import com.dchuvasov.searchgithub.screen.search.RepositoriesAdapter;
import com.dchuvasov.searchgithub.screen.search.RepositoryPresenter;
import com.dchuvasov.searchgithub.screen.search.RepositoryPresenterStorageFragment;
import com.dchuvasov.searchgithub.screen.search.RepositoryView;
import com.dchuvasov.searchgithub.utils.OnLoadMoreListener;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity implements RepositoryView, OnLoadMoreListener {
    private static final String STORAGE_TAG = "storage";

    @BindView(R.id.rootView)
    CoordinatorLayout rootCoordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    View progressBar;

    @BindView(R.id.notFoundView)
    View notFoundView;

    RepositoriesAdapter adapter;

    private RepositoryPresenter repositoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new RepositoriesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        repositoryPresenter = getPresenter();
        repositoryPresenter.attachView(this);
    }

    private RepositoryPresenter getPresenter() {
        final RepositoryPresenterStorageFragment repositoryPresenterStorageFragment =
                (RepositoryPresenterStorageFragment) getSupportFragmentManager().findFragmentByTag(STORAGE_TAG);

        if (repositoryPresenterStorageFragment != null) {
            return repositoryPresenterStorageFragment.getRepositoryPresenter();
        } else {
            final RepositoryPresenterStorageFragment fragment = RepositoryPresenterStorageFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(fragment, STORAGE_TAG)
                    .commit();

            return fragment.getRepositoryPresenter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        RxSearchView.queryTextChanges(searchView)
                .debounce(charSequence -> {
                    if(charSequence.length() == 0)
                        return Observable.empty();

                    return Observable.<CharSequence>empty().delay(1, TimeUnit.SECONDS);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> repositoryPresenter.onSearch(charSequence.toString()));
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoryPresenter.detachView();
    }

    @Override
    public void addSearchResult(List<Repository> repositories) {
        notFoundView.setVisibility(View.GONE);
        adapter.addRepositories(repositories);
    }

    @Override
    public void reset() {
        adapter.clear();
    }

    @Override
    public void showNotFound() {
        notFoundView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(rootCoordinatorLayout, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore() {
        repositoryPresenter.onGetNextPage();
    }
}
