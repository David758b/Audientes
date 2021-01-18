package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.PresetDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.model.data.SoundDB;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetDAO implements IDAO<PresetDTO> {
    private ContentValues row;
    private SQLiteDatabase db;
    private String presetName;
    private PresetDTO presetDTO;
    private List<PresetDTO> presetDTOList;

    /**
     * Constructor of the presetDAO which initialize the relevant attributes of the class.
     * @param db- den skal sørme også bare have en db
     */
    public PresetDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Adds a specific presetDTO to the database
     * @param presetDTO you wish to save in the database
     */
    @Override
    public void add(PresetDTO presetDTO) {

        presetName = presetDTO.getPresetName();

        row = new ContentValues();

        row.put(SoundDB.PRESET_NAME,presetName);

        db.insert(SoundDB.TABEL_Presets,null,row);

    }

    /**
     * Deletes a row containing the presetDTO in the database
     * @param presetDTO which you want to delete
     */

    @Override
    public void delete(PresetDTO presetDTO) {

        presetName = presetDTO.getPresetName();

        db.delete(SoundDB.TABEL_Presets,SoundDB.PRESET_NAME + " = '" + presetName + "'",null);

    }

    /**
     * This method is for retrieving a list of all the presets
     * @return a list of presetDTO presetDTOs
     */
    @Override
    public List<PresetDTO> getList() {

        presetDTOList = new ArrayList<>();

        String[] column = {SoundDB.PRESET_NAME};

        Cursor cursor = db.query(SoundDB.TABEL_Presets,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            presetDTO = new PresetDTO(name);
            presetDTOList.add(presetDTO);
        }

        cursor.close();

        return presetDTOList;
    }

    public void updateName(PresetDTO oldPresetDTO, PresetDTO newPresetDTO){

        // UPDATE TABLE_CATEGORY SET CATEGORY_NAME = newName WHERE CATEGORY_NAME = oldName
        row = new ContentValues();

        row.put(SoundDB.PRESET_NAME,newPresetDTO.getPresetName());

        db.execSQL("UPDATE " + SoundDB.TABEL_Presets+ " SET " + SoundDB.PRESET_NAME + "="
                + "'" + newPresetDTO.getPresetName()+ "'" + " WHERE " + SoundDB.PRESET_NAME + " = "
                + "'" + oldPresetDTO.getPresetName() + "'");
        db.execSQL("UPDATE " + SoundDB.TABEL_PresetElements + " SET " + SoundDB.PRESET_NAME + "="
                + "'" + newPresetDTO.getPresetName()+ "'" + " WHERE " + SoundDB.PRESET_NAME + " = "
                + "'" + oldPresetDTO.getPresetName() + "'");
        db.execSQL("UPDATE " + SoundDB.TABEL_PresetCategories + " SET " + SoundDB.PRESET_NAME + "="
                + "'" + newPresetDTO.getPresetName()+ "'" + " WHERE " + SoundDB.PRESET_NAME + " = "
                + "'" + oldPresetDTO.getPresetName() + "'");
        // UPDATE TABEL_Category SET
    }
}
