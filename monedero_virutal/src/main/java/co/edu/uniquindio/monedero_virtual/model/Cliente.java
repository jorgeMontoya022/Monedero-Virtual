package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnCircularList;

public class Cliente implements Comparable<Cliente> {
    private String nombreCompleto;
    private String celular;
    private String email;
    private int contraseña;
    private LocalDate fechaNacimiento; 
    private LocalDate fechaRegistro; 
    private int id;
    private String dirección;
    private List<Cuenta> listaCuentas;
    private TipoRango tipoRango;
    private OwnCircularList<Notificacion> listaNotificacion;
    private PuntosCliente puntos;

    public Cliente(String nombreCompleto, String celular, String email, int contraseña, LocalDate fechaNacimiento,
            LocalDate fechaRegistro,String direccion ,TipoRango tipoRango, int id) {
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.email = email;
        this.contraseña = contraseña; 
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.tipoRango = tipoRango;
        this.id = id;
        this.dirección = direccion;
        this.listaCuentas = new ArrayList<>();
        this.listaNotificacion = new OwnCircularList<>();
        this.puntos = new PuntosCliente(this);
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

    public int getContraseña() {
        return contraseña;
    }

    public void setContraseña(int contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
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

    public PuntosCliente getPuntos() {
        return puntos;
    }

    public void setPuntos(PuntosCliente puntos) {
        this.puntos = puntos;
    }

    public void agregarPuntos(Transaccion transaccion){
        puntos.añadirPuntos(transaccion);
    }

    public void retirarPuntos(Transaccion transaccion){
        puntos.eliminarPuntos(transaccion);
    }

    @Override
    public int compareTo(Cliente otro) {
        int comparacion = Integer.compare(otro.getPuntos().getPuntosAcumulados(), this.getPuntos().getPuntosAcumulados());
        if (comparacion == 0){
            return Integer.compare(this.getId(), otro.getId());
        }
        return comparacion;
    }

    private void setRangoCliente (){
        this.tipoRango = TipoRango.obtenerRango(getPuntos().getPuntosAcumulados());
    }
    


}

