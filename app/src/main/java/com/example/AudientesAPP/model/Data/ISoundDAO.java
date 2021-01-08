package com.example.AudientesAPP.model.Data;

import java.util.List;

public interface ISoundDAO extends IDAO {
    void add (Object object);
    void delete (Object object);
    List<Object> getList();
    Object getSound(String soundName);
}
