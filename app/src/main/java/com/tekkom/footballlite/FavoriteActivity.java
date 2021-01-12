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


public class FavoriteActivity extends AppCompatActivity {
    private FavDB favDB;
    private Team team = new Team();
    private Favorite favorite = new Favorite();
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


        favorite = getIntent().getParcelableExtra("detail");

        if (favorite != null) {
            idTeam = favorite.getIdTeam();
            strTeam = favorite.getStrTeam();
            strStadium = favorite.getStrStadium();
            strDescriptionEN = favorite.getStrDescriptionEN();
            strBadgeTeam = favorite.getStrTeamBadge();

            tv_name.setText(strTeam);
            tv_stadium.setText(strStadium);
            tv_desc.setText(strDescriptionEN);
            Glide.with(getApplicationContext())
                    .load(favorite.getStrTeamBadge())
                    .apply(new RequestOptions())
                    .into(img_photo);

            for (int i = 0; i < favList.size(); i++) {
                if (favList.get(i).getStrTeam().equals(favList.get(i).getStrTeam())) {
                    btn_fav.setFavorite(true);
                }
            }
        }


        btn_fav.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        idTeam = FavoriteActivity.this.favorite.getIdTeam();
                        strTeam = FavoriteActivity.this.favorite.getStrTeam();
                        strStadium = FavoriteActivity.this.favorite.getStrStadium();
                        strDescriptionEN = FavoriteActivity.this.favorite.getStrDescriptionEN();
                        strBadgeTeam = FavoriteActivity.this.favorite.getStrTeamBadge();

                        favDB.insertIntoTheDatabase(idTeam, strTeam, strStadium, strDescriptionEN, strBadgeTeam);
                        Toast.makeText(FavoriteActivity.this, "Added To Favorite", Toast.LENGTH_SHORT).show();

                    } else {
                        favDB.deleteFavorite(idTeam);
                        Toast.makeText(FavoriteActivity.this, "Removed From Favorite", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}