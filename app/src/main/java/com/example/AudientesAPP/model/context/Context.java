package com.example.AudientesAPP.model.context;

import android.database.Cursor;

import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.Data.CategoryDAO;
import com.example.AudientesAPP.model.Data.SoundDB;

public class Context {
    private MainActivity main;
    private CategoryDAO categoryDAO;

    public Context(MainActivity main) {
        this.main = main;
        categoryDAO = new CategoryDAO(this);

        //TESTING
        //categoryDAO.add("te");
        categoryDAO.delete("test");
        Cursor cursor = main.getDB().query(SoundDB.TABEL_Category,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String navn = cursor.getString(0);
            System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
            System.out.println(navn);

        }
        cursor.close();


    }





    public MainActivity getActivity(){
        return main;
    }
}
