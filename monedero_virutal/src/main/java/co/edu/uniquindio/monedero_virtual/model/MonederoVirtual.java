package co.edu.uniquindio.monedero_virtual.model;

import java.util.ArrayList;
import java.util.List;

public class MonederoVirtual {
    private List<Cliente> listaClientes;
    private List<Cuenta> listaCuentas;

    public MonederoVirtual(){
        this.listaClientes = new ArrayList<>();
        this.listaCuentas = new ArrayList<>();
    }

    public List<Cliente> getListaClientes () {
        return listaClientes;
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }
}
