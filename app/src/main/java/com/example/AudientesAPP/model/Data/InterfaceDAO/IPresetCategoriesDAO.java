package com.example.AudientesAPP.model.Data.InterfaceDAO;

import com.example.AudientesAPP.model.Data.InterfaceDAO.IDAO;

import java.util.List;

public interface IPresetCategoriesDAO extends IDAO {
    void add (Object object);
    void delete (Object object);
    List<Object> getList();
}
