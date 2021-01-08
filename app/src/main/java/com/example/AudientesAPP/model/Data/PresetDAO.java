package com.example.AudientesAPP.model.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.DTO.PresetDTO;
import com.example.AudientesAPP.model.context.Context;

import java.util.ArrayList;
import java.util.List;

public class PresetDAO implements IPresetDAO {
    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String presetName;
    private PresetDTO presetDTO;
    private List<Object> presetDTOList;

    /**
     * Constructor of the presetDAO which initialize the relevant attributes of the class.
     * @param context
     */
    public PresetDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();
    }

    /**
     * Adds a specific object to the database
     * @param object you wish to save in the database
     */
    @Override
    public void add(Object object) {

        presetDTO = (PresetDTO) object;
        presetName = presetDTO.getPresetName();

        row = new ContentValues();

        row.put(SoundDB.PRESET_NAME,presetName);

        db.insert(SoundDB.TABEL_Presets,null,row);

    }

    /**
     * Deletes a row containing a name in the database
     * @param object which you want to delete
     */

    @Override
    public void delete(Object object) {

        presetDTO = (PresetDTO) object;
        presetName = presetDTO.getPresetName();

        db.delete(SoundDB.TABEL_Presets,SoundDB.PRESET_NAME + " = '" + presetName + "'",null);

    }

    /**
     * This method is for retrieving a list of all the presets
     * @return a list of presetDTO objects
     */
    @Override
    public List<Object> getList() {

        presetDTOList = new ArrayList<>();

        String[] column = {SoundDB.PRESET_NAME};

        Cursor cursor = db.query(SoundDB.TABEL_Presets,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            PresetDTO presetDTO = new PresetDTO(name);
            presetDTOList.add(presetDTO);
        }

        cursor.close();

        return presetDTOList;
    }
}
