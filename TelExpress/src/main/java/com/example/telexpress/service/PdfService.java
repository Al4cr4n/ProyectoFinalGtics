package com.example.telexpress.service;

import com.example.telexpress.dto.DatosCompra;
import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoOrdenes;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PdfService {
    public String generateBoucher(Ordenes orden, DatosCompra pagoRequestDTO) {
        String pdfPath = "bouchers/orden_" + orden.getIdOrdenes() + ".pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);

            // Escribir detalles del boucher
            contentStream.showText("Boucher de Compra");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("N° Orden: " + orden.getIdOrdenes());
            contentStream.newLine();
            contentStream.showText("Correo: " + pagoRequestDTO.getCorreo()); // Usar el correo del DTO
            contentStream.newLine();
            contentStream.showText("Dirección: " + pagoRequestDTO.getDireccion()); // Usar la dirección del DTO
            contentStream.newLine();
            contentStream.showText("Fecha de Compra: " + LocalDateTime.now());
            contentStream.newLine();
            contentStream.newLine();
            // Listar productos
            contentStream.showText("Productos:");
            contentStream.newLine();
            for (ProductoOrdenes po : orden.getProductoHasOrdenes()) {
                Producto producto = po.getProducto(); // Obtener el producto asociado
                contentStream.showText(
                        "- " + producto.getNombreProducto() + " x" + po.getCantidadxproducto() +
                                " (S/. " + producto.getPrecio() + ")"
                );
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            // Guardar el PDF
            document.save(pdfPath);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
        return pdfPath;
    }
}
