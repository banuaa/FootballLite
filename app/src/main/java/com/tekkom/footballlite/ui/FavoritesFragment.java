package com.tekkom.footballlite.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tekkom.footballlite.FavDB;
import com.tekkom.footballlite.R;
import com.tekkom.footballlite.adapter.FavoriteAdapter;
import com.tekkom.footballlite.adapter.TeamAdapter;
import com.tekkom.footballlite.data.Favorite;
import com.tekkom.footballlite.data.Teams;
import com.tekkom.footballlite.network.ApiClient;
import com.tekkom.footballlite.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {
    private Activity activity;
    private RecyclerView recyclerView;
    private ArrayList<Favorite> favoriteArrayList;
    private FavoriteAdapter adapter;
    FavDB favDB;

    public FavoritesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvTeamsFav);
        adapter = new FavoriteAdapter(getActivity());
        favDB = new FavDB(getContext());
        favoriteArrayList = favDB.getFavorite();
        adapter.setFavoriteArrayList(favoriteArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }
}