package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

public class CategorySoundsLogic {
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    private final List<SoundCategoriesDTO> soundCategories;
    private final List<SoundDTO> sounds;

    public CategorySoundsLogic(SoundCategoriesDAO soundCategoriesDAO, SoundDAO soundDAO) {
        this.soundCategoriesDAO = soundCategoriesDAO;
        this.soundDAO = soundDAO;
        this.soundCategories = new ArrayList<>();
        this.sounds = new ArrayList<>();
        initSoundCategories();
    }

    public List<String> getSoundsList (String category) {
        List<SoundCategoriesDTO> soundCategoriesDTOS = getSoundCategories();
        List<String> sounds = new ArrayList<>();
        for (SoundCategoriesDTO DTO: soundCategoriesDTOS) {
            if (DTO.getCategoryName().equals(category)) {
                sounds.add(DTO.getSoundName());
            }
        }
        return sounds;
    }

    public List<SoundCategoriesDTO> getSoundCategories(){
        return soundCategories;
    }

    public List<SoundDTO> getSounds(){
        return sounds;
    }

    public void initSoundCategories(){
        soundCategories.clear();
        sounds.clear();
        List<SoundCategoriesDTO> soundCategoriesDTOS = soundCategoriesDAO.getList();
        List<SoundDTO> soundDTOS = soundDAO.getList();
        sounds.addAll(soundDTOS);
        soundCategories.addAll(soundCategoriesDTOS);
    }

    //TODO: alt for mange database kald herinde, dette skal laves om
    public List<String> getDuration (List<String> sounds) {
        List<String> durations = new ArrayList<>();
        List<SoundDTO> soundDTOS = getSounds();
        for (String sound:sounds) {
            for (SoundDTO soundDTO: soundDTOS) {
                if (sound.equals(soundDTO.getSoundName())){
                    durations.add(soundDTO.getSoundDuration());
                }
            }
        }

        return durations;
    }

    /**
     * Adds a SoundCategoriesDTO with a given category name and soundname to a SoundCategoriesDAO and
     * updates the temporary list of soundCategoriesDTO
     * @param categoryName The given category you want to add a sound to
     * @param soundName The sound you want to add to the given category
     */
    //Todo vi skal nok have noget listener værk her
    public void addSoundsToCategory(String categoryName, String soundName){

        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO(soundName,categoryName);
        soundCategoriesDAO.add(soundCategoriesDTO);
        soundCategories.add(soundCategoriesDTO);

    }




}
