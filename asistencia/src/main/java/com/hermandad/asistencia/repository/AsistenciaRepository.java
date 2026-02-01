package com.hermandad.asistencia.repository;

import com.hermandad.asistencia.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
}
