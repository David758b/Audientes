package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.UI.LibraryListCategory;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class LibraryListCategoryLogic {
    private Context context;
    private CategoryDAO categoryDAO;

    public LibraryListCategoryLogic (Context context) {
        this.context = context;
        this.categoryDAO = new CategoryDAO(context);
    }



    public List<String> getCategoryNames () {
        List<CategoryDTO> categoryDTOS = categoryDAO.getList();
        List<String> categoryNames = new ArrayList<>();

        categoryNames.add("Create category");
        for (CategoryDTO a: categoryDTOS) {
            categoryNames.add(a.getCategoryName());
        }
        return categoryNames;
    }
}
