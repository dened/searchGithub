package com.dchuvasov.searchgithub.screen.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dchuvasov.searchgithub.R;
import com.dchuvasov.searchgithub.api.dto.Repository;
import com.dchuvasov.searchgithub.utils.L;
import com.dchuvasov.searchgithub.utils.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis_chuvasov on 19.12.16.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryHolder> {
    private static final String TAG = RepositoriesAdapter.class.getSimpleName();
    private List<Repository> dataItems = new ArrayList<>();
    private OnLoadMoreListener onLoadMoreListener;

    public RepositoriesAdapter(@NonNull OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    @Override
    public RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new RepositoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryHolder holder, int position) {
        holder.bind(dataItems.get(position));
        int preloadPosition = Math.max(0, dataItems.size() - 3);
        if(position >= preloadPosition)
            onLoadMoreListener.onLoadMore();
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void addRepositories(List<Repository> repositories) {
        dataItems.addAll(repositories);
        notifyDataSetChanged();
    }

    public void clear() {
        L.d(TAG, "clear() called");
        dataItems.clear();
        notifyDataSetChanged();
    }
}
