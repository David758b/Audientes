package com.example.AudientesAPP.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.DTO.PresetCategoriesDTO;
import com.example.AudientesAPP.DTO.PresetDTO;
import com.example.AudientesAPP.DTO.PresetElementDTO;
import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.data.ExternalStorage;
import com.example.AudientesAPP.model.data.SoundDB;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.PresetDAO;
import com.example.AudientesAPP.model.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    private NavController navController;
    MediaPlayer mediaPlayer;
    LydAfspiller lydAfspiller;
    private SQLiteDatabase db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundDB SoundDatabase = new SoundDB(this);
        db = SoundDatabase.getWritableDatabase();

        //Creates a context and gives this for later use in the database
        context = new Context(this);

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

        getSupportFragmentManager().beginTransaction().add(R.id.playBar, new PlayBar_Frag()).addToBackStack(null).commit();
        lydAfspiller = new LydAfspiller(mediaPlayer, this);


        //Creating the Audientes directory in the external storage system under "music"
        File directory = makeDirectory();

        //Saving a file in the audientes directory with a given file name
        fileSaving(R.raw.cricket, directory, "Cricket");

        //Test size for a specific file in bytes
        System.out.println(getFile(directory,"Cricket").length());


        //outputs the table names in the cmd promt
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println("Table Name=> "+c.getString(0));
                //Toast.makeText(this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                c.moveToNext();
            }
        }
        c.close();


        saveSound();
        //test ----det virker sgu !!!
        try {
            MediaPlayer mPlayer = new MediaPlayer();
            Uri myUri =
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(getApplicationContext(), myUri);
            mPlayer.prepare();
            mPlayer.start();

        }catch (Exception e){
            e.printStackTrace();
        }



        /* This can be run to initialize the database with random test data, and output something
        fillDBUp();
        databaseTest();
         */


    }

    //Fill db with test data
    public void fillDBUp(){
        CategoryDAO categoryDAO = new CategoryDAO(context);
        CategoryDTO newCategory = new CategoryDTO("I Like to move it");
        categoryDAO.add(newCategory);

        PresetDAO presetDAO = new PresetDAO(context);
        PresetDTO presetDTO = new PresetDTO("Sove tid");
        presetDAO.add(presetDTO);

        SoundDAO soundDAO = new SoundDAO(context);
        SoundDTO soundDTO = new SoundDTO("city noise", "sauce", 420);
        soundDAO.add(soundDTO);

        SoundCategoriesDAO soundCategoriesDAO = new SoundCategoriesDAO(context);
        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO("city noise","I Like to move it");
        soundCategoriesDAO.add(soundCategoriesDTO);

        PresetCategoriesDAO presetCategoriesDAO = new PresetCategoriesDAO(context);
        PresetCategoriesDTO presetCategoriesDTO = new PresetCategoriesDTO("Sove tid","I Like to move it");
        presetCategoriesDAO.add(presetCategoriesDTO);

        PresetElementDAO presetElementDAO = new PresetElementDAO(context);
        PresetElementDTO newPresetElement = new PresetElementDTO("Sove tid","city noise",15);
        presetElementDAO.add(newPresetElement);

    }

    //Testing of the database may delete later
    public void databaseTest(){
        List list;
        PresetElementDAO presetElementDAO = new PresetElementDAO(context);
        list = presetElementDAO.getList();
        PresetElementDTO DTO;
        for (Object a: list) {
            DTO = (PresetElementDTO) a;
            System.out.println("CATEGORY DTO OUTPUT --------------");
            System.out.println(DTO.getPresetName());
            System.out.println(DTO.getSoundName());
            System.out.println(DTO.getSoundVolume());
        }


    }



    public File makeDirectory(){
        File directory = new File(this.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"Audientes");
        directory.mkdir();
        return directory;
    }

    //TODO change RID to new path where the files are
    public void fileSaving(int RID, File directory, String newFileName){

        String[] existingFiles = directory.list();

        //returns if a file is allready existing
        for (String fileName: existingFiles) {
            System.out.println("Files in Audientes dir:   " + fileName);
            if (fileName.equals(newFileName)){
                return;
            }
        }

        //Creates the new file
        File newFile = new File(directory.getAbsolutePath(), newFileName);

        try {
            InputStream in = getResources().openRawResource(RID);
            FileOutputStream out = new FileOutputStream(newFile.getAbsolutePath());
            byte[] buff = new byte[1024];
            int read = 0;

            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
                System.out.println("DET VIRKER!!!!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public File getFile(File directory, String fileName){
        File[] files = directory.listFiles();
        for (File file: files) {
            if(file.getName().equals(fileName)){
                return file;
            }
        }
        return null;
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

    public void saveSound(){
        SoundDTO soundDTO = new SoundDTO("brown_noise", "", 22);
        SoundDAO soundDAO = new SoundDAO(context);
        ExternalStorage externalStorage = new ExternalStorage(context);
        File dir = externalStorage.makeDirectory();
        externalStorage.fileSaving(R.raw.brown_noise, dir, soundDTO.getSoundName());
        File soundSrc = externalStorage.getFile(dir, soundDTO.getSoundName());
        Uri uriSoundSrc = Uri.parse(soundSrc.getAbsolutePath());
        soundDTO.setSoundSrc(uriSoundSrc.toString());
        soundDAO.add(soundDTO);
    }

    public LydAfspiller getLydAfspiller() {
        return lydAfspiller;
    }

    public NavController getNavController(){
        return navController;
    }
    //We use this to get the database in the DAO's
    public SQLiteDatabase getDB(){
        return db;
    }

    public Context getContext() {
        return context;
    }



}


