package com.example.AudientesAPP.model.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.Context;

import java.util.ArrayList;
import java.util.List;

public class SoundDAO implements ISoundDAO{

    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String soundName;
    private String soundSrc;
    private int soundDuration;
    private SoundDTO soundDTO;
    private List<Object> soundDTOList;

    /**
     * Constructor of the soundDAO which initialize the relevant attributes of the class.
     * @param context
     */
    public SoundDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();
    }

    /**
     * Adds a specific object to the database
     * @param object you wish to save in the database
     */
    @Override
    public void add(Object object) {
        soundDTO = (SoundDTO) object;
        soundName = soundDTO.getSoundName();
        soundSrc = soundDTO.getSoundSrc();
        soundDuration = soundDTO.getSoundDuration();


        row = new ContentValues();

        row.put(SoundDB.SOUND_NAME,soundName);
        row.put(SoundDB.PATH,soundSrc);
        row.put(SoundDB.DURATION,soundDuration);

        db.insert(SoundDB.TABEL_Sounds,null,row);

    }

    /**
     * Deletes a row containing a name in the database
     * @param object which you want to delete
     */
    @Override
    public void delete(Object object) {

        soundDTO = (SoundDTO) object;
        soundName = soundDTO.getSoundName();

        db.delete(SoundDB.TABEL_Sounds,SoundDB.SOUND_NAME + " = '" + soundName + "'",null);

    }

    /**
     * This method is for retrieving a list of all the presets
     * @return a list of soundDTO objects
     */
    @Override
    public List<Object> getList() {

        soundDTOList = new ArrayList<>();

        String[] column = {SoundDB.SOUND_NAME,SoundDB.PATH,SoundDB.DURATION};

        Cursor cursor = db.query(SoundDB.TABEL_Sounds,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String path = cursor.getString(1);
            int duration = cursor.getInt(2);

            SoundDTO soundDTO = new SoundDTO(name,path,duration);
            soundDTOList.add(soundDTO);

        }

        cursor.close();

        return soundDTOList;
    }

}
