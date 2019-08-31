package com.mitocode.controller;

import com.mitocode.exception.ModeloNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Menu;
import com.mitocode.service.IMenuService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private IMenuService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> listar() {
        List<Menu> menues = new ArrayList<>();
        menues = service.listar();
        return new ResponseEntity<List<Menu>>(menues, HttpStatus.OK);
    }

    @PostMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> listar(@RequestBody String nombre) {
        List<Menu> menues = new ArrayList<>();
        menues = service.listarMenuPorUsuario(nombre);
        return new ResponseEntity<List<Menu>>(menues, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> leer(@PathVariable("id") Integer idMenu) {
        Menu obj = service.leer(idMenu);
        if (obj.getIdMenu() == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idMenu);
        }
        return new ResponseEntity<Menu>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "/hateoas/{id}")
    public Resource<Menu> listarPorId(@PathVariable("id") Integer id) {

        Menu menu = service.leer(id);
        if (menu.getIdMenu() == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
        }

        Resource<Menu> resource = new Resource<Menu>(menu);
        // /peliculas/{4}
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
        resource.add(linkTo.withRel("pelicula-resource"));

        return resource;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> registrar(@Valid @RequestBody Menu menu) {
        Menu pel = service.registrar(menu);
        //localhost:8080/peliculas/1
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pel.getIdMenu()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Menu> modificar(@Valid @RequestBody Menu menu) {
        Menu obj = service.modificar(menu);
        return new ResponseEntity<Menu>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer idMenu) {
        service.eliminar(idMenu);
    }

    @PutMapping(value = "/asignarroles", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Menu> asignarRoles(@RequestBody Menu menuForm) {
        Menu menu = service.asignarMenuRol(menuForm);
        return new ResponseEntity<Menu>(menu, HttpStatus.OK);
    }

}
