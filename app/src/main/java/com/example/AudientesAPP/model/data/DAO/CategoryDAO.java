package com.example.AudientesAPP.model.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.InterfaceDAO.IDAO;
import com.example.AudientesAPP.model.data.SoundDB;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements IDAO<CategoryDTO> {

    private ContentValues row;
    private SQLiteDatabase db;
    private String categoryName;
    private String picture;
    private String color;
    private CategoryDTO categoryDTO;
    private List<CategoryDTO> categoryDTOList;

    /**
     * Constructor of the CategoryDAO which initialize the relevant attributes of the class.
     * @param db - den skal bare have en db ska den!
     */
    public CategoryDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Adds a specific object to the database
     * @param categoryDTO you wish to save in the database
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        categoryName = categoryDTO.getCategoryName();
        picture = categoryDTO.getPicture();
        color = categoryDTO.getColor();

        row = new ContentValues();

        row.put(SoundDB.CATEGORY_NAME,categoryName);
        row.put(SoundDB.CATEGORY_Pic,picture);
        row.put(SoundDB.CATEGORY_Color,color);

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

        String[] column = {SoundDB.CATEGORY_NAME, SoundDB.CATEGORY_Pic, SoundDB.CATEGORY_Color};

        Cursor cursor = db.query(SoundDB.TABEL_Category,column,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String pic = cursor.getString(1);
            String color = cursor.getString(2);
            CategoryDTO categoryDTO = new CategoryDTO(name, pic, color);
            categoryDTOList.add(categoryDTO);
        }

        cursor.close();

        return categoryDTOList;
    }

    public void updateName(CategoryDTO oldCategoryDTO,CategoryDTO newCategoryDTO){

        // UPDATE TABLE_CATEGORY SET CATEGORY_NAME = newName WHERE CATEGORY_NAME = oldName
        row = new ContentValues();

        row.put(SoundDB.CATEGORY_NAME,newCategoryDTO.getCategoryName());
        //row.put(SoundDB.CATEGORY_Pic,newCategoryDTO.getPicture());
        //row.put(SoundDB.CATEGORY_Color,newCategoryDTO.getColor());
        db.update(SoundDB.TABEL_SoundCategories,row,SoundDB.CATEGORY_NAME +
                " = '" + oldCategoryDTO.getCategoryName() + "'",null);


    }

}
