package com.example.AudientesAPP.model.funktionalitet;

import android.content.Context;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.Controller;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class LibraryListCategoryLogic {
    private CategoryDAO categoryDAO;
    private Context context;
    List<OnLibraryLCLogicListener> listeners;
    private final List<String> categoryNames;

    public LibraryListCategoryLogic (Controller controller, Context context) {
        this.context = context;
        this.categoryDAO = new CategoryDAO(controller);
        this.listeners = new ArrayList<>();
        this.categoryNames = new ArrayList<>();
        initCategoryNames();
    }

    public void addCategory(String categoryName) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        categoryDAO.add(categoryDTO);
        //Måske få listen fra DAO og kald initCategoryNames istedet for linje 30
        categoryNames.add(categoryDTO.getCategoryName());
        System.out.println("-------------------ADD CATEGORY------------------");
        notifyListeners();
    }

    //Burde returnere en liste af CategoryDTO's, hvis vi også skal have farve og billede reference med.
    public List<String> getCategoryNames () {
        return categoryNames;
    }

    //Hvis init skal køres fra fragmentet og ikke konstruktøren i denne klasse skal den være public og slettes fra konstruktøren
    private void initCategoryNames(){
        //Hvis den indeholder noget så clearer vi den
        categoryNames.clear();
        List<CategoryDTO> categoryDTOS = categoryDAO.getList();
        categoryNames.add("Create category");
        for (CategoryDTO a: categoryDTOS) {
            categoryNames.add(a.getCategoryName());
        }
    }

    public void addLibraryLCLogicListener(OnLibraryLCLogicListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(){
        System.out.println("222222222222222222222222222");
        for (OnLibraryLCLogicListener listener: listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updateLibraryListCategory(this);
        }
    }

    public interface OnLibraryLCLogicListener{
        void updateLibraryListCategory(LibraryListCategoryLogic libraryListCategoryLogic);
    }

}
