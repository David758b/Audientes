package com.example.AudientesAPP.model.funktionalitet;

import android.content.Context;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.PresetDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetDAO;

import java.util.ArrayList;
import java.util.List;

public class PresetLogic {
    private final PresetDAO presetDAO;
    List<PresetLogic.OnPresetLogicListener> listeners;
    private PresetContentLogic presetContentLogic;

    //We need to creates these lists because the recycleviewAddapter can only take one instance of a list
    private final List<PresetDTO> presets;


    public PresetLogic(PresetDAO presetDAO, PresetContentLogic presetContentLogic) {
        this.presetDAO = presetDAO;
        this.listeners = new ArrayList<>();
        this.presets = new ArrayList<>();
        this.presetContentLogic = presetContentLogic;
        initPresetNames();
    }

    public void addPreset(String presetName) {
        PresetDTO presetDTO = new PresetDTO(presetName);
        presetDAO.add(presetDTO);
        //Måske få listen fra DAO og kald initCategoryNames istedet for linje 30
        presets.add(presetDTO);
        notifyListeners();
    }
    //Hvis init skal køres fra fragmentet og ikke konstruktøren i denne klasse skal den være public og slettes fra konstruktøren
    private void initPresetNames(){
        //Hvis den indeholder noget så clearer vi den
        presets.clear();
        List<PresetDTO> presetDTOS = presetDAO.getList();
        presets.addAll(presetDTOS);
    }
    public boolean isExisting(String presetName){
        for (PresetDTO DTO:presets) {
            if(DTO.getPresetName().equals(presetName)){
                return true;
            }
        }
        return false;
    }

    //Burde returnere en liste af PresetDTO's, hvis vi også skal have farve og billede reference med.
    public List<PresetDTO> getPresets () {
        return presets;
    }

    public PresetDTO getPreset(String presetName){
        for (PresetDTO DTO:presets) {
            if (DTO.getPresetName().equals(presetName)){
                return DTO;
            }
        }
        return null;
    }

    public List<String> getPresetNames() {
        List<String> pNames = new ArrayList<>();
        for (PresetDTO DTO:presets) {
                pNames.add(DTO.getPresetName());
        }
        return pNames;
    }


    /**
     * Updates the preset name to a another given preset name
     * @param oldPresetName The current name of the category
     * @param newPresetName The desired name of the category
     */
    // Note to self: checks for difference in names should happen in the class that calls the method
    public void updatePreset(String oldPresetName, String newPresetName){
        PresetDTO presetOldDTO = getPreset(oldPresetName);
        PresetDTO presetNewDTO = new PresetDTO(newPresetName);

        //updates the database
        presetDAO.updateName(presetOldDTO, presetNewDTO);

        //updates the list
        for (PresetDTO dto:presets) {
            if (presetOldDTO.getPresetName().equals(dto.getPresetName())){
                dto.setPresetName(presetNewDTO.getPresetName());
            }
        }
        presetContentLogic.initPresetElements();
        presetContentLogic.setCurrentPreset(newPresetName);
    }



    public void addPresetLogicListener(OnPresetLogicListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(){
        for (PresetLogic.OnPresetLogicListener listener: listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updatePresetList(this);
        }
    }

    public interface OnPresetLogicListener{
        void updatePresetList(PresetLogic presetLogic);
    }
}
