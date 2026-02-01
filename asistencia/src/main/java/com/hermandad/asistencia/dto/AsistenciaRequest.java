package com.hermandad.asistencia.dto;

public class AsistenciaRequest {
    // Esta variable debe llamarse IGUAL que en el JSON: {"dni": "..."}
    private String dni;

    public AsistenciaRequest() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
