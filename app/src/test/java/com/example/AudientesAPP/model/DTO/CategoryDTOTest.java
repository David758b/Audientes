package com.example.AudientesAPP.model.DTO;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryDTOTest {

    @Test
    public void getCategoryName() {
        String category = "TesterTester";
        String picture = null;
        String color = "Blue";

        CategoryDTO categoryDTO = new CategoryDTO(category, picture, color);

        assertEquals("TesterTester", categoryDTO.getCategoryName());

        categoryDTO.setCategoryName("Hej");

        assertEquals("Hej", categoryDTO.getCategoryName());


    }

    @Test
    public void setCategoryName() {
        String category = "TesterTester";
        String picture = null;
        String color = "Blue";

        CategoryDTO categoryDTO = new CategoryDTO(category, picture, color);

        categoryDTO.setCategoryName("TesterIgen");

        assertEquals("TesterIgen", categoryDTO.getCategoryName());
    }

    @Test
    public void getPicture() {
        String category = "TesterTester2";
        String picture = "Projekter\\Audientes\\app\\src\\main\\res\\drawable-v24";
        String color = "Blue";

        CategoryDTO categoryDTO = new CategoryDTO(category, picture, color);

        assertEquals("Projekter\\Audientes\\app\\src\\main\\res\\drawable-v24", categoryDTO.getPicture());
    }

    @Test
    public void getColor() {
        String category = "TesterTester2";
        String picture = "null";
        String color = "Blue";

        CategoryDTO categoryDTO = new CategoryDTO(category, picture, color);

        assertEquals("Blue", categoryDTO.getColor());
    }
}