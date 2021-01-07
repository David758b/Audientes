package com.example.AudientesAPP.DTO;

import java.util.List;

public class PresetDTO {
    private String presetName;
    private List<SoundDTO> presetSounds;
    private int presetDuration;

    public PresetDTO(String presetName, List<SoundDTO> presetSounds, int presetDuration) {
        this.presetName = presetName;
        this.presetSounds = presetSounds;
        this.presetDuration = presetDuration;
    }

    public String getPresetName() {
        return presetName;
    }

    public List<SoundDTO> getPresetSounds() {
        return presetSounds;
    }

    public int getPresetDuration() {
        return presetDuration;
    }

    @Override
    public String toString() {
        return "PresetDTO{" +
                "presetName='" + presetName + '\'' +
                ", presetSounds=" + presetSounds +
                ", presetDuration=" + presetDuration +
                '}';
    }
}
