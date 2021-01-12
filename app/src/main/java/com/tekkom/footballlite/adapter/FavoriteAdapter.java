package com.tekkom.footballlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tekkom.footballlite.DetailActivity;
import com.tekkom.footballlite.FavDB;
import com.tekkom.footballlite.FavoriteActivity;
import com.tekkom.footballlite.R;
import com.tekkom.footballlite.data.Favorite;
import com.tekkom.footballlite.data.Team;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Activity activity;
    private FavDB favDB;
    private ArrayList<Team> team = new ArrayList<>();
    private ArrayList<Favorite> favoriteArrayList = new ArrayList<>();

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
        favDB = new FavDB(activity);
    }

    public ArrayList<Favorite> getFavoriteArrayList() {
        return favoriteArrayList;
    }

    public void setFavoriteArrayList(ArrayList<Favorite> list) {
        if (list.size() > 0) {
            this.favoriteArrayList.clear();
            this.favoriteArrayList = favoriteArrayList;
        }

        this.favoriteArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favoriteArrayList.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), FavoriteActivity.class);
            intent.putExtra("detail", favoriteArrayList.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return favoriteArrayList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_photo;
        private TextView tv_name, tv_id, tv_stadium, tv_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_stadium = itemView.findViewById(R.id.tv_stadium);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }

        public void bind(Favorite favoriteArrayList) {
            tv_name.setText(favoriteArrayList.getStrTeam());
            Glide.with(itemView.getContext())
                    .load(favoriteArrayList.getStrTeamBadge())
                    .apply(new RequestOptions())
                    .into(img_photo);
        }
    }
}
