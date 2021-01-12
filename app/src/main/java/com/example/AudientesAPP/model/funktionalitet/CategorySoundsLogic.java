package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

public class CategorySoundsLogic {
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    public CategorySoundsLogic(SoundCategoriesDAO soundCategoriesDAO, SoundDAO soundDAO) {
        this.soundCategoriesDAO = soundCategoriesDAO;
        this.soundDAO = soundDAO;
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

    //TODO: alt for mange database kald herinde, dette skal laves om
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

}
