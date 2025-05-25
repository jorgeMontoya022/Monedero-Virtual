package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

public class Transferencia  extends Transaccion  {

    private Cuenta cuentaRecibe;

    public Transferencia(String idTransaccion, LocalDate fechaTransaccion, double monto, String descripcion, Cuenta cuenta, Cuenta cuentaRecibe, Monedero monedero) {
        super(idTransaccion, fechaTransaccion, monto, descripcion, cuenta, monedero);
        this.cuentaRecibe = cuentaRecibe;
    }

    public Cuenta getCuentaRecibe() {
        return cuentaRecibe;
    }

    public void setCuentaRecibe(Cuenta cuentaRecibe) {
        this.cuentaRecibe = cuentaRecibe;
    }
    

    

}
