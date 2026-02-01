package com.hermandad.asistencia.service;

import com.hermandad.asistencia.model.Usuario;
import com.hermandad.asistencia.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DetalleUsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DetalleUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en TU base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Traducimos TU usuario a un usuario de SPRING SECURITY
        // (Spring necesita saber: username, password y roles)
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.singletonList( new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
    }
}
