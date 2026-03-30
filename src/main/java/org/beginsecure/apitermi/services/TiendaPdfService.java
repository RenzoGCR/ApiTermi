package org.beginsecure.apitermi.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.beginsecure.apitermi.entities.Tienda;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

@Service
public class TiendaPdfService {

    public void exportar(HttpServletResponse response, List<Tienda> tiendas, String agenciaFiltro) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // Estilo del título
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitulo.setSize(18);
        fontTitulo.setColor(new Color(123, 97, 255)); // El morado de tu diseño

        Paragraph p = new Paragraph("Reporte de Tiendas", fontTitulo);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(p);

        String subtitulo = (agenciaFiltro == null || agenciaFiltro.isEmpty()) ? "Todas las agencias" : "Agencia: " + agenciaFiltro;
        Paragraph p2 = new Paragraph(subtitulo, FontFactory.getFont(FontFactory.HELVETICA, 12));
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setSpacingAfter(20);
        documento.add(p2);

        // Crear tabla PDF
        PdfPTable tabla = new PdfPTable(2); // 2 columnas
        tabla.setWidthPercentage(100);

        // Cabeceras
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(30, 30, 30));
        cell.setPadding(5);
        Font fontHC = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHC.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Tienda", fontHC));
        tabla.addCell(cell);
        cell.setPhrase(new Phrase("Agencia", fontHC));
        tabla.addCell(cell);

        // Datos
        for (Tienda t : tiendas) {
            tabla.addCell(t.getTienda());
            tabla.addCell(t.getAgencia());
        }

        documento.add(tabla);
        documento.close();
    }
}