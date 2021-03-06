package com.tekkom.footballlite.network;

import com.tekkom.footballlite.data.Teams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("lookup_all_teams.php")
    Call<Teams> getTeams(
            @Query("id") String id
    );

    @GET("lookupteam.php")
    Call<Teams> getTeamDetail(
            @Query("id") String id
    );
}
