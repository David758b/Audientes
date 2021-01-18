package com.example.AudientesAPP.model.funktionalitet;

import android.content.SharedPreferences;

import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class CategorySoundsLogic {
    public class SoundWithDuration{
        private String soundName;
        private String soundDuration;

        public SoundWithDuration(String soundName, String soundDuration) {
            this.soundName = soundName;
            this.soundDuration = soundDuration;
        }

        public String getSoundName() {
            return soundName;
        }

        public String getSoundDuration() {
            return soundDuration;
        }
    }
    private SoundCategoriesDAO soundCategoriesDAO;
    private SoundDAO soundDAO;
    private SharedPreferences prefs;
    List<OnCategorySoundsLogicListener> listeners;

    private final List<SoundCategoriesDTO> soundCategories;
    private final List<SoundDTO> sounds;
    private final List<SoundWithDuration> soundsWithDuration;

    public CategorySoundsLogic(SoundCategoriesDAO soundCategoriesDAO, SoundDAO soundDAO, SharedPreferences prefs) {
        this.soundCategoriesDAO = soundCategoriesDAO;
        this.soundDAO = soundDAO;
        this.prefs = prefs;
        this.soundsWithDuration = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.soundCategories = new ArrayList<>();
        this.sounds = new ArrayList<>();
        initSoundCategories();
    }

    public void initSoundCategories(){
        soundCategories.clear();
        sounds.clear();
        List<SoundCategoriesDTO> soundCategoriesDTOS = soundCategoriesDAO.getList();
        List<SoundDTO> soundDTOS = soundDAO.getList();
        sounds.addAll(soundDTOS);
        soundCategories.addAll(soundCategoriesDTOS);
    }

    private void initSoundsWithDuration(){
        soundsWithDuration.clear();
        String categoryName = getCurrentCategory();
        List<SoundCategoriesDTO> soundCategoriesDTOS = getSoundCategories();
        List<SoundDTO> soundDTOS = getSounds();
        for (SoundCategoriesDTO soundCategoriesDTO: soundCategoriesDTOS) {
            if (soundCategoriesDTO.getCategoryName().equals(categoryName)) {
                String duration = "";
                String name = soundCategoriesDTO.getSoundName();
                    for (SoundDTO soundDTO: soundDTOS) {
                        if (name.equals(soundDTO.getSoundName())){
                            duration = soundDTO.getSoundDuration();
                        }
                    }
                soundsWithDuration.add(new SoundWithDuration(name,duration));
            }
        }
    }

    public List<SoundWithDuration> getSoundsWithDuration() {
        return soundsWithDuration;
    }

    public List<String> getSoundsList () {
        String categoryName = getCurrentCategory();
        List<SoundCategoriesDTO> soundCategoriesDTOS = getSoundCategories();
        List<String> sounds = new ArrayList<>();
        for (SoundCategoriesDTO DTO: soundCategoriesDTOS) {
            if (DTO.getCategoryName().equals(categoryName)) {
                sounds.add(DTO.getSoundName());
            }
        }
        return sounds;
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

    public List<SoundCategoriesDTO> getSoundCategories(){
        return soundCategories;
    }

    public String getCurrentCategory(){
        return prefs.getString("Category", "Fejl");
    }

    public void setCurrentCategory(String category){
        prefs.edit().putString("Category", category).apply();
        initSoundsWithDuration();
    }

    public List<SoundDTO> getSounds(){
        return sounds;
    }

    /**
     * method for getting the sounds which is not in the given category
     * @return List of soundNames of the sounds that is not in the category
     */
    public List<String> getAvailableSounds(){

        List<String> categorySounds = getSoundsList();
        List<SoundDTO> allSoundsDTO = getSounds();
        List<String> availableSounds = new ArrayList<>();

        /*
        for (String name: categorySounds) {
            for (SoundDTO dto: allSoundsDTO) {

                if(!dto.getSoundName().equals(name)){
                    availableSounds.add(dto.getSoundName());
                }
            }
        }

         */

        for (SoundDTO dto: allSoundsDTO) {

            if(!categorySounds.contains(dto.getSoundName())){
                availableSounds.add(dto.getSoundName());
            }
        }

        return availableSounds;
    }

    /**
     * Adds a SoundCategoriesDTO with a given category name and soundname to a SoundCategoriesDAO and
     * updates the temporary list of soundCategoriesDTO
     * @param soundName The sound you want to add to the given category
     */

    public void addSoundsToCategory(String soundName){
        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO(soundName, getCurrentCategory());
        soundCategoriesDAO.add(soundCategoriesDTO);
        soundCategories.add(soundCategoriesDTO);
        initSoundsWithDuration();
        notifyListeners();
    }


    public interface OnCategorySoundsLogicListener {
        void updateCategorySounds(CategorySoundsLogic categorySoundsLogic);
    }

    public void addCategorySoundsLogicListener(OnCategorySoundsLogicListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (OnCategorySoundsLogicListener  listener : listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updateCategorySounds(this);
        }
    }

    public void deleteSoundCategory(String soundName) {
        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO(soundName, getCurrentCategory());
        soundCategoriesDAO.delete(soundCategoriesDTO);
         //kan ikke bruge foreach, det skal være fori
        for (int i = 0; i < soundCategories.size(); i++) {
            if (soundCategories.get(i).getCategoryName().equals(getCurrentCategory()) && soundCategories.get(i).getSoundName().equals(soundName)) {
                soundCategories.remove(soundCategories.get(i));
            }
        }
        initSoundsWithDuration();
        notifyListeners();
    }

    public void deleteCategory(String categoryName){

        int counter = 0;
        while(counter!=soundCategories.size()){
            if(soundCategories.get(counter).getCategoryName().equals(categoryName)){
                soundCategories.remove(counter);
            }else{
                counter++;
            }
        }
    }
}
