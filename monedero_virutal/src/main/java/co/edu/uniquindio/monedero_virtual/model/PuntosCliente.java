package co.edu.uniquindio.monedero_virtual.model;

public class PuntosCliente {

    private Cliente cliente;
    private int puntosAcumulados;


    public PuntosCliente(Cliente cliente) {
        this.puntosAcumulados = 0;
        this.cliente = cliente;
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
    

    
}
