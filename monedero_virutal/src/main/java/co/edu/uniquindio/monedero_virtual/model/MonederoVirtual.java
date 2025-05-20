package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues.ownQueue.Node;

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


    // METODOS PARA TRANSACCIONES
    public void realizarDeposito(Deposito deposito) throws Exception{

        double monto = deposito.getMonto();
        Cuenta cuenta = deposito.getCuenta();
        Monedero monedero = deposito.getMonedero();

        if(!verificarClienteExiste(cuenta.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar el deposito. El cliente no existe.");
        }

        monedero.agregarDinero(monto);
        cuenta.setMonto(cuenta.getMonto() + monto);
        cuenta.getClienteAsociado().agregarPuntos(deposito);

        cuenta.agregarTransaccion(deposito);
        //cuenta.agregarTransaccionReversible(deposito);

    }
    
    public void realizarRetiro(Retiro retiro) throws Exception{

        double monto = retiro.getMonto();
        Cuenta cuenta = retiro.getCuenta();
        Monedero monedero = retiro.getMonedero();

        if(!verificarClienteExiste(cuenta.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar el retiro. El cliente no existe.");
        }

        try {
            monedero.retirarDinero(monto);
            cuenta.setMonto(cuenta.getMonto() - monto);
            cuenta.getClienteAsociado().agregarPuntos(retiro);

            cuenta.agregarTransaccion(retiro);
            //cuenta.agregarTransaccionReversible(retiro);

        } catch (Exception e) {
            System.out.println("No se pudo realizar el retiro.");
        }

    }

    public void realizarTransferencia(Transferencia transferencia) throws Exception{

        double monto = transferencia.getMonto();
        Cuenta cuentaEmisora = transferencia.getCuenta();
        Monedero monederoEmisor = transferencia.getMonedero();

        Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

        if(!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente no existe.");
        }
        if(!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente de destino no existe.");
        }

        try {
            monederoEmisor.retirarDinero(monto);
            cuentaEmisora.setMonto(cuentaEmisora.getMonto() - monto);
            cuentaEmisora.getClienteAsociado().agregarPuntos(transferencia);

            cuentaReceptora.setMonto(cuentaReceptora.getMonto() + monto);

            cuentaEmisora.agregarTransaccion(transferencia);
            cuentaEmisora.agregarTransaccionReversible(transferencia);

        } catch (Exception e) {
            System.out.println("No se pudo realizar la transferencia.");
        }

    }


    // METODOS PARA REVERTIR TRANSACCIONES
    public void revertirTransferencia(Transferencia transferencia) throws Exception{
        double monto = transferencia.getMonto();
        Cuenta cuentaEmisora = transferencia.getCuenta();
        Monedero monederoEmisor = transferencia.getMonedero();

        Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

        if(!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente no existe.");
        }
        if(!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())){
            throw new Exception("No se puede realizar la transferencia. El cliente de destino no existe.");
        }

        try {
            cuentaReceptora.setMonto(cuentaReceptora.getMonto() - monto);

            monederoEmisor.agregarDinero(monto);
            cuentaEmisora.setMonto(cuentaEmisora.getMonto() + monto);
            cuentaEmisora.getClienteAsociado().retirarPuntos(transferencia);

        } catch (Exception e) {
            System.out.println("No se pudo revertir la transferencia.");
        }
    }


}
