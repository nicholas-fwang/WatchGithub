package io.fisache.watchgithub.ui.userslist;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.model.User;

public class UsersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int NORMAL = 0;
    private final int POPULAR = 1;
    private final int ORGANIZATION = 2;

    private UsersListActivity usersListActivity;

    private Resources resources;

    private final List<User> users = new ArrayList<>();

    public UsersListAdapter(UsersListActivity usersListActivity) {
        this.usersListActivity = usersListActivity;
        resources = usersListActivity.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        switch (viewType) {
            case NORMAL :
                break;
            case POPULAR :
                itemView.setBackgroundColor(resources.getColor(R.color.colorOrange));
                break;
            case ORGANIZATION :
                itemView.setBackgroundColor(resources.getColor(R.color.colorGreen));
                break;
        }
        return new UsersListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((UsersListHolder)holder).bind(users.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserItemClicked(position);
            }
        });
        ((UsersListHolder) holder).ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserSettingClicked(position);
            }
        });
        Picasso.with(usersListActivity).load(users.get(position).avatar_url).into(((UsersListHolder) holder).ivAvatar);
    }

    private void onUserItemClicked(int adapterPosition) {
        usersListActivity.onUserItemClicked(users.get(adapterPosition));
    }

    private void onUserSettingClicked(int adapterPosition) {
        usersListActivity.onUserSettingClicked(users.get(adapterPosition));
    }

    @Override
    public int getItemViewType(int position) {
        if(users.get(position).type.equals("Organization")) {
            return ORGANIZATION;
        } else if(users.get(position).followers >= resources.getInteger(R.integer.user_popular_follower)) {
            return POPULAR;
        } else {
            return NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsersList(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }


}
