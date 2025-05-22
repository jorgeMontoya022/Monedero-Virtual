package co.edu.uniquindio.monedero_virtual.utils;

import java.time.LocalDate;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.CuentaAhorrro;
import co.edu.uniquindio.monedero_virtual.model.CuentaCorriente;
import co.edu.uniquindio.monedero_virtual.model.MonederoVirtual;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;

public class MonederoVirtualUtils {

    public static MonederoVirtual initializeData() {

        Cliente cliente = new Cliente(
                "Jorge William Montoya",
                "3244544139",
                "jorgetoro708@gmail.com",
                1234,
                LocalDate.of(2005, 01, 13),
                LocalDate.now(),
                "Quimbaya, Quindío",
                TipoRango.BRONCE,
                1097032932);

        Cuenta cuentaAhorro = new CuentaAhorrro(
                1000000,
                "Davivienda",
                109989022,
                cliente);
        
        Cuenta cuentaCorriente = new CuentaCorriente(
            1500000, 
            "Banco de bogotá", 1022938292, 
            cliente);

        
        cliente.getListaCuentas().add(cuentaCorriente);
        cliente.getListaCuentas().add(cuentaAhorro);

        MonederoVirtual monederoVirtual = new MonederoVirtual();
        monederoVirtual.getListaClientes().add(cliente);
        monederoVirtual.getListaCuentas().add(cuentaAhorro);
        monederoVirtual.getListaCuentas().add(cuentaCorriente);

        return monederoVirtual;

    }

}
