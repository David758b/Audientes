package com.example.AudientesAPP.model.data.InterfaceDAO;

import java.util.List;

public interface IDAO<T> {

    void add (T object);
    void delete (T object);
    List<T> getList();
}
