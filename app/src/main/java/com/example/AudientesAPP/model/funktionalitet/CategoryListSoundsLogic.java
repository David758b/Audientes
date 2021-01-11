package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.Controller;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

public class CategoryListSoundsLogic {
    private Controller controller;
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    public CategoryListSoundsLogic (Controller controller) {
        this.controller = controller;
        this.soundCategoriesDAO = new SoundCategoriesDAO(controller);
        this.soundDAO = new SoundDAO(controller);
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
