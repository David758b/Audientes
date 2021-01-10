package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.DTO.CategoryDTO;

import java.util.List;

public interface ICategoryDAO {

    void add(CategoryDTO categoryDTO);

    void delete(CategoryDTO categoryDTO);

    List<CategoryDTO> getList();
}
