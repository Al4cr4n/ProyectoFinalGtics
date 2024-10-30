/*package com.example.telexpress.controller;

import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.OrdenesRepository;
import com.example.telexpress.repository.UsuarioRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

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
            row.createCell(0).
        }
    }
}
*/