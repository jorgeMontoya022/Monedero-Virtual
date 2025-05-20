package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

public class Retiro extends Transaccion{

    private double limiteRetiro;
    private double comision;

    public Retiro(int idTransaccion, LocalDate fechaTransaccion, double monto, String descripcion, Cuenta cuenta, double limiteRetiro, double comision, Monedero monedero) {
        super(idTransaccion, fechaTransaccion, monto, descripcion, cuenta, monedero);
        this.comision = comision;
        this.limiteRetiro = monedero.getMonto();
    }

    public double getLimiteRetiro() {
        return limiteRetiro;
    }

    public void setLimiteRetiro(double limiteRetiro) {
        this.limiteRetiro = limiteRetiro;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    
   


    
}
