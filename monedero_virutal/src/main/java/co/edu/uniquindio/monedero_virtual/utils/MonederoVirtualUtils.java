package co.edu.uniquindio.monedero_virtual.utils;

import java.time.LocalDate;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.MonederoVirtual;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;

public class MonederoVirtualUtils {

    public static MonederoVirtual initializeData() {

        Cliente cliente = new Cliente(
                "Carlos William Montoya",
                "3244544139",
                "jorgetoro708@gmail.com",
                1234,
                LocalDate.of(2005, 01, 13),
                LocalDate.now(),
                "Quimbaya, Quindío",
                TipoRango.BRONCE,
                1097032932);

        Cuenta cuenta = new Cuenta(
                1000000,
                "Davivienda",
                109989022,
                cliente);

        MonederoVirtual monederoVirtual = new MonederoVirtual();
        monederoVirtual.getListaClientes().add(cliente);
        monederoVirtual.getListaCuentas().add(cuenta);

        return monederoVirtual;

    }

}
