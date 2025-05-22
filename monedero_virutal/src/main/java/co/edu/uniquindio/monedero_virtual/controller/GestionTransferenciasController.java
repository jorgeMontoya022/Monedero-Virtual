package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;

public class GestionTransferenciasController {
    ModelFactory modelFactory;

    public GestionTransferenciasController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }
}
