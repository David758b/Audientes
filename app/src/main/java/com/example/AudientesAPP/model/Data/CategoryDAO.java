package com.example.AudientesAPP.model.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.context.Context;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO{
    private Context context;
    private ContentValues row;
    private SQLiteDatabase db;
    private String categoryName;
    private CategoryDTO categoryDTO;

    /**
     * Constructor of the CategoryDAO which initialize the relevant attributes of the class.
     * @param context
     */
    public CategoryDAO(Context context) {
        this.context = context;
        db = context.getActivity().getDB();

    }

    /**
     * Adds a specific object to the database
     * @param object you wish to save in the database
     */
    @Override
    public void add(Object object) {

        categoryDTO = (CategoryDTO) object;
        categoryName = categoryDTO.getCategoryName();

        row = new ContentValues();

        row.put(SoundDB.CATEGORY_NAME,categoryName);

        db.insert(SoundDB.TABEL_Category,null,row);


    }


    /**
     * Deletes a row containing a category name in the database
     * @param object which you want to delete
     */
    @Override
    public void delete(Object object) {

        categoryDTO = (CategoryDTO) object;
        categoryName = categoryDTO.getCategoryName();

        db.delete(SoundDB.TABEL_Category,SoundDB.CATEGORY_NAME + " = '" + categoryName + "'",null);
    }


    /**
     * This method is for retrieving a list of all the categories
     * @return a list of CategoryDTO objects
     */
    @Override
    public List<Object> getList() {

        List<Object> categoryDTOList = new ArrayList<>();

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
