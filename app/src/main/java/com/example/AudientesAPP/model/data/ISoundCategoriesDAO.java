package com.example.AudientesAPP.model.data;

import java.util.List;

public interface ISoundCategoriesDAO extends IDAO {
    void add (Object object);
    void delete (Object object);
    List<Object> getList();

    List<Object> getCategorySound(Object SoundDTO);
    List<Object> getSoundCategory(Object CategoryDTO);
}
