package com.example.AudientesAPP.model.DTO;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetElementDTO {
    private String presetName;
    private String soundName;
    private int soundVolume;


    public PresetElementDTO(String presetName, String soundName, int soundVolume) {
        this.presetName = presetName;
        this.soundName = soundName;
        this.soundVolume = soundVolume;
    }

    public String getPresetName() {
        return presetName;
    }

    public String getSoundName() {
        return soundName;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }
}
