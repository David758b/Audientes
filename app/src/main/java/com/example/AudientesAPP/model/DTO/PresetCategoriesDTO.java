package com.example.AudientesAPP.model.DTO;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetCategoriesDTO {
    private String presetName;
    private String categoryName;

    public PresetCategoriesDTO(String presetName, String categoryName) {
        this.presetName = presetName;
        this.categoryName = categoryName;
    }

    public String getPresetName() {
        return presetName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
