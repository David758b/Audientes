package com.example.AudientesAPP.model.Data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.Data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.model.Data.SoundDB;
import com.example.AudientesAPP.model.context.Context;

import java.util.ArrayList;
import java.util.List;

public class SoundCategoriesDAO implements IDAO {
    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String soundName;
    private String categoryName;
    private SoundCategoriesDTO soundCategoriesDTO;
    private List<Object> soundCategoriesDTOList;


    public SoundCategoriesDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();
    }

    @Override
    public void add(Object object) {
        soundCategoriesDTO = (SoundCategoriesDTO) object;
        soundName = soundCategoriesDTO.getSoundName();
        categoryName = soundCategoriesDTO.getCategoryName();

        row = new ContentValues();
        row.put(SoundDB.SOUND_NAME, soundName);
        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_SoundCategories,null,row);
    }

    @Override
    public void delete(Object object) {
        soundCategoriesDTO = (SoundCategoriesDTO) object;
        soundName = soundCategoriesDTO.getSoundName();
        categoryName = soundCategoriesDTO.getCategoryName();

        db.delete(SoundDB.TABEL_SoundCategories,
                SoundDB.SOUND_NAME + " = '" + soundName + "' AND "
                        + SoundDB.CATEGORY_NAME + " = '"+ categoryName + "'",
                null);



    }

    @Override
    public List<Object> getList() {

        soundCategoriesDTOList = new ArrayList<>();

        String[] column = {SoundDB.SOUND_NAME,SoundDB.CATEGORY_NAME};

        Cursor cursor = db.query(SoundDB.TABEL_SoundCategories,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String soundName = cursor.getString(0);
            String categoryName = cursor.getString(1);


            soundCategoriesDTO = new SoundCategoriesDTO(soundName,categoryName);
            soundCategoriesDTOList.add(soundCategoriesDTO);

        }

        cursor.close();
        return soundCategoriesDTOList;
    }


}
