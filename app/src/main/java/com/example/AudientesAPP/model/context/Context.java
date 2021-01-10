package com.example.AudientesAPP.model.context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;

public class Context {
    private MainActivity main;
    private SharedPreferences prefs;
    //private CategoryDAO categoryDAO;

    public Context(MainActivity main) {
        this.main = main;
        prefs = PreferenceManager.getDefaultSharedPreferences(main);
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

    public SharedPreferences getPrefs () {
        return prefs;
    }


    public MainActivity getActivity(){
        return main;
    }
}
