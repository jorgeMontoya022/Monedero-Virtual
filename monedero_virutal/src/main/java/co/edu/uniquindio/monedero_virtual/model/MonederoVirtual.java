package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.enums.Beneficio;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnLinkedList;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues.ownPriorityQueue;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownTrees.OwnTreeAVL;

public class MonederoVirtual {
    private OwnLinkedList<Cliente> listaClientes;
    private OwnLinkedList<Cuenta> listaCuentas;
    private OwnTreeAVL<Cliente> rankingClientes;
    private OwnLinkedList<Transaccion> listaTransacciones;

    public MonederoVirtual() {
        this.listaClientes = new OwnLinkedList<>();
        this.listaCuentas = new OwnLinkedList<>();

        this.rankingClientes = new OwnTreeAVL<>();

        this.listaTransacciones = new OwnLinkedList<>();

    }

    public OwnLinkedList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public OwnLinkedList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public OwnLinkedList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public boolean agregarCliente(Cliente cliente) {
        // Busca en la lista de Clientes, si hay otro
        Cliente clienteEncontrado = buscarCliente(cliente.getId());
        /**
         * Si el clienteEncontrado no es nulo, significa que un cliente
         * con la misma ID ya está registrado, por lo cuál no se puede registrar
         */
        if (clienteEncontrado != null) {
            return false;
        } else {
            /**
             * Se añade el cliente a la lista de clientes del sistema
             * y se inserta al ranking de puntos ya que sus puntos se inicializan en 0
             */
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

    // Recorre la lista mediante un for (ya que ownLinkedList es Iterable)
    // buscando un cliente dada una ID
    public Cliente buscarCliente(int id) {
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
    public boolean realizarDeposito(Deposito deposito) throws Exception {

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
        listaTransacciones.add(deposito);
        // cuenta.agregarTransaccionReversible(deposito);
        return true;

    }

    public boolean realizarRetiro(Retiro retiro) throws Exception {
        double comision = retiro.getComision();
        double monto = retiro.getMonto();
        Cuenta cuenta = retiro.getCuenta();
        Monedero monedero = retiro.getMonedero();

        if (!verificarClienteExiste(cuenta.getClienteAsociado().getEmail())) {
            throw new Exception("No se puede realizar el retiro. El cliente no existe.");
        }

        try {
            PuntosCliente puntosCliente = cuenta.getClienteAsociado().getPuntos();
            Beneficio beneficioActivo = puntosCliente.getBeneficioActivo();

            if (beneficioActivo != null) {
                switch (beneficioActivo) {
                    case BONO_SALDO:
                        monedero.agregarDinero(50);
                        System.out.println("Beneficio BONO_SALDO aplicado: se agregaron $50 al monedero.");
                        puntosCliente.setBeneficioActivo(null);
                        puntosCliente.setFechaDeActivacion(null);
                        break;
                    case MES_LIBRE_RETIROS:
                        LocalDate hoy = LocalDate.now();
                        LocalDate fechaActivacion = puntosCliente.getFechaActivacion();
                        LocalDate finBeneficio = fechaActivacion.plusMonths(1);
                        if (hoy.isAfter(finBeneficio)) {
                            System.out.println("El beneficio ha expirado");
                            puntosCliente.setBeneficioActivo(null);
                            puntosCliente.setFechaDeActivacion(null);
                        } else {
                            retiro.setComision(0);
                            System.out.println("Beneficio MES_LIBRE_RETIROS aplicado, no se cobrará comisión");
                        }
                        break;
                    case REDUCCION_COMISION:
                        retiro.setComision(comision * 0.50);
                        System.out.println("Beneficio REDUCCION_COMISION aplicado, se redujo el costo de la comisión");
                        puntosCliente.setBeneficioActivo(null);
                        puntosCliente.setFechaDeActivacion(null);
                    default:
                        break;

                }
            }

            monedero.retirarDinero(monto - comision);
            cuenta.setMonto(cuenta.getMonto() - monto - comision);
            cuenta.getClienteAsociado().agregarPuntos(retiro);
            actualizarRanking(cuenta.getClienteAsociado());

            cuenta.agregarTransaccion(retiro);
            // cuenta.agregarTransaccionReversible(retiro);
            listaTransacciones.add(retiro);
            return true;

        } catch (Exception e) {
            System.out.println("No se pudo realizar el retiro.");
            return false;
        }

    }

    public boolean realizarTransferencia(Transferencia transferencia) {
        double monto = transferencia.getMonto();
        Cuenta cuentaEmisora = transferencia.getCuenta();
        Monedero monederoEmisor = transferencia.getMonedero();
        Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

        // Validación de existencia de clientes
        if (!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())) {
            System.out.println("Error: El cliente emisor no existe.");
            return false;
        }

        if (!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())) {
            System.out.println("Error: El cliente receptor no existe.");
            return false;
        }

        // Validación de fondos suficientes
        if (monederoEmisor.getMonto() < monto) {
            System.out.println("Error: Fondos insuficientes en el monedero.");
            return false;
        }

        if (cuentaEmisora.getMonto() < monto) {
            System.out.println("Error: Fondos insuficientes en la cuenta.");
            return false;
        }

        try {
            // Aplicar beneficios si existen
            PuntosCliente puntosCliente = cuentaEmisora.getClienteAsociado().getPuntos();
            Beneficio beneficioActivo = puntosCliente.getBeneficioActivo();

            if (beneficioActivo != null) {
                switch (beneficioActivo) {
                    case BONO_SALDO:
                        monederoEmisor.agregarDinero(50);
                        System.out.println("Beneficio BONO_SALDO aplicado: se agregaron $50 al monedero.");
                        puntosCliente.setBeneficioActivo(null);
                        puntosCliente.setFechaDeActivacion(null);
                        break;

                    case REDUCCION_COMISION:
                        // Ya no hay comisión, solo se limpia el estado
                        System.out.println("Beneficio REDUCCION_COMISION omitido (no hay comisión).");
                        break;
                    case MES_LIBRE_RETIROS:
                        System.out.println("Beneficio MES_LIBRE_RETIROS no aplica");
                        break;

                    default:
                        break;
                }

            }

            // Realizar transferencia
            monederoEmisor.retirarDinero(monto);
            cuentaEmisora.setMonto(cuentaEmisora.getMonto() - monto);
            cuentaReceptora.setMonto(cuentaReceptora.getMonto() + monto);

            // Agregar puntos al cliente emisor y actualizar su ranking
            cuentaEmisora.getClienteAsociado().agregarPuntos(transferencia);
            actualizarRanking(cuentaEmisora.getClienteAsociado());

            // Registrar la transacción
            cuentaEmisora.agregarTransaccion(transferencia);
            cuentaEmisora.agregarTransaccionReversible(transferencia);
            cuentaReceptora.agregarTransaccion(transferencia);
            listaTransacciones.add(transferencia);

            System.out.println("Transferencia realizada exitosamente.");
            return true;

        } catch (Exception e) {
            System.out.println("Error al realizar la transferencia: " + e.getMessage());
            e.printStackTrace(); // Para depuración
            return false;
        }
    }

    // METODOS PARA REVERTIR TRANSACCIONES
   public boolean revertirTransferencia(Cuenta cuenta) throws Exception {
    // Verificar que la cuenta tenga transacciones reversibles
    if (cuenta.getTransaccionesReversibles().isEmpty()) {
        throw new Exception("No hay transferencias para revertir en esta cuenta.");
    }

    // Obtener la última transferencia de la pila
    Transaccion ultimaTransferencia = (Transaccion) cuenta.getTransaccionesReversibles().pop();
    
    // Verificar que sea una transferencia (no otro tipo de transacción)
    if (!(ultimaTransferencia instanceof Transferencia)) {
        // Si no es transferencia, la devolvemos a la pila
        cuenta.getTransaccionesReversibles().push(ultimaTransferencia);
        throw new Exception("La última transacción no es una transferencia.");
    }
    
    Transferencia transferencia = (Transferencia) ultimaTransferencia;
    double monto = transferencia.getMonto();
    Cuenta cuentaEmisora = transferencia.getCuenta();
    Monedero monederoEmisor = transferencia.getMonedero();
    Cuenta cuentaReceptora = transferencia.getCuentaRecibe();

    // Verificar que los clientes existan
    if (!verificarClienteExiste(cuentaEmisora.getClienteAsociado().getEmail())) {
        // Devolver la transferencia a la pila si hay error
        cuenta.getTransaccionesReversibles().push(transferencia);
        throw new Exception("No se puede revertir la transferencia. El cliente emisor no existe.");
    }
    if (!verificarClienteExiste(cuentaReceptora.getClienteAsociado().getEmail())) {
        // Devolver la transferencia a la pila si hay error
        cuenta.getTransaccionesReversibles().push(transferencia);
        throw new Exception("No se puede revertir la transferencia. El cliente receptor no existe.");
    }

    try {
        // Revertir los movimientos de dinero
        cuentaReceptora.setMonto(cuentaReceptora.getMonto() - monto);
        monederoEmisor.agregarDinero(monto);
        cuentaEmisora.setMonto(cuentaEmisora.getMonto() + monto);
        listaTransacciones.remove(transferencia);
        cuentaEmisora.getTransacciones().remove(transferencia);
        
        // Revertir los puntos si es necesario
        cuentaEmisora.getClienteAsociado().retirarPuntos(transferencia);
        actualizarRanking(cuentaEmisora.getClienteAsociado());

        return true; // Todo bien, parcero
    } catch (Exception e) {
        // Si hay error, devolver la transferencia a la pila
        cuenta.getTransaccionesReversibles().push(transferencia);
        System.out.println("No se pudo revertir la transferencia: " + e.getMessage());
        return false;
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

    public Cuenta buscarCuenta(int numeroCuenta) {
        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getNumeroCuenta() == numeroCuenta) {
                return cuenta;
            }
        }
        return null;
    }

    private void procesarTransaccionesPendientes(Cuenta cuenta) throws Exception {
        ownPriorityQueue<Transaccion> listaTransacciones = cuenta.getTransaccionesProgramadas();
        LocalDate hoy = LocalDate.now();
        ownPriorityQueue<Transaccion> transaccionesNoProcesadas = new ownPriorityQueue<>();

        while (!listaTransacciones.isEmpty()) {
            Transaccion transaccion = listaTransacciones.dequeue();
            if (transaccion.getFechaTransaccion().equals(hoy)) {
                if (transaccion instanceof Transferencia) {
                    realizarTransferencia((Transferencia) transaccion);
                } else {
                    throw new Exception("No se admiten otro tipo de transacciones programadas");
                }
            } else {
                transaccionesNoProcesadas.enqueue(transaccion);
            }
        }
        while (!transaccionesNoProcesadas.isEmpty()) {
            listaTransacciones.enqueue(transaccionesNoProcesadas.dequeue());
        }
    }

    public boolean eliminarCuenta(Cuenta cuentaSeleccionada) {
        Cuenta cuentaEncontrada = buscarCuenta(cuentaSeleccionada.getNumeroCuenta());
        if (cuentaEncontrada != null) {
            listaCuentas.remove(cuentaSeleccionada);
            eliminarCuentaDeCliente(cuentaSeleccionada.getNumeroCuenta());
            return true;

        }
        return false;
    }

    private void eliminarCuentaDeCliente(int numeroCuenta) {
        for (Cliente cliente : listaClientes) {
            for (Cuenta cuenta : cliente.getListaCuentas()) {
                if (cuenta.getNumeroCuenta() == numeroCuenta) {
                    cliente.getListaCuentas().remove(cuenta);
                    break;
                }
            }

        }
    }

    public List<Transaccion> getTrasaccionesCliente(int idCliente) {
        List<Transaccion> lista = new ArrayList<>();
        for (Transaccion transaccion : listaTransacciones) {
            // Transferencias donde el cliente es el emisor
            if (transaccion.getCuenta().getClienteAsociado().getId() == idCliente) {
                lista.add(transaccion);
            }
            // Transferencias donde el cliente es el receptor (para Transferencias)
            else if (transaccion instanceof Transferencia) {
                Transferencia transferencia = (Transferencia) transaccion;
                if (transferencia.getCuentaRecibe() != null &&
                        transferencia.getCuentaRecibe().getClienteAsociado().getId() == idCliente) {
                    lista.add(transaccion);
                }
            }
        }
        return lista;
    }

    public List<Transaccion> getDepositosCliente(int idCliente) {
        Cliente cliente = buscarCliente(idCliente);
        return cliente.getListaDepositos();
    }

    public List<Transaccion> getRetirosCliente(int idCliente) {
        Cliente cliente = buscarCliente(idCliente);
        return cliente.getListaRetiros();
    }

    public List<Transaccion> getTransferenciasCliente(int idCliente) {
        Cliente cliente = buscarCliente(idCliente);
        return cliente.getListaTransferencias();
    }

    public List<Monedero> getMonederosUsuario(int idCliente) {
        List<Monedero> monederosCliente = new LinkedList<>();
        Cliente cliente = buscarCliente(idCliente);
        for (Cuenta cuenta : cliente.getListaCuentas()) {
            monederosCliente.addAll(cuenta.getMonederos());
        }
        return monederosCliente;
    }

    public boolean actualizarCliente(int id, Cliente clienteData) throws Exception {
        Cliente clienteAcutal = buscarCliente(id);
        if (clienteAcutal == null) {
            throw new Exception("El cliente a actualizar no existe");

        } else {
            clienteAcutal.setNombreCompleto(clienteData.getNombreCompleto());
            clienteAcutal.setCelular(clienteData.getCelular());
            clienteAcutal.setEmail(clienteData.getEmail());
            clienteAcutal.setFechaNacimiento(clienteData.getFechaNacimiento());
            clienteAcutal.setDirección(clienteData.getDirección());
            clienteAcutal.setFechaRegistro(clienteData.getFechaRegistro());

            // ELIMINADO: Lógica que causaba duplicación de cuentas
            // if (clienteData.getListaCuentas() != null) {
            // clienteAcutal.getListaCuentas().addAll(clienteData.getListaCuentas());
            // }
            return true;
        }
    }

    public Transferencia obtenerUltimaTransferencia(Cuenta cuentaSeleccionada) {
        Transferencia ultimTransferencia = (Transferencia)cuentaSeleccionada.getTransaccionesReversibles().peek();
        return ultimTransferencia;
    }
}
