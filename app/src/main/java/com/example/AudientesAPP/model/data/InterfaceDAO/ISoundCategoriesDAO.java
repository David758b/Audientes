package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;

import java.util.List;

public interface ISoundCategoriesDAO {
    void add(SoundCategoriesDTO soundCategoriesDTO);


    void delete(SoundCategoriesDTO soundCategoriesDTO);

    List<SoundCategoriesDTO> getList();
}
