package co.edu.uniquindio.monedero_virtual.controller;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.enums.Beneficio;

public class GestionPuntosController {
    ModelFactory modelFactory;

    public GestionPuntosController(){
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public List<Beneficio> getBeneficios() {
        return modelFactory.getBeneficios();
    }

}
