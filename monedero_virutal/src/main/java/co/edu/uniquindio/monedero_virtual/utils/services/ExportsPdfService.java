package co.edu.uniquindio.monedero_virtual.utils.services;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.utils.GeneradorReportePdf;

public class ExportsPdfService {

    public void exportarMovimientos(
            String filePath,
            Cliente cliente,
            Cuenta cuenta,
            List<Transaccion> movimientos,
            LocalDate fechaInicio,
            LocalDate fechaFin) throws Exception {

        try (FileOutputStream output = new FileOutputStream(filePath)) {
            GeneradorReportePdf pdfGenerador = new GeneradorReportePdf(output, cliente, cuenta, movimientos,
                    fechaInicio, fechaFin);
            pdfGenerador.generarPdf();
        }

    }

}
