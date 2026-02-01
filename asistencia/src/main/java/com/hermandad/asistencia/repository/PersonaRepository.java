package com.hermandad.asistencia.repository;

import com.hermandad.asistencia.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, String> {
}
