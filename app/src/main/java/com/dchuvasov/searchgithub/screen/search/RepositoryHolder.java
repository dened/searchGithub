package com.dchuvasov.searchgithub.screen.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dchuvasov.searchgithub.R;
import com.dchuvasov.searchgithub.api.dto.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by denis_chuvasov on 19.12.16.
 */

class RepositoryHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatarView)
    ImageView avatarView;

    @BindView(R.id.repositoryNameView)
    TextView repositoryNameView;

    @BindView(R.id.repositoryDescriptionView)
    TextView repositoryDescriptionView;

    RepositoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Repository repository) {
        Glide.with(itemView.getContext())
                .load(repository.avatarUrl())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(avatarView);

        repositoryDescriptionView.setText(repository.getDescription());
        repositoryNameView.setText(repository.getFullName());
    }
}
