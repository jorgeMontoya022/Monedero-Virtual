package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cliente;

public class DatosClienteController {
    ModelFactory modelFactory;

    public DatosClienteController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public boolean actualizarCliente(Cliente clienteLogueado, Cliente clienteData) {
        return modelFactory.actualizarCliente(clienteLogueado, clienteData);

    }

    public Cliente getClienteById(int id){
        return modelFactory.getClienteById(id);
    }
}
