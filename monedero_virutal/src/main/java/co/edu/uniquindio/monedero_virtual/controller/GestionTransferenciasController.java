package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.Transferencia;

public class GestionTransferenciasController {
    ModelFactory modelFactory;

    public GestionTransferenciasController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public boolean realizarTransferencia(Transferencia transferencia){
        return modelFactory.realizarTransferencia(transferencia);
    }

    public List<Transaccion> getTransferenciasCliente(int idCliente){
        return modelFactory.getTransferenciasCliente(idCliente);
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        return modelFactory.getCuentasUsuario(idCliente);
    }

    public List<Monedero> getMonederosCliente(int idCliente) {
        return modelFactory.getMonederosCliente(idCliente);
    }

    public Cuenta buscarCuenta(int idCuenta) {
        return modelFactory.buscarCuenta(idCuenta);
    }

    public boolean revertirTransferencia(Cuenta cuenta) {
       return modelFactory.revertirTransferencia(cuenta);
    }

    public Transferencia obtenerUltimaTransferencia(Cuenta cuentaSeleccionada) {
        return modelFactory.obtenerUltimaTransferencia(cuentaSeleccionada);
    }

}
