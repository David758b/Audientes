package com.example.AudientesAPP.model.Data;

import com.example.AudientesAPP.DTO.CategoryDTO;

import java.util.List;

public interface ICategoryDAO extends IDAO {
    void add (Object object);
    void delete (Object object);
    List<Object> getList();
}
