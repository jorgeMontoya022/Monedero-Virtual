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
        Cliente clienteEncontrado = buscarCliente(cliente.getId());
        if(clienteEncontrado != null) {
            return false;
        } else {
            listaClientes.add(cliente);
            return true;
        }
    }

    private Cliente buscarCliente(int id) {
        for(Cliente cliente : listaClientes) {
            if(cliente.getId() == (id)) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente validarAcceso(String correo, int contrasenia) throws Exception {
        for (Cliente cliente : listaClientes) {
            if (verificarClienteExiste(correo)) {
                if(cliente.getEmail().equals(correo) && cliente.getContraseña() == contrasenia) {
                    return cliente;

                }
            }else {
                throw new Exception("No existe el cliente");
            }
        }
        throw new Exception("Validaciones incorrectas");
      
    }

    private boolean verificarClienteExiste(String correo) {
        for(Cliente cliente : listaClientes) {
            if(cliente.getEmail().equals(correo)) {
                return true;
            }
        }
        return false;
    }


    



}
