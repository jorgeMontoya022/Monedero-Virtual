package co.edu.uniquindio.monedero_virtual.model.enums;

public enum Beneficio {

    REDUCCION_COMISION, MES_LIBRE_RETIROS, BONO_SALDO;

    @Override
public String toString() {
    switch (this) {
        case REDUCCION_COMISION: return "Reducción de Comisión";
        case MES_LIBRE_RETIROS: return "Mes libre de Retiros";
        case BONO_SALDO: return "Bono de Saldo";
        default: return name();
    }
}

        

}
