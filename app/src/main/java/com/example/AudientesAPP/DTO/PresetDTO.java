package com.example.AudientesAPP.DTO;

import java.util.List;

public class PresetDTO {
    private String presetName;


    public PresetDTO(String presetName, List<SoundDTO> presetSounds, int presetDuration) {
        this.presetName = presetName;
    }

    public String getPresetName() {
        return presetName;
    }

}
