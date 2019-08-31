package com.mitocode.controller;

import com.mitocode.exception.ModeloNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @PostMapping(produces = "application/json", consumes = "application/json")
    private ResponseEntity<Object> registrar(@RequestBody Usuario usuario) {
        usuario.setPassword(bcrypt.encode(usuario.getPassword()));
        service.registrarTransaccional(usuario);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = service.listar();
        return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> leer(@PathVariable("id") Integer idUsuario) {
        Usuario obj = service.leer(idUsuario);
        if (obj.getIdUsuario() == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idUsuario);
        }
        return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
    }

    @PutMapping(value = "/asignarroles", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Usuario> asignarRoles(@RequestBody Usuario usuarioForm) {
        Usuario usuario = service.asignarRolUsuario(usuarioForm);
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

}
