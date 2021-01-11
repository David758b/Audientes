package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.Controller;
import com.example.AudientesAPP.model.data.InterfaceDAO.ICategoryDAO;
import com.example.AudientesAPP.model.data.SoundDB;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {
    private Controller controller;
    private ContentValues row;
    private SQLiteDatabase db;
    private String categoryName;
    private CategoryDTO categoryDTO;
    private List<CategoryDTO> categoryDTOList;

    /**
     * Constructor of the CategoryDAO which initialize the relevant attributes of the class.
     * @param controller
     */
    public CategoryDAO(Controller controller) {
        this.controller = controller;
        db = controller.getActivity().getDB();

    }

    /**
     * Adds a specific object to the database
     * @param categoryDTO you wish to save in the database
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        categoryName = categoryDTO.getCategoryName();

        row = new ContentValues();

        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_Category,null,row);


    }


    /**
     * Deletes a row containing the object in the database
     * @param categoryDTO which you want to delete
     */
    @Override
    public void delete(CategoryDTO categoryDTO) {

        categoryName = categoryDTO.getCategoryName();

        db.delete(SoundDB.TABEL_Category,SoundDB.CATEGORY_NAME + " = '" + categoryName + "'",null);
    }


    /**
     * This method is for retrieving a list of all the categories
     * @return a list of CategoryDTO objects
     */
    @Override
    public List<CategoryDTO> getList() {

        categoryDTOList = new ArrayList<>();

        String[] column = {SoundDB.CATEGORY_NAME};

        Cursor cursor = db.query(SoundDB.TABEL_Category,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            CategoryDTO categoryDTO = new CategoryDTO(name);
            categoryDTOList.add(categoryDTO);
        }

        cursor.close();

        return categoryDTOList;
    }
}
