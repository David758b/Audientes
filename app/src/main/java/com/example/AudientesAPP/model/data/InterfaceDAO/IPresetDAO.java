package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.model.DTO.PresetDTO;

import java.util.List;

public interface IPresetDAO {
    void add(PresetDTO presetDTO);


    void delete(PresetDTO presetDTO);

    List<PresetDTO> getList();
}
