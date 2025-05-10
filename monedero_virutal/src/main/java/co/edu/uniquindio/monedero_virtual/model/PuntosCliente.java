package co.edu.uniquindio.monedero_virtual.model;

public class PuntosCliente {

    private Cliente cliente;
    private int puntosAcumulados;


    public PuntosCliente(Cliente cliente) {
        this.puntosAcumulados = cliente.getPuntos();
        this.cliente = cliente;
    }

    public void calcularPuntosTransaccion(Transaccion transaccion) {
        double monto = transaccion.getMonto();
        int puntos = 0;

        if(transaccion instanceof Deposito) {
            puntos = (int) (monto/100);

        }else if(transaccion instanceof Retiro) {
            puntos = (int) (monto/100) *2;
        }else if (transaccion instanceof Transferencia) {
            puntos = (int)(monto/100) *3;
            
        }
    }

    
}
