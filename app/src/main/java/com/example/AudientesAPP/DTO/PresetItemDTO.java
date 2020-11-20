package com.example.AudientesAPP.DTO;

public class PresetItemDTO {
    private String presetName;

    public PresetItemDTO(String presetName){
        this.presetName = presetName;
    }

    public String getPresetName() {
        return presetName;
    }

    @Override
    public String toString() {
        return "PresetItemDTO{" +
                "presetName='" + presetName + '\'' +
                '}';
    }
}
