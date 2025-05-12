package co.edu.uniquindio.monedero_virtual.model;

import java.util.HashSet;
import java.util.Set;

import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnLinkedList;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues.ownPriorityQueue;


public class Cuenta {
    private double monto;
    private String banco;
    private int numeroCuenta;
    private Cliente clienteAsociado;
    private Set<Monedero> monederos;
    private ownPriorityQueue<Transaccion> transaccionesProgramadas;
    private OwnLinkedList<Transaccion> transacciones;
    
    public Cuenta(double monto, String banco, int numeroCuenta, Cliente clienteAsociado) {
        this.monto = monto;
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.clienteAsociado = clienteAsociado;
        this.monederos = new HashSet<>();
        this.transaccionesProgramadas = new ownPriorityQueue<>();
        this.transacciones = new OwnLinkedList<>();
    }


    public double getMonto() {
        return monto;
    }


    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getBanco() {
        return banco;
    }


    public void setBanco(String banco) {
        this.banco = banco;
    }


    public int getNumeroCuenta() {
        return numeroCuenta;
    }


    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    public Cliente getClienteAsociado() {
        return clienteAsociado;
    }


    public void setClienteAsociado(Cliente clienteAsociado) {
        this.clienteAsociado = clienteAsociado;
    }

    
    public OwnLinkedList<Transaccion> getTransacciones() {
        return transacciones;
    }


    public void setTransacciones(OwnLinkedList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }


    public ownPriorityQueue<Transaccion> getTransaccionesProgramadas() {
        return transaccionesProgramadas;
    }


    public void setTransaccionesProgramadas(ownPriorityQueue<Transaccion> transaccionesProgramadas) {
        this.transaccionesProgramadas = transaccionesProgramadas;
    }


    public Set<Monedero> getMonederos() {
        return monederos;
    }


    public void setMonederos(Set<Monedero> monederos) {
        this.monederos = monederos;
    }

    


}
