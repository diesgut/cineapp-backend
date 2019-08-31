package com.mitocode.service.impl;

import com.mitocode.model.Rol;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Usuario;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.service.IUsuarioService;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, IUsuarioService {

    @Autowired
    private IUsuarioRepo userRepo;

    @Value("${mitocine.default-rol}")
    private Integer DEFAULT_ROL;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepo.findOneByUsername(username); //from usuario where username := username

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Usuario no existe", username));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getNombre()));
        });

        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);

        return userDetails;
    }

    @Transactional
    @Override
    public Usuario registrarTransaccional(Usuario usuario) {
        Usuario u;
        try {
            u = userRepo.save(usuario);
            userRepo.registrarRolPorDefecto(u.getIdUsuario(), DEFAULT_ROL);
        } catch (Exception e) {
            throw e;
        }

        return u;

    }

    @Override
    public List<Usuario> listar() {
        return userRepo.findAll();
    }

    @Override
    public Usuario leer(Integer id) {
        Optional<Usuario> op = userRepo.findById(id);
        return op.isPresent() ? op.get() : new Usuario();
    }

    @Transactional
    @Override
    public Usuario asignarRolUsuario(Usuario usuarioForm) {
        Optional<Usuario> oUsuarioBD = userRepo.findById(usuarioForm.getIdUsuario());
        Usuario usuarioBD = oUsuarioBD.get();
        List<Rol> rolesForm = usuarioForm.getRoles();
        usuarioBD.setRoles(rolesForm);
        return userRepo.save(usuarioBD);
    }

}
