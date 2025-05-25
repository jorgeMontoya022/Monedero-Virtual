package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;

public class GestionCuentasController {
    ModelFactory modelFactory;

    public GestionCuentasController () {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        return modelFactory.getCuentasUsuario(idCliente);
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return modelFactory.agregarCuenta(cuenta);
    }

    public boolean eliminarCuenta(Cuenta cuentaSeleccionada) {
        return modelFactory.eliminarCuenta(cuentaSeleccionada);
    }
}
