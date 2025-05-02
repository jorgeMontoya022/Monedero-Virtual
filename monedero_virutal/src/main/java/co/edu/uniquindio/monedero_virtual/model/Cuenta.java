package co.edu.uniquindio.monedero_virtual.model;

public class Cuenta {
    private double monto;
    private String banco;
    private int numeroCuenta;
    private Cliente clienteAsociado;
    //lista de transacciones(por definir)


    public Cuenta(double monto, String banco, int numeroCuenta, Cliente clienteAsociado) {
        this.monto = monto;
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.clienteAsociado = clienteAsociado;
    }


    public double getMonto() {
        return monto;
    }


    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getBanco() {
        return banco;
    }


    public void setBanco(String banco) {
        this.banco = banco;
    }


    public int getNumeroCuenta() {
        return numeroCuenta;
    }


    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    public Cliente getClienteAsociado() {
        return clienteAsociado;
    }


    public void setClienteAsociado(Cliente clienteAsociado) {
        this.clienteAsociado = clienteAsociado;
    }

    

}
