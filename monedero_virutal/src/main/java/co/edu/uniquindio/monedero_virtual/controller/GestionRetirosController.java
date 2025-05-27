package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Retiro;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;

public class GestionRetirosController {
    ModelFactory modelFactory;

    public GestionRetirosController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public boolean realizarRetiro(Retiro retiro){
        return modelFactory.realizarRetiro(retiro);
    }

    public List<Transaccion> getRetirosCliente(int idCliente){
        return modelFactory.getRetirosCliente(idCliente);
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        return modelFactory.getCuentasUsuario(idCliente);
    }

    public List<Monedero> getMonederosCliente(int idCliente) {
        return modelFactory.getMonederosCliente(idCliente);
    }
}
