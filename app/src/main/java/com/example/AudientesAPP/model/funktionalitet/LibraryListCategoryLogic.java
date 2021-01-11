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

    public LibraryListCategoryLogic (Controller controller, Context context) {
        this.context = context;
        this.categoryDAO = new CategoryDAO(controller);
        this.listeners = new ArrayList<>();
    }

    public void addCategory(String categoryName) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        categoryDAO.add(categoryDTO);
        System.out.println("-------------------ADD CATEGORY------------------");
        notifyListeners();
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
