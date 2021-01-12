package com.example.AudientesAPP.model.DTO;

public class CategoryDTO {
    private String categoryName;
    private String picture;
    private String color;



    public CategoryDTO(String categoryName, String picture, String color) {
        this.categoryName = categoryName;
        this.picture = picture;
        this.color = color;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getPicture() {
        return picture;
    }
    public String getColor() {
        return color;
    }
    //Skal vi evt. adde lyde til kategorien denne vej fra? Ellers skal det gøres gennem UI, som vi så henter fra DB.

}
