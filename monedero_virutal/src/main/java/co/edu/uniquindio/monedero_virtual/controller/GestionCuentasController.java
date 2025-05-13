package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;

public class GestionCuentasController {
    ModelFactory modelFactory;

    public GestionCuentasController () {
        this.modelFactory = ModelFactory.getINSTANCE();
    }
}
