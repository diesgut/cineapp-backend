package com.mitocode.service;

import com.mitocode.model.Usuario;
import java.util.List;

public interface IUsuarioService {

    Usuario registrarTransaccional(Usuario us);

    List<Usuario> listar();

    Usuario leer(Integer id);

    Usuario asignarRolUsuario(Usuario usuarioForm);
}
