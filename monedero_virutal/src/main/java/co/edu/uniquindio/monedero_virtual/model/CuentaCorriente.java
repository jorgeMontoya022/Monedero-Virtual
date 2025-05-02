package co.edu.uniquindio.monedero_virtual.model;

public class CuentaCorriente extends Cuenta {

    private double limiteSobreGiro;

    public CuentaCorriente(double monto, String banco, int numeroCuenta, Cliente clienteAsociado, double limiteSobreGiro) {
        super(monto, banco, numeroCuenta, clienteAsociado);
        this.limiteSobreGiro = limiteSobreGiro;
      
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public void setLimiteSobreGiro(double limiteSobreGiro) {
        this.limiteSobreGiro = limiteSobreGiro;
    }

    
}
