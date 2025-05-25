package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;

public class GestionMovimientosController {
    ModelFactory modelFactory;

    public GestionMovimientosController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public List<Transaccion> getTrasaccionesCliente(int idCliente) {
       return modelFactory.getTrasaccionesCliente(idCliente);
    }
}
