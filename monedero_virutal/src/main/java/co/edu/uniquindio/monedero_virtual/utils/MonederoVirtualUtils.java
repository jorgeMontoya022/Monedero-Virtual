package co.edu.uniquindio.monedero_virtual.utils;

import java.time.LocalDate;

import co.edu.uniquindio.monedero_virtual.model.*;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoNotifiacion;

public class MonederoVirtualUtils {

    public static MonederoVirtual initializeData() {

        // ------------------- Cliente 1: Jorge W. Montoya -------------------
        Cliente jorge = new Cliente(
                "Jorge William Montoya",
                "3244544139",
                "jorgetoro708@gmail.com",
                1234,
                LocalDate.of(2005, 1, 13),
                LocalDate.now(),
                "Quimbaya, Quindío",
                TipoRango.BRONCE,
                1097032932);

        Cuenta ahorroJorge = new CuentaAhorrro(1_000_000, "Davivienda", 109989022, jorge);
        Cuenta corrienteJorge = new CuentaCorriente(1_500_000, "Banco de Bogotá", 1022938292, jorge);

        Monedero monederoComida = new Monedero(1029, 500_000, "Comida", corrienteJorge);
        ahorroJorge.getMonederos().add(monederoComida);

        // Notificaciones de creación para Jorge
        jorge.getListaNotificacion().add(new Notificacion("Se ha creado tu cuenta de ahorros en Davivienda.", TipoNotifiacion.INFORMACION, LocalDate.now()));
        jorge.getListaNotificacion().add(new Notificacion("Se ha creado tu cuenta corriente en Banco de Bogotá.", TipoNotifiacion.INFORMACION, LocalDate.now()));
        jorge.getListaNotificacion().add(new Notificacion("Se ha creado el monedero 'Comida' con saldo de $500.000.", TipoNotifiacion.INFORMACION, LocalDate.now()));

        // Transacciones para Jorge
        Transaccion deposito1 = new Deposito("9001", LocalDate.of(2024, 12, 20), 10_000, "Depósito para comida",
                ahorroJorge, monederoComida);
        Transaccion retiro1 = new Retiro("9002", LocalDate.of(2025, 1, 10), 5_000, "Retiro efectivo", ahorroJorge,
                10_000, 500, monederoComida);

        ahorroJorge.agregarTransaccion(deposito1);
        ahorroJorge.agregarTransaccion(retiro1);

        // Notificaciones de transacciones para Jorge
        jorge.getListaNotificacion().add(new Notificacion("Se ha realizado un depósito de $10.000 para comida.", TipoNotifiacion.INFORMACION, deposito1.getFechaTransaccion()));
        jorge.getListaNotificacion().add(new Notificacion("Se ha realizado un retiro de $5.000 del monedero 'Comida'.", TipoNotifiacion.INFORMACION, retiro1.getFechaTransaccion()));

        jorge.getListaCuentas().add(corrienteJorge);
        jorge.getListaCuentas().add(ahorroJorge);

        // ------------------- Cliente 2: Andrea Gómez -------------------
        Cliente andrea = new Cliente(
                "Andrea Gómez",
                "3109988776",
                "andrea.gomez@gmail.com",
                5678,
                LocalDate.of(2003, 8, 5),
                LocalDate.now(),
                "Armenia, Quindío",
                TipoRango.PLATA,
                1001122334);

        Cuenta ahorroAndrea = new CuentaAhorrro(500_000, "Bancolombia", 204455667, andrea);
        Cuenta corrienteAndrea = new CuentaCorriente(800_000, "Nequi", 889977445, andrea);

        Monedero monederoAhorro = new Monedero(2932, 500_000, "Ahorro Viaje", corrienteAndrea);
        ahorroAndrea.getMonederos().add(monederoAhorro);

        // Notificaciones de creación para Andrea
        andrea.getListaNotificacion().add(new Notificacion("Se ha creado tu cuenta de ahorros en Bancolombia.", TipoNotifiacion.INFORMACION, LocalDate.now()));
        andrea.getListaNotificacion().add(new Notificacion("Se ha creado tu cuenta corriente en Nequi.", TipoNotifiacion.INFORMACION, LocalDate.now()));
        andrea.getListaNotificacion().add(new Notificacion("Se ha creado el monedero 'Ahorro Viaje' con saldo de $500.000.", TipoNotifiacion.INFORMACION, LocalDate.now()));

        // Transacciones para Andrea
        Transaccion deposito2 = new Deposito("9004", LocalDate.of(2025, 2, 5), 20_000, "Ahorro para viaje",
                ahorroAndrea, monederoAhorro);
        Transaccion retiro2 = new Retiro("9005", LocalDate.of(2025, 4, 12), 10_000, "Pago hotel", ahorroAndrea, 20_000,
                800, monederoAhorro);
        Transaccion transferencia2 = new Transferencia("9006", LocalDate.of(2025, 5, 1), 5_000,
                "Transferencia desde Jorge", ahorroAndrea, ahorroJorge, monederoAhorro);

        ahorroAndrea.agregarTransaccion(deposito2);
        ahorroAndrea.agregarTransaccion(retiro2);
        ahorroAndrea.agregarTransaccion(transferencia2);

        // Notificaciones de transacciones para Andrea
        andrea.getListaNotificacion().add(new Notificacion("Se ha realizado un depósito de $20.000 para el viaje.", TipoNotifiacion.INFORMACION, deposito2.getFechaTransaccion()));
        andrea.getListaNotificacion().add(new Notificacion("Se ha realizado un retiro de $10.000 para pagar el hotel.", TipoNotifiacion.INFORMACION, retiro2.getFechaTransaccion()));
        andrea.getListaNotificacion().add(new Notificacion("Has recibido una transferencia de $5.000 de Jorge William Montoya.", TipoNotifiacion.INFORMACION, transferencia2.getFechaTransaccion()));

        andrea.getListaCuentas().add(corrienteAndrea);
        andrea.getListaCuentas().add(ahorroAndrea);

        // Transferencia de Jorge a Andrea
        Transaccion transferencia1 = new Transferencia("9003", LocalDate.of(2025, 3, 15), 3_000,
                "Transferencia a Andrea", ahorroJorge, ahorroAndrea, monederoComida);
        ahorroJorge.agregarTransaccion(transferencia1);

        // Notificación para Jorge por transferencia enviada
        jorge.getListaNotificacion().add(new Notificacion("Has transferido $3.000 a Andrea Gómez.", TipoNotifiacion.INFORMACION, transferencia1.getFechaTransaccion()));

        // ------------------- Construcción del sistema -------------------
        MonederoVirtual sistema = new MonederoVirtual();

        sistema.getListaClientes().add(jorge);
        sistema.getListaClientes().add(andrea);

        sistema.getListaCuentas().add(ahorroJorge);
        sistema.getListaCuentas().add(corrienteJorge);
        sistema.getListaCuentas().add(ahorroAndrea);
        sistema.getListaCuentas().add(corrienteAndrea);

        sistema.getListaTransacciones().add(deposito1);
        sistema.getListaTransacciones().add(retiro1);
        sistema.getListaTransacciones().add(transferencia1);
        sistema.getListaTransacciones().add(deposito2);
        sistema.getListaTransacciones().add(retiro2);
        sistema.getListaTransacciones().add(transferencia2);

        return sistema;
    }
}
