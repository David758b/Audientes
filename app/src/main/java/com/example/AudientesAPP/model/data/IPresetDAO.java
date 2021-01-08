package com.example.AudientesAPP.model.data;

import java.util.List;

public interface IPresetDAO extends IDAO{
    void add (Object object);
    void delete (Object object);
    List<Object> getList();
}
