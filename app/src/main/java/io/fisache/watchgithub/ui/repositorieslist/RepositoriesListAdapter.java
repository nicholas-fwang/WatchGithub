package io.fisache.watchgithub.ui.repositorieslist;

import android.support.annotation.BoolRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.ui.userslist.UsersListHolder;
import io.fisache.watchgithub.util.DateUtils;

public class RepositoriesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int NORMAL = 0;
    private final int POPULAR = 1;
    private final int RECENTLY = 2;

    private RepositoriesListActivity repositoriesListActivity;

    private List<Repository> repositories = new ArrayList<>();

    public RepositoriesListAdapter(RepositoriesListActivity repositoriesListActivity) {
        this.repositoriesListActivity = repositoriesListActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_repo, parent, false);

        switch (viewType) {
            case POPULAR :
                itemView.setBackgroundColor(repositoriesListActivity.getResources().getColor(R.color.colorOrange));
                break;
            case RECENTLY :
                itemView.setBackgroundColor(repositoriesListActivity.getResources().getColor(R.color.colorGreen));

        }
        return new RepositoriesListHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {

        Date pushed = DateUtils.convertStringToDate(repositories.get(position).pushed_at);
        if(DateUtils.differInDate(pushed, DateUtils.getCurrentDate()) <= 3) {
            return RECENTLY;
        } else if(repositories.get(position).star_count > 500) {
//        if(repositories.get(position).star_count > 500) {
            return POPULAR;
        } else {
            return NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RepositoriesListHolder)holder).bind(repositories.get(position));
    }

    public void updateRepositoriesList(List<Repository> repositories) {
        this.repositories.clear();
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }
}
