package co.edu.uniquindio.monedero_virtual.controller;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.factory.ModelFactory;
import co.edu.uniquindio.monedero_virtual.model.Deposito;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;

public class GestionDepositosController {
    ModelFactory modelFactory;

    public GestionDepositosController() {
        this.modelFactory = ModelFactory.getINSTANCE();
    }

    public boolean realizarDeposito(Deposito deposito){
        return modelFactory.realizarDeposito(deposito);
    }

    public List<Transaccion> getDepositosCliente(int idCliente){
        return modelFactory.getDepositosCliente(idCliente);
    }
    
}
