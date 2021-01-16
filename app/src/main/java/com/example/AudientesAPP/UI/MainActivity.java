package com.example.AudientesAPP.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ModelViewController modelViewController;
    private NavController navController;
    private Executor bgThread;
    private Handler uiThread;
    private List<String> newFileNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgThread = Executors.newSingleThreadExecutor();
        uiThread = new Handler(Looper.getMainLooper());
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


        //dialog
        showHProgressDialog(findViewById(R.id.libraryMain));



        bgThread.execute(()->{
            saveSounds();
            uiThread.post(()->{
               // setContentView(R.layout.activity_main);
                System.out.println("CHANGE ACTIVITY");
            });
        });

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




        List<String> filePath = new ArrayList<>();
        //adding all the files to a list so we can comepare the length with the length of our database to see when everything is dowlnoaded
//        newFileNames.add("cobratronik_wind");
//        filePath.add("soundfiles/117136__cobratronik__wind-artic-cold.wav");
//        newFileNames.add("cathedral_ambience_01");
//        filePath.add("soundfiles/170675__klankbeeld__cathedral-ambience-01.wav");
//        newFileNames.add("water_dripping_in_cave");
//        filePath.add("soundfiles/177958__sclolex__water-dripping-in-cave.wav");
//        newFileNames.add("downtown_calm");
//        filePath.add("soundfiles/216734__klankbeeld__down-town-calm-140124-01.wav");
//        newFileNames.add("rain_and_thunder_4");
//        filePath.add("soundfiles/237729__flathill__rain-and-thunder-4.wav");

        boolean isDownloaded = false;
        for (int i = 0; i < newFileNames.size(); i++) {
            modelViewController.getDlSoundFiles().downloadSoundFiles(newFileNames.get(i),filePath.get(i));
        }

        int totalFilesAmount = modelViewController.getSoundDAO().getList().size();

      //  while(modelViewController.getSoundDAO().getList().size() != newFileNames.size() + 8 ){
        //}


        //modelViewController.getDlSoundFiles().downloadSoundFiles(newFileNames.get(0),"soundfiles/117136__cobratronik__wind-artic-cold.wav");
        //modelViewController.getDlSoundFiles().downloadSoundFiles("cathedral_ambience_01","soundfiles/170675__klankbeeld__cathedral-ambience-01.wav");
        //modelViewController.getDlSoundFiles().downloadSoundFiles("water_dripping_in_cave","soundfiles/177958__sclolex__water-dripping-in-cave.wav");
        //modelViewController.getDlSoundFiles().downloadSoundFiles("downtown_calm","soundfiles/216734__klankbeeld__down-town-calm-140124-01.wav");
        //modelViewController.getDlSoundFiles().downloadSoundFiles("rain_and_thunder_4","soundfiles/237729__flathill__rain-and-thunder-4.wav");
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

    public void showHProgressDialog(View view)
    {


        final Timer timer = new Timer();
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        dialog.setMax(100);
        dialog.setTitle("Dialog Title");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // Update the progress bar
        Handler handler = new Handler();

        TimerTask tt = new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                dialog.setProgress(counter);

                if (counter == 100){
                    timer.cancel();
                }
            }
        };

        timer.schedule(tt,0,100);


        while(modelViewController.getSoundDAO().getList().size() != newFileNames.size() + 8 ){
            System.out.println("getList: "+modelViewController.getSoundDAO().getList().size());
            System.out.println("newFiles: "+newFileNames.size() + 8 );

            System.out.println("Inside while downloading loop");
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (Exception e){
                System.out.println(" TIMER ERROR: " + e);
            }
            System.out.println("After 10 sec loop");
        }
        //dialog.setCancelable(false);
        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();

//
//        handler.post(new Runnable() {
//            public void run() {
//
//                while(true) {
//                    try {
//                        timer.wait(500);
//                        helo++;
//                        dialog.setProgress(helo);
//                        //dialog.setProgress(modelViewController.getSoundDAO().getList().size());
//                    } catch(Exception e){
//                        System.out.println("EXCEPTION IN RUN IN THE DIALOG: " + e );
//                    }
//                }
//
//            }
  //      });


    }


}


