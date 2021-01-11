package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.model.data.InterfaceDAO.ISoundDAO;
import com.example.AudientesAPP.model.data.SoundDB;
import com.example.AudientesAPP.model.context.Context;
import java.util.ArrayList;
import java.util.List;

public class SoundDAO implements ISoundDAO {

    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String soundName;
    private String soundSrc;
    private String soundDuration;
    private SoundDTO soundDTO;
    private List<SoundDTO> soundDTOList;

    /**
     * Constructor of the soundDAO which initialize the relevant attributes of the class.
     * @param context
     */
    public SoundDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();
    }

    /**
     * Adds a specific soundDTO to the database
     * @param soundDTO you wish to save in the database
     */
    @Override
    public void add(SoundDTO soundDTO) {
        soundName = soundDTO.getSoundName();
        soundSrc = soundDTO.getSoundSrc();
        soundDuration = soundDTO.getSoundDuration();


        row = new ContentValues();

        row.put(SoundDB.SOUND_NAME,soundName);
        row.put(SoundDB.PATH,soundSrc);
        row.put(SoundDB.DURATION,soundDuration);

        //Catches an exeption if you try to insert some file that already exists in the database
        try{
            db.insertOrThrow(SoundDB.TABEL_Sounds,null,row);
        }catch (SQLException e){
            System.out.println("The file already exists");
        }


    }

    /**
     * Deletes a row containing the soundDTO in the database
     * @param soundDTO which you want to delete
     */
    @Override
    public void delete(SoundDTO soundDTO) {

        soundName = soundDTO.getSoundName();

        db.delete(SoundDB.TABEL_Sounds,SoundDB.SOUND_NAME + " = '" + soundName + "'",null);

    }

    /**
     * This method is for retrieving a list of all the presets
     * @return a list of soundDTO soundDTOs
     */
    @Override
    public List<SoundDTO> getList() {

        soundDTOList = new ArrayList<>();

        String[] column = {SoundDB.SOUND_NAME,SoundDB.PATH,SoundDB.DURATION};

        Cursor cursor = db.query(SoundDB.TABEL_Sounds,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String path = cursor.getString(1);
            String duration = cursor.getString(2);

            soundDTO = new SoundDTO(name,path,duration);
            soundDTOList.add(soundDTO);

        }

        cursor.close();

        return soundDTOList;
    }

}
