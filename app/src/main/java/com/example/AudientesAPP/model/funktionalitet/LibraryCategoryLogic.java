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
import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;

import java.util.ArrayList;
import java.util.List;

/*
 * To seperate the data that the views use from the actual views
 * We use this class between the view and data
 * */
public class LibraryCategoryLogic {
    private CategoryDAO categoryDAO;
    List<OnLibraryLCLogicListener> listeners;
    private CategorySoundsLogic categorySoundsLogic;
    /*
     * final list of CategoryDTO's, this list gets cleared everytime the constructor gets called
     * and gets filled up with data from the database
     * By having this list seperate we decide when theres a need to fetch data from the database.
     * If wanted we can save an instance of this list in the view, as long as we don't handle/create it there.
     * The recyclerviewAdapter that is made to only and always expect one list.
     * */
    private final List<CategoryDTO> categories;

    public LibraryCategoryLogic(CategoryDAO categoryDAO, CategorySoundsLogic categorySoundsLogic) {
        this.categoryDAO = categoryDAO;
        this.listeners = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.categorySoundsLogic = categorySoundsLogic;
        initCategories();
    }

    //Burde returnere en liste af CategoryDTO's, hvis vi også skal have farve og billede reference med.
    //Hvis init skal køres fra fragmentet og ikke konstruktøren i denne klasse skal den være public og slettes fra konstruktøren
    private void initCategories() {
        //Hvis den indeholder noget så clearer vi den
        categories.clear();

        List<CategoryDTO> categoryDTOS = categoryDAO.getList();

        //tilføj create category's farve
        CategoryDTO create = new CategoryDTO("Create category", "", "#F2994A");
        categories.add(create);
        CategoryDTO preset = new CategoryDTO("Presets", "", "#3F51B5");
        categories.add(preset);
        categories.addAll(categoryDTOS);
    }

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

    public void addCategory(String categoryName, String picture, String color) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryName, picture, color);
        categoryDAO.add(categoryDTO);
        //Måske få listen fra DAO og kald initCategoryNames istedet for linje 30
        categories.add(categoryDTO);
        notifyListeners();
    }


    /**
     * Updates the category name to a another given category name
     * @param oldCategoryName The current name of the category
     * @param newCategoryName The desired name of the category
     */
    // Note to self: checks for difference in names should happen in the class that calls the method
    public void updateCategory(String oldCategoryName, String newCategoryName){
        CategoryDTO categoryOldDTO = getCategory(oldCategoryName);
        CategoryDTO categoryNewDTO = new CategoryDTO(categoryOldDTO.getCategoryName(), categoryOldDTO.getPicture(), categoryOldDTO.getColor());
        categoryNewDTO.setCategoryName(newCategoryName);

        //updates the database
        categoryDAO.updateName(categoryOldDTO, categoryNewDTO);

        //updates the list
        for (CategoryDTO dto:categories) {
            if (categoryOldDTO.getCategoryName().equals(dto.getCategoryName())){
                dto.setCategoryName(categoryNewDTO.getCategoryName());
            }
        }
        categorySoundsLogic.initSoundCategories();
        categorySoundsLogic.setCurrentCategory(newCategoryName);
    }


    public boolean isExisting(String categoryName){
        for (CategoryDTO DTO: categories) {
            if(DTO.getCategoryName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }

    public void deleteCategory(String categoryName){
        CategoryDTO categoryDTO = new CategoryDTO(categoryName,"","");
        categoryDAO.delete(categoryDTO);

        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getCategoryName().equals(categoryName)){
                categories.remove(i);
            }
        }
        for (CategoryDTO a:categories) {
            System.out.println("------nej" +a.getCategoryName());
        }
        notifyListeners();
        categorySoundsLogic.deleteCategory(categoryName);


    }

//--------------------------------- LISTENER INTERFACE ---------------------
    public interface OnLibraryLCLogicListener {
        void updateLibraryListCategory(LibraryCategoryLogic libraryCategoryLogic);
    }

    public void addLibraryLCLogicListener(OnLibraryLCLogicListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (OnLibraryLCLogicListener listener : listeners) {
            listener.updateLibraryListCategory(this);
        }
    }

}
