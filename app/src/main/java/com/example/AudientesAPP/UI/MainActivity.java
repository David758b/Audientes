package com.example.AudientesAPP.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.data.DownloadSoundFiles;
import com.example.AudientesAPP.data.DAO.CategoryDAO;
import com.example.AudientesAPP.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.data.DAO.SoundDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.sentry.Sentry;

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, DownloadSoundFiles.OnDownloadSoundFilesListener{
    private ModelViewController modelViewController;
    private NavController navController;
    private Executor bgThread;
    private Handler uiThread;
    private DownloadSoundFiles dlLogic;
    private ProgressDialog dialog;
    private int counter = 0;
    private List<String> newFileNames = new ArrayList<>();
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dette bruges til at få en nedbrudsrapport
        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        if (!EMULATOR) {
            Sentry.init(options -> {options.setDsn("https://f1a313db1d68416a9f839a67ee979515@o506917.ingest.sentry.io/5597257");});
        }




        bgThread = Executors.newSingleThreadExecutor();
        uiThread = new Handler(Looper.getMainLooper());

        //Instansierer dialog
        dialog = new ProgressDialog(MainActivity.this);

        //Creates a context and gives this for later use in the database
        //Denne skal kun bruge context
        modelViewController = new ModelViewController(this);
        navController = modelViewController.getNavController();

        //gets the preference manager
        prefs = modelViewController.getPrefs();

        //laver en instans af bundmenuen og gør den synlig
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        //Sætter en listener, så man kan håndterer hvad der skal ske, når der trykkes på et
        //menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        dlLogic = modelViewController.getDlSoundFiles();
        dlLogic.addDLSoundFilesListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.playBar, new PlayBar_Frag()).addToBackStack(null).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            saveSounds();
            System.out.println("Amount of new files: " + newFileNames.size());
            if (newFileNames.size() != 0) {
                showProgressDialog(findViewById(R.id.libraryMain));
            }
        }
    }


    //Her implementeres listeneren, hvor vi skal definerer hvad der sker når der trkker på
    // et menu item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //eks. hvis der trykkes på Library item, skal navcontrolleren navigerer til fragmentet
        //libraryMain
        if (menuItem.getItemId() == R.id.library_nav) {
            navController.navigate(R.id.libraryMain);
        } else if (menuItem.getItemId() == R.id.preset_nav) {
            navController.navigate(R.id.presetMain);
        } else if (menuItem.getItemId() == R.id.hearingTest_nav) {
            navController.navigate(R.id.hearingtestMain);
        }
        return true;
    }

    public ModelViewController getModelViewController() {
        return modelViewController;
    }

    public void showProgressDialog(View view) {

        dialog.setMax(newFileNames.size());
        dialog.setTitle("Downloading sound files..");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
    }

    @Override
    public void updateDownloadSoundFiles(DownloadSoundFiles dlSoundFiles) {
        counter++;
        if (counter != dialog.getMax()) {
            System.out.println("Files downloaded \"counter\": " + counter);
            dialog.setProgress(counter);
        } else {
            System.out.println("Downloading finished. Dismissing dialog...");
            dialog.dismiss();
            prefs.edit().putBoolean("firstrun", false).commit();
            navController.navigate(R.id.libraryMain);
            // Reloads the activity, so the final lists in the logic-classes have the right sounds from the cloud
            finish();
            startActivity(getIntent());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public void saveSounds() {


        List<String> filePath = new ArrayList<>();
        //adding all the files to a list so we can comepare the length with the length of our database to see when everything is dowlnoaded
        newFileNames.add("water_dripping_in_cave");
        filePath.add("soundfiles/177958__sclolex__water-dripping-in-cave.wav");
        newFileNames.add("Cricket");
        filePath.add("soundfiles/337435__ev-dawg__cricket.wav");
        newFileNames.add("Bees buzzing");
        filePath.add("soundfiles/41497941_bees-buzzing-01.mp3");
        newFileNames.add("Crows crawing");
        filePath.add("soundfiles/41498003_crow-cawing-05.mp3");
        newFileNames.add("Chihuaha growl");
        filePath.add("soundfiles/smartsound_ANIMAL_DOG_Chihuahua_Growl_Heavy_01.mp3");
        newFileNames.add("Waterfall");
        filePath.add("soundfiles/365915__inspectorj__waterfall-small-a.wav");

        for (int i = 0; i < newFileNames.size(); i++) {
            modelViewController.getDlSoundFiles().downloadSoundFiles(newFileNames.get(i), filePath.get(i));
        }
    }
}


