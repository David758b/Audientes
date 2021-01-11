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
    List<OnLibraryLCLogicListener> listeners;

    public LibraryListCategoryLogic (Context context) {
        this.context = context;
        this.categoryDAO = new CategoryDAO(context);
        this.listeners = new ArrayList<>();
    }

    public void addCategory(String categoryName) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        categoryDAO.add(categoryDTO);
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
        for (OnLibraryLCLogicListener listener: listeners) {
            System.out.println("----------------NOTIFY LISTENERS----------------");
            listener.updateLibraryListCategory(this);
        }
    }

    public interface OnLibraryLCLogicListener{
        void updateLibraryListCategory(LibraryListCategoryLogic libraryListCategoryLogic);
    }

}
