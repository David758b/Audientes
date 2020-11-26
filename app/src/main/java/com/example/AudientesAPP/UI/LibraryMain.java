package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibraryMain extends Fragment implements View.OnClickListener {
    private MainActivity mainActivity;
    private NavController navListController;
    private TextView categories;
    private TextView sounds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_main, container, false);

        // da vi er i en navhost i en navhost, skal vi tage fat i den nedeste (barnets) fragmentmanager
        // og tage fat i den nederste navhost gennem denne
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.List_Navhost);

        navListController = navHostFragment.getNavController();
        categories = rod.findViewById(R.id.categories_page);
        sounds = rod.findViewById(R.id.sounds_page);
        categories.setOnClickListener(this);
        sounds.setOnClickListener(this);

        mainActivity = (MainActivity) navHostFragment.getParentFragment().getActivity();


        return rod;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    public void onClick(View v) {
        if(v == categories){

            navListController.navigate(R.id.libraryListCategory);

        }
        else if(v == sounds){

            navListController.navigate(R.id.libraryListSound);

        }
    }
}
