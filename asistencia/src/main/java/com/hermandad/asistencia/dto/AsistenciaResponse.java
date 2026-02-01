package com.hermandad.asistencia.dto;

import java.time.LocalDateTime;

public class AsistenciaResponse {

    // Estos son los datos que verá el usuario en la pantalla

    private String nombreCompleto;
    private String cuadrilla;
    private LocalDateTime fechaHora;

    public AsistenciaResponse() {
    }

    public AsistenciaResponse(String nombreCompleto, String cuadrilla, LocalDateTime fechaHora) {
        this.nombreCompleto = nombreCompleto;
        this.cuadrilla = cuadrilla;
        this.fechaHora = fechaHora;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
