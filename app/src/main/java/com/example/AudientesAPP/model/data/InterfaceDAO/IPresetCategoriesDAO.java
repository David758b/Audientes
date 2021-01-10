package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.DTO.PresetCategoriesDTO;

import java.util.List;

public interface IPresetCategoriesDAO {
    void add(PresetCategoriesDTO presetCategoriesDTO);


    void delete(PresetCategoriesDTO presetCategoriesDTO);

    List<PresetCategoriesDTO> getList();
}
