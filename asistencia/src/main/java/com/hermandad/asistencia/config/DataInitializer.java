package com.hermandad.asistencia.config;

import com.hermandad.asistencia.model.Usuario;
import com.hermandad.asistencia.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyectamos las dependencias necesarias
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Preguntamos: ¿Hay alguien en la base de datos?
        if (usuarioRepository.count() == 0) {

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setRol("ADMIN");

            // ¡AQUÍ ESTÁ LA MAGIA!
            // Encriptamos la contraseña "hsmv1948@dmin2025" antes de guardarla
            admin.setPassword(passwordEncoder.encode("hsmv1948@dmin2025"));

            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN creado exitosamente");
        } else {
            System.out.println("Ya existe usuarios, no es necesario crear el admin.");
        }
    }
}
