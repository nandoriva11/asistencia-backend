package com.hermandad.asistencia.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dniPersona;

    private LocalDateTime fechaRegistro;

    public Asistencia() {
    }

    public Asistencia(String dniPersona, LocalDateTime fechaRegistro) {
        this.dniPersona = dniPersona;
        this.fechaRegistro = fechaRegistro;
    }

    public Asistencia(Long id, String dniPersona, LocalDateTime fechaRegistro) {
        this.id = id;
        this.dniPersona = dniPersona;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(String dniPersona) {
        this.dniPersona = dniPersona;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
