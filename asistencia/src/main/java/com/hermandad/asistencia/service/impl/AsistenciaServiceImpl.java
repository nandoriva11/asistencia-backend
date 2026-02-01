package com.hermandad.asistencia.service.impl;

import com.hermandad.asistencia.dto.AsistenciaResponse;
import com.hermandad.asistencia.model.Asistencia;
import com.hermandad.asistencia.model.Persona;
import com.hermandad.asistencia.repository.AsistenciaRepository;
import com.hermandad.asistencia.repository.PersonaRepository;
import com.hermandad.asistencia.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public AsistenciaResponse registrarAsistencia(String dni) {
        //Buscamos si la persona existe
        Persona persona = personaRepository.findById(dni).orElse(null);

        if (persona != null) {
            Asistencia nueva = new Asistencia(dni, LocalDateTime.now());
            asistenciaRepository.save(nueva);

            // 2. CREAMOS LA RESPUESTA CON LOS DATOS DE LA PERSONA
            // Aqui juntamos Nombre + Apellido
            String nombreCompleto = persona.getNombre() + " " + persona.getApellido();

            // Retornamos el "Recibo Detallado"
            return new AsistenciaResponse(
                    nombreCompleto,
                    persona.getCuadrilla(),
                    nueva.getFechaRegistro()
            );
        }
        return null;
    }

    @Override
    public List<AsistenciaResponse> listarAsistencias() {
        // 1. Obtenemos la lista cruda de la BD
        List<Asistencia> todasLasAsistencias = asistenciaRepository.findAll();

        // 2. Perparamos una lista vacia para guardas las respuestas que queremos mostrar
        List<AsistenciaResponse> respuestas = new ArrayList<>();

        // 3. Recorremos cada asistencia una por una
        for (Asistencia asistencia : todasLasAsistencias) {

            // --- TU CÓDIGO VA AQUÍ ---

            String dni = asistencia.getDniPersona();

            // Paso A: Obtén el DNI de esta asistencia específica (asistencia.getDniPersona())
            asistencia.getDniPersona();

            // Paso B: Busca a la Persona en el repositorio (¡Igual que en registrar!)
            Persona persona = personaRepository.findById(dni).orElse(null);

            // Paso C: Si la persona existe (!= null), crea un 'new AsistenciaResponse(...)'
            //         y agrégalo a la lista 'respuestas'.

            if (persona != null) {
                asistencia.getFechaRegistro();

                // 2. CREAMOS LA RESPUESTA CON LOS DATOS DE LA PERSONA
                // Aqui juntamos Nombre + Apellido
                String nombreCompleto = persona.getNombre() + " " + persona.getApellido();

                // Retornamos el "Recibo Detallado"
                respuestas.add(new AsistenciaResponse(
                        nombreCompleto,
                        persona.getCuadrilla(),
                        asistencia.getFechaRegistro()
                ));
            }
        }
        return respuestas;
    }
}