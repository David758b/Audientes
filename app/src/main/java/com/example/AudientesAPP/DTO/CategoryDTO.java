package com.example.AudientesAPP.DTO;

import java.util.List;

public class CategoryDTO {
    private String categoryName;

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //Skal vi evt. adde lyde til kategorien denne vej fra? Ellers skal det gøres gennem UI, som vi så henter fra DB.

}
