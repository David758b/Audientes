package com.example.AudientesAPP.model.Data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.context.Context;

import java.util.List;

public class CategoryDAO implements ICategoryDAO{
    private Context context;
    ContentValues row;
    SQLiteDatabase db;
    String categoryName;

    public CategoryDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();

    }

    @Override
    public void add(Object object) {
        // TODO get variable form the DTO object when it is done
        categoryName = "Kirsten";
        row = new ContentValues();

        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_Category,null,row);


    }

    @Override
    public void delete(Object object) {
        categoryName = "Karsten";
        db.delete(SoundDB.TABEL_Category,SoundDB.CATEGORY_NAME + " = '" + categoryName + "'",null);
    }

    @Override
    public List<Object> getList() {

        return null;
    }
}
