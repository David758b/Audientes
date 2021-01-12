package com.example.AudientesAPP.model.DTO;

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
