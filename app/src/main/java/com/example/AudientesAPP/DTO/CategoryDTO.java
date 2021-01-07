package com.example.AudientesAPP.DTO;

import java.util.List;

public class CategoryDTO {
    private String categoryName;
    private List<SoundDTO> categorySounds;
    private List<PresetDTO> categoryPresets;

    public CategoryDTO(String categoryName, List<SoundDTO> categorySounds, List<PresetDTO> categoryPresets) {
        this.categoryName = categoryName;
        this.categorySounds = categorySounds;
        this.categoryPresets = categoryPresets;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<SoundDTO> getCategorySounds() {
        return categorySounds;
    }

    public List<PresetDTO> getCategoryPresets() {
        return categoryPresets;
    }

    //Skal vi evt. adde lyde til kategorien denne vej fra? Ellers skal det gøres gennem UI, som vi så henter fra DB.

}
