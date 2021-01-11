package com.tekkom.footballlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tekkom.footballlite.data.Team;
import com.tekkom.footballlite.data.Teams;
import com.tekkom.footballlite.network.ApiClient;
import com.tekkom.footballlite.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;


public class DetailActivity extends AppCompatActivity {
    private Team team = new Team();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tv_id = findViewById(R.id.tv_id);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_stadium = findViewById(R.id.tv_stadium);
        TextView tv_desc = findViewById(R.id.tv_desc);
        ImageView img_photo = findViewById(R.id.img_photo);

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
                        tv_id.setText(team.getIdTeam());
                        tv_name.setText(team.getStrTeam());
                        tv_stadium.setText(team.getStrStadium());
                        tv_desc.setText(team.getStrDescriptionEN());
                        Glide.with(getApplicationContext())
                                .load(team.getStrTeamBadge())
                                .apply(new RequestOptions())
                                .into(img_photo);
                    }
                }
            }

            @Override
            public void onFailure(Call<Teams> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}