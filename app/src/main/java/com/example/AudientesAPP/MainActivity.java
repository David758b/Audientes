package com.example.AudientesAPP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    private NavController navController;
    MediaPlayer test;
    LydSpiller lydSpiller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigations komponenten indeholder en default implementation af Navhost (NavHostFragment)
        //der viser destinationer af typen fragmenter
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.navHost);

        //Navcontrolleren er et objekt der styrer navigatioen inde i Navhosten. dvs den står for
        //koordinationen af at skifte mellem forskellige destinationer
        navController = navHostFragment.getNavController();


        //laver en instans af bundmenuen og gør den synlig
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        //Sætter en listener, så man kan håndterer hvad der skal ske, når der trykkes på et
        //menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        lydSpiller = new LydSpiller(test, this);

    }

    //Her implementeres listeneren, hvor vi skal definerer hvad der sker når der trkker på
    // et menu item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //eks. hvis der trykkes på Library item, skal navcontrolleren navigerer til fragmentet
        //libraryMain
        if (menuItem.getItemId() == R.id.library_nav) {
            navController.navigate(R.id.libraryMain);
        }
        else if (menuItem.getItemId() == R.id.preset_nav){
                navController.navigate(R.id.presetMain);
        }

        else if (menuItem.getItemId() == R.id.hearingTest_nav) {
            navController.navigate(R.id.hearingtestMain);
        }


        return true;
    }

    public LydSpiller getLydSpiller() {
        return lydSpiller;
    }
}


