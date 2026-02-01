package com.hermandad.asistencia.service;

import com.hermandad.asistencia.dto.AsistenciaResponse;
import com.hermandad.asistencia.model.Asistencia;

import java.util.List;

public interface AsistenciaService {

    AsistenciaResponse registrarAsistencia(String dni);

    List<AsistenciaResponse> listarAsistencias();
}
