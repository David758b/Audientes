package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class LibrarySoundLogic {

    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;

    public LibrarySoundLogic(SoundCategoriesDAO soundCategoriesDAO, SoundDAO soundDAO) {
        this.soundCategoriesDAO = soundCategoriesDAO;
        this.soundDAO = soundDAO;
    }

    /**
     * Returns a list of all the sounds in the database
     * @return list of strings
     */
    public List<String> getSoundsList () {
        List<SoundDTO> soundDTOS = soundDAO.getList();
        List<String> sounds = new ArrayList<>();
        for (SoundDTO DTO: soundDTOS) {
            sounds.add(DTO.getSoundName());
        }
        return sounds;
    }


    /**
     * takes a list of sounds and returns the duration of each sound
     * @param sounds
     * @return
     */
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

    /**
     * returns the categories for each sound
     * @param sounds
     * @return
     */

    public List<String> getCategories (List<String> sounds){
        List<String> categories = new ArrayList<>();
        List<SoundCategoriesDTO> DTOS = soundCategoriesDAO.getList();
        StringBuilder finalString = new StringBuilder();

        for (String sound: sounds) {
            for (SoundCategoriesDTO dtos: DTOS) {
                if(sound.equals(dtos.getSoundName())){
                    finalString.append("#").append(dtos.getCategoryName()).append(" ");
                }
            }
            categories.add(finalString.toString());
            finalString = new StringBuilder();
        }

        return categories;

    }

}
