package com.example.AudientesAPP.model.context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.funktionalitet.LibrarySoundLogic;
import com.example.AudientesAPP.model.funktionalitet.SoundSaver;
import com.example.AudientesAPP.model.funktionalitet.Utilities;

public class ModelViewController {
    private MainActivity main;
    private SharedPreferences prefs;
    private LibrarySoundLogic librarySoundLogic;
    private Utilities utils;
    private SoundSaver soundSaver;
    //private CategoryDAO categoryDAO;

    public ModelViewController(MainActivity main) {
        this.main = main;
        prefs = PreferenceManager.getDefaultSharedPreferences(main);
        librarySoundLogic = new LibrarySoundLogic(this);
        utils = new Utilities();
        soundSaver = new SoundSaver(this);
//        categoryDAO = new CategoryDAO(this);

        //TESTING
        //categoryDAO.add("te");
       /*
        Cursor cursor = main.getDB().query(SoundDB.TABEL_Category,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String navn = cursor.getString(0);
            System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
            System.out.println(navn);

        }
        cursor.close();

        */
    }

    public SoundSaver getSoundSaver() {
        return soundSaver;
    }

    public Utilities getUtils() {
        return utils;
    }

    public SharedPreferences getPrefs () {
        return prefs;
    }

    public LibrarySoundLogic getLibrarySoundLogic() {
        return librarySoundLogic;
    }

    public MainActivity getActivity(){
        return main;
    }
}
