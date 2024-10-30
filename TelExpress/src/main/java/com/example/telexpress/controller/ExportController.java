/*package com.example.telexpress.controller;

import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.OrdenesRepository;
import com.example.telexpress.repository.UsuarioRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.element.Text;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/agente/export")
public class ExportController {
    private final OrdenesRepository ordenesRepository;
    private final UsuarioRepository usuarioRepository;

    public ExportController(OrdenesRepository ordenesRepository,UsuarioRepository usuarioRepository) {
        this.ordenesRepository = ordenesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByCorreo(correo);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=lista_ordenes.xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ordenes");

        // Crear encabezados
        String[] columnas = {"N°", "Cliente", "Apellido", "Dirección", "Estado", "Fecha de Arribo"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO");
        List<Ordenes> ordenes = ordenesRepository.findByEstadoOrdenesIn(estadosTodos);

        int rowNum = 1;
        for (Ordenes orden : ordenes){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(orden.getIdOrdenes());
            row.createCell(1).setCellValue(orden.getUsuario().getNombre());
            row.createCell(2).setCellValue(orden.getUsuario().getApellido());
            row.createCell(3).setCellValue(orden.getUsuario().getDireccion());
            row.createCell(4).setCellValue(orden.getEstadoOrdenes());
            row.createCell(5).setCellValue(orden.getFechaArribo().toString());
        }
        // Ajuste automático del ancho de columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    // Método para aplicar estilo a los encabezados
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    @GetMapping("/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException{
        response.setContentType("aplication/pdf");
        response.setHeader("Content-Disposition","attachment; filename =ordenes.pdf");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document document = new Document(pdfDoc);
        //titulo del doc
        document.add(new Paragraph("Lista de Ordenes")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(10));
        //encabezados de la tabla
        float[] columnWidths = {1,3,3,4,3,3};
        Table table = new Table(columnWidths);
        String[] columnas ={"N°", "Cliente", "Apellido", "Dirección", "Estado", "Fecha de Arribo"};
        for(String column : columnas){
            Cell headerCell = new Cell().add(new Paragraph(column)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.WHITE));
            headerCell.setBackgroundColor(ColorConstants.DARK_GRAY);
            headerCell.setTextAlignment(TextAlignment.LEFT);
            table.addHeaderCell(headerCell);
        }
        // Obtener las órdenes
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByCorreo(correo);
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO", "EN PROCESO");
        List<Ordenes> ordenes = ordenesRepository.findByEstadoOrdenesIn(estadosTodos);

        //ahora se llenan las filas de la tabla
        for(Ordenes orden : ordenes){
            table.addCell(new Cell().add(new Paragraph(String.valueOf(orden.getIdOrdenes()))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getNombre())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getApellido())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getApellido())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getEstadoOrdenes())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(orden.getFechaArribo()))).setTextAlignment(TextAlignment.LEFT));
        }
        document.add(table);
        document.close();
    }
}*/
package com.example.telexpress.controller;

import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.OrdenesRepository;
import com.example.telexpress.repository.UsuarioRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/agente/export")
public class ExportController {
    private final OrdenesRepository ordenesRepository;
    private final UsuarioRepository usuarioRepository;

    public ExportController(OrdenesRepository ordenesRepository, UsuarioRepository usuarioRepository) {
        this.ordenesRepository = ordenesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Exportar a Excel usando Apache POI
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByCorreo(correo);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=lista_ordenes.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ordenes");

        // Crear encabezados
        String[] columnas = {"N°", "Cliente", "Apellido", "Dirección", "Estado", "Fecha de Arribo"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            //Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }

        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO");
        List<Ordenes> ordenes = ordenesRepository.findByEstadoOrdenesIn(estadosTodos);

        int rowNum = 1;
        for (Ordenes orden : ordenes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(orden.getIdOrdenes());
            row.createCell(1).setCellValue(orden.getUsuario().getNombre());
            row.createCell(2).setCellValue(orden.getUsuario().getApellido());
            row.createCell(3).setCellValue(orden.getUsuario().getDireccion());
            row.createCell(4).setCellValue(orden.getEstadoOrdenes());
            row.createCell(5).setCellValue(orden.getFechaArribo().toString());
        }

        // Ajuste automático del ancho de columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Método para aplicar estilo a los encabezados en Apache POI
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    // Exportar a PDF usando iText
    @GetMapping("/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ordenes.pdf");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document document = new Document(pdfDoc);

        // Título del documento
        document.add(new Paragraph("Lista de Órdenes")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Crear encabezados de la tabla en iText
        float[] columnWidths = {1, 3, 3, 4, 3, 3};
        Table table = new Table(columnWidths);
        String[] columnas = {"N°", "Cliente", "Apellido", "Dirección", "Estado", "Fecha de Arribo"};

        for (String column : columnas) {
            Cell headerCell = new Cell().add(new Paragraph(column)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.WHITE));
            headerCell.setBackgroundColor(ColorConstants.DARK_GRAY);
            headerCell.setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(headerCell);
        }

        // Obtener las órdenes
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByCorreo(correo);
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO");
        List<Ordenes> ordenes = ordenesRepository.findByEstadoOrdenesIn(estadosTodos);

        // Llenar filas de la tabla con datos en iText
        for (Ordenes orden : ordenes) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(orden.getIdOrdenes()))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getNombre())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getApellido())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getUsuario().getDireccion())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getEstadoOrdenes())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(orden.getFechaArribo().toString())).setTextAlignment(TextAlignment.CENTER));
        }

        // Agregar la tabla al documento
        document.add(table);
        document.close();
    }
}

