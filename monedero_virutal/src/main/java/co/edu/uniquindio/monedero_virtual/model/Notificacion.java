package co.edu.uniquindio.monedero_virtual.model;

import java.time.LocalDate;

import co.edu.uniquindio.monedero_virtual.model.enums.TipoNotifiacion;

public class Notificacion {
    private String mensaje;
    private TipoNotifiacion tipoNotificacion;
    private LocalDate fechaNotifiacion;


    public Notificacion(String mensaje, TipoNotifiacion tipoNotificacion, LocalDate fechaNotifiacion) {
        this.mensaje = mensaje;
        this.tipoNotificacion = tipoNotificacion;
        this.fechaNotifiacion = fechaNotifiacion;
    }


    public String getMensaje() {
        return mensaje;
    }


    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public TipoNotifiacion getTipoNotificacion() {
        return tipoNotificacion;
    }


    public void setTipoNotificacion(TipoNotifiacion tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }


    public LocalDate getFechaNotifiacion() {
        return fechaNotifiacion;
    }


    public void setFechaNotifiacion(LocalDate fechaNotifiacion) {
        this.fechaNotifiacion = fechaNotifiacion;
    }

    


    
}
