package co.edu.uniquindio.monedero_virtual.factory;

import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Deposito;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.MonederoVirtual;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.Transferencia;
import co.edu.uniquindio.monedero_virtual.utils.MonederoVirtualUtils;

public class ModelFactory {

    MonederoVirtual monederoVirtual;

    private static ModelFactory modelFactory;

    private ModelFactory() {
        if (monederoVirtual == null) {
            initializeData();
        }
    }

    private void initializeData() {
        monederoVirtual = MonederoVirtualUtils.initializeData();
    }

    public static ModelFactory getINSTANCE() {
        if (modelFactory == null) {
            
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    public boolean registrarUsuario(Cliente cliente) {
        try{
           return monederoVirtual.agregarCliente(cliente);
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public Cliente validarAcceso(String correo, int contrasenia) {
        try {
            return monederoVirtual.validarAcceso(correo, contrasenia);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cuenta> getCuentasUsuario(int idCliente) {
        return monederoVirtual.getCuentasUsuario(idCliente);
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        try{
            return monederoVirtual.agregarCuenta(cuenta);

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCuenta(Cuenta cuentaSeleccionada) {
        try {
            return monederoVirtual.eliminarCuenta(cuentaSeleccionada);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaccion> getTrasaccionesCliente(int idCliente) {
        return monederoVirtual.getTrasaccionesCliente(idCliente);
    }

    public List<Transaccion> getDepositosCliente(int idCliente) {
        return monederoVirtual.getDepositosCliente(idCliente);
    }

    public List<Transaccion> getTransferenciasCliente(int idCliente) {
        return monederoVirtual.getTransferenciasCliente(idCliente);
    }

    public List<Monedero> getMonederosCliente(int idCliente) {
        return monederoVirtual.getMonederosUsuario(idCliente);
    }

    public boolean realizarDeposito(Deposito deposito){
        try {
           return monederoVirtual.realizarDeposito(deposito);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public boolean realizarTransferencia(Transferencia transferencia){
        try {
           return monederoVirtual.realizarTransferencia(transferencia);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
       
    }

    public Cuenta buscarCuenta(int idCuenta) {
        return monederoVirtual.buscarCuenta(idCuenta);
    }
    
}
