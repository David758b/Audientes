package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.UI.CategoryListSounds;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

public class CategoryListSoundsLogic {
    private Context context;
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    public CategoryListSoundsLogic (Context context) {
        this.context = context;
        this.soundCategoriesDAO = new SoundCategoriesDAO(context);
        this.soundDAO = new SoundDAO(context);
    }

    public List<String> getSoundsList (String category) {
        List<SoundCategoriesDTO> soundCategoriesDTOS = soundCategoriesDAO.getList();
        List<String> sounds = new ArrayList<>();
        for (SoundCategoriesDTO DTO: soundCategoriesDTOS) {
            if (DTO.getCategoryName().equals(category)) {
                sounds.add(DTO.getSoundName());
            }
        }
        return sounds;
    }

    public List<String> getDuration (List<String> sounds) {
        List<String> durations = new ArrayList<>();
        List<SoundDTO> soundDTOS = soundDAO.getList();
        for (String sound:sounds) {
            for (SoundDTO soundDTO: soundDTOS) {
                if (sound.equals(soundDTO.getSoundName())){
                    // TODO convert duration to the right format (00:00:00)
                    durations.add("" + soundDTO.getSoundDuration());
                }
            }
        }

        return durations;
    }

}
