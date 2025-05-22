package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;

public class DatosClienteController {
    ModelFactory modelFactory;

    public DatosClienteController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }
}
