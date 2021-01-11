package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

public class SoundListLogic {
    private Context context;
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    public SoundListLogic (Context context) {
        this.context = context;
        this.soundCategoriesDAO = new SoundCategoriesDAO(context);
        this.soundDAO = new SoundDAO(context);
    }

    public List<String> getSoundsList () {
        List<SoundDTO> soundDTOS = soundDAO.getList();
        List<String> sounds = new ArrayList<>();
        for (SoundDTO DTO: soundDTOS) {
            sounds.add(DTO.getSoundName());
        }
        return sounds;
    }


    public List<String> getDuration (List<String> sounds) {
        List<String> durations = new ArrayList<>();
        List<SoundDTO> soundDTOS = soundDAO.getList();
        for (String sound:sounds) {
            for (SoundDTO soundDTO: soundDTOS) {
                if (sound.equals(soundDTO.getSoundName())){
                    durations.add(soundDTO.getSoundDuration());
                }
            }
        }

        return durations;
    }

    public List<String> getCategories (List<String> sounds){
        List<String> categories = new ArrayList<>();
        List<SoundCategoriesDTO> DTOS = soundCategoriesDAO.getList();
        String finalString = "";

        for (String sound: sounds) {
            for (SoundCategoriesDTO dtos: DTOS) {
                if(sound.equals(dtos.getSoundName())){
                    finalString = finalString + "#" + dtos.getCategoryName() + " ";
                }
            }
            categories.add(finalString);
            finalString = "";
        }

        return categories;

    }

}
