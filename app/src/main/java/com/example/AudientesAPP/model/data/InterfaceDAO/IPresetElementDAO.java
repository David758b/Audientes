package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.DTO.PresetElementDTO;

import java.util.List;

public interface IPresetElementDAO {
    void add(PresetElementDTO presetElementDTO);


    void delete(PresetElementDTO presetElementDTO);

    List<PresetElementDTO> getList();
}
