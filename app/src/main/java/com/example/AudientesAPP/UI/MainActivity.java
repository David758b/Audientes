package com.example.AudientesAPP.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    private NavController navController;
    MediaPlayer test;
    LydAfspiller lydAfspiller;

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

        getSupportFragmentManager().beginTransaction().add(R.id.playBar, new PlayBar_Frag()).addToBackStack(null).commit();

        lydAfspiller = new LydAfspiller(test, this);



        /*
        //test ----det virker sgu !!!
        try {
            MediaPlayer mPlayer = new MediaPlayer();
            Uri myUri = Uri.parse(chihua.getAbsolutePath());
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(getApplicationContext(), myUri);
            mPlayer.prepare();
            mPlayer.start();

        }catch (Exception e){
            e.printStackTrace();
        }
        */

    }


    public String makeDirectory(){
        File directory = new File(this.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"Audientes");
        directory.mkdir();
        return directory.getAbsolutePath();
    }


    //TODO change RID to new path where the files are
    public void fileSaving(int RID, String directoryPath, String newFileName){

        File newFile = new File(directoryPath, newFileName);

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

    public LydAfspiller getLydAfspiller() {
        return lydAfspiller;
    }


}


