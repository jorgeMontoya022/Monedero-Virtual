package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;

public class GestionMonederosController {
    ModelFactory modelFactory;

    public GestionMonederosController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }
    
}
