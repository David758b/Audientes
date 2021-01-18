package com.example.AudientesAPP.model.DTO;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class SoundDTO {
    private String soundName;
    private String soundSrc;
    private String soundDuration;


    public SoundDTO(String soundName, String soundSrc, String soundDuration) {
        this.soundName = soundName;
        this.soundSrc = soundSrc;
        this.soundDuration = soundDuration;
    }

    public String getSoundName() {
        return soundName;
    }

    public String getSoundSrc() {
        return soundSrc;
    }

    public String getSoundDuration() {
        return soundDuration;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public void setSoundSrc(String soundSrc) {
        this.soundSrc = soundSrc;
    }

    public void setSoundDuration(String soundDuration) {
        this.soundDuration = soundDuration;
    }

    @Override
    public String toString() {
        return "SoundDTO{" +
                "soundName='" + soundName + '\'' +
                ", soundSrc='" + soundSrc + '\'' +
                ", soundDuration=" + soundDuration +
                '}';
    }
}
