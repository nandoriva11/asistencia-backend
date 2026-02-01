package com.hermandad.asistencia.controller;

import com.hermandad.asistencia.dto.AsistenciaRequest;
import com.hermandad.asistencia.dto.AsistenciaResponse;
import com.hermandad.asistencia.model.Asistencia;
import com.hermandad.asistencia.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // Botón 1
    @PostMapping("registrar")
    public ResponseEntity<?> registrar(@RequestBody AsistenciaRequest request) {

        String dniRecibido = request.getDni();

        AsistenciaResponse nueva = asistenciaService.registrarAsistencia(dniRecibido);
        System.out.println("🔍 Buscando DNI: '" + request.getDni() + "'");

        if (nueva != null) {
            return ResponseEntity.ok(nueva); // Devuelve la asistencia creada (200 ok)
        }
        // Si devuelve null, es porque el DNI no existe
        return ResponseEntity.badRequest().body("Error: El DNI no se encuentra en la BD.");
    }
    // Botón 2: VER LISTA
    // La ruta será: GET http://localhost:8080/api/lista
    @GetMapping("/lista")
    public List<AsistenciaResponse> listar() {
        return asistenciaService.listarAsistencias();
    }
}
