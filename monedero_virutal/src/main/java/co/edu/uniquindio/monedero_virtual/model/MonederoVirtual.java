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


    public boolean agregarCliente(Cliente cliente) {
        Cliente clienteEncontrado = buscarCliente(cliente.getEmail());
        if(clienteEncontrado != null) {
            return false;
        } else {
            listaClientes.add(cliente);
            return true;
        }
    }

    private Cliente buscarCliente(String email) {
        for(Cliente cliente : listaClientes) {
            if(cliente.getEmail().equals(email)) {
                return cliente;
            }
        }
        return null;
    }


    



}
