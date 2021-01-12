package com.tekkom.footballlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.tekkom.footballlite.data.Favorite;
import com.tekkom.footballlite.data.Team;
import com.tekkom.footballlite.data.Teams;
import com.tekkom.footballlite.network.ApiClient;
import com.tekkom.footballlite.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class DetailActivity extends AppCompatActivity {
    private FavDB favDB;
    private Team team = new Team();
    private ArrayList<Favorite> favList = new ArrayList<>();
    String idTeam;
    String strTeam;
    String strStadium;
    String strDescriptionEN;
    String strBadgeTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favDB = new FavDB(this);
        favList = favDB.getFavorite();

        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_stadium = findViewById(R.id.tv_stadium);
        TextView tv_desc = findViewById(R.id.tv_desc);
        ImageView img_photo = findViewById(R.id.img_photo);
        MaterialFavoriteButton btn_fav = findViewById(R.id.fav);


        team = getIntent().getParcelableExtra("detail");

        ApiInterface service = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Teams> call = service.getTeamDetail(team.getIdTeam());
        call.enqueue(new Callback<Teams>() {
            @Override
            public void onResponse(Call<Teams> call, retrofit2.Response<Teams> response) {
                if (response.isSuccessful()) {
                    Teams teams = response.body();
                    if (teams != null && teams.getTeams() != null) {
                        team = teams.getTeams().get(0);
                        tv_name.setText(team.getStrTeam());
                        tv_stadium.setText(team.getStrStadium());
                        tv_desc.setText(team.getStrDescriptionEN());
                        Glide.with(getApplicationContext())
                                .load(team.getStrTeamBadge())
                                .apply(new RequestOptions())
                                .into(img_photo);

                        for (int i = 0; i < favList.size(); i++) {
                            if (favList.get(i).getStrTeam().equals((team.getStrTeam()))) {
                                btn_fav.setBackgroundResource(R.drawable.ic_favorites);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Teams> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
        btn_fav.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        idTeam = team.getIdTeam();
                        strTeam = team.getStrTeam();
                        strStadium = team.getStrStadium();
                        strDescriptionEN = team.getStrDescriptionEN();
                        strBadgeTeam = team.getStrTeamBadge();

                        favDB.insertIntoTheDatabase(idTeam, strTeam, strStadium, strDescriptionEN, strBadgeTeam);
                        Toast.makeText(DetailActivity.this, "Added To Favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        favDB.deleteFavorite(idTeam);
                        Toast.makeText(DetailActivity.this, "Removed From Favorite", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}