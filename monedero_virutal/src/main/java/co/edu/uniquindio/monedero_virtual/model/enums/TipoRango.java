package co.edu.uniquindio.monedero_virtual.model.enums;

public enum TipoRango {
    BRONCE (0, 500),
    ORO (501, 1000),
    PLATA(1001, 5000),
    PLATINO(5001, Integer.MAX_VALUE);

    private final int puntosMinimos;
    private final int puntosMaximos;

    TipoRango(int puntosMinimos, int puntosMaximos){
        this.puntosMaximos = puntosMaximos;
        this.puntosMinimos = puntosMinimos;
    }

    public int getPuntosMinimos(){
        return puntosMinimos;
    }
    
    public int getPuntosMaximos(){
        return puntosMaximos;
    }

    public static TipoRango obtenerRango(int puntos){
        for (TipoRango rango : values()){
            if (puntos >= rango.getPuntosMinimos() && puntos <= rango.getPuntosMaximos()){
                return rango;
            }
        }
        return BRONCE;
    }


}