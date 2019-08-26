package com.mitocode.service.impl;

import com.mitocode.model.Pelicula;
import com.mitocode.model.Rol;
import com.mitocode.repo.IRolRepo;
import com.mitocode.service.IRolService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepo repo;

    @Override
    public Rol registrar(Rol t) {
        return repo.save(t);
    }

    @Override
    public Rol modificar(Rol t) {
        return repo.save(t);
    }

    @Override
    public List<Rol> listar() {
        return repo.findAll();
    }

    @Override
    public Rol leer(Integer id) {
        Optional<Rol> op = repo.findById(id);
        return op.isPresent() ? op.get() : new Rol();
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

}
