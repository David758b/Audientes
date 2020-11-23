package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.AudientesAPP.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibraryMain extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private MainActivity mainActivity;
    private NavController navListController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_main, container, false);

        // da vi er i en navhost i en navhost, skal vi tage fat i den nedeste (barnets) fragmentmanager
        // og tage fat i den nederste navhost gennem denne
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.List_Navhost);

        navListController = navHostFragment.getNavController();


        //todo dette er mega hardcoded... sæt det ind i en ressurcefil istedet og referer den
        String[] lande = {"Presets", "Sleep", "Nature", "Ocean", "Music"};

        BottomNavigationView tabNavigationBar = rod.findViewById(R.id.tabMenu);
        tabNavigationBar.setVisibility(View.VISIBLE);

        tabNavigationBar.setOnNavigationItemSelectedListener(this);
        //mainActivity = (MainActivity) getActivity();
        mainActivity = (MainActivity) navHostFragment.getParentFragment().getActivity();


        return rod;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.Category_tab){

            navListController.navigate(R.id.libraryListCategory);

        }
        else if(menuItem.getItemId()== R.id.Sounds_tab){

            navListController.navigate(R.id.libraryListSound);

        }

        return false;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
