package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnCircularList;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnLinkedList;

public class Cliente {
    private String nombreCompleto;
    private String celular;
    private String email;
    private String contraseña;
    private LocalDate fechaNacimiento; // Usar LocalDate en lugar de String
    private LocalDate fechaRegistro; // Usar LocalDate en lugar de String
    private int id;
    private String dirección;
    private int puntos;
    private List<Cuenta> listaCuentas;
    private TipoRango tipoRango;
    private OwnCircularList<Notificacion> listaNotificacion;
    private OwnLinkedList<PuntosCliente> puntosCliente;

    public Cliente(String nombreCompleto, String celular, String email, String contraseña, LocalDate fechaNacimiento,
            LocalDate fechaRegistro, int puntos, TipoRango tipoRango, int id) {
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.email = email;
        this.contraseña = contraseña; // Debería ser encriptada
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.puntos = puntos;
        this.tipoRango = tipoRango;
        this.id = id;
        this.listaCuentas = new ArrayList<>();
        this.listaNotificacion = new OwnCircularList<>();
        this.puntosCliente = new OwnLinkedList<>();
    }

    // Getters y Setters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public TipoRango getTipoRango() {
        return tipoRango;
    }

    public void setTipoRango(TipoRango tipoRango) {
        this.tipoRango = tipoRango;
    }

    public OwnCircularList<Notificacion> getListaNotificacion() {
        return listaNotificacion;
    }

    public void setListaNotificacion(OwnCircularList<Notificacion> listaNotificacion) {
        this.listaNotificacion = listaNotificacion;
    }

    public int getId() {
        return id;
    }

    

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    public OwnLinkedList<PuntosCliente> getPuntosCliente() {
        return puntosCliente;
    }

    public void setPuntosCliente(OwnLinkedList<PuntosCliente> puntosCliente) {
        this.puntosCliente = puntosCliente;
    }

}
