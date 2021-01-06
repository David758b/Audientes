package com.example.AudientesAPP.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.Data.SoundDB;
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

        SoundDB SoundDatabase = new SoundDB(this);
        SQLiteDatabase db = SoundDatabase.getWritableDatabase();

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


        //Creating the Audientes directory in the external storage system under "music"
        File directory = makeDirectory();

        //Saving a file in the audientes directory with a given file name
        fileSaving(R.raw.cricket, directory, "Cricket");

        //Test size for a specific file in bytes
        System.out.println(getFile(directory,"Cricket").length());



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

        String datatest = getFilesDir().getAbsolutePath();
        File[] dataarray = this.getFilesDir().listFiles();

        System.out.println("------------------------------------------------------");
        System.out.println(this.getFilesDir().getAbsolutePath());
        for (File a: dataarray) {
            System.out.println(a.toString());
        }
        System.out.println("-------------------------------------------------------");



        //-------------------------------------------------------------
        //database
        /*
        Dette er android måden at indsætte en række i en tabel.
        Dette gøres ved at oprette et ContentValues objekt der er et
        nøgle-værdipar hashmap.
         */
        ContentValues row = new ContentValues();
        //hvis man angiver et id som allerede eksisterer så kommer dataen ikke med
        // Det jo fordi der kun kan være 1 unikt id (primary key)
        //Hvis man ikke angiver id, så bliver der bare genereret et nyt hver gang man sætter data ind
        //Den tæller bare op (1, 2 ...) og tildeler disse id'er.

        //Det kan være en fordel eller ulempe, hvilket man selv lige må vurdere.
        // dette eksempel angiver ikke id, for at vise at der oprettes ny data hver gang vi kører
        //aktiviteten og at denne data huskes, selv når appen lukkes

        //row.put(KundeDatabase.ID, 1);
        //row.put(SoundDatabase.SOUND_NAME, "Karsten");
        //row.put(KundeDatabase.POINT, 5);

        //indsætter dette objekt ind i tabellen
        //db.insert(KundeDatabase.TABEL1, null, row);


        //ved søgning i databasen er det en god ide at opdele ens query
        // i stedet for at kører standard SQL
        //String[] kolonner = {KundeDatabase.ID, KundeDatabase.NAVN, KundeDatabase.POINT};
        //String[] kolonner = {KundeDatabase.ID, KundeDatabase.NAVN, KundeDatabase.POINT};
        // String valg = KundeDatabase.POINT + "> 3"; //Where clause

        //Cursor cursor = db.query("kunder", kolonner,null, null,
        //        null, null, null);

        // Cursor cursor = db.rawQuery("SELECT * from kunder", null);

        //hente data fra cursoren
        /*while(cursor.moveToNext()){

            //gemmer data for hver iteration...overskrives hver gang
            int id = cursor.getInt(0);
            String navn = cursor.getString(1);
            int point = cursor.getInt(2);

            //bygger textviewet op, ved at tillægge resultatet af hver iteration til dette
            data.append(id + " " + navn + " " + point +"\n");
        }*/

        //cursor.close();

        //Man behøves ikke at lukke databasen da SQLiteOpenHelper gør det for en.
        // db.close();


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

    public LydAfspiller getLydAfspiller() {
        return lydAfspiller;
    }


}


