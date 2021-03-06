package com.example.AudientesAPP.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.model.DTO.PresetCategoriesDTO;
import com.example.AudientesAPP.data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.data.SoundDB;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetCategoriesDAO implements IDAO<PresetCategoriesDTO> {

    private ContentValues row;
    private SQLiteDatabase db;
    private String presetName;
    private String categoryName;
    private PresetCategoriesDTO presetCategoriesDTO;
    private List<PresetCategoriesDTO> presetCategoriesDTOList;


    /**
     * Constructor of the presetDAO which initialize the relevant attributes of the class.
     * @param db - den skal sørme også have en db
     */
    public PresetCategoriesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Adds a specific presetCategoriesDTO to the database
     * @param presetCategoriesDTO you wish to save in the database
     */
    @Override
    public void add(PresetCategoriesDTO presetCategoriesDTO) {
        presetName = presetCategoriesDTO.getPresetName();
        categoryName = presetCategoriesDTO.getCategoryName();

        row = new ContentValues();
        row.put(SoundDB.PRESET_NAME,presetName);
        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_PresetCategories,null,row);


    }

    /**
     * Deletes a row containing the presetCategoriesDTO in the database
     * @param presetCategoriesDTO which you want to delete
     */
    @Override
    public void delete(PresetCategoriesDTO presetCategoriesDTO) {
        presetName = presetCategoriesDTO.getPresetName();
        categoryName = presetCategoriesDTO.getCategoryName();

        db.delete(SoundDB.TABEL_PresetCategories,
                SoundDB.PRESET_NAME + " = '" + presetName + "' AND "
                        + SoundDB.CATEGORY_NAME + " = '"+ categoryName + "'",
                null);

    }

    /**
     * This method is for retrieving a list of all the presetCategories
     * @return a list of presetCategoriesDTO presetCategoriesDTOs
     */
    @Override
    public List<PresetCategoriesDTO> getList() {

        presetCategoriesDTOList = new ArrayList<>();

        String[] column = {SoundDB.PRESET_NAME,SoundDB.CATEGORY_NAME};

        Cursor cursor = db.query(SoundDB.TABEL_PresetCategories,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String presetName = cursor.getString(0);
            String categoryName = cursor.getString(1);


            presetCategoriesDTO = new PresetCategoriesDTO(presetName,categoryName);
            presetCategoriesDTOList.add(presetCategoriesDTO);

        }

        cursor.close();

        return presetCategoriesDTOList;

    }

}
