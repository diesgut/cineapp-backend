package com.mitocode.service;

import com.mitocode.model.Rol;
import java.util.List;

public interface IRolService {

    Rol registrar(Rol t);

    Rol modificar(Rol t);

    List<Rol> listar();

    Rol leer(Integer id);

    void eliminar(Integer id);

}
