package com.example.AudientesAPP.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.PresetCategoriesDTO;
import com.example.AudientesAPP.model.DTO.PresetDTO;
import com.example.AudientesAPP.model.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.data.SoundDB;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.PresetDAO;
import com.example.AudientesAPP.model.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ModelViewController modelViewController;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a context and gives this for later use in the database
        //Denne skal kun bruge context
        modelViewController = new ModelViewController(this);
        navController = modelViewController.getNavController();

        //laver en instans af bundmenuen og gør den synlig
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        //Sætter en listener, så man kan håndterer hvad der skal ske, når der trykkes på et
        //menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.playBar, new PlayBar_Frag()).addToBackStack(null).commit();
        saveSounds();

        //-------------------------------Resten er test---------------------------------------------
        // printTables();
        // mediaPlayerTest(controller);

        /* This can be run to initialize the database with random test data, and output something

        databaseTest();
         */

        //fillDBUp();

    }

    //----------------------test-------------------------------------------------------
    public void saveSounds() {
        //Gemmer alle lyde i raw mappen i external storage
        modelViewController.getSoundSaver().saveSound(R.raw.andreangelo, "andreangelo");
        modelViewController.getSoundSaver().saveSound(R.raw.brown_noise, "brown_noise");
        modelViewController.getSoundSaver().saveSound(R.raw.chihuahua, "chihuahua");
        modelViewController.getSoundSaver().saveSound(R.raw.cricket, "cricket");
        modelViewController.getSoundSaver().saveSound(R.raw.melarancida__monks_praying, "melarancida__monks_praying");
        modelViewController.getSoundSaver().saveSound(R.raw.rain_street, "rain_street");
        modelViewController.getSoundSaver().saveSound(R.raw.testlyd, "testlyd");
        modelViewController.getSoundSaver().saveSound(R.raw.train_nature, "train_nature");
    }

    //Printing tables
    public void printTables() {
        //outputs the table names in the cmd promt
        Cursor c = modelViewController.getDb().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                System.out.println("Table Name=> " + c.getString(0));
                //Toast.makeText(this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                c.moveToNext();
            }
        }
        c.close();
    }

    //mediaplayer test
    public void mediaPlayerTest(ModelViewController modelViewController) {
        //test ----det virker sgu !!!
        try {
            MediaPlayer mPlayer = new MediaPlayer();
            SoundDAO soundDAO = modelViewController.getSoundDAO();
            List<SoundDTO> list = soundDAO.getList();
            Uri myUri = null;

            for (SoundDTO dto : list) {
                if (dto.getSoundName().equals("andreangelo")) {
                    myUri = Uri.parse(dto.getSoundSrc());
                }
            }
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(getApplicationContext(), myUri);
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Fill db with test data
    public void fillDBUp() {
        CategoryDAO categoryDAO = modelViewController.getCategoryDAO();
        CategoryDTO newCategory = new CategoryDTO("I Like to move it", "pictureSrc", "Blue");
        categoryDAO.add(newCategory);

        PresetDAO presetDAO = modelViewController.getPresetDAO();
        PresetDTO presetDTO = new PresetDTO("Sove tid");
        presetDAO.add(presetDTO);

        SoundDAO soundDAO = modelViewController.getSoundDAO();
        SoundDTO soundDTO = new SoundDTO("city noise", "sauce", "420");
        soundDAO.add(soundDTO);

        SoundCategoriesDAO soundCategoriesDAO = modelViewController.getSoundCategoriesDAO();
        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO("city noise", "I Like to move it");
        soundCategoriesDAO.add(soundCategoriesDTO);

        PresetCategoriesDAO presetCategoriesDAO = modelViewController.getPresetCategoriesDAO();
        PresetCategoriesDTO presetCategoriesDTO = new PresetCategoriesDTO("Sove tid", "I Like to move it");
        presetCategoriesDAO.add(presetCategoriesDTO);

        PresetElementDAO presetElementDAO = modelViewController.getPresetElementDAO();
        PresetElementDTO newPresetElement = new PresetElementDTO("Sove tid", "city noise", 15);
        presetElementDAO.add(newPresetElement);

    }

    //Testing of the database may delete later
    public void databaseTest() {
        List list;
        PresetElementDAO presetElementDAO = modelViewController.getPresetElementDAO();
        list = presetElementDAO.getList();
        PresetElementDTO DTO;
        for (Object a : list) {
            DTO = (PresetElementDTO) a;
            System.out.println("CATEGORY DTO OUTPUT --------------");
            System.out.println(DTO.getPresetName());
            System.out.println(DTO.getSoundName());
            System.out.println(DTO.getSoundVolume());
        }
    }
    //--------------------end test ----------------------------------------------------

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
}


