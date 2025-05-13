package co.edu.uniquindio.monedero_virtual.controller;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;

public class LoginController {
    ModelFactory modelFactory;

    public LoginController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public Cliente validarAcceso(String correo, int contrasenia) {
       return modelFactory.validarAcceso(correo, contrasenia);
    }

    public void guardarSesion(Cliente clienteValidado) {
       Sesion.getInstance().setCliente(clienteValidado);
    }
}
