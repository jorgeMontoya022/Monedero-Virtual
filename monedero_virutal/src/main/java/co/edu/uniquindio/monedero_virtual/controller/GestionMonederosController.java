package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;

public class GestionMonederosController {
    ModelFactory modelFactory;

    public GestionMonederosController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public boolean agregarMonedero(Monedero monedero) {
        return modelFactory.agregarMonedero(monedero);
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        return modelFactory.getCuentasUsuario(idCliente);
    }

    public List<Monedero> getMonederosCliente(int idCliente) {
        return modelFactory.getMonederosCliente(idCliente);
    }

    
}
