package com.example.AudientesAPP.model.data.InterfaceDAO;

import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public interface IDAO<T> {

    void add (T object);
    void delete (T object);
    List<T> getList();
}
