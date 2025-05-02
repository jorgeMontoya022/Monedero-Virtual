package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnCircularList;

public class Cliente {
    private final String nombreCompleto;
    private final String celular;
    private final String email;
    private String contraseña; 
    private final LocalDate fechaNacimiento; // Usar LocalDate en lugar de String
    private final LocalDate fechaRegistro;  // Usar LocalDate en lugar de String
    private int puntos;
    private List<Cuenta> listaCuentas;
    private TipoRango tipoRango;
    private OwnCircularList<Notificacion> listaNotificacion;

    //clase puntos(por definir)

    /**
     * Constructor para la clase Cliente.
     * @param nombreCompleto El nombre completo del cliente.
     * @param celular El número de celular del cliente.
     * @param email El correo electrónico del cliente.
     * @param contraseña La contraseña del cliente.
     * @param fechaNacimiento La fecha de nacimiento del cliente.
     * @param fechaRegistro La fecha en que el cliente se registró.
     * @param puntos Los puntos iniciales del cliente.
     * @param tipoRango El rango del cliente.
     */
    public Cliente(String nombreCompleto, String celular, String email, String contraseña, LocalDate fechaNacimiento,
            LocalDate fechaRegistro, int puntos, TipoRango tipoRango) {
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.email = email;
        this.contraseña = contraseña; // Debería ser encriptada
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.puntos = puntos;
        this.tipoRango = tipoRango;
        this.listaCuentas = new ArrayList<>();
        this.listaNotificacion = new OwnCircularList<>();
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

}
