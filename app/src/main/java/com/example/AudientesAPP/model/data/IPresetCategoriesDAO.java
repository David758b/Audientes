package com.example.AudientesAPP.model.data;

import java.util.List;

public interface IPresetCategoriesDAO extends IDAO {
    void add (Object object);
    void delete (Object object);
    List<Object> getList();

    List<Object> getPresetCategory(Object CategoryDTO);
    List<Object> getCategoryPreset(Object PresetDTO);
}
