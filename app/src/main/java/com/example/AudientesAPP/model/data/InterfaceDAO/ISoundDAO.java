package com.example.AudientesAPP.model.data.InterfaceDAO;

import com.example.AudientesAPP.model.DTO.SoundDTO;

import java.util.List;

public interface ISoundDAO{

    void add(SoundDTO soundDTO);


    void delete(SoundDTO soundDTO);

    List<SoundDTO> getList();
}
