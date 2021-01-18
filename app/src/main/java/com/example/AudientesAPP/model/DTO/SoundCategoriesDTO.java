package com.example.AudientesAPP.model.DTO;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class SoundCategoriesDTO {
    private String soundName;
    private String categoryName;


    public SoundCategoriesDTO(String soundName, String categoryName) {
        this.soundName = soundName;
        this.categoryName = categoryName;
    }

    public String getSoundName() {
        return soundName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
