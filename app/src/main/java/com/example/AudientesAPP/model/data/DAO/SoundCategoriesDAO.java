package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.InterfaceDAO.ISoundCategoriesDAO;
import com.example.AudientesAPP.model.data.SoundDB;

import java.util.ArrayList;
import java.util.List;

public class SoundCategoriesDAO implements ISoundCategoriesDAO {
    private ModelViewController modelViewController;
    private ContentValues row;
    private SQLiteDatabase db;
    private String soundName;
    private String categoryName;
    private SoundCategoriesDTO soundCategoriesDTO;
    private List<SoundCategoriesDTO> soundCategoriesDTOList;


    public SoundCategoriesDAO(ModelViewController modelViewController) {
        this.modelViewController = modelViewController;
        db = modelViewController.getActivity().getDB();
    }

    @Override
    public void add(SoundCategoriesDTO soundCategoriesDTO) {
        soundName = soundCategoriesDTO.getSoundName();
        categoryName = soundCategoriesDTO.getCategoryName();

        row = new ContentValues();
        row.put(SoundDB.SOUND_NAME, soundName);
        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_SoundCategories,null,row);
    }

    @Override
    public void delete(SoundCategoriesDTO soundCategoriesDTO) {
        soundName = soundCategoriesDTO.getSoundName();
        categoryName = soundCategoriesDTO.getCategoryName();

        db.delete(SoundDB.TABEL_SoundCategories,
                SoundDB.SOUND_NAME + " = '" + soundName + "' AND "
                        + SoundDB.CATEGORY_NAME + " = '"+ categoryName + "'",
                null);



    }

    @Override
    public List<SoundCategoriesDTO> getList() {

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
