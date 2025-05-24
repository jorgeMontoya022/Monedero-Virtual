package co.edu.uniquindio.monedero_virtual.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Deposito;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.Transferencia;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorReportePdf {

    private final Document document;
    private final PdfWriter writer;
    private final Cliente cliente;
    private final Cuenta cuenta;
    private final List<Transaccion> movimientos;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;

    public GeneradorReportePdf(
            OutputStream outputStream,
            Cliente cliente,
            Cuenta cuenta,
            List<Transaccion> movimientos,
            LocalDate fechaInicio,
            LocalDate fechaFin) throws DocumentException {
        this.document = new Document(PageSize.A4, 40, 40, 70, 50);
        this.writer = PdfWriter.getInstance(document, outputStream);
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.movimientos = movimientos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public void generarPdf() throws DocumentException {
        document.open();
        agregarEncabezadoMonedero();
        agregarEspaciado(15);
        agregarInformacionCliente();
        agregarEspaciado(20);
        agregarTablaMovimientos();
        agregarEspaciado(20);
        agregarResumenFinanciero();
        agregarEspaciado(30);
        agregarPiePagina();
        document.close();
    }

    private void agregarEspaciado(float puntos) throws DocumentException {
        Paragraph espaciado = new Paragraph();
        espaciado.setSpacingAfter(puntos);
        document.add(espaciado);
    }

    private void agregarEncabezadoMonedero() throws DocumentException {
        // Crear tabla para el encabezado principal
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[] { 1.2f, 3.8f });

        // === CELDA DEL LOGO ===
        PdfPCell logoCell = new PdfPCell();
        logoCell.setPadding(20);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        try {
            Image logo = Image.getInstance(getClass().getResource("/co/edu/uniquindio/monedero_virtual/solvi blanco.png"));
            logo.scaleToFit(70, 70);
            logoCell.addElement(logo);
        } catch (Exception e) {
            // Logo alternativo con mejor diseño
            Paragraph logoText = new Paragraph("SOLVI", PdfStyle.getTitleFont());
            logoText.setAlignment(Element.ALIGN_CENTER);
            logoCell.addElement(logoText);
        }

        // === CELDA DE INFORMACIÓN PRINCIPAL ===
        PdfPCell infoCell = new PdfPCell();
        infoCell.setPadding(25);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);
        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Título principal con mejor tipografía
        Paragraph tituloMonedero = new Paragraph();
        Chunk titulo = new Chunk("SOLVI ", PdfStyle.getTitleFont());
        Chunk subtitulo = new Chunk("MONEDERO VIRTUAL", new Font(Font.FontFamily.HELVETICA, 24, Font.NORMAL, BaseColor.WHITE));
        tituloMonedero.add(titulo);
        tituloMonedero.add(subtitulo);
        tituloMonedero.setAlignment(Element.ALIGN_LEFT);
        infoCell.addElement(tituloMonedero);

        // Separador visual
        agregarSeparadorEnCelda(infoCell);

        // Subtítulo del reporte con mejor espaciado
        Paragraph tipoReporte = new Paragraph("REPORTE DE MOVIMIENTOS TRANSACCIONALES", 
                                            new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE));
        tipoReporte.setAlignment(Element.ALIGN_LEFT);
        tipoReporte.setSpacingBefore(8);
        infoCell.addElement(tipoReporte);

        // Fecha de generación
        Paragraph fechaGeneracion = new Paragraph("Generado el: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(255, 255, 255, 180)));
        fechaGeneracion.setAlignment(Element.ALIGN_LEFT);
        fechaGeneracion.setSpacingBefore(5);
        infoCell.addElement(fechaGeneracion);

        headerTable.addCell(logoCell);
        headerTable.addCell(infoCell);
        document.add(headerTable);
    }

    private void agregarSeparadorEnCelda(PdfPCell celda) {
        Paragraph separador = new Paragraph();
        separador.add(new Chunk("_____", new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.WHITE)));
        separador.setSpacingBefore(5);
        celda.addElement(separador);
    }

    private void agregarInformacionCliente() throws DocumentException {
        // Título de sección con diseño mejorado
        Paragraph tituloSeccion = new Paragraph("INFORMACIÓN DEL CLIENTE", PdfStyle.getSubtitleFont());
        tituloSeccion.setSpacingBefore(10);
        tituloSeccion.setSpacingAfter(10);
        document.add(tituloSeccion);

        // Tabla principal de información
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[] { 3f, 2f });

        // === CELDA IZQUIERDA: Información del cliente ===
        PdfPCell clienteCell = new PdfPCell();
        clienteCell.setPadding(20);
        clienteCell.setBackgroundColor(PdfStyle.BACKGROUND_COLOR);
        clienteCell.setBorder(Rectangle.NO_BORDER);

        // Información del cliente con mejor formato
        Paragraph infoCliente = new Paragraph();
        agregarCampoInfo(infoCliente, "Cliente:", cliente.getNombreCompleto().toUpperCase());
        agregarCampoInfo(infoCliente, "Documento:", String.valueOf(cliente.getId()));
        agregarCampoInfo(infoCliente, "Cuenta:", cuenta.getBanco() + " - " + cuenta.getNumeroCuenta());
        clienteCell.addElement(infoCliente);

        // === CELDA DERECHA: Información del período ===
        PdfPCell periodoCell = new PdfPCell();
        periodoCell.setPadding(20);
        periodoCell.setBackgroundColor(new BaseColor(250, 251, 252));
        periodoCell.setBorder(Rectangle.NO_BORDER);

        Paragraph infoPeriodo = new Paragraph();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        agregarCampoInfo(infoPeriodo, "Período:", "Del " + fechaInicio.format(formatter) + "\nal " + fechaFin.format(formatter));
        agregarCampoInfo(infoPeriodo, "Total movimientos:", String.valueOf(movimientos.size()));
        periodoCell.addElement(infoPeriodo);

        infoTable.addCell(clienteCell);
        infoTable.addCell(periodoCell);
        document.add(infoTable);
    }

    private void agregarCampoInfo(Paragraph paragraph, String etiqueta, String valor) {
        paragraph.add(new Phrase(etiqueta + " ", PdfStyle.getHeaderFont()));
        paragraph.add(new Phrase(valor, PdfStyle.getNormalFont()));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase(" ", new Font(Font.FontFamily.HELVETICA, 6))); // Espaciado entre campos
        paragraph.add(Chunk.NEWLINE);
    }

    private void agregarTablaMovimientos() throws DocumentException {
        // Título de la sección con línea decorativa
        Paragraph movimientosTitle = new Paragraph();
        movimientosTitle.add(new Phrase("DETALLE DE MOVIMIENTOS", PdfStyle.getSubtitleFont()));
        movimientosTitle.setSpacingBefore(10);
        movimientosTitle.setSpacingAfter(5);
        document.add(movimientosTitle);

        // Línea decorativa
        PdfPTable lineaDecorativa = new PdfPTable(1);
        lineaDecorativa.setWidthPercentage(100);
        PdfPCell lineaCell = new PdfPCell();
        lineaCell.setFixedHeight(2f);
        lineaCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);
        lineaCell.setBorder(Rectangle.NO_BORDER);
        lineaDecorativa.addCell(lineaCell);
        document.add(lineaDecorativa);

        agregarEspaciado(10);

        // Tabla de movimientos con mejor diseño
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        float[] columnWidths = { 1.8f, 2.5f, 1.5f, 1.8f, 2f, 1.4f };
        table.setWidths(columnWidths);

        agregarEncabezadosTablaMovimientos(table);
        agregarContenidoTablaMovimientos(table);

        document.add(table);
    }

    private void agregarEncabezadosTablaMovimientos(PdfPTable table) {
        String[] headers = { "FECHA", "DESCRIPCIÓN", "TIPO", "MONTO", "MONEDERO", "ID" };

        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);
            headerCell.setPadding(15);
            headerCell.setBorderColor(BaseColor.WHITE);
            headerCell.setBorderWidth(1);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            headerCell.setPhrase(new Phrase(header, headerFont));
            table.addCell(headerCell);
        }
    }

    private void agregarContenidoTablaMovimientos(PdfPTable table) {
        boolean alternarColor = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Transaccion transaccion : movimientos) {
            BaseColor colorFila = alternarColor ? PdfStyle.BACKGROUND_COLOR : BaseColor.WHITE;

            // Fecha - centrada
            agregarCeldaMovimiento(table, transaccion.getFechaTransaccion().format(formatter), 
                                 colorFila, false, Element.ALIGN_CENTER);

            // Descripción - alineada a la izquierda
            agregarCeldaMovimiento(table, transaccion.getDescripcion(), 
                                 colorFila, false, Element.ALIGN_LEFT);

            // Tipo - centrada con color especial
            String tipoTransaccion = getTipoTransaccion(transaccion);
            agregarCeldaMovimiento(table, tipoTransaccion, colorFila, true, Element.ALIGN_CENTER);

            // Monto - alineado a la derecha
            String montoFormateado = String.format("$%,.2f", transaccion.getMonto());
            agregarCeldaMovimiento(table, montoFormateado, colorFila, false, Element.ALIGN_RIGHT);

            // Monedero - alineado a la izquierda
            agregarCeldaMovimiento(table, transaccion.getMonedero().getNombreMonedero(), 
                                 colorFila, false, Element.ALIGN_LEFT);

            // ID - centrado
            agregarCeldaMovimiento(table, String.valueOf(transaccion.getIdTransaccion()), 
                                 colorFila, false, Element.ALIGN_CENTER);

            alternarColor = !alternarColor;
        }
    }

    private void agregarCeldaMovimiento(PdfPTable table, String contenido, BaseColor colorFondo, 
                                      boolean esTipo, int alineacion) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(12);
        cell.setBorderColor(new BaseColor(230, 230, 230));
        cell.setBorderWidth(0.5f);
        cell.setBackgroundColor(colorFondo);
        cell.setHorizontalAlignment(alineacion);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        if (esTipo) {
            Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            font.setColor(PdfStyle.getColorByTransactionType(contenido));
            cell.setPhrase(new Phrase(contenido, font));
        } else {
            cell.setPhrase(new Phrase(contenido, PdfStyle.getNormalFont()));
        }

        table.addCell(cell);
    }

    private String getTipoTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Deposito) {
            return "Depósito";
        } else if (transaccion instanceof Transferencia) {
            return "Transferencia";
        } else {
            return "Retiro";
        }
    }

    private void agregarResumenFinanciero() throws DocumentException {
        // Título con diseño mejorado
        Paragraph tituloResumen = new Paragraph("RESUMEN FINANCIERO", PdfStyle.getSubtitleFont());
        tituloResumen.setAlignment(Element.ALIGN_CENTER);
        tituloResumen.setSpacingBefore(10);
        tituloResumen.setSpacingAfter(15);
        document.add(tituloResumen);

        // Calcular totales
        double totalDepositos = 0;
        double totalTransferencias = 0;
        double totalRetiros = 0;

        for (Transaccion transaccion : movimientos) {
            if (transaccion instanceof Deposito) {
                totalDepositos += transaccion.getMonto();
            } else if (transaccion instanceof Transferencia) {
                totalTransferencias += transaccion.getMonto();
            } else {
                totalRetiros += transaccion.getMonto();
            }
        }

        // Crear tabla de resumen con mejor diseño
        PdfPTable resumenTable = new PdfPTable(3);
        resumenTable.setWidthPercentage(85);
        resumenTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        resumenTable.setWidths(new float[] { 1f, 1f, 1f });

        // Tarjetas de resumen
        agregarTarjetaResumen(resumenTable, "TOTAL DEPÓSITOS", String.format("$%,.2f", totalDepositos), 
                            PdfStyle.SUCCESS_COLOR, "📈");
        agregarTarjetaResumen(resumenTable, "TOTAL TRANSFERENCIAS", String.format("$%,.2f", totalTransferencias), 
                            PdfStyle.WARNING_COLOR, "💸");
        agregarTarjetaResumen(resumenTable, "TOTAL RETIROS", String.format("$%,.2f", totalRetiros), 
                            PdfStyle.DANGER_COLOR, "📉");

        document.add(resumenTable);
    }

    private void agregarTarjetaResumen(PdfPTable table, String titulo, String valor, BaseColor color, String icono) {
        PdfPCell card = new PdfPCell();
        card.setPadding(20);
        card.setBackgroundColor(new BaseColor(255, 255, 255));
        card.setBorderColor(color);
        card.setBorderWidth(2);
        card.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph contenido = new Paragraph();
        
        // Título de la tarjeta
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, color);
        contenido.add(new Phrase(titulo, tituloFont));
        contenido.add(Chunk.NEWLINE);
        contenido.add(Chunk.NEWLINE);
        
        // Valor principal
        Font valorFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, color);
        Phrase valorPhrase = new Phrase(valor, valorFont);
        contenido.add(valorPhrase);
        
        contenido.setAlignment(Element.ALIGN_CENTER);
        card.addElement(contenido);
        table.addCell(card);
    }

    private void agregarPiePagina() throws DocumentException {
        // Línea separadora elegante
        PdfPTable separadorTable = new PdfPTable(1);
        separadorTable.setWidthPercentage(80);
        separadorTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell separadorCell = new PdfPCell();
        separadorCell.setFixedHeight(1f);
        separadorCell.setBackgroundColor(PdfStyle.LIGHT_PRIMARY);
        separadorCell.setBorder(Rectangle.NO_BORDER);
        separadorTable.addCell(separadorCell);
        document.add(separadorTable);

        agregarEspaciado(20);

        // Información de pie de página con mejor diseño
        PdfPTable firmaTable = new PdfPTable(1);
        firmaTable.setWidthPercentage(100);

        PdfPCell firmaCell = new PdfPCell();
        firmaCell.setPadding(20);
        firmaCell.setBorder(Rectangle.NO_BORDER);
        firmaCell.setBackgroundColor(new BaseColor(248, 249, 250));

        Paragraph firmaInfo = new Paragraph();
        
        // Nombre de la empresa
        Font empresaFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, PdfStyle.PRIMARY_COLOR);
        firmaInfo.add(new Phrase("SOLVI MONEDERO VIRTUAL", empresaFont));
        firmaInfo.add(Chunk.NEWLINE);
        firmaInfo.add(Chunk.NEWLINE);
        
        // Información del gerente
        firmaInfo.add(new Phrase("Gerente General: ", PdfStyle.getHeaderFont()));
        firmaInfo.add(new Phrase("Robinson Arias Muños", PdfStyle.getNormalFont()));
        firmaInfo.add(Chunk.NEWLINE);
        firmaInfo.add(Chunk.NEWLINE);
        
        // Fecha y hora de generación
        firmaInfo.add(new Phrase("Documento generado automáticamente el ", PdfStyle.getSmallFont()));
        firmaInfo.add(new Phrase(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                                " a las " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), 
                                PdfStyle.getSmallFont()));
        
        firmaInfo.setAlignment(Element.ALIGN_CENTER);
        firmaCell.addElement(firmaInfo);
        firmaTable.addCell(firmaCell);
        document.add(firmaTable);
    }
}