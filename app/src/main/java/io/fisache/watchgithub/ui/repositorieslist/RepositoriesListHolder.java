package io.fisache.watchgithub.ui.repositorieslist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.model.Repository;

public class RepositoriesListHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tvRepoName)
    TextView tvRepoName;
    @Bind(R.id.tvRepoUrl)
    TextView tvRepoUrl;
    @Bind(R.id.tvForked)
    TextView tvForked;

    public RepositoriesListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Repository repository) {
        tvRepoName.setText(repository.name);
        tvRepoUrl.setText(repository.url);
        if(repository.fork) {
            tvForked.setText("Forked");
        } else {
            tvForked.setText("Not Forked");
        }
    }
}
