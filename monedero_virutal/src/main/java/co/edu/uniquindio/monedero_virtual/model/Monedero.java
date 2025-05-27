package co.edu.uniquindio.monedero_virtual.model;

public class Monedero {
    private int id;
    private double monto;
    private String nombreMonedero;
    private Cuenta cuenta;


    public Monedero(int id, double monto, String nombreMonedero, Cuenta cuenta) {
        this.id = id;
        this.monto = monto;
        this.nombreMonedero = nombreMonedero;
        this.cuenta = cuenta;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public double getMonto() {
        return monto;
    }


    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getNombreMonedero() {
        return nombreMonedero;
    }


    public void setNombreMonedero(String nombreMonedero) {
        this.nombreMonedero = nombreMonedero;
    }


    public Cuenta getCuenta() {
        return cuenta;
    }


    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void agregarDinero(double cantidadAgregar){
        monto += cantidadAgregar;
    }

    public void retirarDinero(double cantidadRetirar)throws Exception{
        if(cantidadRetirar > monto){
            throw new Exception("Error al retirar dinero del monedero: Saldos insuficientes");
        }
        monto -= cantidadRetirar;
    }


    @Override
    public String toString() {
        return nombreMonedero + " - Monto: " + monto;
    }

    
}
