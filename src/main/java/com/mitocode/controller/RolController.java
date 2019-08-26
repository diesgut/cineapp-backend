package com.mitocode.controller;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Pelicula;
import com.mitocode.model.Rol;
import com.mitocode.service.IRolService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    IRolService service;

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> lista = service.listar();
        return new ResponseEntity<List<Rol>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> leer(@PathVariable("id") Integer idPelicula) {
        Rol obj = service.leer(idPelicula);
        if (obj.getIdRol() == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idPelicula);
        }
        return new ResponseEntity<Rol>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "/hateoas/{id}")
    public Resource<Rol> listarPorId(@PathVariable("id") Integer id) {

        Rol pel = service.leer(id);
        if (pel.getIdRol() == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
        }

        Resource<Rol> resource = new Resource<Rol>(pel);
        // /peliculas/{4}
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
        resource.add(linkTo.withRel("pelicula-resource"));

        return resource;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> registrar(@Valid @RequestBody Rol rol) {
        Rol pel = service.registrar(rol);
        //localhost:8080/peliculas/1
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pel.getIdRol()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Rol> modificar(@Valid @RequestBody Rol pel) {
        Rol obj = service.modificar(pel);
        return new ResponseEntity<Rol>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer idPelicula) {
        service.eliminar(idPelicula);
    }

}
