package com.example.AudientesAPP.model.data.InterfaceDAO;

import java.util.List;

public interface IDAO {

    void add (Object object);
    void delete (Object object);
    List<Object> getList();
}