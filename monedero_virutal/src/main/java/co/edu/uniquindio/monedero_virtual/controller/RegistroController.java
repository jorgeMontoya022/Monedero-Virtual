package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cliente;

public class RegistroController {

    ModelFactory modelFactory;
    
    public RegistroController(){
        this.modelFactory = ModelFactory.getINSTANCE();

    }

    public boolean registrarUsuario(Cliente cliente) {
        return modelFactory.registrarUsuario(cliente);
    }
    
}
