package com.example.AudientesAPP.model.funktionalitet;

import android.content.Context;

import com.example.AudientesAPP.model.DTO.PresetDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetDAO;

import java.util.ArrayList;
import java.util.List;

public class PresetLogic {
    private final PresetDAO presetDAO;
    List<PresetLogic.OnPresetLogicListener> listeners;

    //We need to creates these lists because the recycleviewAddapter can only take one instance of a list
    private final List<String> presetNames;


    public PresetLogic(PresetDAO presetDAO) {
        this.presetDAO = presetDAO;
        this.listeners = new ArrayList<>();
        this.presetNames = new ArrayList<>();
        initPresetNames();
    }

    public void addPreset(String presetName) {
        PresetDTO presetDTO = new PresetDTO(presetName);
        presetDAO.add(presetDTO);
        //Måske få listen fra DAO og kald initCategoryNames istedet for linje 30
        presetNames.add(presetDTO.getPresetName());
        
        System.out.println("-------------------ADD CATEGORY------------------");
        notifyListeners();
    }
    //Hvis init skal køres fra fragmentet og ikke konstruktøren i denne klasse skal den være public og slettes fra konstruktøren
    private void initPresetNames(){
        //Hvis den indeholder noget så clearer vi den
        presetNames.clear();
        List<PresetDTO> presetDTOS = presetDAO.getList();
        for (PresetDTO a: presetDTOS) {
            presetNames.add(a.getPresetName());
        }
    }
    public boolean isExisting(String presetName){
        for (String pName:presetNames) {
            if(presetName.equals(pName)){
                return true;
            }
        }
        return false;
    }

    //Burde returnere en liste af PresetDTO's, hvis vi også skal have farve og billede reference med.
    public List<String> getPresetNames () {
        return presetNames;
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
