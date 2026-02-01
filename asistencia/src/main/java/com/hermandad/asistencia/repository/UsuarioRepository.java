package com.hermandad.asistencia.repository;

import com.hermandad.asistencia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Este método nos servirá para buscar usuarios por su nombre al loguearse
    Optional<Usuario> findByUsername(String username);
}
