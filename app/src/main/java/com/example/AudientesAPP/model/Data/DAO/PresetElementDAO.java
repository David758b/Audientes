package com.example.AudientesAPP.model.Data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.Data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.model.Data.SoundDB;
import com.example.AudientesAPP.model.context.Context;

import java.util.ArrayList;
import java.util.List;

public class PresetElementDAO implements IDAO {
    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String presetName;
    private String soundName;
    private int soundVolume;
    private PresetElementDTO presetElementDTO;
    private List<Object> presetElementDTOList;

    public PresetElementDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();
    }

    @Override
    public void add(Object object) {
        presetElementDTO = (PresetElementDTO) object;

        presetName = presetElementDTO.getPresetName();
        soundName = presetElementDTO.getSoundName();
        soundVolume = presetElementDTO.getSoundVolume();

        row = new ContentValues();
        row.put(SoundDB.PRESET_NAME,presetName);
        row.put(SoundDB.SOUND_NAME, soundName);
        row.put(SoundDB.VOLUME,soundVolume);

        db.insert(SoundDB.TABEL_PresetElements,null,row);
    }

    @Override
    public void delete(Object object) {
        presetElementDTO = (PresetElementDTO) object;
        presetName = presetElementDTO.getPresetName();
        soundName = presetElementDTO.getSoundName();

        db.delete(SoundDB.TABEL_PresetElements,
                SoundDB.PRESET_NAME + " = '" + presetName + "' AND "
                        + SoundDB.SOUND_NAME + " = '"+ soundName + "'",
                null);
    }

    @Override
    public List<Object> getList() {

        presetElementDTOList = new ArrayList<>();

        String[] column = {SoundDB.PRESET_NAME,SoundDB.SOUND_NAME,SoundDB.VOLUME};

        Cursor cursor = db.query(SoundDB.TABEL_PresetElements,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String presetName = cursor.getString(0);
            String soundName = cursor.getString(1);
            int soundVolume = cursor.getInt(2);

            presetElementDTO = new PresetElementDTO(presetName,soundName,soundVolume);
            presetElementDTOList.add(presetElementDTO);

        }
        cursor.close();
        return presetElementDTOList;
    }

}
