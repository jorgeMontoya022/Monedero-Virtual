package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

public class Transaccion implements Comparable<Transaccion>{
    private String idTransaccion;
    private LocalDate fechaTransaccion;
    private double monto;
    private String descripcion;
    private Cuenta cuenta;
    private Monedero monedero;



    public Transaccion(String idTransaccion, LocalDate fechaTransaccion, double monto, String descripcion, Cuenta cuenta, Monedero monedero) {
        this.idTransaccion = idTransaccion;
        this.fechaTransaccion = fechaTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuenta = cuenta;
        this.monedero = monedero;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }


    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }


    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }


    public void setFechaTransaccion(LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }


    public double getMonto() {
        return monto;
    }


    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Cuenta getCuenta() {
        return cuenta;
    }


    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }


    public Monedero getMonedero() {
        return monedero;
    }


    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }


   @Override
    public int compareTo(Transaccion otra) {
            return this.fechaTransaccion.compareTo(otra.getFechaTransaccion());
    }

   @Override
   public String toString() {
    return "Transaccion [idTransaccion=" + idTransaccion + ", fechaTransaccion=" + fechaTransaccion + ", monto=" + monto
            + ", descripcion=" + descripcion + ", cuenta=" + cuenta + ", monedero=" + monedero + "]";
   }


}
