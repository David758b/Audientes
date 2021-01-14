package com.example.AudientesAPP.model.funktionalitet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.UI.CategorySounds;
import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

/*
 * To seperate the data that the views use from the actual views
 * We use this class between the view and data
 * */
public class LibraryCategoryLogic {
    private CategoryDAO categoryDAO;
    List<OnLibraryLCLogicListener> listeners;

    /*
     * final list of CategoryDTO's, this list gets cleared everytime the constructor gets called
     * and gets filled up with data from the database
     * By having this list seperate we decide when theres a need to fetch data from the database.
     * If wanted we can save an instance of this list in the view, as long as we don't handle/create it there.
     * The recyclerviewAdapter that is made to only and always expect one list.
     * */
    private final List<CategoryDTO> categories;

    public LibraryCategoryLogic(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
        this.listeners = new ArrayList<>();
        this.categories = new ArrayList<>();
        initCategories();
    }

    public void addCategory(String categoryName, String picture, String color) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryName, picture, color);
        categoryDAO.add(categoryDTO);
        //Måske få listen fra DAO og kald initCategoryNames istedet for linje 30
        categories.add(categoryDTO);
        notifyListeners();
    }

    //Burde returnere en liste af CategoryDTO's, hvis vi også skal have farve og billede reference med.
    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public CategoryDTO getCategory(String categoryName){
        for (CategoryDTO categoryDTO:categories) {
            if (categoryDTO.getCategoryName().equals(categoryName)){
                return categoryDTO;
            }
        }
        return null;
    }

    //Hvis init skal køres fra fragmentet og ikke konstruktøren i denne klasse skal den være public og slettes fra konstruktøren
    private void initCategories() {
        //Hvis den indeholder noget så clearer vi den
        categories.clear();

        List<CategoryDTO> categoryDTOS = categoryDAO.getList();

        //tilføj create category's farve
        CategoryDTO create = new CategoryDTO("Create category", "", "#F2994A");
        categories.add(create);
        categories.addAll(categoryDTOS);
    }

    public void addLibraryLCLogicListener(OnLibraryLCLogicListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (OnLibraryLCLogicListener listener : listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updateLibraryListCategory(this);
        }
    }

    public interface OnLibraryLCLogicListener {
        void updateLibraryListCategory(LibraryCategoryLogic libraryCategoryLogic);
    }

    public void fragmentJump (String categoryName, NavController navController, Fragment fragment, SharedPreferences prefs) {
        prefs.edit().putString("Category", categoryName).apply();
        navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
    }

    public void updateCategory(CategoryDTO oldCategoryDTO, CategoryDTO newCategoryDTO){
        //updates the database
        categoryDAO.updateName(oldCategoryDTO, newCategoryDTO);

        //updates the list
        for (CategoryDTO dto:categories) {
            if (oldCategoryDTO.getCategoryName().equals(dto.getCategoryName())){
                dto.setCategoryName(newCategoryDTO.getCategoryName());
            }
        }
    }

}
