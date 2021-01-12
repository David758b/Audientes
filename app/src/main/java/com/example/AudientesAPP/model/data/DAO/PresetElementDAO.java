package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.model.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.model.data.SoundDB;

import java.util.ArrayList;
import java.util.List;

public class PresetElementDAO implements IDAO<PresetElementDTO> {
    private ModelViewController modelViewController;
    private ContentValues row;
    private SQLiteDatabase db;
    private String presetName;
    private String soundName;
    private int soundVolume;
    private PresetElementDTO presetElementDTO;
    private List<PresetElementDTO> presetElementDTOList;

    public PresetElementDAO(ModelViewController modelViewController) {
        this.modelViewController = modelViewController;
        db = modelViewController.getActivity().getDB();
    }

    @Override
    public void add(PresetElementDTO presetElementDTO) {

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
    public void delete(PresetElementDTO presetElementDTO) {
        presetName = presetElementDTO.getPresetName();
        soundName = presetElementDTO.getSoundName();

        db.delete(SoundDB.TABEL_PresetElements,
                SoundDB.PRESET_NAME + " = '" + presetName + "' AND "
                        + SoundDB.SOUND_NAME + " = '"+ soundName + "'",
                null);
    }

    @Override
    public List<PresetElementDTO> getList() {

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
