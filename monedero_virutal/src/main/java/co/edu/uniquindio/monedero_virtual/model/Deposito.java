package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

public class Deposito extends Transaccion{

    public Deposito(int idTransaccion, LocalDate fechaTransaccion, double monto, String descripcion, Cuenta cuenta, Monedero monedero) {
        super(idTransaccion, fechaTransaccion, monto, descripcion, cuenta, monedero);
       
    }
    
}
