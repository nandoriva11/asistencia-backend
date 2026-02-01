package com.hermandad.asistencia.controller;

import com.hermandad.asistencia.dto.LoginRequest;
import com.hermandad.asistencia.model.Usuario;
import com.hermandad.asistencia.service.DetalleUsuarioService;
import com.hermandad.asistencia.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final DetalleUsuarioService detalleUsuarioService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationController(JwtService jwtService, DetalleUsuarioService detalleUsuarioService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.detalleUsuarioService = detalleUsuarioService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        // 1. Autenticar (usando el authenticationManager y los datos del loginRequest)

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // 2. Cargar los datos del usuario (usando detalleUsuarioService.loadUserByUsername)
        // Crea una variable tipo UserDetails para guardarlo.
        UserDetails userDetails = detalleUsuarioService.loadUserByUsername(loginRequest.getUsername());

        // 3. Generar el Token (usando jwtService.generateToken) y retornarlo
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
