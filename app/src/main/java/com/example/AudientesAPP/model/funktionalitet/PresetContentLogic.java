package com.example.AudientesAPP.model.funktionalitet;

import android.content.SharedPreferences;

import com.example.AudientesAPP.model.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetContentLogic {
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
    private PresetElementDAO presetElementDAO;
    private SoundDAO soundDAO;
    private SharedPreferences prefs;
    List<PresetContentLogic.OnPresetContentLogicListener> listeners;
    private final List<PresetElementDTO> presetElements;
    private final List<SoundDTO> sounds;
    private final List<PresetContentLogic.SoundWithDuration> soundsWithDuration;

    public PresetContentLogic(PresetElementDAO presetElementDAO, SoundDAO soundDAO, SharedPreferences prefs) {
        this.presetElementDAO = presetElementDAO;
        this.soundDAO = soundDAO;
        this.prefs = prefs;
        this.soundsWithDuration = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.presetElements = new ArrayList<>();
        this.sounds = new ArrayList<>();
        initPresetElements();
    }

    public void initPresetElements(){
        presetElements.clear();
        sounds.clear();
        List<PresetElementDTO> presetElementsDTOs = presetElementDAO.getList();
        List<SoundDTO> soundDTOS = soundDAO.getList();
        sounds.addAll(soundDTOS);
        presetElements.addAll(presetElementsDTOs);
    }

    private void initSoundsWithDuration(){
        soundsWithDuration.clear();
        String presetName = getCurrentPreset();
        List<PresetElementDTO> presetElementDTOS = getPresetElements();
        List<SoundDTO> soundDTOS = getSounds();
        for (PresetElementDTO presetElementDTO: presetElementDTOS) {
            if (presetElementDTO.getPresetName().equals(presetName)) {
                String duration = "";
                String name = presetElementDTO.getSoundName();
                for (SoundDTO soundDTO: soundDTOS) {
                    if (name.equals(soundDTO.getSoundName())){
                        duration = soundDTO.getSoundDuration();
                    }
                }
                soundsWithDuration.add(new SoundWithDuration(name,duration));
            }
        }
    }

    public List<PresetContentLogic.SoundWithDuration> getSoundsWithDuration() {
        return soundsWithDuration;
    }

    public List<String> getSoundsList () {
        String presetName = getCurrentPreset();
        List<PresetElementDTO> presetElementDTOS = getPresetElements();
        List<String> sounds = new ArrayList<>();
        for (PresetElementDTO DTO: presetElementDTOS) {
            if (DTO.getPresetName().equals(presetName)) {
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

    public List<PresetElementDTO> getPresetElements(){
        return presetElements;
    }

    public String getCurrentPreset(){
        return prefs.getString("Preset", "Fejl");
    }

    public void setCurrentPreset(String preset){
        prefs.edit().putString("Preset", preset).apply();
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

        List<String> presetSounds = getSoundsList();
        List<SoundDTO> allSoundsDTO = getSounds();
        List<String> availableSounds = new ArrayList<>();

        for (SoundDTO dto: allSoundsDTO) {

            if(!presetSounds.contains(dto.getSoundName())){
                availableSounds.add(dto.getSoundName());
            }
        }

        return availableSounds;
    }

    /**
     * Adds a PresetElementDTO with a given category name and soundname to a SoundCategoriesDAO and
     * updates the temporary list of PresetElementDTO
     * @param soundName The sound you want to add to the given category
     */

    public void addSoundsToPreset(String soundName){
        PresetElementDTO presetElementDTO = new PresetElementDTO(getCurrentPreset(),soundName,2);
        presetElementDAO.add(presetElementDTO);
        presetElements.add(presetElementDTO);
        initSoundsWithDuration();
        notifyListeners();
    }


    public interface OnPresetContentLogicListener {
        void updatePresetElements(PresetContentLogic presetContentLogic);
    }

    public void addPresetContentLogicListener(PresetContentLogic.OnPresetContentLogicListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (PresetContentLogic.OnPresetContentLogicListener listener : listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updatePresetElements(this);
        }
    }

    public void deletePresetElement(String soundName) {
        PresetElementDTO presetElementDTO = new PresetElementDTO(getCurrentPreset(), soundName, 2 );
        presetElementDAO.delete(presetElementDTO);
        //kan ikke bruge foreach, det skal være fori
        for (int i = 0; i < presetElements.size(); i++) {
            if (presetElements.get(i).getPresetName().equals(getCurrentPreset()) && presetElements.get(i).getSoundName().equals(soundName)) {
                presetElements.remove(presetElements.get(i));
            }
        }
        initSoundsWithDuration();
        notifyListeners();
    }


    /**
     * Method for deleting presets in the temporary list presetElements that does not exist in the db
     * @param presetName The preset that has to be deleted
     */
    public void deletePreset(String presetName){

        int counter = 0;
        while(counter!=presetElements.size()){
            if(presetElements.get(counter).getPresetName().equals(presetName)){
                presetElements.remove(counter);
            }else{
                counter++;
            }
        }
    }
}
