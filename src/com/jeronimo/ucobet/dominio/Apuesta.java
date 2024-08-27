package com.jeronimo.ucobet.dominio;

public class Apuesta {
    private String numeroApostado;
    private double monto;
    private boolean resultado;

    public Apuesta(String numeroApostado, long monto) {
        this.numeroApostado = numeroApostado;
        this.monto = monto;
        this.resultado = false;
    }


    public String getNumeroApostado() {
        return numeroApostado;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Apuesta{" +
                "numeroApostado='" + numeroApostado + '\'' +
                ", monto=" + monto +
                '}';
    }
}
