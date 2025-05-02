package co.edu.uniquindio.monedero_virtual.model;

public class Monedero {
    private int id;
    private double monto;
    private String nombreMonedero;


    public Monedero(int id, double monto, String nombreMonedero) {
        this.id = id;
        this.monto = monto;
        this.nombreMonedero = nombreMonedero;
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

    

    
}
