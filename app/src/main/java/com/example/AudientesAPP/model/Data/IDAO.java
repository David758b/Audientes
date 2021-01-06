package com.example.AudientesAPP.model.Data;

import java.util.List;

public interface IDAO {

    void add (Object object);
    void delete (Object object);
    List<Object> getList();
    /*
        ListDTO

        return List<String> categories;
     */



    List<Object> getCategorySound(Object SoundDTO);
    List<Object> getSoundCategory(Object CategoryDTO);
    List<Object> getPresetCategory(Object CategoryDTO);
    List<Object> getCategoryPreset(Object PresetDTO);
    List<Object> getPresetElementer(String presetName);
    Object getSound(String soundName);


}
