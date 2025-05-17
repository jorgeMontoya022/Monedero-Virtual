package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
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


    public void realizarDeposito(Monedero monederoCliente, double cantidadDepositar) throws Exception{
        if(!verificarClienteExiste(monederoCliente.getCuenta().getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar el deposito. El cliente no existe.");
        }

        try{
            monederoCliente.agregarDinero(cantidadDepositar);
        }catch(Exception e){
            System.out.println("No se pudo realizar el depósito.");
        }

        // No sé cual sería el id aquí.
        Deposito depositoHecho = new Deposito(0, LocalDate.now(), cantidadDepositar, ("Deposito de " + cantidadDepositar), monederoCliente.getCuenta(), monederoCliente);
        
        // Agregando la transaccion al registro de transacciones que maneja la cuenta del cliente. 
        monederoCliente.getCuenta().agregarTransaccion(depositoHecho);
        monederoCliente.getCuenta().agregarTransaccionReversible(depositoHecho);

    }
    
    public void realizarRetiro(Monedero monederoCliente, double cantidadRetirar) throws Exception{
        if(!verificarClienteExiste(monederoCliente.getCuenta().getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar el retiro. El cliente no existe.");
        }

        try{
            monederoCliente.retirarDinero(cantidadRetirar);
        }catch(Exception e){
            System.out.println("No se pudo realizar el retiro.");
        }

        // No sé cual sería el id aquí.
        Retiro retiroHecho = new Retiro(0, LocalDate.now(), cantidadRetirar, ("Retiro de " + cantidadRetirar), monederoCliente.getCuenta(), monederoCliente.getMonto(), cantidadRetirar, monederoCliente);
        
        // Agregando la transaccion al registro de transacciones que maneja la cuenta del cliente. 
        monederoCliente.getCuenta().agregarTransaccion(retiroHecho);
        monederoCliente.getCuenta().agregarTransaccionReversible(retiroHecho);

    }

    public void realizarTransferencia(Monedero monderoEmisor, Monedero monederoReceptor, double cantidad) throws Exception{
        if(!verificarClienteExiste(monderoEmisor.getCuenta().getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente emisor no existe.");
        }
        if(!verificarClienteExiste(monederoReceptor.getCuenta().getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente receptor no existe.");
        }

        try {
            monderoEmisor.retirarDinero(cantidad);
            monederoReceptor.agregarDinero(cantidad);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la transferencia.");
        }
        
        // Aun falta agregar la transferencia a la lista de transacciones de la cuenta del cliente.

    }




}
