package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.enums.Beneficio;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnLinkedList;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues.ownPriorityQueue;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownTrees.OwnTreeAVL;

public class MonederoVirtual {
    private OwnLinkedList<Cliente> listaClientes;
    private OwnLinkedList<Cuenta> listaCuentas;
    private OwnTreeAVL<Cliente> rankingClientes;

    public MonederoVirtual() {
        this.listaClientes = new OwnLinkedList<>();
        this.listaCuentas = new OwnLinkedList<>();
        this.rankingClientes = new OwnTreeAVL<>();
    }

    public OwnLinkedList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public OwnLinkedList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public boolean agregarCliente(Cliente cliente) {
        Cliente clienteEncontrado = buscarCliente(cliente.getId());
        if (clienteEncontrado != null) {
            return false;
        } else {
            listaClientes.add(cliente);
            try {
                rankingClientes.insertar(cliente);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }
    }

    public void actualizarRanking(Cliente cliente) {
        try {
            rankingClientes.eliminar(cliente);
            rankingClientes.insertar(cliente);
        } catch (Exception e) {

        }
    }

    private Cliente buscarCliente(int id) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getId() == (id)) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente validarAcceso(String correo, int contrasenia) throws Exception {
        for (Cliente cliente : listaClientes) {
            if (cliente.getEmail().equals(correo) && cliente.getContraseña() == contrasenia) {
                return cliente;
            }
        }
        throw new Exception("Credenciales incorrectas");
    }

    private boolean verificarClienteExiste(String correo) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getEmail().equals(correo)) {
                return true;
            }
        }
        return false;

    }

    // METODOS PARA TRANSACCIONES
    public void realizarDeposito(Deposito deposito) throws Exception {

        double monto = deposito.getMonto();
        Cuenta cuenta = deposito.getCuenta();
        Monedero monedero = deposito.getMonedero();

        if (!verificarClienteExiste(cuenta.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar el deposito. El cliente no existe.");
        }

        monedero.agregarDinero(monto);
        cuenta.setMonto(cuenta.getMonto() + monto);
        cuenta.getClienteAsociado().agregarPuntos(deposito);
        actualizarRanking(cuenta.getClienteAsociado());

        cuenta.agregarTransaccion(deposito);
        // cuenta.agregarTransaccionReversible(deposito);

    }

    public void realizarRetiro(Retiro retiro) throws Exception {

        double monto = retiro.getMonto();
        Cuenta cuenta = retiro.getCuenta();
        Monedero monedero = retiro.getMonedero();

        if (!verificarClienteExiste(cuenta.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar el retiro. El cliente no existe.");
        }

        try {
            monedero.retirarDinero(monto);
            cuenta.setMonto(cuenta.getMonto() - monto);
            cuenta.getClienteAsociado().agregarPuntos(retiro);
            actualizarRanking(cuenta.getClienteAsociado());

            cuenta.agregarTransaccion(retiro);
            // cuenta.agregarTransaccionReversible(retiro);

        } catch (Exception e) {
            System.out.println("No se pudo realizar el retiro.");
        }

    }
    public void realizarTransferencia(Transferencia transferencia) throws Exception {

        double monto = transferencia.getMonto();
        Cuenta cuentaEmisora = transferencia.getCuenta();
        Monedero monederoEmisor = transferencia.getMonedero();
        Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

        if (!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar la transferencia. El cliente no existe.");
        }
        if (!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar la transferencia. El cliente de destino no existe.");
        }

        try {
            double comision = monto * 0.05;
            PuntosCliente puntosCliente = cuentaEmisora.getClienteAsociado().getPuntos();
            Beneficio beneficioActivo = puntosCliente.getBeneficioActivo();

            if (beneficioActivo != null) {
                switch (beneficioActivo) {
                    case REDUCCION_COMISION:
                        comision = comision * 0.90;
                        puntosCliente.setBeneficioActivo(null);
                        puntosCliente.setFechaDeActivacion(null);
                        break;
                    case BONO_SALDO:
                        monederoEmisor.agregarDinero(50);
                        puntosCliente.setBeneficioActivo(null);
                        puntosCliente.setFechaDeActivacion(null);
                        break;
    
                    default:
                        break;
                }
            }
            monederoEmisor.retirarDinero(monto + comision);
            cuentaEmisora.setMonto(cuentaEmisora.getMonto() - (monto + comision));
            cuentaEmisora.getClienteAsociado().agregarPuntos(transferencia);
            actualizarRanking(cuentaEmisora.getClienteAsociado());

            cuentaReceptora.setMonto(cuentaReceptora.getMonto() + monto);


            cuentaEmisora.agregarTransaccion(transferencia);
            cuentaEmisora.agregarTransaccionReversible(transferencia);

        } catch (Exception e) {
            System.out.println("No se pudo realizar la transferencia.");
        }
    }

    

    // METODOS PARA REVERTIR TRANSACCIONES
    public void revertirTransferencia(Transferencia transferencia) throws Exception {
        double monto = transferencia.getMonto();
        Cuenta cuentaEmisora = transferencia.getCuenta();
        Monedero monederoEmisor = transferencia.getMonedero();

        Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

        if (!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar la transferencia. El cliente no existe.");
        }
        if (!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar la transferencia. El cliente de destino no existe.");
        }

        try {
            cuentaReceptora.setMonto(cuentaReceptora.getMonto() - monto);

            monederoEmisor.agregarDinero(monto);
            cuentaEmisora.setMonto(cuentaEmisora.getMonto() + monto);
            cuentaEmisora.getClienteAsociado().retirarPuntos(transferencia);
            actualizarRanking(cuentaEmisora.getClienteAsociado());

        } catch (Exception e) {
            System.out.println("No se pudo revertir la transferencia.");
        }
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        Cliente cliente = buscarCliente(idCliente);
        return cliente.getListaCuentas();
    }


    

    public boolean agregarCuenta(Cuenta cuenta) {

        Cuenta cuentaEncontrada = buscarCuenta(cuenta.getNumeroCuenta());
        if (cuentaEncontrada == null) {
            listaCuentas.add(cuenta);

            for (Cliente cliente : listaClientes) {
                if (cliente.getId() == cuenta.getClienteAsociado().getId()) {
                    cliente.getListaCuentas().add(cuenta);
                    return true;
                }
            }
        }
        return false;

    }

    private Cuenta buscarCuenta(int numeroCuenta) {
        for(Cuenta cuenta : listaCuentas) {
            if(cuenta.getNumeroCuenta() == numeroCuenta) {
                return cuenta;
            }
        }
        return null;
    }

    private void procesarTransaccionesPendientes(Cuenta cuenta) throws Exception {
        ownPriorityQueue<Transaccion> listaTransacciones = cuenta.getTransaccionesProgramadas();
        LocalDate hoy = LocalDate.now();
        ownPriorityQueue<Transaccion> transaccionesNoProcesadas = new ownPriorityQueue<>();

        while (!listaTransacciones.isEmpty()){
            Transaccion transaccion = listaTransacciones.dequeue();
            if (transaccion.getFechaTransaccion().equals(hoy)){
                if (transaccion instanceof Transferencia){
                    realizarTransferencia((Transferencia) transaccion);
                } else {
                    throw new Exception("No se admiten otro tipo de transacciones programadas");
                }
            } else {
                transaccionesNoProcesadas.enqueue(transaccion);
            }
        }
        while (!transaccionesNoProcesadas.isEmpty()){
            listaTransacciones.enqueue(transaccionesNoProcesadas.dequeue());
     }
    }
        
        
}
    


