package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

import co.edu.uniquindio.monedero_virtual.model.enums.Beneficio;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;

public class PuntosCliente {

    private Cliente cliente;
    private int puntosAcumulados;
    private Beneficio beneficioActivo;
    private LocalDate fechaDeActivacion;


    public PuntosCliente(Cliente cliente) {
        this.puntosAcumulados = 0;
        this.cliente = cliente;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public int calcularPuntosTransaccion(Transaccion transaccion) {
        double monto = transaccion.getMonto();
        int puntos = 0;

        if(transaccion instanceof Deposito) {
            puntos = (int) (monto/100);

        }else if(transaccion instanceof Retiro) {
            puntos = (int) (monto/100) *2;
        }else if (transaccion instanceof Transferencia) {
            puntos = (int)(monto/100) *3;
        }

        return puntos;
    }

    public void añadirPuntos(Transaccion transaccion){
        int puntos = calcularPuntosTransaccion(transaccion);
        puntosAcumulados += puntos;
    }

    public void eliminarPuntos(Transaccion transaccion){
        int puntos = calcularPuntosTransaccion(transaccion);
        puntosAcumulados -= puntos;
    }

    public boolean consumirPuntos (int puntos){
        if (puntos > 0 && puntosAcumulados >= puntos){
            this.puntosAcumulados -= puntos;
            cliente.setTipoRango(TipoRango.obtenerRango(this.puntosAcumulados));
            return true;
        }
        return false;
        
    }

    public Beneficio getBeneficioActivo() {
        return beneficioActivo;
    }

    public void setBeneficioActivo(Beneficio beneficioActivo) {
        this.beneficioActivo = beneficioActivo;
    }

    public boolean canjearPuntos (Beneficio beneficio){
        int puntosRequeridos;

        switch (beneficio) {
            case REDUCCION_COMISION:
                puntosRequeridos = 100;
                break;
            case MES_LIBRE_RETIROS:
                puntosRequeridos = 500;
                break;
            case BONO_SALDO:
                puntosRequeridos = 1000;
                break;
            default:
                return false;
        }
        if (consumirPuntos(puntosRequeridos)){
            beneficioActivo = beneficio;
            fechaDeActivacion = LocalDate.now();
            return true;
        }
        return false;
    }

    public void setFechaDeActivacion(LocalDate fechaDeActivacion) {
        this.fechaDeActivacion = fechaDeActivacion;
    }


    


    

    
}
