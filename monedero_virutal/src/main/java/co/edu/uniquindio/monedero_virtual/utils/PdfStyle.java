package co.edu.uniquindio.monedero_virtual.utils;

import com.itextpdf.text.*;

public class PdfStyle {
    
    // === COLORES IDENTIDAD SOLVI (Extraídos del FXML) ===
    
    // Colores principales del gradiente Solvi
    public static final BaseColor SOLVI_PRIMARY = new BaseColor(102, 126, 234);      // #667eea
    public static final BaseColor SOLVI_SECONDARY = new BaseColor(118, 75, 162);     // #764ba2
    
    // Colores de fondo del sistema
    public static final BaseColor SOLVI_BACKGROUND_LIGHT = new BaseColor(245, 247, 250);  // #f5f7fa
    public static final BaseColor SOLVI_BACKGROUND_GRADIENT = new BaseColor(195, 207, 226); // #c3cfe2
    
    // Colores de texto del sistema
    public static final BaseColor SOLVI_TEXT_DARK = new BaseColor(51, 51, 51);       // #333333
    public static final BaseColor SOLVI_TEXT_MEDIUM = new BaseColor(102, 102, 102);  // #666666
    public static final BaseColor SOLVI_TEXT_LIGHT = new BaseColor(136, 136, 136);   // #888888
    
    // Colores puros
    public static final BaseColor SOLVI_WHITE = BaseColor.WHITE;
    public static final BaseColor SOLVI_BLACK = BaseColor.BLACK;
    
    // === COLORES DERIVADOS PARA FUNCIONALIDADES ===
    
    // Variaciones del color principal para diferentes usos
    public static final BaseColor SOLVI_PRIMARY_LIGHT = new BaseColor(102, 126, 234, 80);   // Primary con transparencia
    public static final BaseColor SOLVI_PRIMARY_ULTRA_LIGHT = new BaseColor(102, 126, 234, 40); // Primary muy suave
    public static final BaseColor SOLVI_SECONDARY_LIGHT = new BaseColor(118, 75, 162, 80);  // Secondary con transparencia
    
    // Colores para estados (basados en la paleta Solvi)
    public static final BaseColor SOLVI_SUCCESS = new BaseColor(76, 175, 80);    // Verde suave inspirado en Solvi
    public static final BaseColor SOLVI_WARNING = new BaseColor(255, 152, 0);    // Naranja que combina con la paleta
    public static final BaseColor SOLVI_DANGER = new BaseColor(244, 67, 54);     // Rojo que no choca con el gradiente
    public static final BaseColor SOLVI_INFO = new BaseColor(33, 150, 243);      // Azul complementario
    
    // Colores de fondo alternativos
    public static final BaseColor SOLVI_CARD_BACKGROUND = new BaseColor(250, 251, 253);     // Fondo de tarjetas
    public static final BaseColor SOLVI_STRIPE_BACKGROUND = new BaseColor(248, 249, 252);   // Fondo de filas alternas
    
    // === ALIAS PARA COMPATIBILIDAD (mantener funcionalidad existente) ===
    public static final BaseColor PRIMARY_COLOR = SOLVI_PRIMARY;
    public static final BaseColor SECONDARY_COLOR = SOLVI_SECONDARY;
    public static final BaseColor LIGHT_PRIMARY = SOLVI_PRIMARY_LIGHT;
    public static final BaseColor BACKGROUND_COLOR = SOLVI_BACKGROUND_LIGHT;
    public static final BaseColor TEXT_COLOR = SOLVI_TEXT_DARK;
    public static final BaseColor SECONDARY_TEXT = SOLVI_TEXT_MEDIUM;
    public static final BaseColor WHITE = SOLVI_WHITE;
    public static final BaseColor SUCCESS_COLOR = SOLVI_SUCCESS;
    public static final BaseColor WARNING_COLOR = SOLVI_WARNING;
    public static final BaseColor DANGER_COLOR = SOLVI_DANGER;

    // === FUENTES CON IDENTIDAD SOLVI ===
    
    /**
     * Fuente para títulos principales - Refleja la elegancia de Solvi
     */
    public static Font getSolviTitleFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD);
        font.setColor(SOLVI_WHITE);
        return font;
    }
    
    /**
     * Fuente para subtítulos - Color principal de Solvi
     */
    public static Font getSolviSubtitleFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        font.setColor(SOLVI_PRIMARY);
        return font;
    }
    
    /**
     * Fuente para encabezados de sección
     */
    public static Font getSolviHeaderFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        font.setColor(SOLVI_TEXT_DARK);
        return font;
    }
    
    /**
     * Fuente normal para contenido
     */
    public static Font getSolviNormalFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        font.setColor(SOLVI_TEXT_DARK);
        return font;
    }
    
    /**
     * Fuente pequeña para detalles
     */
    public static Font getSolviSmallFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        font.setColor(SOLVI_TEXT_MEDIUM);
        return font;
    }
    
    /**
     * Fuente blanca para elementos sobre fondo colorido
     */
    public static Font getSolviWhiteFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        font.setColor(SOLVI_WHITE);
        return font;
    }
    
    /**
     * Fuente blanca normal
     */
    public static Font getSolviWhiteNormalFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        font.setColor(SOLVI_WHITE);
        return font;
    }
    
    /**
     * Fuente especial para la marca Solvi
     */
    public static Font getSolviBrandFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD);
        font.setColor(SOLVI_WHITE);
        return font;
    }
    
    /**
     * Fuente para valores monetarios importantes
     */
    public static Font getSolviMoneyFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        font.setColor(SOLVI_PRIMARY);
        return font;
    }

    // === MÉTODOS DE COMPATIBILIDAD (mantener API existente) ===
    
    public static Font getTitleFont() { return getSolviTitleFont(); }
    public static Font getSubtitleFont() { return getSolviSubtitleFont(); }
    public static Font getHeaderFont() { return getSolviHeaderFont(); }
    public static Font getNormalFont() { return getSolviNormalFont(); }
    public static Font getSmallFont() { return getSolviSmallFont(); }
    public static Font getWhiteFont() { return getSolviWhiteFont(); }
    public static Font getWhiteNormalFont() { return getSolviWhiteNormalFont(); }

    // === MÉTODOS UTILITARIOS ESPECÍFICOS DE SOLVI ===
    
    /**
     * Obtiene el color apropiado según el tipo de transacción
     * Usa la paleta de colores de Solvi
     */
    public static BaseColor getSolviColorByTransactionType(String tipoTransaccion) {
        switch (tipoTransaccion.toLowerCase()) {
            case "depósito":
            case "deposito":
                return SOLVI_SUCCESS;
            case "transferencia":
                return SOLVI_WARNING; 
            case "retiro":
                return SOLVI_DANGER;
            default:
                return SOLVI_TEXT_DARK;
        }
    }
    
    /**
     * Método de compatibilidad
     */
    public static BaseColor getColorByTransactionType(String tipoTransaccion) {
        return getSolviColorByTransactionType(tipoTransaccion);
    }
    
    /**
     * Obtiene un color de fondo alternativo para crear variedad visual
     * manteniendo la coherencia con Solvi
     */
    public static BaseColor getSolviAlternateBackground(boolean isAlternate) {
        return isAlternate ? SOLVI_STRIPE_BACKGROUND : SOLVI_WHITE;
    }
    
    /**
     * Crea un gradiente conceptual usando los colores de Solvi
     * (Para usar en efectos visuales donde sea posible)
     */
    public static BaseColor getSolviGradientColor(float position) {
        // Interpola entre SOLVI_PRIMARY y SOLVI_SECONDARY
        // position debe estar entre 0.0 y 1.0
        int r = (int) (102 + (118 - 102) * position);
        int g = (int) (126 + (75 - 126) * position);  
        int b = (int) (234 + (162 - 234) * position);
        return new BaseColor(r, g, b);
    }
    
    /**
     * Colores temáticos para diferentes secciones del reporte
     */
    public static class SolviTheme {
        public static final BaseColor HEADER = SOLVI_PRIMARY;
        public static final BaseColor SECTION_TITLE = SOLVI_SECONDARY;
        public static final BaseColor TABLE_HEADER = SOLVI_PRIMARY;
        public static final BaseColor TABLE_STRIPE = SOLVI_STRIPE_BACKGROUND;
        public static final BaseColor CARD_BACKGROUND = SOLVI_CARD_BACKGROUND;
        public static final BaseColor SEPARATOR = SOLVI_PRIMARY_LIGHT;
        public static final BaseColor FOOTER = SOLVI_BACKGROUND_GRADIENT;
    }
}