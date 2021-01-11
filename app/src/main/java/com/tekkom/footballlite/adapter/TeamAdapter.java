package com.tekkom.footballlite.adapter;

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
import com.tekkom.footballlite.R;
import com.tekkom.footballlite.data.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private Team team;
    private Context context;

    private List<Team> teams = new ArrayList<>();

    public void setData(List<Team> teams) {
        this.context = context;
        this.teams.clear();
        this.teams = teams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    private void createTableOnFirstStart() {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }


    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        holder.bind(teams.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("detail", teams.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return teams.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_photo;
        private TextView tv_name, tv_id, tv_stadium, tv_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_stadium = itemView.findViewById(R.id.tv_stadium);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }

        public void bind(Team team) {
            tv_name.setText(team.getStrTeam());
            Glide.with(itemView.getContext())
                    .load(team.getStrTeamBadge())
                    .apply(new RequestOptions())
                    .into(img_photo);
        }
    }
}
