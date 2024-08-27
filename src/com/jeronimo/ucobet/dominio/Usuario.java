package com.jeronimo.ucobet.dominio;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String identificacion;
    private String celular;
    private String correo;
    private int acierto;
    private double saldo;
    private List<Apuesta> apuestas;

    public Usuario(String nombre, String correo , String identificacion, String celular) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.celular = celular;
        this.correo = correo;
        this.acierto = 0;
        this.saldo = 0;
        this.apuestas = new ArrayList<>();
    }


    public void agregarApuesta(Apuesta apuesta){
        apuestas.add(apuesta);
    }

    public int getAcierto() {
        return acierto;
    }

    public void setAcierto() {
        this.acierto++;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Apuesta> getApuestas() {
        return apuestas;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = this.saldo + saldo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", celular='" + celular + '\'' +
                ", correo='" + correo + '\'' +
                ", acierto=" + acierto +
                ", apuestas=" + apuestas +
                '}';
    }
}
